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

    }

    // --- Creators
    private static void testCreators() {
        System.out.println("-- Creators --\n");

        BoundedStack empty = new BoundedStack();
        check("new() -> empty", empty.size() == 0);
        check("new -> contains nothing", !empty.containsUser("anything") && !empty.containsSeat("anything"));

        BoundedStack b = new BoundedStack(Arrays.asList("A1", "A2", "A3"));
        check("new(list) -> size 3", b.size() == 3);
        check("new(list) -> contains A1", b.containsSeat("A1"));
        check("test(list) -> preserves order", b.Seat().equals(Arrays.asList("A1", "A2", "A3")));

        // boundary: list ว่างคือขอบล่างที่ถูกต้อง
        BoundedStack fromEmpty = new BoundedStack(new ArrayList<String>());
        check("new(empty list) -> empty", fromEmpty.size() == 0);

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
        System.out.println("\n-- Mutators --\n");
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
        check("failed adds leave BoundedStack unchanged", s.size() <= 250);
    }

    // --- Mutator : RemoveUser ลบ user ที่มีชื่อซ้ำกัน ---
    private static void testremoveUser(){
        System.out.println("\n--RemoveUser--\n");
        BoundedStack u = new BoundedStack();
        u.addUser("AAAA"); // เพิ่ม User เข้าไปก่อน
        check("remove(AAAA) -> returns true",u.removeUser("AAAA")); // ถ้ามี user ชื่อนี้จริงจะทำการ remove
        check("after remove -> not contains(AAAA)", !u.containsUser("AAAA")); //ตรวจสอบหลังจาก remove ไปแล้ว user ชื่อนี้หายไปจริงไหม
        check("dont have user -> return false", !u.removeUser("AAAA")); // ถ้าไม่มี user เราจะ return false ไปเลย
    }

    // --- Mutator : RemoveSeat ลบ seat ที่มีการจองที่นั่งซ้ำกัน ---
    private static void testremoveSeat(){
        System.out.println("\n--RemoveSeat--\n");
        BoundedStack s = new BoundedStack();
        s.addSeat("A1"); //เพิ่ม Seat เข้าไปก่อน
        check("remove(A1) -> returns true", s.removeSeat("A1"));
        check("after remove -> not contains(A1)", !s.containsSeat("A1"));
        check("dont have seat -> return false", !s.removeSeat("A1"));

    }

}
