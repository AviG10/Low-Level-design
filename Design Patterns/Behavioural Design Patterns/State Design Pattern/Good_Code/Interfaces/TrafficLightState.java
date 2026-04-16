package Good_Code.Interfaces;

import Good_Code.TrafficLightContext;

public interface TrafficLightState {
    void nextTrafficLight(TrafficLightContext context);
    String getColor();
}
