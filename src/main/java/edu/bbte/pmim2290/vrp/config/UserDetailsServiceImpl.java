package edu.bbte.pmim2290.vrp.config;

import edu.bbte.pmim2290.vrp.dao.UserDAO;
import edu.bbte.pmim2290.vrp.exception.AuthenticationFailedException;
import edu.bbte.pmim2290.vrp.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserDAO userDAO;

    public UserDetailsServiceImpl(final UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) {
        final User user = userDAO.findByUsername(username)
                .orElseThrow(() -> new AuthenticationFailedException("Invalid username or password"));
        return new UserDetailsImpl(user);
    }
}
