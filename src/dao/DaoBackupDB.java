/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import server.Connect;
import view.Backup;
/**
 *
 * @author nghia
 */
public class DaoBackupDB {
    public static void full(String dbName, String path){
        String sql="exec sp_backupdb_full ?, ?";
        Connection connection;
        CallableStatement cs;
        connection = Connect.getConnect();
        try {
            cs = connection.prepareCall(sql);
            cs.setString(1, dbName);
            cs.setString(2, path);
            cs.execute();
            //JOptionPane.showMessageDialog(null, "Success!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(DaoDatabaseRoles.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void differential(String dbName, String path){
        String sql="exec sp_backupdb_diff ?, ?";
        Connection connection;
        CallableStatement cs;
        connection = Connect.getConnect();
        try {
            cs = connection.prepareCall(sql);
            cs.setString(1, dbName);
            cs.setString(2, path);
            cs.execute();
            //JOptionPane.showMessageDialog(null, "Success!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(DaoDatabaseRoles.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void log(String dbName, String path){
        String sql="exec sp_backuplog ?, ?";
        Connection connection;
        CallableStatement cs;
        connection = Connect.getConnect();
        try {
            cs = connection.prepareCall(sql);
            cs.setString(1, dbName);
            cs.setString(2, path);
            cs.execute();
            //JOptionPane.showMessageDialog(null, "Success!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(DaoDatabaseRoles.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void execNonQuery(String execStmt) {
        Connection connection = Connect.getConnect();
        Statement s;
        try {
            s = connection.createStatement();
            s.execute(execStmt);
        } catch (SQLException ex) {
            Logger.getLogger(DaoRestore.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.getMessage());
            //có lỗi trong quá trình backup list file
            Backup.hasError++;
        }
    }
}
