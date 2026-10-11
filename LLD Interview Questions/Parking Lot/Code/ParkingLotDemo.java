package Code;

import Code.enums.VehicleSize;
import Code.models.ParkingFloor;
import Code.models.ParkingTicket;
import Code.models.vehicles.Bike;
import Code.models.vehicles.Car;
import Code.models.vehicles.Truck;
import Code.strategies.feeStrategies.HourlyFeeStrategy;
import Code.strategies.spotAllocationStrategies.BestFitStrategy;
import Code.strategies.spotAllocationStrategies.NearestFirstStrategy;

import java.util.*;
import java.util.concurrent.*;
import java.time.*;

public class ParkingLotDemo {
    public static void main(String[] args) {
        // Build two floors with a mix of spot sizes
        Map<VehicleSize, Integer> floor1Counts = new HashMap<>();
        floor1Counts.put(VehicleSize.SMALL, 2);
        floor1Counts.put(VehicleSize.MEDIUM, 2);
        floor1Counts.put(VehicleSize.LARGE, 1);

        Map<VehicleSize, Integer> floor2Counts = new HashMap<>();
        floor2Counts.put(VehicleSize.SMALL, 1);
        floor2Counts.put(VehicleSize.MEDIUM, 1);
        floor2Counts.put(VehicleSize.LARGE, 1);

        List<ParkingFloor> floors = List.of(
                new ParkingFloor(1, floor1Counts),
                new ParkingFloor(2, floor2Counts)
        );

        // Initialize the singleton with strategies
        ParkingLot lot = ParkingLot.getInstance();
        lot.initialize(floors, new HourlyFeeStrategy(10.0), new NearestFirstStrategy());

        lot.displayAvailability();

        // === Scenario 1: Park a few vehicles (nearest-first) ===
        System.out.println("========== SCENARIO 1: Park Vehicles (Nearest First) ==========");
        ParkingTicket bikeTicket = lot.parkVehicle(new Bike("KA-01-1111"));
        ParkingTicket carTicket = lot.parkVehicle(new Car("KA-02-2222"));
        lot.parkVehicle(new Truck("KA-03-3333"));

        lot.displayAvailability();

        // === Scenario 2: Switch to best-fit allocation ===
        System.out.println("========== SCENARIO 2: Park With Best Fit ==========");
        lot.setSpotAllocationStrategy(new BestFitStrategy());
        lot.parkVehicle(new Car("KA-04-4444"));

        // === Scenario 3: Unpark and pay ===
        System.out.println("\n========== SCENARIO 3: Unpark Vehicles ==========");
        lot.unparkVehicle(bikeTicket.getTicketId());
        lot.unparkVehicle(carTicket.getTicketId());

        lot.displayAvailability();

        // === Scenario 4: Fill the large spots, then fail to park a truck ===
        System.out.println("========== SCENARIO 4: No Spot Available ==========");
        try {
            lot.setSpotAllocationStrategy(new NearestFirstStrategy());
            lot.parkVehicle(new Truck("KA-05-5555"));  // remaining large spot
            lot.parkVehicle(new Truck("KA-06-6666"));  // should fail
        } catch (RuntimeException e) {
            System.out.println("Caught expected error: " + e.getMessage());
        }

        // Clean up the singleton so repeated runs start fresh
        ParkingLot.resetInstance();
    }
}