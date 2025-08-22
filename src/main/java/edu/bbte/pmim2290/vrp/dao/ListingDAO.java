package edu.bbte.pmim2290.vrp.dao;

import edu.bbte.pmim2290.vrp.model.Listing;

import java.util.List;

public interface ListingDAO extends BaseDAO<Listing> {
    List<Listing> findByDepotId(Long depotId);
}
