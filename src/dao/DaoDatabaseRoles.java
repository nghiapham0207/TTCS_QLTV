/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.sql.Statement;
import java.sql.CallableStatement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.Member;
import server.Connect;

/**
 *
 * @author nghia
 */
public class DaoDatabaseRoles {

    public static List<String> getList(String dbName) {
        String sql = "use [" + dbName + "] select name from sys.sysusers where issqlrole = 1";
        List<String> list = new ArrayList<>();
        Connection connection = Connect.getConnect();
        Statement statement;
        ResultSet resultSet;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                list.add(resultSet.getString("name"));
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

    public static void removeMember(String roleName, String memberName, String dbName) {
        String sql = "use [" + dbName + "] exec sp_droprolemember ?, ?";
        Connection connection;
        CallableStatement cs;
        connection = Connect.getConnect();
        try {
            cs = connection.prepareCall(sql);
            cs.setString(1, roleName);
            cs.setString(2, memberName);
            cs.execute();
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

    public static void insert(String roleName, String dbName) {
//        String sql = "exec sp_addrole ?";
        String sql = "USE [" + dbName + "] CREATE ROLE [" + roleName + "]";
        Connection connection;
        Statement s;
//        CallableStatement ps;
        connection = Connect.getConnect();
        try {
//            ps = connection.prepareCall(sql);
//            ps.setString(1, roleName);
//            ps.execute();
            s = connection.createStatement();
            s.execute(sql);
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

    public static void addMember(String roleName, List<Member> list, String dbName) {
//        String sql = "USE ["+dbName+ "]" + " GO " + "ALTER ROLE [" + roleName + "] ADD MEMBER [?]";
        String sql = "use [" + dbName + "] exec sp_addrolemember ?, ?";
        Connection connection;
        CallableStatement cs;
        connection = Connect.getConnect();
//        int count=0;
        try {
            cs = connection.prepareCall(sql);
            for (Member member : list) {
                cs.setString(1, roleName);
                cs.setString(2, member.getName());
                cs.execute();
            }
            JOptionPane.showMessageDialog(null, "Success!\nRefresh to update!");
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
