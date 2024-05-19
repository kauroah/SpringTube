document.addEventListener("DOMContentLoaded", function () {
    // Function to handle navigation to another video
    function handleVideoNavigation(itemsSelector) {
        const videoItems = document.querySelectorAll(itemsSelector);

        videoItems.forEach(item => {
            item.addEventListener("click", function () {
                const videoId = item.getAttribute("data-video-id");
                const videoUrl = `/play/` + videoId;
                window.location.href = videoUrl;
            });
        });
    }

    // Handle navigation for other videos
    handleVideoNavigation(".other-video-item");
    handleVideoNavigation(".video-item");



    // Handle reaction buttons
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

    // Handle play for video items
    const videoItems = document.querySelectorAll(".other-video-item");

    videoItems.forEach(item => {
        item.addEventListener("click", function () {
            const videoId = item.getAttribute("data-video-id");
            const video = document.querySelector(`video[data-video-id="${videoId}"]`);

            if (video) {
                video.play();
            }
        });
    });


    // Handle comment form submission
    const commentForm = document.getElementById('commentForm');

    if (commentForm) {
        commentForm.addEventListener('submit', function(event) {
            event.preventDefault();
            const formData = new FormData(event.target);

            fetch('/comment', {
                method: 'POST',
                body: formData
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    return response.json();
                })
                .then(comment => {
                    const commentsContainer = document.getElementById('commentsContainer');
                    const commentElement = document.createElement('div');
                    commentElement.className = 'comment';
                    commentElement.innerHTML = `<p><strong>${comment.userName}</strong>: ${comment.text}</p>`;
                    commentsContainer.appendChild(commentElement);
                    document.getElementById('text').value = '';
                })
                .catch(error => console.error('Error:', error));
        });
    }
});