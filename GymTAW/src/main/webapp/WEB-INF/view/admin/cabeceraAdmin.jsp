<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String paginaActual = (String) request.getAttribute("paginaActual");
%>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav nav-fill w-100">
            <li class="nav-item">
                <a class="nav-link <%= paginaActual.equals("inicio") ? "active fw-bold" : "" %>" href="/admin/">Inicio</a>
            </li>
            <li class="nav-item">
                <a class="nav-link <%= paginaActual.equals("autenticar") ? "active fw-bold" : "" %>" href="/admin/autenticarUsuarios">Autenticar</a>
            </li>
            <li class="nav-item">
                <a class="nav-link <%= paginaActual.equals("usuarios") ? "active fw-bold" : "" %>" href="/admin/mostrarUsuarios">Usuarios</a>
            </li>
            <li class="nav-item">
                <a class="nav-link <%= paginaActual.equals("asignar") ? "active fw-bold" : "" %>" href="/admin/asignarCliente">Asignar</a>
            </li>
            <li class="nav-item">
                <a class="nav-link <%= paginaActual.equals("ejercicios") ? "active fw-bold" : "" %>" href="/comun/mostrarEjercicios">Ejercicios</a>
            </li>
            <li class="nav-item">
                <a class="nav-link <%= paginaActual.equals("platos") ? "active fw-bold" : "" %>" href="/admin/mostrarPlatos">Platos</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/cerrarSesion">Cerrar sesion</a>
            </li>
        </ul>
    </div>
</nav>