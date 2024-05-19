<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <title>My Videos</title>
    <link rel="stylesheet" href="/static/css/home.css">
</head>
<body>
<header>
    <div class="logo">
        <a href="/springtube" style="text-decoration: none; color: inherit;">SpringTube</a>
    </div>
    <nav>
        <ul>
            <li><a href="/springtube">Home</a></li>
            <li><a href="/profile">Profile</a></li>
            <li><a href="/signOut">Sign Out</a></li>
        </ul>
    </nav>
</header>

<h2>Welcome to ${channelName}</h2>


<h2>Upload New Video</h2>
<p>Channel: <span id="channel-name">${channelName}</span></p>
<form id="upload-form" action="/channel" method="post" enctype="multipart/form-data">
    <label for="file">Choose a video file:</label><br>
    <input type="file" id="file" name="file" required><br><br>


    <label for="thumbnail">Choose a thumbnail:</label><br>
    <input type="file" id="thumbnail" name="thumbnail" required><br><br>

    <label for="categories">Categories (comma-separated):</label><br>
    <input type="text" id="categories" name="categories" placeholder="e.g. Music, Sport, Education" required><br><br>
    <input type="submit" value="Upload" class="button">
</form>


<h2>My Videos</h2>
<div id="video-list" class="video-grid">
    <#list videos as video>
        <#if video_index % 5 == 0>
        <#--    <div class="video-row">-->
        </#if>
        <div class="video-item" data-video-id="${video.id}">
            <div class="video-container">
                <img class="thumbnail" src="/thumbnails/${video.thumbnailStorageName}" alt="Thumbnail">
                <video controls>
                    <source th:src="@{'/files/' + ${video.storageFileName}}" type="video/mp4">
                    Your browser does not support the video tag.
                </video>
            </div>
            <p>${video.originalName}</p>
        </div>
    </#list>
</div>



<script src="/static/js/upload.js"></script>
<script>
    document.getElementById('upload-form').addEventListener('submit', function(event) {

        // Create FormData object to send file data
        var formData = new FormData(this);

        // Send AJAX request to upload the video
        fetch('/channel', {
            method: 'POST',
            body: formData
        })
            .then(response => response.json())
            .then(data => {
                // Create video element and set attributes
                var video = document.createElement('video');
                video.setAttribute('controls', '');
                video.setAttribute('src', data.videoUrl);

                // Create img element for thumbnail and set attributes
                var thumbnail = document.createElement('img');
                thumbnail.setAttribute('src', data.thumbnailUrl);
                thumbnail.setAttribute('alt', 'Thumbnail');

                // Create div to hold video and thumbnail
                var videoContainer = document.createElement('div');
                videoContainer.classList.add('video-item');
                videoContainer.appendChild(video);
                videoContainer.appendChild(thumbnail);

                // Append the video container to the video list
                document.getElementById('video-list').appendChild(videoContainer);
            })
            .catch(error => console.error('Error:', error));
    });
</script>

</body>
</html>