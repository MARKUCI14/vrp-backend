package edu.bbte.pmim2290.vrp.dao.jpa;

import edu.bbte.pmim2290.vrp.dao.ListingDAO;
import edu.bbte.pmim2290.vrp.model.Listing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaListingDAO extends ListingDAO, JpaRepository<Listing, Long> {
}
