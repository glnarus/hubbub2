package edu.acc.j2ee.hubbub1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HubbubDAO {
    private List<User> users = new ArrayList<>();
    
    public void addUser(User user) {
        users.add(user);
    }
    
    public void addPost(Post post) {
        User author = find(post.getAuthor().getUserName());
        author.getPosts().add(post);
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
    
    public List<Post> getSortedAndEscapedPosts() {
        List<Post> posts = new ArrayList<>();
        for (User user: users){
            for (Post post : user.getPosts()){
                post.setContent(PostValidator.unEscapeSafe(post.getContent()));
                posts.add(post);
            }
        }
        Collections.sort(posts, new PostComparator());
        return posts;
    }
}
