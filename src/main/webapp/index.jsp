<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Academic Project Management - Home</title>
    <style>
        body, html {
            height: 100%;
            margin: 0;
            font-family: Arial, Helvetica, sans-serif;
        }

        .bg {
            background-image: url('images/hbg.jpg');
            height: 100%;
            background-position: center;
            background-repeat: no-repeat;
            background-size: cover;
        }

        .navbar {
            overflow: hidden;
            background-color: #333;
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 0 10px;
        }

        .navbar .nav-left, .navbar .nav-right {
            display: flex;
            align-items: center;
        }

        .navbar a {
            display: block;
            color: #f2f2f2;
            text-align: center;
            padding: 14px 16px;
            text-decoration: none;
        }

        .navbar a:hover {
            background-color: #ddd;
            color: black;
        }

        .container {
            position: absolute;
            left: 50%;
            top: 50%;
            transform: translate(-50%, -50%);
            text-align: center;
        }
    </style>
</head>
<body>
    <div class="bg">
        <div class="navbar">
            <div class="nav-left">
                <a href="index.jsp">Home</a>
                <a href="login.jsp">Login</a> <!-- Redirect to registration page first -->
                <a href="register.jsp">Register</a>
                <a href="About Us.jsp">About Us</a>
            </div>
            <div class="nav-right">
                <a href="logout.jsp">Logout</a>
            </div>
        </div>
        <div class="container">
            <h1>Welcome to Academic Project Management</h1>
        </div>
    </div>
</body>
</html>