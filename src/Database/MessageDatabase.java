package Database;

import iso8583.FieldInfo;
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
    public static String HexToAsci(String hex){
        
        StringBuilder output = new StringBuilder();
        for(int i =0;i<hex.length();i+=2)
        {String str=hex.substring(i, i+2);
        output.append((char)Integer.parseInt(str,16));
        }
     return output+"";
    }
    public void insertInDb(ArrayList<FieldInfo> DataInfo,String MTI) throws SQLException, ClassNotFoundException
    { 
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
        String column="`Field"+DataInfo.get(0).getFieldNo()+"` ";
        String Value=HexToAsci(DataInfo.get(0).getDE());
        for(int i =1; i<DataInfo.size();i++){
            column += ", `Field"+DataInfo.get(i).getFieldNo()+"`";
            Value +=", "+'"'+HexToAsci(DataInfo.get(i).getDE())+'"';
            
        }





        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String CurrDate='"'+dateFormat.format(date)+'"';


        String sql = "INSERT INTO elements(`MTI`," + column+",`Status`,`LoggingTime`) VALUES ("+MTI+","+Value+","+'"'+"ACCEPTED"+'"'+" ,"+CurrDate+")";
        stmt.executeUpdate(sql);
       
  
    
    
    
    }
    
}
