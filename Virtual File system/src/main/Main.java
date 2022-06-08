package main;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import main.allocations.Allocation;
import main.allocations.ContiguousAllocation;
import main.allocations.IndexedAllocation;
import main.allocations.LinkedAllocation;
import main.commands.CommandManager;
import main.disk.Directory;
import main.disk.Disk;
import main.disk.File;
import main.users.Capability;
import main.users.User;
import main.users.UserManager;
import main.util.Navigator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author EHAB
 */
public class Main {

    private static CommandManager commandManager;
    private static Directory root;
    private static final Map<String, Allocation> allocations = Map.ofEntries(
            Map.entry("Contiguous", new ContiguousAllocation()),
            Map.entry("Linked", new LinkedAllocation()),
            Map.entry("Indexed", new IndexedAllocation())
    );

    public static void main(String[] args) {
        commandManager = new CommandManager();
        root = new Directory("root", "root");
        Scanner in = new Scanner(System.in);
        init();
        while (true) {
            System.out.print(">> ");
            try {
                commandManager.execute(in.nextLine(), root);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void init() {
        Disk disk = Disk.getInstance();
        commandManager.execute("Login admin admin", root);
        readDisk(disk);
        readUsers();
        readCapabilities();
    }

    public static void readDisk(Disk disk) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("disk.txt"));
            if (reader.readLine() == null)
                return;
            String allocation = reader.readLine();
            disk.setAllocation(allocations.get(allocation));
            reader.readLine();
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                String[] parts = line.split(" ");
                if (parts.length > 1) {
                    commandManager.execute(String.format("CreateFile %s %d", parts[0], 0), root);
                    List<String> blocks = List.of(parts).subList(1, parts.length);
                    blocks.forEach(block -> disk.getBlocks()[Integer.parseInt(block)] = true);
                    String[] path = parts[0].split("/");
                    Directory directory = Navigator.navigateTo(root, List.of(path).subList(1, path.length - 1));
                    File file = directory.getFiles()
                            .stream()
                            .filter(f -> f.getFileName().equals(path[path.length  -1]))
                            .findFirst()
                            .get();
                    for (String block : blocks) file.getAllocatedBlocks().add(Integer.valueOf(block));
                } else commandManager.execute(String.format("CreateFolder %s", parts[0]), root);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readUsers() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("users.txt"));
            if (reader.readLine() == null) return;
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                String[] credentials = line.split(", ");
                commandManager.execute(String.format("CUser %s %s", credentials[0], credentials[1]), root);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readCapabilities() {
        UserManager userManager = UserManager.getInstance();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("capabilities.txt"));
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                String[] parts = line.split(", ");
                User user = userManager.getUser(parts[0]);
                user.getCapabilities().add(new Capability(parts[2].charAt(0) == '1', parts[2].charAt(1) == '1', parts[1]));
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
