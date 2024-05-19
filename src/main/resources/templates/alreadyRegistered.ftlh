<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Email Already Registered</title>
    <style>
        body {
            font-family: 'Roboto', sans-serif;
            margin: 0;
            padding: 0;
            color: #000000;
            background: linear-gradient(to bottom, #45A29E 5%, #000000 45%);
            background-attachment: fixed;
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            text-align: center;
        }
        .container {
            background: #000000;
            padding: 20px 40px;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
        }
        h1 {
            color: #ffffff;
        }
        p {
            color: #666666;
        }
        a {
            color: #45A29E;
            text-decoration: none;
        }
        a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Email Already Registered</h1>
    <p>The email you provided is already registered. Please use a different email or <a href="/signIn">sign in</a>.</p>
</div>
</body>
</html>
