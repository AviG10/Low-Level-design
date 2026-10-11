package Code.models;

import Code.enums.VehicleSize;
import Code.interfaces.Vehicle;

public class ParkingSpot {
    private final String spotId;
    private final VehicleSize size;
    private Vehicle parkedVehicle;

    public ParkingSpot(String spotId, VehicleSize size) {
        this.spotId = spotId;
        this.size = size;
    }

    public String getSpotId() {
        return spotId;
    }

    public VehicleSize getSize() {
        return size;
    }

    public boolean isAvailable(){
        return parkedVehicle == null;
    }

    public boolean canFitVehicle(VehicleSize vehicleSize){
        return vehicleSize.ordinal() <= this.size.ordinal();
    }

    public synchronized void parkVehicle(Vehicle vehicle) {
        if(!isAvailable()){
            throw  new RuntimeException("SpotId : "+ spotId +" is already occupied");
        }

        if(!canFitVehicle(vehicle.getSize())){
            throw  new RuntimeException("SpotId : "+ spotId +" cannot fit the vehicle of size "+ vehicle.getSize());
        }

        this.parkedVehicle = vehicle;
    }

    public synchronized void unparkVehicle() {
        if (isAvailable()) {
            throw new RuntimeException("Spot " + spotId + " is already empty");
        }

        this.parkedVehicle = null;
    }

}
