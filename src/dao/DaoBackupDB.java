/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import server.KetNoi;
/**
 *
 * @author nghia
 */
public class DaoBackupDB {
    public static void full(String dbName, String path){
        String sql="exec sp_backupdb_full ?, ?";
        Connection connection;
        PreparedStatement ps;
        connection = KetNoi.layKetNoi();
        try {
            ps = connection.prepareCall(sql);
            ps.setString(1, dbName);
            ps.setString(2, path);
            ps.execute();
            JOptionPane.showMessageDialog(null, "Success!");
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
        PreparedStatement ps;
        connection = KetNoi.layKetNoi();
        try {
            ps = connection.prepareCall(sql);
            ps.setString(1, dbName);
            ps.setString(2, path);
            ps.execute();
            JOptionPane.showMessageDialog(null, "Success!");
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
        PreparedStatement ps;
        connection = KetNoi.layKetNoi();
        try {
            ps = connection.prepareCall(sql);
            ps.setString(1, dbName);
            ps.setString(2, path);
            ps.execute();
            JOptionPane.showMessageDialog(null, "Success!");
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
}
