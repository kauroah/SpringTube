let refetchVideos = () => {
    $.ajax({
        type: "GET",
        url: "/static/files/", // Update the URL to the endpoint that fetches videos
        success: function(response) {
            // Process the response here
            // For example, you can render the videos on the page
            renderVideos(response, $('#video-container'));
        },
        // Specify the data type we expect to receive
        dataType: "json",
        contentType: "application/json"
    });
};

// Function to render videos on the page
function renderVideos(videos, container) {
    // Clear the existing videos in the container
    container.empty();

    // Iterate through the list of videos and render each one
    videos.forEach(video => {
        // Create a new video element
        let videoElement = $('<video controls></video>');

        // Set the source of the video
        videoElement.attr('src', video.url); // Assuming 'url' is the property in the video object

        // Append the video element to the container
        container.append(videoElement);
    });
}


const videoItems = document.querySelectorAll('.video-item');

videoItems.forEach(videoItem => {
    videoItem.addEventListener('click', function() {
        const videoId = this.getAttribute('data-video-id');
        window.location.href = '/files/' + videoId; // Redirect to separate URL for the clicked video
    });
});