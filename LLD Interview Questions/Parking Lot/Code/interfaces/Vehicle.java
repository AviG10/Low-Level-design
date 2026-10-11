package Code.interfaces;

import Code.enums.VehicleSize;

public abstract class Vehicle {
    private final String licensePlate;
    private final VehicleSize size;

    protected Vehicle(String licensePlate, VehicleSize size) {
        if(licensePlate == null || licensePlate.trim().isEmpty()){
            throw new IllegalArgumentException("License plate cannot be null or empty");
        }

        this.licensePlate = licensePlate;
        this.size = size;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public VehicleSize getSize() {
        return size;
    }
}
