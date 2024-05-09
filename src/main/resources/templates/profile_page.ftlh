<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Update Profile</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap" rel="stylesheet">
    <link href="https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css" rel="stylesheet" />
    <link rel="stylesheet" href="/static/css/profile.css" />
</head>
<body>
<div class="container">
    <h1>Update Profile</h1>
    <form action="/profile/update" method="post" th:object="${user}">
        <div class="form-group">
            <label for="firstName">First Name</label>
            <input type="text" id="firstName" name="firstName" th:value="${user.firstName}" required>
        </div>
        <div class="form-group">
            <label for="lastName">Last Name</label>
            <input type="text" id="lastName" name="lastName" th:value="${user.lastName}" required>
        </div>
        <div class="form-group">
            <label for="phone">Phone</label>
            <input type="text" id="phone" name="phone" th:value="${user.phone}">
        </div>
        <div class="form-group">
            <label for="password">Password</label>
            <input type="text" id="password" name="password" th:value="${user.password}">
        </div>
        <button type="submit">Update Profile</button>
    </form>

</div>
<script src="/static/js/profile.js"></script>
</body>
</html>
