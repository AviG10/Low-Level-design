package Ugly_Code;

public class StatePatternUglyMain {
    public static void main(String[] args) {
        TrafficLight trafficLight = new TrafficLight();
        trafficLight.nextTrafficLight(); // Green
        trafficLight.nextTrafficLight(); // yellow
        trafficLight.nextTrafficLight(); // Red

        trafficLight.setColor("BLINKING");
        trafficLight.nextTrafficLight(); // Maintenance
        trafficLight.nextTrafficLight(); // RED
        trafficLight.nextTrafficLight(); // GREEN
    }
}
