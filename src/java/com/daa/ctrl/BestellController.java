package com.daa.ctrl;

import com.daa.ejb.TransmitSaveBestellungSessionBeanRemote;
import com.daa.model.BestellWrapper;
import com.daa.model.Bestellung;
import com.daa.model.Gericht;
import com.daa.model.Kunde;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
//

@Named(value = "bestellController")
@SessionScoped
public class BestellController implements Serializable {

    @EJB
    private TransmitSaveBestellungSessionBeanRemote transmitSaveBestellungSessionBean;
    private static final long serialVersionUID = 1L;

    @Resource(mappedName = "jms/myPizzaBestellConnectionFactory")
    private ConnectionFactory myPizzaBestellConnectionFactory;
    @Resource(mappedName = "jms/myPizzaBestellQueue")
    private Queue myPizzaBestellQueue;

    private BestellWrapper wrappedBestellung;
    private Kunde currentKunde = new Kunde();
    private Integer lastKundeId;
    private Bestellung current;
    private List<Gericht> gerichte = new ArrayList<>();
    private List<Gericht> bestellGerichte = new ArrayList<>();
    private boolean isKundeSet = false;
    private boolean areGerichteChoosen = false;
    private Double totalPay = 0.0;

    @PostConstruct
    public void init() {
        gerichte = transmitSaveBestellungSessionBean.initializeMenu();
    }

    public BestellWrapper getWrappedBestellung() {
        return wrappedBestellung;
    }

    public void setWrappedBestellung(BestellWrapper wrappedBestellung) {
        this.wrappedBestellung = wrappedBestellung;
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

    public Double getTotalPay() {
        return totalPay;
    }

    public void setTotalPay(Double totalPay) {
        this.totalPay = totalPay;
    }
    

    public String saveGerichte() {
        current = new Bestellung();
        /*
        Get clients IP address
         */
        HttpServletRequest req
                = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        ipdAndSession(req);
        totalPay = 0.0;
        for (Gericht g : gerichte) {
            if (g.getAmount() != 0) {
                totalPay += g.getPreis() * g.getAmount();
                bestellGerichte.add(g);
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
        // set the orderDate and IsOrdered = true
        current.setOrderDate(new Date());
        current.setIsOrdered(true);
        wrappedBestellung = new BestellWrapper();
        wrappedBestellung.setBestellung(current);
        wrappedBestellung.setGerichte(bestellGerichte);
        wrappedBestellung.setKunde(currentKunde);
        produceBestellung(wrappedBestellung);
        return "bestellung";
    }

    public void produceBestellung(BestellWrapper wrappedBestellung) {
        MessageProducer messageProducer;
//        TextMessage textMessage;
        ObjectMessage objMessage;

        System.out.println("BestellWrapper reference ? " + wrappedBestellung.toString());
        System.out.println("Has myPizzaBestellConnectionFactory a reference ? " + myPizzaBestellConnectionFactory.toString());
        System.out.println("Has myPizzaBestellQueue a reference ? " + myPizzaBestellQueue.toString());
        try {
            try (Connection connection = myPizzaBestellConnectionFactory.createConnection(); 
                    Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE)) {
                messageProducer = session.createProducer((Destination) myPizzaBestellQueue);
                objMessage = session.createObjectMessage(wrappedBestellung);
                messageProducer.send(objMessage);
                messageProducer.close();
            }
        } catch (JMSException jex) {
            jex.printStackTrace();
        }
    }
}
