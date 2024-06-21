<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String error = (String) request.getAttribute("error");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <style>
        .form-container {
            background: #343a40;
            padding: 30px;
            border-radius: 10px;
        }
        .form-label {
            font-weight: bold;
            color: white;
        }
    </style>
</head>
<body>

<div class="d-flex flex-column justify-content-center align-items-center vh-100">
    <h1 class="text-center p-2">Login</h1>
    <div class="form-container">
        <form method="post" action="/autentica">
            <div class="mb-3">
                <label class="form-label">Usuario:</label>
                <input type="text" class="form-control" id="usuario_login" name="usuario_login" required>
            </div>
            <div class="mb-3">
                <label class="form-label">Contraseña:</label>
                <input type="password" class="form-control" id="password_login" name="password_login" required>
            </div>
            <button type="submit" class="btn btn-success w-100">Acceder</button>
        </form>
        <div class="text-center mt-3">
            <a href="/registrar" class="text-white">Registrarse</a>
        </div>
    </div>
    <% if(error != null) { %>
    <div class="alert alert-danger mt-3">
        <h5>ERROR: <%= error %></h5>
    </div>
    <% } %>
</div>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXlXtW8VDtnrROZqPLFpuUWI4a2sA8pD5A4cJZHPUOks+EmW1E6Lxk3VFtDM" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGktK0gYf94IYNd2tKpREIHMR5cQm75J5pbWuyj6cvF2DkSPEj3h4dHGsR9" crossorigin="anonymous"></script>
</body>
</html>