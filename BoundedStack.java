import java.util.*;
/*
 * 
 * BoundedStack คือ 
 */
// จิรายุ เขียวภักดี 6821651116
// ศุภธิษณ์ กอประเสริฐสุด 6821651825

public class BoundedStack {
    private final List<String> user;
    private final List<String> password;
    private final int ticket;
    private final List<String> seat;
    public static final int MAX_USER = 10;
    public static final int MAX_PASSWORD = 8;
    public static final int MAX_SEAT = 250;
    // Abstraction Function:
    // AF(user,password,ticket,seat = กดบัตรคอนเสิร์ตแบบตามลำดับ User และ Seat
    // ตามลำดับ)

    // Representation Invariant:
    // ชื่อ user ห้ามซ้ำกันและชื่อ user ห้ามเกิน 10 ตัว
    // ไม่มีชื่อ user,password,seat ที่เป็นสตริงว่าง
    // password ต้องมีมากกว่า 8 ตัว และต้องไม่เป็นช่องว่าง
    // password ต้องมีทั้งตัวเลข ตัวอักษรพิมพ์เล็กพิมพ์ใหญ๋ผสมกัน
    // ตำแหน่ง Seat ต้องไม่ซ้ำกันมีได้ไม่เกิน 250;
    // user 1 คน ซื้อ Ticket ได้ 1 ใบ
    //

    // safety form rep exposure:
    // สร้าง Private final เพื่อไม่ให้เเก้ไขได้
    // user เป็น private final
    // password เป็น private final
    // ticket เป็น private final
    // seat เป็น private final
    // คัดลอกทั้งขาเข้าขาออก
    //

    private void CheckRep() {
        assert user != null : "user is not null";
        assert password != null : "password is not null";
        assert seat != null : "seat is not null";
        assert password.size() <= MAX_PASSWORD;
        assert seat.size() <= MAX_SEAT;
        Set<String> seen = new HashSet<>();
        for (String u : user) {
            assert u != null;
            assert u != "";
            assert seen.add(u);
        }
        for (String pw : seen) {
            assert pw != null;
            assert pw != "";
            assert (pw != pw.toLowerCase() || pw != pw.toUpperCase());
            assert seen.add(pw);
        }

    }

    /**
     * 
     * @param capacity
     */
    public BoundedStack(int Ticket) {
        this.user = new ArrayList<>();
        this.password = new ArrayList<>();
        this.ticket = Ticket;
        this.seat = new ArrayList<>();
    }

    /**
     * 
     * @param s
     */
    public void push(String s) {

    }

   public void pop(String s){

   }

   public void observe(String s)



}
