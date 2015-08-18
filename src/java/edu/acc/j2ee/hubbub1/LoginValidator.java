package edu.acc.j2ee.hubbub1;

import org.apache.commons.lang3.StringUtils;

/**Validates username, password.  Valid usernames have no <>'"& characters
 * while valid passwords must also not have those characters plus they must
 * be between 6 and 15 characters.
 */
public class LoginValidator {
    private static final char[] forbiddenChars = {'<','>','\'','\"','&'};
    
    public static boolean isValid (String username, String password){
        if ((password.length() >= 6) && (password.length() <= 15)) {
            boolean userBad = StringUtils.containsAny(username, forbiddenChars);
            boolean passBad = StringUtils.containsAny(password, forbiddenChars);            
            return (!passBad) && (!userBad);            
        }
        else
            return false;      
        
    }
    
}
