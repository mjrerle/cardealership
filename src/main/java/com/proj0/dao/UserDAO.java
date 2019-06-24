package com.proj0.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.proj0.logger.CarDealershipLogger;
import com.proj0.models.User;
import com.proj0.utils.DBConnector;

import org.apache.log4j.Logger;

public class UserDAO implements IUser {
    public static Connection conn = DBConnector.getConnection();
    public static Logger logger = CarDealershipLogger.logger;

    @Override
    public User getUser(String username, String password) {
        try {
            String sql = "select * from users where username = ? and password = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return new User(Integer.parseInt(rs.getString("u_id")), rs.getString("username"),
                        rs.getString("password"), rs.getString("role"));
            }
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        }
        return null;
    }

    @Override
    public boolean addUser(User user) {
        try {
            String sql = "call add_user(?, ?, ?)";
            CallableStatement cs = conn.prepareCall(sql);
            cs.setString(1, user.getUsername());
            cs.setString(2, user.getPassword());
            cs.setString(3, user.getRole());

            cs.execute();
            return true;
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean updateUser(User user) {
        try {
            String sql = "update users set password = ?, role = ? where username = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user.getPassword());
            ps.setString(2, user.getRole());
            ps.setString(3, user.getUsername());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteUser(String username) {
        try {
            String sql = "delete from users where username = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteAllUsers() {
        try {
            String sql = "delete users";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        }
        return false;
    }

    @Override
    public User getUser(String username) {
        try {
            String sql = "select * from users where username = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return new User(Integer.parseInt(rs.getString("u_id")), rs.getString("username"),
                        rs.getString("password"), rs.getString("role"));
            }
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        }
        return null;
    }

    @Override
    public User getUser(int uid) {
        try {
            String sql = "select * from users where u_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, Integer.toString(uid));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return new User(Integer.parseInt(rs.getString("u_id")), rs.getString("username"),
                        rs.getString("password"), rs.getString("role"));
            }
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        }
        return null;
    }

    @Override
    public List<User> getUsers() {
        List<User> result = new ArrayList<>();

        try {
            String sql = "select * from users order by u_id";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(new User(Integer.parseInt(rs.getString("u_id")), rs.getString("username"),
                        rs.getString("password"), rs.getString("role")));
            }
            return result;
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        }
        return null;
    }
}
