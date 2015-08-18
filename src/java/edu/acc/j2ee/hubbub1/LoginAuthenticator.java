package edu.acc.j2ee.hubbub1;

/**
 * Authenticates username/password combination by
 * accessing the HubbubDAO to query if the user 
 * exists and the password matches.  Expects the
 * LoginValidator to be run first to remove
 * unsafe characters.  
 */
public class LoginAuthenticator {
    /**
     * 
     * Looks up if a provided string username/password exists as a user
     * in HubbubDAO db.  If it does, a User object is returned.  If no
     * username/password match is found, null is returned.
     */
    public static User authenticate(String username, 
                                    String password, HubbubDAO db) {
        
        User person = db.find(username);
        if (person != null) {
            if (person.getPassword().equals(password))
                return person;
            else
                return null;
        }
        else
            return null;        
    }
    /**
     * If a username exists in the HubbubDAO database, this method will
     * return true, otherwise false.
     */
    public static boolean exists(String username, HubbubDAO db) {       
        return !(db.find(username) == null);
    }
    
}
