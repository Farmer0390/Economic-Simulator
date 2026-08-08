package Realestate;

public enum BuildingType {
    // --- Residential buildings ---
    SINGLE_HOUSE,       // Single-family house
    DOUBLE_HOUSE,       // Semi-detached house
    ROW_HOUSE,          // Terraced house
    APARTMENT_BLOCK,    // Multi-family building
    SKYSCRAPER,         // High-rise

    // --- Commercial & Services ---
    OFFICE,             // Office (software, insurance, advertising)
    SHOP,               // Shop/retail
    WAREHOUSE,          // Warehouse
    SHOPPING_MALL,      // Shopping mall

    // --- Industry & Raw Materials ---
    FACTORY,            // Standard factory (furniture, tools, consumer goods)
    STEELWORKS,         // Steel mill
    RAFFINERIE,         // Oil refinery
    POWER_PLANT,        // Power plant (generates Electricity)
    PUMP_STATION,       // Pump station / waterworks (generates Water)
    MINE,               // Mine (ores, coal, stone, sand, salt)

    // --- Agriculture (Farming) ---
    FARM,               // Farm/field
    STALL,              // Livestock stall (milk, eggs, meat)
    GREENHOUSE,         // Greenhouse (high-efficiency food production)

    // --- Public Service & Infrastructure ---
    SCHOOL,             // School / university (Education sector)
    HOSPITAL,           // Hospital (Healthcare / medicine)
    FIRE_STATION,       // Fire station
    POLICE_STATION      // Police
, FOREST
}