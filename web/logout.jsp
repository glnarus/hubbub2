<%--Simply invalidate the user session object and redirect to timeline--%>
<% 
    if (session.getAttribute("user") != null) {
        session.removeAttribute("user");
        session.invalidate();
    }
    response.sendRedirect ("posts");
    
%>
