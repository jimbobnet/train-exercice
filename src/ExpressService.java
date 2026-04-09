/**
 * An express train service — a specialised type of Service that also
 * records the platform number from which it departs.
 *
 * Because ExpressService IS-A Service, it can be stored anywhere a Service
 * is expected — including in Timetable's schedule array.
 *
 * ─────────────────────────────────────────────────────────────────────────
 * YOUR TASK — Task 6, Part A  (complete all three TODOs in this file)
 * ─────────────────────────────────────────────────────────────────────────
 */
public class ExpressService extends Service {

    private int platformNumber;

    /**
     * Creates a new ExpressService.
     *
     * @param train          the assigned train
     * @param driver         the assigned driver
     * @param departureTime  departure time as "HH:MM"
     * @param routeCode      route identifier
     * @param platformNumber the departure platform (1–12)
     */
    public ExpressService(Train train, Driver driver,
                          String departureTime, String routeCode,
                          int platformNumber) {
        // super() calls the Service constructor to initialise the inherited fields.
        // It must be the very first statement in any subclass constructor.
        super(train, driver, departureTime, routeCode);

        // TODO A-1: assign the platformNumber parameter to this.platformNumber
    }

    // TODO A-2: write the getter  public int getPlatformNumber()
    //           (remove this comment and add the method below)
    public int getPlatformNumber() {
        return 0; // replace this line
    }

    /**
     * TODO A-3: override toString() to return a String in the format:
     *
     *   "[EXPRESS T034 08:00 R2 platform=3]"
     *
     * Hint: you can call the inherited getters — getTrain().getTrainId(),
     * getDepartureTime(), getRouteCode(), getPlatformNumber().
     */
    @Override
    public String toString() {
        return super.toString(); // replace this line
    }
}
