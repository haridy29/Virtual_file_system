package main.allocations;

import main.disk.File;
import main.disk.Disk;

public class LinkedAllocation implements Allocation {
    @Override
    public boolean allocate(File file, int size) {
        if (size == 0)
            return true;
        Disk disk = Disk.getInstance();
        if (size > disk.freeIndices().size())
            return false;
        for (int i = 0, j = 0; i < disk.getBlocks().length && j < size; i++) {
            if (!disk.getBlocks()[i]) {
                disk.getBlocks()[i] = true;
                file.getAllocatedBlocks().add(i);
                j++;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "Linked";
    }
}
