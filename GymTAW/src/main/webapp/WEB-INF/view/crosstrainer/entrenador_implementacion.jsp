<%@ page import="es.uma.entity.Ejercicio" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uma.entity.ImplementacionEjercicioRutina" %>
<%
    List<Ejercicio> ejercicios = (List<Ejercicio>) request.getAttribute("ejercicios");
    ImplementacionEjercicioRutina imp = (ImplementacionEjercicioRutina) request.getAttribute("imp");
    Boolean editable = (Boolean) request.getAttribute("editable");

    String sets="",repeticiones="",peso="",tiempo="",cal="",metros="";

    if(editable!=null && editable){
        sets=imp.getSets();
        repeticiones=imp.getRepeticiones();
        peso=imp.getPeso();
        tiempo=imp.getTiempo();
        cal=imp.getKilocalorias();
        metros=imp.getMetros();
    }


%>
<html>

<head>



</head>
<body>
<form action="/entrenamientos/guardarimplementacion" method="post">
    <h3>Sets:  <input name="sets" value="<%=sets%>"></h3>
    <h3>Repeticiones: <input name="repeticiones" value="<%=repeticiones%>"></h3>
    <h3>Peso: <input name="peso" value="<%=peso%>"></h3>
    <h3>Tiempo: <input name="tiempo" value="<%=tiempo%>"></h3>
    <h3>Kcal: <input name="cal" value="<%=cal%>"></h3>
    <h3>Metros: <input name="metros" value="<%=metros%>"></h3>
    <h3>Ejercicio:</h3>

    <select>
        <%
            for(Ejercicio ej: ejercicios){
                String seleccionado= "";

                if( ej.getId().equals(imp.getEjercicio().getId()) ){
                    seleccionado = "selected";
                }

        %>
            <option name="ejercicio" value="<%=ej.getId()%>" <%=seleccionado%>> <%=ej.getNombre()%></option>
        <%
            }
        %>
    </select>

    <button>GUARDAR</button>
</form>

</body>
</html>