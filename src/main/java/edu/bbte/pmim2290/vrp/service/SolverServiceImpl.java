package edu.bbte.pmim2290.vrp.service;

import edu.bbte.pmim2290.vrp.dto.OutDeliveryDTO;
import edu.bbte.pmim2290.vrp.dto.VRPRequestDTO;
import edu.bbte.pmim2290.vrp.exception.DatabaseException;
import edu.bbte.pmim2290.vrp.exception.EntityNotFoundException;
import edu.bbte.pmim2290.vrp.mapper.DeliveryMapper;
import edu.bbte.pmim2290.vrp.model.Car;
import edu.bbte.pmim2290.vrp.model.Depot;
import edu.bbte.pmim2290.vrp.model.Listing;
import edu.bbte.pmim2290.vrp.model.Package;
import edu.bbte.pmim2290.vrp.utils.VRPSolverHelper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SolverServiceImpl implements SolverService {

    private final DepotService depotService;
    private final ListingService listingService;
    private final DeliveryService deliveryService;
    private final DeliveryMapper deliveryMapper;

    private final VRPSolverHelper vrpSolverHelper;

    public SolverServiceImpl(DepotService depotService,
                             ListingService listingService,
                             DeliveryService deliveryService,
                             DeliveryMapper deliveryMapper,
                             VRPSolverHelper vrpSolverHelper) {
        this.depotService = depotService;
        this.listingService = listingService;
        this.deliveryService = deliveryService;
        this.deliveryMapper = deliveryMapper;
        this.vrpSolverHelper = vrpSolverHelper;
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
                .orElseThrow(() -> new RuntimeException("Listing not found"));

        if (!listing.getDepot().getId().equals(depotId)) {
            throw new SecurityException("Access denied");
        }

        Depot depot = depotService.getDepotById(depotId).get();
        List<Car> cars = vrpSolverHelper.carService.findByDepotId(depotId);
        List<Package> packages = vrpSolverHelper.packageService
                .findByDepotIdAndDeliveryDate(depotId, listing.getDate());

        VRPRequestDTO request = vrpSolverHelper.buildRequest(depot, cars, packages);
        var solverResponse = vrpSolverHelper.callSolver(request);
        vrpSolverHelper.persistDeliveries(listing, solverResponse.getBestRoute());

        return vrpSolverHelper.getDeliveries(listingId, deliveryService, deliveryMapper);
    }
}
