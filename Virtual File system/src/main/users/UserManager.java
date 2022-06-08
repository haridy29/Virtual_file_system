package main.users;

import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private User loggedUser;
    private final List<User> users;

    private static UserManager userManager = null;

    private UserManager() {
        users = new ArrayList<>();
        users.add(new User("admin", "admin", true)); // Test
    }

    public static UserManager getInstance() {
        userManager = userManager == null ? new UserManager() : userManager;

        return userManager;
    }

    public User login(String username, String password) {
        return users.stream()
                .filter(user -> user.getUsername().equalsIgnoreCase(username) && user.getPassword().equals(password))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Incorrect username or password."));
    }

    public boolean register(String username, String password) {
        if (userExists(username))
            return false;
        boolean isAdmin = username.equalsIgnoreCase("admin") && password.equals("admin");
        users.add(new User(username, password, isAdmin));
        return true;
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    public boolean userExists(String username) {
        return users.stream().anyMatch(user -> user.getUsername().equalsIgnoreCase(username));
    }

    public User getUser(String username) {
        if (userExists(username)) {
            return users.stream()
                    .filter(user -> user.getUsername().equalsIgnoreCase(username))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("User does not exist"));
        }
        throw new IllegalArgumentException("User does not exist");
    }

    public List<User> getUsers() {
        return users;
    }
}
