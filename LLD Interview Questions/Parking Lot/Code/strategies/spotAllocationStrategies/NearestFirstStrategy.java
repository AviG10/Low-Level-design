package Code.strategies.spotAllocationStrategies;

import Code.enums.VehicleSize;
import Code.interfaces.SpotAllocationStrategy;
import Code.models.ParkingFloor;
import Code.models.ParkingSpot;

import java.util.List;

public class NearestFirstStrategy implements SpotAllocationStrategy {

    @Override
    public ParkingSpot findSpot (List<ParkingFloor> floors, VehicleSize size){

        for(ParkingFloor floor : floors){
            ParkingSpot spot = floor.findAvailableSpot(size);

            if(spot != null){
                return spot;
            }
        }

        return null;
    }
}
