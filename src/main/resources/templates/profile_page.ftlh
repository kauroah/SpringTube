<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Profile</title>
    <link rel="stylesheet" href="/static/css/profile.css">
</head>
<body>
<header>
    <div class="logo">
        <a href="/springtube" style="text-decoration: none; color: inherit;">SpringTube</a>
    </div>

    <nav>
        <ul>
            <li><a href="/springtube">Home</a></li>
            <li><a href="/contact">Contact Us</a></li>
            <li><a href="/profile">Profile</a></li>
            <li><a href="/signOut">Sign Out</a></li>
        </ul>
    </nav>
</header>

<h2>User Profile</h2>
<div class="profile-details">
    <p><strong>First Name:</strong> ${user.firstName}</p>
    <p><strong>Last Name:</strong> ${user.lastName}</p>
    <p><strong>Email:</strong> ${user.email}</p>
    <p><strong>Phone:</strong> ${user.phone}</p>
</div>

<h2>Update Profile</h2>
<form action="/profile/update" method="post">
    <label for="firstName">First Name:</label><br>
    <input type="text" id="firstName" name="firstName" value="${user.firstName}"><br><br>

    <label for="lastName">Last Name:</label><br>
    <input type="text" id="lastName" name="lastName" value="${user.lastName}"><br><br>

    <label for="phone">Phone:</label><br>
    <input type="text" id="phone" name="phone" value="${user.phone}"><br><br>

    <label for="password">Password:</label><br>
    <input type="password" id="password" name="password"><br><br>

    <input type="submit" value="Update Profile">
</form>

</body>
</html>