package mate.academy.service.impl;

import java.util.Optional;
import mate.academy.dao.UserDao;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.security.PasswordUtil;
import mate.academy.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Inject
    private UserDao userDao;

    @Override
    public User add(User user) {
        // Тут user.getPassword() — це PLAIN пароль з AuthenticationService
        String salt = PasswordUtil.generateSalt();
        String hashedPassword =
                PasswordUtil.hashPassword(user.getPassword(), salt);

        user.setSalt(salt);
        user.setPassword(hashedPassword);

        return userDao.add(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userDao.findByEmail(email);
    }
}
