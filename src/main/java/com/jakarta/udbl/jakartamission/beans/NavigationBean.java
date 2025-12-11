/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jakarta.udbl.jakartamission.beans;

import java.io.IOException;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

/**
 *
 * @author gaelm
 * Navigation implicite entre les pages
 */

@Named(value = "navigationController")
@RequestScoped
public class NavigationBean {
    public void voirAccueil() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("../home.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void voirAjouter() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("pages/ajouter.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void voirVisiter() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("pages/lieu.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void voirApropos() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("pages/a_propos.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
