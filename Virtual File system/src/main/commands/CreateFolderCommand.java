package main.commands;

import main.disk.Directory;
import main.users.UserManager;
import main.util.Navigator;
import main.disk.Disk;

import java.util.Arrays;
import java.util.List;

public class CreateFolderCommand implements Command {
    @Override
    public void execute(List<String> arguments, Directory root) {
        if (arguments.size() != 1)
            throw new IllegalArgumentException("Invalid arguments to command 'CreateFolder'.");
        String regex = "^root\\/((\\.\\.|[a-zA-Z0-9_/\\-\\\\])*[a-zA-Z0-9]+)$";
        if (!arguments.get(0).matches(regex))
            throw new IllegalArgumentException("Invalid path.");
        UserManager userManager = UserManager.getInstance();
        if (!userManager.getLoggedUser().canCreate(arguments.get(0)))
            throw new IllegalArgumentException("You don't have permission.");
        String[] path = arguments.get(0).split("/");
        createFolder(List.of(path).subList(1, path.length), root);
    }

    private void createFolder(List<String> path, Directory root) {
        String directoryName = path.get(path.size() - 1);
        Directory targetDirectory = Navigator.navigateTo(root, path.subList(0, path.size() - 1));
        if (targetDirectory.getSubDirectories().stream().anyMatch(subDirectory -> subDirectory.getName().equals(directoryName)))
            throw new IllegalArgumentException("Directory already exists.");
        targetDirectory.getSubDirectories().add(new Directory(directoryName, String.format("%s/%s", targetDirectory.getFolderPath(), directoryName)));
    }
}
