<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Video Detail</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
    <link rel="stylesheet" href="/static/css/home.css">
    <script
            src="https://code.jquery.com/jquery-3.5.1.js"
            integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc="
            crossorigin="anonymous"></script>
</head>
<body>

<header>
    <div class="logo">
        <a href="/springtube" style="text-decoration: none; color: inherit;">SpringTube</a>
    </div>


    <div class="search-bar">
        <form action="/springtube" method="get" onsubmit="return onSubmit()">
            <input id="searchInput" type="text" name="query" placeholder="Search videos">
            <button type="submit"><i class="fas fa-search"></i></button>
        </form>
    </div>
    <nav>
        <ul>
            <li><a href="/springtube">Home</a></li>
            <li><a href="/profile">Profile</a></li>
            <li><a href="/signOut">Sign Out</a></li>
        </ul>
    </nav>
</header>

<form action="/play/${mainVideo.id}" method="post">
    <div class="main-video-container">
        <video id="mainVideoPlayer" controls autoplay width="1300" height="700">
            <source src="/files/${mainVideo.storageFileName}" type="${mainVideo.type}">
            Your browser does not support the video tag.
        </video>
        <div class="video-info">
            <h2>${mainVideo.originalName}</h2>
        </div>

        <div class="reaction-buttons">
            <button class="reaction-button like-button" data-video-id="${mainVideo.id}" data-reaction="like">
                <i class="fas fa-thumbs-up"></i> Like
            </button>
            <button class="reaction-button dislike-button" data-video-id="${mainVideo.id}" data-reaction="dislike">
                <i class="fas fa-thumbs-down"></i> Dislike
            </button>
        </div>

        <#--            <div class="subscribe-button">-->
        <#--                <div class="subscribe-unsubscribe-container">-->
        <#--                    <p>Channel: <a href="/channel/${mainVideo.channel.id}">${mainVideo.channel.name}</a></p>-->
        <#--                    <form id="subscribeForm" action="/subscribe/${mainVideo.channel.id}" method="post">-->
        <#--                        <button type="submit" id="subscribeButton">Subscribe</button>-->
        <#--                    </form>-->
        <#--                    <form id="unsubscribeForm" action="/unsubscribe/${mainVideo.channel.id}" method="post">-->
        <#--                        <button type="submit" id="unsubscribeButton">Unsubscribe</button>-->
        <#--                    </form>-->
        <#--                </div>-->
        <#--            </div>-->
        <div class="subscribe-button">
            <div class="subscribe-unsubscribe-container">
                <p>Channel: <a href="/channel/${mainVideo.channel.id}">${mainVideo.channel.name}</a></p>
                <p>Followers: <span id="followerCount">${channelDto.subscriberCount}</span></p>
                <button id="subscribeButton">Subscribe</button>
                <button id="unsubscribeButton">Unsubscribe</button>
            </div>
        </div>
    </div>
</form>


<div class="comments-section" style="width: 70%;">
    <h3>Comments</h3>
    <form id="commentForm" action="/comment" method="post">
        <input type="hidden" name="videoId" id="videoId" value="${mainVideo.id}">
        <input type="text" name="text" id="text" placeholder="Write your comment here..." style="width: calc(70% - 120px); background-color: black; color: white; border: 1px solid #ccc; padding: 8px;">
        <button type="submit" class="comment-submit-button">Submit</button>
    </form>

    <div id="commentsContainer" class="comment-list">
        <#list comments as comment>
            <div class="comment">
                <p><strong>${comment.user.firstName}</strong>: ${comment.text}</p>
            </div>
        </#list>
    </div>
</div>


<div class="other-videos-container">
    <div class="column">
        <#list otherVideos as video>
            <div class="col-md-4 mb-3">
                <div class="other-video-item" data-video-id="${video.id}">
                    <div class="thumbnail">
                        <img src="${video.thumbnailUrl}" alt="Thumbnail">
                    </div>
                    <div class="video-info">
                        <h3>${video.originalName}</h3>
                    </div>
                </div>
            </div>
        </#list>
    </div>
</div>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="/static/js/video_detail.js"></script>
<script>
    $("#subscribeButton").on("click", function(event) {
        event.preventDefault();
        $.ajax({
            url: `/subscribe/${mainVideo.channel.id}`,
            type: "POST",
            success: function(response) {
                $("#followerCount").text(response.subscriberCount);
            },
            error: function(xhr, status, error) {
                console.error("Error subscribing to the channel:", xhr.responseText);
                alert("Error subscribing to the channel.");
            }
        });
    });

    $("#unsubscribeButton").on("click", function(event) {
        event.preventDefault();
        $.ajax({
            url: `/unsubscribe/${mainVideo.channel.id}`,
            type: "POST",
            success: function(response) {
                $("#followerCount").text(response.subscriberCount);
            },
            error: function(xhr, status, error) {
                console.error("Error unsubscribing from the channel:", xhr.responseText);
                alert("Error unsubscribing from the channel.");
            }
        });
    });

</script>
</body>
</html>