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
        <div class="reaction-buttons">
            <button class="reaction-button like-button" data-video-id="${mainVideo.id}" data-reaction="like">
                <i class="fas fa-thumbs-up"></i> Like
            </button>
            <button class="reaction-button dislike-button" data-video-id="${mainVideo.id}" data-reaction="dislike">
                <i class="fas fa-thumbs-down"></i> Dislike
            </button>
        </div>
    </div>

    <!-- Other Videos Section -->
    <div class="other-videos-container">
        <h2>Other Videos</h2>
        <div class="column">
            <#list otherVideos as video>
                <div class="col-md-4 mb-3">
                    <div class="other-video-item" data-video-id="${video.id}">
                        <div class="thumbnail">
                            <img src="${video.thumbnailUrl}" alt="Thumbnail">
                        </div>
                        <div class="video-info">
                            <h3>${video.originalName}</h3>
<#--                            <div class="reaction-buttons">-->
<#--                                <button class="reaction-button like-button" data-video-id="${video.id}" data-reaction="like">-->
<#--                                    <i class="fas fa-thumbs-up"></i> Like-->
<#--                                </button>-->
<#--                                <button class="reaction-button dislike-button" data-video-id="${video.id}" data-reaction="dislike">-->
<#--                                    <i class="fas fa-thumbs-down"></i> Dislike-->
<#--                                </button>-->
<#--                            </div>-->
                        </div>
                    </div>
                </div>
            </#list>


            <!-- Your other content here -->

    <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/js/all.min.js"></script>
    <script src="/static/js/video_detail.js"></script>
        </div>
    </div>
</form>

<script>

    // document.addEventListener("DOMContentLoaded", function () {
    //     const reactionButtons = document.querySelectorAll(".reaction-button");
    //
    //     reactionButtons.forEach(button => {
    //         button.addEventListener("click", function (event) { // Pass event as a parameter
    //             event.preventDefault(); // Prevent default form submission behavior
    //
    //             const videoId = button.getAttribute("data-video-id");
    //             const reactionType = button.getAttribute("data-reaction");
    //
    //             fetch("/reaction", {
    //                 method: "POST",
    //                 headers: {
    //                     "Content-Type": "application/json"
    //                 },
    //                 body: JSON.stringify({
    //                     videoId: videoId,  // Ensure proper key-value pair
    //                     reactionType: reactionType
    //                 })
    //             })
    //                 .then(response => {
    //                     if (!response.ok) {
    //                         throw new Error("Failed to update reaction");
    //                     }
    //                     return response.text();
    //                 })
    //                 .then(data => {
    //                     console.log(data);
    //                     // Optionally, you can update UI to reflect the reaction change
    //                 })
    //                 .catch(error => {
    //                     console.error("Error:", error);
    //                 });
    //         });
    //     });
    // });
    //

    document.addEventListener("DOMContentLoaded", function () {
        const reactionButtons = document.querySelectorAll(".reaction-button");

        reactionButtons.forEach(button => {
            button.addEventListener("click", function (event) {
                event.preventDefault();

                const videoId = button.getAttribute("data-video-id");
                const reactionType = button.getAttribute("data-reaction");

                fetch("/reaction", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify({
                        videoId: videoId,
                        reactionType: reactionType
                    })
                })
                    .then(response => {
                        if (!response.ok) {
                            throw new Error("Failed to update reaction");
                        }
                        return response.json(); // Parse response as JSON
                    })
                    .then(data => {
                        // Toggle like/dislike buttons
                        const oppositeReactionType = reactionType === 'like' ? 'dislike' : 'like';
                        const oppositeButton = document.querySelector(`.reaction-button[data-reaction=${oppositeReactionType}][data-video-id="${videoId}"]`);
                        if (oppositeButton) {
                            oppositeButton.classList.remove('active'); // Remove active class from opposite button
                        }

                        // Toggle active class for the clicked button
                        button.classList.toggle('active');

                        // Optionally, you can update UI to reflect the reaction change
                        console.log(data);
                    })
                    .catch(error => {
                        console.error("Error:", error);
                    });
            });
        });
    });

</script>
</body>
</html>