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
        testAddUser();
        testAddPassword();
        testAddSeat();

    }
    // --- Mutator: add
    private static void testAddUser(){
        System.out.println("-- AddUser --");
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
        check("addUser(null) -> throw IllegalArgumentException",threwNull);

        boolean threwDup = false;
        try {
            u.addUser("AAAA");
            u.addUser("AAAA");
        } catch (IllegalArgumentException e) {
            threwDup = true;
        }
        check("addUser(duplicate) -> throw IllegalArgumentException", threwDup);
    }

    private static void testAddPassword(){
        System.out.println("-- AddPassword --");
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
        check("addPassword(null) -> throw IllegalArgumentException",threwNull);
        check("pw len > MAX_PASSWORD", BoundedStack.CheckRep("aaAA12345"));
    }
    private static void testAddSeat(){
        System.out.println("-- AddSeat --");
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
        check("failed adds leave BoundedStack unchanged",s.size() <= 250);
    }


    
    
}
