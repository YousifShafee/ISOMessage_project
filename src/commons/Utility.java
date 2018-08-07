package commons;

import iso8583.ErrorCode;
import org.apache.log4j.Logger;
// Loggin by Log4J Made By Yousif Tariq on 31-7-2018

public class Utility {
// Static logger variabel to used by All Method in Project By Yousif

    public static Logger logger = Logger.getLogger(Utility.class);

// Static String which have logger Message By Yousif
    public static String loggerString;
    public static String loggerEnd = "=====================End Parsing=====================";
    public static boolean MsgStatus = false; // Flag to indicate Sign-on message Recieved or not. //Omar Saad & Islam Tarek // 1/8/2018 
    public static ErrorCode ReversedStatus;
    public static boolean isRepeated = false;
    
    public static void logError(Exception ex){
        Utility.logger.error(ex);
        Utility.logger.error(Utility.loggerEnd);
    }
    
     //Change from hex to Binary
    //Omar Saad // 29/7/2018
     public static String hexToBin(String hex) {
        String bin = "";
        String binFragment = "";
        int iHex;
        hex = hex.trim();
        hex = hex.replaceFirst("0x", "");

        for (int i = 0; i < hex.length(); i++) {
            iHex = Integer.parseInt("" + hex.charAt(i), 16);
            binFragment = Integer.toBinaryString(iHex);

            while (binFragment.length() < 4) {
                binFragment = "0" + binFragment;
            }
            bin += binFragment;
        }
        return bin;
    }
     
      public static String HexToAsci(String hex) {

        StringBuilder output = new StringBuilder();
        for (int i = 0; i < hex.length(); i += 2) {
            String str = hex.substring(i, i + 2);
            output.append((char) Integer.parseInt(str, 16));
        }
        return output + "";
    }
      
      
}
