/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.daa.bean;

//import com.daa.model.Bestellung;
import com.daa.model.Gericht;
import com.daa.model.Kunde;
import com.daa.util.GConnection;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author dboehm
 */
public class OrderBean extends GConnection implements Serializable {

    private static final long serialVersionUID = 1L;
    private Kunde kunde;
    private List<Gericht> bestellung;
    private final List<Gericht> gerichte;
    private String sessionId;
    private String ipAddress;
    private double totalPay;
    private boolean isOrdered;
    private final Connection conn;
    private Statement stmt;
    private ResultSet rs;
    private int lastId;

    public OrderBean() {
        conn = getConnection();
        this.kunde = new Kunde();
        this.bestellung = new ArrayList<>();
        this.totalPay = 0.0;
        this.isOrdered = false;
        this.gerichte = new ArrayList<>();
        this.initializeMenu();
    }

    public Kunde getKunde() {
        return kunde;
    }

    public double getTotalPay() {
        // calculate every time again tu update
        this.calculateTotalPay();
        return totalPay;
    }

    public List<Gericht> getGerichte() {
        return gerichte;
    }

    public void setTotalPay(double totalPay) {
        this.totalPay = totalPay;
    }

    public void setKunde(Kunde kunde) {
        this.kunde = kunde;
    }

    public List<Gericht> getBestellung() {
        return bestellung;
    }

    public void setBestellung(List<Gericht> bestellung) {
        this.bestellung = bestellung;
    }

    public String getSessionId() {
        return sessionId;
    }

    public boolean isIsOrdered() {
        return isOrdered;
    }

    public void setIsOrdered(boolean isOrdered) {
        this.isOrdered = isOrdered;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
// end of getters and setters here; from here own implemented logic 

    public void calculateTotalPay() {
        // reset the TotalPay
        this.totalPay = 0.0;
        for (Gericht g : this.bestellung) {
            this.totalPay = this.totalPay + g.getPreis() * g.getAmount();
        }
    }

    public void ipdAndSession(HttpServletRequest req) {
        ipAddress = req.getRemoteAddr();
        HttpSession sess = req.getSession();
        sessionId = sess.getId();
    }

    public void addGerichtToBestellung(Gericht g, Integer gerichteAmount) {
        g.setAmount(gerichteAmount);
        bestellung.add(g);
    }

    private void initializeMenu() {
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT idGericht, bezeichnung, preis FROM Gericht");
            while (rs.next()) {
                Gericht gericht = new Gericht(rs.getInt("idGericht"), rs.getString("BEZEICHNUNG"),
                        rs.getDouble("PREIS"));
                this.gerichte.add(gericht);

            }

        } catch (SQLException ex) {
            Logger.getLogger(OrderBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean addBestellungToDatabase() {
        Connection conn = null;
        PreparedStatement stmt = null;
        // ResultSet for call and set lastId 
        ResultSet rs = null;
        boolean success = false;
        try {
            conn = getConnection();
            if (conn == null) {
                return false;
            }
            stmt = conn.prepareStatement("INSERT INTO Bestellung (keyKunde, ipAddress, "
                    + "orderDate, sessionId, isOrdered, totalPay) "
                    + "VALUES(?,?,CURRENT_TIMESTAMP,?,?,?)");
            stmt.setInt(1, this.getKunde().getLastId());
            stmt.setString(2, this.getIpAddress().trim());
            stmt.setString(3, this.getSessionId().trim());
            stmt.setBoolean(4, this.isIsOrdered());
            stmt.setDouble(5, this.getTotalPay());
            int rows = stmt.executeUpdate();
            conn.commit();
            success = (rows == 1);
            // store the last_insert_id of the Bestellung object in lastId, 
            rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");
            if (rs.next()) {
                this.lastId = rs.getInt(1);
            } else {
                // throw an exception from here
            }
            /**
             * Now save the OrderPositions to Table OrderPosition which is a n:m
             * relation of Table Bestellung and Table Gericht.
             */

            stmt = conn.prepareStatement("INSERT INTO OrderPosition (keyOrder, keyGericht, "
                    + "amountPosition) "
                    + "VALUES(?,?, ?)");
            /**
             * The lastId from the Bestellung-Table is the key forOrder in Table
             * OrderPosition .. and it is a single value for all Gerichte, so we
             * can set ot outside the for loop
             */
            stmt.setInt(1, this.lastId);

            for (Gericht temp : this.getBestellung()) {
                stmt.setInt(2, temp.getGerichtId());
                stmt.setInt(3, temp.getAmount());
                rows = stmt.executeUpdate();
                conn.commit();
                success = (rows == 1);
            }

            return success;

        } catch (SQLException ex) {
            org.jboss.logging.Logger.getLogger(OrderBean.class.getName()).log(org.jboss.logging.Logger.Level.FATAL, null, ex);
            success = false;

        } finally {
            try {
                stmt.close();
            } catch (SQLException ex) {
                java.util.logging.Logger.getLogger(Kunde.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                conn.close();
            } catch (SQLException ex) {
                java.util.logging.Logger.getLogger(Kunde.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return success;
    }

}
