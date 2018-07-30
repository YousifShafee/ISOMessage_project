
package iso8583;

//Omar Saad 30/7
public class FieldType {
   private  boolean isVar; // check whether this field is fixed or variable if true then variable otherwise false (Fixed)
   private int length ;
   private String Des ; //Description
   
   public FieldType (boolean isVar,int length , String Des){
       this.Des=Des;
       this.isVar=isVar;
       this.length=length;
   }
   
   public boolean getIsVar(){
       return isVar;
   }
   public int getLength(){
       return length;
   }
   public String getDes(){
       return Des;
   }
}
