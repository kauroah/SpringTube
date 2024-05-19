<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reset Password</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto">
    <link rel="stylesheet" href="/static/css/epassword.css">
</head>
<body>
<div class="container">
    <div class="logo-container">
        <img src="/static/images/logo.png" alt="SpringTube Logo">
    </div>
    <h2>Password Reset</h2>
    <p>You've requested a password reset for your account. Click the link below to reset your password:</p>
    <p><a href="${token}">Reset Password</a></p>
    <p>If you didn't request this, you can ignore this email.</p>
    <p>This link will expire in 1 hour.</p>
</div>
</body>
</html>
