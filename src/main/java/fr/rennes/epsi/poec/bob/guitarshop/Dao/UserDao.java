package fr.rennes.epsi.poec.bob.guitarshop.Dao;

import fr.rennes.epsi.poec.bob.guitarshop.Domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class UserDao {

    private final DataSource data_source;

    @Autowired
    public UserDao(DataSource data_source) {
        this.data_source = data_source;
    }

    @Autowired(required = false)
    private PasswordEncoder passwordEncoder;


    public void registerUser(User user) throws SQLException {
        // pre-encoding checks

        // en of pre-encoding checks
        // String password = passwordEncoder.encode(user.getPassword());

        String sql = "INSERT INTO users (login, password, role) VALUES (?,?,?)";
        System.out.println(sql);
        try (PreparedStatement stmt = data_source.getConnection().prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, passwordEncoder.encode(user.getPassword()));
            stmt.setString(3, user.getRole());
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
}
