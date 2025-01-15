package connecter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class db_connection {

    public Connection connect() {
        try {
            String url = "jdbc:mysql://localhost:3306/schedule_management_db";
            String user = "root";
            String password = "root";

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            return conn;
        } catch (ClassNotFoundException | SQLException e) {
            Logger.getLogger(db_connection.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }
}