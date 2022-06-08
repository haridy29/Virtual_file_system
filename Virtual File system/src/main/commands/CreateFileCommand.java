package main.commands;

import main.disk.Directory;
import main.disk.File;
import main.users.UserManager;
import main.util.Navigator;

import java.util.List;

public class CreateFileCommand implements Command {

    @Override
    public void execute(List<String> arguments, Directory root) {
        if (arguments.size() != 2)
            throw new IllegalArgumentException("Invalid arguments for command 'CreateFile'.");
        String regex = "^root\\/((\\.\\.|[a-zA-Z0-9_/\\-\\\\])*[a-zA-Z0-9]\\.[a-zA-Z0-9]+)$";
        if (!arguments.get(0).matches(regex))
            throw new IllegalArgumentException("Invalid path.");
        int size;
        try {
            size = Integer.parseInt(arguments.get(1));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid file size.");
        }
        UserManager userManager = UserManager.getInstance();
        if (!userManager.getLoggedUser().canCreate(arguments.get(0).substring(0, arguments.get(0).lastIndexOf('/'))))
            throw new IllegalArgumentException("You don't have permission.");

        String[] path = arguments.get(0).split("/");
        createFile(List.of(path).subList(1, path.length), size, root);
    }

    private void createFile(List<String> path, int size, Directory root) {
        Directory targetDirectory = Navigator.navigateTo(root, path.subList(0, path.size() - 1));
        String fileName = path.get(path.size() - 1);
        if (targetDirectory.getFiles().stream().anyMatch(file -> file.getFileName().equals(fileName)))
            throw new IllegalArgumentException("File already exists.");
        targetDirectory.getFiles().add(new File(fileName, targetDirectory.getFolderPath(), size));
    }
}
