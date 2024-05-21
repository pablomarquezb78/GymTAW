<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <title>Registrar</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <style>
        .form-container {
            background: rgba(0, 0, 0, 0.7);
            padding: 30px;
            border-radius: 10px;
            color: white;
        }
        .form-label {
            font-weight: bold;
        }
        h1, h2 {
            text-align: center;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
<div class="container mt-5 vh-100">
    <h1>Registrar</h1>
    <h2>Introduzca los datos para crear un nuevo usuario</h2>
    <div class="form-container mx-auto col-md-6 col-lg-4">
        <form method="post" action="/peticion">
            <div class="mb-3">
                <label class="form-label">*Usuario:</label>
                <input type="text" class="form-control" id="usuario" name="usuario" required>
            </div>
            <div class="mb-3">
                <label class="form-label">*Contraseña:</label>
                <input type="password" class="form-control" id="password" name="password" required>
            </div>
            <div class="mb-3">
                <label class="form-label">*Nombre:</label>
                <input type="text" class="form-control" id="nombre" name="nombre" required>
            </div>
            <div class="mb-3">
                <label class="form-label">*Apellidos:</label>
                <input type="text" class="form-control" id="apellidos" name="apellidos" required>
            </div>
            <div class="mb-3">
                <label class="form-label">*Fecha de nacimiento:</label>
                <input type="date" class="form-control" id="fecha_nacimiento" name="fecha_nacimiento" required>
            </div>
            <div class="mb-3">
                <label class="form-label">Teléfono:</label>
                <input type="number" class="form-control" id="telefono" name="telefono">
            </div>
            <div class="mb-3">
                <label class="form-label">*Rol:</label>
                <div class="form-check">
                    <input class="form-check-input" type="radio" id="cliente" name="rol" value="2" required>
                    <label class="form-check-label">Cliente</label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="radio" id="bodybuilder" name="rol" value="3" required>
                    <label class="form-check-label">Bodybuilder</label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="radio" id="crosstrainer" name="rol" value="4" required>
                    <label class="form-check-label">Crosstrainer</label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="radio" id="dietista" name="rol" value="5" required>
                    <label class="form-check-label" for="dietista">Dietista</label>
                </div>
            </div>
            <button type="submit" class="btn btn-success mb-3 w-100">Enviar</button>
        </form>

        <a href="/cancelar" type="submit" class="btn btn-danger">Cancelar</a>
    </div>
</div>
<script src="https://cdn"></script>