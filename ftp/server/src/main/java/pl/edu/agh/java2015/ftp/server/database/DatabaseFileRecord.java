package pl.edu.agh.java2015.ftp.server.database;

import pl.edu.agh.java2015.ftp.server.Group;
import pl.edu.agh.java2015.ftp.server.User;

/**
 * Created by Kamil on 25.01.2016.
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

    public boolean isGroupWrite() {
        return groupWrite;
    }

    public Integer getFileId() {
        return fileId;
    }

    public String getFilename() {
        return filename;
    }

    public User getOwner() {
        return owner;
    }

    public Group getGroup() {
        return group;
    }

    public boolean isOwnerRead() {
        return ownerRead;
    }

    public boolean isOwnerWrite() {
        return ownerWrite;
    }

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
