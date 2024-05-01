<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Admin</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav nav-fill w-100">
                <li class="nav-item active">
                    <a class="nav-link">Autenticar</a>
                </li>
                <li class="nav-item">
                    <form class="nav-link" action="/admin/mostrarUsuarios" method="post">
                        <button>Usuarios</button>
                    </form>
                </li>
                <li class="nav-item">
                    <a class="nav-link">Asignar</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link">Ejercicios</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link">Platos</a>
                </li>
            </ul>
        </div>
    </nav>
    <h1>
        Bienvenido admin
    </h1>
</body>
</html>