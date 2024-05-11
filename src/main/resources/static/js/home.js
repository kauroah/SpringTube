
const videoItems = document.querySelectorAll('.video-item');

videoItems.forEach(videoItem => {
    videoItem.addEventListener('click', function() {
        const videoId = this.getAttribute('data-video-id');
        window.location.href = '/files/' + videoId; // Redirect to separate URL for the clicked video
    });
});



