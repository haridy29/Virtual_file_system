package main.commands;

import main.disk.Directory;
import main.users.Capability;
import main.users.User;
import main.users.UserManager;
import main.util.Navigator;

import java.util.List;

public class GrantCommand implements Command {
    @Override
    public void execute(List<String> arguments, Directory root) {
        if (arguments.size() != 3)
            throw new IllegalArgumentException("Invalid arguments for command 'Grant'.");
        UserManager userManager = UserManager.getInstance();
        if (!userManager.getLoggedUser().isAdmin())
            throw new IllegalArgumentException("Only admin can use Grant command.");
        if (!userManager.userExists(arguments.get(0)))
            throw new IllegalArgumentException("User does not exist.");
        String regex = "^root\\/((\\.\\.|[a-zA-Z0-9_/\\-\\\\])*[a-zA-Z0-9]+)$";
        if (!arguments.get(1).equals("root")) {
            if (!arguments.get(1).matches(regex))
                throw new IllegalArgumentException("Path is not a folder.");
        }
        String[] path = arguments.get(1).split("/");
        Navigator.navigateTo(root, List.of(path).subList(1, path.length));
        String capability = arguments.get(2);
        if (!List.of("00", "01", "10", "11").contains(capability))
            throw new IllegalArgumentException("Invalid capability.");
        boolean create = capability.charAt(0) == '1';
        boolean delete = capability.charAt(1) == '1';
        User user = userManager.getUser(arguments.get(0));
        Capability cap = user.getCapabilities().stream().filter(capa -> capa.getDirectory().equals(arguments.get(1))).findFirst().orElse(null);
        if (cap == null)
            user.getCapabilities().add(new Capability(create, delete, arguments.get(1)));
        else {
            cap.setCanCreate(create);
            cap.setCanDelete(delete);
        }
    }
}
