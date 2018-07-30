package Database;

import iso8583.FieldInfo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MessageDatabase {
    public static String HexToAsci(String hex){
        
        StringBuilder output = new StringBuilder();
        for(int i =0;i<hex.length();i+=2)
        {String str=hex.substring(i, i+2);
        output.append((char)Integer.parseInt(str,16));
        }
     return output+"";
    }
    public void insertInDb(ArrayList<FieldInfo> DataInfo) throws SQLException, ClassNotFoundException
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
            Value +=", "+HexToAsci(DataInfo.get(i).getDE());
        }








        String sql = "INSERT INTO elements( " + column+") VALUES ("+Value +")";
    stmt.executeUpdate(sql);
      //  System.out.println(sql);
  
    
    
    
    }
    
}
