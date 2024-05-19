<!DOCTYPE html>
<html>
<head>
    <title>Error - SpringTube</title>
    <link rel="stylesheet" href="/static/css/error.css">
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
