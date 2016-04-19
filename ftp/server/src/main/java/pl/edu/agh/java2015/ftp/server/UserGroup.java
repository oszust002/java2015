package pl.edu.agh.java2015.ftp.server;

/**
 * Created by Kamil on 04.02.2016.
 */
public class UserGroup {
    private Integer userID;
    private Integer groupID;

    public UserGroup(Integer userID, Integer groupID) {
        this.userID = userID;
        this.groupID = groupID;
    }


    public Integer getGroupID() {
        return groupID;
    }

    public Integer getUserID() {
        return userID;
    }
}
