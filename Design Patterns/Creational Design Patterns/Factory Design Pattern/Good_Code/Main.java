package Good_Code;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter vehicle type");
        String vehicleType = input.next();

        Vehicle vehicle = VehicleFactory.getVehicle(vehicleType);
        vehicle.start();
        vehicle.stop();
    }
}
