


package iso8583;

//this class made by Omar Saad (29/7/2018)

import java.util.ArrayList;

public class Message  extends MessageParser {
    
    private String Msg; // The complete message 
    private String MsgLength=""; //Message Length 
    private String ISO="";
    private String PowerCardHeader="";
    private String MTI="";
    private String BitMap="";
    private String DataElements="";
    
    
    public Message (String Msg ){
        this.Msg=Msg;
      //remove spaces from String
        Msg=Msg.replaceAll(" ", "");
      //get message length in hex and put it in String MsgLength
        int i = 0;//counter
        while(i<8){
            MsgLength = MsgLength + Msg.charAt(i);
            i++;
        }
        //get ISO in hex and put it in String ISO
        while(i<14){
            ISO = ISO + Msg.charAt(i);
            i++;
        }
        
        //get Power Card Header in hex and put it in String PowerCardHeader
        while(i<30){
            PowerCardHeader = PowerCardHeader + Msg.charAt(i);
            i++;
        }
        
        
        //get MTI in hex and put it in String MTI
        while(i<38){
            MTI = MTI + Msg.charAt(i);
            i++;
        }
        
        //get Bit Map in hex and put it in String BitMap
       boolean flag;  // this flag indicates the first bit of bitmap in binary if 0 then flag = false and if 1 then flag = true 
       switch (Msg.charAt(i)){
           case '1':;
           case '2':;
           case '3':;
           case '4':;
           case '5':;
           case '6':;
           case '7':flag = false;break;
           default:flag =true; 
               }
       int BitMapEndIndex;
       if(flag)
           BitMapEndIndex=69;
       else
           BitMapEndIndex=53;
       
       while(i<BitMapEndIndex+1){
            BitMap = BitMap + Msg.charAt(i);
            i++;
       }
           
       //get Data Elements in hex and put it in String DataElements
       while(i<Msg.length()){
            DataElements = DataElements + Msg.charAt(i);
            i++;
       }
      
    }
    
    
    //GETTERS
    public String getMsgLength(){
        return HexToAsci(MsgLength);
    }
    
    public String getISO(){
        return HexToAsci(ISO);
    }
    
    public String getPowerCardHeader(){
        return HexToAsci(PowerCardHeader);
    }
    public String getMTI () {
        return HexToAsci(MTI);
    }
    
    public String getBitMap(){
        return BitMap;
    
    } 
    public String getDataElements(){
     return DataElements;
    }
    
    //tostring method
    public String toString(){
        String x= "Message Length:(hex) "+MsgLength+" >>> (Asci)"+HexToAsci(MsgLength) ;
        x=x+'\n'+"ISO:(hex) "+ISO+" >>> (Asci)"+HexToAsci(ISO) ;
        x=x+'\n'+"Power Card Header:(hex) "+PowerCardHeader+" >>> (Asci)"+HexToAsci(PowerCardHeader) ;
        x=x+'\n'+"MTI:(hex) "+MTI+" >>> (Asci)"+HexToAsci(MTI) ;
        x=x+'\n'+"BitMap:(hex) "+BitMap ;
       // x=x+'\n'+"Data Elements:(hex) "+DataElements ;
        ArrayList<FieldInfo> res= parsingMessage(DataElements,BitMap);
        for(int i=0;i<res.size();i++){
            x=x+'\n'+"Field Number "+res.get(i).getFieldNo()+ ">>>" +res.get(i).getDE();
        }
            
        return x;
        
    }
    
    //Method that changes from hex to asci 
    public static String HexToAsci(String hex){
        
        StringBuilder output = new StringBuilder();
        for(int i =0;i<hex.length();i+=2)
        {String str=hex.substring(i, i+2);
        output.append((char)Integer.parseInt(str,16));
        }
     return output+"";
    }
    
}