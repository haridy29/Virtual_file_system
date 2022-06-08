package main.commands;

import main.disk.Directory;
import main.users.UserManager;

import java.util.List;

public class CUserCommand implements Command {
    @Override
    public void execute(List<String> arguments, Directory root) {
        if (arguments.size() != 2)
            throw new IllegalArgumentException("Invalid arguments for command 'CUser'.");
        if (!UserManager.getInstance().getLoggedUser().isAdmin())
            throw new IllegalArgumentException("Only admin can use CUser command.");
        if (!UserManager.getInstance().register(arguments.get(0), arguments.get(1)))
            throw new IllegalArgumentException("Username already exists.");
    }
}
