package edu.bbte.pmim2290.vrp.dao.jpa;

import edu.bbte.pmim2290.vrp.dao.CarDAO;
import edu.bbte.pmim2290.vrp.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaCarDAO extends CarDAO, JpaRepository<Car, Long> {
}
