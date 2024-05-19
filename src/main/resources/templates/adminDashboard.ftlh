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
    <div class="page-titles">
        <div class="page-title">
            <a href="/admin/channels">Channels</a>
        </div>
        <div class="page-title">
            <a href="/admin/dashboard">Dashboard</a>
        </div>
        <div class="page-title">
            <a href="/admin/videos">Videos</a>
        </div>
    </div>
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
                    <form action="/admin/block" method="post">
                        <input type="hidden" name="userId" value="${user.id}">
                        <button type="submit">Block</button>
                    </form>
                    <form action="/admin/unblock" method="post">
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


    <ul>
        <#if currentPage gt 1>
            <li><a href="?page=1">First</a></li>
            <li><a href="?page=${currentPage - 1}">Previous</a></li>
        </#if>
        <#list 1..totalPages as page>
            <li <#if page == currentPage> class="active" </#if>><a href="?page=${page}">${page}</a></li>
        </#list>
        <#if currentPage lt totalPages>
            <li><a href="?page=${currentPage + 1}">Next</a></li>
            <li><a href="?page=${totalPages}">Last</a></li>
        </#if>
    </ul>

</div>
<script src="/static/js/admin.js"></script>
</body>
</html>