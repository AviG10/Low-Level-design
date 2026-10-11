package Code.strategies.feeStrategies;

import Code.interfaces.FeeStrategy;
import Code.models.ParkingTicket;

public class FlatRateFeeStrategy implements FeeStrategy {
    private final int flatFee;

    public FlatRateFeeStrategy(int flatFee) {
        this.flatFee = flatFee;
    }

    @Override
    public double calculateFee(ParkingTicket ticket) {
        return flatFee;
    }
}
