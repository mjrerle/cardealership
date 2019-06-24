package com.proj0.driver;

import java.util.ArrayList;

public class Capabilities {
    public static ArrayList<String> getCustomerCapabilities() {
        ArrayList<String> result = new ArrayList<>();
        result.add("Make An Offer");
        result.add("Make A Payment");
        result.add("View Cars On Lot");
        result.add("View Owned Cars");
        result.add("View Remaining Payments");
        result.add("Change Password");
        result.add("Main Menu");
        result.add("Logout");
        result.add("Quit");
        return result;
    }

    public static ArrayList<String> getEmployeeCapabilities() {
        ArrayList<String> result = new ArrayList<>();
        result.add("Remove Car From Lot");
        result.add("Add Car To Lot");
        result.add("Evaluate Offers");
        result.add("View All Payments");
        result.add("Manage Users");
        result.add("Change Password");
        result.add("Main Menu");
        result.add("Logout");
        result.add("Quit");

        return result;
    }

    public static ArrayList<String> getUserCapabilities() {
        ArrayList<String> result = new ArrayList<>();
        result.add("Login");
        result.add("Register");
        result.add("Change Password");
        result.add("Logout");
        result.add("Quit");

        return result;
    }


    public static ArrayList<String> getAdminCapabilities() {
        ArrayList<String> result = new ArrayList<>();
        result.add("Make An Offer");
        result.add("Make A Payment");
        result.add("Add Car To Lot");
        result.add("View Cars On Lot");
        result.add("View Owned Cars");
        result.add("View Remaining Payments");
        result.add("View All Payments");
        result.add("Remove Car From Lot");
        result.add("Evaluate Offers");
        result.add("Manage Users");
        result.add("Change Password");
        result.add("Main Menu");
        result.add("Logout");
        result.add("Quit");
        return result;
    }
}
