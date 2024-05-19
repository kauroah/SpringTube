// Function to handle click events on video items
const handleVideoItemClick = (event) => {
    const videoId = event.currentTarget.getAttribute('data-video-id');
    window.location.href = '/play/' + videoId; // Redirect to the video detail page
};

// Attach event listeners to video items
document.addEventListener('DOMContentLoaded', function() {
    const videoItems = document.querySelectorAll('.video-item');

    videoItems.forEach(videoItem => {
        videoItem.addEventListener('click', handleVideoItemClick);
    });
});
