package com.proj0.dao;

import java.util.List;

import com.proj0.models.Offer;

public interface IOffer {

    public boolean createOffer(Offer offer);
    public List<Offer> getOffers();
    public List<Offer> getOffersForCar(int cid);
    public boolean removeOffer(int oid);
    public Offer getOffer(int oid);
    public boolean updateOffer(Offer offer);
}
