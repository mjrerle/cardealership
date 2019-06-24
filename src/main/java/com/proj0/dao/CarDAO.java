package com.proj0.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.proj0.logger.CarDealershipLogger;
import com.proj0.models.Car;
import com.proj0.models.User;
import com.proj0.utils.DBConnector;

import org.apache.log4j.Logger;

public class CarDAO implements ICar {
    public static Connection conn = DBConnector.getConnection();
    public static Logger logger = CarDealershipLogger.logger;

    @Override
    public boolean removeCarFromLot(Car car) {
        try {
            String sql = "delete from cars where c_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, Integer.toString(car.getCid()));
            ps.execute();
            return true;
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean addCarToLot(Car car) {
        try {
            String sql = "call add_car(?, ?, ?, ?, ?)";
            CallableStatement cs = conn.prepareCall(sql);
            cs.setString(1, car.getModel());
            cs.setString(2, Integer.toString(car.getYear()));
            cs.setString(3, Double.toString(car.getPrice()));
            cs.setString(4, car.getLocation());
            cs.setString(5, Integer.toString(car.getUid()));
            cs.execute();
            return true;
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        }
        return true;
    }



    @Override
    public Car getCar(int cid) {
        try {
            String sql = "select * from cars where c_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, Integer.toString(cid));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return new Car(Integer.parseInt(rs.getString("c_id")), rs.getString("model"),
                        Integer.parseInt(rs.getString("year")), Double.parseDouble(rs.getString("price")),
                        Integer.parseInt(rs.getString("u_id")), rs.getString("location"));
            }
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        }
        return null;
    }

    @Override
    public boolean updateCar(Car car) {
        try {
            String sql = "update cars set model = ?, year = ?, price = ?, location = ?, u_id = ? where c_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, car.getModel());
            ps.setString(2, Integer.toString(car.getYear()));
            ps.setString(3, Double.toString(car.getPrice()));
            ps.setString(4, car.getLocation());
            ps.setString(5, Integer.toString(car.getUid()));
            ps.setString(6, Integer.toString(car.getCid()));
            ps.execute();
            return true;
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        }
        return false;
    }
    @Override
    public List<Car> getCarsOnLot() {
        ArrayList<Car> result = new ArrayList<>();
        try {
            String sql = "select * from cars where location = 'lot' order by c_id";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(new Car(
                    Integer.parseInt(rs.getString("c_id")),
                    rs.getString("model"),
                    Integer.parseInt(rs.getString("year")),
                    Double.parseDouble(rs.getString("price")),
                    Integer.parseInt(rs.getString("u_id")),
                    rs.getString("location")
                ));
            }
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        }
        return result;
    }

    @Override
    public List<Car> getOwnedCars(User user) {
        ArrayList<Car> result = new ArrayList<>();
        try {
            String sql = "select * from cars where u_id = ? order by c_id";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, Integer.toString(user.getUid()));
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                result.add(new Car(
                    Integer.parseInt(rs.getString("c_id")),
                    rs.getString("model"),
                    Integer.parseInt(rs.getString("year")),
                    Double.parseDouble(rs.getString("price")),
                    Integer.parseInt(rs.getString("u_id")),
                    rs.getString("location")
                ));
            }
        } catch(SQLException e) {
            logger.warn(e.getMessage());
        }
        return result;
    }
}
