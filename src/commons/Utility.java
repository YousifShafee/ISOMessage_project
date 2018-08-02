package commons;

import iso8583.ErrorCode;
import org.apache.log4j.Logger;
// Loggin by Log4J Made By Yousif Tariq on 31-7-2018

public class Utility {
// Static logger variabel to used by All Method in Project By Yousif

    public static Logger logger = Logger.getLogger(Utility.class);

// Static String which have logger Message By Yousif
    public static String loggerString;
    public static boolean MsgStatus = false; // Flag to indicate Sign-on message Recieved or not. //Omar Saad & Islam Tarek // 1/8/2018 
    public static ErrorCode ReversedStatus;
    public static boolean isRejected = false;
}
