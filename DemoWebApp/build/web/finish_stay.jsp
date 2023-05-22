<%@page import="model.VehicleStay"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    String errorMessage = null;
    VehicleStay vs = null;
    try{
        int id = Integer.parseInt(request.getParameter("id"));
        vs = VehicleStay.getStay(id);
        if(request.getParameter("cancel")!=null){
            response.sendRedirect(request.getContextPath()+"/stays.jsp");
        }else if(request.getParameter("confirm")!=null){
            VehicleStay.finishVehicleStay(id, vs.getTotal());
            response.sendRedirect(request.getContextPath()+"/stays.jsp");
        }
    }catch(Exception ex){
        errorMessage = ex.getMessage();
    }
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ESTADIAS - Parking WebApp</title>
    </head>
    <body>
        <%@include file="WEB-INF/jspf/header.jspf" %>
        <h2>Finalizar Estadia</h2>
        <%if(errorMessage!=null){%>
            <div style="color: red;"><%=errorMessage%></div>
        <%}else{%>
            <h3>ID: <%= vs.getRowId() %></h3>
            <h3>Modelo: <%= vs.getVehicleModel()%></h3>
            <h3>Cor: <%= vs.getVehicleColor()%></h3>
            <h3>Placa: <%= vs.getVehiclePlate()%></h3>
            <h3>Entrada: <%= vs.getBeginStay()%></h3>
            <h3><b>Preço: <%= vs.getTotal()%></b></h3>
            <form>
                <input type="hidden" name="id" value="<%= vs.getRowId() %>"/>
                <input type="submit" name="cancel" value="Cancelar"/>
                <input type="submit" name="confirm" value="Confirmar"/>
            </form>
            <hr/>
        <%}%>
        <%@include file="WEB-INF/jspf/footer.jspf" %>
    </body>
</html>