package edu.bbte.pmim2290.vrp.service;

import edu.bbte.pmim2290.vrp.dao.UserDAO;
import edu.bbte.pmim2290.vrp.exception.DatabaseException;
import edu.bbte.pmim2290.vrp.exception.EntityNotFoundException;
import edu.bbte.pmim2290.vrp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplements implements UserService {
    @Autowired
    private final UserDAO userDAO;

    public UserServiceImplements(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public User createUser(User user) throws EntityNotFoundException, DatabaseException {
        return userDAO.save(user);
    }

    @Override
    public Optional<User> getUserById(Long id) throws EntityNotFoundException, DatabaseException {
        return userDAO.findById(id);
    }

    @Override
    public User updateUser(User user) throws EntityNotFoundException, DatabaseException {
        return userDAO.save(user);
    }

    @Override
    public void deleteUser(Long id) throws EntityNotFoundException, DatabaseException {
        userDAO.deleteById(id);
    }

    @Override
    public List<User> getAllUsers() throws DatabaseException {
        return userDAO.findAll();
    }
}
