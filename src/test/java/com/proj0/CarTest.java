package com.proj0;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import com.proj0.models.Car;
import com.proj0.models.User;
import com.proj0.services.CarService;
import com.proj0.services.UserService;

public class CarTest {
    @Test
    public void anEmployeeCanAddACarToTheLot() {
        // they can add a car to the lot
        Car c = new Car(7, "poseidon", 2015, 16000, 0, "lot");
        assertTrue(CarService.addCarToLot(c));
        CarService.removeCarFromLot(c);
    }

    @Test
    public void anEmployeeCanRemoveACarFromTheLot() {
        // they can remove a car from the lot
        Car c = new Car(7, "poseidon", 2015, 16000, 0, "lot");
        CarService.addCarToLot(c);
        assertTrue(CarService.removeCarFromLot(c));
    }

    @Test
    public void anEmployeeCanGetACar() {
        // they can get a specific car
        Car car = CarService.getCar(1);
        assertNotNull(car);
        assertTrue(car.getCid() == 1);
        assertTrue(car.getUid() == 0);
        assertTrue(car.getLocation().equals("lot"));
    }

    @Test
    public void anEmployeeCanModifyACar() {
        Car car = CarService.getCar(1);
        double originalPrice = car.getPrice();
        car.setPrice(10000);
        assertTrue(CarService.updateCar(car));
        assertTrue(CarService.getCar(1).getPrice() == 10000);
        car.setPrice(originalPrice);
        CarService.updateCar(car);
    }

    @Test
    public void aCustomerCanViewCarsOnTheLot() {
        User u = new User("customer", "password");
        u = UserService.login(u.getUsername(), u.getPassword());
        List<Car> actualCars = CarService.getCarsOnLot();
        List<Car> expectedCars = new ArrayList<>();
        expectedCars.add(new Car(1, "hephaestus", 2011, 7500, 0, "lot"));
        expectedCars.add(new Car(2, "aphrodite", 2003, 3200, 0, "lot"));

        expectedCars.add(new Car(4, "penelope", 2004, 3500, 0, "lot"));

        assertTrue(actualCars.size() == 3);
        assertTrue(actualCars.get(0).getCid() == expectedCars.get(0).getCid());
        assertTrue(actualCars.get(1).getCid() == expectedCars.get(1).getCid());
        assertTrue(actualCars.get(2).getCid() == expectedCars.get(2).getCid());
    }

    @Test
    public void aCustomerCanViewOwnedCars() {
        //given a customer
        User u = new User("customer", "password");
        u = UserService.login(u.getUsername(), u.getPassword());
        //they can view cars that belong to them
        List<Car> expectedCars = new ArrayList<>();
        expectedCars.add(new Car(3, "hades", 1999, 1300, u.getUid(), "garage"));
        List<Car> actualCars = CarService.getOwnedCars(u);
        assertTrue(actualCars.size() == 1);
        assertTrue(actualCars.get(0).getCid() == expectedCars.get(0).getCid());
    }
}
