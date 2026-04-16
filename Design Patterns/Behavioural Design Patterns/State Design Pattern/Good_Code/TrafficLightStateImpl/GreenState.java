package Good_Code.TrafficLightStateImpl;

import Good_Code.Interfaces.TrafficLightState;
import Good_Code.TrafficLightContext;

public class GreenState implements TrafficLightState {
    @Override
    public void nextTrafficLight(TrafficLightContext context) {
        System.out.println("Change to YELLOW. Cars Slow Down!");
        context.setState(new YellowState());
    }

    @Override
    public String getColor() {
        return "GREEN";
    }
}
