package Main;

import java.util.HashMap;
import java.util.Map;

import Market.ProductType;
import Realestate.BuildingType;

public class RecipeBook {
    private static final Map<ProductType, ProductionRecipe> registry = new HashMap<>();

    static {
        
        ProductionRecipe water = new ProductionRecipe(0.1, 1, BuildingType.PUMP_STATION);
        water.addIngredient(ProductType.Electricity, 1);
        registry.put(ProductType.Water, water);


        ProductionRecipe wood = new ProductionRecipe(0.5, 2, BuildingType.FOREST);
        wood.addIngredient(ProductType.Water, 2);
        registry.put(ProductType.Wood, wood);

        ProductionRecipe stone = new ProductionRecipe(0.6, 3, BuildingType.MINE);
        stone.addIngredient(ProductType.Electricity, 2);
        registry.put(ProductType.Stone, stone);

  
        ProductionRecipe ironOre = new ProductionRecipe(0.8, 4, BuildingType.MINE);
        ironOre.addIngredient(ProductType.Electricity, 3);
        registry.put(ProductType.Iron_Ore, ironOre);

    
        ProductionRecipe copperOre = new ProductionRecipe(0.8, 4, BuildingType.MINE);
        copperOre.addIngredient(ProductType.Electricity, 3);
        registry.put(ProductType.Copper_Ore, copperOre);

      
        ProductionRecipe coal = new ProductionRecipe(0.7, 4, BuildingType.MINE);
        coal.addIngredient(ProductType.Electricity, 2);
        registry.put(ProductType.Coal, coal);

       
        ProductionRecipe crudeOil = new ProductionRecipe(1.0, 5, BuildingType.MINE);
        crudeOil.addIngredient(ProductType.Electricity, 5);
        registry.put(ProductType.Crude_Oil, crudeOil);

        
        ProductionRecipe naturalGas = new ProductionRecipe(1.0, 5, BuildingType.MINE);
        naturalGas.addIngredient(ProductType.Electricity, 4);
        registry.put(ProductType.Natural_Gas, naturalGas);

        
        ProductionRecipe sand = new ProductionRecipe(0.3, 2, BuildingType.MINE);
        registry.put(ProductType.Sand, sand);

      
        ProductionRecipe salt = new ProductionRecipe(0.4, 2, BuildingType.MINE);
        salt.addIngredient(ProductType.Water, 1);
        registry.put(ProductType.Salt, salt);

        ProductionRecipe charcoal = new ProductionRecipe(0.6, 2, BuildingType.FACTORY);
        charcoal.addIngredient(ProductType.Wood, 3);
        registry.put(ProductType.Charcoal, charcoal);

   
        ProductionRecipe electricity = new ProductionRecipe(0.2, 10, BuildingType.POWER_PLANT);
        electricity.addIngredient(ProductType.Coal, 2);
        registry.put(ProductType.Electricity, electricity);

        
        

        ProductionRecipe wheat = new ProductionRecipe(0.4, 1, BuildingType.FARM);
        wheat.addIngredient(ProductType.Water, 3);
        wheat.addIngredient(ProductType.Fertilizer, 1);
        registry.put(ProductType.Wheat, wheat);

       
        ProductionRecipe corn = new ProductionRecipe(0.4, 1, BuildingType.FARM);
        corn.addIngredient(ProductType.Water, 2);
        corn.addIngredient(ProductType.Fertilizer, 1);
        registry.put(ProductType.Corn, corn);

        ProductionRecipe rice = new ProductionRecipe(0.5, 1, BuildingType.FARM);
        rice.addIngredient(ProductType.Water, 5);
        rice.addIngredient(ProductType.Fertilizer, 1);
        registry.put(ProductType.Rice, rice);


        ProductionRecipe potatoes = new ProductionRecipe(0.4, 1, BuildingType.FARM);
        potatoes.addIngredient(ProductType.Water, 2);
        registry.put(ProductType.Potatoes, potatoes);


        ProductionRecipe sugarCane = new ProductionRecipe(0.5, 1, BuildingType.FARM);
        sugarCane.addIngredient(ProductType.Water, 4);
        registry.put(ProductType.Sugar_Cane, sugarCane);


        ProductionRecipe cotton = new ProductionRecipe(0.6, 1, BuildingType.FARM);
        cotton.addIngredient(ProductType.Water, 3);
        cotton.addIngredient(ProductType.Fertilizer, 1);
        registry.put(ProductType.Cotton, cotton);


        ProductionRecipe coffee = new ProductionRecipe(0.8, 1, BuildingType.FARM);
        coffee.addIngredient(ProductType.Water, 2);
        registry.put(ProductType.Coffee, coffee);

  
        ProductionRecipe cocoa = new ProductionRecipe(0.8, 1, BuildingType.FARM);
        cocoa.addIngredient(ProductType.Water, 3);
        registry.put(ProductType.Cocoa, cocoa);

       
        ProductionRecipe feed = new ProductionRecipe(0.3, 2, BuildingType.FACTORY);
        feed.addIngredient(ProductType.Wheat, 1);
        feed.addIngredient(ProductType.Corn, 1);
        registry.put(ProductType.Animal_Feed, feed);
        registry.put(ProductType.Livestock_Food, feed);

      
        ProductionRecipe milk = new ProductionRecipe(0.5, 2, BuildingType.STALL);
        milk.addIngredient(ProductType.Animal_Feed, 2);
        milk.addIngredient(ProductType.Water, 3);
        registry.put(ProductType.Milk, milk);
        
        ProductionRecipe eggs = new ProductionRecipe(0.3, 1, BuildingType.STALL);
        eggs.addIngredient(ProductType.Animal_Feed, 1);
        eggs.addIngredient(ProductType.Water, 1);
        registry.put(ProductType.Eggs, eggs);

      
        ProductionRecipe beef = new ProductionRecipe(0.8, 3, BuildingType.STALL);
        beef.addIngredient(ProductType.Animal_Feed, 5);
        beef.addIngredient(ProductType.Water, 5);
        registry.put(ProductType.Beef, beef);

      
        ProductionRecipe pork = new ProductionRecipe(0.6, 3, BuildingType.STALL);
        pork.addIngredient(ProductType.Animal_Feed, 4);
        pork.addIngredient(ProductType.Water, 3);
        registry.put(ProductType.Pork, pork);

       
        ProductionRecipe chicken = new ProductionRecipe(0.4, 2, BuildingType.STALL);
        chicken.addIngredient(ProductType.Animal_Feed, 2);
        chicken.addIngredient(ProductType.Water, 1);
        registry.put(ProductType.Chicken_Meat, chicken);

     
        ProductionRecipe fish = new ProductionRecipe(0.7, 2, BuildingType.FARM);
        registry.put(ProductType.Fish, fish);

    
        ProductionRecipe leather = new ProductionRecipe(0.5, 2, BuildingType.FACTORY);
        leather.addIngredient(ProductType.Beef, 1);
        registry.put(ProductType.Leather, leather);

    
        ProductionRecipe wool = new ProductionRecipe(0.4, 1, BuildingType.STALL);
        wool.addIngredient(ProductType.Animal_Feed, 2);
        registry.put(ProductType.Wool, wool);

     
        ProductionRecipe honey = new ProductionRecipe(0.5, 1, BuildingType.FARM);
        registry.put(ProductType.Honey, honey);

     
        ProductionRecipe butter = new ProductionRecipe(0.3, 2, BuildingType.FACTORY);
        butter.addIngredient(ProductType.Milk, 3);
        registry.put(ProductType.Butter, butter);

       
        ProductionRecipe cheese = new ProductionRecipe(0.6, 2, BuildingType.FACTORY);
        cheese.addIngredient(ProductType.Milk, 5);
        cheese.addIngredient(ProductType.Salt, 1);
        registry.put(ProductType.Cheese, cheese);


        ProductionRecipe steel = new ProductionRecipe(1.5, 10, BuildingType.STEELWORKS);
        steel.addIngredient(ProductType.Iron_Ore, 2);
        steel.addIngredient(ProductType.Coal, 1);
        steel.addIngredient(ProductType.Electricity, 5);
        registry.put(ProductType.Steel, steel);

 
        ProductionRecipe copperWire = new ProductionRecipe(0.8, 4, BuildingType.FACTORY);
        copperWire.addIngredient(ProductType.Copper_Ore, 2);
        copperWire.addIngredient(ProductType.Electricity, 3);
        registry.put(ProductType.Copper_Wire, copperWire);

      
        ProductionRecipe aluminum = new ProductionRecipe(2.0, 15, BuildingType.FACTORY);
        aluminum.addIngredient(ProductType.Electricity, 15);
        registry.put(ProductType.Aluminum, aluminum);

      
        ProductionRecipe cement = new ProductionRecipe(0.5, 5, BuildingType.FACTORY);
        cement.addIngredient(ProductType.Stone, 3);
        cement.addIngredient(ProductType.Coal, 1);
        registry.put(ProductType.Cement, cement);

   
        ProductionRecipe glass = new ProductionRecipe(1.0, 6, BuildingType.FACTORY);
        glass.addIngredient(ProductType.Sand, 3);
        glass.addIngredient(ProductType.Electricity, 4);
        registry.put(ProductType.Glass, glass);

      
        ProductionRecipe plastic = new ProductionRecipe(0.9, 5, BuildingType.RAFFINERIE);
        plastic.addIngredient(ProductType.Crude_Oil, 2);
        plastic.addIngredient(ProductType.Electricity, 3);
        registry.put(ProductType.Plastic, plastic);


        ProductionRecipe paper = new ProductionRecipe(0.4, 3, BuildingType.FACTORY);
        paper.addIngredient(ProductType.Wood, 2);
        paper.addIngredient(ProductType.Water, 4);
        registry.put(ProductType.Paper, paper);
        
       
        ProductionRecipe chemicals = new ProductionRecipe(1.2, 8, BuildingType.FACTORY);
        chemicals.addIngredient(ProductType.Crude_Oil, 1);
        chemicals.addIngredient(ProductType.Natural_Gas, 1);
        chemicals.addIngredient(ProductType.Salt, 1);
        registry.put(ProductType.Chemicals, chemicals);
       
        ProductionRecipe fertilizer = new ProductionRecipe(0.5, 3, BuildingType.FACTORY);
        fertilizer.addIngredient(ProductType.Chemicals, 1);
        fertilizer.addIngredient(ProductType.Natural_Gas, 1);
        registry.put(ProductType.Fertilizer, fertilizer);
        //
        ProductionRecipe rubber = new ProductionRecipe(0.8, 4, BuildingType.FACTORY);
        rubber.addIngredient(ProductType.Chemicals, 1);
        rubber.addIngredient(ProductType.Crude_Oil, 1);
        registry.put(ProductType.Rubber, rubber);
        
        // 4.
        ProductionRecipe gasoline = new ProductionRecipe
        (0.5, 8, BuildingType.RAFFINERIE);
        gasoline.addIngredient(ProductType.Crude_Oil, 2);
        registry.put(ProductType.Gasoline, gasoline);
     
        ProductionRecipe diesel = new ProductionRecipe(0.5, 8, BuildingType.RAFFINERIE);
        diesel.addIngredient(ProductType.Crude_Oil, 2);
        registry.put(ProductType.Diesel, diesel);
      
        ProductionRecipe heatingOil = new ProductionRecipe(0.4, 6, BuildingType.RAFFINERIE);
        heatingOil.addIngredient(ProductType.Crude_Oil, 2);
        registry.put(ProductType.Heating_Oil, heatingOil);

        ProductionRecipe batteries = new ProductionRecipe(1.0, 5, BuildingType.FACTORY);
        batteries.addIngredient(ProductType.Chemicals, 2);
        batteries.addIngredient(ProductType.Copper_Wire, 1);
        registry.put(ProductType.Batteries, batteries);
   
        ProductionRecipe solar = new ProductionRecipe(3.0, 12, BuildingType.FACTORY);
        solar.addIngredient(ProductType.Glass, 2);
        solar.addIngredient(ProductType.Computer_Chips, 2);
        registry.put(ProductType.Solar_Panels, solar);
        
        ProductionRecipe wind = new ProductionRecipe(5.0, 20, BuildingType.FACTORY);
        wind.addIngredient(ProductType.Steel, 10);
        wind.addIngredient(ProductType.Generators, 1);       
        wind.addIngredient(ProductType.Cables, 5);
        registry.put(ProductType.Wind_Turbines, wind);
      
        ProductionRecipe
nuclear = new ProductionRecipe(8.0, 30, BuildingType.FACTORY);
        nuclear.addIngredient(ProductType.Chemicals, 5);
        nuclear.addIngredient(ProductType.Electricity, 50);
        registry.put(ProductType.Nuclear_Fuel, nuclear);
   
        ProductionRecipe hydrogen = new ProductionRecipe(1.2, 15, BuildingType.FACTORY);
        hydrogen.addIngredient(ProductType.Water, 5);
        hydrogen.addIngredient(ProductType.Electricity, 20);
        registry.put(ProductType.Hydrogen, hydrogen);
     
     
        ProductionRecipe hammers = new ProductionRecipe(0.3, 1, BuildingType.FACTORY);
        hammers.addIngredient(ProductType.Steel, 1);
        hammers.addIngredient(ProductType.Wood, 1);
        registry.put(ProductType.Hammers, hammers);
 
        ProductionRecipe screws = new ProductionRecipe(0.2, 2, BuildingType.FACTORY);
        screws.addIngredient(ProductType.Steel, 1);
        registry.put(ProductType.Screws, screws);
     
        ProductionRecipe nails = new ProductionRecipe(0.2, 2, BuildingType.FACTORY);
        nails.addIngredient(ProductType.Steel, 1);
        registry.put(ProductType.Nails, nails);
      
        ProductionRecipe pipes = new ProductionRecipe(0.5, 4, BuildingType.FACTORY);
        pipes.addIngredient(ProductType.Steel, 2);
        pipes.addIngredient(ProductType.Plastic, 1);
        registry.put(ProductType.Pipes, pipes);
    
        ProductionRecipe pumps = new ProductionRecipe(1.5, 6, BuildingType.FACTORY);
        pumps.addIngredient(ProductType.Pipes, 2);
        pumps.addIngredient(ProductType.Engines, 1);
        registry.put(ProductType.Pumps, pumps);
      
        ProductionRecipe engines = new ProductionRecipe(2.5, 12, BuildingType.FACTORY);
        engines.addIngredient(ProductType.Steel, 4);
        engines.addIngredient(ProductType.Copper_Wire, 3);
        engines.addIngredient(ProductType.Screws, 10);
        registry.put(ProductType.Engines, engines);
    
        ProductionRecipe generators = new ProductionRecipe(3.0, 15, BuildingType.FACTORY);
        generators.addIngredient(ProductType.Copper_Wire, 6);
        generators.addIngredient(ProductType.Steel, 5);
        generators.addIngredient(ProductType.Engines, 1);
        registry.put(ProductType.Generators, generators);

        ProductionRecipe tractors = new ProductionRecipe(6.0, 25, BuildingType.FACTORY);
        tractors.addIngredient(ProductType.Engines, 1);
        tractors.addIngredient(ProductType.Steel, 8);
        tractors.addIngredient(ProductType.Tires, 4);
        registry.put(ProductType.Tractors, tractors);
        
        ProductionRecipe tools = new ProductionRecipe(5.0, 30, BuildingType.FACTORY);
        tools.addIngredient(ProductType.Steel, 10);
        tools.addIngredient(ProductType.Computer_Chips, 2);
        registry.put(ProductType.Machine_Tools, tools);
    
        ProductionRecipe chips = new ProductionRecipe(4.0, 40, BuildingType.FACTORY);
        chips.addIngredient(ProductType.Sand, 1);
        chips.addIngredient(ProductType.Chemicals, 2);
        chips.addIngredient(ProductType.Copper_Wire, 2);
        chips.addIngredient(ProductType.Electricity, 10);
        registry.put(ProductType.Computer_Chips, chips);
        // 6. 


        ProductionRecipe bricks = new ProductionRecipe(0.4, 4, BuildingType.FACTORY);
        bricks.addIngredient(ProductType.Sand, 2);
        bricks.addIngredient(ProductType.Stone, 1);
        bricks.addIngredient(ProductType.Water, 1);
        registry.put(ProductType.Bricks, bricks);
 
        ProductionRecipe asphalt = new ProductionRecipe(0.5, 5, BuildingType.FACTORY);
        asphalt.addIngredient(ProductType.Crude_Oil, 1);
        asphalt.addIngredient(ProductType.Stone, 4);
        registry.put(ProductType.Asphalt, asphalt);
   
        ProductionRecipe windows = new ProductionRecipe(1.0, 4, BuildingType.FACTORY);
        windows.addIngredient(ProductType.Glass, 2);
        windows.addIngredient(ProductType.Plastic, 1);
        registry.put(ProductType.Windows, windows);
    
        ProductionRecipe doors = new ProductionRecipe(0.8, 3, BuildingType.FACTORY);
        doors.addIngredient(ProductType.Wood, 2);
        doors.addIngredient(ProductType.Hammers, 1);
        registry.put(ProductType.Doors, doors);
      
        ProductionRecipe cables = new ProductionRecipe(0.4, 3, BuildingType.FACTORY);
        cables.addIngredient(ProductType.Copper_Wire, 2);
        cables.addIngredient(ProductType.Plastic, 1);
        registry.put(ProductType.Cables, cables);
     
        ProductionRecipe paint = new ProductionRecipe(0.5, 2, BuildingType.FACTORY);
        paint.addIngredient(ProductType.Chemicals, 2);
        paint.addIngredient(ProductType.Water, 2);
        registry.put(ProductType.Paint, paint);
      
        ProductionRecipe furniture = new ProductionRecipe(2.0, 5, BuildingType.FACTORY);
        furniture.addIngredient(ProductType.Wood, 4);
        furniture.addIngredient(ProductType.Screws, 8);
        furniture.addIngredient(ProductType.Paint, 1);
        registry.put(ProductType.Furniture, furniture);
       
        ProductionRecipe insulation = new ProductionRecipe(0.6, 3, BuildingType.FACTORY);
        insulation.addIngredient(ProductType.Plastic, 2);
        insulation.addIngredient(ProductType.Chemicals, 1);
        registry.put(ProductType.Insulation, insulation);
     
        ProductionRecipe beams = new ProductionRecipe(1.8, 12, BuildingType.STEELWORKS);
        beams.addIngredient(ProductType.Steel, 3);
        registry.put(ProductType.Steel_Beams, beams);
       
        ProductionRecipe plumbing = new ProductionRecipe(1.5, 6, BuildingType.FACTORY);
        plumbing.addIngredient(ProductType.Pipes, 3);
        plumbing.addIngredient(ProductType.Pumps, 1);
        registry.put(ProductType.Plumbing_Equipment, plumbing);
       
        // 7. TRANSPORTMITTEL & SCHWERTRANSPORT
        
        ProductionRecipe bicycles = new ProductionRecipe(1.2, 4, BuildingType.FACTORY);
        bicycles.addIngredient(ProductType.Steel, 1);
        bicycles.addIngredient(ProductType.Tires, 2);
        registry.put(ProductType.Bicycles, bicycles);
  
        ProductionRecipe
        motorcycles = new ProductionRecipe(2.5, 10, BuildingType.FACTORY);
        motorcycles.addIngredient(ProductType.Engines, 1);
        motorcycles.addIngredient(ProductType.Steel, 2);
        motorcycles.addIngredient(ProductType.Tires, 2);
        registry.put(ProductType.Motorcycles, motorcycles);

        ProductionRecipe
        cars = new ProductionRecipe(8.0, 35, BuildingType.FACTORY);
        cars.addIngredient(ProductType.Steel, 4);
        cars.addIngredient(ProductType.Engines, 1);
        cars.addIngredient(ProductType.Tires, 4);
        cars.addIngredient(ProductType.Glass, 4);
        cars.addIngredient(ProductType.Cables, 10);
        cars.addIngredient(ProductType.Computer_Chips, 4);
        registry.put(ProductType.Cars, cars);
       
        ProductionRecipe 
        trucks = new ProductionRecipe(15.0, 50, BuildingType.FACTORY);
        trucks.addIngredient(ProductType.Steel, 12);
        trucks.addIngredient(ProductType.Engines, 2);
        trucks.addIngredient(ProductType.Tires, 6);
        trucks.addIngredient(ProductType.Computer_Chips, 4);
        registry.put(ProductType.Trucks, trucks);
      
        ProductionRecipe buses = new ProductionRecipe(18.0, 55, BuildingType.FACTORY);
        buses.addIngredient(ProductType.Steel, 15);
        buses.addIngredient(ProductType.Engines, 2);
        buses.addIngredient(ProductType.Tires, 6);
        buses.addIngredient(ProductType.Glass, 10);
        registry.put(ProductType.Buses, buses);
     
        ProductionRecipe trains = new ProductionRecipe(50.0, 100, BuildingType.FACTORY);
        trains.addIngredient(ProductType.Steel, 50);
        trains.addIngredient(ProductType.Generators, 4);
        registry.put(ProductType.Trains, trains);
      
        ProductionRecipe ships = new ProductionRecipe(80.0, 120, BuildingType.FACTORY);
        ships.addIngredient(ProductType.Steel, 100);
        ships.addIngredient(ProductType.Engines, 4);
        ships.addIngredient(ProductType.Pumps, 8);
        registry.put(ProductType.Ships, ships);
        
        ProductionRecipe airplanes = new ProductionRecipe(120.0, 150, BuildingType.FACTORY);
        airplanes.addIngredient(ProductType.Aluminum, 40);
        airplanes.addIngredient(ProductType.Engines, 4);
        airplanes.addIngredient(ProductType.Computer_Chips, 50);
        registry.put(ProductType.Airplanes, airplanes);
        
        ProductionRecipe tires = new ProductionRecipe(0.6, 4, BuildingType.FACTORY);
        tires.addIngredient(ProductType.Rubber, 2);
        tires.addIngredient(ProductType.Steel, 1);
        registry.put(ProductType.Tires, tires);

        ProductionRecipe containers = new ProductionRecipe(1.5, 8, BuildingType.FACTORY);
        containers.addIngredient(ProductType.Steel, 5);
        registry.put(ProductType.Shipping_Containers, containers);
       
        // 8.


        ProductionRecipe clothing = new ProductionRecipe(1.0, 2, BuildingType.FACTORY);
        clothing.addIngredient(ProductType.Cotton, 3);
        clothing.addIngredient(ProductType.Wool, 1);
        registry.put(ProductType.Clothing, clothing);
        // Schuhe
        ProductionRecipe shoes = new ProductionRecipe(1.2, 3, BuildingType.FACTORY);
        shoes.addIngredient(ProductType.Leather, 2);
        shoes.addIngredient(ProductType.Rubber, 1);
        registry.put(ProductType.Shoes, shoes);
  
        ProductionRecipe phones = new ProductionRecipe(3.5, 15, BuildingType.FACTORY);
        phones.addIngredient(ProductType.Computer_Chips, 3);
        phones.addIngredient(ProductType.Glass, 1);
        phones.addIngredient(ProductType.Plastic, 1);
        registry.put(ProductType.Smartphones, phones);
        
        ProductionRecipe tv = new ProductionRecipe(4.5, 18, BuildingType.FACTORY);
        tv.addIngredient(ProductType.Glass, 4);
        tv.addIngredient(ProductType.Computer_Chips, 4);
        tv.addIngredient(ProductType.Cables, 3);
        registry.put(ProductType.Televisions, tv);
      
        ProductionRecipe fridge = new ProductionRecipe(4.0, 12, BuildingType.FACTORY);
        fridge.addIngredient(ProductType.Steel, 4);
        fridge.addIngredient(ProductType.Pumps, 1);
        fridge.addIngredient(ProductType.Chemicals, 2);
        registry.put(ProductType.Refrigerators, fridge);
  
        ProductionRecipe washing = new ProductionRecipe(4.0, 12, BuildingType.FACTORY);
        washing.addIngredient(ProductType.Steel, 5);
        washing.addIngredient(ProductType.Engines, 1);
        washing.addIngredient(ProductType.Pipes, 2);
        registry.put(ProductType.Washing_Machines, washing);
       
        ProductionRecipe soap = new ProductionRecipe(0.3, 1, BuildingType.FACTORY);
        soap.addIngredient(ProductType.Chemicals, 1);
        soap.addIngredient(ProductType.Water, 1);
        registry.put(ProductType.Soap, soap);
       
        ProductionRecipe shampoo = new ProductionRecipe(0.3, 1, BuildingType.FACTORY);
        shampoo.addIngredient(ProductType.Chemicals, 1);
        shampoo.addIngredient(ProductType.Water, 2);
        registry.put(ProductType.Shampoo, shampoo);
    
        ProductionRecipe medicine = new ProductionRecipe(2.0, 20, BuildingType.HOSPITAL);
        medicine.addIngredient(ProductType.Chemicals, 3);
        medicine.addIngredient(ProductType.Honey, 1);
        registry.put(ProductType.Medicine, medicine);
     
        ProductionRecipe toys = new ProductionRecipe(0.6, 2, BuildingType.FACTORY);
        toys.addIngredient(ProductType.Plastic, 2);
        toys.addIngredient(ProductType.Paint, 1);
        registry.put(ProductType.Toys, toys);
   
        ProductionRecipe internet = new ProductionRecipe(0.5, 5, BuildingType.OFFICE);
        internet.addIngredient(ProductType.Cables, 1);
        internet.addIngredient(ProductType.Electricity, 10);
        registry.put(ProductType.Internet_Access, internet);
       
        ProductionRecipe software = new ProductionRecipe(5.0, 2, BuildingType.OFFICE);
        software.addIngredient(ProductType.Electricity, 5);
        registry.put(ProductType.Software, software);
       
        ProductionRecipe insurance = new ProductionRecipe(2.0, 1, BuildingType.OFFICE);
        insurance.addIngredient(ProductType.Paper, 2);
        registry.put(ProductType.Insurance, insurance);
        
        ProductionRecipe health = new ProductionRecipe(4.0, 10, BuildingType.HOSPITAL);
        health.addIngredient(ProductType.Medicine, 2);
        health.addIngredient(ProductType.Electricity, 15);
        registry.put(ProductType.Healthcare, health);
       
        ProductionRecipe advertising = new ProductionRecipe(1.5, 3, BuildingType.OFFICE);
        advertising.addIngredient(ProductType.Paper, 1);
        advertising.addIngredient(ProductType.Software, 1);
        registry.put(ProductType.Advertising, advertising);
     
        ProductionRecipe entertainment = new ProductionRecipe(2.0, 5, BuildingType.SCHOOL);
        entertainment.addIngredient(ProductType.Electricity, 5);
        registry.put(ProductType.Entertainment, entertainment);
        
        registry.put(ProductType.none, new ProductionRecipe(0, 0, null));
 

        // --- TIER 1
        ProductionRecipe pureCoal = new ProductionRecipe(0.2, 2, BuildingType.FACTORY);
        pureCoal.addIngredient(ProductType.Coal, 3);
        pureCoal.addIngredient(ProductType.Water, 1);
        registry.put(ProductType.Charcoal, pureCoal);

        // Mehl (Flour / über Livestock_Food/Animal_Feed abgedeckt)
        // 0.2 Wochen = 8 Stunden Arbeit in der Mühle
        ProductionRecipe flour = new ProductionRecipe(0.2, 3, BuildingType.FACTORY);
        flour.addIngredient(ProductType.Wheat, 4);
        registry.put(ProductType.Animal_Feed, flour);

        ProductionRecipe paper2 = new ProductionRecipe(0.5, 4, BuildingType.FACTORY);
        paper2.addIngredient(ProductType.Wood, 2);
        paper2.addIngredient(ProductType.Water, 5);
        registry.put(ProductType.Paper, paper2);


        // --- TIER 2: ELEKTRONIK-BAUSTEINE & CHEMIC-BASIS ---

    
        ProductionRecipe cables2 = new ProductionRecipe(0.4, 5, BuildingType.FACTORY);
        cables2.addIngredient(ProductType.Copper_Wire, 2);
        cables2.addIngredient(ProductType.Plastic, 1);
        registry.put(ProductType.Cables, cables2);

        ProductionRecipe batteries2 = new ProductionRecipe(1.0, 10, BuildingType.FACTORY);
        batteries2.addIngredient(ProductType.Chemicals, 2);
        batteries2.addIngredient(ProductType.Copper_Wire, 1);
        batteries2.addIngredient(ProductType.Aluminum, 1);
        registry.put(ProductType.Batteries, batteries2);

     
        ProductionRecipe computer_Chips = new ProductionRecipe(4.0, 50, BuildingType.FACTORY);
        computer_Chips.addIngredient(ProductType.Sand, 2); 
        computer_Chips.addIngredient(ProductType.Chemicals, 3);
        computer_Chips.addIngredient(ProductType.Copper_Wire, 2);
        computer_Chips.addIngredient(ProductType.Electricity, 40);
        registry.put(ProductType.Computer_Chips, computer_Chips);


        // --- TIER 3: HOCHENTWICKELTER MASCHINENBAU ---


        ProductionRecipe pumps2 = new ProductionRecipe(1.5, 12, BuildingType.FACTORY);
        pumps2.addIngredient(ProductType.Pipes, 3);
        pumps2.addIngredient(ProductType.Engines, 1);
        pumps2.addIngredient(ProductType.Screws, 12);
        registry.put(ProductType.Pumps, pumps2);

    
        ProductionRecipe generators2 = new ProductionRecipe(3.0, 20, BuildingType.FACTORY);
        generators2.addIngredient(ProductType.Copper_Wire, 8);
        generators2.addIngredient(ProductType.Steel, 6);
        generators2.addIngredient(ProductType.Engines, 1);
        generators2.addIngredient(ProductType.Electricity, 10);
        registry.put(ProductType.Generators, generators2);

 
        ProductionRecipe tractors2 = new ProductionRecipe(6.0, 40, BuildingType.FACTORY);
        tractors2.addIngredient(ProductType.Engines, 1);
        tractors2.addIngredient(ProductType.Steel, 10);
        tractors2.addIngredient(ProductType.Tires, 4);

        tractors2.addIngredient(ProductType.Computer_Chips, 1);
        registry.put(ProductType.Tractors, tractors2);


        // --- TIER 4: 

        ProductionRecipe windows2 = new ProductionRecipe(0.8, 6, BuildingType.FACTORY);
        windows2.addIngredient(ProductType.Glass, 3);
        windows2.addIngredient(ProductType.Plastic, 1);
        windows2.addIngredient(ProductType.Screws, 8);
        registry.put(ProductType.Windows, windows2);

     
        ProductionRecipe doors2 = new ProductionRecipe(0.6, 4, BuildingType.FACTORY);
        doors2.addIngredient(ProductType.Wood, 3);
        doors2.addIngredient(ProductType.Screws, 4);
        registry.put(ProductType.Doors, doors2);

      
        ProductionRecipe furniture2 = new ProductionRecipe(2.0, 8, BuildingType.FACTORY);
        furniture2.addIngredient(ProductType.Wood, 5);
        furniture2.addIngredient(ProductType.Screws, 16);
        furniture2.addIngredient(ProductType.Paint, 1);
        registry.put(ProductType.Furniture, furniture2);


        // --- TIER 5

        // Fahrräder (Bicycles)
        ProductionRecipe bicycles2 = new ProductionRecipe(1.0, 5, BuildingType.FACTORY);
        bicycles2.addIngredient(ProductType.Steel, 2);
        bicycles2.addIngredient(ProductType.Tires, 2);
        registry.put(ProductType.Bicycles, bicycles2);

    
        ProductionRecipe motorcycles2 = new ProductionRecipe(2.5, 15, BuildingType.FACTORY);
        motorcycles2.addIngredient(ProductType.Engines, 1);
        motorcycles2.addIngredient(ProductType.Steel, 3);
        motorcycles2.addIngredient(ProductType.Tires, 2);
        motorcycles2.addIngredient(ProductType.Batteries, 1);
        registry.put(ProductType.Motorcycles, motorcycles2);

        
        ProductionRecipe cars2 = new ProductionRecipe(8.0, 60, BuildingType.FACTORY);
        cars2.addIngredient(ProductType.Steel, 6);
        cars2.addIngredient(ProductType.Engines, 1);
        cars2.addIngredient(ProductType.Tires, 4);
        cars2.addIngredient(ProductType.Glass, 4);
        cars2.addIngredient(ProductType.Cables, 12);

        cars2.addIngredient(ProductType.Computer_Chips, 4);
        registry.put(ProductType.Cars, cars2);

   
        ProductionRecipe trucks2 = new ProductionRecipe(14.0, 80, BuildingType.FACTORY);
        trucks2.addIngredient(ProductType.Steel, 15);
        trucks2.addIngredient(ProductType.Engines, 2);
        trucks2.addIngredient(ProductType.Tires, 6);
      
        trucks2.addIngredient(ProductType.Computer_Chips, 6);
        registry.put(ProductType.Trucks, trucks2);


       
        ProductionRecipe clothing2 = new ProductionRecipe(0.8, 4, BuildingType.FACTORY);
        clothing2.addIngredient(ProductType.Cotton, 4);
        clothing2.addIngredient(ProductType.Wool, 1);
        registry.put(ProductType.Clothing, clothing2);

      
        ProductionRecipe shoes2 = new ProductionRecipe(1.0, 4, BuildingType.FACTORY);
        shoes2.addIngredient(ProductType.Leather, 2);
        shoes2.addIngredient(ProductType.Rubber, 1);
        registry.put(ProductType.Shoes, shoes2);

       
        ProductionRecipe smartphones = new ProductionRecipe(3.5, 30, BuildingType.FACTORY);
      
        smartphones.addIngredient(ProductType.Computer_Chips, 4);
        smartphones.addIngredient(ProductType.Glass, 1);
        smartphones.addIngredient(ProductType.Batteries, 1);
        smartphones.addIngredient(ProductType.Plastic, 2);
        registry.put(ProductType.Smartphones, smartphones);

        
      
        ProductionRecipe televisions = new ProductionRecipe(4.5, 35, BuildingType.FACTORY);
        televisions.addIngredient(ProductType.Glass, 4);
        televisions.addIngredient(ProductType.Computer_Chips, 5);
        televisions.addIngredient(ProductType.Cables, 5);
        registry.put(ProductType.Televisions, televisions);

       
        ProductionRecipe refrigerators = new ProductionRecipe(4.0, 25, BuildingType.FACTORY);
        refrigerators.addIngredient(ProductType.Steel, 5);
        refrigerators.addIngredient(ProductType.Pumps, 1);
        refrigerators.addIngredient(ProductType.Chemicals, 3);
        registry.put(ProductType.Refrigerators, refrigerators);

        
        ProductionRecipe medicine2 = new ProductionRecipe(2.0, 30, BuildingType.HOSPITAL);
        medicine2.addIngredient(ProductType.Chemicals, 4);
        medicine2.addIngredient(ProductType.Honey, 2); 
        registry.put(ProductType.Medicine, medicine2);
        }
    public static ProductionRecipe getRecipe(ProductType output) {
    	return registry.get(output);
    }
}
