    <!DOCTYPE html>
    <html lang="en">
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
            <div class="subscribe-button">
                <form id="subscribeForm" action="/subscribe/${mainVideo.channel.id}" method="post">
                    <button type="submit" id="subscribeButton">Subscribe</button>
                </form>
                <form id="unsubscribeForm" action="/unsubscribe/${mainVideo.channel.id}" method="post" style="display: none;">
                    <button type="submit" id="unsubscribeButton">Unsubscribe</button>
                </form>
            </div>

        </div>

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
                            </div>
                        </div>
                    </div>
                </#list>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/js/all.min.js"></script>
        <script src="/static/js/video_detail.js"></script>
            </div>
        </div>
    </form>

    <script>

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

        document.addEventListener("DOMContentLoaded", function () {
            const subscribeForm = document.getElementById("subscribeForm");
            const unsubscribeForm = document.getElementById("unsubscribeForm");
            const subscribeButton = document.getElementById("subscribeButton");
            const unsubscribeButton = document.getElementById("unsubscribeButton");

            console.log("Subscribe button:", subscribeButton);
            console.log("Unsubscribe button:", unsubscribeButton);

            // Function to update UI based on subscription status
            function updateUI(subscribed) {
                if (subscribed) {
                    showUnsubscribeForm();
                } else {
                    showSubscribeForm();
                }
            }

            // Check if user is subscribed or not
            fetch(`/checkSubscription/${mainVideo.channel.id}`)
                .then(response => response.json())
                .then(data => {
                    updateUI(data.subscribed); // Update UI based on subscription status
                })
                .catch(error => {
                    console.error("Error:", error);
                });

            // Subscribe button click event
            subscribeButton.addEventListener("click", function (event) {
                event.preventDefault(); // Prevent default form submission behavior
                fetch(`/subscribe/${mainVideo.channel.id}`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    }
                })
                    .then(response => {
                        if (response.ok) {
                            return response.json();
                        }
                        throw new Error('Failed to subscribe');
                    })
                    .then(data => {
                        console.log(data.message);
                        updateUI(true); // Update UI to reflect subscription
                    })
                    .catch(error => {
                        console.error("Error:", error);
                    });
            });

            // Unsubscribe button click event
            unsubscribeButton.addEventListener("click", function (event) {
                event.preventDefault(); // Prevent default form submission behavior
                fetch(`/unsubscribe/${mainVideo.channel.id}`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    }
                })
                    .then(response => {
                        if (response.ok) {
                            return response.json();
                        }
                        throw new Error('Failed to unsubscribe');
                    })
                    .then(data => {
                        console.log(data.message);
                        updateUI(false); // Update UI to reflect unsubscription
                    })
                    .catch(error => {
                        console.error("Error:", error);
                    });
            });

            function showSubscribeForm() {
                subscribeForm.style.display = "block";
                unsubscribeForm.style.display = "none";
            }

            function showUnsubscribeForm() {
                subscribeForm.style.display = "none";
                unsubscribeForm.style.display = "block";
            }
        });


    </script>
    </body>
    </html>