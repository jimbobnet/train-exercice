/**
 * Represents a rolling-stock train unit owned by MetroLine.
 *
 * A train starts inService (operational). It can be taken out of service
 * via setInService(false), e.g. for maintenance.
 */
public class Train {

    private String  trainId;
    private int     capacity;
    private boolean inService;

    /**
     * @param trainId  unique fleet identifier, e.g. "T042"
     * @param capacity maximum passenger seats
     */
    public Train(String trainId, int capacity) {
        this.trainId   = trainId;
        this.capacity  = capacity;
        this.inService = true;
    }

    public String  getTrainId()            { return trainId; }
    public int     getCapacity()           { return capacity; }
    public boolean getInService()          { return inService; }
    public void    setInService(boolean b) { inService = b; }

    @Override
    public String toString() {
        return trainId + " (cap=" + capacity + ", inService=" + inService + ")";
    }
}
