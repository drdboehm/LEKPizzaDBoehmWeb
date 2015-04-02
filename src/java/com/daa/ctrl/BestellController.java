package com.daa.ctrl;

import com.daa.model.Bestellung;
import com.daa.model.Gericht;
import com.daa.util.GConnection;

import java.io.Serializable;
import java.sql.Connection;
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
import javax.inject.Named;

@Named(value = "bestellController")
@SessionScoped
public class BestellController extends GConnection implements Serializable {
//public class BestellController implements Serializable {

    private Bestellung current;
    private List<Gericht> gerichte = new ArrayList<>();
    private Connection conn;
    private Statement stmt;
    private ResultSet rs;

    @PostConstruct
    public void init() {
        conn = GConnection.getConnection();
        this.initializeMenu();
    }

    public BestellController() {

    }

    public List<Gericht> getGerichte() {
        return gerichte;
    }

    public void setGerichte(List<Gericht> gerichte) {
        this.gerichte = gerichte;
    }

    public void initializeMenu() {
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT idGericht, bezeichnung, preis FROM gericht");
            while (rs.next()) {
                Gericht gericht = new Gericht(rs.getInt("idGericht"), rs.getString("BEZEICHNUNG"),
                        rs.getDouble("PREIS"));
                this.gerichte.add(gericht);
                System.err.println("Anzahl Gerichte: " + this.gerichte.size());
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
}
