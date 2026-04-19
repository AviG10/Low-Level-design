package Good_Code.AdapterImpl;

import Good_Code.Devices.AirConditioner;
import Good_Code.Interfaces.SmartDevice;

public class AirConditionerAdapter implements SmartDevice {
    private final AirConditioner airConditioner;

    public AirConditionerAdapter(AirConditioner airConditioner){
        this.airConditioner = airConditioner;
    }

    @Override
    public void turnOn(){
        airConditioner.connectViaBluetooth();
        airConditioner.startCooling();
    }

    @Override
    public void turnOff(){
        airConditioner.stopCooling();
        airConditioner.disconnectViaBluetooth();
    }
}
