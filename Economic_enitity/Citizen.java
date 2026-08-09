package Economic_enitity;

import java.util.concurrent.ThreadLocalRandom;
import java.math.BigDecimal;
import java.math.RoundingMode;

import Main.Education2;
import Market.ProductType;
import Market.Market;
import job.Job;
import job.Jobskill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Citizen implements Economic_Entity {
    
    // --- SKILLS & ATTRIBUTES ---
    short skill_lesen_schreiben;
    short skill_führen;
    short skill_fitness;
    public short skill_kommunikation;
    short skill_technik_handwerk;
    short skill_kreativität_Innovation;
    public short skill_wirtschaftliches_denken;
    public short skill_Empathie;
    short skill_Reßoursenmanagement;
    short skill_logisches_denken;
	
    public Education2 qualification_education;
    byte experience_manufacturing;
    byte experience_farmer;
    byte experience_handyman;
    byte experience_engineer;
    byte experience_management;
    public byte experience_finance;
    public byte experience_education;
	
    public Job job_currentField;
    public boolean job_jobless;
    boolean gender_is_male;
	
    Citizen family_mom;
    Citizen family_dad;
    Citizen family_fiance;
    ArrayList<Citizen> family_children = new ArrayList<>();
    byte family_relation_to_family;
    byte family_love;
	
    // --- FINANCES & PERSONAL ---
    String id;
    public BigDecimal money_income = BigDecimal.ZERO;
    BigDecimal money_income_tax = BigDecimal.ZERO;
    private BigDecimal money_balance = BigDecimal.ZERO;
    private BigDecimal savings = BigDecimal.ZERO; // separate savings/investment pool
    Company[] money_companies_owned = new Company[10]; 
    float[] money_companies_owned_share = new float[10];
    int age; 

    // --- NEW: Personal inventory using ProductType ---
    public final Map<ProductType, BigDecimal> personalInventory = new HashMap<>();

    // Survival / wellbeing
    public int monthsWithoutFood = 0;
    public int happiness = 50; // 0..100
    public boolean alive = true;

    // Default constructor for birth (used by zeugeKind)
    public Citizen() {
        this.id = String.valueOf(System.nanoTime());
        this.job_jobless = true;
        this.age = 0;
    }

    // Constructor for starting citizens
    public Citizen(String id, BigDecimal startCapital, short age2) {
        this.id = id;
        this.setMoney_balance(startCapital);
        this.job_jobless = true;
        this.age = age2;
    }

    // --- REPRODUCTION LOGIC (unchanged) ---
    public static Citizen zeugeKind(Citizen vater, Citizen mutter, String nameDesKindes) {
        Citizen kind = new Citizen();
        kind.id = nameDesKindes;
        kind.family_dad = vater;
        kind.family_mom = mutter;
        vater.family_children.add(kind);
        mutter.family_children.add(kind);

        kind.skill_lesen_schreiben          = calculate_gene(vater.skill_lesen_schreiben, mutter.skill_lesen_schreiben);
        kind.skill_führen                   = calculate_gene(vater.skill_führen, mutter.skill_führen);
        kind.skill_fitness                  = calculate_gene(vater.skill_fitness, mutter.skill_fitness);
        kind.skill_kommunikation            = calculate_gene(vater.skill_kommunikation, mutter.skill_kommunikation);
        kind.skill_technik_handwerk         = calculate_gene(vater.skill_technik_handwerk, mutter.skill_technik_handwerk);
        kind.skill_kreativität_Innovation   = calculate_gene(vater.skill_kreativität_Innovation, mutter.skill_kreativität_Innovation);
        kind.skill_wirtschaftliches_denken  = calculate_gene(vater.skill_wirtschaftliches_denken, mutter.skill_wirtschaftliches_denken);
        kind.skill_Empathie                 = calculate_gene(vater.skill_Empathie, mutter.skill_Empathie);
        kind.skill_Reßoursenmanagement      = calculate_gene(vater.skill_Reßoursenmanagement, mutter.skill_Reßoursenmanagement);
        kind.skill_logisches_denken         = calculate_gene(vater.skill_logisches_denken, mutter.skill_logisches_denken);

        return kind;
    }

    public static short calculate_gene(short father, short mother) {
        short child = (short) ((father + mother) / 2);
        child += gaus_0_10();
        child -= 5;
        if (child > 100) return 100;
        if (child < 0) return 0; // Prevent negative skills
        return child;
    }

    public static int gaus_0_10() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int r1 = random.nextInt(5); // 0 to 4
        int r2 = random.nextInt(4); // 0 to 3
        int r3 = random.nextInt(4); // 0 to 3
        return r1 + r2 + r3;
    }

    // --- INTERFACE IMPLEMENTATION (ADAPTED TO PRODUCTTYPE & BIGDECIMAL) ---

    @Override
    public String getName() {
        return this.id; // Uses id field as name
    }

    @Override
    public BigDecimal getBalance() {
        return this.getMoney_balance();
    }

    @Override
    public void changeBalance(BigDecimal amount) {
        this.setMoney_balance(this.getMoney_balance().add(amount));
    }

    @Override
    public void addProduct(ProductType type, BigDecimal tradeAmount) {
        if (type == null || tradeAmount == null || tradeAmount.compareTo(BigDecimal.ZERO) <= 0) return;
        BigDecimal current = personalInventory.getOrDefault(type, BigDecimal.ZERO);
        personalInventory.put(type, current.add(tradeAmount));
    }

    @Override
    public void removeProduct(ProductType type, BigDecimal tradeAmount) {
        if (type == null || tradeAmount == null || tradeAmount.compareTo(BigDecimal.ZERO) <= 0) return;
        BigDecimal current = personalInventory.getOrDefault(type, BigDecimal.ZERO);
        if (tradeAmount.compareTo(current) >= 0) {
            personalInventory.remove(type);
        } else {
            personalInventory.put(type, current.subtract(tradeAmount));
        }
    }

    @Override
    public void registerBuy(ProductType type, BigDecimal tradeAmount, BigDecimal finalPricePerUnit, BigDecimal cheapestMarketPrice) {
        // Store buyer feedback from the market (important for evolutionary price adjustment)
        System.out.println("[Buyer Report] " + getName() + " bought " + tradeAmount + "x " + type + " at " + finalPricePerUnit + "€ each.");
    }

    @Override
    public void registerSale(ProductType type, BigDecimal tradeAmount, BigDecimal finalPrice, BigDecimal highestPrice) {
        // Citizens do not sell on the market themselves currently
    }

    // --- ORIGINAL GAME METHODS ---

    public void money_add_balance(BigDecimal d) {
        if (d != null) {
            this.setMoney_balance(this.getMoney_balance().add(d)); 
        }
    }
    
    public void setMoney_balance(BigDecimal i)  {
        this.money_balance = (i != null) ? i : BigDecimal.ZERO;
    }
    
    public void money_add_Company_ownership(Company company, byte share_amount_percentage) {
        for (int i = 0; i < this.money_companies_owned.length; i++) {
            if (this.money_companies_owned[i] == null) {
                this.money_companies_owned[i] = company;
                this.money_companies_owned_share[i] = share_amount_percentage;
                return; 
            }
        }
        System.out.println("No free slot for company shares found.");
    }

    public void Update() {
        this.age++;
        this.money_add_balance(money_income);
    }

    public void calculate_tax() {
        BigDecimal income = this.money_income;
        
        BigDecimal limit1000 = BigDecimal.valueOf(1000);
        BigDecimal limit2500 = BigDecimal.valueOf(2500);
        BigDecimal limit5000 = BigDecimal.valueOf(5000);
        BigDecimal limit20000 = BigDecimal.valueOf(20000);

        if (income.compareTo(limit1000) <= 0) {
            this.money_income_tax = BigDecimal.ZERO;
        } else if (income.compareTo(limit2500) <= 0) {
            this.money_income_tax = income.subtract(limit1000).multiply(BigDecimal.valueOf(0.2));
        } else if (income.compareTo(limit5000) <= 0) {
            BigDecimal taxStep1 = limit2500.subtract(limit1000).multiply(BigDecimal.valueOf(0.2));
            BigDecimal taxStep2 = income.subtract(limit2500).multiply(BigDecimal.valueOf(0.3));
            this.money_income_tax = taxStep1.add(taxStep2);
        } else if (income.compareTo(limit20000) <= 0) {
            BigDecimal taxStep1 = limit2500.subtract(limit1000).multiply(BigDecimal.valueOf(0.2));
            BigDecimal taxStep2 = limit5000.subtract(limit2500).multiply(BigDecimal.valueOf(0.3));
            BigDecimal taxStep3 = income.subtract(limit5000).multiply(BigDecimal.valueOf(0.4));
            this.money_income_tax = taxStep1.add(taxStep2).add(taxStep3);
        } else {
            BigDecimal taxStep1 = limit2500.subtract(limit1000).multiply(BigDecimal.valueOf(0.2));
            BigDecimal taxStep2 = limit5000.subtract(limit2500).multiply(BigDecimal.valueOf(0.3));
            BigDecimal taxStep3 = limit20000.subtract(limit5000).multiply(BigDecimal.valueOf(0.4));
            BigDecimal taxStep4 = income.subtract(limit20000).multiply(BigDecimal.valueOf(0.5));
            this.money_income_tax = taxStep1.add(taxStep2).add(taxStep3).add(taxStep4);
        }
    }

    public void money_search_for_job() {}
    public void Searchforjob() {}

    @Override
    public boolean hasProductInInventory(ProductType type, BigDecimal tradeAmount) {
        if (tradeAmount == null) return false;
        return personalInventory.getOrDefault(type, BigDecimal.ZERO).compareTo(tradeAmount) >= 0;
    }

    /**
     * Decision and consumption function that determines whether a citizen consumes, buys, invests or saves.
     * - Considers financial state, genetic/economic skill (skill_wirtschaftliches_denken), experience_finance and happiness.
     * - Tries to satisfy essential needs first (food, water, hygiene, medicine). If essentials cannot be met for
     *   a configurable number of months the citizen dies (alive=false).
     * - Places buy orders on the market for shortfalls up to what the citizen can afford at the cheapest market price.
     * - Allocates a fraction of remaining balance to savings/investment depending on propensity determined by skills and happiness.
     * - Adjusts happiness up/down (boni/mali) based on whether needs were met.
     *
     * @param market Market instance used to read prices and place buy orders (may be null, in which case no market purchases happen)
     * @return true if the citizen is still alive after the decision, false if starved to death
     */
    public boolean decideConsumeOrInvest(Market market) {
        if (!alive) return false;

        // Essential needs profile (monthly). Values can be tuned or moved to config.
        Map<ProductType, BigDecimal> essentials = new HashMap<>();
        essentials.put(ProductType.Water, BigDecimal.valueOf(10));
        essentials.put(ProductType.Wheat, BigDecimal.valueOf(3));
        essentials.put(ProductType.Milk, BigDecimal.valueOf(1));
        essentials.put(ProductType.Eggs, BigDecimal.valueOf(4));
        essentials.put(ProductType.Soap, BigDecimal.valueOf(0.5));
        essentials.put(ProductType.Medicine, BigDecimal.valueOf(0.1));
        essentials.put(ProductType.Clothing, BigDecimal.valueOf(0.02)); // durable, rarely consumed

        // Parameters
        final int MONTHS_TO_STARVE = 2;
        final BigDecimal MIN_BALANCE_BUFFER = BigDecimal.valueOf(5); // keep a tiny buffer

        // Compute disposable funds this month (balance + income) but leave a buffer
        BigDecimal disposable = getMoney_balance().add(money_income).subtract(MIN_BALANCE_BUFFER);
        if (disposable.compareTo(BigDecimal.ZERO) < 0) disposable = BigDecimal.ZERO;

        // Determine investment propensity from economics skill, finance experience and happiness
        double skillFactor = (this.skill_wirtschaftliches_denken / 100.0); // 0..1
        double financeExpFactor = (this.experience_finance / 10.0); // scaled
        double happinessFactor = ((this.happiness - 50) / 100.0); // -0.5 .. +0.5

        double basePropensity = 0.05; // base fraction to invest/save
        double propensity = basePropensity + skillFactor * 0.25 + financeExpFactor * 0.1 + happinessFactor * 0.05;
        // clamp
        if (propensity < 0.0) propensity = 0.0;
        if (propensity > 0.8) propensity = 0.8;

        // Track whether critical food needs met
        boolean criticalShortfall = false;

        // 1) Satisfy essentials
        for (Map.Entry<ProductType, BigDecimal> need : essentials.entrySet()) {
            ProductType type = need.getKey();
            BigDecimal required = need.getValue();
            if (required.compareTo(BigDecimal.ZERO) <= 0) continue;

            BigDecimal have = personalInventory.getOrDefault(type, BigDecimal.ZERO);
            BigDecimal toConsume = have.min(required);

            if (toConsume.compareTo(BigDecimal.ZERO) > 0) {
                removeProduct(type, toConsume);
                required = required.subtract(toConsume);
                // small happiness bonus for consuming
                this.happiness = Math.min(100, this.happiness + 1);
            }

            if (required.compareTo(BigDecimal.ZERO) > 0) {
                // Need to buy some amount
                if (market != null) {
                    BigDecimal cheapest = market.getCheapestPrice(type);
                    if (cheapest != null && cheapest.compareTo(BigDecimal.ZERO) > 0) {
                        // affordable units
                        BigDecimal affordable = BigDecimal.ZERO;
                        if (disposable.compareTo(cheapest) >= 0) {
                            affordable = disposable.divide(cheapest, 0, RoundingMode.FLOOR);
                        }
                        BigDecimal toBuy = affordable.min(required);
                        if (toBuy.compareTo(BigDecimal.ZERO) > 0) {
                            market.placeOrder(this, type, cheapest, toBuy, true);
                            // reserve money for purchase (optimistic; actual deduction happens on match)
                            BigDecimal reserved = toBuy.multiply(cheapest);
                            disposable = disposable.subtract(reserved);
                            // count as expected consumption
                            required = required.subtract(toBuy);
                            // planned consumption gives small happiness
                            this.happiness = Math.min(100, this.happiness + 1);
                        }
                    }
                }
            }

            // If still short after attempting to buy
            if (required.compareTo(BigDecimal.ZERO) > 0) {
                // apply malus depending on type (food/water more severe)
                if (type == ProductType.Water || type == ProductType.Wheat || type == ProductType.Milk || type == ProductType.Eggs || type == ProductType.Beef || type == ProductType.Pork || type == ProductType.Chicken_Meat) {
                    // critical food shortfall
                    this.happiness = Math.max(0, this.happiness - 20);
                    this.monthsWithoutFood++;
                    criticalShortfall = true;
                } else {
                    // less critical
                    this.happiness = Math.max(0, this.happiness - 5);
                }
            }
        }

        // If critical shortfalls persisted for too long, citizen dies
        if (this.monthsWithoutFood >= MONTHS_TO_STARVE) {
            this.alive = false;
            System.out.println("[Citizen] " + getName() + " has starved to death.");
            return false;
        }

        // Successful month resets starvation counter
        if (!criticalShortfall) this.monthsWithoutFood = 0;

        // 2) After essentials satisfied (or attempted), allocate remaining disposable to savings/investment or discretionary consumption
        // Recompute disposable from current balance (note some money may be reserved by placed buy orders but not deducted yet)
        BigDecimal currentDisposable = getMoney_balance().add(money_income).subtract(MIN_BALANCE_BUFFER);
        if (currentDisposable.compareTo(BigDecimal.ZERO) < 0) currentDisposable = BigDecimal.ZERO;

        BigDecimal investAmount = BigDecimal.valueOf(propensity).multiply(currentDisposable).setScale(2, RoundingMode.FLOOR);
        if (investAmount.compareTo(BigDecimal.ZERO) > 0) {
            // Move to savings (representing investment)
            this.savings = this.savings.add(investAmount);
            this.changeBalance(investAmount.negate());
            // small happiness bonus for investing successfully (if they had surplus)
            this.happiness = Math.min(100, this.happiness + 2);
        }

        // 3) Discretionary consumption: small fraction of leftover
        BigDecimal leftover = getMoney_balance().subtract(MIN_BALANCE_BUFFER);
        if (leftover.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal discretionary = leftover.multiply(BigDecimal.valueOf(0.1)).setScale(2, RoundingMode.FLOOR);
            if (discretionary.compareTo(BigDecimal.ZERO) > 0) {
                // Use discretionary to buy random non-essential goods to increase happiness
                ProductType[] luxuries = new ProductType[] { ProductType.Soap, ProductType.Clothing, ProductType.Entertainment, ProductType.Soap };
                ProductType pick = luxuries[ThreadLocalRandom.current().nextInt(luxuries.length)];
                BigDecimal price = (market != null) ? market.getCheapestPrice(pick) : BigDecimal.ZERO;
                if (price != null && price.compareTo(BigDecimal.ZERO) > 0) {
                    BigDecimal qty = discretionary.divide(price, 0, RoundingMode.FLOOR);
                    if (qty.compareTo(BigDecimal.ZERO) > 0) {
                        market.placeOrder(this, pick, price, qty, true);
                        this.changeBalance(price.multiply(qty).negate()); // assume immediate purchase for simplicity
                        // increase happiness for luxury
                        this.happiness = Math.min(100, this.happiness + 3);
                    }
                }
            }
        }

        // Clamp happiness
        if (this.happiness < 0) this.happiness = 0;
        if (this.happiness > 100) this.happiness = 100;

        return true;
    }

    // Getter for private variable money_balance
    public BigDecimal getMoney_balance() {
        return money_balance;
    }

    public short  getSkillValue(Jobskill targetSkill) {
		if(targetSkill==null) {
			return 0;
		}
		switch(targetSkill) {
			case lesen_schreiben:
				return this.skill_lesen_schreiben;
			case führen:
				return this.skill_führen;
			case fitness:
				return this.skill_fitness;
			case kommunikation:
				return this.skill_kommunikation;
			case kreativität_innovation:
				return this.skill_kreativität_Innovation;
			case wirtschaftliches_denken:
				return this.skill_wirtschaftliches_denken;
			case Empathie:
				return this.skill_Empathie;
			case Reßoursenmanagement:
				return this.skill_Reßoursenmanagement;
			case technik_handwerk:
				return this.skill_technik_handwerk;
			case logisches_denken:
				return this.skill_logisches_denken;
			
		}
		return 0;
	}


	public short getExperienceValue(Job experience) {
	    if (experience == null) {
	        return 0;
	    }
	
	    switch (experience) {
	        case manufacturing:
	            return this.experience_manufacturing;
	        case farmer:
	            return this.experience_farmer;
	        case handyman:
	            return this.experience_handyman;
	        case engineer:
	            return this.experience_engineer;
	        case management:
	            return this.experience_management;
	        case finance:
	            return this.experience_finance;
	        case education:
	            return this.experience_education;
	        case unemployed:
	        default:
	            return 0;
	    }
	

}
}
