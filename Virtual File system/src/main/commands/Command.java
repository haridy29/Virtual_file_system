package main.commands;
import main.disk.Directory;

import java.util.List;

public interface Command {
    void execute(List<String> arguments, Directory root);
}
