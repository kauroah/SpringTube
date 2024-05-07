// Fetch user data using AJAX when the page loads
document.addEventListener('DOMContentLoaded', function () {
    fetchUserData();
});

// Function to fetch user data using AJAX
function fetchUserData() {
    fetch('/profile', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => response.json())
        .then(data => {
            // Populate form fields with retrieved data
            document.getElementById('firstName').value = data.firstName;
            document.getElementById('lastName').value = data.lastName;
            document.getElementById('phone').value = data.phone;
        })
        .catch(error => {
            console.error('Error:', error);
        });
}

// Handle form submission using AJAX
document.getElementById('profileForm').addEventListener('submit', function (event) {
    event.preventDefault(); // Prevent default form submission
    updateProfile();
});

// Function to update profile using AJAX
function updateProfile() {
    const formData = new FormData(document.getElementById('profileForm'));
    fetch('/profile/update', {
        method: 'POST',
        body: formData
    })
        .then(response => response.json())
        .then(data => {
            // Handle success response
            console.log('Profile updated successfully:', data);
            // Optionally, display a success message or redirect to another page
        })
        .catch(error => {
            console.error('Error:', error);
            // Handle error response
        });
}