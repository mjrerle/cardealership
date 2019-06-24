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
import com.proj0.models.Payment;
import com.proj0.models.User;
import com.proj0.utils.DBConnector;

import org.apache.log4j.Logger;

public class PaymentDAO implements IPayment {
    public static Connection conn = DBConnector.getConnection();
    public static Logger logger = CarDealershipLogger.logger;

    @Override
    public List<Payment> getPaymentsOnCarForUser(User user, Car car) {
        ArrayList<Payment> result = new ArrayList<>();
        try {
            String sql = "select * from payments where u_id = ? and c_id = ? order by p_id";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, Integer.toString(user.getUid()));
            ps.setString(2, Integer.toString(car.getCid()));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(new Payment(Integer.parseInt(rs.getString("p_id")),
                        Double.parseDouble(rs.getString("amount_paid")), Integer.parseInt(rs.getString("months_left")),
                        Integer.parseInt(rs.getString("c_id")), Integer.parseInt(rs.getString("u_id"))));
            }
            return result;
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        }
        return null;
    }

    @Override
    public boolean createPayment(Payment payment) {
        try {
            String sql = "call add_payment(?, ?, ?, ?)";
            CallableStatement cs = conn.prepareCall(sql);
            cs.setString(1, Double.toString(payment.getAmountPaid()));
            cs.setString(2, Integer.toString(payment.getMonthsLeft()));
            cs.setString(3, Integer.toString(payment.getCid()));
            cs.setString(4, Integer.toString(payment.getUid()));

            cs.execute();
            return true;
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        }
        return false;
    }

    @Override
    public List<Payment> getPayments() {
        List<Payment> result = new ArrayList<>();
        try {
            String sql = "select * from payments order by p_id";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(new Payment(Integer.parseInt(rs.getString("p_id")),
                        Double.parseDouble(rs.getString("amount_paid")), Integer.parseInt(rs.getString("months_left")),
                        Integer.parseInt(rs.getString("c_id")), Integer.parseInt(rs.getString("u_id"))));
            }
            return result;
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Payment> getPaymentsForCar(int cid) {
        List<Payment> result = new ArrayList<>();
        try {
            String sql = "select * from payments where c_id = ? order by p_id";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, Integer.toString(cid));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(new Payment(Integer.parseInt(rs.getString("p_id")),
                        Double.parseDouble(rs.getString("amount_paid")), Integer.parseInt(rs.getString("months_left")),
                        Integer.parseInt(rs.getString("c_id")), Integer.parseInt(rs.getString("u_id"))));
            }
            return result;
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        }
        return null;
    }

    @Override
    public double getRemainingPaymentsLumpSum(User user, Car car) {
        try {
            String sql = "select amount_paid, min(months_left) as min from payments where u_id = ? and c_id = ? group by amount_paid";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, Integer.toString(user.getUid()));
            ps.setString(2, Integer.toString(car.getCid()));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return Double.parseDouble(rs.getString("amount_paid")) * Double.parseDouble(rs.getString("min"));
            }

        } catch (SQLException e) {
            logger.warn(e.getMessage());
        }
        return 0;
    }
}
