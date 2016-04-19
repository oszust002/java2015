package pl.edu.agh.java2015.ftp.server.database;

import pl.edu.agh.java2015.ftp.server.Group;
import pl.edu.agh.java2015.ftp.server.User;

/**
 * Class that holds info about file, which were took from database
 * @author Kamil Osuch
 * @version 1.0
 */
public class DatabaseFileRecord {
    private final Integer fileId;
    private final String filename;
    private final User owner;
    private final Group group;
    private final boolean ownerRead;
    private final boolean ownerWrite;
    private final boolean groupRead;
    private final boolean groupWrite;

    /**
     * Creates file with all file parameters from the files database
     * @param fileId ID of a file
     * @param filename name/path to a file
     * @param owner Owner of a file
     * @param group Group which can work on file when have permissions
     * @param ownerRead true if owner can read, false otherwise
     * @param ownerWrite true if owner can write, false otherwise
     * @param groupRead true if group can read, false otherwise
     * @param groupWrite true if group can write, false otherwise
     * @see DBFilesManager
     */
    public DatabaseFileRecord(Integer fileId, String filename, User owner, Group group, boolean ownerRead,
                              boolean ownerWrite, boolean groupRead, boolean groupWrite) {
        this.fileId = fileId;
        this.filename = filename;
        this.owner = owner;
        this.group = group;
        this.ownerRead = ownerRead;
        this.ownerWrite = ownerWrite;
        this.groupRead = groupRead;
        this.groupWrite = groupWrite;
    }

    /**
     * Checks if group members can write
     * @return true if group can write, false otherwise
     */
    public boolean isGroupWrite() {
        return groupWrite;
    }

    /**
     * Returns the owner of the file
     * @return {@link User} object, owner of the file
     */
    public User getOwner() {
        return owner;
    }

    /**
     * Returns the group which can work on file
     * @return {@link Group} object, which can work on file
     */
    public Group getGroup() {
        return group;
    }
    /**
     * Checks if owner can read
     * @return true if owner can read, false otherwise
     */
    public boolean isOwnerRead() {
        return ownerRead;
    }
    /**
     * Checks if owner can write
     * @return true if owner can write, false otherwise
     */
    public boolean isOwnerWrite() {
        return ownerWrite;
    }
    /**
     * Checks if group members can read
     * @return true if group can read, false otherwise
     */
    public boolean isGroupRead() {
        return groupRead;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DatabaseFileRecord that = (DatabaseFileRecord) o;

        if (ownerRead != that.ownerRead) return false;
        if (ownerWrite != that.ownerWrite) return false;
        if (groupRead != that.groupRead) return false;
        if (groupWrite != that.groupWrite) return false;
        if (fileId != null ? !fileId.equals(that.fileId) : that.fileId != null) return false;
        if (filename != null ? !filename.equals(that.filename) : that.filename != null) return false;
        if (owner != null ? !owner.equals(that.owner) : that.owner != null) return false;
        return group != null ? group.equals(that.group) : that.group == null;

    }

    @Override
    public int hashCode() {
        int result = fileId != null ? fileId.hashCode() : 0;
        result = 31 * result + (filename != null ? filename.hashCode() : 0);
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        result = 31 * result + (group != null ? group.hashCode() : 0);
        result = 31 * result + (ownerRead ? 1 : 0);
        result = 31 * result + (ownerWrite ? 1 : 0);
        result = 31 * result + (groupRead ? 1 : 0);
        result = 31 * result + (groupWrite ? 1 : 0);
        return result;
    }
}
