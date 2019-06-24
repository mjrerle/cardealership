package com.proj0.dao;

import java.util.List;

import com.proj0.models.Car;
import com.proj0.models.User;

public interface ICar {
    public boolean removeCarFromLot(Car car);
    public boolean addCarToLot(Car car);
    public Car getCar(int cid);
    public boolean updateCar(Car car);
    public List<Car> getCarsOnLot();
    public List<Car> getOwnedCars(User user);

}
