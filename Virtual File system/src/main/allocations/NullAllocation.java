package main.allocations;

import main.disk.File;

public class NullAllocation implements Allocation {
    @Override
    public boolean allocate(File file, int size) {
        return false;
    }
}
