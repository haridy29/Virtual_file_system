package main.commands;
import main.disk.Directory;

import java.util.List;
import java.util.Map;

public class CommandManager {

    private final Map<String, Command> commands = Map.ofEntries(
            Map.entry("CreateFile", new CreateFileCommand()),
            Map.entry("CreateFolder", new CreateFolderCommand()),
            Map.entry("DeleteFile", new DeleteFileCommand()),
            Map.entry("DeleteFolder", new DeleteFolderCommand()),
            Map.entry("DisplayDiskStatus", new DisplayDiskStatusCommand()),
            Map.entry("DisplayDiskStructure", new DisplayDiskStructureCommand()),
            Map.entry("Format", new FormatCommand()),
            Map.entry("TellUser", new TellUserCommand()),
            Map.entry("CUser", new CUserCommand()),
            Map.entry("Login", new LoginCommand()),
            Map.entry("Grant", new GrantCommand()),
            Map.entry("Exit", new ExitCommand())
    );

    public void execute(String fullCommand, Directory root) {
        String[] parts = fullCommand.split(" ");
        commands.getOrDefault(parts[0], new NullCommand()).execute(List.of(parts).subList(1, parts.length), root);
    }
}
