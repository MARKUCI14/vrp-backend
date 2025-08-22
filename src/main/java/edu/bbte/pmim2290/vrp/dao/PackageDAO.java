package edu.bbte.pmim2290.vrp.dao;

import edu.bbte.pmim2290.vrp.model.Package;

import java.util.List;

public interface PackageDAO extends BaseDAO<Package> {
    List<Package> findByDepotId(Long depotId);
}
