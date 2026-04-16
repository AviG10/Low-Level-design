package Good_Code.TrafficLightStateImpl;

import Good_Code.Interfaces.TrafficLightState;
import Good_Code.TrafficLightContext;

public class Maintanence implements TrafficLightState {
    @Override
    public String getColor() {
        return "MAINTENANCE";
    }

    @Override
    public void nextTrafficLight(TrafficLightContext context) {
        System.out.println("MAINTENANCE done, Back to RED");
        context.setState(new RedState());
    }
}
