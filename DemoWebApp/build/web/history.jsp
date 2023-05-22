<%@page import="java.util.Date"%>
<%@page import="model.VehicleStay"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    String plate = null;
    if(request.getParameter("plate")!=null){
        if(!request.getParameter("plate").equals(""))
            plate = request.getParameter("plate");
    }
    String beginDate = null;
    if(request.getParameter("date")!=null){
        if(!request.getParameter("date").equals(""))
            beginDate = request.getParameter("date")+" 00:00:00.000";
    }
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>HISTÓRICO - Parking WebApp</title>
    </head>
    <body>
        <%@include file="WEB-INF/jspf/header.jspf" %>
        <h2>Histórico</h2>
        <fieldset>
            <legend>Filtro</legend>
            <form>
                Placa: <input type="text" name="plate"/>
                Data: <input type="date" name="date"/>
                <input type="submit" name="filter" value="Filtrar"/>
            </form>
            <form>
                <input type="submit" name="clear" value="Limpar filtro"/>
            </form>
        </fieldset>
        <hr/>
        <table border="1">
            <tr>
                <th>Modelo</th>
                <th>Cor</th>
                <th>Placa</th>
                <th>Entrada</th>
                <th>Saída</th>
                <th>Preço</th>
            </tr>
            <%try{%>
                <%for(VehicleStay vs: VehicleStay.getHistoryList(plate, beginDate)){%>
                <tr>
                    <td><%= vs.getVehicleModel() %></td>
                    <td><%= vs.getVehicleColor() %></td>
                    <td><%= vs.getVehiclePlate() %></td>
                    <td><%= vs.getBeginStay() %></td>
                    <td><%= vs.getEndStay() %></td>
                    <td><%= vs.getPrice() %></td>
                </tr>
                <%}%>
            <%}catch(Exception e){%>
                <div style="color: red;"><%=e.getMessage()%></div>
            <%}%>
        </table>
        <%@include file="WEB-INF/jspf/footer.jspf" %>
    </body>
</html>