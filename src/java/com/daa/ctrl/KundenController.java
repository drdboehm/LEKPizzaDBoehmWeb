package com.daa.ctrl;

import com.daa.model.Bestellung;
import com.daa.model.Kunde;
import com.daa.util.GConnection;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.jboss.logging.Logger;

@ManagedBean(name = "kundenController")
@SessionScoped
public class KundenController extends GConnection implements Serializable {

    private Kunde currentKunde;
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
    
}
