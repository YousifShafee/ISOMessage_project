
package exceptions;


public class WrongMessageException extends Exception{
        String e ;
    public WrongMessageException (String e){
        super(e);
        this.e=e;
    }
}
