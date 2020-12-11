<%--
  Created by IntelliJ IDEA.
  User: Mahdy
  Date: 12/07/2020
  Time: 10:21 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Main.model.User" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin</title>
    <link rel="stylesheet" href="style.css">
    <link rel='stylesheet' href='webjars/bootstrap/3.2.0/css/bootstrap.min.css'>
    <script src="webjars/jquery/1.11.1/jquery.min.js"></script>
    <script src="script.js"></script>

    <script>
        function removeUser(user) {
            console.log(user)
        }

        const pages = {
            "Users List": `<ul class="list-group">
            <%

                ArrayList<User> users = (ArrayList<User>) request.getAttribute("users");
                for (User user : users) {
            %>
            <li class="list-group-item item"><%= user.getUsername() %>
            <% if(!user.getUsername().equals("admin")) { %>
            <button onclick="removeUser('<%= user.getUsername() %>')" class="btn"><span class="glyphicon glyphicon-trash"></span></button>
            <% } %>
            </li>
            <% } %>
        </ul>`,
            "Add New User": "hi"
        }
    </script>
</head>
<body>
<div class="container">
    <ul class="side-bar">
        <li class="side-bar__header">Admin Panel</li>
        <li>Users List</li>
        <li>Add New User</li>
    </ul>

    <div class="content">

    </div>
</div>
</body>
</html>
