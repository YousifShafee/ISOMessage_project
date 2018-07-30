
package iso8583;

import java.util.ArrayList;
import java.util.HashMap;


public class MessageParser {

    //Change from hex to Binary
    // Omar Saad // 29/7/2018
    private static String hexToBin(String hex){
    String bin = "";
    String binFragment = "";
    int iHex;
    hex = hex.trim();
    hex = hex.replaceFirst("0x", "");

    for(int i = 0; i < hex.length(); i++){
        iHex = Integer.parseInt(""+hex.charAt(i),16);
        binFragment = Integer.toBinaryString(iHex);

        while(binFragment.length() < 4){
            binFragment = "0" + binFragment;
        }
        bin += binFragment;
    }
    return bin;
}
    
    //this method get all the 1's indexs of bitmap in binary . 
    //Omar Saad 29/7/2018    
    public static ArrayList<Integer> getExistingElementNo(String bitMap)
    { 
        ArrayList<Integer> res = new ArrayList();
        int counter=0; 
        String bin = hexToBin(bitMap);
        for(int i=0;i<bin.length();i++ ){
            if(bin.charAt(i)=='1'){
                res.add(counter, i+1);
                counter ++;
                }
        }  
        return res;
    }
    
    
    
    
            
     // function get type and number of field 
     // mariam esmail //29/7/2018
      public static FieldType getElementLength(int elementno){
        
          
           //System.out.println ("Field number you want?");
        // HashMap<String,String> elementNo = new HashMap<String,String>();
         //Scanner sc = new Scanner ( System.in);
         //"v,2,Primary account number"
         
         FieldType[] DataElements = new FieldType[129]; 
         DataElements[0]=null;
         DataElements[1]=null;
         DataElements[2]=new FieldType(true,2,"Primary account number");
        
         
         
         return  DataElements[elementno];
         
         
         
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
    public static ArrayList<FieldInfo> parsingMessage(String restOfMsg,String BitMap) {
        
        ArrayList<FieldInfo> dataElements = new ArrayList<>();
        int dataElementNo, lengthDigits;
        String  dataElement = null;
       
        FieldType lengthIndecator ;
        for (int i = 0; i < getExistingElementNo(BitMap).size(); ++i) {
            dataElementNo = getExistingElementNo(BitMap).get(i);
            lengthIndecator = getElementLength(dataElementNo);
           
            if (!lengthIndecator.getIsVar()) {
                dataElement = restOfMsg.substring(0, lengthIndecator.getLength() * 2);
                restOfMsg = restOfMsg.substring(lengthIndecator.getLength() * 2);
                dataElements.add(new FieldInfo(dataElementNo, dataElement,lengthIndecator.getDes()));

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

               restOfMsg = restOfMsg.substring( lengthIndecator.getLength() * 2);
                dataElement = restOfMsg.substring(0, lengthDigits * 2);
                dataElements.add(new FieldInfo(dataElementNo, dataElement,lengthIndecator.getDes()));
            }

        }

        return dataElements;
    }

    
}
