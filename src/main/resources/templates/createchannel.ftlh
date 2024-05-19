<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Create Channel</title>
    <link rel="stylesheet" href="/static/css/channel.css">
</head>
<body>
<div class="container">
    <h2>Create Channel</h2>
    <form action="/createChannel" method="post">
        <label for="name">Channel Name:</label>
        <input type="text" id="name" name="name">
        <input type="submit" value="Create Channel">
    </form>
</div>
</body>
</html>
