package mate.academy;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Injector;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.academy");

        AuthenticationService authService =
                (AuthenticationService) injector.getInstance(AuthenticationService.class);

        try {
            System.out.println("=== REGISTER ===");
            User user = authService.register("test@mail.com", "1234");
            System.out.println("User created: " + user.getEmail());

            System.out.println("=== LOGIN OK ===");
            User logged = authService.login("test@mail.com", "1234");
            System.out.println("Logged in as: " + logged.getEmail());

            System.out.println("=== LOGIN FAIL ===");
            authService.login("test@mail.com", "wrongpassword");

        } catch (RegistrationException e) {
            System.out.println("Registration error: " + e.getMessage());
        } catch (AuthenticationException e) {
            System.out.println("Authentication error: " + e.getMessage());
        }
    }
}
