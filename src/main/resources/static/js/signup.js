document.addEventListener("DOMContentLoaded", function() {
    // Get the signup form element
    const signupForm = document.getElementById("signupForm");

    // Add an event listener to handle the form submission
    signupForm.addEventListener("submit", function(event) {
        event.preventDefault(); // Prevent the default form submission

        // Create a FormData object from the form
        const formData = new FormData(signupForm);

        // Convert FormData to a plain object
        const data = Object.fromEntries(formData.entries());

        // Send a POST request to the server with the form data
        fetch("/signUp", {
            method: "POST",
            headers: {
                "Content-Type": "application/json" // Set content type to JSON
            },
            body: JSON.stringify(data) // Convert the data object to a JSON string
        })
            .then(response => response.json()) // Parse the JSON response
            .then(data => {
                if (data.error) {
                    // If there is an error, display it in the error message element
                    const errorMessageElement = document.getElementById("errorMessage");
                    errorMessageElement.textContent = data.error;
                } else {
                    // If successful, redirect to the signup success page
                    window.location.href = "/springtube";
                }
            })
            .catch(error => {
                // Handle any unexpected errors
                const errorMessageElement = document.getElementById("errorMessage");
                errorMessageElement.textContent = "An unexpected error occurred. Please try again.";
            });
    });
});
