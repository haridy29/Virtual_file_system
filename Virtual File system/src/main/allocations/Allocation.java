package main.allocations;

import main.disk.File;

public interface Allocation {
    boolean allocate(File file, int size);
}
