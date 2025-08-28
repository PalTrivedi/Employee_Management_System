package Pack;

import java.sql.*;

public class App {
    public static Connection Check() throws Exception {
        String dburl = "jdbc:mysql://localhost:3306/employee_management_system";
        String dbuser = "root";
        String dbpass = "";
        String driver = "com.mysql.jdbc.Driver";
        Class.forName(driver);
        Connection con = DriverManager.getConnection(dburl, dbuser, dbpass);
        if (con != null) {
            return con;
        } else {
            return null;
        }
    }
}
