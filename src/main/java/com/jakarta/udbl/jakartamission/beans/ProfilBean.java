package com.jakarta.udbl.jakartamission.beans;

import com.jakarta.udbl.jakartamission.business.SessionManager;
import com.jakarta.udbl.jakartamission.business.UtilisateurEntrepriseBean;
import com.jakarta.udbl.jakartamission.entities.Utilisateur;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.mindrot.jbcrypt.BCrypt;

import java.io.Serializable;

/**
 * Bean pour la gestion du profil utilisateur
 * 
 * @author gaelm
 */
@SessionScoped
@Named
public class ProfilBean implements Serializable {

    @EJB
    private UtilisateurEntrepriseBean utilisateurEntrepriseBean;

    @Inject
    private SessionManager sessionManager;

    private String username;
    private String email;
    private String description;
    
    private String ancienMotDePasse;
    private String nouveauMotDePasse;
    private String confirmationMotDePasse;

    public ProfilBean() {
    }

    /**
     * Charge les informations du profil utilisateur connecté
     */
    @PostConstruct
    public void chargerProfil() {
        String emailSession = sessionManager.getValueFromSession("utilisateurEmail");
        if (emailSession != null) {
            Utilisateur utilisateur = utilisateurEntrepriseBean.trouverParEmail(emailSession);
            if (utilisateur != null) {
                this.username = utilisateur.getUsername();
                this.email = utilisateur.getEmail();
                this.description = utilisateur.getDescription();
            }
        }
    }

    /**
     * Met à jour la description du profil
     * @return Navigation
     */
    public String modifierProfil() {
        FacesContext context = FacesContext.getCurrentInstance();
        String emailSession = sessionManager.getValueFromSession("utilisateurEmail");
        
        if (emailSession != null) {
            Utilisateur utilisateur = utilisateurEntrepriseBean.trouverParEmail(emailSession);
            if (utilisateur != null) {
                utilisateur.setDescription(description);
                utilisateurEntrepriseBean.modifier(utilisateur);
                
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, 
                    "Profil mis à jour avec succès", null));
            }
        }
        
        return null;
    }

    /**
     * Modifie le mot de passe de l'utilisateur
     * @return Navigation
     */
    public String modifierMotDePasse() {
        FacesContext context = FacesContext.getCurrentInstance();
        
        // Vérifier que tous les champs sont remplis
        if (ancienMotDePasse == null || ancienMotDePasse.trim().isEmpty() ||
            nouveauMotDePasse == null || nouveauMotDePasse.trim().isEmpty() ||
            confirmationMotDePasse == null || confirmationMotDePasse.trim().isEmpty()) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                "Tous les champs sont obligatoires", null));
            return null;
        }

        // Vérifier que le nouveau mot de passe a au moins 6 caractères
        if (nouveauMotDePasse.length() < 6) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                "Le nouveau mot de passe doit contenir au moins 6 caractères", null));
            return null;
        }

        // Vérifier que les nouveaux mots de passe correspondent
        if (!nouveauMotDePasse.equals(confirmationMotDePasse)) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                "Les nouveaux mots de passe ne correspondent pas", null));
            return null;
        }

        String emailSession = sessionManager.getValueFromSession("utilisateurEmail");
        if (emailSession != null) {
            Utilisateur utilisateur = utilisateurEntrepriseBean.trouverParEmail(emailSession);
            if (utilisateur != null) {
                // Vérifier l'ancien mot de passe
                if (!utilisateurEntrepriseBean.verifierMotDePasse(ancienMotDePasse, utilisateur.getPassword())) {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                        "L'ancien mot de passe est incorrect", null));
                    return null;
                }

                // Hasher et mettre à jour le nouveau mot de passe
                String hashedPassword = BCrypt.hashpw(nouveauMotDePasse, BCrypt.gensalt());
                utilisateur.setPassword(hashedPassword);
                utilisateurEntrepriseBean.modifier(utilisateur);

                // Réinitialiser les champs
                this.ancienMotDePasse = null;
                this.nouveauMotDePasse = null;
                this.confirmationMotDePasse = null;

                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, 
                    "Mot de passe modifié avec succès", null));
            }
        }

        return null;
    }

    /**
     * Déconnexion de l'utilisateur
     * @return Navigation vers la page de connexion
     */
    public String seDeconnecter() {
        sessionManager.invalidateSession();
        return "/index?faces-redirect=true";
    }

    // Getters et Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAncienMotDePasse() {
        return ancienMotDePasse;
    }

    public void setAncienMotDePasse(String ancienMotDePasse) {
        this.ancienMotDePasse = ancienMotDePasse;
    }

    public String getNouveauMotDePasse() {
        return nouveauMotDePasse;
    }

    public void setNouveauMotDePasse(String nouveauMotDePasse) {
        this.nouveauMotDePasse = nouveauMotDePasse;
    }

    public String getConfirmationMotDePasse() {
        return confirmationMotDePasse;
    }

    public void setConfirmationMotDePasse(String confirmationMotDePasse) {
        this.confirmationMotDePasse = confirmationMotDePasse;
    }
}
