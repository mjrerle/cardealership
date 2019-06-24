package com.proj0.dao;

import java.util.List;

import com.proj0.models.User;

public interface IUser {
    public User getUser(String username, String password);
    public User getUser(String username);
    public User getUser(int uid);
    public List<User> getUsers();
    public boolean addUser(User user);
    public boolean updateUser(User user);
    public boolean deleteUser(String username);
    public boolean deleteAllUsers();
}
