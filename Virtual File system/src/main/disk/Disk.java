package main.disk;

import main.allocations.Allocation;
import main.allocations.ContiguousAllocation;
import main.allocations.LinkedAllocation;
import main.allocations.NullAllocation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Disk {
    public final int TOTAL_SIZE = 10;
    private Boolean[] blocks;
    private Allocation allocation;
    private static Disk disk = null;

    private Disk() {
        blocks = new Boolean[TOTAL_SIZE];
        for (int i = 0; i < TOTAL_SIZE; i++)
            blocks[i] = false;
        allocation = new ContiguousAllocation();
    }

    public static Disk getInstance() {
        disk = disk == null ? new Disk() : disk;
        return disk;
    }

    public void setAllocation(Allocation allocation) {
        if (allocation instanceof NullAllocation)
            throw new IllegalArgumentException("Invalid allocation.");
        this.allocation = allocation;
    }

    public Allocation getAllocation() {
        return allocation;
    }

    public boolean allocate(File file, int size) {
        return allocation.allocate(file, size);
    }

    private List<Integer> getIndices(boolean bool) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < blocks.length; i++)
            if (blocks[i] == bool)
                result.add(i);
        return result;
    }

    public List<Integer> freeIndices() {
        return getIndices(false);
    }

    public List<Integer> allocatedIndices() {
        return getIndices(true);
    }

    public void deallocate(File file) {
        file.getAllocatedBlocks().forEach(index -> blocks[index] = false);
    }

    public void deallocate(Directory directory) {
        directory.getFiles().forEach(this::deallocate);
        directory.getSubDirectories().forEach(this::deallocate);
    }

    public Boolean[] getBlocks() {
        return blocks;
    }
}
