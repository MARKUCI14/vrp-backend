package edu.bbte.pmim2290.vrp.utils;

import edu.bbte.pmim2290.vrp.dto.OutDeliveryDTO;
import edu.bbte.pmim2290.vrp.dto.VRPRequestDTO;
import edu.bbte.pmim2290.vrp.dto.SolverResponse;
import edu.bbte.pmim2290.vrp.exception.DatabaseException;
import edu.bbte.pmim2290.vrp.exception.EntityNotFoundException;
import edu.bbte.pmim2290.vrp.mapper.CarMapper;
import edu.bbte.pmim2290.vrp.mapper.DeliveryMapper;
import edu.bbte.pmim2290.vrp.mapper.PackageMapper;
import edu.bbte.pmim2290.vrp.mapper.DepotMapper;
import edu.bbte.pmim2290.vrp.model.Car;
import edu.bbte.pmim2290.vrp.model.Listing;
import edu.bbte.pmim2290.vrp.model.Package;
import edu.bbte.pmim2290.vrp.model.Depot;
import edu.bbte.pmim2290.vrp.model.Delivery;
import edu.bbte.pmim2290.vrp.service.CarService;
import edu.bbte.pmim2290.vrp.service.DeliveryService;
import edu.bbte.pmim2290.vrp.service.PackageService;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.Long.parseLong;

@Component
public class VRPSolverHelper {
    public final CarService carService;
    public final PackageService packageService;
    private final DeliveryService deliveryService;

    private final DepotMapper depotMapper;
    private final CarMapper carMapper;
    private final PackageMapper packageMapper;

    public VRPSolverHelper(CarService carService,
                           PackageService packageService,
                           DeliveryService deliveryService,
                           DepotMapper depotMapper,
                           CarMapper carMapper,
                           PackageMapper packageMapper) {
        this.carService = carService;
        this.packageService = packageService;
        this.deliveryService = deliveryService;
        this.depotMapper = depotMapper;
        this.carMapper = carMapper;
        this.packageMapper = packageMapper;
    }

    public VRPRequestDTO buildRequest(Depot depot, List<Car> cars, List<Package> packages) {
        return new VRPRequestDTO(
                depotMapper.toOutDepotDTO(depot),
                packages.stream().map(packageMapper::toOutPackageDTO).collect(Collectors.toList()),
                cars.stream().map(carMapper::toOutCarDTO).collect(Collectors.toList())
        );
    }

    public SolverResponse callSolver(VRPRequestDTO request) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject("http://vrp-solver:5001/api/solver", request, SolverResponse.class);
    }

    public void persistDeliveries(Listing listing, Map<String, List<Long>> bestRoute)
            throws EntityNotFoundException, DatabaseException {
        for (Map.Entry<String, List<Long>> entry : bestRoute.entrySet()) {
            Long carId = parseLong(entry.getKey());
            Car car = carService.getCarById(carId)
                    .orElseThrow(() -> new EntityNotFoundException("Car not found with id: " + carId));

            int order = 0;
            for (Long packageId : entry.getValue()) {
                Delivery delivery = new Delivery();
                delivery.setListing(listing);
                delivery.setPkg(packageService.getPackageById(packageId)
                        .orElseThrow(() -> new EntityNotFoundException("Package not found with id: " + packageId)));
                delivery.setCar(car);
                delivery.setDeliveryOrder(order++);
                deliveryService.createDelivery(delivery);
            }
        }
    }

    public List<OutDeliveryDTO> getDeliveries(Long listingId, DeliveryService deliveryService,
                                              DeliveryMapper deliveryMapper)
            throws DatabaseException {
        return deliveryService.findByListingId(listingId)
                .stream().map(deliveryMapper::toOutDeliveryDTO)
                .collect(Collectors.toList());
    }
}
