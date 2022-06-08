package main.allocations;

import main.disk.Disk;
import main.disk.File;

public class IndexedAllocation implements Allocation {
    @Override
    public boolean allocate(File file, int size) {
        if (size == 0)
            return true;
        Disk disk = Disk.getInstance();
        if (size + 1 > disk.freeIndices().size())
            return false;
        boolean isStart = true;
        for (int i = 0, j = 0; i < disk.getBlocks().length && j < size; i++) {
            if (!disk.getBlocks()[i] && isStart) {
                isStart = false;
                file.getAllocatedBlocks().add(i);
            } else if (!disk.getBlocks()[i]) {
                file.getAllocatedBlocks().add(i);
                j++;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "Indexed";
    }
}
