// Get references to form and input elements
const form = document.getElementById('signIn-form');
const emailInput = document.getElementById('email');
const passwordInput = document.getElementById('password');

// Add event listener to handle form submission
form.addEventListener('submit', function(event) {
    event.preventDefault(); // Prevent default form submission

    if (validateForm()) {
        // If validation is successful, log a success message
        console.log('Form submitted successfully!');
        // Clear form fields
        form.reset();
    }
});

// Function to validate the form inputs
function validateForm() {
    const email = emailInput.value.trim();
    const password = passwordInput.value;

    // Validate email
    if (email === '') {
        showError(emailInput, 'Email is required');
        return false;
    } else if (!isValidEmail(email)) {
        showError(emailInput, 'Invalid email address');
        return false;
    }

    // Validate password
    if (password === '') {
        showError(passwordInput, 'Password is required');
        return false;
    }

    // Clear errors if validations pass
    clearError(emailInput);
    clearError(passwordInput);
    return true;
}

// Function to display error messages
function showError(input, message) {
    const formGroup = input.parentElement; // Get the parent element of the input (form-group)
    const errorMessage = formGroup.querySelector('.error-message'); // Find the error message span within the form-group
    errorMessage.textContent = message; // Set the error message text
    formGroup.classList.add('error'); // Add the 'error' class to highlight the input field
}

// Function to clear error messages
function clearError(input) {
    const formGroup = input.parentElement; // Get the parent element of the input (form-group)
    formGroup.classList.remove('error'); // Remove the 'error' class
    const errorMessage = formGroup.querySelector('.error-message'); // Find the error message span within the form-group
    errorMessage.textContent = ''; // Clear the error message text
}

// Function to validate email format using a regular expression
function isValidEmail(email) {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/; // Regular expression for basic email validation
    return emailRegex.test(email); // Test the email against the regular expression
}
