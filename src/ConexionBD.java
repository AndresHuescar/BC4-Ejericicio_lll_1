import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    static String url = "jdbc:oracle:thin:@localhost:1521:XE";
    static String user = "RIBERA";
    static String pass = "ribera";

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(url, user, pass);
    }
}