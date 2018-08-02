package Database;

import commons.Constants;
import iso8583.FieldInfo;
import commons.Utility;
import iso8583.Reversing;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

//By:Omar Saad, Mostafa Mohamed Abdel Rahaman , Mariam Esmail
public class MessageDatabase {

    public static String HexToAsci(String hex) {

        StringBuilder output = new StringBuilder();
        for (int i = 0; i < hex.length(); i += 2) {
            String str = hex.substring(i, i + 2);
            output.append((char) Integer.parseInt(str, 16));
        }
        return output + "";
    }

    public void insertInDb(ArrayList<FieldInfo> DataInfo, String MTI) throws SQLException, ClassNotFoundException {
        
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "root", "");
        Statement stmt = con.createStatement();
        
        String column = "`Field" + DataInfo.get(0).getFieldNo() + "` ";
        String Value = HexToAsci(DataInfo.get(0).getDE());
        for (int i = 1; i < DataInfo.size(); i++) {
            column += ", `Field" + DataInfo.get(i).getFieldNo() + "`";
            Value += ", " + '"' + HexToAsci(DataInfo.get(i).getDE()) + '"';

        }
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String CurrDate = '"' + dateFormat.format(date) + '"';
        
        
        
        
        // To check whether sign-on message has been recieved or not so that the message is accepted or rejected
        // Omar Saad & Islam Tarek // 1-8-2018
        
        String Status = "";
        int errorcode = Constants.ISO_ERROR_CODE_SUCCESS; // 0 if no error occured and message was accepted 
        if(Utility.MsgStatus)
            Status="ACCEPTED";
        else {
            Status="REJECTED";
            errorcode=Constants.ISO_ERROR_CODE_SIGN_ON_NOT_RECEIVED ; //7 is the error code if the message is rejected as it was send before the sign-on message
        }
         if(Utility.ReversedStatus!=null){
         if(Utility.ReversedStatus.getStatus()==Reversing.ACCEPTED)
            Status="ACCEPTED";
        else if(Utility.ReversedStatus.getStatus()==Reversing.REJECTED){
            Status="REJECTED";
            errorcode=Constants.ISO_ERROR_REVERSING_REJECTED ;
        }
        else if(Utility.ReversedStatus.getStatus()==Reversing.NOT_FOUND){
            Status="REJECTED";
            errorcode=Constants.ISO_ERROR_REVERSING_NOTFOUND ;
        }
           }
        

        String sql = "INSERT INTO elements(`ErrorCode`,`MTI`," + column + ",`Status`,`LoggingTime`) VALUES ("+errorcode+"," + MTI + "," + Value + "," + '"' + Status + '"' + " ," + CurrDate + ")";
        stmt.executeUpdate(sql);

    }
    
    //Omar Saad & Youssef Shafee & Mostafa Mohamed & Islam tareq // 2 -8-2018  
    public static Reversing SearchReversal (String MTI,String DE7,String DE11,String DE12) throws SQLException, ClassNotFoundException{
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "root", "");
        Statement stmt = con.createStatement();
                  ResultSet rs = stmt.executeQuery("SELECT `Status`,`id`,`Field12` FROM `elements` WHERE `MTI`="+'"'+MTI+'"'+" AND `Field7`="+'"'+DE7+'"'+" AND `Field11`="+'"'+DE11+'"');//" AND `Field12`="+'"'+DE12+'"'//" AND `Field12`="+'"'+DE12+'"'
           String Status=""; 
           int id=0;
         while(rs.next())
        {
            String f = rs.getString(3);
            f= f.charAt(0)+""+f.charAt(1);
          
            if(f.equals(DE12)){
            id = rs.getInt(2);
            Status = rs.getString(1);
             //System.out.println(Status+'\n'+id+'\n');
            }
            
               
            //System.out.println(Status);
            //String name= rs.getString(2);
        }
        
        if (Status.equals("ACCEPTED")){
            stmt.executeUpdate("UPDATE elements SET Status ="+'"'+"REVERSED"+'"'+ "WHERE id = "+id);
            return Reversing.ACCEPTED;}
        else if (Status.equals("REJECTED"))
            return Reversing.REJECTED;
        else {
            return Reversing.NOT_FOUND;
            
        }
    }

}
