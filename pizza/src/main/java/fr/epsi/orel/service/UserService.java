package fr.epsi.orel.service;

import fr.epsi.orel.dao.UserDAO;
import fr.epsi.orel.domain.User;
import fr.epsi.orel.domain.UserRole;
import fr.epsi.orel.exception.TechnicalException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Repository
public class UserService implements UserDetailsService {

    private static final Logger logger = LogManager.getLogger(UserService.class);
    @Autowired
    private UserDAO userDAO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            UserDetails user = userDAO.getUserByMail(username);
            if (user == null) {
                throw new UsernameNotFoundException("User not found : " + username);
            }
            return user;
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new TechnicalException(e);
        }
    }

    public void addUser(User user) {
        try {
            userDAO.setRole(UserRole.ROLE_USER.name());
            userDAO.addUser(user);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new TechnicalException(e);
        }
    }
}
