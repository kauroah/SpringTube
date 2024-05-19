<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Videos</title>
    <link rel="stylesheet" href="/static/css/admin.css">
</head>
<body>
<h1>Admin Videos</h1>
<table id="videos">
    <thead>
    <tr>
        <th>Video ID</th>
        <th>Title</th>
        <th>Channel</th>
        <th>Action</th>
    </tr>
    </thead>
    <tbody>
    <#list videos as video>
        <tr>
            <td>${video.id}</td>
            <td>${video.originalName}</td>
            <td>${video.channel.name}</td>


            <td class="action-buttons">
                <form action="/admin/videos/delete" method="post">
                    <input type="hidden" name="videoId" value="${video.id}">
                    <button type="submit">Delete</button>
                </form>
            </td>
        </tr>
    </#list>
    </tbody>
</table>
</body>
</html>