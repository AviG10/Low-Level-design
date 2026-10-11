package Code.interfaces;

import Code.enums.VehicleSize;
import Code.models.ParkingFloor;
import Code.models.ParkingSpot;

import java.util.List;

public interface SpotAllocationStrategy {
    ParkingSpot findSpot(List<ParkingFloor> floors, VehicleSize size);
}
