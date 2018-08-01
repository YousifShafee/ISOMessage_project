package iso8583;

import Database.MessageDatabase;
import exceptions.WrongMessageException;
import exceptions.ZeroBitmapException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class MessageParser {

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

    //this method get all the 1's indexs of bitmap in binary . 
    //Omar Saad 29/7/2018    
    /*
     author: islam tareq
     date:   1/8/2018
     details:Exceptions added for bitmap of only zeros
     */
    public static ArrayList<Integer> getExistingElementNo(String bitMap) throws WrongMessageException {
        ArrayList<Integer> res = new ArrayList();
        int counter = 0;
        String bin = hexToBin(bitMap);

        for (int i = 0; i < bin.length(); i++) {
            if (bin.charAt(i) == '1') {
                res.add(counter, i + 1);
                counter++;
            }
        }
        if (bin.length() == 0) {
            /*
             author: islam tareq
             date:   1/8/2018
             details:Throws ZeroBitmapException 
             */
            throw new ZeroBitmapException("Bitmap does not refere to any of datafields", bitMap);
        }
        return res;
    }

    // function get type and number of field 
    // mariam esmail //29/7/2018
    public static FieldType getElementLength(int elementno) {

        //System.out.println ("Field number you want?");
        // HashMap<String,String> elementNo = new HashMap<String,String>();
        //Scanner sc = new Scanner ( System.in);
        //"v,2,Primary account number"
        FieldType[] DataElements = new FieldType[129];
        DataElements[0] = null;
        DataElements[1] = null;
        DataElements[2] = new FieldType(true, 2, "Primary account number");
        DataElements[3] = new FieldType(false, 6, "Processing code");
        DataElements[4] = new FieldType(false, 12, "Amount transaction");
        DataElements[5] = new FieldType(false, 12, "Amount settlement");
        DataElements[6] = new FieldType(false, 12, "Amount cardholder billing");
        DataElements[7] = new FieldType(false, 10, "Transmission date & time");
        DataElements[8] = new FieldType(false, 8, "Amount, cardholder billing fee");
        DataElements[9] = new FieldType(false, 8, "Conversion rate settlement");
        DataElements[10] = new FieldType(false, 8, "Conversion rate cardholder billing");
        DataElements[11] = new FieldType(false, 6, "System trace audit number");
        DataElements[12] = new FieldType(false, 12, "Transaction Date and Time");
        DataElements[13] = new FieldType(false, 4, "Date local transaction");
        DataElements[14] = new FieldType(false, 4, "Date expiration");
        DataElements[15] = new FieldType(false, 6, "Date settlement");
        DataElements[16] = new FieldType(false, 4, "Date conversion");
        DataElements[17] = new FieldType(false, 4, "Date capture");
        DataElements[18] = new FieldType(false, 4, "Merchant type");
        DataElements[19] = new FieldType(false, 3, "Acquiring institution country code");
        DataElements[20] = new FieldType(false, 3, "PAN extended, country code");
        DataElements[21] = new FieldType(false, 3, "Forwarding institution. country code");
        DataElements[22] = new FieldType(false, 12, "Point of service entry mode");
        DataElements[23] = new FieldType(false, 3, "Application PAN sequence number");
        DataElements[24] = new FieldType(false, 3, "Function code /Network International");
        DataElements[25] = new FieldType(false, 4, "Point of service condition code");
        DataElements[26] = new FieldType(false, 4, "Point of service capture code");
        DataElements[27] = new FieldType(false, 1, "Authorizing identification response length");
        DataElements[28] = new FieldType(false, 9, "Amount transaction fee");
        DataElements[29] = new FieldType(false, 3, "Amount, settlement fee");
        DataElements[30] = new FieldType(false, 9, "Amount, transaction processing fee");
        DataElements[31] = new FieldType(true, 2, "Amount, settlement processing fee");
        DataElements[32] = new FieldType(true, 2, "Acquiring institution identification code");
        DataElements[33] = new FieldType(true, 2, "Forwarding institution identification code");
        DataElements[34] = new FieldType(true, 2, "Primary account number extended");
        DataElements[35] = new FieldType(true, 2, "Track 2 data");
        DataElements[36] = new FieldType(true, 3, "Track 3 data");
        DataElements[37] = new FieldType(false, 12, "Retrieval reference number");
        DataElements[38] = new FieldType(false, 6, "Authorization identification response");
        DataElements[39] = new FieldType(false, 3, "Response code");
        DataElements[40] = new FieldType(false, 3, "Service restriction code");
        DataElements[41] = new FieldType(false, 8, "Card acceptor terminal identification");
        DataElements[42] = new FieldType(false, 15, "Card acceptor identification code");
        DataElements[43] = new FieldType(true, 2, "Card acceptor name/location");
        DataElements[44] = new FieldType(true, 3, "Additional response data");
        DataElements[45] = new FieldType(true, 2, "Track 1 data");
        DataElements[46] = new FieldType(false, 9, "Additional data - ISO");
        DataElements[47] = new FieldType(true, 3, "Additional data - national");
        DataElements[48] = new FieldType(true, 3, "Additional data - private");
        DataElements[49] = new FieldType(false, 3, "Currency code, transaction");
        DataElements[50] = new FieldType(false, 3, "Currency code settlement");
        DataElements[51] = new FieldType(false, 3, "Currency code, cardholder billing");
        DataElements[52] = new FieldType(false, 8, "Personal identification number data");
        DataElements[53] = new FieldType(true, 2, "Security related control information");
        DataElements[54] = new FieldType(true, 3, "Additional amounts");
        DataElements[55] = new FieldType(true, 3, "Reserved ISO");
        DataElements[56] = new FieldType(true, 2, "Reserved ISO");
        DataElements[57] = new FieldType(false, 3, "Reserved national");
        DataElements[58] = new FieldType(true, 2, "Reserved national");
        DataElements[59] = new FieldType(true, 3, "Reserved national");
        DataElements[60] = new FieldType(true, 3, "Reserved national");
        DataElements[61] = new FieldType(true, 3, "Reserved private");
        DataElements[62] = new FieldType(true, 3, "Reserved private");
        DataElements[63] = new FieldType(true, 3, "Reserved private");
        DataElements[64] = new FieldType(false, 8, "Message authentication code");
        DataElements[65] = new FieldType(false, 8, "Bitmap, extended");
        DataElements[66] = new FieldType(true, 3, "Settlement code");
        DataElements[67] = new FieldType(false, 2, "Extended payment code");
        DataElements[68] = new FieldType(false, 3, "Receiving institution country code");
        DataElements[69] = new FieldType(false, 3, "Settlement institution country code");
        DataElements[70] = new FieldType(false, 3, "Network management information code");
        DataElements[71] = new FieldType(false, 8, "Message number");
        DataElements[72] = new FieldType(true, 3, "Message number last");
        DataElements[73] = new FieldType(false, 6, "Date action");
        DataElements[74] = new FieldType(false, 10, "Credits number");
        DataElements[75] = new FieldType(false, 10, "Credits reversal number");
        DataElements[76] = new FieldType(false, 10, "Debits, number");
        DataElements[77] = new FieldType(false, 10, "Debits reversal number");
        DataElements[78] = new FieldType(false, 10, "Transfer number");
        DataElements[79] = new FieldType(false, 10, "Transfer, reversal number");
        DataElements[80] = new FieldType(false, 10, "Inquiries number");
        DataElements[81] = new FieldType(false, 10, "Authorizations number");
        DataElements[82] = new FieldType(false, 10, "Credits processing fee amount");
        DataElements[83] = new FieldType(false, 10, "Credits transaction fee amount");
        DataElements[84] = new FieldType(false, 10, "Debits processing fee amount");
        DataElements[85] = new FieldType(false, 10, "Debits transaction fee amount");
        DataElements[86] = new FieldType(false, 16, "Credits amount");
        DataElements[87] = new FieldType(false, 16, "Credits, reversal amount");
        DataElements[88] = new FieldType(false, 16, "Debits amount");
        DataElements[89] = new FieldType(false, 16, "Debits reversal amount");
        DataElements[90] = new FieldType(false, 10, "Original data elements");
        DataElements[91] = new FieldType(false, 3, "File update code");
        DataElements[92] = new FieldType(false, 3, "File security code");
        DataElements[93] = new FieldType(true, 2, "Response indicator");
        DataElements[94] = new FieldType(true, 2, "Service indicator");
        DataElements[95] = new FieldType(true, 2, "Replacement amounts");
        DataElements[96] = new FieldType(true, 3, "Message security code");
        DataElements[97] = new FieldType(false, 17, "Amount, net settlement");
        DataElements[98] = new FieldType(false, 25, "Payee");
        DataElements[99] = new FieldType(true, 2, "Settlement institution identification code");
        DataElements[100] = new FieldType(true, 2, "Receiving institution identification code");
        DataElements[101] = new FieldType(true, 2, "File name");
        DataElements[102] = new FieldType(true, 2, "Account identification 1");
        DataElements[103] = new FieldType(true, 2, "Account identification 2");
        DataElements[104] = new FieldType(true, 2, "Transaction description");
        DataElements[105] = new FieldType(false, 16, "Reserved for ISO use");
        DataElements[106] = new FieldType(false, 16, "Reserved for ISO use");
        DataElements[107] = new FieldType(false, 16, "Reserved for ISO use");
        DataElements[108] = new FieldType(false, 16, "Reserved for ISO use");
        DataElements[109] = new FieldType(true, 2, "Reserved for ISO use");
        DataElements[110] = new FieldType(true, 2, "Reserved for ISO use");
        DataElements[111] = new FieldType(true, 3, "Reserved for ISO use");
        DataElements[112] = new FieldType(true, 3, "Reserved for national use");
        DataElements[113] = new FieldType(true, 3, "Reserved for national use");
        DataElements[114] = new FieldType(true, 3, "Reserved for national use");
        DataElements[115] = new FieldType(true, 3, "Reserved for national use");
        DataElements[116] = new FieldType(true, 3, "Reserved for national use");
        DataElements[117] = new FieldType(true, 3, "Reserved for national use");
        DataElements[118] = new FieldType(true, 3, "Reserved for national use");
        DataElements[119] = new FieldType(true, 3, "Reserved for national use");
        DataElements[120] = new FieldType(true, 3, "Reserved for private use");
        DataElements[121] = new FieldType(true, 3, "Reserved for private use");
        DataElements[122] = new FieldType(true, 3, "Reserved for private use");
        DataElements[123] = new FieldType(true, 3, "Reserved for private use");
        DataElements[124] = new FieldType(true, 3, "Reserved for private use");
        DataElements[125] = new FieldType(true, 3, "Reserved for private use");
        DataElements[126] = new FieldType(true, 3, "Reserved for private use");
        DataElements[127] = new FieldType(true, 3, "Reserved for private use");
        DataElements[128] = new FieldType(false, 8, "Message authentication code");

        return DataElements[elementno];

        /* elementNo.put("2","v,2,Primary account number");  elementNo.put("3","F,6,Processing code"); 
         elementNo.put("4","F,12,Amount, transaction"); elementNo.put("5","F,12,Amount, settlement");
         elementNo.put("6","F,12,Amount, cardholder billing"); elementNo.put("7","F,10,Transmission date & time");
         elementNo.put("8","F,8,Amount, cardholder billing fee");  elementNo.put("9","F,8,Conversion rate, settlement");
         elementNo.put("10","F,8,Conversion rate, cardholder billing"); elementNo.put("11","F,6,System trace audit number");
         elementNo.put("12","F,12,Transaction Date and Time");elementNo.put("13","F,4,Date, local transaction");
         elementNo.put("14","F,4,Date, expiration"); elementNo.put("15","F,6,Date, settlement");
         elementNo.put("16","F,4,Date, conversion"); elementNo.put("17","F,4,Date, capture");
         elementNo.put("18","F,4,Merchant type"); elementNo.put("19","F,3,Acquiring institution country code");
         elementNo.put("20","F,3,PAN extended, country code"); elementNo.put("21","F,3,Forwarding institution. country code");
         elementNo.put("22","F,12,Point of service entry mode");elementNo.put("23","F,3,Application PAN sequence number");
         elementNo.put("24","F,3,Function code /Network International"); elementNo.put("25","F,4,Point of service condition code");
         elementNo.put("26","F,4,Point of service capture code"); elementNo.put("27","F,1,Authorizing identification response length");
         elementNo.put("28","F,9,Amount, transaction fee"); elementNo.put("29","F,3,Amount, settlement fee");
         elementNo.put("30","F,9,Amount, transaction processing fee"); elementNo.put("31","v,2,Amount, settlement processing fee");
         elementNo.put("32","v,2,Acquiring institution identification code"); elementNo.put("33","v,2,Forwarding institution identification code");
         elementNo.put("34","v,2,Primary account number, extended"); elementNo.put("35","v,2,Track 2 data");
         elementNo.put("36","v,3,Track 3 data"); elementNo.put("37","F,12,Retrieval reference number");
         elementNo.put("38","F,6,Authorization identification response"); elementNo.put("39","F,3,Response code");
         elementNo.put("40","F,3,Service restriction code"); elementNo.put("41","F,8,Card acceptor terminal identification");
         elementNo.put("42","F,15,Card acceptor identification code");elementNo.put("43","v,2,Card acceptor name/location");
         elementNo.put("44","v,3,Additional response data"); elementNo.put("45","v,2,Track 1 data");
         elementNo.put("46","F,9,Additional data - ISO"); elementNo.put("47","v,3,Additional data - national");
         elementNo.put("48","v,3,Additional data - private"); elementNo.put("49","F,3,Currency code, transaction");
         elementNo.put("50","F,3,Currency code, settlement"); elementNo.put("51","F,3,Currency code, cardholder billing");
         elementNo.put("52","F,8,Personal identification number data");elementNo.put("53","v,2,Security related control information");
         elementNo.put("54","v,3,Additional amounts"); elementNo.put("55","v,3,Reserved ISO");elementNo.put("56","v,2,Reserved ISO"); 
         elementNo.put("57","F,3,Reserved national");elementNo.put("58","v,2,Reserved national"); elementNo.put("59","v,3,Reserved national");
         elementNo.put("60","v,3,Reserved national");elementNo.put("61","v,3,Reserved private");
         elementNo.put("62","v,3,Reserved private"); elementNo.put("63","v,3,Reserved private");
         elementNo.put("64","F,8,Message authentication code");elementNo.put("65","F,8,Bitmap, extended");
         elementNo.put("66","v,3,Settlement code"); elementNo.put("67","F,2,Extended payment code");
         elementNo.put("68","F,3,Receiving institution country code");elementNo.put("69","F,3,Settlement institution country code");
         elementNo.put("70","F,3,Network management information code"); elementNo.put("71","F,8,Message number");
         elementNo.put("72","v,3,Message number, last"); elementNo.put("73","F,6,Date, action");    
         elementNo.put("74","F,10,Credits, number");elementNo.put("75","F,10,Credits, reversal number");
         elementNo.put("76","F,10,Debits, number");elementNo.put("77","F,10,Debits, reversal number");
         elementNo.put("78","F,10,Transfer number");elementNo.put("79","F,10,Transfer, reversal number");
         elementNo.put("80","F,10,Inquiries number");elementNo.put("81","F,10,Authorizations, number");
         elementNo.put("82","F,10,Credits, processing fee amount");elementNo.put("83","F,10,Credits, transaction fee amount");
         elementNo.put("84","F,10,Debits, processing fee amount");elementNo.put("85","F,10,Debits, transaction fee amount");
         elementNo.put("86","F,16,Credits, amount");elementNo.put("87","F,16,Credits, reversal amount");
         elementNo.put("88","F,16,Debits, amount");elementNo.put("89","F,16,Debits, reversal amount");
         elementNo.put("90","F,10,Original data elements");elementNo.put("91","F,3,File update code");
         elementNo.put("92","F,3,File security code");elementNo.put("93","v,2,Response indicator");
         elementNo.put("94","v,2,Service indicator"); elementNo.put("95","v,2Replacement amounts");
         elementNo.put("96","v,3Message security code"); elementNo.put("97","F,17,Amount, net settlement");
         elementNo.put("98","F,25,Payee");elementNo.put("99","v,2,Settlement institution identification code");
         elementNo.put("100","v,2,Receiving institution identification code"); elementNo.put("101","v,2,File name");
         elementNo.put("102","v,2,Account identification 1");elementNo.put("103","v,2,Account identification 2");
         elementNo.put("104","v,2,Transaction description");elementNo.put("105","F,16,Reserved for ISO use");
         elementNo.put("106","F,16,Reserved for ISO use");elementNo.put("107","F,16,Reserved for ISO use");
         elementNo.put("108","F,16,Reserved for ISO use");elementNo.put("109","v,2,Reserved for ISO use");
         elementNo.put("110","v,2,Reserved for ISO use");elementNo.put("111","v,3,Reserved for ISO use");
         elementNo.put("112","v,3,Reserved for national use");elementNo.put("113","v,3,Reserved for national use");
         elementNo.put("114","v,3,Reserved for national use");elementNo.put("115","v,3,Reserved for national use");
         elementNo.put("116","v,3,Reserved for national use");elementNo.put("117","v,3,Reserved for national use");
         elementNo.put("118","v,3,Reserved for national use");elementNo.put("119","v,3,Reserved for national use");
         elementNo.put("120","v,3,Reserved for private use");elementNo.put("121","v,3,Reserved for private use");
         elementNo.put("122","v,3,Reserved for private use");elementNo.put("123","v,3,Reserved for private use");
         elementNo.put("124","v,3,Reserved for private use");elementNo.put("125","v,3,Reserved for private use");
         elementNo.put("126","v,3,Reserved for private use");elementNo.put("127","v,3,Reserved for private use");
         elementNo.put("128","F,8,Message authentication code,");*/
        //String elemntno=sc.next();
    }

    /* parsingMessage function
     input: data element code in HexaDecimal
     output: arraylist of parsed data element (NumberOfField,DataElement)
     By:Mostafa Mohamed Abdel Rahaman
     7/29/2018
     */
    //Omar Review:- this method can take only 1  parameter(Message Msg) and use the getter methods to get any variable
    //              instead of (String restOfMsg,String BitMap,String MTI) 
    /*
     author: islam tareq
     date:   1/8/2018
     details:Throws ZerobitmapException
     */
    public static ArrayList<FieldInfo> parsingMessage(String restOfMsg, String BitMap, String MTI) throws SQLException, ClassNotFoundException, WrongMessageException {

        ArrayList<FieldInfo> dataElements = new ArrayList<>();
        int dataElementNo, lengthDigits;
        String dataElement = null;
        ArrayList<Integer> elementNo = getExistingElementNo(BitMap);
        FieldType lengthIndecator;
        for (int i = 0; i < elementNo.size(); ++i) {
            if (elementNo.get(i) != 1) {
                dataElementNo = elementNo.get(i);
                lengthIndecator = getElementLength(dataElementNo);

                if (!lengthIndecator.getIsVar()) {
                    dataElement = restOfMsg.substring(0, lengthIndecator.getLength() * 2);
                    restOfMsg = restOfMsg.substring(lengthIndecator.getLength() * 2);
                    dataElements.add(new FieldInfo(dataElementNo, dataElement, lengthIndecator.getDes()));

                } else if (lengthIndecator.getIsVar()) {
///////////////////////////////////////
                    String hex = restOfMsg.substring(0, lengthIndecator.getLength() * 2);
                    StringBuilder output = new StringBuilder();
                    for (int j = 0; j < hex.length(); j += 2) {
                        String str = hex.substring(j, j + 2);
                        output.append((char) Integer.parseInt(str, 16));
                    }
                    ///////////////////  ////    //   ///    //////
                    lengthDigits = Integer.parseInt(output.toString());

                    restOfMsg = restOfMsg.substring(lengthIndecator.getLength() * 2);
                    dataElement = restOfMsg.substring(0, lengthDigits * 2);
                    restOfMsg = restOfMsg.substring(lengthDigits * 2);
                    dataElements.add(new FieldInfo(dataElementNo, dataElement, lengthIndecator.getDes()));
                }

            }
        }
        MessageDatabase DB = new MessageDatabase();
        DB.insertInDb(dataElements, Message.HexToAsci(MTI));
        return dataElements;
    }

}
