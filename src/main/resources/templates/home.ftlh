<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SpringTube</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;700&display=swap" rel="stylesheet">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
    <link rel="stylesheet" href="/static/css/home.css">
</head>

<body>
<form action="springtube" method="get">
    <header>
        <div class="logo">SpringTube</div>
        <div class="search-bar">
            <input type="text" placeholder="Search videos">
            <i class="fas fa-search"></i>
        </div>
        <nav>
            <ul>
                <li><a href="/springtube">Home</a></li>
                <li><a href="/createChannel">Channel</a></li>
                <li><a href="/profile">Profile</a></li>
                <li><a href="/signIn">Sign In</a></li>

            </ul>
        </nav>
    </header>

    <main>
        <#list videos as video>
        <form action="/springtube" method="get">
            <#if video_index % 5 == 0>
                <div class="video-row">
            </#if>
            <div class="video-item" data-video-id="${video.id}">
                <div class="video-container">
                    <img class="thumbnail" src="${video.thumbnailUrl}" alt="Thumbnail">
<#--                    <video th:src="@{'/files/' + ${video.originalName}}" controls></video>-->
                    <video controls>
                        <source th:src="@{'/files/' + ${video.storageFileName}}" type="video/mp4">
                        Your browser does not support the video tag.
                    </video>
                </div>
                <div class="video-actions">
<#--                    <button class="like-btn"><i class="fas fa-thumbs-up"></i> Like</button>-->
<#--                    <button class="dislike-btn"><i class="fas fa-thumbs-down"></i> Dislike</button>-->
                </div>
                <h4>${video.originalName}</h4>

            </div>
            <#if (video_index + 1) % 5 == 0 || (video_index + 1) == videos?size>
                </div>
            </#if>
        </#list>
        </form>
    </main>

<#--    <main>-->
<#--        <form action="/springtube" method="get">-->
<#--            <#list videos as video>-->
<#--                <div class="video-item" data-video-id="${video.id}">-->
<#--                    <div class="video-container">-->
<#--                        <img class="thumbnail" src="${video.thumbnailUrl}" alt="Thumbnail">-->
<#--                        <video controls>-->
<#--                            <source th:src="@{'/files/' + ${video.storageFileName}}" type="video/mp4">-->
<#--                            Your browser does not support the video tag.-->
<#--                        </video>-->
<#--                    </div>-->
<#--                    <h4>${video.originalName}</h4>-->
<#--                </div>-->
<#--            </#list>-->
<#--        </form>-->
<#--    </main>-->
</form>
<script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/js/all.min.js"></script>
<script src="/static/js/home.js"></script>
<script>
</script>
</body>
</html>