package edu.bbte.pmim2290.vrp.service;

import edu.bbte.pmim2290.vrp.dto.InUserDTO;
import edu.bbte.pmim2290.vrp.exception.DatabaseException;
import edu.bbte.pmim2290.vrp.exception.EntityNotFoundException;
import edu.bbte.pmim2290.vrp.model.User;

public interface AuthService {

    User registerUser(InUserDTO userDTO) throws EntityNotFoundException, DatabaseException;

    String login(String username, String password);
}
