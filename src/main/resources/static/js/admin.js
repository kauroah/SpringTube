// Function to handle AJAX request for user action forms
const handleUserAction = async (formId) => {
    const form = document.getElementById(formId);
    const formData = new FormData(form);

    try {
        const response = await fetch(form.action, {
            method: form.method,
            body: formData
        });
        if (response.ok) {
            location.reload(); // Reload the page after successful action
        } else {
            console.error('Failed to perform action');
        }
    } catch (error) {
        console.error('Error occurred:', error);
    }
};

// Attach event listeners to each user action form
document.addEventListener("DOMContentLoaded", function() {
    const userActionForms = document.querySelectorAll('.user-action-form');

    userActionForms.forEach(form => {
        form.addEventListener('submit', function(event) {
            event.preventDefault(); // Prevent form submission

            // Determine action based on form ID
            const formId = form.id;
            if (formId === 'blockForm') {
                handleUserAction('blockForm');
            } else if (formId === 'unblockForm') {
                handleUserAction('unblockForm');
            }
        });
    });
});
