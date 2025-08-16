package edu.bbte.pmim2290.vrp.dao.jpa;

import edu.bbte.pmim2290.vrp.dao.PackageDAO;
import edu.bbte.pmim2290.vrp.model.Package;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaPackageDAO extends PackageDAO, JpaRepository<Package, Long> {
}
