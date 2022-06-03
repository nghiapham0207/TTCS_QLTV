/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.Login;
import server.KetNoi;
import view.ListLogin;

/**
 *
 * @author nghia
 */
public class DaoLogin {

    public static List<Login> getList() {
        List<Login> list = new ArrayList<>();
//        String sql = "select loginname, u.name, dbname as defdb from sys.syslogins l inner join sys.sysusers u on l.sid = u.sid where uid != 1";
        String sql = "select loginname, name, dbname as defdb from sys.syslogins where sid != 1 and hasaccess = 1 and isntname = 0 and name not like '##%'";
        Connection connection = KetNoi.layKetNoi();
        Statement statement;
        ResultSet resultSet;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                list.add(new Login(resultSet.getString("loginname"), resultSet.getString("name"), resultSet.getString("defdb")));
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

    public static void insert(String name, String pwd, String defDB, boolean policy,
            boolean exp, boolean mustChange) {
        String sql = "exec sp_newLogin ?, ?, ?, ?, ?, ?";
        Connection connection = KetNoi.layKetNoi();
        PreparedStatement ps;
        try {
            ps = connection.prepareCall(sql);
            ps.setString(1, name);
            ps.setString(2, pwd);
            ps.setString(3, defDB);
            ps.setBoolean(4, policy);
            ps.setBoolean(5, exp);
            ps.setBoolean(6, mustChange);
            ps.execute();
            JOptionPane.showMessageDialog(null, "Success!");
            ListLogin.loadListLogin(getList());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(DaoLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void delete(String loginName, String nameInDB) {
        String sql = "exec sp_removeLogin ?, ?";
        Connection connection = KetNoi.layKetNoi();
        PreparedStatement ps;
        try {
            ps = connection.prepareCall(sql);
            ps.setString(1, loginName);
            ps.setString(2, nameInDB);
            ps.execute();
            JOptionPane.showMessageDialog(null, "Success!");
            ListLogin.loadListLogin(getList());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(DaoLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void changePwd(String newPwd, String loginName) {
        String sql = "exec sp_password NUll, ?, ?";
        Connection connection = KetNoi.layKetNoi();
        PreparedStatement ps;
        try {
            ps = connection.prepareCall(sql);
            ps.setString(1, newPwd);
            ps.setString(2, loginName);
            ps.execute();
            JOptionPane.showMessageDialog(null, "Success!");
            ListLogin.loadListLogin(getList());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(DaoLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
