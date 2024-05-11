<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org" lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="/static/css/admin.css">
</head>
<body>
<div class="admin-container">
    <h1>Admin Dashboard</h1>
    <table id="users">
        <tr>
            <th>ID</th>
            <th>Email</th>
            <th>Role</th>
            <th>State</th>
            <th>Actions</th>
        </tr>
        <tbody>
        <#list users as user>
            <tr>

            <td> ${user.id} </td>
            <td> ${user.email} </td>
            <td> ${user.role} </td>
            <td> ${user.state}</td>

                <td class="action-buttons">
            <form action="/admin/block" method="post" id="blockForm" class="user-action-form">
                <input type="hidden" name="userId" value="${user.id}">
                <button type="submit">Block</button>
            </form>
            <form action="/admin/unblock" method="post" id="unblockForm" class="user-action-form">
                <input type="hidden" name="userId" value="${user.id}">
                <button type="submit">Unblock</button>
            </form>
            <form action="/admin/grantAdmin" method="post">
                <input type="hidden" name="userId" value="${user.id}">
                <button type="submit">Grant Admin</button>
            </form>
            <form action="/admin/revokeAdmin" method="post">
                <input type="hidden" name="userId" value="${user.id}">
                <button type="submit">Revoke Admin</button>
            </form>
            <form action="/admin/delete" method="post">
                <input type="hidden" name="userId" value="${user.id}">
                <button type="submit">Delete</button>
            </form>
                </td>
        </tr>
        </#list>
        </tbody>
    </table>
</div>
<script src="/static/js/admin.js"></script>
</body>
</html>
