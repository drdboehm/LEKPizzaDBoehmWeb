package com.daa.ctrl;

import com.daa.model.Bestellung;
import com.daa.model.Gericht;
import com.daa.model.Kunde;
import com.daa.util.GConnection;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import sun.awt.X11.XConstants;

@Named(value = "bestellController")
@SessionScoped
public class BestellController extends GConnection implements Serializable {

    private Kunde currentKunde = new Kunde();
    private Integer lastKundeId;
    private Bestellung current;
    private List<Gericht> gerichte = new ArrayList<>();
    private List<Gericht> bestellGerichte = new ArrayList<>();
    private Connection conn;
    private Statement stmt;
    private ResultSet rs;
    private boolean isKundeSet = false;
    private boolean areGerichteChoosen = false;

    @PostConstruct
    public void init() {
        conn = GConnection.getConnection();
        this.initializeMenu();
    }

    public boolean isIsKundeSet() {
        return isKundeSet;
    }

    public void resetKunde() {
        isKundeSet = false;
    }

    public boolean isAreGerichteChoosen() {
        return areGerichteChoosen;
    }

    public void resetGerichte() {
        areGerichteChoosen = false;
    }
    
    public BestellController() {

    }

    public Kunde getCurrentKunde() {
        return currentKunde;
    }

    public void setCurrentKunde(Kunde currentKunde) {
        this.currentKunde = currentKunde;
    }

    public Integer getLastKundeId() {
        return lastKundeId;
    }

    public void setLastKundeId(Integer lastKundeId) {
        this.lastKundeId = lastKundeId;
    }

    public Bestellung getCurrent() {
        return current;
    }

    public void setCurrent(Bestellung current) {
        this.current = current;
    }

    public List<Gericht> getGerichte() {
        return gerichte;
    }

    public void setGerichte(List<Gericht> gerichte) {
        this.gerichte = gerichte;
    }

    public List<Gericht> getBestellGerichte() {
        return bestellGerichte;
    }

    public void setBestellGerichte(List<Gericht> bestellGerichte) {
        this.bestellGerichte = bestellGerichte;
    }

    public void initializeMenu() {
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT idGericht, bezeichnung, preis FROM Gericht");
            while (rs.next()) {
                Gericht gericht = new Gericht(rs.getInt("idGericht"), rs.getString("BEZEICHNUNG"),
                        rs.getDouble("PREIS"));
                this.gerichte.add(gericht);
//                System.err.println("Anzahl Gerichte: " + this.gerichte.size());
            }

        } catch (SQLException ex) {
            Logger.getLogger(BestellController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @PreDestroy
    public void delete() {
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(BestellController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String saveGerichte() {
        current = new Bestellung();
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        ipdAndSession(req);
        Double totalPay = 0.0;
        for (Gericht g : gerichte) {
            if (g.getAmount() != 0) {
                totalPay += g.getPreis() * g.getAmount();
                bestellGerichte.add(g);
                System.out.print(g.getBezeichnung() + " " + g.getAmount());
            }
        }
        current.setKeyKunde(currentKunde);
        current.setTotalPay(BigDecimal.valueOf(totalPay));
        areGerichteChoosen = true;
        return "index";
    }

    public void ipdAndSession(HttpServletRequest req) {
        current.setIpAddress(req.getRemoteAddr());
        HttpSession sess = req.getSession();
        current.setSessionId(sess.getId());
    }

    public String saveKunde() {
// make NOT persitent here
//        if (storeKunde()) {
        isKundeSet = true;
        return "index";
//        } else {
//            return "index";
//        }
    }

    public boolean storeKunde() {
        Connection conn = null;
        PreparedStatement stmt = null;
        // ResultSet for call and set lastId 
        ResultSet rs = null;
        boolean success = false;
        try {
            conn = getConnection();
            if (conn == null) {
                return success;
            }
            stmt = conn.prepareStatement("INSERT INTO Kunde (username, vorname, "
                    + "nachname, strasse, hausnr, PLZ, Ort, firstEntryDate, lastEntryDate) "
                    + "VALUES(?,?,?,?,?,?,?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)");
            stmt.setString(1, currentKunde.getUsername().trim());
            stmt.setString(2, currentKunde.getVorname().trim());
            stmt.setString(3, currentKunde.getNachname().trim());
            stmt.setString(4, currentKunde.getStrasse().trim());
            stmt.setString(5, currentKunde.getHausnr().trim());
            stmt.setString(6, currentKunde.getPlz().trim());
            stmt.setString(7, currentKunde.getOrt().trim());
            int rows = stmt.executeUpdate();
            conn.commit();
            success = (rows == 1);
            // storeKunde the last_insert_id of the Kunde object in lastId, 
            rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");
            if (rs.next()) {
                this.lastKundeId = rs.getInt(1);
            } else {
                // throw an exception from here
            }

        } catch (SQLException ex) {
            org.jboss.logging.Logger.getLogger(Kunde.class.getName()).log(org.jboss.logging.Logger.Level.FATAL, null, ex);
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
