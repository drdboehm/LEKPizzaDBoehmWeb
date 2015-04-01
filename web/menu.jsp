<%-- 
    Document   : menu
    Created on : 18.02.2015, 10:08:57
    Author     : dboehm
--%>

<%@page import="com.daa.model.Kunde"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.daa.bean.OrderBean"%>
<%@page import="com.daa.model.Gericht"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="order" scope="session" class="com.daa.bean.OrderBean" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Bärenhunger Menus zur Auswahl</title>
    </head>
    <body>
        <h1>Bärenhunger Menu-Auswahl</h1>
        <form action="bestellung.jsp">
            <table border="1"> 
                <%
                    order.getKunde().store();
                    out.println("<tr><td>Your Id:</td><td>" + order.getKunde().getLastId() + "</td></tr>");
                    /* next line is to reset the ArryList named bestellung when you 
                     come back from bestellung.jsp
                     */
                    order.setBestellung(new ArrayList<Gericht>());
                    for (Gericht g : order.getGerichte()) {
                        out.print("<tr><td>" + g.getGerichtId() + ". " + "</td>");
                        out.print("<td>" + g.getBezeichnung() + "</td>");
                        out.print("<td><input type=\"text\" name=" + 
                                g.getGerichtId() + " value=\"0\" size=\"3\" " 
                                + " /></td><td>" + 
                                g.getPreis() + " €</td></tr>");
                    }
                %>

            </table> <input type="submit" value="Auswahl" /></form>
    </body>
</html>
