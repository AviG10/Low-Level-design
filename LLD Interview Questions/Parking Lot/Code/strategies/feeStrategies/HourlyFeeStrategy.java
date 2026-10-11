package Code.strategies.feeStrategies;

import Code.interfaces.FeeStrategy;
import Code.models.ParkingTicket;

public class HourlyFeeStrategy implements FeeStrategy {
    private final double feePerHour;

    public HourlyFeeStrategy(double feePerHour) {
        this.feePerHour = feePerHour;
    }

    @Override
    public double calculateFee(ParkingTicket ticket) {
        return ticket.getDurationInHours() * feePerHour;
    }

}
