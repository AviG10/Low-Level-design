package Code.strategies.spotAllocationStrategies;

import Code.enums.VehicleSize;
import Code.interfaces.SpotAllocationStrategy;
import Code.models.ParkingFloor;
import Code.models.ParkingSpot;

import java.util.List;

public class BestFitStrategy implements SpotAllocationStrategy {

    @Override
    public ParkingSpot findSpot(List<ParkingFloor> floors, VehicleSize size) {

        for(ParkingFloor floor: floors){
            for(ParkingSpot spot: floor.getSpots()){
                if(spot.isAvailable() && spot.getSize() == size){
                    return spot;
                }
            }
        }

        return null;
    }
}
