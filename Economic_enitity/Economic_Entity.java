package Economic_enitity;

import Market.ProductType;
import java.math.BigDecimal;

public interface Economic_Entity {
    String getName();
    BigDecimal getBalance();
    void changeBalance(BigDecimal amount);

    // Now unified with ProductType instead of the old Product class
    void addProduct(ProductType type, BigDecimal tradeAmount);
    void removeProduct(ProductType type, BigDecimal tradeAmount);
    boolean hasProductInInventory(ProductType type, BigDecimal tradeAmount);
    
    // Feedback methods called by the market for buyers and sellers
    void registerBuy(ProductType type, BigDecimal tradeAmount, BigDecimal finalPrice, BigDecimal cheapestPrice);
    void registerSale(ProductType type, BigDecimal tradeAmount, BigDecimal finalPrice, BigDecimal highestPrice);
}