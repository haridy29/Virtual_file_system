package main.commands;

import main.disk.Directory;
import main.users.User;
import main.users.UserManager;

import java.util.List;

public class LoginCommand implements Command {
    @Override
    public void execute(List<String> arguments, Directory root) {
        if (arguments.size() != 2)
            throw new IllegalArgumentException("Invalid arguments for command 'Login'.");
        User user = UserManager.getInstance().login(arguments.get(0), arguments.get(1));
        UserManager.getInstance().setLoggedUser(user);
    }
}
