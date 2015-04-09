/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.daa.util;

//import javax.annotation.ManagedBean;
import com.daa.model.BestellWrapper;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 *
 * @author teilnehmer
 */
@ManagedBean
@SessionScoped
public class NewJSFManagedBean implements Serializable {

    /**
     * Creates a new instance of NewJSFManagedBean
     */
    public NewJSFManagedBean() {
    }
    @Resource(mappedName = "jms/myPizzaBestellConnectionFactory")
    private static ConnectionFactory myPizzaBestellConnectionFactory;
    @Resource(mappedName = "jms/myPizzaBestellQueue")
    private static Queue myPizzaBestellQueue;

    public static void produceBestellung(BestellWrapper wrappedBestellung) {
        MessageProducer messageProducer;
        TextMessage textMessage;
        ObjectMessage objMessage;

       
            System.out.println("Has ObjectWrapper a reference ? " + wrappedBestellung.toString());
//        System.out.println("Has myPizzaBestellConnectionFactory a reference ? " + myPizzaBestellConnectionFactory.toString());
//            System.out.println("Has myPizzaBestellQueue a reference ? " + myPizzaBestellQueue.toString());
        try {
            Connection connection = myPizzaBestellConnectionFactory.createConnection();
//            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//            messageProducer = session.createProducer((Destination) myPizzaBestellQueue);
////            objMessage = session.createObjectMessage(bestellWrapper);
////            messageProducer.send(objMessage);
////            messageProducer.close();
////            session.close();
////            connection.close();
//
//            textMessage = session.createTextMessage();
//            textMessage.setText("Testing, 1, 2, 3. Can you hear me?");
//            System.out.println("Sending the following message: "
//                    + textMessage.getText());
//            messageProducer.send(textMessage);
//
//            textMessage.setText("Do you copy?");
//            System.out.println("Sending the following message: "
//                    + textMessage.getText());
//            messageProducer.send(textMessage);
//
//            textMessage.setText("Good bye!");
//            System.out.println("Sending the following message: "
//                    + textMessage.getText());
//            messageProducer.send(textMessage);
//            messageProducer.close();
//            session.close();
//            connection.close();

        } catch (JMSException ex) {
            Logger.getLogger(NewJSFManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
