package main.users;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private String password;
    private boolean isAdmin;
    private List<Capability> capabilities;

    public User(String username, String password, boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
        capabilities = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public List<Capability> getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(List<Capability> capabilities) {
        this.capabilities = capabilities;
    }

    public boolean canCreate(String path) {
        return capabilities.stream()
                .anyMatch(capability -> path.equals(capability.getDirectory()) && capability.isCanCreate()) || isAdmin;
    }

    public boolean canDelete(String path) {
        return capabilities.stream()
                .anyMatch(capability -> path.equals(capability.getDirectory()) && capability.isCanDelete()) || isAdmin;
    }

    @Override
    public String toString() {
        return String.format("%s, %s\n", username, password);
    }

    public String formatCapabilities() {
        StringBuilder caps = new StringBuilder();
        capabilities.forEach(capability -> caps.append(String.format("%s, %s\n", username, capability.toString())));
        return caps.toString();
    }
}
