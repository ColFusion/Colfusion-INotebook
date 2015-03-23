/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pitt.sis.infsci2711.iNotebook.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.pitt.sis.infsci2711.iNotebook.models.UserDBModel;
import edu.pitt.sis.infsci2711.iNotebook.utils.JdbcUtil;

/**
 *
 * @author Wu
 */
public class UserDAO {
    
    public static List<UserDBModel> findAll() throws SQLException, Exception {
        
        try (Connection connection = JdbcUtil.getConnection()) {
            String sql = "SELECT * FROM Person";
            try (Statement statement = connection.createStatement()) {
                
                ResultSet resultSet = statement.executeQuery(sql);
                
                List<UserDBModel> result = new ArrayList<UserDBModel>();
                
                while (resultSet.next()) {
                    result.add(new UserDBModel(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4)));
                }
                
                return result;
            }
        }
        
    }
    
    public static UserDBModel findById(final int id) throws SQLException, Exception {
        
        try (Connection connection = JdbcUtil.getConnection()) {
            String sql = "SELECT * FROM Person where id = " + id;
            try (Statement statement = connection.createStatement()) {
                
                ResultSet resultSet = statement.executeQuery(sql);
                
                while (resultSet.next()) {
                    return new UserDBModel(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),resultSet.getString(4));
                }
                
                return null;
            }
        }
        
    }
    
    public static int save(final UserDBModel user) throws SQLException, Exception {
        
        
        try (Connection connection = JdbcUtil.getConnection()) {
            String sql = String.format("INSERT INTO Person (username, password, email) VALUES ('%s', '%s','%s')", user.getUserName(), user.getPassword(),user.getEmail());
            System.out.println(sql);
            try (Statement statement = connection.createStatement()) {
                
                int res = statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
                
                ResultSet rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    user.setId(rs.getInt(1));
                }
                
                return res;
            }
        }
        
    }
    
    public static void delete(final int id) throws SQLException, Exception {
        
        try (Connection connection = JdbcUtil.getConnection()) {
            String sql = String.format("DELETE FROM Person WHERE id = '%s'", id);
            try (Statement statement = connection.createStatement()) {
                System.out.println(sql);
                int res = statement.executeUpdate(sql);
                
            }
        }
    }

    public static boolean checkUserName(final UserDBModel user) throws Exception {
        try (Connection connection = JdbcUtil.getConnection()) {
            String sql = String.format("SELECT FROM Person where username = '%s'", user.getUserName());
            boolean res = false;
            try (Statement statement = connection.createStatement()) {
                
                ResultSet rs = statement.executeQuery(sql);
                
                if (rs.next()) {
                    res = true;
                }
                
                return res;
            }
        }
    }

    public static UserDBModel checkUser(final UserDBModel user) throws Exception {
        try (Connection connection = JdbcUtil.getConnection()) {
            String sql = String.format("SELECT FROM Person where username = '%s' and  password = '%s'", user.getUserName(), user.getPassword());
            try (Statement statement = connection.createStatement()) {
                
                ResultSet rs = statement.executeQuery(sql);
                
                if (rs.next()) {
                    user.setId(rs.getInt(1));
                } else {
                    return null;
                }
                
                return user;
            }
        }
    }
}
