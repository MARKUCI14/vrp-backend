package edu.bbte.pmim2290.vrp.controller;

import edu.bbte.pmim2290.vrp.config.UserDetailsImpl;
import edu.bbte.pmim2290.vrp.dto.InListingDTO;
import edu.bbte.pmim2290.vrp.dto.OutDeliveryDTO;
import edu.bbte.pmim2290.vrp.dto.OutListingDTO;
import edu.bbte.pmim2290.vrp.exception.DatabaseException;
import edu.bbte.pmim2290.vrp.exception.EntityNotFoundException;
import edu.bbte.pmim2290.vrp.mapper.*;
import edu.bbte.pmim2290.vrp.model.*;
import edu.bbte.pmim2290.vrp.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("api/depots/{depotId}/listings")
public class DepotListingController {
    private final DepotService depotService;
    private final ListingService listingService;
    private final DeliveryService deliveryService;
    @Autowired
    private ListingMapper listingMapper;
    @Autowired
    private SolverService solverService;
    @Autowired
    private DeliveryMapper deliveryMapper;

    public DepotListingController(DepotService depotService,
                                  ListingService listingService,
                                  DeliveryService deliveryService) {
        this.depotService = depotService;
        this.listingService = listingService;
        this.deliveryService = deliveryService;
    }

    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        return userDetails.getUser();
    }

    public boolean validateDepotId(@PathVariable Long depotId)
            throws EntityNotFoundException, DatabaseException {
        Depot depot = depotService.getDepotById(depotId)
                .orElseThrow(() -> new EntityNotFoundException("Depot not found"));
        User user = getCurrentUser();

        return depot.getUser().getId().equals(user.getId());
    }

    @GetMapping
    public List<OutListingDTO> getDepotsListings(@PathVariable Long depotId)
            throws DatabaseException, EntityNotFoundException {
        if (!validateDepotId(depotId)) {
            throw new SecurityException("Access denied");
        }

        List<Listing> listings;
        listings = listingService.findByDepotId(depotId);

        return listings.stream()
                .map(listingMapper::toOutListingDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public List<OutDeliveryDTO> getDepotsListing(@PathVariable Long depotId, @PathVariable Long id)
            throws EntityNotFoundException, DatabaseException {
        if (!validateDepotId(depotId)) {
            throw new SecurityException("Access denied");
        }

        Listing listing = listingService.getListingById(id)
                .orElseThrow(() -> new EntityNotFoundException("Listing not found"));

        if (!listing.getDepot().getId().equals(depotId)) {
            throw new SecurityException("Access denied");
        }

        return deliveryService.findByListingId(id)
                .stream().map(deliveryMapper::toOutDeliveryDTO)
                .collect(Collectors.toList());
    }

    @PostMapping
    public List<OutDeliveryDTO> createListing(@PathVariable Long depotId, @Valid @RequestBody InListingDTO inListingDTO)
            throws EntityNotFoundException, DatabaseException {
        if (!validateDepotId(depotId)) {
            throw new SecurityException("Access denied");
        }

        Depot depot = depotService.getDepotById(depotId)
                .orElseThrow(() -> new EntityNotFoundException("Depot not found"));

        Listing listing = listingMapper.toListing(inListingDTO);
        listing.setDepot(depot);

        Listing savedListing = listingService.createListing(listing);

        return solverService.solveAndPersistListing(depotId, savedListing.getId());
    }

    @PutMapping("/{id}")
    public List<OutDeliveryDTO> updateListing(@PathVariable Long depotId, @PathVariable Long id)
            throws EntityNotFoundException, DatabaseException {
        if (!validateDepotId(depotId)) {
            throw new SecurityException("Access denied");
        }

        Listing listing = listingService.getListingById(id)
                .orElseThrow(() -> new EntityNotFoundException("Listing not found"));

        if (!listing.getDepot().getId().equals(depotId)) {
            throw new SecurityException("Access denied: Listing does not belong to this depot");
        }

        return solverService.resolve(depotId, listing.getId());
    }
}
