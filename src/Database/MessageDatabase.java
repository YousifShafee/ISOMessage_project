package Database;

import com.sun.xml.internal.ws.api.ha.StickyFeature;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MessageDatabase {

    public static void insertInDb() throws SQLException, ClassNotFoundException
    { 
    Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/trans", "root", "");
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select id from account");
        while(rs.next())
        {
            String id = rs.getString(1);
          //  String name= rs.getString(2);
            System.out.println(id+"    ");
        }
        String sql = "INSERT INTO account " +
        "VALUES ('3','omar')";
    stmt.executeUpdate(sql);
  
    
    
    
    }
    
}
