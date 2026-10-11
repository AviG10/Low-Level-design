package Code.models;

import Code.interfaces.Vehicle;

import java.time.Duration;
import java.time.LocalDateTime;

public class ParkingTicket {
    private final String TicketId;
    private final Vehicle vehicle;
    private final ParkingSpot spot;
    private final LocalDateTime entryTime;
    private LocalDateTime exitTime;

    public ParkingTicket(String ticketId, Vehicle vehicle, ParkingSpot spot) {
        this.TicketId = ticketId;
        this.vehicle = vehicle;
        this.spot = spot;
        entryTime = LocalDateTime.now();
    }

    public void setExitTime(LocalDateTime exitTime) {
        this.exitTime = exitTime;
    }

    public String getTicketId() {
        return TicketId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public ParkingSpot getSpot() {
        return spot;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public double getDurationInHours() {
        LocalDateTime end = exitTime != null ? exitTime : LocalDateTime.now();
        long minutes  = Duration.between(entryTime, end).toMinutes();
        long hours = (minutes + 59) / 60;
        return Math.max(1, hours);
    }
}
