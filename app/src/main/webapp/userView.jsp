<%@ page import="Main.model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User</title>
    <link rel="stylesheet" href="style.css">
    <link rel='stylesheet' href='webjars/bootstrap/4.5.3/css/bootstrap.min.css'>
    <script src="webjars/jquery/3.5.1/jquery.min.js"></script>
    <script src="script.js"></script>

    <script>
        const pages = {
            "Profile": `
                <div>
                    <% User me = (User) request.getAttribute("self"); %>
                     <ul class="list-group">
                      <li class="list-group-item">
                        <b>Name:</b> <%= me.getUsername() %>
                      </li>
                    </ul>
                    <ul class="list-group">
                      <li class="list-group-item">
                        <b>Email:</b> <%= me.getEmail() %>
                      </li>
                    </ul>
                    <ul class="list-group">
                      <li class="list-group-item">
                        <b>Role:</b> <%= me.getRole() %>
                      </li>
                    </ul>

                </div>
            `,
        }
    </script>
</head>
<body>
<div class="container">
    <ul class="side-bar">
        <li class="side-bar__header">User Panel</li>
        <li>Profile</li>
        <li><a href="${pageContext.request.contextPath}/logout">logout</a></li>
    </ul>

    <div class="content">

    </div>
</div>
</body>
</html>
