<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Forgot Password</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/password.css">
</head>
<body>
<div class="container mt-5">
    <div class="logo-container">
        <img src="/static/images/logo.png" alt="SpringTube Logo">
    </div>

    <h2>Forgot Password</h2>
    <p>Please enter your email address to request a password reset:</p>
    <form action="/forgotPassword" method="post">
        <div class="mb-3">
            <label for="email" class="form-label">Email address:</label>
            <input type="email" class="form-control" id="email" name="email" required>
        </div>
        <button type="submit" class="btn btn-primary">Submit</button>
    </form>
</div>
</body>
</html>
