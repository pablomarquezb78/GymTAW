<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="es.uma.entity.Ejercicio" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uma.ui.Implementacion" %>
<%@ page import="es.uma.entity.UserRol" %>
<%
    List<Ejercicio> ejercicios = (List<Ejercicio>) request.getAttribute("ejercicios");
    Boolean editable = (Boolean) request.getAttribute("editable");

    Implementacion implementacion = (Implementacion) request.getAttribute("implementacion");

    String sets = "", repeticiones = "", peso = "", tiempo = "", cal = "", metros = "";

    UserRol userRol = (UserRol) session.getAttribute("rol");
    String actionRol = "/entrenamientos/guardarimplementacion";

    if(userRol.getId() == 1 && implementacion.getId() == null) {
        actionRol = "/admin/anyadirImplementacion";
    }else if(userRol.getId() == 1 && implementacion.getId() != null){
        actionRol = "/admin/modificarImplmentacion";
    }

    if (editable != null && editable) {
        sets = implementacion.getSets();
        repeticiones = implementacion.getRepeticiones();
        peso = implementacion.getPeso();
        tiempo = implementacion.getTiempo();
        cal = implementacion.getKilocalorias();
        metros = implementacion.getMetros();
    }
%>
<html>
<head>
    <title>Formulario de Implementación</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

</head>
<body>
<jsp:include page="admin/cabeceraAdmin.jsp"></jsp:include>
<h3>
    <%=implementacion.getId() == null ? "Crear implementacion" : "Modificar implementacion"%>
</h3>
<p>
    <%=implementacion.getId() == null ? "Introduzca los datos necesarios para añadir una nueva implementacion" : "Modifica los datos de la implmentacion como desee"%>
</p>

<form:form action="<%=actionRol%>" method="post" modelAttribute="implementacion">
    <label>Set: </label>
    <form:input path="sets" value="${sets}"/>
    <br>
    <label>Repeticiones: </label>
    <form:input path="repeticiones" value="${repeticiones}"/>
    <br>
    <label>Peso: </label>
    <form:input path="peso" value="${peso}"/>
    <br>
    <label>Tiempo: </label>
    <form:input path="tiempo" value="${tiempo}"/>
    <br>
    <label>Kcal: </label>
    <form:input path="kilocalorias" value="${cal}"/>
    <br>
    <label>Metros: </label>
    Metros: <form:input path="metros" value="${metros}"/>
    <br>
    <label>Ejercicio: </label>
    <form:select path="ejercicio" items="${ejercicios}" itemValue="id" itemLabel="nombre" >
    </form:select>
    <form:hidden path="idDia"></form:hidden>
    <form:hidden path="id"></form:hidden>
    <form:button>GUARDAR</form:button>
</form:form>
</body>
</html>
