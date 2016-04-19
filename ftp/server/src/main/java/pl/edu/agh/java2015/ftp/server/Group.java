package pl.edu.agh.java2015.ftp.server;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Kamil on 13.01.2016.
 */
public class Group {
    private Integer id;
    private String groupname;
    private List<User> membersList = new LinkedList<User>();
    private boolean membersUpdated = false;

    public Group(int id, String groupname){
        this.id = id;
        this.groupname = groupname;
    }

    public String getGroupname() {
        return groupname;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void addMember(User user){
        membersList.add(user);
        membersUpdated = false;
    }

    public void removeMember(User user){
        membersList.remove(user);
        membersUpdated = false;
    }


    public void setMembersList(List<User> membersList) {
        this.membersList = membersList;
        membersUpdated = false;
    }

    public void membersUpdated(){
        membersUpdated = true;
    }

    public boolean isMembersUpdated() {
        return membersUpdated;
    }

}
