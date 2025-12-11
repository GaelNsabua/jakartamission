/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jakarta.udbl.jakartamission.beans;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

/**
 *
 * @author gaelm
 */

@RequestScoped
@Named 
public class WelcomeBean {
    private String nom;
    private String message;
    private double montant;
    private String deviseSource;
    private String deviseCible;
    private double resultat;
    private String resultConversion;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    public void afficher() {
        this.message = "Welcome to Indonesia dear " + this.nom;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public String getDeviseSource() {
        return deviseSource;
    }

    public void setDeviseSource(String deviseSource) {
        this.deviseSource = deviseSource;
    }

    public String getDeviseCible() {
        return deviseCible;
    }

    public void setDeviseCible(String deviseCible) {
        this.deviseCible = deviseCible;
    }

    public double getResultat() {
        return resultat;
    }

    public void setResultat(double resultat) {
        this.resultat = resultat;
    }

    public String getResultConversion() {
        return resultConversion;
    }

    public void setResultConversion(String resultConversion) {
        this.resultConversion = resultConversion;
    }

    public void convertir() {
        // Conversion simplifiée
        if (deviseSource.equals("CDF") && deviseCible.equals("IDR")) {
            resultat = montant * 7.09;
        } else if (deviseSource.equals("IDR") && deviseCible.equals("CDF")) {
            resultat = montant / 7.09;
        } else if (deviseSource.equals("USD") && deviseCible.equals("IDR")) {
            resultat = montant * 16660;
        } else if (deviseSource.equals("IDR") && deviseCible.equals("USD")) {
            resultat = montant / 16660;
        } else if (deviseSource.equals("USD") && deviseCible.equals("CDF")) {
            resultat = montant * 2232;
        } else if (deviseSource.equals("CDF") && deviseCible.equals("USD")) {
            resultat = montant / 2232;
        } else {
            resultat = montant;
        }

        getConversionDetails();
    }

    public void getConversionDetails() {
        this.resultConversion = montant + " " + deviseSource + " = " + resultat + " " + 
            deviseCible;
    }
    
}
