package Good_Code.AdapterImpl;

import Good_Code.Devices.SmartLight;
import Good_Code.Interfaces.SmartDevice;

public class SmartLightAdapter implements SmartDevice {
    private final SmartLight smartLight;

    public SmartLightAdapter(SmartLight smartLight) {
        this.smartLight = smartLight;
    }

    @Override
    public void turnOn(){
        smartLight.connectToWifi();
        smartLight.switchOn();
    }

    @Override
    public void turnOff(){
        smartLight.switchOff();
        smartLight.disconnectFromWifi();
    }
}
