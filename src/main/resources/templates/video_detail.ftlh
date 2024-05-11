<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Video Detail</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
    <!-- Add your other stylesheets here -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
    <link rel="stylesheet" href="/static/css/home.css">
</head>
<body>
<!-- Main Video Player Section -->
<form action="/play/${mainVideo.id}" method="post">
<div class="main-video-container">
    <h2>Main Video</h2>
    <video id="mainVideoPlayer" controls autoplay>

        <source src="/files/${mainVideo.storageFileName}" type="${mainVideo.type}">
        Your browser does not support the video tag.
    </video>
</div>

<!-- Other Videos Section -->
<div class="other-videos-container">
    <h2>Other Videos</h2>
    <div class="row">
        <#list otherVideos as video>
            <div class="col-md-4 mb-3">
                <div class="other-video-item" data-video-id="${video.storageFileName}">
                    <div class="thumbnail">
                        <img src="/files/${video.thumbnailUrl}" alt="Thumbnail">
                    </div>
                    <div class="video-info">
                        <h3>${video.originalName}</h3>
                        <!-- Add any additional video info if needed -->
                    </div>
                </div>
            </div>
        </#list>
    </div>
</div>

<!-- Your other content here -->

<script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/js/all.min.js"></script>
<script src="/static/js/video_detail.js"></script>
</form>
</body>
</html>
