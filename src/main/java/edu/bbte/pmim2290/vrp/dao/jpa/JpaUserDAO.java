package edu.bbte.pmim2290.vrp.dao.jpa;

import edu.bbte.pmim2290.vrp.dao.UserDAO;
import edu.bbte.pmim2290.vrp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaUserDAO extends UserDAO, JpaRepository<User, Long> {
}
