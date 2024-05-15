 document.addEventListener("DOMContentLoaded", function () {
        const otherVideoItems = document.querySelectorAll(".other-video-item");

        otherVideoItems.forEach(item => {
            item.addEventListener("click", function () {
                const videoId = item.getAttribute("data-video-id");
                const videoUrl = `/play/` + videoId;

                window.location.href = videoUrl;
            });
        });
    });

    document.addEventListener("DOMContentLoaded", function () {
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
    });


    document.addEventListener("DOMContentLoaded", function () {
        const subscribeForm = document.getElementById("subscribeForm");
        const unsubscribeForm = document.getElementById("unsubscribeForm");
        const subscribeButton = document.getElementById("subscribeButton");
        const unsubscribeButton = document.getElementById("unsubscribeButton");

        console.log("Subscribe button:", subscribeButton);
        console.log("Unsubscribe button:", unsubscribeButton);

        // Function to update UI based on subscription status
        function updateUI(subscribed) {
            if (subscribed) {
                showUnsubscribeForm();
            } else {
                showSubscribeForm();
            }
        }

        // Check if user is subscribed or not
        fetch(`/checkSubscription/${mainVideo.channel.id}`)
            .then(response => response.json())
            .then(data => {
                updateUI(data.subscribed); // Update UI based on subscription status
            })
            .catch(error => {
                console.error("Error:", error);
            });

        // Subscribe button click event
        subscribeButton.addEventListener("click", function (event) {
            event.preventDefault(); // Prevent default form submission behavior
            fetch(`/subscribe/${mainVideo.channel.id}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                }
            })
                .then(response => {
                    if (response.ok) {
                        return response.json();
                    }
                    throw new Error('Failed to subscribe');
                })
                .then(data => {
                    console.log(data.message);
                    updateUI(true);
                })
                .catch(error => {
                    console.error("Error:", error);
                });
        });

        // Unsubscribe button click event
        unsubscribeButton.addEventListener("click", function (event) {
            event.preventDefault(); // Prevent default form submission behavior
            fetch(`/unsubscribe/${mainVideo.channel.id}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                }
            })
                .then(response => {
                    if (response.ok) {
                        return response.json();
                    }
                    throw new Error('Failed to unsubscribe');
                })
                .then(data => {
                    console.log(data.message);
                    updateUI(false);
                })
                .catch(error => {
                    console.error("Error:", error);
                });
        });

        function showSubscribeForm() {
            subscribeForm.style.display = "block";
            unsubscribeForm.style.display = "none";
        }

        function showUnsubscribeForm() {
            subscribeForm.style.display = "none";
            unsubscribeForm.style.display = "block";
        }
    });


    document.addEventListener("DOMContentLoaded", function () {
        const otherVideoItems = document.querySelectorAll(".other-video-item");

        otherVideoItems.forEach(item => {
            item.addEventListener("click", function () {
                const videoId = item.getAttribute("data-video-id");
                const video = document.querySelector(`video[data-video-id="${videoId}"]`);

                if (video) {
                    video.play();
                }
            });
        });
    });
