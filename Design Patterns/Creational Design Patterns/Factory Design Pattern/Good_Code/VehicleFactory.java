package Good_Code;

import Good_Code.VehicleTypes.Bike;
import Good_Code.VehicleTypes.Car;
import Good_Code.VehicleTypes.Truck;

public class VehicleFactory {
    public static Vehicle getVehicle(String vehicleType){
        if(vehicleType.equalsIgnoreCase("Car")){
            return new Car();
        }
        else if(vehicleType.equalsIgnoreCase("Truck")){
            return new Truck();
        }
        else if(vehicleType.equalsIgnoreCase("Bike")){
            return new Bike();
        }
        else{
            throw new IllegalArgumentException("Invalid Vehicle Type");
        }
    }
}
