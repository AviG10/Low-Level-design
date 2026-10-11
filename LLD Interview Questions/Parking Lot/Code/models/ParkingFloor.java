package Code.models;

import Code.enums.VehicleSize;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ParkingFloor {
    private final int floorNumber;
    private final List<ParkingSpot> spots;

    public ParkingFloor(int floorNumber, Map<VehicleSize, Integer> spotCounts) {
        this.floorNumber = floorNumber;
        this.spots = new ArrayList<>();

        initializeSpots(spotCounts);


    }

    private void initializeSpots(Map<VehicleSize, Integer> spotCounts) {
        // Create parking spots based on the provided counts
        int spotCounter = 1;
        for (VehicleSize size : VehicleSize.values()) {
            Integer count = spotCounts.get(size);
            if (count != null) {
                for (int i = 0; i < count; i++) {
                    String spotId = "F" + floorNumber + "-" + size.name().charAt(0) + String.format("%03d", spotCounter++);
                    spots.add(new ParkingSpot(spotId, size));
                }
            }
        }
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public List<ParkingSpot> getSpots() {
        return spots;
    }

    public ParkingSpot findAvailableSpot(VehicleSize size) {
        for(ParkingSpot spot : spots){
            if(spot.isAvailable() && spot.canFitVehicle(size)) {
                return spot;
            }
        }

        return null;
    }
}
