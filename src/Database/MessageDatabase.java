package Database;

import iso8583.FieldInfo;
import iso8583.Utility;
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
        // String value="VALUES (";
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "root", "");
        Statement stmt = con.createStatement();
        //ResultSet rs = stmt.executeQuery("select id from account");
//        while(rs.next())
//        {
//            String id = rs.getString(1);
//          //  String name= rs.getString(2);
//            System.out.println(id+"    ");
//        }
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
        int errorcode = 0; // 0 if no error occured and message was accepted 
        if(Utility.MsgStatus)
            Status="ACCEPTED";
        else {
            Status="REJECTED";
            errorcode=7 ; //7 is the error code if the message is rejected as it was send before the sign-on message
        }
       

        String sql = "INSERT INTO elements(`ErrorCode`,`MTI`," + column + ",`Status`,`LoggingTime`) VALUES ("+errorcode+"," + MTI + "," + Value + "," + '"' + Status + '"' + " ," + CurrDate + ")";
        stmt.executeUpdate(sql);

    }

}
