package main.disk;

import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author EHAB
 */
public class File {
    private int size;
    private String filePath;
    private String name;
    private List<Integer> allocatedBlocks;
    
    public File(String name, String path, int size) {
        this.allocatedBlocks = new ArrayList<>();
        this.name=name;
        this.size=size;
        this.filePath =path;
        if (!Disk.getInstance().allocate(this, size))
            throw new IllegalArgumentException("No enough space available.");
        
    }
    public String getFileName(){
        return name;
    }

    public String getFilePath() {
        return filePath;
    }

    public List<Integer> getAllocatedBlocks() {
        return allocatedBlocks;
    }

    public void setAllocatedBlocks(List<Integer> allocatedBlocks) {
        this.allocatedBlocks = allocatedBlocks;
    }
}