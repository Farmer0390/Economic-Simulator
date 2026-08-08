package Economic_enitity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Market.Market;
import Market.ProductType;

public class Company implements Economic_Entity {
    private static final int HISTORY_MONTHS = 12;

    private static final BigDecimal WEIGHT_CURRENT_MONTH = new BigDecimal("0.7");
    private static final BigDecimal WEIGHT_AVERAGE = new BigDecimal("0.3");

    private final String companyName;
    private BigDecimal balance; 
    private BigDecimal income = BigDecimal.ZERO;
    private BigDecimal tax = BigDecimal.ZERO;
    private BigDecimal profit = BigDecimal.ZERO;
    
  
    private final Map<ProductType, BigDecimal> inventory = new HashMap<>();
    private final ProductType inputProduct;  
    private final ProductType outputProduct; 
    
    private final List<Citizen> employees = new ArrayList<>();
    private final List<Citizen> owners = new ArrayList<>();

    
    private final BigDecimal[] salesHistory = new BigDecimal[HISTORY_MONTHS]; 
    private int historyIndex = 0;
    private BigDecimal soldThisMonth = BigDecimal.ZERO;            

    private BigDecimal currentMarketPrice = BigDecimal.ZERO;
    private BigDecimal productionCostPerUnit = BigDecimal.ZERO;
    private BigDecimal maxProductionCapacity = BigDecimal.ZERO; 
    private BigDecimal optimism = BigDecimal.ONE;               
    private BigDecimal desiredInventory = BigDecimal.ZERO;       

    public Company(Citizen founder, BigDecimal deposit, ProductType input, ProductType output) {
        this.employees.add(founder);
        this.owners.add(founder);
        this.balance = deposit;
        this.companyName = "Modern Factory";
        this.inputProduct = input;
        this.outputProduct = output;
        
        
        for (int i = 0; i < HISTORY_MONTHS; i++) {
            salesHistory[i] = BigDecimal.ZERO;
        }
    }

    public void executePlanningAndProduction(Market market) {
        BigDecimal price = market.getCheapestPrice(outputProduct); // Assume Market returns BigDecimal
        if (price.compareTo(BigDecimal.ZERO) > 0) {
            this.currentMarketPrice = price;
        }

        BigDecimal sum = BigDecimal.ZERO;
        for (BigDecimal sales : salesHistory) {
            sum = sum.add(sales);
        }
        
        BigDecimal averageSales = sum.divide(BigDecimal.valueOf(HISTORY_MONTHS), RoundingMode.HALF_UP);
        
        // expectedDemand = (WEIGHT_CURRENT_MONTH * current_sales + WEIGHT_AVERAGE * averageSales) * optimism
        BigDecimal currentMonthSales = salesHistory[historyIndex];
        BigDecimal expectedDemand = WEIGHT_CURRENT_MONTH.multiply(currentMonthSales)
                .add(WEIGHT_AVERAGE.multiply(averageSales))
                .multiply(optimism);

        BigDecimal currentStock = getProductCount(outputProduct);
        
        // productionNeeded = max(0, (expectedDemand + desiredInventory) - currentStock)
        BigDecimal productionNeeded = expectedDemand.add(desiredInventory).subtract(currentStock);
        if (productionNeeded.compareTo(BigDecimal.ZERO) < 0) {
            productionNeeded = BigDecimal.ZERO;
        }

        // Profitability check: currentMarketPrice - productionCostPerUnit <= 0
        if (currentMarketPrice.subtract(productionCostPerUnit).compareTo(BigDecimal.ZERO) <= 0) {
            return;
        }

        // production = min(productionNeeded, maxProductionCapacity)
        BigDecimal production = productionNeeded.min(maxProductionCapacity);
        
        // affordableProduction = balance / productionCostPerUnit
        BigDecimal affordableProduction = BigDecimal.ZERO;
        if (productionCostPerUnit.compareTo(BigDecimal.ZERO) > 0) {
            affordableProduction = this.balance.divide(productionCostPerUnit, RoundingMode.FLOOR); // Floor for whole units or portions
        }
        
        // production = min(production, affordableProduction)
        production = production.min(affordableProduction);

        // production = min(production, availableRaw)
        BigDecimal availableRaw = getProductCount(inputProduct);
        production = production.min(availableRaw);

        if (production.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal totalCost = production.multiply(productionCostPerUnit);
            this.changeBalance(totalCost.negate());
            removeProduct(inputProduct, production); 
            addProduct(outputProduct, production);   
        }

        BigDecimal stockToSell = getProductCount(outputProduct);
        if (stockToSell.compareTo(BigDecimal.ZERO) > 0) {
           
            market.placeOrder(this, outputProduct, currentMarketPrice, stockToSell, false);
        }
    }

    public void endOfMonthUpdate() {
        salesHistory[historyIndex] = soldThisMonth;
        historyIndex = (historyIndex + 1) % HISTORY_MONTHS;
        this.soldThisMonth = BigDecimal.ZERO;
    }

    public BigDecimal getProductCount(ProductType type) {
        return inventory.getOrDefault(type, BigDecimal.ZERO);
    }

    // --- INTERFACE IMPLEMENTATION USING PRODUCTTYPE ---

    @Override
    public void addProduct(ProductType type, BigDecimal tradeAmount) {
        if (type == null || tradeAmount == null || tradeAmount.compareTo(BigDecimal.ZERO) <= 0) return;
        inventory.put(type, getProductCount(type).add(tradeAmount));
    }

    @Override
    public void removeProduct(ProductType type, BigDecimal tradeAmount) {
        if (type == null || tradeAmount == null || tradeAmount.compareTo(BigDecimal.ZERO) <= 0) return;
        BigDecimal current = getProductCount(type);
        if (tradeAmount.compareTo(current) >= 0) {
            inventory.remove(type); 
        } else {
            inventory.put(type, current.subtract(tradeAmount));
        }
    }

    @Override
    public boolean hasProductInInventory(ProductType type, BigDecimal tradeAmount) {
        return getProductCount(type).compareTo(tradeAmount) >= 0;
    }

    @Override
    public void registerSale(ProductType type, BigDecimal tradeAmount, BigDecimal finalPrice, BigDecimal highestPrice) {
        if (tradeAmount == null || tradeAmount.compareTo(BigDecimal.ZERO) <= 0) return;
        this.soldThisMonth = this.soldThisMonth.add(tradeAmount);
        this.income = this.income.add(tradeAmount.multiply(finalPrice));
    }

    @Override
    public void registerBuy(ProductType type, BigDecimal tradeAmount, BigDecimal finalPrice, BigDecimal cheapestPrice) {
        // Feedback for raw material calculation
    }

    @Override
    public String getName() { return this.companyName; }
    
    @Override
    public BigDecimal getBalance() { return this.balance; } 
    
    @Override
    public void changeBalance(BigDecimal amount) { 
        if (amount != null) {
            this.balance = this.balance.add(amount); 
        }
    }

    public void hireEmployee(Citizen citizen) {
        // TODO: add employee to staff and handle payroll
    }
}