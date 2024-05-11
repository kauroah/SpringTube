document.addEventListener("DOMContentLoaded", function() {
    // Load other videos when the page is loaded
    loadOtherVideos();
});

function loadOtherVideos() {
    // Make an AJAX request to load other videos
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "/api/otherVideos", true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            // Process the response and display other videos
            var otherVideos = JSON.parse(xhr.responseText);
            displayOtherVideos(otherVideos);
        }
    };
    xhr.send();
}

function displayOtherVideos(videos) {
    var otherVideosContainer = document.querySelector('.other-videos-container');

    videos.forEach(function(video) {
        var videoItem = document.createElement('div');
        videoItem.classList.add('other-video-item');
        videoItem.innerHTML = `
            <div class="thumbnail">
                <img src="${video.thumbnailUrl}" alt="Thumbnail">
            </div>
            <div class="video-info">
                <h3>${video.originalName}</h3>
                <!-- Add any additional video info if needed -->
            </div>
        `;
        // Add click event listener to play the video when clicked
        videoItem.addEventListener('click', function() {
            window.location.href = '/files/' + video.storageFileName;
        });

        otherVideosContainer.appendChild(videoItem);
    });
}
