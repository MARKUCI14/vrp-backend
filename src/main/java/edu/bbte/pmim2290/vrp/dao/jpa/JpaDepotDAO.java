package edu.bbte.pmim2290.vrp.dao.jpa;

import edu.bbte.pmim2290.vrp.dao.DepotDAO;
import edu.bbte.pmim2290.vrp.model.Depot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaDepotDAO extends DepotDAO, JpaRepository<Depot, Long> {
}
