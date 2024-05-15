// async function fetchMyVideos() {
//     const response = await fetch('/channel');
//     const data = await response.json();
//     const videoList = document.getElementById('video-list');
//     videoList.innerHTML = '';
// }
// // Fetch user's uploaded videos
// fetchMyVideos();


document.addEventListener('DOMContentLoaded', function() {
    const videoItems = document.querySelectorAll('.video-item');

    videoItems.forEach(videoItem => {
        videoItem.addEventListener('click', function() {
            const videoId = this.getAttribute('data-video-id');
            window.location.href = '/play/' + videoId; // Redirect to the video detail page
        });
    });
});


function fetchMyVideos() {
    $.ajax({
        url: '/channel',
        method: 'GET',
        dataType: 'json',
        success: function(data) {
            const videoList = $('#video-list');
            videoList.empty(); // Clear the video list before adding new videos

            // Handle the data and update the video list accordingly
            // For example:
            data.forEach(video => {
                const videoItem = $('<div class="video-item"></div>');
                videoItem.text(video.title); // Assuming 'title' is a property of each video object
                videoList.append(videoItem);
            });
        },
        error: function(xhr, status, error) {
            console.error('Error fetching videos:', error);
        }
    });
}

// Fetch user's uploaded videos
fetchMyVideos();





document.addEventListener('DOMContentLoaded', function() {
    const videoItems = document.querySelectorAll('.video-item');

    videoItems.forEach(videoItem => {
        videoItem.addEventListener('click', function() {
            const videoId = this.getAttribute('data-video-id');
            window.location.href = '/play/' + videoId; // Redirect to the video detail page
        });
    });
});






