/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.ResultSet;
import java.sql.Connection;
//import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.BackupSet;
import model.DatabaseFile;
import server.Connect;

/**
 *
 * @author nghia
 */
public class DaoRestore {

    public static String generateWherePhysicalName(String pathBak) {
        String physical_device_name = "";
        String[] listPath = pathBak.split(";");
        System.out.println("start pathString after split");
        for (int i = 0; i < listPath.length; i++) {
            physical_device_name = physical_device_name.concat("physical_device_name = N'"
                    + listPath[i] + "' ");
            if (i != listPath.length - 1) {
                physical_device_name = physical_device_name.concat(" or ");
            }
        }
        System.out.println(physical_device_name);
        System.out.println("end show pathString after split");
        return physical_device_name;
    }

    public static List<String> getDBNameFromBak(String pathBak) {
        String sql = "select distinct database_name as DatabaseName from msdb.dbo.backupset b "
                + "inner join msdb.dbo.backupmediafamily m on b.media_set_id = m.media_set_id "
                + " where "; //physical_device_name = ?
//        String sql = "RESTORE HEADERONLY FROM DISK = ?";

        sql = sql.concat(generateWherePhysicalName(pathBak));

        System.out.println(sql);
        List<String> list = new ArrayList<>();
        Connection connection = Connect.getConnect();
//        PreparedStatement ps;
        Statement ps;
        ResultSet rs;
        try {
            ps = connection.createStatement();
//            ps.setString(1, pathBak);
            rs = ps.executeQuery(sql);
            while (rs.next()) {
                list.add(rs.getString("DatabaseName"));
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

    public static List<BackupSet> getBackupSets(String dbName, String physical_device_name) {
//        String sql = "exec sp_backupsets ?, ?";
        String path = generateWherePhysicalName(physical_device_name);
        //poor
        String sql = "SELECT case [type]"
                + "		when 'D' then 'Full'"
                + "		when 'I' then 'Differential'"
                + "		when 'L' then 'Transaction Log'"
                + "		else [type]"
                + "	end "
                + "as backuptype, physical_device_name, database_name, position, backup_start_date, backup_finish_date "
                + "FROM msdb.dbo.backupset "
                + "  INNER JOIN msdb.dbo.backupmediafamily ON backupset.media_set_id = backupmediafamily.media_set_id "
                + "WHERE database_name = N'" + dbName + "' and "
                + "(" + path
                + ")  and backup_finish_date >= (SELECT TOP 1 backup_finish_date "
                + " FROM msdb.dbo.backupset b1 "
                + " WHERE b1.database_name = N'" + dbName + "' AND"
                + " b1.type = 'D'"
                + " ORDER BY b1.backup_finish_date DESC)"
                + "ORDER BY [type], backup_finish_date";
        System.out.println("get backup sets");
        System.out.println(sql);
        List<BackupSet> list = new ArrayList<>();
        Connection connection = Connect.getConnect();
//        CallableStatement cs;
        Statement s;
        ResultSet rs;
        try {
//            cs = connection.prepareCall(sql);
//            cs.setString(1, dbName);
//            cs.setString(2, generateWherePhysicalName(physical_device_name));
            s = connection.createStatement();
            rs = s.executeQuery(sql);
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
        Connection connection = Connect.getConnect();
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
        Connection connection = Connect.getConnect();
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

    public static List<DatabaseFile> getDBFiles(String dbName) {
        String sql = "use [" + dbName
                + "] select type_desc as Type, name as LogicalName, physical_name as PhysicalName from sys.database_files";
//        String sql = "RESTORE FILELISTONLY  FROM DISK = '" + pathDisk + "'";
        List<DatabaseFile> list = new ArrayList<>();
        Connection connection = Connect.getConnect();
        Statement s;
        ResultSet rs;
        try {
            s = connection.createStatement();
            rs = s.executeQuery(sql);
            while (rs.next()) {
                String type = "";
                switch (rs.getString("Type")) {
                    case "ROWS" ->
                        type = "Rows Data";
                    case "LOG" ->
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

    public static boolean checkExistDB(String dbName) {
        String sql = "select name from sys.sysdatabases where name = '" + dbName + "'";
        Connection connection = Connect.getConnect();
        Statement s;
        ResultSet rs;
        try {
            s = connection.createStatement();
            System.out.println("check existing db: " + sql);
            rs = s.executeQuery(sql);
            if (rs.next()) {
                return true; //existing
            }
        } catch (SQLException ex) {
            Logger.getLogger(DaoRestore.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
