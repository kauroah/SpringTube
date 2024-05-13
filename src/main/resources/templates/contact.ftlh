<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Support</title>
  <link rel="stylesheet" href="/static/css/contact.css">
</head>
<body>
<div class="contact-form">
  <div class="logo-container">
    <img src="/static/images/logo.png" alt="SpringTube Logo">
  </div>
  <h2>Contact Support</h2>

  <form action="/contact" method="post">
    <label for="name">Your Name:</label>
    <input type="text" id="name" name="name" required>

    <label for="email">Your Email:</label>
    <input type="email" id="email" name="email">

    <label for="message">Your Message:</label>
    <textarea id="message" name="message" required></textarea>

    <button type="submit">Send Message</button>
  </form>
</div>
</body>
</html>