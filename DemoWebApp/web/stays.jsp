<%@page import="model.VehicleStay"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    String enterParkingErrorMessage = null;
    if(request.getParameter("enter-parking")!= null){
        String model = request.getParameter("model");
        String color = request.getParameter("color");
        String plate = request.getParameter("plate");
        try{
            VehicleStay.addVehicleStay(model, color, plate);
            response.sendRedirect(request.getRequestURI());
        }catch(Exception e){
            enterParkingErrorMessage = e.getMessage();
        }
    }
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ESTADIAS - Parking WebApp</title>
    </head>
    <body>
        <%@include file="WEB-INF/jspf/header.jspf" %>
        <h2>Estadias</h2>
        <fieldset>
            <legend>Nova Entrada</legend>
            <%if(enterParkingErrorMessage!=null){%>
                <div style="color: red;"><%=enterParkingErrorMessage%></div>
            <%}%>
            <form>
                Modelo: <input type="text" name="model"/>
                Cor: <input type="text" name="color"/>
                Placa: <input type="text" name="plate"/>
                <input type="submit" name="enter-parking" value="Registrar"/>
            </form>
        </fieldset>
        <hr/>
        <table border="1">
            <tr>
                <th>Modelo</th>
                <th>Cor</th>
                <th>Placa</th>
                <th>Entrada</th>
                <th>Controle</th>
            </tr>
            <%try{%>
                <%for(VehicleStay vs: VehicleStay.getList()){%>
                <tr>
                    <td><%= vs.getVehicleModel() %></td>
                    <td><%= vs.getVehicleColor()%></td>
                    <td><%= vs.getVehiclePlate()%></td>
                    <td><%= vs.getBeginStay() %></td>
                    <td>
                        <form action="finish_stay.jsp">
                            <input type="hidden" name="id" 
                                   value="<%= vs.getRowId()%>"/>
                            <input type="submit" name="exit-parking" 
                                   value="Registrar saída"/>
                        </form>
                    </td>
                </tr>
                <%}%>
            <%}catch(Exception e){%>
                <div style="color: red;"><%=e.getMessage()%></div>
            <%}%>
        </table>
        <%@include file="WEB-INF/jspf/footer.jspf" %>
    </body>
</html>