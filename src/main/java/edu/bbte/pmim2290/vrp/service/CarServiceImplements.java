package edu.bbte.pmim2290.vrp.service;

import edu.bbte.pmim2290.vrp.dao.CarDAO;
import edu.bbte.pmim2290.vrp.exception.DatabaseException;
import edu.bbte.pmim2290.vrp.exception.EntityNotFoundException;
import edu.bbte.pmim2290.vrp.model.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarServiceImplements implements CarService {
    @Autowired
    private final CarDAO carDAO;

    public CarServiceImplements(CarDAO carDAO) {
        this.carDAO = carDAO;
    }

    @Override
    public Car createCar(Car car) throws EntityNotFoundException, DatabaseException {
        return carDAO.save(car);
    }

    @Override
    public Optional<Car> getCarById(Long id) throws EntityNotFoundException, DatabaseException {
        return carDAO.findById(id);
    }

    @Override
    public Car updateCar(Car car) throws EntityNotFoundException, DatabaseException {
        return carDAO.save(car);
    }

    @Override
    public void deleteCar(Long id) throws EntityNotFoundException, DatabaseException {
        carDAO.deleteById(id);
    }

    @Override
    public List<Car> getAllCars() throws DatabaseException {
        return carDAO.findAll();
    }

    @Override
    public List<Car> findByDepotId(Long depotId) throws DatabaseException {
        return carDAO.findByDepotId(depotId);
    }
}
