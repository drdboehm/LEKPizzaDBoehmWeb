<%-- 
    Document   : kunden
    Created on : 17.02.2015, 14:57:06
    Author     : dboehm
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="order" scope="session" class="com.daa.bean.OrderBean" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Bärenhunger Pizza-Service</title>
    </head>
    <body>
        <h1>Ihre Adressdaten: </h1>
        <%  order.getKunde().setUsername(request.getParameter("username"));
            order.getKunde().setVorname(request.getParameter("vorname"));
            order.getKunde().setNachname(request.getParameter("nachname"));
            order.getKunde().setStrasse(request.getParameter("strasse"));
            order.getKunde().setHausnr(request.getParameter("hausnr"));
            order.getKunde().setPlz(request.getParameter("plz"));
            order.getKunde().setOrt(request.getParameter("ort"));
        %>
        <table border="0" cellspacing="2" bgcolor="#ffffff" style="margin:20px"> <!--;margin-top:20px; margin-bottom: 20px"> -->
            <tr>
                <td><b>Username</b></td><td><%= order.getKunde().getUsername()%></td>
            </tr>
            <tr>
                <td><b>Vorname:</b></td><td><%= order.getKunde().getVorname()%></td>
            </tr>
            <tr>
                <td><b>Nachname:</b></td><td><%= order.getKunde().getNachname()%></td>
            </tr>
            <tr>
                <td><b>Strasse + Nr.:</b></td><td><%= order.getKunde().getStrasse()%> <%=order.getKunde().getHausnr()%></td>
            </tr>
            <tr>
                <td><b>PLZ + Ort:</b></td><td><%= order.getKunde().getPlz()%> <%=order.getKunde().getOrt()%></td>
            </tr>
        </table>

        <table border="0">
            <tr>
                <td>Sind diese Daten korrekt ?</td>
                <td> <form action="menu.jsp"><input type="submit" value="Ja" /></form></td>
                <td> <form action="index.jsp"><input type="submit" value="Nein / erneute Eingabe" /></form></td>
            </tr>
        </table>
    </body>
</html>
