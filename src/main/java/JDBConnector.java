import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBConnector {
    public static Connection getConnection () {
        //dette skal ændres så det passer dem den computer det køres på
        Connection con = null;
        String user = "root";
        String password = "123root";
        String urlAddOn = "?serverTimezone=UTC&allowPublicKeyRetrieval=true&useSSL=false";
        String url = "jdbc:mysql://localhost/ebberodbank" + urlAddOn;
        try {
            con = DriverManager.getConnection(url, user, password);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }
}
