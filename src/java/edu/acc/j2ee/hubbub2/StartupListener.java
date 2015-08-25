package edu.acc.j2ee.hubbub2;

import java.util.Date;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class StartupListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        HubbubDAO db = new HubbubDAO();
        User jd = new User("johndoe", "password", new Date(113, 4, 9));
        User jj = new User("jilljack", "password", new Date(113, 9, 31));
        db.addUser(jd);
        db.addUser(jj);

        Post one = new Post(
                "I'm a white-hat hacking my wonky Twonky server.",
                new Date(113, 4, 9),
                jd);
        db.addPost(one);

        Post two = new Post(
                "My wonky Twonky server conked out.",
                new Date(114, 5, 23),
                jd);
        db.addPost(two);

        Post three = new Post(
                "I see good reason not to configure Twonky.",
                new Date(113, 10, 1),
                jj);
        db.addPost(three);
        sce.getServletContext().setAttribute("db", db);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
