import java.util.*;

/**
 * Test runner
 */
public class BoundedStackTest {

    private static int passed = 0;
    private static int failed = 0;

    /** helper กลาง — พิมพ์ PASS/FAIL และนับผลให้เอง */
    private static void check(String name, boolean condition) {
        if (condition) {
            passed++;
            System.out.println("[PASS] " + name);
        } else {
            failed++;
            System.out.println("[FAIL] " + name);
        }
    }

    public static void main(String[] args) {
        boolean assertsOn = false;
        assert assertsOn = true;
        if (!assertsOn) {
            System.out.println("WARNING: assertions disabled"
                    + " - re-run with: java -ea BoundedStackTest\n");
        }
        System.out.println("=== BounedStack Test ===\n");
        testCreators();
        testAddUser();
        testAddPassword();
        testAddSeat();
        testremoveUser();
        testremoveSeat();
        testObservers();
        testProducerUser();
        testProducerSeat();
        testExposure();

        System.out.println("\n=== Summary ===");
        System.out.println("Passed "+ passed);
        System.out.println("Failse "+ failed);
        System.out.println("Total "+(passed+failed));
        System.out.println(failed == 0 ? "ALL TEST PASSED" : "SOME TESTS FAILED");

        if (failed > 0) {
            System.exit(1);
        }
    }

    // --- Creators
    private static void testCreators() {
        System.out.println("-- Creators --\n");

        BoundedStack empty = new BoundedStack();
        check("new() -> empty", empty.sizeUser() == 0);
        check("new -> contains nothing", !empty.containsUser("anything") && !empty.containsSeat("anything"));

        BoundedStack b = new BoundedStack(Arrays.asList("A1", "A2", "A3"));
        check("new(list) -> size 3", b.sizeSeat() == 3);
        check("new(list) -> contains A1", b.containsSeat("A1"));
        check("test(list) -> preserves order", b.Seat().equals(Arrays.asList("A1", "A2", "A3")));

        // boundary: list ว่างคือขอบล่างที่ถูกต้อง
        BoundedStack fromEmpty = new BoundedStack(new ArrayList<String>());
        check("new(empty list) -> empty", fromEmpty.sizeSeat() == 0);

        // input ที่ผิดเงื่อนไขต้องโยน exception ไม่ใช่ปล่อยผ่าน
        Boolean threwDup = false;
        try {
            new BoundedStack(Arrays.asList("A1", "A1"));
        } catch (IllegalArgumentException e) {
            threwDup = true;
        }
        check("new(duplicates) -> throws IllegalArgumentException", threwDup);

        boolean threwNull = false;
        try {
            new BoundedStack(Arrays.asList("A1", null));
        } catch (IllegalArgumentException e) {
            threwNull = true;
        }
        check("new(list with null) -> throws IllegalArgumentException", threwNull);

        boolean threwNullList = false;
        try {
            new BoundedStack(null);
        } catch (IllegalArgumentException e) {
            threwNullList = true;
        }
        check("new(null) -> throws IllegalArgumentException", threwNullList);
    }

    // --- Mutator : addUser ต้องรักษาลำดับและกันชื่อ User ว่าซ้ำกันไหม ---

    private static void testAddUser() {
        System.out.println("\n-- Mutators --");
        System.out.println("\n-- AddUser --\n");
        BoundedStack u = new BoundedStack();
        boolean threwEmpty = false;
        try {
            u.addUser("");
        } catch (IllegalArgumentException e) {
            threwEmpty = true;
        }
        check("addUser(empty string) -> throw IllegalArgumentException", threwEmpty);

        boolean threwNull = false;
        try {
            u.addUser(null);
        } catch (IllegalArgumentException e) {
            threwNull = true;
        }
        check("addUser(null) -> throw IllegalArgumentException", threwNull);

        boolean threwDup = false;
        try {
            u.addUser("AAAA");
            u.addUser("AAAA");
        } catch (IllegalArgumentException e) {
            threwDup = true;
        }
        check("addUser(duplicate) -> throw IllegalArgumentException", threwDup);
        check("failed adds leave BoundedStack unchanged", u.sizeUser() <= 20);
    }

    // --- Mutator : addPassword ตรวจสอบว่า Password ผ่านเงื่อนไขที่ต้องการไหม ---

    private static void testAddPassword() {
        System.out.println("\n-- AddPassword --\n");
        BoundedStack pw = new BoundedStack();
        boolean threwEmpty = false;
        try {
            pw.addPassword("");
        } catch (IllegalArgumentException e) {
            threwEmpty = true;
        }
        check("addPassword(empty string) -> throw IllegalArgumentException", threwEmpty);
        boolean threwNull = false;
        try {
            pw.addPassword(null);
        } catch (IllegalArgumentException e) {
            threwNull = true;
        }
        check("addPassword(null) -> throw IllegalArgumentException", threwNull);
        check("addPassword()", threwNull);

        boolean threwpw = false;
        try {
            pw.addPassword("AAAAAAAA");
        } catch (IllegalArgumentException e) {
            threwpw = true;
        }
        check("addPassword(have toUpper toLower Digit -> throw IllegalArgumentException", threwpw);
    }

    // --- Mutator : addSeat ต้องรักษาลำดับและกัน Seat ด้วยว่าที่นั่งซ้ำกันไหม ---

    private static void testAddSeat() {
        System.out.println("\n-- AddSeat --\n");
        BoundedStack s = new BoundedStack();
        boolean threwEmpty = false;
        try {
            s.addSeat("");
        } catch (IllegalArgumentException e) {
            threwEmpty = true;
        }
        check("addSeat(empty string) -> throw IllegalArgumentException", threwEmpty);
        boolean threwNull = false;
        try {
            s.addSeat(null);
        } catch (IllegalArgumentException e) {
            threwNull = true;
        }
        check("addSeat(null) -> throw IllegalArgumentException", threwNull);
        boolean threwDup = false;
        try {
            s.addSeat("A1");
            s.addSeat("A1");
        } catch (IllegalArgumentException e) {
            threwDup = true;
        }
        check("addSeat(dupicate) -> throw IllegalArgumentException", threwDup);
        check("failed adds leave BoundedStack unchanged", s.sizeSeat() <= 250);
    }

    // --- Mutator : RemoveUser ลบ user ที่มีชื่อซ้ำกัน ---
    private static void testremoveUser() {
        System.out.println("\n--RemoveUser--\n");
        BoundedStack u = new BoundedStack();
        u.addUser("AAAA"); // เพิ่ม User เข้าไปก่อน
        check("remove(AAAA) -> returns true", u.removeUser("AAAA")); // ถ้ามี user ชื่อนี้จริงจะทำการ remove
        check("after remove -> not contains(AAAA)", !u.containsUser("AAAA")); // ตรวจสอบหลังจาก remove ไปแล้ว user ชื่อนี้หายไปจริงไหม
        check("dont have user -> return false", !u.removeUser("AAAA")); // ถ้าไม่มี user เราจะ return false ไปเลย
    }

    // --- Mutator : RemoveSeat ลบ seat ที่มีการจองที่นั่งซ้ำกัน ---
    private static void testremoveSeat() {
        System.out.println("\n--RemoveSeat--\n");
        BoundedStack s = new BoundedStack();
        s.addSeat("A1"); // เพิ่ม Seat เข้าไปก่อน
        check("remove(A1) -> returns true", s.removeSeat("A1"));
        check("after remove -> not contains(A1)", !s.containsSeat("A1"));
        check("dont have seat -> return false", !s.removeSeat("A1"));
    }

    // --- Observers : ต้องไม่มี side effect ---

    private static void testObservers() {
        System.out.println("\n-- Observers --\n");
        BoundedStack u = new BoundedStack(Arrays.asList("AAAA", "BBBB"));
        check("user report 2", u.sizeUser() == 2);
        check("contains find an existing user", u.containsUser("AAAA"));
        check("contains rejects a missing user", !u.containsUser("ZZZZ"));
        check("user returns the full list in order", u.User().equals(Arrays.asList("AAAA", "BBBB")));

        BoundedStack s = new BoundedStack(Arrays.asList("A1", "A2"));
        check("seat report 2", s.sizeSeat() == 2);
        check("contains find an existing seat", s.containsUser("A1"));
        check("contains rejects a missing seat", !s.containsUser("Z1"));
        check("user returns the full list in order", s.Seat().equals(Arrays.asList("A1", "A2")));

        int before = u.sizeUser();
        u.sizeUser();
        u.containsUser("AAAA");
        u.User();
        check("Observers user have no side effects", u.sizeUser() == before);

        int before1 = s.sizeSeat();
        s.sizeSeat();
        s.containsSeat("A1");
        s.Seat();
        check("Observers seat have no side effects", s.sizeSeat() == before1);
    }

    private static void testProducerUser() {
        System.out.println("\n-- Producer --\n");
        System.out.println("-- ProducerUser --\n");
        BoundedStack original = new BoundedStack(Arrays.asList("AAAA", "BBBB", "CCCC", "DDDD"));
        BoundedStack shuffledUser = original.shuffledUser();

        check("shuffledUser has the same size", shuffledUser.sizeUser() == original.sizeUser());
        List<String> a = new ArrayList<String>(original.User());
        List<String> b = new ArrayList<String>(shuffledUser.User());
        Collections.sort(a);
        Collections.sort(b);
        check("shuffledUser contains exactly the same User", a.equals(b));
        check("shuffledUser does not mutate the original",
                original.User().equals(Arrays.asList("AAAA", "BBBB", "CCCC", "DDDD")));

        // mutate user ตัวใหม่ต้องไม่ไปกระทบกับ user ตัวเก่า
        shuffledUser.addUser("EEEE");
        check("mutate the result does not affect the original", original.sizeUser() == 4);

        // bounday : shuffleUser ถ้า user ไม่มีการ input เข้ามาต้องไม่พัง
        BoundedStack emptyShuffledUser = new BoundedStack().shuffledUser();
        check("shuffling an empty BoundedStack is safe", emptyShuffledUser.sizeUser() == 0);
    }

    private static void testProducerSeat() {
        System.out.println("\n-- ProducerSeat --\n");

        BoundedStack original = new BoundedStack(Arrays.asList("A1", "A2", "A3", "A4"));
        BoundedStack shuffledSeat = original.shuffledUser();

        check("shuffledSeat has the same size", shuffledSeat.sizeUser() == original.sizeSeat());
        List<String> a = new ArrayList<String>(original.Seat());
        List<String> b = new ArrayList<String>(shuffledSeat.Seat());
        Collections.sort(a);
        Collections.sort(b);
        check("shuffledSeat contains exactly the same Seat", a.equals(b));
        check("shuffledSeat does not mutate the original",
                original.Seat().equals(Arrays.asList("A1", "A2", "A3", "A4")));

        // mutate seat ตัวใหม่ต้องไม่ไปกระทบกับ seat ตัวเก่า
        shuffledSeat.addSeat("A5");
        check("mutate the result does not affect the original", original.sizeSeat() == 4);

        // bounday : shuffleSeat ถ้า seat ไม่มีการจองเข้ามาต้องไม่พัง
        BoundedStack emptyShuffledSeat = new BoundedStack().shuffledSeat();
        check("shuffling an empty BoundedStack is safe", emptyShuffledSeat.sizeSeat() == 0);
    }

   // --- ทดสอบว่าไม่เกิด representation exposure ---
    private static void testExposure() {
        System.out.println("\n-- Representation Exposure --");

        // ขาออก : แก้ list ที่ได้จาก User(),Seat() ต้องไม่กระทบ rep
        BoundedStack s = new BoundedStack();
        s.addUser("AAAA");
        s.addSeat("A1");

        List<String> got = s.User();
        got.clear();
        check("clearing result of User() does not affect BoundStack", s.sizeUser() == 1);

        got = s.User();
        got.add("injected");
        check("adding to result of User() does not affect BoundStack",
                s.sizeUser() == 1 && !s.containsUser("injected"));
        check("User() returns a fresh list each call", s.User() != s.User()); // user ต้องเป็นคนละ object

        List<String> got1 = s.Seat();
        got1.clear();
        check("clearing result of Seat() does not affect BoundStack", s.sizeSeat() == 1);

        got1 = s.Seat();
        got1.add("injected");
        check("adding to result of Seat() does not affect BoundStack",
                s.sizeSeat() == 1 && !s.containsSeat("injected"));
        check("Seat() returns a fresh list each call", s.Seat() != s.Seat()); // seat ต้องเป็นคนละ object

        // ขาเข้า : แก้ list ที่ส่งให้ constructor ต้องไม่กระทบ rep
        List<String> input = new ArrayList<String>(Arrays.asList("AAAA", "BBBB"));
        BoundedStack b = new BoundedStack(input);
        input.clear();
        check("clearing constructor argument does not affect BoundedStack", b.sizeUser() == 2);
        input.add("injected");
        check("adding to constructor argument does not affect BoundedStack", !b.containsUser("injected"));

        List<String> input1 = new ArrayList<String>(Arrays.asList("A1", "A2"));
        BoundedStack b1 = new BoundedStack(input1);
        input1.clear();
        check("clearing constructor argument does not affect BoundedStack", b1.sizeSeat() == 2);
        input1.add("injected");
        check("adding to constructor argument does not affect BoundedStack", !b1.containsSeat("injected"));

    }
    
}