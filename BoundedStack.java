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
    //ชื่อ User ห้ามซ้ำกัน
    //ไม่มีชื่อ User ที่เป็นสตริงว่าง
    //ตำแหน่ง Seat ต้องไม่ซ้ำกัน
    //User 1 คน ซื้อ Ticket ได้ 1 ใบ
    //Password ห้ามน้อยกว่า8ตัว 
    //

    // safety form rep exposure:
    //สร้าง Private final เพื่อไม่ให้เเก้ไขได้
    //user เป็น private final
    //password เป็น private final
    //คัดลอกทั้งขาเข้าขาออก
    //
    
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
