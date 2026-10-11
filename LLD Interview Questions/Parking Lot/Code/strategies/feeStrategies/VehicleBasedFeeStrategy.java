package Code.strategies.feeStrategies;

import Code.enums.VehicleSize;
import Code.interfaces.FeeStrategy;
import Code.models.ParkingTicket;

import java.util.Map;

public class VehicleBasedFeeStrategy implements FeeStrategy {
    private final Map<VehicleSize, Double> ratePerHour;

    public VehicleBasedFeeStrategy(Map<VehicleSize, Double> ratePerHour) {
        this.ratePerHour = ratePerHour;
    }

    @Override
    public double calculateFee(ParkingTicket ticket){
        VehicleSize size = ticket.getVehicle().getSize();
        double rate =  ratePerHour.getOrDefault(size, 0.0);
        return ticket.getDurationInHours() * rate;
    }
}
