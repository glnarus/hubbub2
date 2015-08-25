package edu.acc.j2ee.hubbub2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class HubbubDAO {
    private final List<User> users = new ArrayList<>();
    
    public void addUser(User user) {
        users.add(user);
    }
    
    public void addPost(Post post) {
        User author = find(post.getAuthor().getUserName());
        author.getPosts().add(post);
    }
    
    public void addPost(String content, User user) {
        Post p = new Post(content, new Date(), user);
        user.getPosts().add(p);
    }
    
    public User find(String userName) {
        for (User user : users)
            if (user.getUserName().equals(userName))
                return user;
        return null;
    }
    
    public List<Post> getSortedPosts() {
        List<Post> posts = new ArrayList<>();
        for (User user : users)
            posts.addAll(user.getPosts());
        Collections.sort(posts, new PostComparator());
        return posts;
    }
    
    public User authenticate(String userName, String password) {
        User user = find(userName);
        if (user != null && user.getPassword().equals(password))
            return user;
        return null;
    }
}
