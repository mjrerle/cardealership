package com.proj0.dao;

import java.util.List;

import com.proj0.models.Car;
import com.proj0.models.Payment;
import com.proj0.models.User;

public interface IPayment {

    public List<Payment> getPayments();
    public List<Payment> getPaymentsForCar(int cid);
    public boolean createPayment(Payment payment);
    public List<Payment> getPaymentsOnCarForUser(User user, Car car);
    public double getRemainingPaymentsLumpSum(User user, Car car);
}
