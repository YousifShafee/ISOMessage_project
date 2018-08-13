package iso8583;

//Omar Saad 2-8-2018 
public class ErrorCode {

    private int ErrorCode;
    private Reversing Status;

    public ErrorCode(Reversing Status, int ErrorCode) {
        this.ErrorCode = ErrorCode;
        this.Status = Status;
    }

    public int getErrorCode() {
        return ErrorCode;
    }

    public Reversing getStatus() {
        return Status;
    }
}
