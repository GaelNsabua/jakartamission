package com.jakarta.udbl.jakartamission.beans;

import com.jakarta.udbl.jakartamission.business.LieuEntrepriseBean;
import com.jakarta.udbl.jakartamission.entities.Lieu;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * Bean gérant les lieux touristiques en Indonésie
 * 
 * @author gaelm
 */
@SessionScoped
@Named
public class LieuBean implements Serializable {
    
    @EJB
    private LieuEntrepriseBean lieuEntrepriseBean;
    
    private String nom;
    private String description;
    private Double latitude;
    private Double longitude;
    
    private Integer lieuIdEnEdition;
    private boolean modeEdition = false;
    
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
            reinitialiserFormulaire();
        }
        
        return null; // Reste sur la même page
    }
    
    /**
     * Sauvegarde un lieu (création ou modification selon le mode)
     * @return Navigation
     */
    public String sauvegarder() {
        if (modeEdition) {
            return modifier();
        } else {
            return enregistrer();
        }
    }
    
    /**
     * Prépare l'édition d'un lieu
     * @param lieu Le lieu à éditer
     * @return Navigation vers la page d'ajout
     */
    public String preparerEdition(Lieu lieu) {
        this.lieuIdEnEdition = lieu.getId();
        this.nom = lieu.getNom();
        this.description = lieu.getDescription();
        this.latitude = lieu.getLatitude();
        this.longitude = lieu.getLongitude();
        this.modeEdition = true;
        return "/pages/ajouter?faces-redirect=true";
    }
    
    /**
     * Modifie un lieu existant
     * @return Navigation
     */
    public String modifier() {
        if (lieuIdEnEdition != null && nom != null && !nom.trim().isEmpty() && 
            description != null && !description.trim().isEmpty() &&
            latitude != null && longitude != null) {
            
            Lieu lieu = lieuEntrepriseBean.trouverParId(lieuIdEnEdition);
            if (lieu != null) {
                lieu.setNom(nom);
                lieu.setDescription(description);
                lieu.setLatitude(latitude);
                lieu.setLongitude(longitude);
                lieuEntrepriseBean.modifier(lieu);
            }
            
            reinitialiserFormulaire();
        }
        
        return null;
    }
    
    /**
     * Supprime un lieu
     * @param id L'identifiant du lieu à supprimer
     * @return Navigation
     */
    public String supprimer(int id) {
        lieuEntrepriseBean.supprimer(id);
        reinitialiserFormulaire();
        return null;
    }
    
    /**
     * Annule l'édition en cours
     * @return Navigation
     */
    public String annulerEdition() {
        reinitialiserFormulaire();
        return null;
    }
    
    /**
     * Réinitialise le formulaire
     */
    private void reinitialiserFormulaire() {
        this.nom = null;
        this.description = null;
        this.latitude = null;
        this.longitude = null;
        this.lieuIdEnEdition = null;
        this.modeEdition = false;
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

    public Integer getLieuIdEnEdition() {
        return lieuIdEnEdition;
    }

    public void setLieuIdEnEdition(Integer lieuIdEnEdition) {
        this.lieuIdEnEdition = lieuIdEnEdition;
    }

    public boolean isModeEdition() {
        return modeEdition;
    }

    public void setModeEdition(boolean modeEdition) {
        this.modeEdition = modeEdition;
    }
}
