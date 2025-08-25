package edu.bbte.pmim2290.vrp.dao;

import edu.bbte.pmim2290.vrp.model.Delivery;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DeliveryDAO extends BaseDAO<Delivery> {
    List<Delivery> findByListingId(Long listingId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Delivery d WHERE d.listing.id = :listingId")
    void deleteByListingId(Long listingId);
}
