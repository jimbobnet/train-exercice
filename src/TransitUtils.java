import java.util.LinkedList;
import java.util.Stack;

/**
 * Utility methods for MetroLine data-structure operations.
 *
 * ─────────────────────────────────────────────────────────────────────────
 * YOUR TASKS
 * ─────────────────────────────────────────────────────────────────────────
 *   Task 5 (core)      – mergeDepartures()   merge two sorted queues
 *   Task 6 (extension) – retrieveTrain()     pop a target off a stack
 * ─────────────────────────────────────────────────────────────────────────
 */
public class TransitUtils {

    // ── Task 5 ────────────────────────────────────────────────────────────

    /**
     * Merges two sorted departure queues into a single sorted LinkedList.
     *
     * Both northQueue and southQueue are already sorted by departure time
     * (earliest at the front). The returned list must also be sorted.
     *
     * Both input queues will be EMPTY after this method returns — every
     * service is moved into the result list.
     *
     * Allowed LinkedList operations: addFirst, addLast, getFirst, getLast,
     * removeFirst, removeLast, size(), isEmpty()
     *
     * @param northQueue services departing from North Terminal (sorted)
     * @param southQueue services departing from South Terminal (sorted)
     * @return a new sorted LinkedList containing all services from both queues
     */
    public static LinkedList<Service> mergeDepartures(
            LinkedList<Service> northQueue,
            LinkedList<Service> southQueue) {

        // TODO – Task 5
        //
        // Create a new LinkedList<Service> called result.
        //
        // Phase 1 – two-pointer merge:
        //   While NEITHER queue is empty:
        //     Compare northQueue.getFirst() with southQueue.getFirst()
        //     using compareWith().  If the north service departs earlier
        //     (or at the same time), removeFirst() from northQueue and
        //     addLast() to result; otherwise do the same for southQueue.
        //
        // Phase 2 – drain any remaining services:
        //   After one queue empties, loop through whichever queue still
        //   has services and removeFirst() / addLast() each one.
        //
        // Return result.
        return null; // replace this line
    }

    // ── Task 6 (Extension) ────────────────────────────────────────────────

    /**
     * Removes the train with the given trainId from the yard stack.
     *
     * The yard is a single-track stabling road modelled as a stack: you
     * can only directly access the top. To reach a train in the middle you
     * must move trains above it out of the way.
     *
     * The relative order of all OTHER trains must be preserved after the
     * method returns.  (If T001 was below T099 before the call, it must
     * still be below T099 afterwards.)
     *
     * Allowed Stack operations: push(item), pop(), peek(), isEmpty()
     *
     * If trainId is not found, the stack must be left unchanged.
     *
     * @param yard    the depot stabling stack
     * @param trainId the ID of the train to retrieve and remove
     */
    public static void retrieveTrain(Stack<Train> yard, String trainId) {
        // TODO – Task 6 (Extension)
        //
        // Create a temporary Stack<Train> called temp.
        //
        // Pop trains from yard one at a time.
        //   If the popped train's ID matches trainId → found!  Stop popping.
        //   Otherwise push it onto temp.
        //
        // Restore order: pop everything from temp back onto yard.
        //   (The last item pushed onto temp was closest to the top of yard,
        //    so popping temp back onto yard restores the original order.)
    }
}
