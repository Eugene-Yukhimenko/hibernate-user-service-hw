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

        String salt = PasswordUtil.generateSalt();
        String hashedPassword = PasswordUtil.hashPassword(password, salt);

        User user = new User();
        user.setEmail(email);
        user.setSalt(salt);
        user.setPassword(hashedPassword);

        return userService.add(user);
    }

    @Override
    public User login(String email, String password)
            throws AuthenticationException {

        User user = userService.findByEmail(email)
                .orElseThrow(() -> new AuthenticationException(
                        "User not found with email: " + email));

        String hashedInputPassword =
                PasswordUtil.hashPassword(password, user.getSalt());

        if (!hashedInputPassword.equals(user.getPassword())) {
            throw new AuthenticationException("Wrong password");
        }

        return user;
    }
}
