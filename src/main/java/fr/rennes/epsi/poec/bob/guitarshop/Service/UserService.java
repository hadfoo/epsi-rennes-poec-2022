package fr.rennes.epsi.poec.bob.guitarshop.Service;

import fr.rennes.epsi.poec.bob.guitarshop.Dao.UserDao;
import fr.rennes.epsi.poec.bob.guitarshop.Domain.user.User;
import fr.rennes.epsi.poec.bob.guitarshop.Domain.user.UserRole;
import fr.rennes.epsi.poec.bob.guitarshop.Exception.TechnicalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class UserService {

    @Autowired
    private UserDao userDAO;

    public void registerUser(User user) {
        try {
            user.setRole(UserRole.ROLE_CUSTOMER.name());
            userDAO.registerUser(user);
        } catch (SQLException e) {
//            logger.error(e.getMessage(), e);
            throw new TechnicalException(e);
        }
    }
}
