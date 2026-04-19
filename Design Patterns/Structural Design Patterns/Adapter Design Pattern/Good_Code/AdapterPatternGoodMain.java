package Good_Code;

import Good_Code.AdapterImpl.AirConditionerAdapter;
import Good_Code.AdapterImpl.SmartLightAdapter;
import Good_Code.AdapterImpl.coffeeMachineAdapter;
import Good_Code.Devices.AirConditioner;
import Good_Code.Devices.CoffeeMachine;
import Good_Code.Devices.SmartLight;
import Good_Code.Interfaces.SmartDevice;

public class AdapterPatternGoodMain {
    public static void main(String[] args) {
        SmartDevice airConditioner = new AirConditionerAdapter(new AirConditioner());
        SmartDevice smartLight = new SmartLightAdapter(new SmartLight());
        SmartDevice coffeeMachine = new coffeeMachineAdapter(new CoffeeMachine());

        airConditioner.turnOn();
        smartLight.turnOn();
        coffeeMachine.turnOn();
        smartLight.turnOff();
        coffeeMachine.turnOff();
        airConditioner.turnOff();
    }
}
