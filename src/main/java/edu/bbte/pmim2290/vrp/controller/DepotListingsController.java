package edu.bbte.pmim2290.vrp.controller;

import edu.bbte.pmim2290.vrp.config.UserDetailsImpl;
import edu.bbte.pmim2290.vrp.dto.InListingDTO;
import edu.bbte.pmim2290.vrp.dto.OutListingDTO;
import edu.bbte.pmim2290.vrp.exception.DatabaseException;
import edu.bbte.pmim2290.vrp.exception.EntityNotFoundException;
import edu.bbte.pmim2290.vrp.mapper.ListingMapper;
import edu.bbte.pmim2290.vrp.model.Listing;
import edu.bbte.pmim2290.vrp.model.Depot;
import edu.bbte.pmim2290.vrp.model.User;
import edu.bbte.pmim2290.vrp.service.ListingService;
import edu.bbte.pmim2290.vrp.service.DepotService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("/api/depots/{depotId}/listings")
public class DepotListingsController {
    private final DepotService depotService;
    private final ListingService listingService;
    private static final Logger logger = LoggerFactory.getLogger(DepotListingsController.class);

    @Autowired
    private ListingMapper listingMapper;

    public DepotListingsController(DepotService depotService, ListingService listingService) {
        this.depotService = depotService;
        this.listingService = listingService;
    }

    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        return userDetails.getUser();
    }

    public boolean validateDepotId(@PathVariable Long depotId) throws EntityNotFoundException, DatabaseException {
        Depot depot = depotService.getDepotById(depotId)
                .orElseThrow(() -> new EntityNotFoundException("Depot not found"));
        User user = getCurrentUser();
        logger.info("Validating depot {}", depot.getId());
        logger.info("Validating depot userId {}", depot.getUser().getId());
        logger.info("Validating user {}", user.getId());
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
    public OutListingDTO getDepotsListing(@PathVariable Long depotId, @PathVariable Long id)
            throws DatabaseException, EntityNotFoundException {

        if (!validateDepotId(depotId)) {
            throw new SecurityException("Access denied");
        }

        Listing listing = listingService.getListingById(id)
                .orElseThrow(() -> new EntityNotFoundException("Listing not found"));

        if (!listing.getDepot().getId().equals(depotId)) {
            throw new SecurityException("Access denied: Listing does not belong to this depot");
        }

        return listingMapper.toOutListingDTO(listing);
    }

    @PostMapping
    public ResponseEntity<OutListingDTO> addListingToDepot(@PathVariable Long depotId,
                                                           @Valid @RequestBody InListingDTO inListingDTO)
            throws DatabaseException, EntityNotFoundException {
        if (!validateDepotId(depotId)) {
            throw new SecurityException("Access denied");
        }

        Depot depot = depotService.getDepotById(depotId)
                .orElseThrow(() -> new EntityNotFoundException("Depot not found"));

        Listing listing = listingMapper.toListing(inListingDTO);
        listing.setDepot(depot);

        Listing savedListing = listingService.createListing(listing);
        URI uri = URI.create("/api/depots/" + depotId + "/listings/" + savedListing.getId());
        return ResponseEntity.created(uri).body(listingMapper.toOutListingDTO(savedListing));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OutListingDTO> updateDepotsListing(@PathVariable Long depotId,
                                                     @PathVariable Long id,
                                                             @Valid @RequestBody InListingDTO inListingDTO)
            throws DatabaseException, EntityNotFoundException {
        if (!validateDepotId(depotId)) {
            throw new SecurityException("Access denied");
        }
        Depot depot = depotService.getDepotById(depotId)
                .orElseThrow(() -> new EntityNotFoundException("Depot not found"));

        Listing listing = listingService.getListingById(id)
                .orElseThrow(() -> new EntityNotFoundException("Listing not found"));

        listingMapper.updateFromDTO(inListingDTO, listing);
        listing.setDepot(depot);

        Listing savedListing = listingService.createListing(listing);
        return ResponseEntity.ok(listingMapper.toOutListingDTO(savedListing));
    }

    @DeleteMapping("/{id}")
    public void deleteDepotsListing(@PathVariable Long depotId, @PathVariable Long id)
            throws EntityNotFoundException, DatabaseException {
        Optional<Depot> depotOptional = depotService.getDepotById(depotId);
        if (!depotOptional.isPresent()) {
            throw new EntityNotFoundException("Depot not found");
        }

        if (!validateDepotId(depotId)) {
            throw new SecurityException("Access denied");
        }

        Optional<Listing> listing = listingService.getListingById(id);
        if (!listing.isPresent()) {
            throw new EntityNotFoundException("Listing not found");
        }

        if (!listing.get().getDepot().getId().equals(depotId)) {
            throw new EntityNotFoundException("Listing does not belong to depot");
        }

        listingService.deleteListing(id);
    }
}
