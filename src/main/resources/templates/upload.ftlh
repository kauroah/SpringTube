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
            <li><a href="/profile">Profile</a></li>
            <li><a href="/updateChannel/">Update</a></li>
            <li><a href="#">Logout</a></li>
        </ul>
    </nav>
</header>




<h2>Welcome to ${channelName}</h2>

<h2>Your Videos</h2>
<div id="video-list">
    <ul>
        <#list videos as video>
            <video width="320" height="240" controls>
                <source th:src="@{'/video?fileName=' + ${video.storageFileName}}" type="video/mp4">
                Your browser does not support the video tag.
            </video>
            <p th:text="${video.originalName}"></p>
        </#list>
    </ul>
</div>


<h2>Upload New Video</h2>
<p>Channel: <span id="channel-name">${channelName}</span></p>
<form id="upload-form" action="/channel" method="post" enctype="multipart/form-data">
    <label for="file">Choose a video file:</label><br>
    <input type="file" id="file" name="file"><br><br>
    <input type="submit" value="Upload">
</form>
<script src="/static/js/upload.js"></script>
<script>
    // Assuming you fetch the channels from your server
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
