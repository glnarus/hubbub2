<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${user == null}">
    <% response.sendRedirect("posts");%>    
</c:if>
<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Hubbub&trade; Write a Post</title>
    </head>
    <body>
        <h2>Submit a Post to Hubbub&trade;!</h2>
        <h3><font color="red">${flash}</font></h3>
        <br>
              <textarea rows="5" cols="60" name="newpost" 
                        form="postform">Enter post here...</textarea>                
        <form method="POST" id="postform" action="posts">
              <input type="submit" value="Submit Post">
        </form>        
        <a href="logout.jsp">Logout</a><br>
        <a href="posts">Back to timeline</a>
    </body>
</html>
