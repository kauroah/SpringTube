<!DOCTYPE html>
<html>
<head>
    <title>Error - SpringTube</title>
    <style>
        body {
            font-family: 'Roboto', sans-serif;
            margin: 0;
            padding: 0;
            color: #fff;
            background: linear-gradient(to bottom, #45A29E 5%, #000000 45%);
            background-attachment: fixed;
            min-height: 100vh;
        }

        .container {
            margin: 0 auto;
            padding: 20px;
            max-width: 600px;
            text-align: center;
        }

        h1 {
            color: #ff5252;
            margin-top: 0;
        }

        .logo-container {
            margin-bottom: 20px;
        }

        .logo-container img {
            width: 300px;
        }

        p {
            margin-top: 20px;
            font-size: 50px;
        }

        .links a {
            color: #fff;
            text-decoration: none;
            padding: 5px 10px;
            border: 1px solid #fff;
            border-radius: 5px;
            margin-right: 10px;
            transition: background-color 0.3s, color 0.3s;
        }

        .links a:hover {
            background-color: #fff;
            color: #000;
        }

        /* Style the dropdown container */
        .dropdown {
            position: relative;
            display: inline-block;
        }

        /* Style the link inside the dropdown container */
        .dropdown > a {
            text-decoration: none;
            color: black;
            padding: 8px;
            display: block;
        }

        /* Dropdown content (hidden by default) */
        .dropdown-content {
            display: none;
            position: absolute;
            background-color: #f9f9f9;
            min-width: 160px;
            box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
            z-index: 1;
        }

        /* Links inside the dropdown */
        .dropdown-content a {
            color: black;
            padding: 12px 16px;
            text-decoration: none;
            display: block;
        }

        /* Change color of dropdown links on hover */
        .dropdown-content a:hover {
            background-color: #f1f1f1;
        }

        /* Show the dropdown menu on hover */
        .dropdown:hover .dropdown-content {
            display: block;
        }

    </style>
</head>
<body>
<div class="container">
    <h1>Error</h1>
    <p>${errorMessage}</p>
    <div class="logo-container">
        <img src="/static/images/logo.png" alt="SpringTube Logo">
    </div>
    <div class="links">
        <a href="/">Go To Home</a>
        <div class="dropdown">
            <a href="#">Options</a>
            <div class="dropdown-content">
                <a href="/signUp">Create another account!</a>
                <a href="/forgotPassword">Reset password</a>
                <a href="/contact">Contact Support</a>
            </div>
        </div>
    </div>
</div>
</body>
</html>
