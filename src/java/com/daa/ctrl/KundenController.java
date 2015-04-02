package com.daa.ctrl;

import com.daa.model.Bestellung;
import com.daa.model.Kunde;
import com.daa.util.GConnection;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named(value = "kundenController")
@SessionScoped
public class KundenController extends GConnection implements Serializable {

    private Kunde currentKunde = new Kunde();
    private Bestellung bestellung;
    
    public KundenController() {
    }

    public Kunde getCurrentKunde() {
        return currentKunde;
    }

    public void setCurrentKunde(Kunde currentKunde) {
        this.currentKunde = currentKunde;
    }


    public Bestellung getBestellung() {
        return bestellung;
    }

    public void setBestellung(Bestellung bestellung) {
        this.bestellung = bestellung;
    }
    
    public Kunde createKunde() {
        return currentKunde = new Kunde();  
    } 
    public String save() {
        return "kunden";
    }
    
}
