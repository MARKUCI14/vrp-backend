package edu.bbte.pmim2290.vrp.dao.jpa;

import edu.bbte.pmim2290.vrp.dao.DeliveryDAO;
import edu.bbte.pmim2290.vrp.model.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaDeliveryDAO extends DeliveryDAO, JpaRepository<Delivery, Long> {
}
