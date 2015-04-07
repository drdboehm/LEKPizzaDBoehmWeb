/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.daa.model;

import java.io.Serializable;

/**
 *
 * @author dboehm
 */
public class Kunde implements Serializable {

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


}
