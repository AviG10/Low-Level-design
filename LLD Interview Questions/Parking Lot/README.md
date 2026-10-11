# 🅿️ Parking Lot Design

## Functional Requirements

| # | Requirement | Details |
|---|---|---|
| 1️⃣ | **Multi-Floor Support** | Each floor has configurable parking spots of different sizes |
| 2️⃣ | **Multiple Vehicle Types** | Support Bike, Car, Truck with automatic size-based allocation |
| 3️⃣ | **Spot Classification** | SMALL, MEDIUM, LARGE sizes; vehicle-to-spot matching |
| 4️⃣ | **Auto-Allocation** | Smart spot assignment based on availability & strategy |
| 5️⃣ | **Ticketing System** | Issue ticket on entry; track entry/exit times |
| 6️⃣ | **Dynamic Pricing** | Support multiple fee strategies (flat, hourly, vehicle-based) |
| 7️⃣ | **Real-Time Availability** | Display free spots per floor, grouped by size |

## Non-Functional Requirements

| # | Requirement | Constraint |
|---|---|---|
| ✅ | **OOPS Principles** | Inheritance, Polymorphism, Encapsulation, Abstraction |
| 🔒 | **Concurrency** | Handle multiple entry/exit gates without race conditions |
| 🔧 | **Extensibility** | Add new spot strategies, fee models without changing core |

---

## Identifying Core Entities

### Architecture Components

```
┌─────────────────────────────────────────────────────────────────────┐
│                   PARKING LOT SYSTEM COMPONENTS                     │
├─────────────────────────────────────────────────────────────────────┤
│                                                                     │
│  🏢 Core Management                                                │
│  ├─ VehicleSize ENUM      → {SMALL, MEDIUM, LARGE}               │
│  ├─ Vehicle (Abstract)    → licensePlate, size                    │
│  │  ├─ Bike              → VehicleSize.SMALL                      │
│  │  ├─ Car               → VehicleSize.MEDIUM                     │
│  │  └─ Truck             → VehicleSize.LARGE                      │
│  │                                                                 │
│  🎫 Ticketing System                                              │
│  ├─ ParkingTicket        → ID, vehicle, spot, entry/exit times   │
│  └─ ParkingSpot          → ID, size, occupancy status            │
│                                                                     │
│  🏗️ Floor Organization                                             │
│  ├─ ParkingFloor         → floor#, list of spots                 │
│  └─ ParkingLot (Singleton) → all floors, orchestrator            │
│                                                                     │
│  🎯 Strategy Pattern (Extensibility)                              │
│  ├─ FeeStrategy          → calculateFee(ticket)                  │
│  │  ├─ HourlyFeeStrategy                                         │
│  │  ├─ FlatRateFeeStrategy                                       │
│  │  └─ VehicleBasedFeeStrategy                                   │
│  │                                                                 │
│  ├─ SpotAllocationStrategy → findSpot(floors, size)             │
│  │  ├─ NearestFirstStrategy                                      │
│  │  └─ BestFitStrategy                                           │
│  │                                                                 │
│  └─ Exceptions           → ParkingException, PaymentException   │
│                                                                     │
└─────────────────────────────────────────────────────────────────────┘
```

### Core Design Challenges

| Challenge | Impact | Solution |
|-----------|--------|----------|
| **Efficient spot allocation** | Need O(1) or O(n) lookup for available spots | Use floor-based indexing + strategy pattern |
| **Vehicle-size matching** | SMALL bikes, MEDIUM cars fit small spots; LARGE trucks need special | VehicleSize enum + spot compatibility checks |
| **Real-time availability** | Drivers need instant feedback on free spots | Maintain count per floor, update on park/unpark |
| **Dynamic pricing** | Different vehicles, durations, times → different fees | Strategy pattern for pluggable pricing |
| **Concurrent access** | Multiple gates = multiple threads accessing shared state | Synchronized methods + atomic operations |

### Design Rationale & FAQs

#### ❓ Why ENUM for VehicleSize?

```
❌ Problem: String-based sizes
   VehicleSize size = "SMALL";  // Typo not caught!
   VehicleSize size = "EXTRA_LARGE";  // Runtime error ❌

✅ Solution: ENUM
   VehicleSize size = VehicleSize.SMALL;  // Compile-time checked ✅
   - Can't create invalid values
   - Catches errors at compile-time
   - Type-safe
   - Self-documenting
```

#### ❓ Why VehicleSize instead of VehicleType (Bike/Car/Truck)?

```
Design Philosophy: Model by Resource (Space), Not Label (Type)

❌ Type-Based: Adding MiniTruck requires code changes everywhere!
✅ Size-Based: Set getSize() → MEDIUM, system works without changes!

Key Insight: Focus on space needed, not vehicle model
              New vehicle types just need new size assignments
```

#### ❓ Why Inheritance for Vehicle (Car, Bike, Truck)?

```
✅ Convenience Layer:
   new Car("ABC-123")  ← Clear and concise
   vs.
   new Vehicle("ABC-123", VehicleSize.MEDIUM)  ← Verbose

✅ Future Extension:
   class HandicapCar extends Car {
       getAccessibility() { return AccessLevel.HANDICAP; }
   }
```

#### ❓ Why Interfaces for Strategies?

```
FeeStrategy & SpotAllocationStrategy as interfaces, not abstract classes

Reason: No shared state or default behavior

Interface Benefits:
  • Each implementation 100% independent
  • Just defines contract (method signature)
  • Cleaner composition
  • Strategies evolve independently
```

#### ❓ Why Singleton for ParkingLot?

```
A parking lot has ONE operational instance (per system)

✅ Benefits:
   • Global single point of control
   • Ensures consistency
   • Real-world model match

❌ Trade-offs:
   • Hidden dependencies
   • Hard to mock for testing
   • Global state

⚖️  For interviews: Singleton is acceptable
    For production: Dependency injection is preferred
```

---

## High-Level Code Explanation and Interaction Flow

### Architecture Overview

```
┌─────────────────────────────────────────────────────────────────┐
│                        ParkingLot (Singleton)                   │
│  ─────────────────────────────────────────────────────────────  │
│  • Orchestrates entire system                                    │
│  • Delegates spot allocation via SpotAllocationStrategy         │
│  • Delegates fee calculation via FeeStrategy                    │
│  • Manages active parking tickets in ConcurrentHashMap          │
│  • Handles concurrent park/unpark with retry logic              │
└─────────────────────────────────────────────────────────────────┘
        │                   │                        │
        ▼                   ▼                        ▼
    ┌─────────┐       ┌──────────────┐        ┌──────────┐
    │ Floors  │       │  Strategies  │        │ Tickets  │
    │ (N)     │       │              │        │ (Active) │
    └─────────┘       ├─ Allocation  │        └──────────┘
        │             ├─ FeeCalc     │
        │             └──────────────┘
        ▼
    ┌──────────────────────────────┐
    │   ParkingFloor (per floor)   │
    │  ────────���───────────────────│
    │  • Manages ParkingSpots      │
    │  • Groups by vehicle size    │
    │  • Spot IDs: F{floor}-{size} │
    └──────────────────────────────┘
        │
        └─────┬─────┬────────┐
              ▼     ▼        ▼
           ┌─────────────────────┐
           │  ParkingSpot (Sync) │
           │  ──────────────────│
           │  • Tracks occupancy │
           │  • Size-based match │
           │  • Thread-safe lock │
           │  • Guards state     │
           ���─────────────────────┘
                  │
              owns─▼
           ┌────────────────┐
           │ Vehicle parked │
           └────────────────┘
```

### Step-by-Step Interaction Flow

#### **1. Initialization Phase**
```
Developer
    │
    └─→ ParkingLot.getInstance()                    [Singleton]
         │
         └─→ lot.initialize(floors, feeStrategy, spotStrategy)
              ├─ Stores list of ParkingFloors
              ├─ Registers FeeStrategy (e.g., HourlyFeeStrategy)
              └─ Registers SpotAllocationStrategy (e.g., NearestFirstStrategy)
```

**Key Point:** Each `ParkingFloor` is initialized with a `Map<VehicleSize, Integer>`, which triggers creation of all `ParkingSpot` objects with unique IDs like `F1-S001`, `F1-M002`, `F1-L003`, etc.

---

#### **2. Vehicle Parking Flow**
```
Vehicle Arrives
    │
    └─→ lot.parkVehicle(new Car("KA-02-2222"))
         │
         1️⃣ Extract vehicle size (MEDIUM for Car)
         │
         2️⃣ SpotAllocationStrategy.findSpot(floors, MEDIUM)
         │  • NearestFirstStrategy: scans floors sequentially
         │  • BestFitStrategy: finds exact size match
         │  → Returns ParkingSpot reference (or null if unavailable)
         │
         3️⃣ Attempt to claim spot (Atomic Operation)
         │
         └─→ spot.parkVehicle(vehicle)  [SYNCHRONIZED]
             ├─ Check: spot.isAvailable() == true?
             ├─ Check: spot.canFitVehicle(MEDIUM) == true?
             └─ Claim: spot.parkedVehicle = vehicle
                
         4️⃣ If claimed successfully:
         │  ├─ Generate UUID-based ticket ID (8 chars)
         │  ├─ Create ParkingTicket(id, vehicle, spot, entryTime)
         │  ├─ Store in activeTickets map (ConcurrentHashMap)
         │  └─ Return ticket to driver
         │
         5️⃣ If spot already taken (race condition):
            ├─ Catch RuntimeException
            └─ Retry loop: ask for another available spot
```

**Critical Detail:** The retry loop in `parkVehicle()` handles races where two threads select the same spot simultaneously. The spot's synchronized lock ensures only one succeeds; others retry.

---

#### **3. Vehicle Exit and Billing Flow**
```
Driver Exits (Has ticket ID: "e85bc4a2")
    │
    └─→ lot.unparkVehicle("e85bc4a2")
         │
         1️⃣ Atomic ticket removal (wins ownership, only once)
         │
         └─→ activeTickets.remove(ticketId)  [ATOMIC - ConcurrentHashMap]
             ├─ If not found: throw "Invalid ticket" error → DONE
             └─ If found: continue (only one thread reaches here for same ticket)
         │
         2️⃣ Record exit time
         │
         └─→ ticket.setExitTime(LocalDateTime.now())
         │
         3️⃣ Calculate duration and fee
         │
         └─→ fee = feeStrategy.calculateFee(ticket)
             ├─ HourlyFeeStrategy: (exitTime - entryTime) × hourlyRate
             ├─ FlatRateFeeStrategy: fixed fee
             └─ VehicleBasedFeeStrategy: rate by vehicle size
         │
         4️⃣ Free the spot (Atomic Operation)
         │
         └─→ spot.unparkVehicle()  [SYNCHRONIZED]
             └─ spot.parkedVehicle = null
         │
         5️⃣ Print confirmation and fee
```

**Critical Detail:** Using `remove()` as the decision point ensures exactly one request per ticket wins. Retries get "invalid ticket," preventing double charges.

---

#### **4. Availability Display Flow**
```
Query Display Board
    │
    └─→ lot.displayAvailability()
         │
         └─→ For each floor:
              └─ For each size (SMALL, MEDIUM, LARGE):
                  └─ Count spots where spot.isAvailable() == true
                     ├─ Read parkedVehicle field (volatile for safety)
                     └─ Increment count if null
         │
         └─→ Print human-readable summary:
             "Floor 1: SMALL=2, MEDIUM=1, LARGE=0"
```

**Note:** This read is eventual-consistent; it may show a spot as free an instant before a gate claims it. This is acceptable and avoids locking every gate behind a display refresh.

---

### Component Responsibilities

| Component | Responsibility | Thread Safety |
|-----------|-----------------|----------------|
| **ParkingLot** | Orchestrates parking/unparking, manages tickets | Retry loop + ConcurrentHashMap |
| **ParkingFloor** | Groups and indexes spots by size | Immutable list (read-only after init) |
| **ParkingSpot** | Tracks occupancy, enforces size rules | Synchronized methods + volatile field |
| **ParkingTicket** | Tracks session (entry, exit, duration) | Ownership via atomic remove in lot |
| **Vehicle** | Encodes type and size | Immutable |
| **FeeStrategy** | Computes parking fee | Stateless (thread-safe by design) |
| **SpotAllocationStrategy** | Finds best available spot | Stateless (thread-safe by design) |

---

## Concurrency and Thread Safety

### Overview

A parking lot with multiple entry/exit gates processes requests concurrently. All gates access shared state: spot occupancy and the active-ticket map. Without proper synchronization, race conditions corrupt state.

### The Three Concurrency Concerns

---

#### **Concern 1: Two Vehicles Assigned the Same Spot** ⚠️ **HIGH RISK**

**Scenario:**
- Two vehicles arrive simultaneously at Gate 1 and Gate 2
- Both need a MEDIUM spot
- Only one MEDIUM spot remains: `F1-M001`

**Without Synchronization:**
```
Timeline (Interleaved):
─────────────────────────────────────────────────────────
Time  │ Gate 1 Thread              │ Gate 2 Thread
─────────────────────────────────────────────────────────
T1    │ Scan floors               │
      │ F1-M001 is FREE ✓         │
      │                            │ Scan floors
T2    │                            │ F1-M001 is FREE ✓ (Gate 1 hasn't claimed yet!)
      │                            │
T3    │ Lock F1-M001              │
      │ Mark: parkedVehicle=Car1  │
      │ Unlock                    │
      │                            │ Lock F1-M001
T4    │ Issue Ticket1: F1-M001    │ Mark: parkedVehicle=Car2 (OVERWRITES Car1!)
      │                            │ Unlock
T5    │ Return Ticket1            │ Issue Ticket2: F1-M001
      │ ✓ Success                 │ ✓ Success (ERROR!)
─────────────────────────────────────────────────────────

Result:
❌ Two tickets for the same spot
❌ Spot points to Car2 only
❌ Car1's ticket is invalid (system no longer owns Car1)
❌ When Car1 exits: can't find the spot (state corrupted)
```

**With Synchronization (synchronized method on ParkingSpot):**
```
Timeline (With Lock on parkVehicle):
─────────────────────────────────────────────────────────
Time  │ Gate 1 Thread              │ Gate 2 Thread
─────────────────────────────────────────────────────────
T1    │ Scan floors               │
      │ F1-M001 is FREE ✓         │
      │                            │ Scan floors
T2    │                            │ F1-M001 is FREE ✓
      │ [LOCK F1-M001]            │
      │ parkVehicle() ENTERS lock │
      │ (exclusive entry)          │
T3    │ Check: still free? YES ✓  │ [WAIT FOR LOCK]
      │ Mark: parkedVehicle=Car1  │ (blocked, no entry)
      │ [UNLOCK F1-M001]          │
T4    │ Return                    │ [LOCK F1-M001]
      │ ✓ Success                 │ parkVehicle() ENTERS lock
      │                            │
T5    │                            │ Check: still free? NO ✗
      │                            │ Throw RuntimeException
      │                            │ [UNLOCK F1-M001]
T6    │                            │ Catch: retry logic
      │                            │ Ask strategy for another spot...
─────────────────────────────────────────────────────────

Result:
✅ Only Car1 claimed F1-M001
✅ Gate 2 caught the failure
✅ Retry loop finds another available spot for Car2
✅ No state corruption
```

**Code Guard:**
```java
public synchronized void parkVehicle(Vehicle vehicle) {
    if (!isAvailable()) {
        throw new RuntimeException("Spot " + spotId + " is already occupied");
    }
    // Atomic: check + claim under one lock
    this.parkedVehicle = vehicle;
}
```

**Lesson:** Lock the data where it lives. Each spot locks its own occupancy. The allocation logic is stateless; the lock is on the resource being claimed.

---

#### **Concern 2: Same Ticket Exits Twice** ⚠️ **MEDIUM RISK**

**Scenario:**
- A driver exits with ticket `"abc123"`
- Network retry or user error submits the same exit request twice within milliseconds
- Both requests reach `unparkVehicle(ticketId)` on the shared lot

**Without Synchronization:**
```
Timeline (Interleaved):
─────────────────────────────────────────────────────────
Time  │ Exit Request #1            │ Exit Request #2
─────────────────────────────────────────────────────────
T1    │ Look up ticket "abc123"    │
      │ Found! ticket object       │
      │ stamped exitTime           │
      │ computed fee=$10.00        │
      │                            │ Look up ticket "abc123"
T2    │                            │ Found! (Request #1 hasn't removed yet)
      ���                            │ stamped exitTime (again)
      │ Free spot F1-M001          │ computed fee=$10.00 (DUPLICATE CHARGE!)
      │ Remove ticket from map     │
T3    │ ✓ Exit complete            │ Free spot F1-M001 (already freed!)
      │                            │ RuntimeException: "Spot already empty"
      │                            │ ❌ Error after double-charging
─────────────────────────────────────────────────────────

Result:
❌ Driver charged twice
❌ Second request errors out mid-exit
❌ Spot state inconsistent (multiple unparkVehicle calls)
```

**With Synchronization (atomic remove on ConcurrentHashMap):**
```
Timeline (With Atomic Remove-and-Check):
─────────────────────────────────────────────────────────
Time  │ Exit Request #1            │ Exit Request #2
─────────────────────────────────────────────────────────
T1    │ ATOMIC: remove("abc123")   │
      │ ✓ Ticket removed           │
      │ (ONLY this request wins)    │
      │ stamp exitTime             │
T2    │ compute fee=$10.00         │ ATOMIC: remove("abc123")
      │ Free spot F1-M001          │ ✗ Already removed (null return)
      │ ✓ Exit complete            │ Throw "Invalid ticket: abc123"
T3    │                            │ No fee, no spot freed
      │                            │ ❌ Clean error (idempotent)
─────────────────────────────────────────────────────────

Result:
✅ Driver charged once only
✅ Spot freed once only
✅ Second request gets clear "invalid ticket" error
✅ No state corruption
✅ Exit is idempotent (replaying has no side effect)
```

**Code Guard:**
```java
public double unparkVehicle(String ticketId) {
    // remove() is ATOMIC on ConcurrentHashMap
    // Only one caller wins; others get null
    ParkingTicket ticket = activeTickets.remove(ticketId);
    
    if (ticket == null) {
        throw new RuntimeException("Invalid ticket: " + ticketId);
    }
    
    // Only the winner reaches here
    ticket.setExitTime(LocalDateTime.now());
    double fee = feeStrategy.calculateFee(ticket);
    ticket.getSpot().unparkVehicle();  // Guaranteed safe
    return fee;
}
```

**Lesson:** Make the critical decision (ownership of the exit) atomic and early. Use `ConcurrentHashMap.remove()`, which atomically checks and removes in one step. Everything after is single-threaded per ticket.

---

#### **Concern 3: Stale Availability Counts** ℹ️ **LOW RISK** (Acceptable)

**Scenario:**
- A display board refreshes the free-spot count once per second
- At the exact moment of refresh, the last MEDIUM spot is being claimed at a gate

**Without Synchronization:**
```
Timeline:
──────────────────────────────────────────────────────
Time  │ Display Thread             │ Gate Thread
──────────────────────────────────────────────────────
T1    │ Start counting             │
      │ Read F1-M001.isAvailable() │
      │ TRUE ✓ (count=1)           │
      │                            │ Park vehicle on F1-M001
T2    │                            │ Lock F1-M001
      │ Finish count               │ parkedVehicle = Car
      │ Display: MEDIUM=1 FREE     │ Unlock F1-M001
      │                            │
T3    │                            │
      │ Board shows FREE ✓         │ F1-M001 is now OCCUPIED
──────────────────────────────────────────────────────

Result:
Driver sees: "1 MEDIUM spot available"
Driver arrives to find: MEDIUM spot taken
⚠️ Mismatch, but no corruption!
    → Actual allocation is still protected by spot lock
    → No ticket duplicated
    → No spot state corrupted
    → Count is just stale (eventually consistent)
```

**Design Decision:** Accept eventual consistency for reads.

**Code Guard:**
```java
public class ParkingSpot {
    // volatile keyword: guarantees readers see latest write without a lock
    private volatile Vehicle parkedVehicle;

    public boolean isAvailable() {
        // Lock-free atomic read
        return parkedVehicle == null;
    }
}
```

**displayAvailability() does not lock the lot; it tolerates stale counts:**
```java
public void displayAvailability() {
    for (ParkingFloor floor : parkingFloors) {
        for (ParkingSpot spot : floor.getSpots()) {
            if (spot.isAvailable() && spot.getSize() == size) {
                count++;  // Lock-free read of each spot
            }
        }
    }
}
```

**Lesson:** Not everything needs strong consistency. Reads that do not drive critical decisions can be eventually consistent. Avoid locking the whole lot for a display refresh—that would serialize every gate behind the display, killing throughput.

---

### Thread-Safety Rules Applied

| Concern | Protected By | Mechanism | Strength |
|---------|--------------|-----------|----------|
| **Spot occupancy** | `synchronized` on `parkVehicle()`/`unparkVehicle()` | Mutual exclusion lock | Strong |
| **Ticket ownership** | `ConcurrentHashMap.remove()` | Atomic CAS (compare-and-swap) | Strong |
| **Availability display** | `volatile` field + lock-free reads | Visibility guarantee | Weak (eventually-consistent) |
| **Retry loop in parkVehicle()** | Catch + retry | Optimistic concurrency | Strong (retries guarantee progress) |

---

### Summary: Where the Locks Are

```
┌─────────────────────────────────────────────────────────────┐
│                  Concurrency & Locks                        │
├─────────────────────────────────────────────────────────────┤
│                                                              │
│  ParkingLot                                                 │
│  ├─ activeTickets: ConcurrentHashMap                        │
│  │  └─ remove(id) is ATOMIC → prevents double-exit         │
│  │                                                           │
│  └─ parkVehicle() RETRY LOGIC                               │
│     └─ Handles race on spot claim by retrying              │
│                                                              │
│  ParkingSpot                                                │
│  ├─ parkVehicle(): SYNCHRONIZED METHOD                      │
│  │  └─ Mutual exclusion: check + claim atomic             │
│  │                                                           │
│  ├─ unparkVehicle(): SYNCHRONIZED METHOD                    │
│  │  └─ Mutual exclusion: unpark atomic                     │
│  │                                                           │
│  └─ parkedVehicle: VOLATILE FIELD                           │
│     └─ Guarantees readers see latest write                 │
│                                                              │
└─────────────────────────────────────────────────────────────┘
```

---

### Key Takeaways

✅ **Lock where data lives:** Each `ParkingSpot` owns its occupancy and locks there.

✅ **Make decisions atomic:** Use `ConcurrentHashMap.remove()` to make ticket-removal + fee-calculation atomic.

✅ **Retry on failure:** The `parkVehicle()` loop catches spot-claim failures and asks for another spot.

✅ **Tolerate eventual consistency:** Display reads do not lock; they show best-effort counts.

✅ **Avoid global locks:** No "lot-wide" lock that serializes all gates behind the display.

✅ **Minimize critical sections:** Locks held only as long as necessary (check + claim for spots, remove for tickets).

✅ **Use appropriate tools:** `synchronized` for mutual exclusion, `volatile` for visibility, `ConcurrentHashMap` for atomic operations.
