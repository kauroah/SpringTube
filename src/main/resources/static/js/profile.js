// Fetch user data using AJAX when the page loads
$(document).ready(function() {
    fetchUserData();
});

// Function to fetch user data using AJAX
function fetchUserData() {
    $.ajax({
        url: '/profile',
        method: 'GET',
        contentType: 'application/json',
        success: function(data) {
            // Populate form fields with retrieved data
            $('#firstName').val(data.firstName);
            $('#lastName').val(data.lastName);
            $('#phone').val(data.phone);
        },
        error: function(error) {
            console.error('Error:', error);
        }
    });
}

// Handle form submission using AJAX
$('#profileForm').submit(function(event) {
    event.preventDefault(); // Prevent default form submission
    updateProfile();
});

// Function to update profile using AJAX
function updateProfile() {
    var formData = new FormData($('#profileForm')[0]);
    $.ajax({
        url: '/profile/update',
        method: 'POST',
        data: formData,
        processData: false,
        contentType: false,
        success: function(data) {
            // Handle success response
            console.log('Profile updated successfully:', data);
            // Optionally, display a success message or redirect to another page
        },
        error: function(error) {
            console.error('Error:', error);
            // Handle error response
        }
    });
}
