package main.commands;

import main.disk.Directory;
import main.disk.Disk;
import main.disk.File;
import main.users.UserManager;
import main.util.Writer;

import java.util.List;

public class ExitCommand implements Command {

    @Override
    public void execute(List<String> arguments, Directory root) {
        if (!arguments.isEmpty())
            throw new IllegalArgumentException("Command 'Exit' takes no arguments.");
        Writer.write("disk.txt", getBackup(root));

        UserManager userManager = UserManager.getInstance();
        StringBuilder users = new StringBuilder();
        userManager.getUsers().forEach(user -> users.append(user.toString()));
        Writer.write("users.txt", users.toString());

        StringBuilder capabilities = new StringBuilder();
        userManager.getUsers().forEach(user -> capabilities.append(user.formatCapabilities()));
        Writer.write("capabilities.txt", capabilities.toString());

        System.exit(0);
    }

    public String getBackup(Directory root) {
        Disk disk = Disk.getInstance();
        StringBuilder backup = new StringBuilder();
        backup.append(disk.freeIndices().toString())
                .append('\n')
                .append(disk.getAllocation().toString())
                .append('\n');
        traverse(root, backup);
        return backup.toString();
    }

    private void traverse(Directory root, StringBuilder backup) {
        backup.append(root.getFolderPath()).append('\n');
        for (File file : root.getFiles()) {
            backup.append(file.getFilePath()).append('/').append(file.getFileName()).append(' ');
            file.getAllocatedBlocks().forEach(block -> backup.append(block).append(' '));
            backup.deleteCharAt(backup.length() - 1).append('\n');
        }
        root.getSubDirectories().forEach(subDirectory -> traverse(subDirectory, backup));
    }
}
