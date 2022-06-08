package main.commands;

import main.disk.Directory;
import main.disk.Disk;

import java.util.List;

public class DisplayDiskStatusCommand implements Command {
    @Override
    public void execute(List<String> arguments, Directory root) {
        if (!arguments.isEmpty())
            throw new IllegalArgumentException("Command 'DisplayDiskStatus' takes no arguments.");
        Disk disk = Disk.getInstance();
        List<Integer> freeIndices = disk.freeIndices();
        List<Integer> allocatedIndices = disk.allocatedIndices();
        System.out.printf("Empty space: %d KB%n", freeIndices.size());
        System.out.printf("Allocated space: %d KB%n", allocatedIndices.size());
        System.out.printf("Empty indices: %s%n", freeIndices);
        System.out.printf("Allocated indices: %s%n", allocatedIndices);
    }
}
