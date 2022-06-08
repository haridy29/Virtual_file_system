package main.commands;

import main.disk.Directory;

import java.util.List;

public class DisplayDiskStructureCommand implements Command {
    @Override
    public void execute(List<String> arguments, Directory root) {
        if (!arguments.isEmpty())
            throw new IllegalArgumentException("Command 'DisplayDiskStructure' takes no arguments.");
        System.out.println(root.buildStructure());
    }
}
