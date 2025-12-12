package com.jakarta.udbl.jakartamission.beans;

import com.jakarta.udbl.jakartamission.business.LieuEntrepriseBean;
import com.jakarta.udbl.jakartamission.entities.Lieu;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * Bean gérant les lieux touristiques en Indonésie
 * 
 * @author gaelm
 */
@RequestScoped
@Named
public class LieuBean implements Serializable {
    
    @EJB
    private LieuEntrepriseBean lieuEntrepriseBean;
    
    private String nom;
    private String description;
    private Double latitude;
    private Double longitude;
    
    public LieuBean() {
    }
    
    /**
     * Enregistre un nouveau lieu
     * @return Navigation vers la même page
     */
    public String enregistrer() {
        if (nom != null && !nom.trim().isEmpty() && 
            description != null && !description.trim().isEmpty() &&
            latitude != null && longitude != null) {
            
            Lieu lieu = new Lieu(nom, description, longitude, latitude);
            lieuEntrepriseBean.creer(lieu);
            
            // Réinitialiser le formulaire
            this.nom = null;
            this.description = null;
            this.latitude = null;
            this.longitude = null;
        }
        
        return null; // Reste sur la même page
    }
    
    /**
     * Récupère la liste de tous les lieux
     * @return Liste des lieux
     */
    public List<Lieu> getLieux() {
        return lieuEntrepriseBean.lister();
    }
    
    // Getters et Setters
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
