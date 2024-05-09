<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reset Password</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto">
    <link rel="stylesheet" href="/static/css/password.css">
</head>
<body>
<div class="container">
    <div class="logo-container">
        <img src="/static/images/logo.png" alt="SpringTube Logo">
    </div>
    <h2>Reset Password</h2>
    <form action="/resetPassword" method="post">
        <input type="hidden" name="token" value="${token}">
        <div>
            <label for="password">New Password:</label>
            <input type="password" id="password" name="password" required>
        </div>
        <div>
            <label for="confirm_password">Confirm New Password:</label>
            <input type="password" id="confirm_password" name="confirm_password" required>
        </div>
        <button type="submit">Reset Password</button>
    </form>
</div>
</body>
</html>
