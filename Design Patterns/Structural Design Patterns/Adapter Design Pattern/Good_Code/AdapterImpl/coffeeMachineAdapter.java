package Good_Code.AdapterImpl;

import Good_Code.Devices.CoffeeMachine;
import Good_Code.Interfaces.SmartDevice;

public class coffeeMachineAdapter implements SmartDevice {
    private final CoffeeMachine coffeeMachine;

    public coffeeMachineAdapter(CoffeeMachine coffeeMachine) {
        this.coffeeMachine = coffeeMachine;
    }

    @Override
    public void turnOn(){
        coffeeMachine.initializeZigbeeConnection();
        coffeeMachine.startBrewing();
    }

    @Override
    public void turnOff(){
        coffeeMachine.stopBrewing();
        coffeeMachine.terminateZigbeeConnection();
    }
}
