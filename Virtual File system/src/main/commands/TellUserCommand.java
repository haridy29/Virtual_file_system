package main.commands;

import main.disk.Directory;
import main.users.UserManager;

import java.util.List;

public class TellUserCommand implements Command {
    @Override
    public void execute(List<String> arguments, Directory root) {
        if (!arguments.isEmpty())
            throw new IllegalArgumentException("Invalid arguments for command 'TellUser'.");
        System.out.println(UserManager.getInstance().getLoggedUser().getUsername());
    }
}
