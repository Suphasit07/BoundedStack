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
        testPush();
        testPop();
        testPeek();
        testObservers();
        testProducerUser();
        testExposure();
        System.out.println("\n=== Summary ===");
        System.out.println("Passed " + passed);
        System.out.println("Failse " + failed);
        System.out.println("Total " + (passed + failed));
        System.out.println(failed == 0 ? "ALL TEST PASSED" : "SOME TESTS FAILED");
        if (failed > 0) {
            System.exit(1);
        }
    }

    // --- Creators ---
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

    // --- Mutator ---
    // testpush ตรวจสอบว่า user,seat มีเป็น null หรือ สตริงว่างไหม และ มีซ้ำกันไหม
    private static void testPush() {
        System.out.println("\n-- Mutators --");
        System.out.println("\n-- Push --\n");
        BoundedStack bs = new BoundedStack();
        bs.push("AAAABBB", "ABCd1234", "A1");
        check("add user password seat succeed -> size 1", bs.sizeUser() == 1);
        check("containsUser", bs.containsUser("AAAABBB"));
        check("containsSeat", bs.containsSeat("A1"));
        boolean threwEmptyUser = false;
        try {
            bs.push("", "ABCD1234", "A1");
        } catch (IllegalArgumentException e) {
            threwEmptyUser = true;
        }
        check("addUser(empty string) -> throw IllegalArgumentException", threwEmptyUser);
        boolean threwEmptySeat = false;
        try {
            bs.push("AAABBBB", "ABCD1234", "");
        } catch (IllegalArgumentException e) {
            threwEmptySeat = true;
        }
        check("addSeat(empty string) -> throw IllegalArgumentException", threwEmptySeat);
        boolean threwNullUser = false;
        try {
            bs.push(null, "ABCD1234", "A2");
        } catch (IllegalArgumentException e) {
            threwNullUser = true;
        }
        check("addUser(null) -> throw IllegalArgumentException", threwNullUser);
        boolean threwNullSeat = false;
        try {
            bs.push("ABCDEFG", "ABCD1234", "A2");
        } catch (IllegalArgumentException e) {
            threwNullSeat = true;
        }
        check("addSeat(null) -> throw IllegalArgumentException", threwNullSeat);
        boolean threwDupUser = false;
        try {
            bs.push("AAAABBB", "ABCD1234", "A2");
        } catch (IllegalArgumentException e) {
            threwDupUser = true;
        }
        check("addUser(duplicate) -> throw IllegalArgumentException", threwDupUser);
        boolean threwDupSeat = false;
        try {
            bs.push("AAABBBB", "ABCD1234", "A1");
        } catch (IllegalArgumentException e) {
            threwDupSeat = true;
        }
        check("addSeat(duplicate) -> throw IllegalArgumentException", threwDupSeat);
        check("failed adds leave BoundedStack unchanged", bs.sizeUser() < 20);
    }

    // testpop ยกเลิกการจองที่นั่งสุดท้าย
    private static void testPop() {
        System.out.println("\n-- Pop --\n");
        BoundedStack bs = new BoundedStack();
        boolean threwEmpty = false;
        try {
            bs.pop();
        } catch (IllegalArgumentException e) {
            threwEmpty = true;
        }
        check("user cancel seat", threwEmpty);
    }

    // --- Observers : ต้องไม่มี side effect ---
    // testPeek ตรวจสอบ user ที่จองที่นั่งคนสุดท้าย โดยไม่ลบ user ออก
    private static void testPeek() {
        System.out.println("\n-- Observers --\n");
        System.out.println("\n-- Peek --\n");
        BoundedStack bs = new BoundedStack();
        boolean threwEmpty = false;
        try {
            bs.peek();
        } catch (IllegalArgumentException e) {
            threwEmpty = true;
        }
        check("check last user not delete", threwEmpty);
    }

    // testObservers ตรวจสอบ ชื่อ User กับ Seat ว่ามีอยู่จริงไหม
    private static void testObservers() {
        System.out.println("\n-- testObservers --\n");
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

    // -- Producer --
    // ProducerUser เปลี่ยนชื่อ User
    private static void testProducerUser() {
        System.out.println("\n-- Producer --\n");
        System.out.println("-- ProducerUser --\n");
        BoundedStack bs = new BoundedStack();
        boolean threwName = false;
        try {
            bs.changeUserName("AAAABBB", "BBBBAAA");
        } catch (IllegalArgumentException e) {
            threwName = true;
        }
        check("change nameUser succeed",threwName);
        
    }

    // --- ทดสอบว่าไม่เกิด representation exposure ---
    private static void testExposure() {
        System.out.println("\n-- Representation Exposure --\n");
        // ขาออก : แก้ list ที่ได้จาก User(),Seat() ต้องไม่กระทบ rep
        BoundedStack s = new BoundedStack();
        s.push("AAAA", "abcD1234", "A1");
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
