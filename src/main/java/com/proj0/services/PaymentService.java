package com.proj0.services;

import java.util.List;

import com.proj0.dao.PaymentDAO;
import com.proj0.models.Car;
import com.proj0.models.Payment;
import com.proj0.models.User;

public class PaymentService {
    private static PaymentDAO pdao = new PaymentDAO();

    public static boolean createPayment(Payment payment) {
        return pdao.createPayment(payment);
    }
    public static List<Payment> getPaymentsOnCarForUser(User user, Car car) {
        return pdao.getPaymentsOnCarForUser(user, car);
    }

    public static List<Payment> viewAllPayments() {
        return pdao.getPayments();
    }

    public static List<Payment> viewAllPaymentsForCar(int cid) {
        return pdao.getPaymentsForCar(cid);
    }

    public static double getRemainingPaymentsLumpSum(User user, Car car) {
        return pdao.getRemainingPaymentsLumpSum(user, car);
    }


}
