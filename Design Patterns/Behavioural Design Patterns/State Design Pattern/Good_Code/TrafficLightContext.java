package Good_Code;

import Good_Code.Interfaces.TrafficLightState;
import Good_Code.TrafficLightStateImpl.RedState;

public class TrafficLightContext {
    private TrafficLightState currentState;

    public TrafficLightContext() {
        currentState = new RedState();
    }

    public void nextTrafficLight() {
        currentState.nextTrafficLight(this);
    }

    public void setState(TrafficLightState state) {
        this.currentState = state;
    }

}
