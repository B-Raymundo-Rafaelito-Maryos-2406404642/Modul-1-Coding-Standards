package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;
import java.util.Iterator;
import java.util.List;

public interface CarRepositoryInterface {
    Car create(Car car);
    Iterator<Car> findAll();
    Car findById(String id);
    List<Car> findCarsByName(String carName);
    Car update(String id, Car updatedCar);
    void delete(String id);
}
