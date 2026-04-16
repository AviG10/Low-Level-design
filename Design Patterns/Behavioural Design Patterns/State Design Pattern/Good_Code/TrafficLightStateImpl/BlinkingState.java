package Good_Code.TrafficLightStateImpl;

import Good_Code.Interfaces.TrafficLightState;
import Good_Code.TrafficLightContext;

public class BlinkingState implements TrafficLightState {
    @Override
    public void nextTrafficLight(TrafficLightContext context) {
        System.out.println("Switching to MAINTENANCE Mode...");
        context.setState(new Maintanence());
    }

    @Override
    public String getColor() {
        return "MAINTENANCE";
    }
}
