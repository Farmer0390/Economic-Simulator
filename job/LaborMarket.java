package job;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import Economic_enitity.Citizen;
import Economic_enitity.Company;
import Economic_enitity.Economic_Entity;
import Main.Education2;

public class LaborMarket {
    // List of all currently open job offers on the market
    private final List<Joboffer> activeOffers = new ArrayList<>();

    /**
     * An employer (company, state or bank) posts an open position.
     */
    public void postJob(Economic_Entity issuer, BigDecimal salary, Education2 requiredEducation, Job experience, Jobskill targetSkill, BigDecimal workTimeFactor) {
        activeOffers.add(new Joboffer(issuer, salary, requiredEducation, experience, targetSkill, workTimeFactor));
    }

    /**
     * The labor market iterates over citizens, filters by education
     * and matches the unemployed based on talent, experience and soft skills.
     */
    public void processLaborMarket(List<Citizen> allCitizens) {
        for (Citizen citizen : allCitizens) {
            // Only citizens who are actually unemployed look for a job
            if (!citizen.job_jobless) continue; 

            Joboffer bestOffer = null;
            // Initialized with -1 as BigDecimal for first comparison
            BigDecimal highestEvaluation = new BigDecimal("-1.0");

            for (Joboffer offer : activeOffers) {
                // 1. EDUCATION FILTER: Does the citizen meet the required education level?
                if (citizen.qualification_education.ordinal() < offer.requiredEducation.ordinal()) {
                    continue; // Rejected: education too low
                }

                // --- SOFT SKILLS INTEGRATION (Empathy & Communication) ---
                BigDecimal softSkillBonus = BigDecimal.ZERO;

                // Convert citizen's int/double values to BigDecimal for calculation
                BigDecimal empathy = BigDecimal.valueOf(citizen.skill_Empathie);
                BigDecimal communication = BigDecimal.valueOf(citizen.skill_kommunikation);

                switch (offer.experience) {
                    case education:
                        // Teachers/trainers need high empathy and rhetoric
                        softSkillBonus = empathy.multiply(new BigDecimal("1.5"))
                                .add(communication.multiply(new BigDecimal("1.5")));
                        break;
                        
                    case management:
                        // Managers steer teams -> communication very important, empathy secondary
                        softSkillBonus = communication.multiply(new BigDecimal("2.0"))
                                .add(empathy.multiply(new BigDecimal("0.5")));
                        break;
                        
                    case finance:
                        // Advisors need communication skills for client work
                        softSkillBonus = communication.multiply(new BigDecimal("1.0"));
                        break;
                        
                    case manufacturing:
                    case engineer:
                    case handyman:
                    case farmer:
                        // Production, logic and craft need almost no social soft skills
                        softSkillBonus = communication.multiply(new BigDecimal("0.1"));
                        break;
                        
                    default:
                        break;
                }

                // --- THE EVOLUTIONARY UTILITY FUNCTION (The AI decides) ---
                // 1. Salary component (influenced by economic thinking gene)
                // Calculation: 1.0 + (citizen.skill_wirtschaftliches_denken / 100.0)
                BigDecimal economicFocus = BigDecimal.ONE.add(
                        BigDecimal.valueOf(citizen.skill_wirtschaftliches_denken)
                                .divide(new BigDecimal("100.0"), 4, RoundingMode.HALF_UP)
                );
                
                // Consideration for full-time (1.0) or part-time (0.5)
                BigDecimal effectiveSalary = offer.offered_salary.multiply(offer.workTimeFactor);
                BigDecimal salaryValue = effectiveSalary.multiply(economicFocus);

                // 2. Talent component (converted from int to BigDecimal)
                BigDecimal talentScore = BigDecimal.valueOf(citizen.getSkillValue(offer.targetSkill));

                // 3. Experience component (converted from short to BigDecimal)
                BigDecimal experienceScore = BigDecimal.valueOf(citizen.getExperienceValue(offer.experience));

                // Total value: salary + (talent * 0.5) + (experience * 0.5) + soft skills
                BigDecimal talentBonus = talentScore.multiply(new BigDecimal("0.5"));
                BigDecimal experienceBonus = experienceScore.multiply(new BigDecimal("0.5"));

                BigDecimal evaluation = salaryValue
                        .add(talentBonus)
                        .add(experienceBonus)
                        .add(softSkillBonus);

                // If this job is more attractive for this profile -> remember it
                if (evaluation.compareTo(highestEvaluation) > 0) {
                    highestEvaluation = evaluation;
                    bestOffer = offer;
                }
            }

            // --- JOB ASSIGNMENT (The final hiring) ---
            if (bestOffer != null) {
                // Configure citizen data for the new employment
                citizen.job_jobless = false;
                citizen.money_income = bestOffer.offered_salary;
                citizen.job_currentField = bestOffer.experience; // The enum from Citizen
                
                // Inform the employer: add employee to staff
                if (bestOffer.issuer instanceof Company) {
                    ((Company) bestOffer.issuer).hireEmployee(citizen);
                }
                
                // Position is filled, remove the offer from the market
                activeOffers.remove(bestOffer);
            }
        }
    }
}
