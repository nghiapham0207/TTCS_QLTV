/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Member;
import server.KetNoi;

/**
 *
 * @author nghia
 */
public class DaoMembers {
    public static List<String> getList(String roleName){
        String sql="exec sp_getlistmember ?";
        List<String> list = new ArrayList<>();
        Connection connection = KetNoi.layKetNoi();
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps=connection.prepareCall(sql);
            ps.setString(1, roleName);
            rs=ps.executeQuery();
            while (rs.next()) {                
                list.add(rs.getString("Role members"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DaoLogin.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(DaoLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return list;
    }
    
    public static List<Member> getListMemberToAdd(){
        String sql="select * from v_membertoadd";
        List<Member> list = new ArrayList<>();
        Connection connection = KetNoi.layKetNoi();
        Statement statement;
        ResultSet rs;
        try {
            statement=connection.createStatement();
            rs = statement.executeQuery(sql);
            while (rs.next()) {                
                list.add(new Member(rs.getString(1), rs.getString(2), false));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DaoLogin.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(DaoLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return list;
    }
}
