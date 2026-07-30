import java.util.*;
/*
 * BoundedStack คือ ระบบจองที่นั่งโดยที่ user ต้องไม่ซ้ำกันและสร้าง password ขึ้นมาเป็นของตัวเอง แล้วห้ามจองที่นั่งเดียวกัน
 */
// จิรายุ เขียวภักดี 6821651116
// ศุภธิษณ์ กอประเสริฐสุด 6821651825
public class BoundedStack {
    private final List<String> User;
    private final List<String> Password;
    private final List<String> Seat;
    private static final int MAX_USER = 20;
    private static final int MAX_PASSWORD = 8;
    private static final int MAX_SEAT = 250;
    // Abstraction Function:
    // AF(user,password,seat = จองที่นั่งแบบตามลำดับที่ขึ้นอยู่กับ User และ Seat)

    // Representation Invariant:
    // ชื่อ user ห้ามซ้ำกันและชื่อ user ห้ามเกิน 20 ตัว
    // ไม่มีชื่อ user,password,seat ที่เป็นสตริงว่าง
    // password ต้องมีมากกว่า 8 ตัว และต้องไม่เป็นช่องว่าง
    // password ต้องมีทั้งตัวเลข ตัวอักษรพิมพ์เล็กพิมพ์ใหญ๋ผสมกัน
    // ตำแหน่ง Seat ต้องไม่ซ้ำกันและมีได้ไม่เกิน 250;
    // user 1 คน จองที่นั่งได้ 1 ที่

    // safety form rep exposure:
    // สร้าง Private final เพื่อไม่ให้เเก้ไขได้
    // user เป็น private final
    // password เป็น private final
    // seat เป็น private final
    // คัดลอกทั้งขาเข้าขาออก

    public void CheckRep() {
        assert User != null : "user is not null";
        assert Password != null : "password is not null";
        assert Seat != null : "seat is not null";
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
    }

    private static boolean validate(String password) {
        if (password == null || password.length() < MAX_PASSWORD)
            return false;
        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;
        for (int i = 0; i < password.length(); i++) {
            Character c = password.charAt(i);
            Character d = password.charAt(i);
            if (Character.isUpperCase(c))
                hasUpper = true;
            if (Character.isLowerCase(c))
                hasLower = true;
            if (Character.isDigit(d))
                hasDigit = true;
        }
        return hasUpper && hasLower && hasDigit;
    }

    // ===== Creator =====
    /**
     * สร้าง Accout User กับ Password เพื่อจอง Seat
     * 
     * @param UserAndSeat ต้องไม่เป็น Null , String ว่าง
     * @throws IllegalArgumentException ถ้าซ้ำกันจะโยน Exception
     */
    public BoundedStack() {
        this.User = new ArrayList<>();
        this.Password = new ArrayList<>();
        this.Seat = new ArrayList<>();
        CheckRep();
    }

    public BoundedStack(List<String> UserAndSeat) {
        this.User = new ArrayList<>();
        this.Password = new ArrayList<>();
        this.Seat = new ArrayList<>();
        if (UserAndSeat == null)
            throw new IllegalArgumentException("UserandSeat is not null");
        if (UserAndSeat.size() > MAX_SEAT)
            throw new IllegalArgumentException("UserandSeat dont have > MAX_SEAT(250)");
        if (UserAndSeat.size() < 0)
            throw new IllegalArgumentException("UserandSeat dont have < 0");
        Set<String> seen = new HashSet<>();
        for (String s : UserAndSeat) {
            if (s == "")
                throw new IllegalArgumentException("s is not empty");
            if (s == null)
                throw new IllegalArgumentException("s is not null");
            if (!seen.add(s))
                throw new IllegalArgumentException("s have duplicate");
        }
        this.User.addAll(UserAndSeat);
        this.Seat.addAll(UserAndSeat);
        CheckRep();
    }

    // ===== Mutators =====

    /**
     * @param user     ต้องไม่เป็น null และไม่เป็นสตริงช่องว่าง user
     *                 ต้องมีตัวอักษรไม่เกิน 20 ตัว ตรวจสอบว่า user
     *                 นี้มีคนจองไปยัง(1 user 1 seat) user ต้องมีน้อยกว่าที่นั่ง
     * @param seat     ต้องไม่เป็น null และไม่เป็นสตริงช่องว่าง ตรวจสอบว่า seat
     *                 นี้มีคนจองไปยัง(1 user 1 seat)
     * @param password เช็คว่าผ่านเงื่อนไขในการตั้งไหม
     * @throws IllegalArgumentException ถ้า user seat password ไม่เป็นไปตามเงื่อนไขที่ต้องการ
     * @return return true ถ้าเพิ่มสำเร็จ , false ถ้าเพิ่มไม่สำเร็จ
     */
    public void push(String user, String password, String seat) {
        if (user == null || user.isEmpty())
            throw new IllegalArgumentException("user is not null and user is not empty");
        if (user.length() > MAX_USER)
            throw new IllegalArgumentException("user dont have length > 20");
        if (User.contains(user))
            throw new IllegalArgumentException("user dont have duplicate");
        if (!validate(password))
            throw new IllegalArgumentException("password must be miss condition");
        if (seat == null || seat.isEmpty())
            throw new IllegalArgumentException("seat is not null and seat not is empty");
        if (Seat.contains(seat))
            throw new IllegalArgumentException("seat dont have duplicate");
        if (User.size() > MAX_SEAT)
            throw new IllegalArgumentException("User dont have > MAX_SEAT(250)");
        User.add(user);
        Password.add(password);
        Seat.add(seat);
        CheckRep();
    }

    /**
     * @param ยกเลิกการจองที่นั่ง จะทำการลบ user,password,seat ที่อยู่คนท้ายสุดออก
     * @return true ถ้าสำเร็จ , false ถ้าไม่เสร็จ
     * @throws IllegalArgumentException ถ้าไม่มีการจอง
     */

    public boolean pop() {
        if (isEmpty()) {
            throw new IllegalArgumentException("User and Seat is not empty");
        }
        int last = User.size() - 1;
        User.remove(last);
        Password.remove(last);
        Seat.remove(last);
        CheckRep();
        return true;
    }

    // ===== Observers =====

    /**
     * ตรวจสอบการจองที่นั่งที่สุดท้าย โดยไม่ลบ
     * @return ชื่อ user คนสุดท้ายที่จอง
     * @throws IllegalArgumentException ถ้า user ไม่จองที่นั่ง
     */

    public String peek() {
        if (User.isEmpty())
            throw new IllegalArgumentException("User is not empty");
        int last = User.size() - 1;
        return User.get(last);
    }

    /**
     * ตรวจสอบชื่อ User และ Seat ว่ามีอยู่จริงไหม
     */

    public boolean containsUser(String user) {
        return (User.contains(user));
    }

    public boolean containsSeat(String seat) {
        return (Seat.contains(seat));
    }

    /**
     * คืนจำนวน user ทั้งหมด
     */
    public int sizeUser() {
        return User.size();
    }

    /**
     * คืนจำนวนที่นั่งทั้งหมด
     */

    public int sizeSeat() {
        return Seat.size();
        
    }

    public boolean isEmpty() {
        return User.isEmpty();
    }

    /**
     * คืนจำนวน User ทั้งหมดตามลำดับ
     */
    public List<String> User() {
        return new ArrayList<>(User);
    }

    /**
     * คืนจำนวนที่นั่งทั้งหมดตามลำดับ
     */

    public List<String> Seat() {
        return new ArrayList<>(Seat);
    }

    // ===== Producer =====
    /**
     * เปลี่ยนชื่อ user ใหม่ 
     * ระวัง : ห้ามแก้ลิสต์ผู้ใช้เดิม (User) เด็ดขาด
     * @param changeUserName ถ้าชื่อ user ใหม่ซ้ำกับชื่อ user ที่มีอยู่แล้วหรือชื่อที่ซ้ำกัน จะทำการ throw new    IllegalArgumentException
     * @return ชื่อ user ที่เปลี่ยนใหม่แล้ว
     */
    public BoundedStack changeUserName(String oldName,String changeName){
        if(oldName == null || oldName.isEmpty() || !User.contains(oldName))
            throw new IllegalArgumentException("oldname is not null and empty and User dont has the same oldName");
        if(changeName == null || changeName.isEmpty() || changeName.length() > MAX_USER)
            throw new IllegalArgumentException("changeName is not null and empty and have > MAX_USER(20)");
        if(User.contains(changeName))
            throw new IllegalArgumentException("User has not the same changeName");

        BoundedStack newUser = new BoundedStack();
        for (int i = 0; i < User.size(); i++) {
            String name;
            if(User.get(i).equals(oldName)){
                name = changeName;
            }else{
                name = User.get(i);
            }
            newUser.push(name, Password.get(i), Seat.get(i));
        }
        return newUser;
    }

}
