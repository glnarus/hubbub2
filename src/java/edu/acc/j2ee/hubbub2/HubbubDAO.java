package edu.acc.j2ee.hubbub2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class HubbubDAO {
    private final List<User> users = new ArrayList<>();    
            
    public void addUser(User user) {
      //we won't implement this at the moment because Hubbub doesn't support
        //registration yet, when it does use addPost as an example of 
        //adding a new record...
    }
    
    public void addPost(Post post) {
        this.addPost(post.getContent(), post.getAuthor());
    }
    
    public void addPost(String content, User user) {
        int userid = 0;
        Connection conn = null;
        Statement stat = null;
        Statement stat2 = null;
        ResultSet rs = null;
        ResultSet rs2 = null;
        try {
            //first, let's find out the user ID for this user
            conn = DriverManager.getConnection (
                "jdbc:derby://localhost:1527/hubbub;user=javauser;password=javauser");
            stat = conn.createStatement();
            rs = stat.executeQuery("SELECT userid FROM users WHERE username='" 
                        + user.getUserName() + "'");
            while (rs.next())
                userid = rs.getInt("userid");
            
            //we have the user ID, now we need to go get the postIDs so we can
            //be sure to create a unique one
            rs = stat.executeQuery("SELECT postid FROM posts ORDER BY postid");
            int newPostId = 0;
            while (rs.next())
                newPostId = rs.getInt("postid");
            newPostId ++; //this should be the next post ID that is unique
            
            //Lastly, get a string for the current date.  Util.Date
            //and sql Date don't automatically play nice to make life interesting
            java.sql.Date d = new java.sql.Date(new java.util.Date().getTime());
            
            //now we can add the post!
            String command = String.format(
                            "INSERT INTO posts VALUES (%d,'%s','%s',%d)",
                            userid, content, d.toString(), newPostId);
            stat.executeUpdate(command);
        }
        catch (SQLException se) {
            se.printStackTrace();
        }        
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (rs != null)
                    rs.close();                
            }
            catch (SQLException se2) {}
            try {
                if (stat != null)
                    stat.close();                
            }
            catch (SQLException se3) {}
            try {
                if (conn != null)
                    conn.close();                
            }
            catch (SQLException se4) {}
        }

    }
    
    public User find(String userName) {
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;
        User user = null;
        try {
            conn = DriverManager.getConnection(
              "jdbc:derby://localhost:1527/hubbub;user=javauser;password=javauser");
            stat = conn.createStatement();
            String command = String.format(
                    "SELECT * FROM users WHERE username='%s'",userName);
            rs = stat.executeQuery(command);
            while (rs.next()) {
                //there should be only one because the DB enforces uniqueness
                DateFormat df = new SimpleDateFormat("YYYY-MM-DD");                
                Date d = new Date();
                d = df.parse(rs.getString("joindate"));      
                user = new User (userName, rs.getString("password"),d);                
            }
            //close resources now that we have what we came for
        }
        catch (SQLException se) {
            se.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (rs != null)
                    rs.close();                
            }
            catch (SQLException se2) {}
            try {
                if (stat != null)
                    stat.close();                
            }
            catch (SQLException se3) {}
            try {
                if (conn != null)
                    conn.close();                
            }
            catch (SQLException se4) {}            
        }
        return user;
    }
    
    public List<Post> getSortedPosts() {
        List<Post> posts = new ArrayList<>();
        Connection conn = null;
        Statement stat = null;
        Statement stat2 = null;
        ResultSet rs = null;
        ResultSet rs2 = null;
        try {
            conn = DriverManager.getConnection(
            "jdbc:derby://localhost:1527/hubbub;user=javauser;password=javauser");        
            stat = conn.createStatement();
            rs = stat.executeQuery(
                    "select * from JAVAUSER.POSTS ORDER BY postdate DESC");
            while (rs.next()) {
                String content = rs.getString("content");
                Date postDate = (Date)rs.getObject("postdate");
                stat2 = conn.createStatement();
                String command = String.format(
                        "SELECT * FROM users WHERE userid=%d",rs.getInt("userid"));
                rs2 = stat2.executeQuery(command);
                //Next, we need to create a User object for the author field
                //of each Post
                rs2.next(); //advance the cursor to the 1st row
                String username = rs2.getString("username");
                String password = rs2.getString("password");
                Date joinDate = (Date)rs2.getObject("joindate");
                                                
                //Now, we have everything needed to create a post object
                //and they should be in proper order already
                posts.add(new Post (content, postDate, 
                          new User(username,password,joinDate)));
                //NOTE : this will not work for the User.posts field since
                //each post has a 'new' version of the User object.   
                
                //before we get into the next row in Posts, close out the
                //ResultSet and statment for getting the username
                rs2.close();
                stat2.close();
            }
            //clean up resources          
        }
        catch (SQLException se) {
            //we can't do much here if the dbase can't connect - show
            //stack trace
            se.printStackTrace();
        }
        catch (Exception e) { //catches null pointer issues when
            //SQL commands go awry     
            e.printStackTrace();
        }
        finally {
            //close resources if anything went wrong
            try {
                if (rs2 != null)
                    rs2.close();                
            }
            catch (SQLException se2) {}
            try {
                if (rs != null)
                    rs.close();                
            }
            catch (SQLException se3) {}
            try {
                if (stat2 != null)
                    stat2.close();                
            }
            catch (SQLException se4) {}
            try {
                if (stat != null)
                    stat.close();
            }
            catch (SQLException se5) {}
            try {
                if (conn != null)
                    conn.close();                
            }
            catch (SQLException se6) {}
            //wow, this is ugly
        }//finally statement                           
        return posts;
    }
    
    public User authenticate(String userName, String password) {
        User user = this.find(userName);
        if (user != null && user.getPassword().equals(password))
           return user;
        return null;
    }
}
