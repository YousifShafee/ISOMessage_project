/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iso8583;

/**
 *
 * @author Mr.Yousif
 */
public class Message {
    
    private String mMessage;
    public Message (String mMsg )
    {
      mMessage=mMsg;
    }
    
    public static String getMTI ()
    {String MTI = null;
     return MTI;
    }
    public  static  String getBitMap(){
    String bitMap=null;
    return bitMap;
    
    } 
    public  static String getRestOfMsg()
    {String restOfMsg=null;
     return restOfMsg;
    }
    
}
