package Good_Code;

public class BuilderPatternGoodMain {
    public static void main(String[] args) {
        // Creating a car using builder pattern
        Car.CarBuilder builder = new Car.CarBuilder();
        Car car1 = builder.setEngine("v8")
                .setColor("Red")
                .setSeats(4)
                .setSunroof(true)
                .build();

        System.out.println(car1);

        Car car2 = builder.setEngine("v9")
                .setColor("white")
                .setSeats(6)
                .setSunroof(true)
                .setNavigationSystem(true)
                .build();

        System.out.println(car2);
    }
}
