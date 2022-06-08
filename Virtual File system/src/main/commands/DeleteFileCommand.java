package main.commands;

import main.disk.Directory;
import main.users.UserManager;
import main.util.Navigator;
import main.disk.Disk;

import java.util.Arrays;
import java.util.List;

public class DeleteFileCommand implements Command {
    @Override
    public void execute(List<String> arguments, Directory root) {
        if (arguments.size() != 1)
            throw new IllegalArgumentException("Invalid arguments for command 'DeleteFile'.");
        String regex = "^root\\/((\\.\\.|[a-zA-Z0-9_/\\-\\\\])*[a-zA-Z0-9]\\.[a-zA-Z0-9]+)$";
        if (!arguments.get(0).matches(regex))
            throw new IllegalArgumentException("Invalid path.");
        UserManager userManager = UserManager.getInstance();
        if (!userManager.getLoggedUser().canDelete(arguments.get(0).substring(0, arguments.get(0).lastIndexOf('/'))))
            throw new IllegalArgumentException("You don't have permission.");
        String[] path = arguments.get(0).split("/");
        deleteFile(List.of(path).subList(1, path.length), root);
    }

    private void deleteFile(List<String> path, Directory root) {
        Directory targetDirectory = Navigator.navigateTo(root, path.subList(0, path.size() - 1));
        String fileName = path.get(path.size() - 1);
        targetDirectory.getFiles()
                .stream()
                .filter(file -> file.getFileName().equals(fileName))
                .findFirst()
                .map(file -> {
                    Disk.getInstance().deallocate(file);
                    return targetDirectory.getFiles().remove(file);
                })
                .orElseThrow(() -> new IllegalArgumentException("File not found."));
    }
}
