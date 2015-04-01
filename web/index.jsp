<%-- 
    Document   : index
    Created on : 17.02.2015, 14:20:11
    Author     : dboehm
--%>

<%@page import="com.daa.model.Kunde"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="order" scope="session" class="com.daa.bean.OrderBean" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Bärenhunger Pizza-Service</title>
    </head>
    <body>
        <h1>Bärenhunger Pizzaversand für ... </h1>

        <%order.ipdAndSession(request);

            /*
             this should later on an implementation to "rescue" the entries from "Kunde" already 
             done, when "Kunde" comes back from "kunden.jsp" and wants to correct mistakes.
             LATER on, kunde fields should be printed in the "name" parameter of the INPUT form 
             */
            /* if (session.getAttribute("neuerKunde") != null) {
             Kunde temp = (Kunde) session.getAttribute("neuerKunde");
             // out.print("'" + session.getAttribute("neuerKunde") + "'" + "'" + temp.getNachname() + "'"); 
             // the problem is, the session exists also from the beginning, when you restarted the progF 
             } */
        %>
        <form action="kunden.jsp">
            <table border="1">
                <tr>
                    <td>Username</td>
                    <td><input type="text" name="username" value="" /></td>
                    <%-- this will be a login form with password soon
                    <td>Passwort</td>
                    <td><input type="password" name="password" value="" /></td> --%>
                </tr>

                <tr>
                    <td>Vorname:</td>
                    <td><input type="text" name="vorname" value="" /></td>
                    <td>Nachname:</td>
                    <td><input type="text" name="nachname" value="" /></td>
                </tr>
                <tr>
                    <td>Strasse: </td>
                    <td><input type="text" name="strasse" value="" /></td>
                    <td>Hausnummer: </td>
                    <td><input type="text" name="hausnr" value="" size="4"/></td>
                </tr>
                <tr>
                    <td>Plz.:</td>
                    <td><input type="text" name="plz" value="" size="6"/></td>
                    <td>Ort:</td>
                    <td><input type="text" name="ort" value="" /></td>
                </tr>
            </table>
            <input type="submit" value="OK" />
        </form>
    </body>
</html>
