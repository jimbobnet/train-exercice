import java.util.LinkedList;
import java.util.Stack;

/**
 * Test harness for the MetroLine coding challenge.
 *
 * Run this after implementing each task to see how many checks pass.
 * A passing check prints "PASS"; a failing check prints "FAIL".
 *
 * Compile & run (from the project root):
 *   javac src/*.java -d out
 *   java  -cp out Main
 */
public class Main {

    // ── Simple test utilities ─────────────────────────────────────────────

    private static int passed = 0;
    private static int failed = 0;

    private static void check(String label, boolean condition) {
        if (condition) {
            System.out.println("  PASS  " + label);
            passed++;
        } else {
            System.out.println("  FAIL  " + label);
            failed++;
        }
    }

    private static void section(String title) {
        System.out.println("\n┌─────────────────────────────────────────┐");
        System.out.println("│ " + title);
        System.out.println("└─────────────────────────────────────────┘");
    }

    // ── Shared test fixtures ──────────────────────────────────────────────

    static Driver  d1, d2, d3;
    static Train   t1, t2, t3, t4, t5;

    static void buildFixtures() {
        d1 = new Driver("DL001", "Alice Nguyen");
        d2 = new Driver("DL002", "Ben Carter");
        d3 = new Driver("DL003", "Carla Diaz");

        t1 = new Train("T011", 320);
        t2 = new Train("T034", 280);
        t3 = new Train("T019", 360);
        t4 = new Train("T007", 300);
        t5 = new Train("T022", 250);
    }

    // ── main ──────────────────────────────────────────────────────────────

    public static void main(String[] args) {

        buildFixtures();

        testTask1and2();
        testTask3();
        testTask4();
        testTask5();
        testTask6();
        testTask7();

        System.out.println("\n══════════════════════════════════════════");
        System.out.printf("  Results: %d passed, %d failed%n", passed, failed);
        System.out.println("══════════════════════════════════════════");
    }

    // ── Task 1 & 2: search() and add() ───────────────────────────────────

    static void testTask1and2() {
        section("Tasks 1 & 2 — add() and search()");

        Timetable tt = new Timetable();

        Service s08 = new Service(t2, d2, "08:00", "R2");
        Service s07 = new Service(t1, d1, "07:15", "R1");
        Service s07b= new Service(t4, d1, "07:45", "R3");
        Service s09 = new Service(t3, d3, "09:30", "R1");
        Service s08b= new Service(t5, d2, "08:30", "R4");

        // Insert in non-chronological order — add() must sort them
        tt.add(s08);   // 08:00
        tt.add(s07);   // 07:15  → should go before 08:00
        tt.add(s07b);  // 07:45  → should go between 07:15 and 08:00
        tt.add(s09);   // 09:30  → should go at the end
        tt.add(s08b);  // 08:30  → should go between 08:00 and 09:30

        // Expected order: 07:15 · 07:45 · 08:00 · 08:30 · 09:30
        check("add() 5 services stored (getSize)", tt.getSize() == 5);
        check("add() position 0 is 07:15",
                tt.getServiceAt(0) != null &&
                tt.getServiceAt(0).getDepartureTime().equals("07:15"));
        check("add() position 1 is 07:45",
                tt.getServiceAt(1) != null &&
                tt.getServiceAt(1).getDepartureTime().equals("07:45"));
        check("add() position 2 is 08:00",
                tt.getServiceAt(2) != null &&
                tt.getServiceAt(2).getDepartureTime().equals("08:00"));
        check("add() position 3 is 08:30",
                tt.getServiceAt(3) != null &&
                tt.getServiceAt(3).getDepartureTime().equals("08:30"));
        check("add() position 4 is 09:30",
                tt.getServiceAt(4) != null &&
                tt.getServiceAt(4).getDepartureTime().equals("09:30"));

        // search() — uses the timetable built above
        check("search() finds R1 at 07:15 → index 0",
                tt.getServiceAt(0) != null &&
                tt.getServiceAt(0).getRouteCode().equals("R1"));

        // Use cancel() to indirectly test search() (cancel calls search internally)
        Service removed = tt.cancel("R2", "08:00");
        check("cancel() (uses search) removes R2 08:00",
                removed != null && removed.getRouteCode().equals("R2"));
        check("cancel() leaves timetable with 4 services",
                tt.getSize() == 4);
        check("cancel() closes the gap — position 2 is now 08:30",
                tt.getServiceAt(2) != null &&
                tt.getServiceAt(2).getDepartureTime().equals("08:30"));
        check("search() returns null-safe miss for nonexistent service",
                tt.cancel("R9", "23:59") == null);
    }

    // ── Task 3: showIncomplete() ──────────────────────────────────────────

    static void testTask3() {
        section("Task 3 — showIncomplete()");

        Timetable tt = new Timetable();
        Service sa = new Service(t1, d1, "07:15", "R1");
        Service sb = new Service(t3, d3, "09:30", "R1");
        Service sc = new Service(t2, d2, "08:00", "R2");
        tt.add(sa); tt.add(sb); tt.add(sc);
        sa.markCompleted();   // 07:15 R1 is done

        System.out.println("  showIncomplete(\"R1\") — expected output: 09:30");
        System.out.print("  Actual output:  ");
        tt.showIncomplete("R1");   // should print only 09:30

        System.out.println("  showIncomplete(\"R2\") — expected output: 08:00");
        System.out.print("  Actual output:  ");
        tt.showIncomplete("R2");   // should print 08:00

        System.out.println("  showIncomplete(\"R9\") — expected output: (nothing)");
        System.out.print("  Actual output:  ");
        tt.showIncomplete("R9");   // should print nothing

        System.out.println("  (visual check — compare expected vs actual above)");
    }

    // ── Task 4: reassignDriver() ──────────────────────────────────────────

    static void testTask4() {
        section("Task 4 — reassignDriver()");

        buildFixtures();   // reset driver availability
        Timetable tt = new Timetable();

        // d1 is on TWO services: one completed, one not
        Service s1 = new Service(t1, d1, "07:15", "R1");   // will be completed
        Service s2 = new Service(t4, d1, "07:45", "R3");   // NOT completed
        Service s3 = new Service(t2, d2, "08:00", "R2");   // different driver
        tt.add(s1); tt.add(s2); tt.add(s3);

        s1.markCompleted();            // 07:15 R1 is done
        d1.setAvailable(false);        // d1 is currently assigned (simulated)

        Driver sub = new Driver("DL099", "Sam Park");
        tt.reassignDriver("DL001", sub);

        Service at0 = tt.getServiceAt(0);
        Service at1 = tt.getServiceAt(1);
        Service at2 = tt.getServiceAt(2);
        check("reassignDriver() leaves completed service unchanged",
                at0 != null && at0.getDriver().getLicenceId().equals("DL001"));
        check("reassignDriver() updates incomplete service with substitute",
                at1 != null && at1.getDriver().getLicenceId().equals("DL099"));
        check("reassignDriver() marks substitute as unavailable",
                !sub.getAvailable());
        check("reassignDriver() marks original driver as available again",
                d1.getAvailable());
        check("reassignDriver() does not touch other drivers (d2 unchanged)",
                at2 != null && at2.getDriver().getLicenceId().equals("DL002"));
    }

    // ── Task 5: mergeDepartures() ─────────────────────────────────────────

    static void testTask5() {
        section("Task 5 — mergeDepartures()");

        buildFixtures();

        LinkedList<Service> north = new LinkedList<>();
        LinkedList<Service> south = new LinkedList<>();

        // North: 07:15 R1,  08:00 R2,  09:30 R1  (already sorted)
        north.addLast(new Service(t1, d1, "07:15", "R1"));
        north.addLast(new Service(t2, d2, "08:00", "R2"));
        north.addLast(new Service(t3, d3, "09:30", "R1"));

        // South: 07:45 R3,  08:30 R4  (already sorted)
        south.addLast(new Service(t4, d1, "07:45", "R3"));
        south.addLast(new Service(t5, d2, "08:30", "R4"));

        LinkedList<Service> merged = TransitUtils.mergeDepartures(north, south);

        // Expected merged order: 07:15 · 07:45 · 08:00 · 08:30 · 09:30
        check("mergeDepartures() returns non-null result", merged != null);
        if (merged == null) return;

        check("mergeDepartures() result has 5 services", merged.size() == 5);
        check("mergeDepartures() [0] is 07:15 R1",
                merged.get(0).getDepartureTime().equals("07:15") &&
                merged.get(0).getRouteCode().equals("R1"));
        check("mergeDepartures() [1] is 07:45 R3",
                merged.get(1).getDepartureTime().equals("07:45") &&
                merged.get(1).getRouteCode().equals("R3"));
        check("mergeDepartures() [2] is 08:00 R2",
                merged.get(2).getDepartureTime().equals("08:00") &&
                merged.get(2).getRouteCode().equals("R2"));
        check("mergeDepartures() [3] is 08:30 R4",
                merged.get(3).getDepartureTime().equals("08:30") &&
                merged.get(3).getRouteCode().equals("R4"));
        check("mergeDepartures() [4] is 09:30 R1",
                merged.get(4).getDepartureTime().equals("09:30") &&
                merged.get(4).getRouteCode().equals("R1"));
        check("mergeDepartures() empties northQueue", north.isEmpty());
        check("mergeDepartures() empties southQueue", south.isEmpty());
    }

    // ── Task 6: ExpressService and showExpressServices() ─────────────────

    static void testTask7() {
        section("Task 6 — ExpressService and showExpressServices()");

        buildFixtures();
        Timetable tt = new Timetable();

        // Mix of regular and express services
        Service      reg1 = new Service(t1, d1, "07:15", "R1");
        ExpressService ex1 = new ExpressService(t2, d2, "08:00", "R2", 3);
        Service      reg2 = new Service(t3, d3, "08:45", "R3");
        ExpressService ex2 = new ExpressService(t4, d1, "09:30", "R1", 7);

        tt.add(reg1); tt.add(ex1); tt.add(reg2); tt.add(ex2);

        // ── Part A: ExpressService class ──────────────────────────────────
        check("ExpressService IS-A Service (instanceof)",
                ex1 instanceof Service);
        check("getPlatformNumber() returns 3 for ex1",
                ex1.getPlatformNumber() == 3);
        check("getPlatformNumber() returns 7 for ex2",
                ex2.getPlatformNumber() == 7);
        check("inherited getDepartureTime() works on ExpressService",
                ex1.getDepartureTime().equals("08:00"));
        check("inherited getRouteCode() works on ExpressService",
                ex2.getRouteCode().equals("R1"));
        check("add() accepts ExpressService in timetable (4 services total)",
                tt.getSize() == 4);

        // ── Part B: showExpressServices() ────────────────────────────────
        System.out.println("  showExpressServices() — expected (express only, in time order):");
        System.out.println("    08:00 R2 [Platform 3]");
        System.out.println("    09:30 R1 [Platform 7]");
        System.out.println("  Actual output:");
        tt.showExpressServices();
        System.out.println("  (regular services 07:15 R1 and 08:45 R3 must NOT appear above)");
    }

    // ── Extension: retrieveTrain() ───────────────────────────────────────

    static void testTask6() {
        section("Extension — retrieveTrain()");

        buildFixtures();
        Stack<Train> yard = new Stack<>();
        Train ta = new Train("T001", 300);   // pushed first → bottom
        Train tb = new Train("T042", 280);   // pushed second → middle
        Train tc = new Train("T099", 350);   // pushed last  → top

        yard.push(ta);
        yard.push(tb);
        yard.push(tc);

        // Retrieve the middle train T042
        TransitUtils.retrieveTrain(yard, "T042");

        check("retrieveTrain() removes exactly one train (size 3 → 2)",
                yard.size() == 2);
        check("retrieveTrain() top of yard is T099 (order preserved)",
                yard.peek().getTrainId().equals("T099"));
        check("retrieveTrain() bottom of yard is T001 (order preserved)",
                yard.get(0).getTrainId().equals("T001"));

        // Retrieving a train not in the yard should leave it unchanged
        Stack<Train> yard2 = new Stack<>();
        Train tx = new Train("T010", 200);
        Train ty = new Train("T020", 200);
        yard2.push(tx); yard2.push(ty);
        TransitUtils.retrieveTrain(yard2, "T999");  // not found
        check("retrieveTrain() leaves stack unchanged when ID not found",
                yard2.size() == 2 && yard2.peek().getTrainId().equals("T020"));
    }
}
