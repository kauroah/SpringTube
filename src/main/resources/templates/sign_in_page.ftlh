<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sign In</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css">
    <link rel="stylesheet" href="/static/css/signin.css">
</head>
<body>
<div class="container">
    <h1>Sign In</h1>
    <form action="/signIn" method="post">
        <div class="form-group">
            <label for="email">Email</label>
            <input type="email" id="email" name="email" required>
        </div>
        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" id="password" name="password" required>
        </div>
        <div class="form-check">
            <input type="checkbox" class="form-check-input" id="remember-me" name="remember-me">
            <a href="/forgotPassword">Forgot Password?</a>
            <label class="form-check-label" for="remember-me">Remember Me</label>
        </div>
        <button type="submit" class="btn btn-primary">Sign In</button>
    </form>

    <p class="signup-link mt-2">Don't have an account? <a href="/signUp">Sign Up</a></p>
</div>
<script src="/static/js/signin.js"></script>
</body>
</html>
