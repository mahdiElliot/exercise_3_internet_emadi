<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>user</title>
    <style>
        body {
            width: 100%;
            text-align: center;
            padding: 16px;
            display: flex;
            flex-direction: column;
            align-items: center;
            position: relative;
        }

        .container {
            position: absolute;
            min-width: 300px;
        }

        .info {
            margin-top: 24px;
        }

        .info p {
            border: gray solid 2px;
            padding: 8px 12px;
            border-radius: 8px;
        }

        .container button {
            width: 90%;
            padding-top: 8px;
            padding-bottom: 8px;
            background-color: lightblue;
            border-color: lightblue;
            border-radius: 12px;
            cursor: pointer;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>user's info</h2>
    <div class="info">
        <p>${user.username}</p>
        <p>${user.email}</p>
        <p>${user.role}</p>
    </div>
    <button>edit info</button>
</div>
</body>
</html>
