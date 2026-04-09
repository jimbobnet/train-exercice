/**
 * Represents a single scheduled train service on the MetroLine network.
 *
 * A Service links a Train, a Driver, a departure time (24-hour "HH:MM"),
 * and a route code (e.g. "R3"). It is created as not-completed; once the
 * train has run, markCompleted() is called.
 *
 * Note: Service has-a Train (aggregation) and has-a Driver (aggregation).
 */
public class Service {

    private Train  train;
    private Driver driver;
    private String departureTime;   // "HH:MM" 24-hour format
    private String routeCode;       // e.g. "R1", "R3"
    private boolean completed;

    /**
     * Creates a new, not-yet-completed service.
     *
     * @param train         the train unit assigned to this service
     * @param driver        the driver assigned to this service
     * @param departureTime departure time as "HH:MM"
     * @param routeCode     route identifier
     */
    public Service(Train train, Driver driver, String departureTime, String routeCode) {
        this.train         = train;
        this.driver        = driver;
        this.departureTime = departureTime;
        this.routeCode     = routeCode;
        this.completed     = false;
    }

    // ── Accessors / mutators ──────────────────────────────────────────────

    public Train   getTrain()                     { return train; }
    public Driver  getDriver()                    { return driver; }
    public void    setDriver(Driver d)            { driver = d; }
    public String  getDepartureTime()             { return departureTime; }
    public void    setDepartureTime(String time)  { departureTime = time; }
    public String  getRouteCode()                 { return routeCode; }
    public boolean getCompleted()                 { return completed; }
    public void    markCompleted()                { completed = true; }

    // ── Comparison helpers ────────────────────────────────────────────────

    /**
     * Compares this service's departure time to a given time string.
     *
     * Returns a negative number if this service departs BEFORE time,
     * zero if equal, positive if AFTER.
     * Because times are in "HH:MM" format, lexicographic order equals
     * chronological order.
     *
     * @param time a departure time string "HH:MM"
     */
    public int compareWith(String time) {
        return this.departureTime.compareTo(time);
    }

    /**
     * Compares this service's departure time to another service's.
     * Same sign convention as compareWith(String).
     *
     * This method has the same name as compareWith(String) but a different
     * parameter type — an example of method overloading.
     *
     * @param other another Service
     */
    public int compareWith(Service other) {
        return this.departureTime.compareTo(other.getDepartureTime());
    }

    @Override
    public String toString() {
        return "[" + train.getTrainId() + " " + departureTime + " " + routeCode
                + (completed ? " DONE" : "") + " driver=" + driver.getName() + "]";
    }
}
