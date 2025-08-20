package edu.bbte.pmim2290.vrp.service;

import edu.bbte.pmim2290.vrp.exception.DatabaseException;
import edu.bbte.pmim2290.vrp.exception.EntityNotFoundException;
import edu.bbte.pmim2290.vrp.model.Car;

import java.util.List;
import java.util.Optional;

public interface CarService {
    Car createCar(Car car) throws EntityNotFoundException, DatabaseException;

    Optional<Car> getCarById(Long id) throws EntityNotFoundException, DatabaseException;

    Car updateCar(Car car) throws EntityNotFoundException, DatabaseException;

    void deleteCar(Long id) throws EntityNotFoundException, DatabaseException;

    List<Car> getAllCars() throws DatabaseException;
}
