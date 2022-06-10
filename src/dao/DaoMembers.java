/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.CallableStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Member;
import server.Connect;

/**
 *
 * @author nghia
 */
public class DaoMembers {

    public static List<String> getList(String roleName, String dbName) {
//        String sql = "exec sp_getlistmember ?";
        String sql = "use [" + dbName + "] select m.name as [Role Members] from sys.database_role_members rm"
                + "\tinner join sys.database_principals r on rm.role_principal_id = r.principal_id"
                + "\tinner join sys.database_principals m on rm.member_principal_id = m.principal_id"
                + "\twhere r.name = ?";
        List<String> list = new ArrayList<>();
        Connection connection = Connect.getConnect();
        CallableStatement cs;
        ResultSet rs;
        try {
            cs = connection.prepareCall(sql);
            cs.setString(1, roleName);
            rs = cs.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("Role members"));
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

    public static List<Member> getListMemberToAdd(String dbName) {
//        String sql="select * from v_membertoadd";
        String sql = "use [" + dbName
                + "] select name, 'User' as [type] from sys.sysusers"
                + " where issqlrole = 0 and uid not in (1, 3, 4)\n"
                + "union\n"
                + "select name, 'Database Role' as [type] from sys.database_principals"
                + " where is_fixed_role = 0 and type = 'R'";
        List<Member> list = new ArrayList<>();
        Connection connection = Connect.getConnect();
        Statement statement;
        ResultSet rs;
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery(sql);
            while (rs.next()) {
                list.add(new Member(rs.getString(1), rs.getString(2), false));
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
}
