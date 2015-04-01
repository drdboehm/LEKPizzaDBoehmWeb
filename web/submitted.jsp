<%-- 
    Document   : submitted
    Created on : 20.02.2015, 08:29:10
    Author     : dboehm
--%>

<%@page import="com.daa.model.Gericht"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="order" scope="session" class="com.daa.bean.OrderBean" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Submitted</title>
    </head>
    <body>
        <h1>Vielen Dank für Ihre Bestellung</h1>
        <%
            // the "Kunde" placed the order, so persist all to database
            order.setIsOrdered(true);
            boolean success = order.addBestellungToDatabase();
            if (success) {
                out.println("Order saved to database ! ");
             }
        %>
        <!--Ausgabe --> 
        <table border="0"> 
            <thead>
                <tr><th>Pos</th><th>Anzahl</th><th>Preis</th><th>Bezeichnung</th><th>Summe</th></tr>
            </thead>
            <%  int pos = 0;
                for (Gericht temp : order.getBestellung()) {
                    out.print("<tr><td>" + (++pos) + ".</td><td>"
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
        <a href="pdfdokumente/PizzaRechnung.pdf">Rechnung als PDF</a>
        <a href="index.jsp">Zurück zur Startseite ... </a>
    </body>
</html>
