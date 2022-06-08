package main.users;

public class Capability {
    private boolean canCreate;
    private boolean canDelete;
    private String directory;

    public Capability(boolean canCreate, boolean canDelete, String directory) {
        this.canCreate = canCreate;
        this.canDelete = canDelete;
        this.directory = directory;
    }

    public boolean isCanCreate() {
        return canCreate;
    }

    public void setCanCreate(boolean canCreate) {
        this.canCreate = canCreate;
    }

    public boolean isCanDelete() {
        return canDelete;
    }

    public void setCanDelete(boolean canDelete) {
        this.canDelete = canDelete;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    @Override
    public String toString() {
        return String.format("%s, %s", directory, (canCreate ? "1" : "0") + (canDelete ? "1" : "0"));
    }
}
