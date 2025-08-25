package edu.bbte.pmim2290.vrp.service;

import edu.bbte.pmim2290.vrp.dto.OutDeliveryDTO;
import edu.bbte.pmim2290.vrp.dto.SolverResponse;
import edu.bbte.pmim2290.vrp.dto.VRPRequestDTO;
import edu.bbte.pmim2290.vrp.exception.DatabaseException;
import edu.bbte.pmim2290.vrp.exception.EntityNotFoundException;
import edu.bbte.pmim2290.vrp.mapper.CarMapper;
import edu.bbte.pmim2290.vrp.mapper.DeliveryMapper;
import edu.bbte.pmim2290.vrp.mapper.DepotMapper;
import edu.bbte.pmim2290.vrp.mapper.PackageMapper;
import edu.bbte.pmim2290.vrp.model.Car;
import edu.bbte.pmim2290.vrp.model.Delivery;
import edu.bbte.pmim2290.vrp.model.Depot;
import edu.bbte.pmim2290.vrp.model.Listing;
import edu.bbte.pmim2290.vrp.model.Package;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.Long.parseLong;

@Service
@SuppressWarnings("PMD.CouplingBetweenObjects")
public class SolverServiceImpl implements SolverService {
    private final DepotService depotService;
    private final CarService carService;
    private final PackageService packageService;
    private final DeliveryService deliveryService;
    private final ListingService listingService;

    private final DepotMapper depotMapper;
    private final CarMapper carMapper;
    private final PackageMapper packageMapper;
    private final DeliveryMapper deliveryMapper;

    public SolverServiceImpl(DepotService depotService,
                             CarService carService,
                             PackageService packageService,
                             DeliveryService deliveryService,
                             ListingService listingService,
                             DepotMapper depotMapper,
                             CarMapper carMapper,
                             PackageMapper packageMapper,
                             DeliveryMapper deliveryMapper) {
        this.depotService = depotService;
        this.carService = carService;
        this.packageService = packageService;
        this.deliveryService = deliveryService;
        this.listingService = listingService;
        this.depotMapper = depotMapper;
        this.carMapper = carMapper;
        this.packageMapper = packageMapper;
        this.deliveryMapper = deliveryMapper;
    }

    @Override
    public List<OutDeliveryDTO> resolve(Long depotId, Long listingId)
            throws EntityNotFoundException, DatabaseException {
        deliveryService.deleteByListingId(listingId);

        return solveAndPersistListing(depotId, listingId);
    }

    @Override
    public List<OutDeliveryDTO> solveAndPersistListing(Long depotId, Long listingId)
            throws EntityNotFoundException, DatabaseException {

        Listing listing = listingService.getListingById(listingId)
                .orElseThrow(() -> new EntityNotFoundException("Listing not found"));

        if (!listing.getDepot().getId().equals(depotId)) {
            throw new SecurityException("Access denied");
        }

        Depot depot = depotService.getDepotById(depotId).get();
        List<Car> cars = carService.findByDepotId(depotId);
        List<Package> packages = packageService.findByDepotIdAndDeliveryDate(depotId, listing.getDate());

        VRPRequestDTO request = new VRPRequestDTO(
                depotMapper.toOutDepotDTO(depot),
                packages.stream().map(packageMapper::toOutPackageDTO).collect(Collectors.toList()),
                cars.stream().map(carMapper::toOutCarDTO).collect(Collectors.toList())
        );

        // Call solver
        RestTemplate restTemplate = new RestTemplate();
        SolverResponse solverResponse = restTemplate.postForObject(
                "http://vrp-solver:5001/api/solver", request, SolverResponse.class);

        Map<String, List<Long>> bestRoute = solverResponse.getBestRoute();

        for (Map.Entry<String, List<Long>> entry : bestRoute.entrySet()) {
            Long carId = parseLong(entry.getKey());
            Car car = carService.getCarById(carId).get();

            int order = 0;
            for (Long packageId : entry.getValue()) {
                Delivery newDelivery = new Delivery();
                newDelivery.setListing(listing);
                newDelivery.setPkg(packageService.getPackageById(packageId).get());
                newDelivery.setCar(car);
                newDelivery.setDeliveryOrder(order++);
                deliveryService.createDelivery(newDelivery);
            }
        }

        return deliveryService.findByListingId(listingId)
                .stream().map(deliveryMapper::toOutDeliveryDTO)
                .collect(Collectors.toList());
    }
}
