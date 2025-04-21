import java.sql.*;

public class DBConnection {
    public static Connection getConnection() {
        Connection connection = null;
        try {
            String url= "jdbc:mariadb://localhost:3306/StatTracker";
            String username="root";
            String password="0110";
            // Get connection
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return connection;
    }
}
