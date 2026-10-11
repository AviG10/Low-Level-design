package Code.interfaces;

import Code.models.ParkingTicket;

public interface FeeStrategy {
    double calculateFee(ParkingTicket ticket);
}
