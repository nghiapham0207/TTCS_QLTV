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
import java.sql.CallableStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.Login;
import server.Connect;
//import view.Backup;
import view.ListLogin;
import view.NewLogin;

/**
 *
 * @author nghia
 */
public class DaoLogin {

    public static List<Login> getList() {
        List<Login> list = new ArrayList<>();
//        String sql = "select loginname, u.name, dbname as defdb from sys.syslogins l inner join sys.sysusers u on l.sid = u.sid where uid != 1";
        String sql = "select loginname, name, dbname as defdb from sys.syslogins where sid != 1 and hasaccess = 1 and isntname = 0 and name not like '##%'";
        Connection connection = Connect.getConnect();
        Statement statement;
        ResultSet resultSet;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                list.add(new Login(resultSet.getString("loginname"), resultSet.getString("name"), resultSet.getString("defdb")));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Can NOT get list login!");
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
        Connection connection = Connect.getConnect();
        CallableStatement cs;
        try {
            cs = connection.prepareCall(sql);
            cs.setString(1, name);
            cs.setString(2, pwd);
            cs.setString(3, defDB);
            cs.setBoolean(4, policy);
            cs.setBoolean(5, exp);
            cs.setBoolean(6, mustChange);
            cs.execute();
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

    public static void delete(String loginName, String dbName, boolean showMessage) {
//        String sql = "exec sp_removeLogin ?, ?";
        String sql = "use [" + dbName + "] drop login [" + loginName + "]";
        Connection connection = Connect.getConnect();
        Statement s;
        try {
            s = connection.createStatement();
            s.execute(sql);
            ListLogin.loadListLogin(getList());
            if (showMessage) {
                JOptionPane.showMessageDialog(null, "Success!");
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
    }

    public static void changePwd(String newPwd, String loginName) {
        String sql = "exec sp_password NUll, ?, ?";
        Connection connection = Connect.getConnect();
        CallableStatement cs;
        try {
            cs = connection.prepareCall(sql);
            cs.setString(1, newPwd);
            cs.setString(2, loginName);
            cs.execute();
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

    public static void execNonQuery(String execStmt) {
        Connection connection = Connect.getConnect();
        Statement s;
        try {
            s = connection.createStatement();
            s.execute(execStmt);
        } catch (SQLException ex) {
            Logger.getLogger(DaoRestore.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.getMessage());
            //có lỗi trong quá trình exec
            NewLogin.hasError = true;
        }
    }

    public static void addUser(String dbName, String userName, String loginName) {
        String sql = "use [" + dbName + "] create user [" + userName + "] for login [" + loginName + "]";
        Connection connection = Connect.getConnect();
        Statement s;
        try {
            s = connection.createStatement();
            s.execute(sql);
            System.out.println(sql);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
//            Logger.getLogger(DaoLogin.class.getName()).log(Level.SEVERE, null, ex);
            NewLogin.hasError = true;
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(DaoLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
