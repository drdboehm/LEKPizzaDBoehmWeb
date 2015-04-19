/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.daa.ctrl;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

/**
 *
 * @author dboehm
 */
@Named
@RequestScoped
public class LogoutManager {

    /**
     * Creates a new instance of LogoutManager
     */
    
    public LogoutManager() {
    }
    
    public String logout(){
        /*
        we need the session to invalidate it
        */
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpSession session = (HttpSession) externalContext.getSession(true);
        session.invalidate();
        return "index?faces-redirect=true";
        
    }
    
    
}
