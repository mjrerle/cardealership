package com.proj0.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.proj0.logger.CarDealershipLogger;
import com.proj0.models.Offer;
import com.proj0.utils.DBConnector;

import org.apache.log4j.Logger;

public class OfferDAO implements IOffer {
    public static Connection conn = DBConnector.getConnection();
    public static Logger logger = CarDealershipLogger.logger;

    @Override
    public boolean createOffer(Offer offer) {
        try {
            String sql = "call add_offer(?, ?, ?, ?, ?, ?)";
            CallableStatement cs = conn.prepareCall(sql);
            cs.setString(1, Double.toString(offer.getAmount()));
            cs.setString(2, Integer.toString(offer.getMonthsOffered()));
            cs.setString(3, offer.getStatus());
            cs.setString(4, Double.toString(offer.getDownPayment()));
            cs.setString(5, Integer.toString(offer.getCid()));
            cs.setString(6, Integer.toString(offer.getUid()));

            cs.execute();
            return true;
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        }
        return false;
    }

    @Override
    public List<Offer> getOffers() {
        List<Offer> result = new ArrayList<>();
        try {
            String sql = "select * from offers order by o_id";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(new Offer(Integer.parseInt(rs.getString("o_id")), Double.parseDouble(rs.getString("amount")),
                        Integer.parseInt(rs.getString("months_offered")), rs.getString("status"),
                        Double.parseDouble(rs.getString("down_payment")), Integer.parseInt(rs.getString("c_id")),
                        Integer.parseInt(rs.getString("u_id"))));
            }
            return result;
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        }
        return null;
    }

    @Override
    public Offer getOffer(int oid) {
        try {
            String sql = "select * from offers where o_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, Integer.toString(oid));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return new Offer(Integer.parseInt(rs.getString("o_id")), Double.parseDouble(rs.getString("amount")),
                        Integer.parseInt(rs.getString("months_offered")), rs.getString("status"),
                        Double.parseDouble(rs.getString("down_payment")), Integer.parseInt(rs.getString("c_id")),
                        Integer.parseInt(rs.getString("u_id")));
            }
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        }
        return null;
    }

    @Override
    public boolean updateOffer(Offer offer) {
        try {
            String sql = "update offers set amount = ?, months_offered = ?, status = ?, down_payment = ? where o_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, Double.toString(offer.getAmount()));
            ps.setString(2, Integer.toString(offer.getMonthsOffered()));
            ps.setString(3, offer.getStatus());
            ps.setString(4, Double.toString(offer.getDownPayment()));
            ps.setString(5, Integer.toString(offer.getOid()));
            ps.execute();
            return true;
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        }
        return false;
    }

    @Override
    public List<Offer> getOffersForCar(int cid) {
        List<Offer> result = new ArrayList<>();
        try {
            String sql = "select * from offers where c_id = ? order by o_id";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, Integer.toString(cid));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(new Offer(Integer.parseInt(rs.getString("o_id")), Double.parseDouble(rs.getString("amount")),
                        Integer.parseInt(rs.getString("months_offered")), rs.getString("status"),
                        Double.parseDouble(rs.getString("down_payment")), Integer.parseInt(rs.getString("c_id")),
                        Integer.parseInt(rs.getString("u_id"))));
            }
            return result;
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        }
        return null;
    }

    @Override
    public boolean removeOffer(int oid) {
        try {
            String sql = "delete from offers where o_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, Integer.toString(oid));
            ps.execute();
            return true;
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        }
        return false;
    }
}
