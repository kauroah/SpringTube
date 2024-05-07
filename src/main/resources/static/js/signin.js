const form = document.getElementById('signIn-form');
const emailInput = document.getElementById('email');
const passwordInput = document.getElementById('password');

form.addEventListener('submit', function(event) {
    event.preventDefault();

    if (validateForm()) {
        // Submit the form (you can replace this with your backend logic)
        console.log('Form submitted successfully!');
        // Clear form fields
        form.reset();
    }
});

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

    // If all validations pass
    clearError(emailInput);
    clearError(passwordInput);
    return true;
}

function showError(input, message) {
    const formGroup = input.parentElement;
    const errorMessage = formGroup.querySelector('.error-message');
    errorMessage.textContent = message;
    formGroup.classList.add('error');
}

function clearError(input) {
    const formGroup = input.parentElement;
    formGroup.classList.remove('error');
    const errorMessage = formGroup.querySelector('.error-message');
    errorMessage.textContent = '';
}

function isValidEmail(email) {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
}