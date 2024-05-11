// Update your existing JavaScript code to handle video clicks
document.addEventListener('DOMContentLoaded', function() {
    const videoItems = document.querySelectorAll('.video-item');

    videoItems.forEach(videoItem => {
        videoItem.addEventListener('click', function() {
            const videoId = this.getAttribute('data-video-id');
            window.location.href = '/play/' + videoId; // Redirect to the video detail page
        });
    });
});
