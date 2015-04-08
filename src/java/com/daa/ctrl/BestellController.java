package com.daa.ctrl;

import com.daa.ejb.TransmitSaveBestellungSessionBeanRemote;
import com.daa.model.Bestellung;
import com.daa.model.Gericht;
import com.daa.model.Kunde;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
//

@Named(value = "bestellController")
@SessionScoped
public class BestellController implements Serializable {

    @EJB
    private TransmitSaveBestellungSessionBeanRemote transmitSaveBestellungSessionBean;
    private static final long serialVersionUID = 1L;

    private Kunde currentKunde = new Kunde();
    private Integer lastKundeId;
    private Bestellung current;
    private List<Gericht> gerichte = new ArrayList<>();
    private List<Gericht> bestellGerichte = new ArrayList<>();
    private boolean isKundeSet = false;
    private boolean areGerichteChoosen = false;

    @PostConstruct
    public void init() {
        gerichte = transmitSaveBestellungSessionBean.initializeMenu();
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
         bestellGerichte.clear();
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

    public String saveGerichte() {
        current = new Bestellung();
        HttpServletRequest req
                = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        ipdAndSession(req);
        Double totalPay = 0.0;
        for (Gericht g : gerichte) {
            if (g.getAmount() != 0) {
                totalPay += g.getPreis() * g.getAmount();
                bestellGerichte.add(g);
//                System.out.print(g.getBezeichnung() + " " + g.getAmount());
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
        isKundeSet = true;
        return "index";
    }

    public String placeBestellung() {
        if (transmitSaveBestellungSessionBean.storeEjb(current)) {
            return "bestellung";
        } else {
            return "error";
        }
    }

}
