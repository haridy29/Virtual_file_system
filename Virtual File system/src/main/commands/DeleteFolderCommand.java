package main.commands;

import main.disk.Directory;
import main.users.UserManager;
import main.util.Navigator;
import main.disk.Disk;

import java.util.Arrays;
import java.util.List;

public class DeleteFolderCommand implements Command {
    @Override
    public void execute(List<String> arguments, Directory root) {
        if (arguments.size() != 1)
            throw new IllegalArgumentException("Invalid arguments to command 'CreateFolder'.");
        String regex = "^root\\/((\\.\\.|[a-zA-Z0-9_/\\-\\\\])*[a-zA-Z0-9]+)$";
        if (!arguments.get(0).matches(regex))
            throw new IllegalArgumentException("Invalid path.");
        UserManager userManager = UserManager.getInstance();
        if (!userManager.getLoggedUser().canDelete(arguments.get(0)))
            throw new IllegalArgumentException("You don't have permission.");
        String[] path = arguments.get(0).split("/");
        deleteFolder(List.of(path).subList(1, path.length), root);
    }

    private void deleteFolder(List<String> path, Directory root) {
        Disk disk = Disk.getInstance();
        String directoryName = path.get(path.size() - 1);
        Directory targetDirectory = Navigator.navigateTo(root, path.subList(0, path.size() - 1));
        targetDirectory.getFiles().forEach(disk::deallocate);
        targetDirectory.getSubDirectories()
                .stream()
                .filter(subDirectory -> subDirectory.getName().equals(directoryName))
                .findFirst()
                .map(subDirectory -> {
                    Disk.getInstance().deallocate(subDirectory);
                    return targetDirectory.getSubDirectories().remove(subDirectory);
                })
                .orElseThrow(() -> new IllegalArgumentException("Directory not found."));
    }
}
