package com.proj0.services;

import java.util.List;

import com.proj0.dao.CarDAO;
import com.proj0.models.Car;
import com.proj0.models.User;

public class CarService {
    private static CarDAO cdao = new CarDAO();
    public static List<Car> getCarsOnLot() {
        return cdao.getCarsOnLot();
    }

    public static List<Car> getOwnedCars(User user) {
        return cdao.getOwnedCars(user);
    }

    public static boolean removeCarFromLot(Car car) {
        return cdao.removeCarFromLot(car);
    }

    public static boolean addCarToLot(Car car) {
        return cdao.addCarToLot(car);
    }

    public static Car getCar(int cid) {
        return cdao.getCar(cid);
    }

    public static boolean updateCar(Car car) {
        return cdao.updateCar(car);
    }
}
