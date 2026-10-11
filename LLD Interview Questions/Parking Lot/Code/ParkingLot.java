package Code;

import Code.enums.VehicleSize;
import Code.interfaces.FeeStrategy;
import Code.interfaces.SpotAllocationStrategy;
import Code.interfaces.Vehicle;
import Code.models.ParkingFloor;
import Code.models.ParkingSpot;
import Code.models.ParkingTicket;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ParkingLot {
    private static volatile ParkingLot instance;

    private List<ParkingFloor> parkingFloors;
    private FeeStrategy feeStrategy;
    private SpotAllocationStrategy spotAllocationStrategy;
    private final Map<String, ParkingTicket> activeTickets;

    private ParkingLot(){
        this.activeTickets = new ConcurrentHashMap<>();
    }

    public static ParkingLot getInstance(){
        if(instance == null){
            synchronized (ParkingLot.class){
                if(instance == null){
                    instance = new ParkingLot();
                }
            }
        }
        return instance;
    }

    public static void resetInstance() {
        synchronized (ParkingLot.class) {
            instance = null;
        }
    }

    public void initialize(
            List<ParkingFloor> floors,
            FeeStrategy feeStrategy,
            SpotAllocationStrategy spotAllocationStrategy){
        this.parkingFloors = floors;
        this.feeStrategy = feeStrategy;
        this.spotAllocationStrategy = spotAllocationStrategy;
    }

    public void setFeeStrategy(FeeStrategy feeStrategy) {
        this.feeStrategy = feeStrategy;
    }

    public void setSpotAllocationStrategy(SpotAllocationStrategy spotAllocationStrategy) {
        this.spotAllocationStrategy = spotAllocationStrategy;
    }

    public ParkingTicket parkVehicle(Vehicle vehicle){

        ParkingSpot spot;

        // Retry if another thread claims the chosen spot before we do
        while (true){
            spot = spotAllocationStrategy.findSpot(parkingFloors, vehicle.getSize());

            if(spot == null){
                throw new RuntimeException("No available parking spot for vehicle size : " + vehicle.getSize());
            }

            try{
                spot.parkVehicle(vehicle);
                break;
            }
            catch(RuntimeException e){
                // Spot was taken between findSpot and parkVehicle; try again
                continue;
            }
        }

        String ticketId = UUID.randomUUID().toString().substring(0, 8);
        ParkingTicket ticket = new ParkingTicket(ticketId, vehicle, spot);
        activeTickets.put(ticketId, ticket);

        System.out.println("Parked " + vehicle + " at spot " + spot.getSpotId() +
                ". Ticket: " + ticketId);
        return ticket;
    }

    public void unparkVehicle(String ticketId) {

        // remove() is atomic on ConcurrentHashMap; only one caller wins the exit
        ParkingTicket ticket = activeTickets.remove(ticketId);

        if (ticket == null) {
            throw new RuntimeException("Invalid ticket: " + ticketId);
        }

        // Set exit time and calculate fee
        ticket.setExitTime(java.time.LocalDateTime.now());
        double fee = feeStrategy.calculateFee(ticket);

        // Free the spot
        ticket.getSpot().unparkVehicle();

        System.out.printf("Unparked %s from spot %s. Fee: $%.2f%n",
                ticket.getVehicle(), ticket.getSpot().getSpotId(), fee);
    }

    public void displayAvailability() {
        System.out.println("\n===== PARKING AVAILABILITY =====");
        for (ParkingFloor floor : parkingFloors) {
            StringBuilder sb = new StringBuilder();
            sb.append("Floor ").append(floor.getFloorNumber()).append(": ");

            for (VehicleSize size : VehicleSize.values()) {
                int count = 0;
                for (ParkingSpot spot : floor.getSpots()) {
                    if (spot.isAvailable() && spot.getSize() == size) {
                        count++;
                    }
                }
                sb.append(size).append("=").append(count);
                if (size.ordinal() < VehicleSize.values().length - 1) {
                    sb.append(", ");
                }
            }
            System.out.println(sb);
        }
        System.out.println("================================\n");
    }

}
