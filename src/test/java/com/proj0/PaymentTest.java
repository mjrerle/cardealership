package com.proj0;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import com.proj0.models.Car;
import com.proj0.models.Payment;
import com.proj0.models.User;
import com.proj0.services.PaymentService;
import com.proj0.services.UserService;

public class PaymentTest {
    @Test
    public void anEmployeeCanViewAllPayments() {
        List<Payment> expectedPayments = new ArrayList<>();
        expectedPayments.add(new Payment(1, 300, 11, 3, 2));
        expectedPayments.add(new Payment(2, 300, 10, 3, 2));
        expectedPayments.add(new Payment(3, 300, 9, 3, 2));
        expectedPayments.add(new Payment(4, 300, 32, 5, 3));
        expectedPayments.add(new Payment(5, 300, 31, 5, 3));

        List<Payment> actualPayments = PaymentService.viewAllPayments();
        assertTrue(actualPayments.size() == expectedPayments.size());
        for (int i = 0; i < actualPayments.size(); i++) {
            assertTrue(expectedPayments.get(i).equals(actualPayments.get(i)));
        }
    }

    @Test
    public void anEmployeeCanViewAllPaymentsMadeOnACar() {
        List<Payment> expectedPayments = new ArrayList<>();
        expectedPayments.add(new Payment(1, 300, 11, 3, 2));
        expectedPayments.add(new Payment(2, 300, 10, 3, 2));
        expectedPayments.add(new Payment(3, 300, 9, 3, 2));
        List<Payment> actualPayments = PaymentService.viewAllPaymentsForCar(3);
        // assertNull(actualPayments);
        assertTrue(actualPayments.size() == expectedPayments.size());
        for (int i = 0; i < actualPayments.size(); i++) {
            assertTrue(expectedPayments.get(i).equals(actualPayments.get(i)));
        }
    }

    @Test
    public void aCustomerCanViewTheirPaymentsMadeOnACar() {
        //given a customer
        User u = UserService.getUser("another_customer");
        //they can view remaining payments on the car that they own
        List<Payment> expectedPayments = new ArrayList<>();
        expectedPayments.add(new Payment(4, 300, 32, 5, 3));
        expectedPayments.add(new Payment(5, 300, 31, 5, 3));
        Car c = new Car(5);
        List<Payment> actualPayments = PaymentService.getPaymentsOnCarForUser(u, c);
        assertTrue(actualPayments.size() == expectedPayments.size());
        assertTrue(actualPayments.get(0).getPid() == expectedPayments.get(0).getPid());
        assertTrue(actualPayments.get(1).getPid() == expectedPayments.get(1).getPid());
    }

    @Test
    public void aCustomerCanViewRemainingPaymentsOnACar() {
        User u = UserService.getUser("another_customer");
        double expectedAmount = 9300;
        double actualAmount = PaymentService.getRemainingPaymentsLumpSum(u, new Car(5));
        assertTrue(expectedAmount == actualAmount);
    }

    @Test
    public void aCustomerCanMakeAPayment() {
        User u = UserService.getUser("customer");
        assertTrue(PaymentService.createPayment(new Payment(300, 30, 5, u.getUid())));
    }
}
