/**
 * Manages the MetroLine daily timetable as a sorted array of Services.
 *
 * The array (schedule) holds up to MAX_SERVICES entries ordered by
 * departure time (earliest first). The variable `last` is the index of
 * the final occupied slot; it starts at -1 (empty timetable).
 *
 *   schedule[0]  ← earliest departure
 *   schedule[1]
 *   ...
 *   schedule[last] ← latest departure
 *   schedule[last+1 .. MAX_SERVICES-1] ← unused (null)
 *
 * ─────────────────────────────────────────────────────────────────────────
 * YOUR TASKS
 * ─────────────────────────────────────────────────────────────────────────
 *   Task 1 – search()          linear search; returns index or -1
 *   Task 2 – add()             sorted insertion; keeps array in time order
 *   Task 3 – showIncomplete()  print departure times of unfinished services
 *   Task 4 – reassignDriver()  swap drivers on all incomplete services
 * ─────────────────────────────────────────────────────────────────────────
 */
public class Timetable {

    static final int MAX_SERVICES = 200;

    Service[] schedule;
    int       last = -1;          // index of last occupied slot (-1 = empty)

    /** Creates an empty timetable. */
    public Timetable() {
        schedule = new Service[MAX_SERVICES];
    }

    // ── Provided helpers (read these — they show useful patterns) ─────────

    /**
     * Cancels the service identified by routeCode and departureTime.
     * Removes it from the array, shifts later entries left, and returns
     * the cancelled Service (or null if not found).
     *
     * This method is fully implemented for you. It depends on search(),
     * so it won't work correctly until you complete Task 1.
     */
    public Service cancel(String routeCode, String departureTime) {
        int i = search(routeCode, departureTime);
        if (i == -1) return null;

        Service removed = schedule[i];
        // shift everything after i one place to the left
        for (int j = i; j < last; j++) {
            schedule[j] = schedule[j + 1];
        }
        schedule[last] = null;
        last--;
        return removed;
    }

    /**
     * Returns the service at position index (0 = earliest), or null if
     * the index is out of range. Useful for testing your implementation.
     */
    public Service getServiceAt(int index) {
        if (index < 0 || index > last) return null;
        return schedule[index];
    }

    /** Returns the number of services currently in the timetable. */
    public int getSize() {
        return last + 1;
    }

    // ── Task 1 ────────────────────────────────────────────────────────────

    /**
     * Searches the timetable for a service matching BOTH routeCode AND
     * departureTime using a linear search.
     *
     * @param routeCode     route to match, e.g. "R1"
     * @param departureTime departure time to match, e.g. "08:00"
     * @return the index of the matching service, or -1 if not found
     */
    private int search(String routeCode, String departureTime) {
        // TODO – Task 1
        // Iterate i from 0 to last (inclusive).
        // Return i if schedule[i] matches both routeCode and departureTime.
        //   Hint: use .equals() to compare Strings
        //   Hint: schedule[i].getRouteCode()    and   schedule[i].getDepartureTime()
        // Return -1 if you reach the end without a match.
        return -1; // replace this line
    }

    // ── Task 2 ────────────────────────────────────────────────────────────

    /**
     * Inserts service s into the array in ascending departure-time order.
     *
     * After this call, the array from schedule[0] to schedule[last] must
     * still be sorted earliest-to-latest with no gaps.
     *
     * @param s the Service to insert
     */
    public void add(Service s) {
        // TODO – Task 2
        //
        // Step 1 – find insertion index i:
        //   Start at i = 0 and advance while i <= last AND schedule[i] departs
        //   before s (i.e. schedule[i].compareWith(s) < 0).
        //
        // Step 2 – shift right to make room at index i:
        //   Move schedule[last] → schedule[last+1],
        //         schedule[last-1] → schedule[last], …,
        //         schedule[i] → schedule[i+1].
        //   Loop from j = last+1 down to j = i+1, setting schedule[j] = schedule[j-1].
        //
        // Step 3 – place s and update last:
        //   schedule[i] = s;
        //   last++;
    }

    // ── Task 3 ────────────────────────────────────────────────────────────

    /**
     * Prints the departure time of every service on the given route that
     * has NOT yet been completed, one time per line.
     *
     * @param routeCode the route to filter by, e.g. "R1"
     */
    public void showIncomplete(String routeCode) {
        // TODO – Task 3
        // Iterate through schedule[0..last].
        // For each service where:
        //   (a) the route matches routeCode, AND
        //   (b) the service is not completed (getCompleted() == false)
        // print its departure time using System.out.println().
    }

    // ── Task 4 ────────────────────────────────────────────────────────────

    /**
     * Replaces the assigned driver on every INCOMPLETE service that currently
     * has the driver identified by licenceId, substituting `replacement`.
     *
     * After all reassignments:
     *   • the original driver becomes available  (setAvailable(true))
     *   • the replacement driver becomes unavailable (setAvailable(false))
     *
     * If no incomplete service matches licenceId, nothing is changed.
     *
     * @param licenceId   the licence ID of the driver being replaced
     * @param replacement the Driver object that will take over
     */
    public void reassignDriver(String licenceId, Driver replacement) {
        // TODO – Task 4
        //
        // Declare a Driver variable `original = null` to keep a reference to
        // the driver being replaced (you'll need it to call setAvailable later).
        //
        // Loop through schedule[0..last].
        //   If the service is NOT completed AND its driver's licenceId matches:
        //     - Save schedule[i].getDriver() into `original` (first time only)
        //     - Call schedule[i].setDriver(replacement)
        //
        // After the loop, if `original != null`:
        //   - original.setAvailable(true)
        //   - replacement.setAvailable(false)
    }
}
