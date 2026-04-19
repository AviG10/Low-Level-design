package Ugly_Code;

public class SmartHomeController {
    private final String deviceType;

    public SmartHomeController(String deviceType){
        this.deviceType = deviceType;
    }

    public void controlDevice(){
        if(deviceType.equalsIgnoreCase("AirConditioner")){
            System.out.println("Connecting to AirConditioner via Bluetooth..");
        }
        else if(deviceType.equalsIgnoreCase("SmartLight")){
            System.out.println("Connecting to SmartLight via Wi-Fi..");
        }
        else if(deviceType.equalsIgnoreCase("CoffeeMachine")){
            System.out.println("Connecting to CoffeeMachine via Zigbee..");
        }
        else {
            System.out.println("Invalid device type");
        }
    }
}
