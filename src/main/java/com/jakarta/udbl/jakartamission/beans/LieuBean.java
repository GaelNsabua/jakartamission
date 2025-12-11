package com.jakarta.udbl.jakartamission.beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Bean gérant les lieux touristiques en Indonésie
 * 
 * @author gaelm
 */
@SessionScoped
@Named
public class LieuBean implements Serializable {
    
    private String nom;
    private String description;
    private Double latitude;
    private Double longitude;
    
    private List<Lieu> lieux;
    
    public LieuBean() {
        this.lieux = new ArrayList<>();
    }
    
    /**
     * Enregistre un nouveau lieu
     * @return Navigation vers la même page
     */
    public String enregistrer() {
        if (nom != null && !nom.trim().isEmpty() && 
            description != null && !description.trim().isEmpty() &&
            latitude != null && longitude != null) {
            
            Lieu lieu = new Lieu(nom, description, latitude, longitude);
            lieux.add(lieu);
            
            // Réinitialiser le formulaire
            this.nom = null;
            this.description = null;
            this.latitude = null;
            this.longitude = null;
        }
        
        return null; // Reste sur la même page
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

    public List<Lieu> getLieux() {
        return lieux;
    }

    public void setLieux(List<Lieu> lieux) {
        this.lieux = lieux;
    }
    
    /**
     * Classe interne représentant un lieu
     */
    public static class Lieu implements Serializable {
        private String nom;
        private String description;
        private Double latitude;
        private Double longitude;
        
        public Lieu() {
        }
        
        public Lieu(String nom, String description, Double latitude, Double longitude) {
            this.nom = nom;
            this.description = description;
            this.latitude = latitude;
            this.longitude = longitude;
        }

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
}
