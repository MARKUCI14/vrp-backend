package edu.bbte.pmim2290.vrp.service;

import edu.bbte.pmim2290.vrp.exception.DatabaseException;
import edu.bbte.pmim2290.vrp.exception.EntityNotFoundException;
import edu.bbte.pmim2290.vrp.model.Package;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PackageService {
    Package createPackage(Package p) throws EntityNotFoundException, DatabaseException;

    Optional<Package> getPackageById(Long id) throws EntityNotFoundException, DatabaseException;

    Package updatePackage(Package p) throws EntityNotFoundException, DatabaseException;

    void deletePackage(Long id) throws EntityNotFoundException, DatabaseException;

    List<Package> getAllPackages() throws DatabaseException;

    List<Package> findByDepotId(Long depotId) throws DatabaseException;

    List<Package> findByDepotIdAndDeliveryDate(Long depotId, LocalDate deliveryDate) throws DatabaseException;
}
