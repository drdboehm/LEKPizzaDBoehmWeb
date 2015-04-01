package com.daa.model;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dboehm
 */
public class Bestellung {

    private List<Gericht> gerichte;
// "preis" need to be formatted, typed, stored and checked

    public Bestellung() {
        gerichte = new ArrayList<Gericht>();
        gerichte.add(new Gericht(1, "Pizza Calzone", 6.50));
        gerichte.add(new Gericht(2, "Spahetti Neopolitaner", 5.50));
        gerichte.add(new Gericht(3, "Bären Pizza mit Gyros", 8.51));
        gerichte.add(new Gericht(4, "Pizza Radieschen", 3.50));
        gerichte.add(new Gericht(5, "Salat vom Chef", 4.50));
        gerichte.add(new Gericht(6, "7 Stk. Pizza Brötchen Hähnchen/Käse", 4.50));
    }

    public List<Gericht> getGerichte() {
        return gerichte;
    }

    public void setGerichte(List<Gericht> gerichte) {
        this.gerichte = gerichte;
    }

}
