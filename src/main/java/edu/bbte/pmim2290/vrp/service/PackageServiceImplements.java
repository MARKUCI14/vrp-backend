package edu.bbte.pmim2290.vrp.service;

import edu.bbte.pmim2290.vrp.dao.PackageDAO;
import edu.bbte.pmim2290.vrp.exception.DatabaseException;
import edu.bbte.pmim2290.vrp.exception.EntityNotFoundException;
import edu.bbte.pmim2290.vrp.model.Package;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PackageServiceImplements implements PackageService {
    @Autowired
    private final PackageDAO packageDAO;

    public PackageServiceImplements(PackageDAO packageDAO) {
        this.packageDAO = packageDAO;
    }

    @Override
    public Package createPackage(Package p) throws EntityNotFoundException, DatabaseException {
        return packageDAO.save(p);
    }

    @Override
    public Optional<Package> getPackageById(Long id) throws EntityNotFoundException, DatabaseException {
        return packageDAO.findById(id);
    }

    @Override
    public Package updatePackage(Package p) throws EntityNotFoundException, DatabaseException {
        return packageDAO.save(p);
    }

    @Override
    public void deletePackage(Long id) throws EntityNotFoundException, DatabaseException {
        packageDAO.deleteById(id);
    }

    @Override
    public List<Package> getAllPackages() throws DatabaseException {
        return packageDAO.findAll();
    }
}
