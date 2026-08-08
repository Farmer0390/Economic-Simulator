package Main;

import java.math.BigDecimal;
import java.util.ArrayList;
import Economic_enitity.Citizen;

public class State {
    // 1. Instance variables converted to BigDecimal
    BigDecimal balance = BigDecimal.ZERO;
    BigDecimal income_per_month = BigDecimal.ZERO;
    
    // Arrays also use the new data type
    BigDecimal [] income_per_month_history; 
    ArrayList<Citizen> citizens = new ArrayList<Citizen>();

    public void pay_tax() {
        for (Citizen citizen : citizens) {
            
            // Get BigDecimal income value from Citizen and handle safely
            BigDecimal income = citizen.money_income;
            BigDecimal tax = BigDecimal.ZERO;

            // 2. Tax brackets using BigDecimal comparisons and arithmetic
            // income.compareTo(X) <= 0 means: income <= X
            if (income.compareTo(BigDecimal.valueOf(1000)) <= 0) {
                tax = BigDecimal.ZERO;
            } else if (income.compareTo(BigDecimal.valueOf(2500)) <= 0) {
                tax = income.subtract(BigDecimal.valueOf(1000)).multiply(BigDecimal.valueOf(0.20));
            } else if (income.compareTo(BigDecimal.valueOf(5000)) <= 0) {
                tax = BigDecimal.valueOf(300).add(income.subtract(BigDecimal.valueOf(2500)).multiply(BigDecimal.valueOf(0.30)));
            } else if (income.compareTo(BigDecimal.valueOf(20000)) <= 0) {
                tax = BigDecimal.valueOf(1050).add(income.subtract(BigDecimal.valueOf(5000)).multiply(BigDecimal.valueOf(0.40)));
            } else {
                tax = BigDecimal.valueOf(7050).add(income.subtract(BigDecimal.valueOf(20000)).multiply(BigDecimal.valueOf(0.50)));
            }

            // 4. Pay tax to the state (add using .add())
            this.balance = this.balance.add(tax);
            this.income_per_month = this.income_per_month.add(tax);
            
            // Adjust the Citizen balance (Citizen should ideally also use BigDecimal)
         // Assumes getMoney_balance() and setMoney_balance() use BigDecimal
            citizen.setMoney_balance(citizen.getMoney_balance().subtract(tax));


        }
    }
    
    public void tick() {
        // Reset to BigDecimal.ZERO instead of 0
        income_per_month = BigDecimal.ZERO; 
    }
}