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
            <li><a href="/signOut">Sign Out</a></li>
        </ul>
    </nav>
</header>

<h2>Welcome to ${channelName}</h2>



<h2>Upload New Video</h2>
<p>Channel: <span id="channel-name">${channelName}</span></p>
<form id="upload-form" action="/channel" method="post" enctype="multipart/form-data">
    <label for="file">Choose a video file:</label><br>
    <input type="file" id="file" name="file"><br><br>
    <label for="thumbnail">Choose a thumbnail:</label><br>
    <input type="file" id="thumbnail" name="thumbnail"><br><br>
    <input type="submit" value="Upload" class="button">
</form>


<h2>Your Videos</h2>
<div id="video-list" class="video-grid">
    <#list videos as video>
        <div class="video-item">
            <video controls>
                <source th:src="${video.url}" type="video/mp4">
                Your browser does not support the video tag.
            </video>
            <p>${video.originalName}</p>
        </div>
    </#list>
</div>



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



    // Fetch videos using AJAX
    function fetchVideos() {
        fetch('/api/videos')
            .then(response => response.json())
            .then(videos => {
                // Clear existing content in the video list
                document.getElementById('video-list').innerHTML = '';

                // Iterate over fetched videos and create HTML elements to display them
                videos.forEach(video => {
                    const videoItem = document.createElement('div');
                    videoItem.classList.add('video-item');
                    videoItem.innerHTML = `
                    <h3>${video.originalName}</h3>
                    <video src="${video.url}" controls></video>
                `;
                    document.getElementById('video-list').appendChild(videoItem);
                });
            })
            .catch(error => {
                console.error('Error fetching videos:', error);
            });
    }

    window.addEventListener('load', fetchVideos);


</script>
</body>
</html>
