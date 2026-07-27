import java.util.*;
/*
 * 
 * BoundedStack คือ 
 */
// จิรายุ เขียวภักดี 6821651116
// ศุภธิษณ์ กอประเสริฐสุด 6821651825

public class BoundedStack {
    private final List<String> User;
    private final List<String> Password;
    private final int Ticket;
    private final List<String> Seat;
    public static final int MAX_USER = 10;
    public static final int MAX_PASSWORD = 8;
    public static final int MAX_TICKET = 250;
    public static final int MAX_SEAT = 250;
    // Abstraction Function:
    // AF(user,password,ticket,seat = กดบัตรคอนเสิร์ตแบบตามลำดับ User และ Seat
    // ตามลำดับ)

    // Representation Invariant:
    // ชื่อ user ห้ามซ้ำกันและชื่อ user ห้ามเกิน 10 ตัว
    // ไม่มีชื่อ user,password,seat ที่เป็นสตริงว่าง
    // password ต้องมีมากกว่า 8 ตัว และต้องไม่เป็นช่องว่าง
    // password ต้องมีทั้งตัวเลข ตัวอักษรพิมพ์เล็กพิมพ์ใหญ๋ผสมกัน
    // ตำแหน่ง Seat ต้องไม่ซ้ำกันและมีได้ไม่เกิน 250;
    // ticket ซื้อได้ไม่เกิน 250 ตั๋ว
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
        assert User != null : "user is not null";
        assert Seat != null : "seat is not null";
        assert Ticket >= 0;
        assert Ticket <= MAX_TICKET;
        assert User.size() <= MAX_USER;
        assert Seat.size() <= MAX_SEAT;
        Set<String> seenUser = new HashSet<>();
        for (String u : User) {
            assert u != null;
            assert !u.isEmpty();
            assert seenUser.add(u);
        }
        Set<String> seenSeat = new HashSet<>();
        for (String s : Seat) {
            assert s != null;
            assert !s.isEmpty();
            assert seenSeat.add(s);
        }
        for (String pw : Password) {
            assert pw != null : "password is not null";
            assert !pw.isEmpty();
            assert pw.length() >= MAX_PASSWORD;

            boolean hasUpper = false;
            boolean hasLower = false;
            boolean hasDigit = false;
            for (int i = 0; i < pw.length(); i++) {
                Character c = pw.charAt(i);
                Character d = pw.charAt(i);
                if (Character.isUpperCase(c))
                    hasUpper = true;
                if (Character.isLowerCase(c))
                    hasLower = true;
                if (Character.isDigit(d))
                    hasDigit = true;
            }
            assert hasUpper && hasLower && hasDigit;
        }
    }
    // ===== Creator =====
    /**
     * 
     * @param capacity
     */

    public BoundedStack(List<String> UserAndSeat,int ticket){
        this.User = new ArrayList<>();
        this.Password = new ArrayList<>();
        this.Ticket = ticket;
        this.Seat = new ArrayList<>();
        if(UserAndSeat == null) throw new IllegalArgumentException();
        if(UserAndSeat.size() > MAX_SEAT) throw new IllegalArgumentException();
        if(UserAndSeat.size() < 0) throw new IllegalArgumentException();
        Set<String> seenUser = new HashSet<>();
        for(String s : UserAndSeat){
            if(s == "") throw new IllegalArgumentException();
            if(s == null) throw new IllegalArgumentException();
            if(!seenUser.add(s)) throw new IllegalArgumentException();
        }
        Set<String> seenSeat = new HashSet<>();
        for(String s : UserAndSeat){
            if(s == "") throw new IllegalArgumentException();
            if(s == null) throw new IllegalArgumentException();
            if(!seenSeat.add(s)) throw new IllegalArgumentException();
        }
        CheckRep();
    }

    // ===== Mutators =====

    /**
     * Mutators
     * @param s
     */
    public boolean add(String user,String password,String seat) {
        if(user == null || user.isEmpty()) // เช็คว่า user เป็น null กับ ช่องว่างไหม
            throw new IllegalArgumentException();
        if(password == null || password.isEmpty()) // เช็คว่า password เป็น null กับ ช่องว่างไหม
            throw new IllegalArgumentException();
        if(this.Password.size() < MAX_PASSWORD)// เช็คว่า password มีมากกว่า 8 ตัวไหม
            throw new IllegalArgumentException();
        if(seat == null || seat.isEmpty()) // เช็คว่า seat เป็น null กับ ช่องว่างไหม
            throw new IllegalArgumentException();
        if(this.User.contains(user)) //ตรวจสอบว่า user คนนี้ซื้อตั๋วไปยัง(user 1 คนต่อ 1 ticket)
            throw new IllegalArgumentException();
        if(this.Seat.contains(seat)) //ตรวจสอบว่า seat มีที่นั่งยัง(seat ห้ามซ้ำกัน)
            throw new IllegalArgumentException();
        if(this.Seat.size() >= MAX_SEAT) //ตรวจสอบว่า seat มีที่นั่งเกินไหม
            throw new IllegalArgumentException();
        if(this.Seat.size() >= MAX_TICKET) //ตรวจสอบว่า seat มีที่นั่งมากกว่า ticket ไหม
            throw new IllegalArgumentException();
        this.User.add(user); //user ที่เพิ่มเข้ามานำไปต่อใน list user
        this.Password.add(password);//password ที่เพิ่มเข้ามานำไปต่อใน list password
        this.Seat.add(seat);//password ที่เพิ่มเข้ามานำไปต่อใน list password
        CheckRep();
        return true;
    }

    public boolean remove(String user,String password,String seat) {
        if(!(User.contains(user))) return false;
        User.remove(user);
        if(!(Seat.contains(seat))) return false;
        Seat.remove(seat);
        CheckRep();
        return true;
    }

    // ===== Observers =====

    /**
     * ตรวจสอบชื่อ User และ Seat ว่ามีอยู่จริงไหม
     */

    public boolean contains(String user,String seat) {
        return (User.contains(user) || Seat.contains(seat));
    }

    /**
     * คืนจำนวนที่นั่งทั้งหมด
     */

    public int size(){
        return Seat.size();
    }

    /**
     * คืนจำนวนที่นั่งทั้งหมดตามลำดับ
     */

    public List<String> Seat(){
        return new ArrayList<>(Seat);
    }

    // ===== Producer =====
}
