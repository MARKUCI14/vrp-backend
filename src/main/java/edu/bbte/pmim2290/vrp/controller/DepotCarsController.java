package edu.bbte.pmim2290.vrp.controller;

import edu.bbte.pmim2290.vrp.config.UserDetailsImpl;
import edu.bbte.pmim2290.vrp.dto.InCarDTO;
import edu.bbte.pmim2290.vrp.dto.OutCarDTO;
import edu.bbte.pmim2290.vrp.exception.DatabaseException;
import edu.bbte.pmim2290.vrp.exception.EntityNotFoundException;
import edu.bbte.pmim2290.vrp.mapper.CarMapper;
import edu.bbte.pmim2290.vrp.model.Car;
import edu.bbte.pmim2290.vrp.model.Depot;
import edu.bbte.pmim2290.vrp.model.User;
import edu.bbte.pmim2290.vrp.service.CarService;
import edu.bbte.pmim2290.vrp.service.DepotService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/depots/{depotId}/cars")
public class DepotCarsController {
    private static final String DEPOT_NOT_FOUND = "Depot not found";
    private static final String CAR_NOT_FOUND = "Car not found";
    private static final String ACCESS_DENIED = "Access denied";

    private final DepotService depotService;
    private final CarService carService;

    @Autowired
    private CarMapper carMapper;

    public DepotCarsController(DepotService depotService, CarService carService) {
        this.depotService = depotService;
        this.carService = carService;
    }

    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        return userDetails.getUser();
    }

    public boolean validateDepotId(@PathVariable Long depotId) throws EntityNotFoundException, DatabaseException {
        Depot depot = depotService.getDepotById(depotId)
                .orElseThrow(() -> new EntityNotFoundException(DEPOT_NOT_FOUND));
        User user = getCurrentUser();

        return depot.getUser().getId().equals(user.getId());
    }

    @GetMapping
    public List<OutCarDTO> getDepotsCars(@PathVariable Long depotId) throws DatabaseException, EntityNotFoundException {
        if (!validateDepotId(depotId)) {
            throw new SecurityException(ACCESS_DENIED);
        }

        List<Car> cars;
        cars = carService.findByDepotId(depotId);

        return cars.stream()
                .map(carMapper::toOutCarDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public OutCarDTO getDepotsCar(@PathVariable Long depotId, @PathVariable Long id)
            throws DatabaseException, EntityNotFoundException {
        if (!validateDepotId(depotId)) {
            throw new SecurityException(ACCESS_DENIED);
        }

        Car car = carService.getCarById(id)
                .orElseThrow(() -> new EntityNotFoundException(CAR_NOT_FOUND));

        if (!car.getDepot().getId().equals(depotId)) {
            throw new SecurityException(ACCESS_DENIED);
        }

        return carMapper.toOutCarDTO(car);
    }

    @PostMapping
    public ResponseEntity<OutCarDTO> addCarToDepot(@PathVariable Long depotId, @Valid @RequestBody InCarDTO inCarDTO)
            throws DatabaseException, EntityNotFoundException {
        if (!validateDepotId(depotId)) {
            throw new SecurityException(ACCESS_DENIED);
        }

        Depot depot = depotService.getDepotById(depotId)
                .orElseThrow(() -> new EntityNotFoundException(DEPOT_NOT_FOUND));

        Car car = carMapper.toCar(inCarDTO);
        car.setDepot(depot);

        Car savedCar = carService.createCar(car);
        URI uri = URI.create("/api/depots/" + depotId + "/cars/" + savedCar.getId());
        return ResponseEntity.created(uri).body(carMapper.toOutCarDTO(savedCar));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OutCarDTO> updateDepotsCar(@PathVariable Long depotId,
            @PathVariable Long id, @Valid @RequestBody InCarDTO inCarDTO)
            throws DatabaseException, EntityNotFoundException {
        if (!validateDepotId(depotId)) {
            throw new SecurityException(ACCESS_DENIED);
        }
        Depot depot = depotService.getDepotById(depotId)
                .orElseThrow(() -> new EntityNotFoundException(DEPOT_NOT_FOUND));

        Car car = carService.getCarById(id)
                .orElseThrow(() -> new EntityNotFoundException(CAR_NOT_FOUND));

        carMapper.updateFromDTO(inCarDTO, car);
        car.setDepot(depot);

        Car savedCar = carService.createCar(car);
        return ResponseEntity.ok(carMapper.toOutCarDTO(savedCar));
    }

    @DeleteMapping("/{id}")
    public void deleteDepotsCar(@PathVariable Long depotId, @PathVariable Long id)
            throws EntityNotFoundException, DatabaseException {
        Optional<Depot> depotOptional = depotService.getDepotById(depotId);
        if (!depotOptional.isPresent()) {
            throw new EntityNotFoundException(DEPOT_NOT_FOUND);
        }

        if (!validateDepotId(depotId)) {
            throw new SecurityException(ACCESS_DENIED);
        }

        Optional<Car> car = carService.getCarById(id);
        if (!car.isPresent()) {
            throw new EntityNotFoundException(CAR_NOT_FOUND);
        }

        if (!car.get().getDepot().getId().equals(depotId)) {
            throw new EntityNotFoundException(CAR_NOT_FOUND);
        }

        carService.deleteCar(id);
    }
}
