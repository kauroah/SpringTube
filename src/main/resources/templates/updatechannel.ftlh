<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Update Channel Name</title>
    <link rel="stylesheet" href="/static/css/channel.css">
</head>
<body>
<header>
    <div class="logo">SpringTube</div>
    <nav>
        <ul>
            <li><a href="/springtube">Home</a></li>
            <li><a href="/profile">Profile</a></li>
            <li><a href="/updateChannel">Update</a></li>
            <li><a href="#">Logout</a></li>
        </ul>
    </nav>
</header>

<div class="update-channel-form">
    <form action="/updateChannel" method="post">
        <input type="hidden" name="channelId" value="${channel.id}">
        <label for="newName">New Channel Name:</label>
        <input type="text" id="newName" name="newName" value="${channel.name}">
        <input type="submit" value="Update Channel Name">
    </form>
</div>

</body>
</html>
