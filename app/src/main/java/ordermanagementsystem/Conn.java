package ordermanagementsystem;

import java.sql.*;

public class Conn {
    private Connection con;
    public Statement statement;
    private String connectionString = "jdbc:mysql://localhost:3306/restaurantmanagement";
    private String username = "root";
    private String password = "root1234";
    
    public Conn(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(connectionString, username, password);
            statement = con.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
