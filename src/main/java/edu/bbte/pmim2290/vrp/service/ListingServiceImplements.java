package edu.bbte.pmim2290.vrp.service;

import edu.bbte.pmim2290.vrp.dao.ListingDAO;
import edu.bbte.pmim2290.vrp.exception.DatabaseException;
import edu.bbte.pmim2290.vrp.exception.EntityNotFoundException;
import edu.bbte.pmim2290.vrp.model.Listing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ListingServiceImplements implements ListingService {
    @Autowired
    private final ListingDAO listingDAO;

    public ListingServiceImplements(ListingDAO listingDAO) {
        this.listingDAO = listingDAO;
    }

    @Override
    public Listing createListing(Listing listing) throws EntityNotFoundException, DatabaseException {
        return listingDAO.save(listing);
    }

    @Override
    public Optional<Listing> getListingById(Long id) throws EntityNotFoundException, DatabaseException {
        return listingDAO.findById(id);
    }

    @Override
    public Listing updateListing(Listing listing) throws EntityNotFoundException, DatabaseException {
        return listingDAO.save(listing);
    }

    @Override
    public void deleteListing(Long id) throws EntityNotFoundException, DatabaseException {
        listingDAO.deleteById(id);
    }

    @Override
    public List<Listing> getAllListings() throws DatabaseException {
        return listingDAO.findAll();
    }

    @Override
    public List<Listing> findByDepotId(Long depotId) throws DatabaseException {
        return listingDAO.findByDepotId(depotId);
    }
}
