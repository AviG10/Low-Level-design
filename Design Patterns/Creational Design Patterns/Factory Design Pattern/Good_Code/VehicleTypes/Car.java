package Good_Code.VehicleTypes;

import Good_Code.Vehicle;

public class Car implements Vehicle {
    public void start() {
        System.out.println("Car started");
    }
    public void stop() {
        System.out.println("Car stopped");
    }
}
