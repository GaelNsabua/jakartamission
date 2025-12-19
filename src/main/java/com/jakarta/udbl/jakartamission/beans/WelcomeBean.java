/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jakarta.udbl.jakartamission.beans;

import com.jakarta.udbl.jakartamission.business.SessionManager;
import com.jakarta.udbl.jakartamission.business.UtilisateurEntrepriseBean;
import com.jakarta.udbl.jakartamission.entities.Utilisateur;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 *
 * @author gaelm
 */

@RequestScoped
@Named 
public class WelcomeBean {
    @EJB
    private UtilisateurEntrepriseBean utilisateurEntrepriseBean;

    @Inject
    private SessionManager sessionManager;
    
    private String email;
    private String password;
    private String message;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String sAuthentifier() {
        Utilisateur utilisateur = utilisateurEntrepriseBean.authentifier(email, password);
        FacesContext context = FacesContext.getCurrentInstance();

        if (utilisateur != null) {
            sessionManager.createSession("utilisateurEmail", utilisateur.getEmail());
            sessionManager.createSession("utilisateurUsername", utilisateur.getUsername());
            return "home?faces-redirect=true";
        } else {
            message = "Échec de l'authentification. Veuillez vérifier vos identifiants.";
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
            return "index?faces-redirect=true";
        }
    }
}