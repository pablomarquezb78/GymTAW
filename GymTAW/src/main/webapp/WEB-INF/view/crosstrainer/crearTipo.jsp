<%@ page import="es.uma.ui.TipoEjercicioUI" %>
<%@ page import="es.uma.entity.UserRol" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%

    TipoEjercicioUI tipoEjercicioUI = (TipoEjercicioUI) request.getAttribute("tipoEjercicio");
    UserRol userRol = (UserRol) session.getAttribute("rol");
    String actionRol = "/entrenamientos/guardar-tipo-ejercicio";
    String cabecera = "cabecera_entrenador.jsp";

%>
<html>

<head>

    <title>Fabrica Tipo Ejercicio</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

</head>
<body>
<jsp:include page="<%=cabecera%>"></jsp:include>

    <h3>
        <%=tipoEjercicioUI.getIdTipoEjercicio() == -1 ? "Crear tipo de ejercicio" : "Modificar tipo de ejercicio"%>
    </h3>
    <p>
        <%=tipoEjercicioUI.getIdTipoEjercicio() == -1 ? "Introduzca los datos necesarios para añadir un nuevo tipo de ejercicio" : "Modifica los datos del tipo de ejercicio como desee"%>
    </p>

   <form:form method="post" action="<%=actionRol%>" modelAttribute="tipoEjercicio">

       <form:hidden path="idTipoEjercicio"></form:hidden>

       <form:input path="nombreTipoEjercicio"></form:input>

       <form:button>Guardar tipo ejercicio</form:button>

   </form:form>


</body>

</html>