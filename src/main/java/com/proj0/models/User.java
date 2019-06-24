package com.proj0.models;

public class User {
    private String username;
    private String password;
    private String role;
    private int uid;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.role = "anonymous";
        this.uid = -1;
    }

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.uid = -1;
    }

    public User(int uid, String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.uid = uid;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getUid() {
        return this.uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        // return "{" +
        // " username='" + getUsername() + "'" +
        // ", password='" + getPassword() + "'" +
        // ", role='" + getRole() + "'" +
        // ", uid='" + getUid() + "'" +
        // "}";
        String format = "|%1$-20s|%2$-20s|%3$-20s|%4$-5s|";
        return String.format(format, getUsername(), getPassword(),
                getRole(), Integer.toString(getUid()));
    }

}
