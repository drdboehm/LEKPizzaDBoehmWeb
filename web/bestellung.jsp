<%-- 
    Document   : bestellung
    Created on : 18.02.2015, 14:25:36
    Author     : teilnehmer
--%>

<%@page import="com.daa.model.Bestellung"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.daa.bean.OrderBean"%>
<%@page import="com.daa.model.Gericht"%>
<%@page import="com.daa.model.Kunde"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="order" scope="session" class="com.daa.bean.OrderBean" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Zusammenfassung</title>
    </head>
    <body>
        <h1>Adressdaten</h1>
        <table border="0"> 
            <%
                out.print("<tr><td>" + order.getKunde().getVorname() + " "
                        + order.getKunde().getNachname() + "</td></tr>");
                out.print("<tr><td>" + order.getKunde().getStrasse() + " " + order.getKunde().getHausnr() + "</td></tr>");
                out.print("<tr><td>" + order.getKunde().getPlz() + " " + order.getKunde().getOrt() + "</td></tr>");
                out.print("<tr><td>SessionId: " + order.getSessionId() + "</td></tr>");
                out.print("<tr><td>RemoteAddr: " + order.getIpAddress() + "</td></tr>");
            %>       
        </table>
        <h1>Ihre Positionen</h1>
        <%
            /**
             * Enumeration values = request.getParameterNames(); Map names =
             * request.getParameterMap(); Map has a size() - Method which
             * returns an Integer of the Value pairs
             */
            // the list is 0-indexed, our Menu is 1-indexed       
                // this controller logic - need to be placed to the controller 
            for (int i = 0; i < request.getParameterMap().size(); i++) {
                // the String is a help construct to understand the mechanism 
                String gerichteAmountStr = request.getParameter(String.valueOf(i + 1));
                Integer gerichteAmount = Integer.parseInt(gerichteAmountStr);
                if (gerichteAmount > 0) {
                    order.addGerichtToBestellung(order.getGerichte().get(i), gerichteAmount);
                }

            }
        %>
        <!-- Ausgabe --> 
        <table border="0"> 
            <thead>
                <tr><th>Nr.</th><th>Anzahl</th><th>Preis</th><th>Bezeichnung</th><th>Summe</th></tr>
            </thead>
            <%   for (Gericht temp : order.getBestellung()) {
                    out.print("<tr><td>" + temp.getGerichtId() + ".</td><td>"
                            + temp.getAmount() + " x "
                            + "</td><td>"
                            + temp.getPreis() + " €"
                            + "</td><td>"
                            + temp.getBezeichnung()
                            + "</td><td>"
                            + temp.getAmount() * temp.getPreis() + " €"
                            + "</td></tr>");
                }

                out.print(
                        "<tr><td><td><td><td>Summe:</td><td>" + order.getTotalPay() + "</td></tr>");
            %>
        </table>
        <table border="0">
            <tbody>
                <tr>
                    <td>Verbindlich bestellen ?</td>
                    <td> <form action="submitted.jsp"><input type="submit" value="Ja" /></form></td>
                    <td> <form action="menu.jsp"><input type="submit" value="Nein /ändern" /></form></td>
                </tr>
            </tbody>
        </table>
    </body>
</html>
