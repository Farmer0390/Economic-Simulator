package job;

import java.math.BigDecimal;

import Economic_enitity.Economic_Entity;
import Main.Education2;

public class Joboffer {
    // --- INSTANCE VARIABLES ---
    public final Economic_Entity issuer;       // The employer (company, state, etc.)
    public final BigDecimal offered_salary;        // The monthly net salary offered
    public final Education2 requiredEducation; // Minimum education level (market filter)
    
    // --- INDUSTRY, SKILL AND TIME VARIABLES ---
    public final Job industryField;        // Sector: "MANUFACTURING", "FARMER", etc.
    public final Jobskill targetSkill;        // Uses the Jobskill enum for talent matching
    public final BigDecimal workTimeFactor;       // Work time: 1.0 = full-time, 0.5 = part-time
    public Job experience;

    /**
     * Constructor to create a job offer.
     * Allows employers to specify exact requirements for the labor market.
     */
    public Joboffer(Economic_Entity issuer, BigDecimal offered_salary, Education2 requiredEducation, Job industryField, Jobskill targetSkill, BigDecimal workTimeFactor) {
        this.issuer = issuer;
        this.offered_salary = offered_salary;
        this.requiredEducation = requiredEducation;
        this.industryField = industryField;
        this.targetSkill = targetSkill;
        this.workTimeFactor = workTimeFactor;
    }

    
    
}