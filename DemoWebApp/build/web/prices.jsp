<%@page import="model.HourPrice"%>
<%@page import="model.VehicleStay"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    String newPriceErrorMessage = null;
    if(request.getParameter("enter-new-price")!= null){
        String priceString = request.getParameter("price");
        try{
            double newPrice = Double.parseDouble(priceString);
            HourPrice.addPrice(newPrice);
            response.sendRedirect(request.getRequestURI());
        }catch(Exception e){
            newPriceErrorMessage = e.getMessage();
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
            <legend>Novo Preço</legend>
            <%if(newPriceErrorMessage!=null){%>
                <div style="color: red;"><%=newPriceErrorMessage%></div>
            <%}%>
            <form>
                <input type="text" name="price"/>
                <input type="submit" name="enter-new-price" value="Atualizar Preço"/>
            </form>
        </fieldset>
        <hr/>
        <table border="1">
            <tr>
                <th>Data</th>
                <th>Preço</th>
            </tr>
            <%try{%>
            <%for(HourPrice p: HourPrice.getList()){%>
                <tr>
                    <td><%= p.getDateTime()%></td>
                    <td><%= p.getPrice()%></td>
                </tr>
                <%}%>
            <%}catch(Exception e){%>
                <div style="color: red;"><%=e.getMessage()%></div>
            <%}%>
        </table>
        <%@include file="WEB-INF/jspf/footer.jspf" %>
    </body>
</html>