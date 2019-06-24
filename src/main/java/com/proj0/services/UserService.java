package com.proj0.services;

import java.util.List;

import com.proj0.dao.UserDAO;
import com.proj0.models.User;

public class UserService {
    private static UserDAO udao = new UserDAO();
    public static User login(String username, String password) {
        return udao.getUser(username, password);
    }

    public static boolean register(User user) {
        if(udao.getUser(user.getUsername()) == null) {
            return udao.addUser(user);
        } else {
            return false;
        }
    }

    public static boolean changePassword(User user) {
        return udao.updateUser(user);
    }

    public static boolean removeUser(String username) {
        return udao.deleteUser(username);
    }

    public static boolean removeAllUsers() {
        return udao.deleteAllUsers();
    }

    public static User getUser(String username) {
        return udao.getUser(username);
    }

    public static User getUser(int uid) {
        return udao.getUser(uid);
    }

    public static List<User> getUsers() {
        return udao.getUsers();
    }

    public static boolean updateUser(User user) {
        return udao.updateUser(user);
    }

}
