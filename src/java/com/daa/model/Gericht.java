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
public class Gericht implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer gerichtId;
    private String bezeichnung;
    private Double preis;
    private Integer amount;

    public Gericht() {
    }

    /**
     *
     * @param gerichtId
     * @param bezeichnung
     * @param preis
     */
    public Gericht(Integer gerichtId, String bezeichnung, Double preis) {
        this.gerichtId = gerichtId;
        this.bezeichnung = bezeichnung;
        this.preis = preis;
        amount = 0;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public Double getPreis() {
        return preis;
    }

    public void setPreis(Double preis) {
        this.preis = preis;
    }

    public Integer getGerichtId() {
        return gerichtId;
    }

    public void setGerichtId(Integer gerichtId) {
        this.gerichtId = gerichtId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

}
