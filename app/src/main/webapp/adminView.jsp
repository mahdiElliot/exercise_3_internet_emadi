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
    <link rel='stylesheet' href='webjars/bootstrap/4.5.3/css/bootstrap.min.css'>
    <script src="webjars/jquery/3.5.1/jquery.min.js"></script>
    <link rel='stylesheet' href='webjars/bootstrap-glyphicons/bdd2cbfba0/css/bootstrap-glyphicons.css'>
    <script src="script.js"></script>

    <script>
                const getUsersList = () => {
            $.ajax({
                url: '${pageContext.request.contextPath}/users',
                type: "GET",
            })
                .done(updateUserList)
                .fail(() => {
                    const error = $("#error")
                    error[0].classList.remove("fade")
                });
        }

        getUsersList()



        const onSubmit = function (event) {
            const username = $("input[name='username']").val();
            const password = $("input[name='password']").val();
            const email = $("input[name='email']").val();
            const role = $("select[name='role']").val();

            $.ajax({
                url: '${pageContext.request.contextPath}/users',
                type: "POST",
                data: {username, password, email, role},
            })
                .done(() => {
                    $("form").trigger("reset")
                    const success = $("#success")
                    success.toggleClass("fade")
                })
                .fail(() => {
                    const error = $("#error")
                    error[0].classList.remove("fade")
                });

            event.preventDefault()
        }

        function removeUser(user) {
            $.ajax({
                url: `${pageContext.request.contextPath}/users/` + user,
                type: "DELETE",
                data: {user},
                contentType:'application/json',
            })
                .done(() => {
                    const success = $("#success")
                    getUsersList()
                    success[0].innerHTML = "User Removed successfully"
                    success[0].classList.remove("fade")
                })
                .fail(() => {
                    const error = $("#error")
                    error[0].innerHTML = "User Removing failed"
                    error[0].classList.remove("fade")
                });
        }

    </script>

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
            "Users List": `
                <ul class="list-group users"></ul>
            `,
            "Add New User": `
                <form onsubmit="return onSubmit(event)" id="form" method="POST">
                    <div class="form-group">
                        <label class="control-label" for="username" >
                            Username
                        </label>
                        <input id="username" class="form-control" required type="text" name="username">
                    </div>
                    <div class="form-group">
                        <label class="control-label" for="password" >
                            Password
                        </label>
                        <input id="password" class="form-control" required type="password" name="password">
                    </div>
                    <div class="form-group">
                        <label class="control-label" for="email" >
                            Email
                        </label>
                        <input id="email" class="form-control" required type="email" name="email">
                    </div>
                    <div class="form-group">
                        <label class="control-label" for="role" >
                            Role
                        </label>
                        <select required name="role" class="form-control">
                            <option value="ADMIN">Admin</option>
                            <option value="USER">User</option>
                        </select>
                    </div>
                    <input class="btn btn-outline-primary" type="submit" value="Signup"/>
                    <br/>
                    <br/>
                    <p class="alert alert-danger alert-dismissible fade " role="alert" id="error">User creation failed</p>
                    <p class="alert alert-success alert-dismissible fade " role="alert" id="success">User Created Successfully.</p>
                </form>
            `
        }
    </script>
</head>
<body>
<div class="container">
    <ul class="side-bar">
        <li class="side-bar__header">Admin Panel</li>
        <li>Profile</li>
        <li>Users List</li>
        <li>Add New User</li>
        <li><a href="${pageContext.request.contextPath}/logout">logout</a></li>
    </ul>

    <div class="content">

    </div>

    <div>

    </div>
</div>
</body>
</html>
