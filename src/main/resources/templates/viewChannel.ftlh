<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${channel.name} - Channel</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
    <link rel="stylesheet" href="/static/css/viewChannel.css">
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

<div class="container">
    <div class="channel-info">
        <h1><span class="creator-name">Channel Name:
            </span> ${channel.name}</h1>
        <p><span>Creator Name:
            </span> ${channel.user.firstName}</p>
        <p><span>Email:
            </span> ${channel.user.email}</p>
        <p><span>Phone:
            </span> ${channel.user.phone}</p>
    </div>


    <hr class="separator">

    <div class="videos-section">
        <div class="row">
            <#list videos as video>
                <div class="col-md-4 mb-3">
                    <div class="video-item" data-video-id="${video.id}">
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
</div>
<script src="/static/js/video_detail.js"></script>
</body>
</html>
