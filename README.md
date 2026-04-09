# MetroLine — Java Coding Challenge

**IB Computer Science HL · Option D: Object-Oriented Programming**  
Estimated time: ~1 hour

---

## Scenario

MetroLine is a commuter rail operator. Their system manages a fleet of **Trains**, a pool of licensed **Drivers**, and a daily **Timetable** of scheduled **Services**.

You are given four fully implemented classes — `Driver`, `Train`, `Service`, and a skeleton `ExpressService` — plus a partial `Timetable` and a utility class `TransitUtils`. Your job is to complete seven methods and one subclass across those last three files.

---

## Project Structure

```
src/
├── Driver.java           ← complete, do not modify
├── Train.java            ← complete, do not modify
├── Service.java          ← complete, do not modify
├── ExpressService.java   ← Task 6 Part A is here
├── Timetable.java        ← Tasks 1–4 and Task 6 Part B are here
├── TransitUtils.java     ← Tasks 5 and extension are here
└── Main.java             ← test harness, do not modify
```

---

## Setup

Compile and run from the project root:

```bash
javac src/*.java -d out
java  -cp out Main
```

A passing test prints `PASS`; a failing test prints `FAIL`.  
Start with 0 passes and work through the tasks in order — each task unlocks the next.

---

## The Tasks

### Task 1 — `Timetable.search()` *(private)*

Implement a **linear search** that finds a service by route code and departure time.

- Iterate through `schedule[0]` to `schedule[last]`.
- Return the **index** of the first entry matching *both* `routeCode` and `departureTime`.
- Return **-1** if no match is found.

> The provided `cancel()` method calls `search()`, so completing Task 1 also unlocks `cancel()`.

---

### Task 2 — `Timetable.add()`

Insert a new `Service` into the array so that the schedule stays **sorted by departure time** (earliest first).

There are three steps:

1. **Find** the insertion index `i` — the first position where the existing service departs *after* `s`.  
   Use `schedule[i].compareWith(s) < 0` to test whether `schedule[i]` departs before `s`.

2. **Shift** all services from index `i` to `last` one position to the right.

3. **Place** `s` at index `i` and increment `last`.

---

### Task 3 — `Timetable.showIncomplete()`

Print the departure time of every service on a given route that has **not** yet been completed, one time per line.

Iterate through the schedule and `System.out.println()` the departure time whenever:
- the service's route code matches the argument, **and**
- `getCompleted()` returns `false`.

---

### Task 4 — `Timetable.reassignDriver()`

Replace the assigned driver on every **incomplete** service currently held by the driver identified by `licenceId`, substituting the `replacement` driver.

After all reassignments:
- mark the **original** driver as available (`setAvailable(true)`)
- mark the **replacement** as unavailable (`setAvailable(false)`)

If no incomplete service matches `licenceId`, nothing should change.

> **Hint:** Save a reference to the original `Driver` object *before* you call `setDriver()` — once replaced, you can no longer retrieve it from the service.

---

### Task 5 — `TransitUtils.mergeDepartures()`

Merge two sorted `LinkedList<Service>` queues (North Terminal and South Terminal) into a **single sorted list** using the two-pointer (merge) algorithm.

**Allowed `LinkedList` operations:** `addFirst`, `addLast`, `getFirst`, `getLast`, `removeFirst`, `removeLast`, `size()`, `isEmpty()`

Steps:
1. While **both** queues are non-empty, compare the front of each with `compareWith()`. Remove and append whichever departs earlier.
2. Once one queue is empty, drain the remaining services from the other.
3. Return the merged list.

Both input queues must be **empty** when the method returns.

---

### Task 6 — `ExpressService` (inheritance)

This task introduces **inheritance** — a concept not covered in the exam paper.

#### Part A — Write the `ExpressService` class (`ExpressService.java`)

`ExpressService` is a subclass of `Service` that adds one extra attribute: the platform number the train departs from.

Complete the three TODOs in `ExpressService.java`:

1. **Declare the private field** `int platformNumber`.

2. **Write the constructor.** It takes the same parameters as `Service` plus `int platformNumber`.  
   Use `super(train, driver, departureTime, routeCode)` as the very first statement to call the parent constructor, then assign `platformNumber`.

3. **Write the getter** `getPlatformNumber()`.

4. **Override `toString()`** to return a String in the format:  
   `"[EXPRESS T034 08:00 R2 platform=3]"`

Because `ExpressService` extends `Service`, an `ExpressService` object can be passed to `Timetable.add()` and stored in the schedule array without any changes to `Timetable` — this is **subtype polymorphism** in action.

#### Part B — Implement `Timetable.showExpressServices()`

Iterate through the schedule and print one summary line for every `ExpressService` entry, skipping regular `Service` objects.

Use the `instanceof` keyword to identify express services, then **downcast** to access `getPlatformNumber()`:

```java
if (schedule[i] instanceof ExpressService) {
    ExpressService es = (ExpressService) schedule[i];
    // ... print es.getDepartureTime(), es.getRouteCode(), es.getPlatformNumber()
}
```

Expected output format:
```
08:00 R2 [Platform 3]
09:30 R1 [Platform 7]
```

---

### Extension — `TransitUtils.retrieveTrain()`

The MetroLine depot yard is modelled as a `Stack<Train>` — a single-track road where you can only access the top train directly.

Remove the train with the given `trainId` from the stack. The relative order of **all other trains** must be preserved.

**Allowed `Stack` operations:** `push(item)`, `pop()`, `peek()`, `isEmpty()`

> **Hint:** Use a second temporary stack. Pop trains from `yard` onto it until you find the target (discard it). Then pop everything back from the temporary stack to `yard` — this restores the original order.

If `trainId` is not found, leave the stack unchanged.

---

## Key Classes at a Glance

| Class | Key attributes | Relationship |
|---|---|---|
| `Driver` | `licenceId`, `name`, `available` | — |
| `Train` | `trainId`, `capacity`, `inService` | — |
| `Service` | `train`, `driver`, `departureTime`, `routeCode`, `completed` | has-a `Train`, has-a `Driver` |
| `ExpressService` | inherits all of `Service` + `platformNumber` | is-a `Service` |
| `Timetable` | `schedule[]` (sorted array), `last` | contains `Service` objects |

The `compareWith()` method on `Service` compares departure times:  
negative → this service departs *earlier*; zero → same time; positive → this service departs *later*.

---

## How departure times are compared

Times are stored as `"HH:MM"` strings (24-hour). Because this format is zero-padded, **lexicographic (alphabetical) order equals chronological order** — so Java's `String.compareTo()` works directly.

```
"07:15".compareTo("08:00")  →  negative  (07:15 is earlier)
"09:30".compareTo("09:30")  →  0
"10:00".compareTo("08:45")  →  positive  (10:00 is later)
```
