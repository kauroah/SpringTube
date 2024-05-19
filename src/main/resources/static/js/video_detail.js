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
                    commentElement.innerHTML = `
                        <p><strong>${comment.user}</strong>: ${comment.text}</p>
                        <form action="/comment/delete/${comment.id}" method="post">
                            <button type="submit">Delete</button>
                        </form>
                        <form id="editForm_${comment.id}" action="/comment/update" method="post" style="display: none;">
                            <input type="hidden" name="commentId" value="${comment.id}">
                            <input type="text" name="newText" placeholder="Edit comment" value="${comment.text}">
                            <button type="submit">Save</button>
                            <button type="button" onclick="cancelEdit(${comment.id})">Cancel</button>
                        </form>
                        <button type="button" onclick="toggleEditForm(${comment.id})">Edit</button>
                    `;
                    commentsContainer.appendChild(commentElement);
                    document.getElementById('text').value = '';
                })
                .catch(error => console.error('Error:', error));
        });
    }

    // Handle delete comment buttons
    const deleteButtons = document.querySelectorAll('.comment form[action^="/comment/delete/"]');

    deleteButtons.forEach(button => {
        button.addEventListener('submit', function(event) {
            event.preventDefault();
            const form = event.target;

            fetch(form.action, {
                method: 'POST'
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    form.parentElement.remove(); // Remove the comment element from the DOM
                })
                .catch(error => console.error('Error:', error));
        });
    });

    // Handle edit comment forms
    const editForms = document.querySelectorAll('.comment form[action^="/comment/update"]');

    editForms.forEach(form => {
        form.addEventListener('submit', function(event) {
            event.preventDefault();
            const formData = new FormData(form);

            fetch(form.action, {
                method: 'POST',
                body: formData
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    return response.json();
                })
                .then(updatedComment => {
                    const commentElement = form.parentElement.querySelector('p');
                    commentElement.innerHTML = `<strong>${updatedComment.user}</strong>: ${updatedComment.text}`;
                    form.style.display = 'none'; // Hide the edit form
                })
                .catch(error => console.error('Error:', error));
        });
    });

    // Toggle edit form display
    function toggleEditForm(commentId) {
        const editForm = document.getElementById(`editForm_${commentId}`);
        editForm.style.display = (editForm.style.display === 'none') ? 'block' : 'none';
    }

    // Cancel edit
    function cancelEdit(commentId) {
        const editForm = document.getElementById(`editForm_${commentId}`);
        editForm.style.display = 'none';
    }

    // Make the functions available globally
    window.toggleEditForm = toggleEditForm;
    window.cancelEdit = cancelEdit;
});
