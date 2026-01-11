package mate.academy.service.impl;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.security.PasswordUtil;
import mate.academy.service.AuthenticationService;
import mate.academy.service.UserService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Inject
    private UserService userService;

    @Override
    public User register(String email, String password)
            throws RegistrationException {

        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("User already exists with email: " + email);
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        return userService.add(user);
    }

    @Override
    public User login(String email, String password)
            throws AuthenticationException {

        User user = userService.findByEmail(email).orElse(null);

        if (user == null
                || !PasswordUtil.hashPassword(password, user.getSalt())
                .equals(user.getPassword())) {
            throw new AuthenticationException("Incorrect email or password");
        }

        return user;
    }
}
