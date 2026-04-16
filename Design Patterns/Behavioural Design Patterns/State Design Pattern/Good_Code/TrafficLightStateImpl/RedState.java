package Good_Code.TrafficLightStateImpl;

import Good_Code.Interfaces.TrafficLightState;
import Good_Code.TrafficLightContext;

public class RedState implements TrafficLightState {
    @Override
    public void nextTrafficLight(TrafficLightContext context) {
        System.out.println("Change to GREEN. Cars go!");
        context.setState(new GreenState());
    }

    @Override
    public String getColor() {
        return "RED";
    }
}
