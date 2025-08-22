package edu.bbte.pmim2290.vrp.dao;

import edu.bbte.pmim2290.vrp.model.User;

import java.util.Optional;

public interface UserDAO extends BaseDAO<User> {
    Optional<User> findByUsername(String username);
}
