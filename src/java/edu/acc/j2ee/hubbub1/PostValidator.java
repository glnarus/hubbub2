package edu.acc.j2ee.hubbub1;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

/**Ensures that special characters like <>, ', ", and & are properly escaped
 * so they appear as plain text which can be safely and correctly interpreted
 * by the browser or database.
 */
public class PostValidator {
    
    public static String escapeSafe (String post){
        if (post == null) return null; 
        post = post.replace(";",":"); //semi-colons are problematic, so let's
              //replace them with colons for now.  Do users really want semi-colon support?
        post = StringEscapeUtils.escapeHtml4(post);
        //that takes care of everything but single ' and & which
        //may still be dangerous
        post = post.replace("/'", "sq;");
        post = post.replace("&", "andsign;");
        return post;
    }
    
    public static String unEscapeSafe (String post) {
        if (post == null) return null;
        post = post.replace("sq;","/'");
        post = post.replace("andsign;","&");
        return post;
    }
}
