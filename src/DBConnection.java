import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    static String url = "jdbc:mysql://localhost:3306/bloodbank";
    static String user = "root";
    static String password = "Kavi@2005"; // your mysql password

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(url, user, password);
    }
}
