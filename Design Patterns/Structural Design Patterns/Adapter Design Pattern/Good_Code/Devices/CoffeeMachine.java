package Good_Code.Devices;

public class CoffeeMachine {
    public void initializeZigbeeConnection(){
        System.out.println("CoffeeMachine connect via Zigbee..");
    }

    public void terminateZigbeeConnection(){
        System.out.println("CoffeeMachine terminate from Zigbee..");
    }

    public void startBrewing(){
        System.out.println("CoffeeMachine start brewing");
    }

    public void stopBrewing(){
        System.out.println("CoffeeMachine stop brewing");
    }
}
