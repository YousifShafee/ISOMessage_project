package iso8583;

//Omar Saad (29/7/2018)
import commons.Utility;
import static Database.MessageDatabase.HexToAsci;
import exceptions.NotHexadecimalFormatException;
import exceptions.WrongMTIException;
import exceptions.WrongMessageException;
import exceptions.ZeroBitmapException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import Database.MessageDatabase;
import commons.Constants;

public class Message extends MessageParser {

    private String Msg; // The complete message 
    private String MsgLength = ""; //Message Length 
    private String ISO = "";
    private String PowerCardHeader = "";
    private String MTI = "";
    private String BitMap = "";
    private String DataElements = "";

    public Message(String Msg) throws WrongMessageException, ClassNotFoundException, SQLException {

        //remove spaces from String
        Msg = Msg.replaceAll(" ", "");

        /*
         author: islam tareq
         date:   1/8/2018
         details:Exceptions added for not correct messages and empty messages
         */
        if (Msg.equals("")) {
            throw new WrongMessageException("Message is empty");
        } else if (!Msg.matches("^[0-9A-F]+$")) {
            throw new NotHexadecimalFormatException("Message is Not in Hexadecimal format", Msg);
        }
        this.Msg = Msg;

        // Start Logging
        Utility.logger.info("=====================Start Parsing=====================");

        //get message length in hex and put it in String MsgLength
        int i = 0;//counter
        while (i < 8) {
            MsgLength = MsgLength + Msg.charAt(i);
            i++;
        }

        // Logging ISO Length by Yousif
        Utility.loggerString = String.format("ISO Msg Length : %s", HexToAsci(MsgLength));
        Utility.logger.info(Utility.loggerString);

        //get ISO in hex and put it in String ISO
        while (i < 14) {
            ISO = ISO + Msg.charAt(i);
            i++;
        }

        // Logging for Protocol ID "ISO"
        Utility.loggerString = String.format("Protocol ID : %s", HexToAsci(ISO));
        Utility.logger.info(Utility.loggerString);

        //get Power Card Header in hex and put it in String PowerCardHeader
        while (i < 30) {
            PowerCardHeader = PowerCardHeader + Msg.charAt(i);
            i++;
        }

        // Logging for Power Card Header"
        Utility.loggerString = String.format("Power Card Header : %s", HexToAsci(PowerCardHeader));
        Utility.logger.info(Utility.loggerString);

        //get MTI in hex and put it in String MTI
        while (i < 38) {
            MTI = MTI + Msg.charAt(i);
            i++;
        }

        // Logging for MTI
        Utility.loggerString = String.format("MTI : %s", HexToAsci(MTI));
        Utility.logger.info(Utility.loggerString);

        //get Bit Map in hex and put it in String BitMap
        boolean flag;  // this flag indicates the first bit of bitmap in binary if 0 then flag = false and if 1 then flag = true 
        switch (Msg.charAt(i)) {
            case '1':;
            case '2':;
            case '3':;
            case '4':;
            case '5':;
            case '6':;
            case '7':
                flag = false;
                break;
            default:
                flag = true;
        }
        int BitMapEndIndex;
        if (flag) {
            BitMapEndIndex = 69;
        } else {
            BitMapEndIndex = 53;
        }

        while (i < BitMapEndIndex + 1) {
            BitMap = BitMap + Msg.charAt(i);
            i++;
        }

        // Logging for BitMap Hexa
        Utility.loggerString = String.format("BITMAP Bytes : %s", BitMap);
        Utility.logger.info(Utility.loggerString);
        String bin = hexToBin(BitMap);

        // Logging for BitMap Bin
        Utility.loggerString = String.format("BITMAP bits  : %s", bin);
        Utility.logger.info(Utility.loggerString);

        //get Data Elements in hex and put it in String DataElements
        while (i < Msg.length()) {
            DataElements = DataElements + Msg.charAt(i);
            i++;
        }
        if (HexToAsci(MTI).equals("1420") || HexToAsci(MTI).equals("1421")) {
            Reversing(DataElements, BitMap, MTI);
        }

    }

    //GETTERS
    public String getMsg() {
        return Msg;
    }

    public String getMsgLength() {

        return HexToAsci(MsgLength);
    }

    public String getISO() {
        return HexToAsci(ISO);
    }

    public String getPowerCardHeader() {
        return HexToAsci(PowerCardHeader);
    }

    public String getMTI() {
        return HexToAsci(MTI);
    }

    public String getBitMap() {
        return BitMap;

    }

    public String getDataElements() {
        return DataElements;
    }

    //tostring method
    public String toString() {
        String x = "Message Length:(hex) " + MsgLength + " >>> (Asci)" + HexToAsci(MsgLength);
        x = x + '\n' + "ISO:(hex) " + ISO + " >>> (Asci)" + HexToAsci(ISO);
        x = x + '\n' + "Power Card Header:(hex) " + PowerCardHeader + " >>> (Asci)" + HexToAsci(PowerCardHeader);
        x = x + '\n' + "MTI:(hex) " + MTI + " >>> (Asci)" + HexToAsci(MTI);
        x = x + '\n' + "BitMap:(hex) " + BitMap;
        // x=x+'\n'+"Data Elements:(hex) "+DataElements ;
        ArrayList<FieldInfo> res = null;
        try {
            res = parsingMessage(DataElements, BitMap, MTI);

            if (!HexToAsci(MTI).equals("1804")) {
                AddToDB(DataElements, BitMap, MTI);
            }

            if (HexToAsci(MTI).equals("1804")) {
                Utility.MsgStatus = true;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        } catch (ZeroBitmapException ex) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
        }
        FieldType gettype;
        for (int i = 0; i < res.size(); i++) {
            gettype = MessageParser.getElementLength(res.get(i).getFieldNo());
            x = x + '\n' + "Field Number " + res.get(i).getFieldNo() + ">>>" + res.get(i).getDE() + ">>>" + res.get(i).getInfo();

            // Logging for BitMap Bin
            if (gettype.getIsVar()) {
                Utility.loggerString = String.format(" Fld(%3d)%s[%3d]     : %s = %s", res.get(i).getFieldNo(), "V", gettype.getLength(), res.get(i).getInfo(), res.get(i).getDE());
                Utility.logger.info(Utility.loggerString);
            } else {
                Utility.loggerString = String.format(" Fld(%3d)%s[%3d]     : %s = %s", res.get(i).getFieldNo(), "F", gettype.getLength(), res.get(i).getInfo(), res.get(i).getDE());
                Utility.logger.info(Utility.loggerString);
            }
        }
        // End Logging
        Utility.logger.info("=====================End Parsing=====================");
        return x;

    }

    //Method that changes from hex to asci 
    public static String HexToAsci(String hex) {

        StringBuilder output = new StringBuilder();
        for (int i = 0; i < hex.length(); i += 2) {
            String str = hex.substring(i, i + 2);
            output.append((char) Integer.parseInt(str, 16));
        }
        return output + "";
    }

    public Message response() throws SQLException, ClassNotFoundException, WrongMessageException {
        String AsciMTI = HexToAsci(MTI);

        String ResponseMTI = "";

        switch (AsciMTI) {
            case "1804":
                ResponseMTI = "31383134";
                break;//1814
            case "1200":
                ResponseMTI = "31323130";
                break;//1210
            case "1420":;
            case "1421":
                ResponseMTI = "31343330";
                break;//1430
            case "1220":;
            case "1221":
                ResponseMTI = "31323330";
                break;//1230
            case "1604":
                ResponseMTI = "31363134";
                break;//1614
            default:
                throw new WrongMTIException("Wrong MTI", MTI);
        }

        Message response = new Message(MsgLength + ISO + PowerCardHeader + ResponseMTI + BitMap + DataElements);
        ArrayList<FieldInfo> res = null;
        res = parsingMessage(DataElements, BitMap, MTI);
        FieldType gettype;
        for (int i = 0; i < res.size(); i++) {
            gettype = MessageParser.getElementLength(res.get(i).getFieldNo());

            // Logging for BitMap Bin
            if (gettype.getIsVar()) {
                Utility.loggerString = String.format(" Fld(%3d)%s[%3d]     : %s = %s", res.get(i).getFieldNo(), "V", gettype.getLength(), res.get(i).getInfo(), HexToAsci(res.get(i).getDE()));
                Utility.logger.info(Utility.loggerString);
            } else {
                Utility.loggerString = String.format(" Fld(%3d)%s[%3d]     : %s = %s", res.get(i).getFieldNo(), "F", gettype.getLength(), res.get(i).getInfo(), HexToAsci(res.get(i).getDE()));
                Utility.logger.info(Utility.loggerString);
            }
        }
        // End Logging 
        Utility.logger.info("=====================End Parsing=====================");
        return response;
    }

    //Youussef Shafee & Omar Saad //1-8-2018 
    public void Reversing(String DataElements, String BitMap, String MTI) throws ClassNotFoundException, SQLException {
        ArrayList<FieldInfo> DE = null;
        try {
            DE = parsingMessage(DataElements, BitMap, MTI);
        } catch (ZeroBitmapException ex) {
            ex.printStackTrace();
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
        }
        String Field56 = "";
        for (int k = 0; k < DE.size(); k++) {
            if (DE.get(k).getFieldNo() == 56) {
                Field56 = HexToAsci(DE.get(k).getDE());
            }
        }
        int c = 0;//counter
        String MTI56 = "";
        while (c < 4) {
            MTI56 = MTI56 + Field56.charAt(c);
            c++;
        }
        String DE11 = "";
        while (c < 10) {
            DE11 = DE11 + Field56.charAt(c);
            c++;
        }
        String DE12 = "";
        while (c < 12) {
            DE12 = DE12 + Field56.charAt(c);
            c++;
        }

        String DE7 = "";
        while (c < 22) {
            DE7 = DE7 + Field56.charAt(c);
            c++;
        }
        try {
            Reversing reversed = MessageDatabase.SearchReversal(MTI56, DE7, DE11, DE12);
            if (reversed == Reversing.ACCEPTED) {
                Utility.ReversedStatus = new ErrorCode(reversed, Constants.ISO_ERROR_CODE_SUCCESS);
            } else if (reversed == Reversing.REJECTED) {
                Utility.ReversedStatus = new ErrorCode(reversed, Constants.ISO_ERROR_REVERSING_REJECTED);
            } else {
                Utility.ReversedStatus = new ErrorCode(reversed, Constants.ISO_ERROR_REVERSING_NOTFOUND);

            }

        } catch (SQLException ex) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
