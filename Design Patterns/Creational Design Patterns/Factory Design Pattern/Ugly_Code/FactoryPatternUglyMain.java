package Ugly_Code;

import Ugly_Code.VehicleTypes.Bike;
import Ugly_Code.VehicleTypes.Car;
import Ugly_Code.VehicleTypes.Truck;

import java.util.Scanner;

public class FactoryPatternUglyMain {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter Vehicle Type: ");

        String vehicleType = input.next();

        Vehicle vehicle;

        if(vehicleType.equalsIgnoreCase("Car")){
            vehicle = new Car();
        }
        else if(vehicleType.equalsIgnoreCase("Bike")){
            vehicle = new Bike();
        }
        else if(vehicleType.equalsIgnoreCase("Truck")){
            vehicle = new Truck();
        }
        else{
            throw new IllegalArgumentException("Invalid Vehicle Type");
        }

        vehicle.start();
        vehicle.stop();
    }
}
