package edu.bbte.pmim2290.vrp.dao;

import edu.bbte.pmim2290.vrp.model.Car;

import java.util.List;

public interface CarDAO extends BaseDAO<Car> {
    List<Car> findByDepotId(Long depotId);
}
