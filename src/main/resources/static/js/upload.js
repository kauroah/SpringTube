async function fetchMyVideos() {
    const response = await fetch('/channel');
    const data = await response.json();
    const videoList = document.getElementById('video-list');
    videoList.innerHTML = '';
}
// Fetch user's uploaded videos
fetchMyVideos();