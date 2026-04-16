package Good_Code.TrafficLightStateImpl;

import Good_Code.Interfaces.TrafficLightState;
import Good_Code.TrafficLightContext;

public class YellowState implements TrafficLightState {
    @Override
    public void nextTrafficLight(TrafficLightContext context) {
        System.out.println("Change to RED. Cars Stop!");
        context.setState(new RedState());
    }

    @Override
    public String getColor(){
        return "YELLOW";
    }
}
