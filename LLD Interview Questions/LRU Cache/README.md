# 🔄 LRU Cache Design

## Functional Requirements

| # | Requirement | Description |
|---|-------------|-------------|
| 1️⃣ | **get(key)** | Return the value if exists, otherwise return null/−1 |
| 2️⃣ | **put(key, value)** | Insert a new key or update the value of existing key |
| 3️⃣ | **Capacity Management** | Auto-remove least recently used item when capacity exceeded |
| 4️⃣ | **Recency Updates** | Both `get()` and `put()` must update recency timestamp |
| 5️⃣ | **Generic Support** | Support generic key-value pairs: `<K, V>` |

## Non-Functional Requirements

| # | Requirement | Constraint |
|---|---|---|
| ⚡ | **Performance** | Both `get()` and `put()` must run in **O(1)** time |
| 🔒 | **Concurrency** | Must be **thread-safe** in multi-threaded environments |

## Identifying Core Entities

Unlike other systems, the design of LRU cache is centered around choosing the **right data structures** and internal abstractions to achieve O(1) performance.

### Core Challenges

| Challenge | Impact | Severity |
|-----------|--------|----------|
| **Fast key-based lookup** | Need to find items by key instantly | ⚠️ CRITICAL |
| **Fast ordering maintenance** | Need to track recency and evict LRU item instantly | ⚠️ CRITICAL |

### Data Structure Decision Tree

```
┌─────────────────────────────────────────────────────────────────┐
│        LRU Cache Data Structure Selection                       │
└─────────────────────────────────────────────────────────────────┘

NEED: Fast Lookup (O(1))
├─ ✅ HashMap     → get/put in O(1)
├─ ❌ Array       → lookup in O(1), but insert/delete O(n)
└─ ❌ Linked List → lookup in O(n)

NEED: Fast Ordering & Eviction (O(1))
├─ ❌ HashMap alone    → no order tracking; eviction is O(n)
├─ ❌ Array            → reordering requires shifting O(n)
├─ ❌ Singly LL        → can't move to front without prev O(n)
└─ ✅ Doubly LL       → move to front O(1), remove tail O(1)

VERDICT:
┌─────────────────────────────────────────────────┐
│  HashMap + Doubly Linked List = Perfect Combo   │
│                                                  │
│  • HashMap: Fast key lookup (O(1))              │
│  • Doubly LL: Ordered tracking (O(1))           │
│  • Together: Complete LRU solution (O(1) all)   │
└─────────────────────────────────────────────────┘
```

### FAQs

#### ❓ Why not HashMap alone?
```
❌ Problem: HashMap doesn't maintain recency order
   - Can access in O(1) ✓
   - But finding LRU item requires scanning all entries O(n) ✗
   - Eviction becomes O(n), breaking O(1) requirement ✗

✅ Solution: Combine with Doubly Linked List
```

#### ❓ Why not Array?
```
❌ Problem: Array reordering is expensive
   - Insert/remove in middle requires shifting all elements
   - Moving an item to front = O(n) shifts ✗
```

#### ❓ Why not Singly Linked List?
```
❌ Problem: No backward reference (prev)
   - To move node to front, you need reference to previous node
   - But finding previous node requires O(n) scan ✗

✅ Solution: Use Doubly Linked List with prev/next pointers
```

#### ❓ Why use Doubly Linked List?
```
✅ Benefits with direct node reference:
   • Move to front:     O(1) [just update pointers]
   • Remove from tail:  O(1) [tail points to LRU node]
   • Remove any node:   O(1) [if we have its reference]
   
⚡ Key: HashMap stores the node reference, eliminating need to search
```

#### ❓ How to get direct node reference without traversing?
```
🎯 Answer: HashMap!

HashMap<K, Node<K,V>> map;

map.get(key)  → gives O(1) reference to the node
             → then manipulate pointers in O(1)
```

### Architecture Overview

```
┌──────────────────────────────────────────────────────────────┐
│                    LRU Cache Architecture                    │
├──────────────────────────────────────────────────────────────┤
│                                                               │
│  ┌────────────────────────────────────────────────────────┐  │
│  │            HashMap<K, Node<K,V>>                       │  │
│  │  ─────────────────────────────────────────────────────  │  │
│  │  • Fast O(1) lookup by key                            │  │
│  │  • Stores reference to node in Doubly LL             │  │
│  │                                                        │  │
│  │   Key    │  Value (Node Reference)                   │  │
│  │  ──────────────────────────────────────────          │  │
│  │   "a"    │  ┌──────────────────┐                    │  │
│  │   "b"    │  │ prev ← Node ↔ next│                   │  │
│  │   "c"    │  │ (in Doubly LL)   │                    │  │
│  │          │  └──────────────────┘                    │  │
│  └────────────────────────────────────────────────────────┘  │
│           ▲                                                   │
│           │ (references)                                     │
│           │                                                   │
│  ┌────────────────────────────────────────────────────────┐  │
│  │      Doubly Linked List (Recency Order)               │  │
│  │  ─────────────────────────────────────────────────────  │  │
│  │                                                        │  │
│  │   HEAD (Most Recently Used)                          │  │
│  │     ▼                                                 │  │
│  │   ┌───────────┐    ┌───────────┐    ┌───────────┐   │  │
│  │   │ "c": val3 │◄──►│ "a": val1 │◄──►│ "b": val2 │   │  │
│  │   └───────────┘    └───────────┘    └───────────┘   │  │
│  │                                            ▲         │  │
│  │                              TAIL (Least Recently Used) │  │
│  │                                                        │  │
│  └────────────────────────────────────────────────────────┘  │
│                                                               │
│  💡 Key Insight:                                            │
│     Head = MRU (accessed/inserted recently)                 │
│     Tail = LRU (not accessed for longest time)              │
│                                                               │
└──────────────────────────────────────────────────────────────┘
```

### Core Components

| Component | Purpose | Time Complexity |
|-----------|---------|-----------------|
| **HashMap** | Fast key lookup | O(1) get/put |
| **Doubly Linked List** | Maintain access order | O(1) move/remove |
| **Node** | Cache entry + LL node | O(1) pointer update |

### Required Classes

#### 1. 📌 Node Class: Cache Entry + Linked List Node

An internal class representing entries in the cache and a node in the Doubly Linked List.

```
┌──────────────────────────────────┐
│        Node<K, V>                │
├──────────────────────────────────┤
│ Fields:                          │
│  • K key          [cache key]    │
│  • V value        [cached value] │
│  • prev: Node     [LL pointer]   │
│  • next: Node     [LL pointer]   │
├──────────────────────────────────┤
│ Dual Role:                       │
│  ✓ Stores cache data             │
│  ✓ Part of doubly linked list    │
└──────────────────────────────────┘
```

**Why store key in the Node?**
- When removing LRU item from tail, must delete from HashMap too
- HashMap.remove(key) requires the key
- Therefore node must remember its key for cleanup

#### 2. 🔗 DoublyLinkedList Class: Recency Tracker

Maintains access order with dummy head and tail for edge-case handling.

```
┌─────────────────────────────────────────────────────┐
│      DoublyLinkedList<K, V> Structure               │
├─────────────────────────────────────────────────────┤
│                                                      │
│ Head (Dummy)  ◄──────────────────────────►  Tail (Dummy) │
│      ▼                                            ▲      │
│   ┌────┐      ┌────┐      ┌────┐            ┌────┐      │
│   │[*] ├─────►│Node│  ... │Node│◄──────────┤[*] │      │
│   └────┘      └────┘      └────┘            └────┘      │
│       │                                           ▲      │
│    Dummy                                      Dummy      │
│      ▼                                           │      │
│   Most Recently            Least Recently Used (LRU)    │
│      ▲                                     ▼        │
│  MRU Direction                       LRU Direction │
│  (newly accessed)              (to be evicted)     │
│                                                      │
│  ✓ Avoids edge-case handling:                      │
│    - Adding to empty list                         │
│    - Removing only node                           │
│    - Removing first/last node                     │
│                                                      │
└─────────────────────────────────────────────────────┘
```

**Operations:**
- `moveToFront(node)`: O(1) - relink pointers
- `removeLast()`: O(1) - removes tail's prev node
- `addFirst(node)`: O(1) - adds after head

#### 3. 💾 LRUCache Class: Main Cache Logic

Owns both HashMap and DoublyLinkedList.

```
┌──────────────────────────────────────┐
│      LRUCache<K, V>                  │
├──────────────────────────────────────┤
│ Fields:                              │
│  • capacity: int                     │
│  • map: HashMap<K, Node<K,V>>       │
│  • list: DoublyLinkedList<K,V>      │
├──────────────────────────────────────┤
│ Methods:                             │
│  • get(key): V                       │
│  • put(key, value): void             │
│  • moveToFront(node): void           │
│  • evictLRU(): void                  │
└──────────────────────────────────────┘
```

### Design Considerations

#### ❓ Why store key in the Node?
```
Scenario: Evicting LRU node from tail

Step 1: Get tail node from list
        tail.prev = LRU node

Step 2: Remove from HashMap
        map.remove(???)  ← What key to use?

Solution: Store key in the node
         map.remove(node.key)  ← Retrieved from node itself
```

#### ❓ Why use dummy head and tail nodes?
```
Without dummy nodes:
❌ Adding to empty list: special case
❌ Removing only node: special case
❌ Removing first node: update head = special case
❌ Removing last node: update tail = special case

With dummy nodes:
✅ Always have head and tail (never null)
✅ All operations same (no special cases)
✅ Cleaner, simpler code
```

## Key Design Principles Used in the Code

| Principle | Application |
|-----------|-------------|
| 🎯 **Single Responsibility** | Node handles data + LL logic, DoublyLinkedList handles ordering, LRUCache orchestrates |
| 📦 **Encapsulation** | Internals hidden; only public `get()`/`put()` exposed |
| 🔒 **Thread Safety** | Synchronized methods protect map + list consistency |

---

## High-Level Code Explanation and Interaction Flow

The code implements the standard **HashMap + Doubly Linked List** pattern for O(1) LRU operations.

### 1. 📌 Node Component: The Dual-Role Entry

```
┌─────────────────────────────────────────────────────────┐
│ Node = Data Container + Linked List Element             │
├─────────────────────────────────────────────────────────┤
│                                                         │
│  Node<K,V>                                             │
│  ┌─────────────────────────────────────────┐          │
│  │ key: K              (cache lookup key)  │          │
│  │ value: V            (cached data)       │          │
│  │ prev: Node<K,V>     (link to prev MRU) │          │
│  │ next: Node<K,V>     (link to next LRU) │          │
│  └─────────────────────────────────────────┘          │
│           ▲                                             │
│           │                                             │
│      Serves double duty:                               │
│      ✓ HashMap value                                   │
│      ✓ DoublyLinkedList element                       │
│                                                         │
└─────────────────────────────────────────────────────────┘
```

### 2. 🔗 DoublyLinkedList Component: The Recency Tracker

The list maintains access order with head = MRU, tail = LRU.

```
┌──────────────────────────────────────────────────────────┐
│ DoublyLinkedList: Order Maintenance                     │
├──────────────────────────────────────────────────────────┤
│                                                          │
│ Head (Dummy)  ◄───────────────────────────►  Tail (Dummy) │
│      ▼                                            ▲        │
│   ┌─────────┐    ┌─────────┐    ┌─────────┐   ┌─────────┐
│   │   [*]   │◄──►│   MRU   │◄──►│   ...   │◄──►│   [*]   │
│   │  Dummy  │    │  Node   │    │  Node   │   │  Dummy  │
│   └─────────┘    └─────────┘    └─────────┘   └─────────┘
│         ▲                                          ▼
│    Most Recently                      Least Recently Used
│   Accessed/Inserted                   (evict this)
│                                                          │
│ Operations:                                            │
│  • moveToFront(node):  O(1) unlink + relink           │
│  • removeLast():       O(1) remove tail.prev          │
│  • addFirst(node):     O(1) insert after head         │
│                                                          │
└──────────────────────────────────────────────────────────┘
```

**Key Insight:** Dummy nodes eliminate edge cases:
- No null checks for head/tail
- Always can move/remove (valid neighbors)
- Code is simpler and more uniform

### 3. 💾 LRUCache Component: The Orchestrator

LRUCache owns both data structures and ensures they stay synchronized.

```
┌─────────────────────────────────────────────────────────┐
│           LRUCache<K,V> Orchestrator                   │
├─────────────────────────────────────────────────────────┤
│                                                         │
│  Key Fields:                                           │
│  ┌──────────────────────┐ ┌──────────────────────┐   │
│  │ HashMap              │ │ DoublyLinkedList     │   │
│  │                      │ │                      │   │
│  │ K ──→ Node (ref)     │ │ Head ◄──→ Nodes     │   │
│  │                      │ │         ◄──→ Tail   │   │
│  │ O(1) lookup          │ │ O(1) move/remove    │   │
│  └──────────────────────┘ └──────────────────────┘   │
│           ▲                       ▲                    │
│           └───────── Synchronized ──────────┘        │
│              (always in sync)                          │
│                                                         │
│  Integer capacity: max items to hold                  │
│                                                         │
└─────────────────────────────────────────────────────────┘
```

### 4. 📖 `get(key)` Operation Flow

```
get(key) → Retrieve and Mark as Most Recently Used
───────────────────────────────────────────────────────

START: get("apple")

Step 1: Lookup in HashMap
        map.containsKey("apple")?
        │
        ├─ NO  → return null (miss)
        │
        └─ YES → Get node reference (O(1))
                 ▼

Step 2: Move Node to Front
        moveToFront(node)
        │
        ├─ Unlink from current position
        │  [prev.next = node.next]
        │  [next.prev = node.prev]
        │
        └─ Relink after head (MRU position)
           [head.next = node]
           [node.prev = head]
           [node.next = old_head.next]
           [old_head.next.prev = node]
        
        ▼

Step 3: Return Value
        return node.value (O(1))

Result: ✅ Cache hit
        ✅ "apple" now MRU
        ✅ All O(1) operations
```

**Example Timeline (capacity=3):**
```
Initial:  [empty]

get("a") → [a]  (not found, return null)
                 
put("a",1) → [a]

put("b",2) → [b ◄──► a]

put("c",3) → [c ◄──► b ◄──► a]

get("a") → [a ◄──► c ◄──► b]  (a moved to front/MRU)
           ▲                   ▲
           MRU             LRU (b will be evicted next)
```

### 5. 📝 `put(key, value)` Operation Flow

```
put(key, value) → Insert/Update and Mark as Most Recently Used
──────────────────────────────────────────────────────────────

START: put("grape", 5)

Step 1: Check if Key Exists
        map.containsKey("grape")?
        │
        ├─ YES (Update case)
        │   │
        │   ├─ Update node.value
        │   ├─ moveToFront(node)  (mark as MRU)
        │   └─ Return
        │
        └─ NO (Insert case)
            ▼

Step 2: Check Cache Capacity
        (size == capacity)?
        │
        ├─ YES → Evict LRU
        │   │
        │   ├─ Get LRU: node = tail.prev
        │   ├─ Remove from LL: list.removeLast()
        │   ├─ Remove from Map: map.remove(node.key)
        │   └─ size--
        │
        └─ NO → Proceed
            ▼

Step 3: Create New Node
        node = new Node(key, value)
        size++
        ▼

Step 4: Add to HashMap
        map.put(key, node)  (O(1))
        ▼

Step 5: Add to Front of List
        list.addFirst(node)  (O(1))
        (mark as MRU)
        ▼

Result: ✅ Item stored
        ✅ Set as MRU
        ✅ LRU evicted if full
        ✅ All O(1) operations
```

**Example: put() with Eviction (capacity=3):**
```
State:        [c ◄──► b ◄──► a]
              ▲               ▲
              MRU             LRU

put("d", 4)   [d ◄──► c ◄──► b]    (a evicted, d inserted as MRU)
              ▲               ▲
              MRU             LRU

put("a", 1)   [a ◄──► d ◄──► c]    (b evicted, a inserted as MRU)
              ▲               ▲
              MRU             LRU

get("d")      [d ◄──► a ◄──► c]    (d moved to MRU)
              ▲               ▲
              MRU             LRU

put("e", 5)   [e ◄──► d ◄──► a]    (c evicted, e inserted as MRU)
              ▲               ▲
              MRU             LRU
```

### 6. 🎯 Why This Design Works

```
✅ Correctness Checklist
╔═══════════════════════════════════════════════════════╗
║ Requirement            │ How it's Satisfied          ║
╠═══════════════════════════════════════════════════════╣
║ get() in O(1)          │ HashMap lookup + move O(1)  ║
║ put() in O(1)          │ HashMap ops + LL ops O(1)   ║
║ Track recency          │ LL head = MRU, move on use  ║
║ Evict LRU              │ Remove tail.prev instantly  ║
║ Capacity management    │ Check size before insert    ║
║ Generic K,V support    │ Class is <K,V> generic      ║
╚═══════════════════════════════════════════════════════╝

🔑 Key Insight:
   HashMap handles FAST ACCESS (what?)
   DoubleLinkedList handles FAST ORDERING (order?)
   Together = O(1) for all operations
```

---

## 🔒 Thread Safety and Concurrency in the Implementation

### Overview: The Synchronization Challenge

```
Multi-Threaded Access Scenario:

Thread 1 (T1)          Thread 2 (T2)          Thread 3 (T3)
   │                       │                       │
   ├─ get("key1")         ├─ put("key2", v)      ├─ put("key3", v)
   │                       │                       │
   ▼ tries to access  ▼ tries to access  ▼ tries to access
   
   ┌─────────────────────────────────────┐
   │   SHARED STATE (Not Thread-Safe!)   │
   ├─────────────────────────────────────┤
   │ HashMap<K, Node>                    │
   │ DoublyLinkedList                    │
   │ capacity counter                    │
   └─────────────────────────────────────┘
   
❌ Without synchronization: corruption, data loss, crashes
✅ With synchronization: safe, consistent, predictable
```

### Why Synchronization is Needed

The cache state consists of **two connected structures** that must always be in sync:

```
┌──────────────────────────────────────────────────────┐
│    Problem: Two Interdependent Data Structures      │
├──────────────────────────────────────────────────────┤
│                                                      │
│ HashMap<K, Node>  ◄───────────────────► DoubleLL   │
│     │                                       │       │
│  stores ref ────────────────────────────► stores    │
│  to node             mutual refs           pointers │
│                                                      │
│ If Thread 1 updates HashMap while                  │
│ Thread 2 updates DoubleLL at same time:            │
│                                                      │
│ ❌ Map points to node that's been unlinked         │
│ ❌ List contains node not in map                    │
│ ❌ Pointers corrupted (prev/next broken)           │
│ ❌ Size counter wrong                              │
│                                                      │
└──────────────────────────────────────────────────────┘
```

### Race Condition: Example Without Lock

```
Timeline: Two threads, one spot to add to cache
──────────────────────────────────────────────────────────

State: Cache full (capacity=3, size=3)

BEFORE:
  HashMap: {a: nodeA, b: nodeB, c: nodeC}
  LL:      [c ◄──► b ◄──► a]
  
Thread 1: put("x", val_x)
Thread 2: put("y", val_y)

Desired Outcome: One succeeds, other queues

T1  │                          │  T2
─────────────────────────────────────
    │  Check size == capacity   │
    │  YES (3 == 3)            │
    │                           │  Check size == capacity
    │  Get LRU: node a         │  YES (3 == 3)
    │  Remove a from LL        │
    │  (LL now broken!)        │  Get LRU: ???
    │                           │  (finds corrupted node!)
    │  Remove a from map       │
    │  Add new node to map     │  Remove ??? from map
    │  Add new node to LL      │  (removes wrong node!)
    │                           │  Add node to map
    │  ✓ Done (thinks ok)      │  Add node to LL
    │                           │  ✓ Done (thinks ok)
    │  RESULT: ❌ Both succeeded, but cache corrupted

How it Should Work (WITH LOCK):
─────────────────────────────────────
T1  │                          │  T2
─────────────────────────────────────
    │ [LOCK cache]             │
    │ Check size, evict, add   │ [WAIT FOR LOCK]
    │ [UNLOCK cache]           │
    │ ✓ Done                   │ [LOCK cache]
    │                           │ Check size, evict, add
    │                           │ [UNLOCK cache]
    │                           │ ✓ Done
    │                           │
    │ RESULT: ✅ Both succeeded, cache consistent
```

### Critical Paths Requiring Synchronization

```
┌─────────────────────────────────────────────────────┐
│  Operations That Must Be Atomic                    │
├─────────────────────────────────────────────────────┤
│                                                     │
│ get(key):                                          │
│   ┌─ HashMap.get(key)           [multi-step]      │
│   ├─ LL.moveToFront(node)        [many pointers]   │
│   └─ return value                [must serialize]  │
│                                                     │
│ put(key, value):                                   │
│   ┌─ Check if exists             [read state]     │
│   ├─ If evict: LL.removeLast()    [update LL]      │
│   ├─ If evict: HashMap.remove()   [update map]     │
│   ├─ Create node                  [allocate]       │
│   ├─ HashMap.put()                [insert map]     │
│   ├─ LL.addFirst()                [insert LL]      │
│   └─ Update size                  [counter]        │
│                                                     │
│ Each entire operation must complete before         │
│ another thread can start its operation             │
│                                                     │
└─────────────────────────────────────────────────────┘
```

### What Can Happen Without Locking

```
❌ Scenario 1: Pointer Corruption
──────────────────────────────────
moveToFront(node): [unlink] [relink]
                   Step 1: node.prev.next = node.next
                   [context switch]
                   Step 2: node.next.prev = node.prev
                   Another thread modifies node → broken chain!

❌ Scenario 2: Double Eviction
──────────────────────────────
if (size == capacity): [check]
                       [context switch]
                       [another thread checks: also true!]
Both evict the same node → size wrong!

❌ Scenario 3: Lost Update
──────────────────────
map.put(key, node)  [inserted]
                    [context switch]
list.addFirst(node) [never happens!]
                    Another thread overwrites without list update!
```

### The Lock Strategy

```
┌──────────────────────────────────────────────────────┐
│ Solution: Synchronized Methods (Coarse Lock)       │
├──────────────────────────────────────────────────────┤
│                                                      │
│ public synchronized V get(K key) {                 │
│     // Entire method runs atomically               │
│     // No other thread can run any method on this  │
│     // cache instance while this executes          │
│     ...                                             │
│ }                                                   │
│                                                      │
│ public synchronized void put(K key, V value) {    │
│     // Same lock as get()                          │
│     // Only one get() or put() at a time           │
│     ...                                             │
│ }                                                   │
│                                                      │
│ Effect: All operations serialized (queue up)       │
│         No interleaving possible                   │
│         State always consistent                    │
│                                                      │
└──────────────────────────────────────────────────────┘
```

### Why `get()` Must Also Be Synchronized

```
Misconception: "get() is read-only, so it's safe"

Reality:
get(key) is NOT read-only!

public V get(K key) {
    Node node = map.get(key);         // ← Read
    if (node != null) {
        list.moveToFront(node);       // ← WRITE! (reorders LL)
        return node.value;
    }
    return null;
}

moveToFront(node):
    ├─ unlinks node from current position (modify 4 pointers)
    ├─ relinks at front (modify 4 more pointers)
    └─ Total: modifies 8 pointers across the LL

🎯 get() modifies LL structure → must be synchronized!
```

### Why Lock Must Cover Both Methods

```
❌ If only put() locked:
   
   Thread 1          Thread 2
   put()             get()
   [LOCK acquired]   [no lock]
   ...               Reads/modifies LL
   [UNLOCK]          While T1 is writing!
   ↓
   RACE CONDITION!

❌ If only get() locked:
   
   Thread 1          Thread 2
   get()             put()
   [LOCK acquired]   [no lock]
   ...               Modifies map/LL
   [UNLOCK]          While T1 is reading!
   ↓
   RACE CONDITION!

✅ If both locked (same lock):
   
   Thread 1          Thread 2
   get()             put()
   [LOCK acquired]   [WAIT]
   [read/modify]     [blocked]
   [UNLOCK]          [LOCK acquired]
   ↓                 [read/modify]
   Only one operates at a time ✓
```

### Trade-off: Correctness vs. Performance

```
┌──────────────────────────────────────────────────────┐
│  Single Global Lock Analysis                        │
├──────────────────────────────────────────────────────┤
│                                                      │
│ ✅ BENEFITS:                                        │
│    • Simple to implement                           │
│    • 100% correct (no races)                       │
│    • Easy to reason about                          │
│    • Good for interviews                           │
│                                                      │
│ ❌ COSTS:                                           │
│    • All operations serialize                      │
│    • Even unrelated keys wait                      │
│    • High contention under load                    │
│    • Throughput limited                            │
│                                                      │
│ SCALABILITY IMPACT:                                 │
│                                                      │
│   1 thread  → throughput = X ops/sec               │
│   4 threads → throughput ≈ X ops/sec (not 4X!)   │
│   16 threads → throughput ≈ X ops/sec (locked)   │
│                                                      │
│   All requests queue behind single lock             │
│   = One big queue, not parallel ❌                 │
│                                                      │
└──────────────────────────────────────────────────────┘
```

### Optimization: Striped Locking (Advanced)

```
┌──────────────────────────────────────────────────────┐
│  Solution for High Concurrency: Lock Sharding       │
├──────────────────────────────────────────────────────┤
│                                                      │
│ Instead of 1 lock protecting entire cache:         │
│ → Divide cache into N shards                       │
│ → Each shard has own lock                          │
│ → Different keys can use different locks           │
│                                                      │
│ DESIGN:                                             │
│                                                      │
│   LRUCache (16 shards)                             │
│   │                                                 │
│   ├─ Shard[0] [Lock0] ─→ Map0 + LL0               │
│   ├─ Shard[1] [Lock1] ─→ Map1 + LL1               │
│   ├─ ...                                            │
│   └─ Shard[15] [Lock15] ─→ Map15 + LL15           │
│                                                      │
│ SHARDING LOGIC:                                     │
│   shard_index = hash(key) % num_shards             │
│   lock = shards[shard_index].lock                  │
│   node = shards[shard_index].map.get(key)          │
│                                                      │
│ BENEFIT:                                            │
│   Thread 1: key1 → shard[0] [Lock0] [proceeds]     │
│   Thread 2: key2 → shard[5] [Lock5] [proceeds]     │
│   Thread 3: key3 → shard[0] [Lock0] [waits]        │
│                   ↑ same shard as T1                │
│                                                      │
│   Different shards = parallel execution! ✅       │
│   Same shard = serialized (local queue)            │
│                                                      │
│ THROUGHPUT:                                         │
│   16 threads, 16 shards → ~16X throughput ✓      │
│   (much better than single lock)                    │
│                                                      │
└──────────────────────────────────────────────────────┘
```

### Practical Takeaways

```
┌──────────────────────────────────────────────────────┐
│  Interview Perspective: When to Use What            │
├──────────────────────────────────────────────────────┤
│                                                      │
│ 🎯 Single Global Lock (@synchronized):             │
│    ✓ Simple to code                                 │
│    ✓ Correct (no races)                            │
│    ✓ Great for learning/interviews                 │
│    ✓ Fine for low concurrency                      │
│    ✗ Not scalable under high load                  │
│                                                      │
│ 🎯 Fine-Grained Locks (per-bucket):                │
│    ✓ More complex                                   │
│    ✓ Better for high concurrency                   │
│    ✓ Standard in production systems                │
│    ✓ Worth mentioning as optimization              │
│                                                      │
│ 🎯 Lock-Free (CAS, AtomicX):                       │
│    ✓ Best performance                              │
│    ✓ Very complex                                   │
│    ✗ Hard to get right                            │
│    ✗ Overkill for most use cases                   │
│                                                      │
│ 📌 BOTTOM LINE:                                     │
│    Start with synchronized (correct & simple)      │
│    Optimize to striped locking if needed           │
│    Use lock-free only for extreme performance      │
│                                                      │
└──────────────────────────────────────────────────────┘
```

