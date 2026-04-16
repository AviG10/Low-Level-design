package Good_Code;

import Good_Code.TrafficLightStateImpl.BlinkingState;

public class StatePatternGoodMain {
    public static void main(String[] args) {
        TrafficLightContext trafficLight = new TrafficLightContext();
        trafficLight.nextTrafficLight(); // Green
        trafficLight.nextTrafficLight(); // yellow
        trafficLight.nextTrafficLight(); // Red

        trafficLight.setState(new BlinkingState());
        trafficLight.nextTrafficLight(); // Maintenance
        trafficLight.nextTrafficLight(); // RED
        trafficLight.nextTrafficLight(); // GREEN
    }
}
