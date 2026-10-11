package Code.models.vehicles;

import Code.enums.VehicleSize;
import Code.interfaces.Vehicle;

public class Car extends Vehicle {
    public Car(String licensePlate) {
        super(licensePlate, VehicleSize.MEDIUM);
    }
}
