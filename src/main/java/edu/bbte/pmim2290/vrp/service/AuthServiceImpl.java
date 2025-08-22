package edu.bbte.pmim2290.vrp.service;

import edu.bbte.pmim2290.vrp.config.JwtUtil;
import edu.bbte.pmim2290.vrp.dao.UserDAO;
import edu.bbte.pmim2290.vrp.dto.InUserDTO;
import edu.bbte.pmim2290.vrp.exception.AuthenticationFailedException;
import edu.bbte.pmim2290.vrp.exception.DatabaseException;
import edu.bbte.pmim2290.vrp.exception.EntityNotFoundException;
import edu.bbte.pmim2290.vrp.exception.UserAlreadyExistsException;
import edu.bbte.pmim2290.vrp.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserDAO userDAO;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthServiceImpl(final UserDAO userDAO, final JwtUtil jwtUtil) {
        this.userDAO = userDAO;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public User registerUser(final InUserDTO userDTO) throws EntityNotFoundException, DatabaseException {
        userDAO.findByUsername(userDTO.getUsername()).ifPresent(u -> {
            throw new UserAlreadyExistsException("Username already taken");
        });

        final User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        return userDAO.save(user);
    }

    @Override
    public String login(final String username, final String password) {
        final User user = userDAO.findByUsername(username)
                .orElseThrow(() -> new AuthenticationFailedException("Invalid username or password"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new AuthenticationFailedException("Invalid username or password");
        }

        return jwtUtil.generateToken(user.getUsername());
    }
}
