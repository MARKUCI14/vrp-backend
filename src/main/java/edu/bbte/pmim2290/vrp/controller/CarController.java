package edu.bbte.pmim2290.vrp.controller;

import edu.bbte.pmim2290.vrp.dto.InCarDTO;
import edu.bbte.pmim2290.vrp.dto.OutCarDTO;
import edu.bbte.pmim2290.vrp.exception.DatabaseException;
import edu.bbte.pmim2290.vrp.exception.EntityNotFoundException;
import edu.bbte.pmim2290.vrp.mapper.CarMapper;
import edu.bbte.pmim2290.vrp.model.Car;
import edu.bbte.pmim2290.vrp.service.CarService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/cars")
public class CarController {
    @Autowired
    private final CarService carService;

    @Autowired
    private CarMapper carMapper;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    public List<OutCarDTO> getCars()
            throws DatabaseException {
        List<Car> cars;
        cars = carService.getAllCars();
        return cars.stream()
                .map(carMapper::toOutCarDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public OutCarDTO getCar(@PathVariable Long id) throws EntityNotFoundException, DatabaseException {
        Optional<Car> car = carService.getCarById(id);
        return carMapper.toOutCarDTO(car.get());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<OutCarDTO> createCar(@Valid @RequestBody InCarDTO inCar)
            throws DatabaseException, EntityNotFoundException {
        Car car = carMapper.toCar(inCar);
        URI uri = URI.create("api/Cars/" + car.getId());
        return ResponseEntity.created(uri).body(carMapper.toOutCarDTO(carService.createCar(car)));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<OutCarDTO> updateCar(@PathVariable Long id, @Valid @RequestBody InCarDTO inCar)
            throws EntityNotFoundException, DatabaseException {
        Car car = carMapper.toCar(inCar);
        car.setId(id);
        return ResponseEntity.ok(carMapper.toOutCarDTO(carService.updateCar(car)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCar(@PathVariable Long id) throws EntityNotFoundException, DatabaseException {
        Optional<Car> car = carService.getCarById(id);
        if (car.isEmpty()) {
            throw new EntityNotFoundException("The requested Car does not exist");
        }

        carService.deleteCar(id);
    }
}
