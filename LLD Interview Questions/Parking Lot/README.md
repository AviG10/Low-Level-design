# Parking Lot Design

## Functional Requirements

1. Supports multi-floor parking, each floor with a configurable parking spots.
2. Supports multiple vehicle types like bike, car, truck.
3. Classify parking spot by size like SMALL, MEDIUM, LARGE and match then with appropriate vehicle type.
4. Automatically assign parking spot based on availability.
5. Issue a parking ticket on entry and mark the entry and exit times.
6. Calculate parking fees based on duration of stay and supports different pricing strategies like flat fee, hourly fees, vehicle type base fees etc.
7. Supports querying and displaying real-time availability of parking spots, groups by floor and spot size.

## Non-Functional Requirements

1. Implementation should follow OOPS principles.
2. System should handle concurrent entry/exit without race condition.
3. Implementation should be modular and extensible.

## Identifying Core Entities

1. **VehicleSize:** ENUM (SMALL, MEDIUM, LARGE)
2. **Vehicle:** Abstract Class (licensePlate, size)
3. **Bike, Car, Trucks:** Data Class extends abstracts class → concrete vehicle Types
4. **ParkingTicket:** Data Class (ID, vehicle, parkingSpot, entryTime, exitTime) → Tracks a particular parking session
5. **ParkingSpot:** Core Class (ID, size, vehicle) → Manage individual spot states
6. **ParkingFloor:** Core Class (ID, parkingSpots) → Group spots by floor
7. **ParkingLot:** Core Class (Singleton) (ParkingFloors, parkingTickets, feeStrategy, spotAllocationStrategy) → Orchestrates the entire system
8. **FeeStrategy:** Interface (calculateFee) → handle the fees calculation
9. **spotAllocationStrategy:** Interface (findSpot) → handle the vehicle allocation

### Core Challenges

1. Need to manage multiple parking floors and spot allocation efficiently.
2. Need to support vehicle-size-based matching and dynamic availability tracking.
3. Need to support real-time ticketing and fee calculations without race conditions.

### FAQs

#### Why to use ENUM for vehicleSize?
- because the develops cannot create a vehicleSize.EXTRA_LARGE accidentally also.
- It catches invalid size at compile-time, not runtime.

#### Why to use VehicleSize instead of VehicleType like Bike, Car, Trucks?
- Designing the system around vehicle size rather than specific vehicle types makes it easier to maintain, scale, and reason about.
- When you use VehicleType, you tie your logic to specific labels like Bike, Car, or Truck. This becomes a problem when a new vehicle type is introduced.
- For example, adding a new type like “Mini Truck” would require updating the allocation logic, billing logic, and compatibility checks.
- On the other hand, using VehicleSize keeps the focus on the space the vehicle occupies, which is the primary concern in a parking lot. Whether a vehicle is a motorcycle, scooter, or bike is less important than the amount of physical room it needs.
- This way, the core logic for allocating spots and calculating fees remains the same. You simply tag each new vehicle with the appropriate size and the system works without any change.

#### Why to use inheritance in Vehicle?
- The subclasses are a thin convenience layer, not a home for type-specific logic.
- Allocation and billing still key off vehicle size, so the earlier point about modeling by size rather than type still holds.
- The subclasses also make the code more readable: new Car("ABC-123") is clearer than new Vehicle("ABC-123", VehicleSize.MEDIUM).

#### Why to use interface rather than abstract class in FeeStrategy ?
- We use an interface rather than an abstract class because strategies have no shared state or default behavior.
- Each implementation is completely independent.

#### What are the downside for using Singleton Design Pattern in ParkingLot?
- difficulty in unit testing (hard to mock)
- hidden dependencies
- global state issues
- dependency injection is often preferred, but Singleton is appropriate when you genuinely need a single instance and DI isn't available
