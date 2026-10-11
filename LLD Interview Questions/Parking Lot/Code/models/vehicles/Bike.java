package Code.models.vehicles;

import Code.enums.VehicleSize;
import Code.interfaces.Vehicle;

public class Bike extends Vehicle {
    public Bike(String licensePlate) {
        super(licensePlate, VehicleSize.SMALL);
    }
}
