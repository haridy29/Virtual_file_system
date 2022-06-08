package main.commands;
import main.disk.Directory;

import java.util.List;

public class NullCommand implements Command {
    @Override
    public void execute(List<String> arguments, Directory root) {
        throw new IllegalArgumentException("Invalid command.");
    }
}
