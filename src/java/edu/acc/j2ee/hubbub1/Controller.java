package edu.acc.j2ee.hubbub1;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Controller extends HttpServlet {
    private final String loginRules = "Username and password must be alphanumeric.\nPassword between 6 and 15 characters";
    private HubbubDAO db;
    
    @Override
    public void init() {
       db = (HubbubDAO)getServletContext().getAttribute("db");
    }            
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        compilePosts (request);
        request.getRequestDispatcher("timeline.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {        
        String destination = "login.jsp";
        String username = request.getParameter("username");
        String password = request.getParameter("password");        
        String post = request.getParameter("newpost"); 
        User thisUser = (User)request.getSession().getAttribute("user");
        
        if (post != null)  {
            if (thisUser == null) {
                request.setAttribute("flash", "You must log in to post");                
            }
            post = PostValidator.escapeSafe(post);
            request.setAttribute("flash", "New post added!");
            //actually need to add the post here
            db.addPost(new Post(post,new Date(), thisUser));
            compilePosts (request);
            destination = "timeline.jsp";                                                       
        }
        else {
            destination = "login.jsp";

            if ((username == null) || (password == null)) {
                request.setAttribute("flash", loginRules);
            }
            else {
                if (!LoginValidator.isValid(username,password)) {
                    request.setAttribute("flash", loginRules);
                }
                else {
                    User person = LoginAuthenticator.authenticate(
                                                             username,password,db);
                    if (person != null) {
                    //user is valid, let's log them in by adding them to the session
                        request.getSession().setAttribute("user", person);
                        compilePosts(request);
                        destination = "timeline.jsp";
                    }
                    else {
                        //figure out if the user exists to inform about the
                        //password mismatch
                        if (LoginAuthenticator.exists(username, db)) {
                            request.setAttribute("flash", "Password mismatch");
                        }
                        else {
                            request.setAttribute("flash", "Username does not exist");
                        }                   
                    }
                }
            }
        }
        request.getRequestDispatcher(destination).forward(request, response);                                
    }
    
    private void compilePosts (HttpServletRequest request) {
        List<Post> posts = db.getSortedAndEscapedPosts();
        request.setAttribute("posts", posts);                
    }
}
