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
        
        System.out.println("=== Playlist Test Suit ===\n");
        testCreators();
    }
    
    //------
    private static void testCreators() {
        System.out.println("-- Creators --");

        BoundedStack empty = new BoundedStack();
        check("new() -> empty",empty.size() == 0);
        check("new -> contains nothing",!empty.contains( "anything","anything"));

        BoundedStack b = new BoundedStack(Arrays.asList("A1","A2","A3"));
        check("new(list) -> size 3", b.size() == 3);
        check("new(list) -> contains 2",b.contains(null, null));
        check("test(list) -> preserves order",b.Seat().equals(Arrays.asList("A1","A2","A3")));

        // boundary: list ว่างคือขอบล่างที่ถูกต้อง
        BoundedStack fromEmpty = new BoundedStack(new ArrayList<String>());
        check("new(empty list) -> empty", fromEmpty.size() == 0);

         // input ที่ผิดเงื่อนไขต้องโยน exception ไม่ใช่ปล่อยผ่าน
        Boolean threwDup = false;
        try {
            new BoundedStack(Arrays.asList("A1","A1"));
        } catch (IllegalArgumentException e) {
            threwDup = true;
        }
        check("new(duplicates) -> throws IllegalArgumentException", threwDup);

        boolean threwNull = false;
        try {
            new BoundedStack(Arrays.asList("A1",null));
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
}
