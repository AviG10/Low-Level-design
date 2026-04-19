package Ugly_Code;

import java.util.Scanner;

public class AdapterPatternUglyMain {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("WelCome to SmartHome!");
        System.out.println("Available Devices are: AirConditioner, SmartLight, CoffeeMachine");

        while(true){
            System.out.println("Enter the device you want to control (or type 'exit' to quit): ");
            String deviceType = input.nextLine();
            if(deviceType.equalsIgnoreCase("exit")){
                System.out.println("Thank you for using SmartHome!");
                break;
            }
            else{
                SmartHomeController smartHome = new SmartHomeController(deviceType);
                smartHome.controlDevice();
            }
        }
    }
}
