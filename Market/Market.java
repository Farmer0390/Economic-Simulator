package Market;

import java.util.HashMap;
import java.util.PriorityQueue;
import java.math.BigDecimal;
import java.util.Map;
import java.util.LinkedHashMap;

import Economic_enitity.Company;
import Economic_enitity.Economic_Entity;

import Main.ProductionRecipe;

import java.util.Comparator;

public class Market {

    private final HashMap<ProductType, PriorityQueue<Order>> buyOrdersMap = new HashMap<>();
    private final HashMap<ProductType, PriorityQueue<Order>> sellOrdersMap = new HashMap<>();

  
    private final Comparator<Order> buyComparator = Comparator
            .comparing((Order o) -> o.pricePerUnit).reversed()
            .thenComparingLong(o -> o.creationTick);

    private final Comparator<Order> sellComparator = Comparator
            .comparing((Order o) -> o.pricePerUnit)
            .thenComparingLong(o -> o.creationTick);

    private int currentGlobalTick = 0; 

    public void placeOrder(Economic_Entity issuer, ProductType type, BigDecimal price, BigDecimal amount, boolean buying) {
        
        if (amount == null || price == null || amount.signum() <= 0 || price.signum() <= 0) return;

        if (!buying && !(issuer instanceof Company)) {
            System.out.println("[Markt] Abgewiesen: Bürger dürfen keine Waren verkaufen!");
            return;
        }

        Order newOrder = new Order(
            issuer, 
            type, 
            buying ? Order.OrderType.BUY : Order.OrderType.SELL, 
            amount, 
            price, 
            this.currentGlobalTick
        );

        if (buying) {
            buyOrdersMap.putIfAbsent(type, new PriorityQueue<>(buyComparator));
            buyOrdersMap.get(type).add(newOrder);
        } else {
            sellOrdersMap.putIfAbsent(type, new PriorityQueue<>(sellComparator));
            sellOrdersMap.get(type).add(newOrder);
        }
    }

    public BigDecimal getCheapestPrice(ProductType type) {
        PriorityQueue<Order> queue = sellOrdersMap.get(type);
        if (queue == null || queue.isEmpty()) {
            return BigDecimal.valueOf(-1.0); 
        }
        return queue.peek().pricePerUnit;
    }

    public BigDecimal getHighestPrice(ProductType type) {
        PriorityQueue<Order> queue = sellOrdersMap.get(type);
        if (queue != null && !queue.isEmpty()) {
            BigDecimal highest = BigDecimal.ZERO;
            for (Order order : queue) {
                if (order.pricePerUnit.compareTo(highest) > 0) {
                    highest = order.pricePerUnit;
                }
            }
            return highest;
        }
        return BigDecimal.ZERO; 
    }

    /**
     * Immediate buy: attempt to buy up to desiredAmount from existing sell orders for type,
     * paying up to maxUnitPrice per unit. Executes trades immediately (no placing buy orders).
     * Returns amount actually purchased.
     */
    public BigDecimal immediateBuy(Economic_Entity buyer, ProductType type, BigDecimal desiredAmount, BigDecimal maxUnitPrice) {
        if (buyer == null || type == null || desiredAmount == null || desiredAmount.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }
        PriorityQueue<Order> sellQueue = sellOrdersMap.get(type);
        if (sellQueue == null || sellQueue.isEmpty()) return BigDecimal.ZERO;

        BigDecimal remainingToBuy = desiredAmount;
        BigDecimal totalBought = BigDecimal.ZERO;

        // Use a temporary list to re-add partially modified orders after iteration
        // But PriorityQueue doesn't support iteration+modification safely; we'll poll and re-add leftovers.
        PriorityQueue<Order> temp = new PriorityQueue<>(sellComparator);

        while (remainingToBuy.compareTo(BigDecimal.ZERO) > 0 && !sellQueue.isEmpty()) {
            Order sell = sellQueue.peek();
            if (sell.pricePerUnit.compareTo(maxUnitPrice) > 0) break; // sellers are too expensive

            // check seller still has inventory
            if (!sell.issuer.hasProductInInventory(type, sell.amount)) {
                // remove invalid order
                sellQueue.poll();
                continue;
            }

            BigDecimal sellerAvailable = sell.amount;
            // how much buyer can afford at this price
            BigDecimal buyerFunds = buyer.getBalance();
            if (buyerFunds.compareTo(BigDecimal.ZERO) <= 0) break;

            BigDecimal affordableByFunds = buyerFunds.divide(sell.pricePerUnit, 0, java.math.RoundingMode.FLOOR);
            if (affordableByFunds.compareTo(BigDecimal.ZERO) <= 0) break;

            BigDecimal tradeAmount = sellerAvailable.min(remainingToBuy).min(affordableByFunds);
            if (tradeAmount.compareTo(BigDecimal.ZERO) <= 0) break;

            BigDecimal totalCost = tradeAmount.multiply(sell.pricePerUnit);

            // Execute transfer
            buyer.changeBalance(totalCost.negate());
            sell.issuer.changeBalance(totalCost);
            sell.issuer.removeProduct(type, tradeAmount);
            buyer.addProduct(type, tradeAmount);

            // feedback callbacks
            BigDecimal cheapest = getCheapestPrice(type);
            buyer.registerBuy(type, tradeAmount, sell.pricePerUnit, cheapest);
            BigDecimal highest = getHighestPrice(type);
            sell.issuer.registerSale(type, tradeAmount, sell.pricePerUnit, highest);

            // update order amounts
            sell.amount = sell.amount.subtract(tradeAmount);
            sell.Amount_fullfilled = sell.Amount_fullfilled.add(tradeAmount);

            totalBought = totalBought.add(tradeAmount);
            remainingToBuy = remainingToBuy.subtract(tradeAmount);

            if (sell.amount.signum() == 0) {
                sellQueue.poll(); // remove completed order
            }
        }

        return totalBought;
    }

    /** existing tryConsumeInputsForProduction from earlier - unchanged */
    public Map<ProductType, BigDecimal> tryConsumeInputsForProduction(Economic_Entity producer,
            ProductionRecipe recipe, int outputUnits, double efficiency, double wasteRate) {

        // Basic validation
        if (producer == null || recipe == null || outputUnits <= 0) return null;
        // Only companies should produce (matches other Market rules)
        if (!(producer instanceof Company)) {
            System.out.println("[Market] Only companies can run production.");
            return null;
        }

        double eff = (efficiency <= 0.0) ? 0.0001 : Math.min(1.0, efficiency);
        double waste = Math.max(0.0, wasteRate);

        // Compute required amounts (integer counts) for each input type
        Map<ProductType, Integer> perUnit = recipe.getInputMaterials();
        Map<ProductType, BigDecimal> requiredAsBig = new LinkedHashMap<>();

        for (Map.Entry<ProductType, Integer> e : perUnit.entrySet()) {
            ProductType type = e.getKey();
            int perUnitAmount = e.getValue();
            if (perUnitAmount <= 0) continue;

            double baseNeeded = (double) perUnitAmount * (double) outputUnits;
            double adjusted = baseNeeded / eff;
            double adjustedWithWaste = adjusted * (1.0 + waste);
            int finalNeeded = (int) Math.ceil(adjustedWithWaste - 1e-9);

            if (finalNeeded < 0) finalNeeded = 0;
            BigDecimal neededBD = BigDecimal.valueOf(finalNeeded);
            requiredAsBig.put(type, neededBD);
        }

        // Check availability
        for (Map.Entry<ProductType, BigDecimal> req : requiredAsBig.entrySet()) {
            ProductType type = req.getKey();
            BigDecimal amountNeeded = req.getValue();
            if (!producer.hasProductInInventory(type, amountNeeded)) {
                // Not enough inputs -> abort
                System.out.println("[Market] Production aborted: " + producer.getName()
                        + " lacks " + type + " (needs " + amountNeeded + ").");
                return null;
            }
        }

        // All inputs available -> consume them
        for (Map.Entry<ProductType, BigDecimal> req : requiredAsBig.entrySet()) {
            producer.removeProduct(req.getKey(), req.getValue());
        }

        return requiredAsBig;
    }

    public void tick() {
        System.out.println("\n--- [MARKT TICK] Monat " + currentGlobalTick + " wird berechnet ---");
        
        for (ProductType type : sellOrdersMap.keySet()) {
            PriorityQueue<Order> buyQueue = buyOrdersMap.get(type);
            PriorityQueue<Order> sellQueue = sellOrdersMap.get(type);

            if (buyQueue == null || buyQueue.isEmpty() || sellQueue == null || sellQueue.isEmpty()) {
                continue;
            }

            while (!buyQueue.isEmpty() && !sellQueue.isEmpty()) {
                Order highestBuyer = buyQueue.peek();  
                Order lowestSeller = sellQueue.peek(); 

                if (highestBuyer.pricePerUnit.compareTo(lowestSeller.pricePerUnit) < 0) {
                    break; 
                }

                BigDecimal availableToBuy = highestBuyer.amount;
                BigDecimal availableToSell = lowestSeller.amount;
               
                BigDecimal tradeAmount = availableToBuy.min(availableToSell);

                BigDecimal finalPricePerUnit = (highestBuyer.creationTick <= lowestSeller.creationTick) 
                                        ? highestBuyer.pricePerUnit 
                                        : lowestSeller.pricePerUnit;
                
                BigDecimal totalCost = tradeAmount.multiply(finalPricePerUnit);

                
                if (highestBuyer.issuer.getBalance().compareTo(totalCost) < 0) {
                    System.out.println("[Markt] " + highestBuyer.issuer.getName() + " hat zu wenig Geld! Order gelöscht.");
                    buyQueue.poll(); 
                    continue;
                }
                if (!lowestSeller.issuer.hasProductInInventory(type, tradeAmount)) {
                    System.out.println("[Markt] " + lowestSeller.issuer.getName() + " hat die Ware nicht mehr! Order gelöscht.");
                    sellQueue.poll(); 
                    continue;
                }

                // --- Geld Transaktion ---
                highestBuyer.issuer.changeBalance(totalCost.negate());
                lowestSeller.issuer.changeBalance(totalCost);
                //--- WarenTransaktion
                lowestSeller.issuer.removeProduct(type, tradeAmount);
                highestBuyer.issuer.addProduct(type, tradeAmount); 

                // --- FEEDBACK AN KÄUFER & VERKÄUFER ---
                BigDecimal cheapestMarketPrice = getCheapestPrice(type);
                highestBuyer.issuer.registerBuy(type, tradeAmount, finalPricePerUnit, cheapestMarketPrice);
                
                BigDecimal highestMarketPrice = getHighestPrice(type);
                lowestSeller.issuer.registerSale(type, tradeAmount, finalPricePerUnit, highestMarketPrice);

                highestBuyer.amount = highestBuyer.amount.subtract(tradeAmount);
                lowestSeller.amount = lowestSeller.amount.subtract(tradeAmount);

                if (highestBuyer.amount.signum() == 0) buyQueue.poll();
                if (lowestSeller.amount.signum() == 0) sellQueue.poll();
            }
        }
        this.currentGlobalTick++;
    }
}
