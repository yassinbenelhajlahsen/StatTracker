import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    public static Connection getConnection() {
        Connection connection = null;
        try {
            String url= "jdbc:mariadb://localhost:3306/StatTracker";
            String username="root";
            String password="0110";
            // Get connection
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}
