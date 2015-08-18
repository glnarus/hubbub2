package edu.acc.j2ee.hubbub1;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User implements java.io.Serializable {
    private String userName;
    private String password;
    private Date joinDate;
    private List<Post> posts = new ArrayList<>();
    
    public User(String userName, String password, Date joinDate) {
        this.userName = userName;
        this.joinDate = joinDate;
        this.password = password;
    }    
    
    public User() {}

    public String getUserName() {
        return userName;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public String getPassword() {
        return password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }
    
    public List<Post> getPosts() {
        return posts;
    }

    public void setPassword(String password) {
        this.password = password;
    }    
    
    @Override
    public String toString() {
        return userName;
    }

}
