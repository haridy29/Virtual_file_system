package main.commands;

import main.allocations.*;
import main.disk.Directory;
import main.disk.Disk;

import java.util.List;
import java.util.Map;

public class FormatCommand implements Command {

    Map<String, Allocation> allocations = Map.ofEntries(
            Map.entry("Contiguous", new ContiguousAllocation()),
            Map.entry("Linked", new LinkedAllocation()),
            Map.entry("Indexed", new IndexedAllocation())
    );

    @Override
    public void execute(List<String> arguments, Directory root) {
        if (arguments.size() > 1)
            throw new IllegalArgumentException("Invalid arguments for command 'Format'.");
        root.clear();
        if (!arguments.isEmpty())
            Disk.getInstance().setAllocation(allocations.getOrDefault(arguments.get(0), new NullAllocation()));
    }
}
