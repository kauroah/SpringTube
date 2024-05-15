// async function fetchMyVideos() {
//     const response = await fetch('/channel');
//     const data = await response.json();
//     const videoList = document.getElementById('video-list');
//     videoList.innerHTML = '';
// }
// // Fetch user's uploaded videos
// fetchMyVideos();
//
//
//
//
document.addEventListener('DOMContentLoaded', function() {
    const videoItems = document.querySelectorAll('.video-item');

    videoItems.forEach(videoItem => {
        videoItem.addEventListener('click', function() {
            const videoId = this.getAttribute('data-video-id');
            window.location.href = '/play/' + videoId; // Redirect to the video detail page
        });
    });
});
