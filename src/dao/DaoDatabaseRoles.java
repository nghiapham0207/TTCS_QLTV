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
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.Member;
import server.KetNoi;

/**
 *
 * @author nghia
 */
public class DaoDatabaseRoles {

    public static List<String> getList() {
        String sql = "select name from sys.sysusers where issqlrole = 1";
        List<String> list = new ArrayList<>();
        Connection connection = KetNoi.layKetNoi();
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

    public static void removeMember(String roleName, String memberName) {
        String sql = "exec sp_droprolemember ?, ?";
        Connection connection;
        PreparedStatement ps;
        connection = KetNoi.layKetNoi();
        try {
            ps = connection.prepareCall(sql);
            ps.setString(1, roleName);
            ps.setString(2, memberName);
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
    
    public static void insert(String roleName){
        String sql = "exec sp_addrole ?";
        Connection connection;
        PreparedStatement ps;
        connection = KetNoi.layKetNoi();
        try {
            ps = connection.prepareCall(sql);
            ps.setString(1, roleName);
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
    
    public static void addMember(String roleName, List<Member> list){
        String sql="exec sp_addrolemember ?, ?";
        Connection connection;
        PreparedStatement ps;
        connection = KetNoi.layKetNoi();
//        int count=0;
        try {
            ps = connection.prepareCall(sql);
            for (Member member : list) {
                ps.setString(1, roleName);
                ps.setString(2, member.getName());
                ps.execute();
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
