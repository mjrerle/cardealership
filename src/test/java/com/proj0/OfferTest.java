package com.proj0;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import com.proj0.models.Car;
import com.proj0.models.Offer;
import com.proj0.services.OfferService;

public class OfferTest {
    @Test
    public void aCustomerCanMakeAnOffer() {

        // they are able to make an offer on a specific car
        Car c = new Car(3, 3);
        Offer o = new Offer(100, 10, 2000, c.getCid(), c.getUid());
        assertTrue(OfferService.createOffer(o));
        List<Offer> offers = OfferService.evaluateOffers();
        OfferService.removeOffer(offers.get(offers.size() - 1).getOid());
    }

    @Test
    public void anEmployeeCanGetAnOffer() {
        // they can get an offer
        Offer offer = OfferService.getOffer(1);
        assertNotNull(offer);
        assertTrue(offer.getUid() == 1);
        assertTrue(offer.getCid() == 2);
    }

    @Test
    public void anEmployeeCanEvaluateAnOffer() {
        // they can accept the offer
        Offer offer = OfferService.getOffer(1);
        offer.setStatus("accepted");
        assertTrue(OfferService.evaluateOffer(offer));
        assertTrue(OfferService.getOffer(1).getStatus().equals("accepted"));
        offer.setStatus("unevaluated");
        OfferService.evaluateOffer(offer);
    }

    @Test
    public void anEmployeeCanGetAllOffers() {
        // they can get all offers everywhere
        List<Offer> expectedOffers = new ArrayList<>();
        expectedOffers.add(new Offer(1, 100, 30, "unevaluated", 2000, 2, 1));
        expectedOffers.add(new Offer(2, 100, 40, "unevaluated", 2000, 2, 1));
        expectedOffers.add(new Offer(3, 100, 40, "unevaluated", 2000, 3, 1));
        expectedOffers.add(new Offer(4, 200, 30, "unevaluated", 2000, 2, 2));

        List<Offer> actualOffers = OfferService.evaluateOffers();
        assertTrue(actualOffers.size() == expectedOffers.size());
        for (int i = 0; i < expectedOffers.size(); i++) {
            assertTrue(actualOffers.get(i).equals(expectedOffers.get(i)));
        };
    }

    @Test
    public void anEmployeeCanGetAllOffersForACar() {
        List<Offer> expectedOffers = new ArrayList<>();
        expectedOffers.add(new Offer(1, 100, 30, "unevaluated", 2000, 2, 1));
        expectedOffers.add(new Offer(2, 100, 40, "unevaluated", 2000, 2, 1));
        expectedOffers.add(new Offer(4, 200, 30, "unevaluated", 2000, 2, 2));
        List<Offer> actualOffers = OfferService.evaluateOffersForACar(2);

        // assertTrue(actualOffers.size() == expectedOffers.size());
        for (int i = 0; i < expectedOffers.size(); i++) {
            assertTrue(actualOffers.get(i).equals(expectedOffers.get(i)));
        }
    }

    @Test
    public void anEmployeeCanDeleteAnOffer() {
        OfferService.createOffer(new Offer(100, 30, "unevaluated", 2000, 2, 1));

        assertTrue(OfferService.removeOffer(5));
    }
}
