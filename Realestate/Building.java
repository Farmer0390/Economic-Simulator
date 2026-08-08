package Realestate;

public class Building {
    private final BuildingType type;
    private final double initialBuildCost;
    private final double footprintArea;
    
    private double currentMarketValue;
    private double condition; // 1.0 = perfect, 0.0 = ruin
    private int remainingConstructionTime; // in months/ticks. 0 = active.
    private Property parentProperty;

    public Building(BuildingType type, double cost, double footprint, int buildTime) {
        this.type = type;
        this.initialBuildCost = cost;
        this.footprintArea = footprint;
        this.condition = 1.0;
        this.remainingConstructionTime = buildTime;
        this.currentMarketValue = cost;
    }

    public void setParentProperty(Property property) {
        this.parentProperty = property;
    }

    public void performMonthlyTick() {
        if (remainingConstructionTime > 0) {
            remainingConstructionTime--;
            return;
        }

        // Natural aging process
        this.condition = Math.max(0.0, this.condition - 0.005);
        this.currentMarketValue = this.initialBuildCost * this.condition;
    }

    public void applyRepair(double conditionBonus) {
        this.condition = Math.min(1.0, this.condition + conditionBonus);
        this.currentMarketValue = this.initialBuildCost * this.condition;
    }

    public int getMachineCapacityOutput() {
        if (remainingConstructionTime > 0 || this.condition <= 0.2) {
            return 0; 
        }

        switch (this.type) {
            case FACTORY:      return (int) (footprintArea * 10 * this.condition);
            case STEELWORKS:   return (int) (footprintArea * 25 * this.condition);
            case POWER_PLANT:  return (int) (footprintArea * 50 * this.condition);
            case FARM:         return (int) (footprintArea * 5 * this.condition);
            case STALL:        return (int) (footprintArea * 8 * this.condition);
            case MINE:         return (int) (footprintArea * 15 * this.condition);
            default:           return (int) (footprintArea * 2 * this.condition);
        }
    }

    // --- GETTERS ---
    public BuildingType getType() { return type; }
    public double getFootprintArea() { return footprintArea; }
    public double getCurrentMarketValue() { return currentMarketValue; }
    public double getCondition() { return condition; }
    public boolean isActive() { return remainingConstructionTime == 0; }
    public Property getParentProperty() { return parentProperty; }
}