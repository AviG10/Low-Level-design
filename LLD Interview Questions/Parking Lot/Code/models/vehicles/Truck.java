package Code.models.vehicles;

import Code.enums.VehicleSize;
import Code.interfaces.Vehicle;

public class Truck extends Vehicle {
    public Truck(String licensePlate) {
        super(licensePlate, VehicleSize.LARGE);
    }
}
