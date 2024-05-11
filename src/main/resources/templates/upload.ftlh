<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Videos</title>
    <link rel="stylesheet" href="/static/css/upload.css">
</head>
<body>
<header>
    <div class="logo">SpringTube</div>
    <nav>
        <ul>
            <li><a href="/springtube">Home</a></li>
            <li><a href="/updateChannel/">Update</a></li>
            <li><a href="#">Logout</a></li>
        </ul>
    </nav>
</header>

<h2>Welcome to ${channelName}</h2>

<h2>Your Videos</h2>
<div id="video-list">

</div>

<h2>Upload New Video</h2>
<p>Channel: <span id="channel-name">${channelName}</span></p>
<form id="upload-form" action="/channel" method="post" enctype="multipart/form-data">
    <label for="file">Choose a video file:</label><br>
    <input type="file" id="file" name="file"><br><br>
    <label for="thumbnail">Choose a thumbnail:</label><br>
    <input type="file" id="thumbnail" name="thumbnail"><br><br>
    <input type="submit" value="Upload">
</form>
<script src="/static/js/upload.js"></script>
<script>
    fetch('/channel')
        .then(response => response.json())
        .then(channels => {
            const select = document.getElementById('channelId');
            channels.forEach(channel => {
                const option = document.createElement('option');
                option.value = channel.id; // Assuming 'id' is the property
                option.textContent = channel.name; // Assuming 'name' is the property
                select.appendChild(option);
            });
        });
</script>
</body>
</html>
