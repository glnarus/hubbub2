<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Hubbub 1 -- Timeline</title>
        <style type="text/css">
            .postdiv {border: 1px solid teal; margin: 8px; padding: 8px;}
            .authorspan {margin: 8px; padding: 8px; font-size: larger;}
            .datespan {font-size: smaller;}
            .contentdiv {background-color: lightgray; margin: 8px; padding: 8px;}
            .footer {background-color: #fedcba;}
        </style>
    </head>
    <body>
        <img src="images/hubbub.png"/><br/>
        <h1>Welcome to Hubbub&trade;!</h1>
        <h2>Timeline</h2>
        <c:if test="${user != null}">
            <h3><font color = "gray"> User ${user}.username Logged In</font></h3>
            <a href ="newpost.jsp">Write new post</a><br>
            <a href ="logout.jsp">Logout</a>
        </c:if>
        <c:if test="${user == null}">
            <a href="login.jsp">Login</a>
        </c:if>
        <h3><font color ="teal">${flash}</font></h3>
        <c:forEach var="post" items="${posts}">
            <div class="postdiv">
                <span class="authorspan">${post.author}</span>
                <span class="datespan">(user since
                    <fmt:formatDate type="DATE" value="${post.author.joinDate}"/>)
                </span>
                <div class="contentdiv">
                    ${post.content}
                </div>
                <span class="datespan">Posted ${post.postDate}</span>
            </div>
        </c:forEach>
        <h3 class="footer">
            Copyright 2015 www.austincc.edu and www.bytecaffeine.com
        </h3>
    </body>
</html>
