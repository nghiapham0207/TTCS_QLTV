/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author nghia
 */
public class KetNoi {

    public static Connection layKetNoi() {
        FileReader fr;
        Properties p;
        String user;
        String password;
        String db;
        String port;
        String url;
        Connection connection = null;
        try {
            fr = new FileReader(new File("connection.properties"));
            p = new Properties();
            p.load(fr);
            user = p.getProperty("Username");
            password = p.getProperty("Password");
            db = p.getProperty("DatabaseName");
            port = p.getProperty("Port");
            url = "jdbc:sqlserver://localhost:" + port + ";databaseName=" + db;
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, user, password);
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } catch (SQLException | ClassNotFoundException | IOException ex) {
            System.out.println("Kết nối thất bại!");
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return connection;
    }
}
