import java.util.*;
/*
 * 
 * BoundedStack คือ ..
 */
public class BoundedStack{
    private final List<String> user;
    private final List<String> password;
    private final int ticket;
    private final List<String> seat;
    //Abstraction Function:
    //AF(User,Ticket,Seat = กดบัตรคอนเสิร์ตแบบตามลำดับ User และ Seat ตามลำดับ)

    // Representation Invariant:
    //RI(User) = ชื่อ User ห้ามซ้ำกัน
    //RI(User) = ไม่มีชื่อ User ที่เป็นสตริงว่าง
    //RI(User) = ตำแหน่ง Seat ต้องไม่ซ้ำกัน
    //RI(User) = User 1 คน ซื้อ Ticket ได้ 1 ใบ
    //RI(User) = 
    //RI(User) =
    // - 
    
    /**
     * 
     * @param capacity
     */
    public BoundedStack(int Ticket){
    this.user = new ArrayList<>();
    this.password = new ArrayList<>();
    this.ticket = Ticket;
    this.seat = new ArrayList<>();
   }


   /**
    * 
    * @param s
    */
   public void push(String  s){

   }



}
