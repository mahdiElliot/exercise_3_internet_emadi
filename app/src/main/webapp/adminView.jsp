<%--
  Created by IntelliJ IDEA.
  User: Mahdy
  Date: 12/07/2020
  Time: 10:21 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="java.util.ArrayList" %>
<%@ page import="Main.Person" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin</title>
</head>
<body>
<%
    ArrayList<Person> users = (ArrayList<Person>) request.getAttribute("users");
    for (Person user : users) {
%>
<h1><%= user.getUsername() %></h1>
<%
    }
%>
</body>
</html>
