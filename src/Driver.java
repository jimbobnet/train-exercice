/**
 * Represents a licensed train driver employed by MetroLine.
 *
 * A Driver is "available" when they are not currently assigned to an active
 * service. The constructor always initialises availability to true.
 */
public class Driver {

    private String licenceId;
    private String name;
    private boolean available;

    /**
     * @param licenceId unique licence identifier, e.g. "DL001"
     * @param name      driver's full name
     */
    public Driver(String licenceId, String name) {
        this.licenceId = licenceId;
        this.name      = name;
        this.available = true;
    }

    public String  getLicenceId()          { return licenceId; }
    public String  getName()               { return name; }
    public boolean getAvailable()          { return available; }
    public void    setAvailable(boolean b) { available = b; }

    @Override
    public String toString() {
        return name + " [" + licenceId + "] available=" + available;
    }
}
