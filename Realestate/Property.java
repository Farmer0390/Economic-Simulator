package Realestate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Economic_enitity.Economic_Entity;
import Market.ProductType;

public class Property {

    private final String id;
    private Economic_Entity owner; // The company or citizen who owns the land
    
    private final double sizeSquareMeters;
    private final double buildableAreaSquareMeters;
    private double currentMarketValue;
    private final double baseLandValuePerM2;
    
    private final List<Building> buildings = new ArrayList<>();
    
    // Resource deposits directly in the soil of this property
    private final Map<ProductType, Integer> resourceStorage = new HashMap<>();
    
    private final double coordinateX;
    private final double coordinateY;

    /**
     * Main constructor for the physical property.
     * Uses double for centimeter-accurate positioning and distance logistics.
     */
    public Property(String id, Economic_Entity owner, double size, double buildableArea, double baseValue, double x, double y) {
        this.id = id;
        this.owner = owner;
        this.sizeSquareMeters = size;
        this.buildableAreaSquareMeters = buildableArea;
        this.baseLandValuePerM2 = baseValue;
        this.coordinateX = x;
        this.coordinateY = y;
        recalculateMarketValue();
    }

    public double getCoordinateX() { return coordinateX; }
    public double getCoordinateY() { return coordinateY; }

    /**
     * Calculates the straight-line (Euclidean) distance to another property.
     * Important for phase 15 (logistics costs in market matching).
     */
    public double calculateDistanceTo(Property other) {
        if (other == null) return 0.0;
        double deltaX = this.coordinateX - other.getCoordinateX();
        double deltaY = this.coordinateY - other.getCoordinateY();
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }

    // Stores ores, coal or water in the soil
    public void depositResourceInSoil(ProductType type, int amount) {
        if (type == null || amount <= 0) return;
        this.resourceStorage.put(type, amount);
    }

    // Extracts resources from the soil (for mines / pumps / farms)
    public int extractResourceFromSoil(ProductType type, int requestedAmount) {
        int available = resourceStorage.getOrDefault(type, 0);
        if (available <= 0) return 0;

        int realExtracted = Math.min(requestedAmount, available);
        int remaining = available - realExtracted;

        if (remaining <= 0) {
            resourceStorage.remove(type);
        } else {
            resourceStorage.put(type, remaining);
        }
        recalculateMarketValue();
        return realExtracted;
    }

    public void recalculateMarketValue() {
        double landValue = this.sizeSquareMeters * this.baseLandValuePerM2;
        double buildingValueSum = 0;
        for (Building b : buildings) {
            buildingValueSum += b.getCurrentMarketValue();
        }
        double resourceValueBonus = 0;
        for (int amount : resourceStorage.values()) {
            resourceValueBonus += (amount * 2.0); 
        }
        this.currentMarketValue = landValue + buildingValueSum + resourceValueBonus;
    }

    public double getFreeCalculatedArea() {
        double usedArea = 0;
        for (Building b : buildings) {
            usedArea += b.getFootprintArea();
        }
        return this.buildableAreaSquareMeters - usedArea;
    }

    // --- GETTERS & SETTERS ---
    public String getId() { return id; }
    public Economic_Entity getOwner() { return owner; }
    public void setOwner(Economic_Entity newOwner) { this.owner = newOwner; }
    public List<Building> getBuildings() { return buildings; }
    public double getCurrentMarketValue() { return currentMarketValue; }
}