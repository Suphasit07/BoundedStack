import java.util.*;
/*
 * 
 * BoundedStack คือ ..
 */
public class BoundedStack{
    private final List<String> User;
    private final int Ticket;
    private final List<String> Seat;
    //Abstraction Function:
    //AF(User,Ticket = กดบัตรคอนเสิร์ตแบบตามลำดับ User ที่เข้ามาก่อน

    // Representation Invariant:
    //RI(User) = ชื่อ User ห้ามซ้ำกัน
    //RI(User) = ไม่มีชื่อ User ที่เป็นสตริงว่าง
    //RI(User) = 
    // - 
    
    /**
     * 
     * @param capacity
     */
    public BoundedStack(int capacity){
    this.User = new ArrayList<>();
    this.Ticket = capacity;
   }


   /**
    * 
    * @param s
    */
   public void push(String  s){

   }



}
