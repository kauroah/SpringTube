<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Channels</title>
    <link rel="stylesheet" href="/static/css/admin.css">
</head>
<body>
<h1>Admin Channels</h1>
<table id="channels">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Actions</th>
    </tr>
    <tbody>
    <#list channels as channel>
        <tr>
            <td>${channel.id}</td>
            <td>${channel.name}</td>
            <td class="action-buttons">
                <form action="/admin/channels/update" method="post">
                    <input type="hidden" name="channelId" value="${channel.id}">
                    <input type="text" name="newName" placeholder="New Name" required>
                    <button type="submit">Update Name</button>
                </form>
            </td>
        </tr>
    </#list>
    </tbody>
</table>
</body>
</html>