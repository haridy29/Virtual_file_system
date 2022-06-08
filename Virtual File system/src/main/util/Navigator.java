package main.util;

import main.disk.Directory;

import java.util.List;

public class Navigator {

    private static boolean found = false;

    public static Directory navigateTo(Directory root, List<String> toPath) {
        Directory targetDirectory = navigateHelper(root, toPath);
        found = false;
        return targetDirectory;
    }

    private static Directory navigateHelper(Directory root, List<String> toPath) {
        if (toPath.isEmpty()) {
            found = true;
            return root;
        }
        for (Directory subDirectory : root.getSubDirectories())
            if (subDirectory.getName().equals(toPath.get(0)))
                root = navigateHelper(subDirectory, toPath.subList(1, toPath.size()));
        if (!found)
            throw new IllegalArgumentException("Path not found.");
        return root;
    }
}
