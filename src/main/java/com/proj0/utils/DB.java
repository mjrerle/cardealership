package com.proj0.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.proj0.logger.CarDealershipLogger;

import org.apache.log4j.Logger;

public class DB {
    public static Logger logger = CarDealershipLogger.logger;
    public static Connection conn = DBConnector.getConnection();

    public static ArrayList<String> getColumnsFor(String table) {
        ArrayList<String> result = new ArrayList<>();
        try{
            String sql = "SELECT table_name, column_name, data_type, data_length FROM USER_TAB_COLUMNS WHERE table_name = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, table);
            ResultSet rs = ps.executeQuery();
            System.out.println(rs);
            while(rs.next()) {
                result.add(rs.getString(0));
            }
        } catch(SQLException e) {
            logger.fatal(e.getMessage());
            e.printStackTrace();
        }

        return null;
    }
}
