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
    </head>
    <body>

    <header>
        <div class="logo">SpringTube</div>

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
            <video id="mainVideoPlayer" controls autoplay width="900" height="240">
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
            <div class="subscribe-button">
                <form id="subscribeForm" action="/subscribe/${mainVideo.channel.id}" method="post">
                    <p>Channel: ${mainVideo.channel.name}</p>
                    <button type="submit" id="subscribeButton">Subscribe</button>
                </form>
                <form id="unsubscribeForm" action="/unsubscribe/${mainVideo.channel.id}" method="post" style="display: none;">
                    <button type="submit" id="unsubscribeButton">Unsubscribe</button>
                </form>
            </div>


        </div>
    </form>

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
    <script src="/static/js/video_detail.js"></script>
    </body>
    </html>