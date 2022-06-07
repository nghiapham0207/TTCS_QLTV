/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.BackupSet;
import model.DatabaseFile;
import server.KetNoi;

/**
 *
 * @author nghia
 */
public class DaoRestore {

    public static List<String> getDBNameFromBak(String pathBak) {
//        String sql = "select distinct database_name as DatabaseName from msdb.dbo.backupset b "
//                + "inner join msdb.dbo.backupmediafamily m on b.media_set_id = m.media_set_id "
//                + " where physical_device_name = ?";
        String sql = "RESTORE HEADERONLY FROM DISK = ?";
        List<String> list = new ArrayList<>();
        Connection connection = KetNoi.layKetNoi();
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = connection.prepareCall(sql);
            ps.setString(1, pathBak);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("DatabaseName"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(DaoLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return list;
    }

    public static List<BackupSet> getBackupSets(String dbName, String physical_device_name) {
        String sql = "exec sp_backupsets ?, ?";
        List<BackupSet> list = new ArrayList<>();
        Connection connection = KetNoi.layKetNoi();
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = connection.prepareCall(sql);
            ps.setString(1, dbName);
            ps.setString(2, physical_device_name);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new BackupSet(
                        true,
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getString(5),
                        rs.getString(6)
                ));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DaoLogin.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(DaoLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return list;
    }

    //You are connected to the database you are trying to restore.
    public static void takeOffline(String dbName) {
//        String sql = "use [master] alter database " + dbName + " set offline";
        String sql = "use [master] alter database [" + dbName
                + "] set single_user with rollback immediate";
        Connection connection = KetNoi.layKetNoi();
        Statement s;
        try {
            s = connection.createStatement();
            s.execute(sql);
            JOptionPane.showMessageDialog(null, "Success!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            Logger.getLogger(DaoLogin.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(DaoLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void execNonQuery(String execStmt) {
        Connection connection = KetNoi.layKetNoi();
        Statement s;
        try {
            s = connection.createStatement();
            s.execute(execStmt);
            JOptionPane.showMessageDialog(null, "Restore successfully!");
        } catch (SQLException ex) {
            //Logger.getLogger(DaoRestore.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public static List<DatabaseFile> getDBFiles(String pathDisk) {
//        String sql = "use [" + dbName
//                + "] select type_desc, name, physical_name from sys.database_files";
        String sql = "RESTORE FILELISTONLY  FROM DISK = '" + pathDisk + "'";
        List<DatabaseFile> list = new ArrayList<>();
        Connection connection = KetNoi.layKetNoi();
        Statement s;
        ResultSet rs;
        try {
            s = connection.createStatement();
            rs = s.executeQuery(sql);
            while (rs.next()) {
                String type = "";
                switch (rs.getString("Type")) {
                    case "D" ->
                        type = "Rows Data";
                    case "L" ->
                        type = "Log";
                }
                list.add(new DatabaseFile(
                        type,
                        rs.getString("LogicalName"),
                        rs.getString("PhysicalName"),
                        ""
                ));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            Logger.getLogger(DaoLogin.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(DaoLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return list;
    }
}
