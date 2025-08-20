package edu.bbte.pmim2290.vrp.service;

import edu.bbte.pmim2290.vrp.exception.DatabaseException;
import edu.bbte.pmim2290.vrp.exception.EntityNotFoundException;
import edu.bbte.pmim2290.vrp.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User createUser(User user) throws EntityNotFoundException, DatabaseException;

    Optional<User> getUserById(Long id) throws EntityNotFoundException, DatabaseException;

    User updateUser(User user) throws EntityNotFoundException, DatabaseException;

    void deleteUser(Long id) throws EntityNotFoundException, DatabaseException;

    List<User> getAllUsers() throws DatabaseException;
}
