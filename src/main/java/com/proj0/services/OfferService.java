package com.proj0.services;

import java.util.List;

import com.proj0.dao.OfferDAO;
import com.proj0.models.Offer;

public class OfferService {
    private static OfferDAO odao = new OfferDAO();


    public static boolean createOffer(Offer offer) {
        return odao.createOffer(offer);
    }
    public static List<Offer> evaluateOffersForACar(int cid) {
        return odao.getOffersForCar(cid);
    }
    public static List<Offer> evaluateOffers() {
        return odao.getOffers();
    }

    public static boolean evaluateOffer(Offer offer) {
        return odao.updateOffer(offer);
    }

    public static Offer getOffer(int oid) {
        return odao.getOffer(oid);
    }

    public static boolean removeOffer(int oid) {
        return odao.removeOffer(oid);
    }



}
