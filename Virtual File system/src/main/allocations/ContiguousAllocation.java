package main.allocations;

import main.disk.Disk;
import main.disk.File;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ContiguousAllocation implements Allocation {
    @Override
    public boolean allocate(File file, int size) {
        if (size == 0)
            return true;
        Disk disk = Disk.getInstance();
        if (size > disk.freeIndices().size())
            return false;
        int startIndex = findBestFit(disk, size);
        if (startIndex == -1)
            return false;
        for (int i = startIndex; i < startIndex + size; i++) {
            disk.getBlocks()[i] = true;
            file.getAllocatedBlocks().add(i);
        }
        return true;
    }

    private int findBestFit(Disk disk, int size) {
        int contiguous = 0;
        boolean isStart = true;
        int startIndex = -1;
        List<Pair<Integer, Integer>> pairs = new ArrayList<>();
        for (int i = 0; i < disk.getBlocks().length; i++) {
            if (!disk.getBlocks()[i]) {
                contiguous++;
                startIndex = isStart ? i : startIndex;
                isStart = false;
            } if (disk.getBlocks()[i] || i == disk.getBlocks().length - 1) {
                pairs.add(new Pair<>(contiguous, startIndex));
                contiguous = 0;
                isStart = true;
            }
        }
        pairs.sort(Comparator.comparingInt(pair -> pair.first));
        for (Pair<Integer, Integer> pair : pairs)
            if (pair.first >= size)
                return pair.second;
        return -1;
    }

    private static class Pair<T, E> {
        T first;
        E second;

        Pair() {

        }

        Pair(T first, E second) {
            this.first = first;
            this.second = second;
        }
    }

    @Override
    public String toString() {
        return "Contiguous";
    }
}
