package main.disk;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.List;

/**
 * @author EHAB
 */
public class Directory {

    private List<File> files;
    private String folderPath;
    private List<Directory> subDirectories;
    private String name;

    public Directory(String name, String path) {
        this.name = name;
        this.folderPath = path;
        files = new ArrayList<>();
        subDirectories = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String buildStructure() {
        StringBuilder structure = new StringBuilder();
        printHelper(0, structure);
        return structure.toString();
    }

    public List<File> getFiles() {
        return files;
    }

    public List<Directory> getSubDirectories() {
        return subDirectories;
    }

    public void printHelper(int level, StringBuilder structure) {
        StringBuilder indentation = new StringBuilder("\t".repeat(level));
        structure.append(String.format("%s<%s>\n", indentation, name));
        indentation.append("\t");
        files.forEach(file -> structure.append(indentation).append(file.getFileName()).append('\n'));
        subDirectories.forEach(subDirectory -> subDirectory.printHelper(level + 1, structure));
    }

    public String getFolderPath() {
        return folderPath;
    }

    public void clear() {
        Disk disk = Disk.getInstance();
        files.forEach(disk::deallocate);
        subDirectories.forEach(disk::deallocate);
        files.clear();
        subDirectories.clear();
    }
}
