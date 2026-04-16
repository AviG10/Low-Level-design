package Ugly_Code;

public class TrafficLight {
    public String color;

    public TrafficLight(){
        this.color = "RED";
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void nextTrafficLight(){
        switch (color) {
            case "RED" -> {
                color = "GREEN";
                System.out.println("Change to GREEN. Cars go!");
            }
            case "GREEN" -> {
                color = "YELLOW";
                System.out.println("Change to YELLOW. Cars Slow Down!");
            }
            case "YELLOW" -> {
                color = "RED";
                System.out.println("Change to RED. Cars Stop!");
            }
            case "BLINKING" ->{
                color = "MAINTENANCE";
                System.out.println("Switching to MAINTENANCE Mode...");
            }
            case "MAINTENANCE" -> {
                color = "RED";
                System.out.println("MAINTENANCE done, Back to RED");
            }
            default -> throw new IllegalArgumentException("Invalid Traffic Light");
        }
    }
}
