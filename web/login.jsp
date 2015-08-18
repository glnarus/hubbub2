<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${user != null}">
    <% response.sendRedirect("posts"); %>    
</c:if>    
<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Hubbub&trade; Login</title>
    </head>
    <body>
        <h1><font color="red">${flash}</font></h1>
        <form method="POST" action="posts">
            <table>
                <tr><td>User Name: </td><td>
                    <input type="text" name="username"/></td></tr>
                <tr><td>Password: </td><td>
                    <input type="password" name="password"/></td></tr>
                <tr><td colspan="2"><center>
                    <input type="submit" value="Login!"/></center></td></tr>
            </table>                           
        </form>
        <a href="registration.jsp">New User Registration</a><br>
        <a href="posts">View timeline as a guest</a>
    </body>
</html>
