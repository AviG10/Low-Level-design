package Good_Code;

public class Car {
    private final String engine;
    private final int wheels;
    private final int seats;
    private final String color;
    private final boolean sunroof;
    private final boolean navigationSystem;

    private Car(CarBuilder builder){
        this.engine = builder.engine;
        this.wheels = builder.wheels;
        this.seats = builder.seats;
        this.color = builder.color;
        this.sunroof = builder.sunroof;
        this.navigationSystem = builder.navigationSystem;
    }

    //Getters method for the fields
    public String getEngine() {
        return engine;
    }
    public int getWheels() {
        return wheels;
    }
    public int getSeats() {
        return seats;
    }
    public String getColor() {
        return color;
    }
    public boolean hasSunroof() {
        return sunroof;
    }
    public boolean hasNavigationSystem() {
        return navigationSystem;
    }

    @Override
    public String toString() {
        return "Car [engine=" + engine + ", wheels=" + wheels
                + ", seats=" + seats + ", color=" + color
                + ", sunroof=" + sunroof + ", navigationSystem="
                + navigationSystem + "]";
    }

    public static class CarBuilder{
        private String engine;
        private int wheels = 4;
        private int seats = 5;
        private String color = "Black";
        private boolean sunroof = false;
        private boolean navigationSystem = false;

        //Builder Method to set attribute
        public CarBuilder setEngine(String engine) {
            this.engine = engine;
            return this;
        }
        public CarBuilder setWheels(int wheels) {
            this.wheels = wheels;
            return this;
        }
        public CarBuilder setSeats(int seats) {
            this.seats = seats;
            return this;
        }
        public CarBuilder setColor(String color) {
            this.color = color;
            return this;
        }
        public CarBuilder setSunroof(boolean sunroof) {
            this.sunroof = sunroof;
            return this;
        }
        public CarBuilder setNavigationSystem(boolean navigationSystem) {
            this.navigationSystem = navigationSystem;
            return this;
        }

        //Build method to create a car object
        public Car build(){
            return new Car(this); // return a new car created using the builder's value
        }
    }
}
