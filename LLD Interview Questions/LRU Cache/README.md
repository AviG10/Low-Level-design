# LRU Cache Design

## Functional Requirements

1. **get(key) Operation**: Return the value if exists, otherwise return -1.
2. **put(key, value) Operation**: Insert a new key or update the value of existing key.
3. **Capacity Management**: If the cache exceeds its capacity, it should automatically remove the least recently used item from cache.
4. **Recency Updates**: Both get and put should update the recency of accessed or inserted item.
5. **Generic Support**: Generic key-value (<k,v>) should be supported.

## Non-Functional Requirements

1. **Performance**: Both get and put must run in **O(1)** time.
2. **Concurrency**: The implementation must be thread-safe in a concurrent environment.

## Identifying Core Entities

Unlike other systems, the design of LRU cache is centered around choosing the right data structures and internal abstractions to achieve the required functionalities and performance.

### Core Challenges

1. We need **fast key-based lookup** for cache read and updates.
2. We need **fast ordering** to track item usage and enforce removal based on recency.

### Data Structure Analysis

#### Why not HashMap?
- For **O(1)** key access, HashMap is the obvious choice.
- However, it doesn't maintain recency order, so when removing the least recently used item, we need to search in HashMap for frequency of occurrences to find the LRU item, which takes **O(n)** time.

#### What to do to maintain recency order in LRU Cache?
- Move the recently accessed item to front.
- Remove the least recently used item from back when cache capacity is exceeded.
- Insert new items at front as they are considered the Most Recently Used.
- Perform all these operations in **O(1)** time.

#### Why not Array?
- Inserting or removing in the middle of an array requires shifting, which takes **O(n)** time.

#### Why not Regular Linked List?
- Finding a specific node to move to front for Most Recently Used access takes **O(n)** time.

#### Why use Doubly Linked List?
- Move Recently used node to head in **O(1)**
- Remove Least Recently used node from tail in **O(1)**
- Remove any node from it in **O(1)** if we have a direct reference to that node stored somewhere.

#### How do we get a direct reference to a node without traversing the list?
- HashMap helps here.

### Verdict

**Use HashMap + Doubly Linked List approach:**
- **HashMap**: Stores a pointer/reference to the node in the Doubly Linked List
- **Doubly Linked List**: Maintains the usage order where head is the most recently used item and tail is the least recently used item

**Operations:**
- To find an item, use HashMap to get a direct pointer to its node in **O(1)**
- Move MRU item to head of Doubly Linked List in **O(1)**
- Move LRU item to tail of Doubly Linked List in **O(1)**

### Required Classes

#### 1. Node Class
An internal class representing entries in the cache and a node in the Doubly Linked List.

**Fields:**
- `K key`
- `V value`
- `Node<K,V> prev`
- `Node<K,V> next`

#### 2. LRUCache Class
Main class which exposes the cache API and owns both HashMap and Doubly Linked List.

**Fields:**
- `Node<K,V> head`
- `Node<K,V> tail`

### Design Considerations

#### Why store key in the Node?
- When we remove the LRU item from the tail of the Doubly Linked List, we need to remove it from HashMap as well.
- HashMap's `remove()` function requires a key to remove the element.
- Therefore, each node must remember its key.

#### Why use dummy head and tail nodes in Doubly Linked List?
- Without this, we would need to handle special cases separately, which would make the code implementation messy:
    - Adding node to empty list
    - Removing the only node in the list
    - Removing first and last nodes in the list

## Key Design Principles Used in the Code

- **Single Responsibility Principle**
- **Encapsulation**
- **Thread Safety**

## High-Level Code Explanation and Interaction Flow

The code in the `Code` folder follows the standard `HashMap + Doubly Linked List` pattern used for LRU cache implementations.

### 1. `Node<K, V>`: the cache entry

Each entry is represented by a `Node` object. It stores:
- `key`
- `value`
- `prev` pointer
- `next` pointer

This node is not just data; it is also a linked-list element. The `prev` and `next` references allow the cache to reorder items in constant time when they become recently used or when they are removed.

### 2. `DoublyLinkedList<K, V>`: the recency tracker

The list maintains the access order.

- The head is the most recently used item.
- The tail is the least recently used item.
- Nodes are moved to the front when accessed.
- Nodes are removed from the tail when capacity is exceeded.

This class uses dummy `head` and `tail` nodes to avoid edge-case logic such as:
- empty list handling
- removing the only node
- removing first or last node manually

### 3. `LRUCache<K, V>`: the main cache logic

The `LRUCache` class owns both the map and the list.

- `Map<K, Node<K, V>> map` gives O(1) access by key.
- `DoublyLinkedList<K, V> list` gives O(1) recency updates and eviction.

#### `get(key)` flow

1. Check whether the key exists in the map.
2. If it exists, fetch the node from the map.
3. Move that node to the front of the linked list using `moveToFront(node)`.
4. Return the value.
5. If it does not exist, return `null`.

This ensures every successful read makes the key the most recently used item.

#### `put(key, value)` flow

1. Check if the key already exists in the map.
2. If it exists:
   - update the value
   - move the node to the front
3. If it does not exist:
   - if the cache is full, remove the last node from the list
   - remove the corresponding key from the map
   - create a new node
   - insert the node into the map
   - add the new node to the front of the list

This ensures new inserts are treated as most recently used, and older entries are evicted from the tail.

### 4. Example interaction flow

Let the cache capacity be 3.

- `put("a", 1)` → list becomes `a`
- `put("b", 2)` → list becomes `b -> a`
- `put("c", 3)` → list becomes `c -> b -> a`
- `get("a")` → list becomes `a -> c -> b`
- `put("d", 4)` → cache is full, remove `b` from the tail, then insert `d` at front → `d -> a -> c`

Now the least recently used item is always at the end of the list, and the most recently used item is always at the beginning.

### 5. Why the design works

This design satisfies the LRU requirements because:

- `HashMap` gives constant-time key lookups.
- the doubly linked list gives constant-time movement and eviction.
- the tail represents the LRU item, so eviction is immediate.
- each node stores its key to remove the correct entry from the map when a node is evicted.

In short, the `HashMap` handles fast access, and the doubly linked list handles fast ordering and eviction. Together they provide the required O(1) behavior for both read and write operations.
