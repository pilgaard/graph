/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.graph;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Emil
 */
public class SQL {

    public SQL() {
    }

    public List<Person> getNames() {
        List<Person> names = new ArrayList<>();
        
        Connection con = null;
        ResultSet rs = null;
        try {
            con = getConn();
            CallableStatement stmt = con.prepareCall("{call getNames()}");
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Person p = new Person();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                names.add(p);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
                System.err.println(ex);
            }
        }
        return names;
    }

    public void makeCall(int id, int dept) {
        List<Person> names = new ArrayList<>();
        Connection con = null;
        ResultSet rs = null;

        try {
            con = getConn();
            CallableStatement stmt = con.prepareCall("{call dept" + dept + "(?)}");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Person p = new Person();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                names.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
                System.err.println(ex);
            }
        }
    }
    
    private Connection getConn(){
        String url = "jdbc:mysql://localhost:3306/graph project";
        String user = "root";
        String password = "pwd";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }   
}
