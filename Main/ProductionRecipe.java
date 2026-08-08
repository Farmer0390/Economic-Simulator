package Main;

import java.util.HashMap;
import java.util.Map;

import Market.ProductType;
import Realestate.BuildingType;

public class ProductionRecipe {
    
   
    private final Map<ProductType, Integer> inputMaterials = new HashMap<>();
    
   
    private final double requiredWorkTimeHours;
    
 
    private final int requiredMachineCapacity;
    
    
    private final BuildingType requiredBuildingType;

  
    public ProductionRecipe(double requiredWorkTimeHours, int requiredMachineCapacity, BuildingType requiredBuildingType) {
        this.requiredWorkTimeHours = requiredWorkTimeHours;
        this.requiredMachineCapacity = requiredMachineCapacity;
        this.requiredBuildingType = requiredBuildingType;
    }

  
    public void addIngredient(ProductType type, int amount) {
        if (type == null || amount <= 0) return;
        this.inputMaterials.put(type, amount);
    }

    // --- GETTERS ---
    
    public Map<ProductType, Integer> getInputMaterials() {
        return this.inputMaterials;
    }

    public double getRequiredWorkTimeHours() {
        return this.requiredWorkTimeHours;
    }

    public int getRequiredMachineCapacity() {
        return this.requiredMachineCapacity;
    }

    public BuildingType getRequiredBuildingType() {
        return this.requiredBuildingType;
    }
    
   
    public boolean isHandwork() {
        return this.requiredBuildingType == null;
    }
}