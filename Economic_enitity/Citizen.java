package Economic_enitity;

import java.util.concurrent.ThreadLocalRandom;
import java.math.BigDecimal;

import Main.Education2;
import Market.ProductType;
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
    Company[] money_companies_owned = new Company[10]; 
    float[] money_companies_owned_share = new float[10];
    int age; 

    // --- NEW: Personal inventory using ProductType ---
    public final Map<ProductType, BigDecimal> personalInventory = new HashMap<>();

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

    // --- ORIGINAL REPRODUCTION LOGIC (SEX) ---
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