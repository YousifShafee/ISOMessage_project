package iso8583;

import Database.MessageDatabase;
import commons.Utility;
import exceptions.ZeroBitmapException;
import java.sql.SQLException;
import java.util.ArrayList;

public class MessageParser {

    private static Database.MessageDatabase messageDatabase = new MessageDatabase();

   
    //this method get all the 1's indexs of bitmap in binary . 
    //Omar Saad 29/7/2018    
    public static ArrayList<Integer> getExistingElementNo(String bitMap) throws ZeroBitmapException {
        ArrayList<Integer> res = new ArrayList();
        int counter = 0;
        String bin = Utility.hexToBin(bitMap);

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

    }

    /* parsingMessage function
     input: data element code in HexaDecimal
     output: arraylist of parsed data element (NumberOfField,DataElement)
     By:Mostafa Mohamed Abdel Rahaman
     7/29/2018
     */
    //Omar Review:- this method can take only 1  parameter(Message Msg) and use the getter methods to get any variable
    //              instead of (String restOfMsg,String BitMap,String MTI) 
    public static ArrayList<FieldInfo> parsingMessage(String restOfMsg, String BitMap, String MTI) throws ZeroBitmapException, ClassNotFoundException, SQLException {
       
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

                    String hex = restOfMsg.substring(0, lengthIndecator.getLength() * 2);
                    String output = Utility.HexToAsci(hex);
                    
                    lengthDigits = Integer.parseInt(output);

                    restOfMsg = restOfMsg.substring(lengthIndecator.getLength() * 2);
                    dataElement = restOfMsg.substring(0, lengthDigits * 2);
                    restOfMsg = restOfMsg.substring(lengthDigits * 2);
                    dataElements.add(new FieldInfo(dataElementNo, dataElement, lengthIndecator.getDes()));
                }

            }
        }

        return dataElements;
    }

    //This method adds the message to the DataBase
    //Omar Saad // 1-8-2018
    public static void AddToDB(String restOfMsg, String BitMap, String MTI) throws SQLException, ClassNotFoundException, ZeroBitmapException {
        MessageDatabase DB = new MessageDatabase();
        DB.insertInDb(parsingMessage(restOfMsg, BitMap, MTI), Utility.HexToAsci(MTI));
    }

}
