package iso8583;

//This object was created to get the field number and its component in the same object
//Omar Saad
public class FieldInfo {

    private int FieldNo; //Field Number 
    private String DE; // Data element which belongs to the corresponding field Number 
    private String Info;

    public FieldInfo(int FieldNo, String DE, String Info) {
        this.FieldNo = FieldNo;
        this.DE = DE;
        this.Info = Info;
    }

    public int getFieldNo() {
        return FieldNo;
    }

    public String getDE() {
        return DE;
    }

    public String getInfo() {
        return Info;
    }
}
