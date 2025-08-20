package edu.bbte.pmim2290.vrp.controller;

import edu.bbte.pmim2290.vrp.dto.InListingDTO;
import edu.bbte.pmim2290.vrp.dto.OutListingDTO;
import edu.bbte.pmim2290.vrp.exception.DatabaseException;
import edu.bbte.pmim2290.vrp.exception.EntityNotFoundException;
import edu.bbte.pmim2290.vrp.mapper.ListingMapper;
import edu.bbte.pmim2290.vrp.model.Listing;
import edu.bbte.pmim2290.vrp.service.ListingService;
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
@RequestMapping("/api/listings")
public class ListingController {
    @Autowired
    private final ListingService listingService;

    @Autowired
    private ListingMapper listingMapper;

    public ListingController(ListingService listingService) {
        this.listingService = listingService;
    }

    @GetMapping
    public List<OutListingDTO> getListings()
            throws DatabaseException {
        List<Listing> listings;
        listings = listingService.getAllListings();


        return listings.stream()
                .map(listingMapper::toOutListingDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public OutListingDTO getListing(@PathVariable Long id) throws EntityNotFoundException, DatabaseException {
        Optional<Listing> listing = listingService.getListingById(id);
        return listingMapper.toOutListingDTO(listing.get());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<OutListingDTO> createListing(@Valid @RequestBody InListingDTO inListing)
            throws DatabaseException, EntityNotFoundException {
        Listing listing = listingMapper.toListing(inListing);
        URI uri = URI.create("api/Listings/" + listing.getId());
        return ResponseEntity.created(uri).body(listingMapper.toOutListingDTO(listingService.createListing(listing)));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<OutListingDTO> updateListing(@PathVariable Long id,
                                                       @Valid @RequestBody InListingDTO inListing)
            throws EntityNotFoundException, DatabaseException {
        Listing listing = listingMapper.toListing(inListing);
        listing.setId(id);
        return ResponseEntity.ok(listingMapper.toOutListingDTO(listingService.updateListing(listing)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteListing(@PathVariable Long id) throws EntityNotFoundException, DatabaseException {
        Optional<Listing> listing = listingService.getListingById(id);
        if (listing.isEmpty()) {
            throw new EntityNotFoundException("The requested Listing does not exist");
        }

        listingService.deleteListing(id);
    }
}
