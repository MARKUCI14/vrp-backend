package edu.bbte.pmim2290.vrp.dao;

import edu.bbte.pmim2290.vrp.model.Depot;

import java.util.List;

public interface DepotDAO extends BaseDAO<Depot> {
    List<Depot> findByUserId(Long id);
}
