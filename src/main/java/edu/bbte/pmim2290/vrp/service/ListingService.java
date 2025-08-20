package edu.bbte.pmim2290.vrp.service;

import edu.bbte.pmim2290.vrp.exception.DatabaseException;
import edu.bbte.pmim2290.vrp.exception.EntityNotFoundException;
import edu.bbte.pmim2290.vrp.model.Listing;

import java.util.List;
import java.util.Optional;

public interface ListingService {
    Listing createListing(Listing listing) throws EntityNotFoundException, DatabaseException;

    Optional<Listing> getListingById(Long id) throws EntityNotFoundException, DatabaseException;

    Listing updateListing(Listing listing) throws EntityNotFoundException, DatabaseException;

    void deleteListing(Long id) throws EntityNotFoundException, DatabaseException;

    List<Listing> getAllListings() throws DatabaseException;
}
