/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.daa.model;

import com.daa.util.GConnection;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import org.jboss.logging.Logger;

/**
 *
 * @author dboehm
 */
public class Kunde extends GConnection implements Serializable {

    private static final long serialVersionUID = 1L;
    private String username;
    private String password;
    private String vorname;
    private String nachname;
    private String strasse;
    private String hausnr;
    private String plz;
    private String ort;
    private Integer lastId;

    /**
     * The constructor initializes a minimal OrderAddress object sufficient for
     * delivery of an order
     *
     * @param vorname
     * @param nachname
     * @param strasseNumber
     * @param plzOrt
     *
     */
    public Kunde() {
        username = "";
        vorname = "";
        nachname = "";
        strasse = "";
        hausnr = "";
        plz = "";
        ort = "";
    }

    public Integer getLastId() {
        return lastId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLastId(Integer lastId) {
        this.lastId = lastId;
    }

    public Kunde(String username, String vorname, String nachname, String strasse, String hausnr, String plz, String ort) {
        this.username = username;
        this.vorname = vorname;
        this.nachname = nachname;
        this.strasse = strasse;
        this.hausnr = hausnr;
        this.plz = plz;
        this.ort = ort;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getStrasse() {
        return strasse;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    public String getPlz() {
        return plz;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

    public String getHausnr() {
        return hausnr;
    }

    public void setHausnr(String hausnr) {
        this.hausnr = hausnr;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    @Override
    public String toString() {
        String out = String.format("Kunde: Vorname=%s%nName: ", vorname);
        // return "Kunde:" + "\nVorname=" + vorname + ", nachname=" + nachname + ", strasseNumber=" + strasseNumber + ", plzOrt=" + plzOrt + '}';
        return out;
    }

//    public boolean store() {
//        Connection conn = null;
//        PreparedStatement stmt = null;
//        // ResultSet for call and set lastId 
//        ResultSet rs = null;
//        boolean success = false;
//        try {
//            conn = getConnection();
//            if (conn == null) {
//                return false;
//            }
//            stmt = conn.prepareStatement("INSERT INTO Kunde (username, vorname, "
//                    + "nachname, strasse, hausnr, PLZ, Ort, firstEntryDate, lastEntryDate) "
//                    + "VALUES(?,?,?,?,?,?,?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)");
//            stmt.setString(1, this.username.trim());
//            stmt.setString(2, this.vorname.trim());
//            stmt.setString(3, this.nachname.trim());
//            stmt.setString(4, this.strasse.trim());
//            stmt.setString(5, this.hausnr.trim());
//            stmt.setString(6, this.plz.trim());
//            stmt.setString(7, this.ort.trim());
//            int rows = stmt.executeUpdate();
//            conn.commit();
//            success = (rows == 1);
//            // store the last_insert_id of the Kunde object in lastId, 
//            rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");
//            if (rs.next()) {
//                this.lastId = rs.getInt(1);
//            } else {
//                // throw an exception from here
//            }
//
//        } catch (SQLException ex) {
//            Logger.getLogger(Kunde.class.getName()).log(Logger.Level.FATAL, null, ex);
//            success = false;
//        } finally {
//            try {
//                stmt.close();
//            } catch (SQLException ex) {
//                java.util.logging.Logger.getLogger(Kunde.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            try {
//                conn.close();
//            } catch (SQLException ex) {
//                java.util.logging.Logger.getLogger(Kunde.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        return success;
//    }

}
