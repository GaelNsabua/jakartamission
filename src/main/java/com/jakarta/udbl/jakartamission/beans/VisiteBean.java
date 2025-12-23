package com.jakarta.udbl.jakartamission.beans;

import com.jakarta.udbl.jakartamission.business.LieuEntrepriseBean;
import com.jakarta.udbl.jakartamission.business.UtilisateurEntrepriseBean;
import com.jakarta.udbl.jakartamission.business.VisiteEntrepriseBean;
import com.jakarta.udbl.jakartamission.business.SessionManager;
import com.jakarta.udbl.jakartamission.entities.Lieu;
import com.jakarta.udbl.jakartamission.entities.Utilisateur;
import com.jakarta.udbl.jakartamission.entities.Visite;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.inject.Inject;
import com.jakarta.udbl.jakartamission.beans.LieuBean;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * Managed bean pour la gestion des visites
 *
 * @author gaelm
 */
@SessionScoped
@Named
public class VisiteBean implements Serializable {

    @EJB
    private VisiteEntrepriseBean visiteEntrepriseBean;

    @EJB
    private UtilisateurEntrepriseBean utilisateurEntrepriseBean;

    @EJB
    private LieuEntrepriseBean lieuEntrepriseBean;

    private LocalDate dateVisite;
    private Integer tempsPasse;
    private String observations;
    private Integer depense;

    private Integer lieuId;
    private String lieuNom;

    @Inject
    private SessionManager sessionManager;

    @Inject
    private LieuBean lieuBean;

    // edition disabled: visits are created and deleted only

    public VisiteBean() {
    }

    /**
     * Prépare le formulaire d'ajout en récupérant le lieu sélectionné en session (si présent)
     */
    public void preparerDepuisSession() {
        if (this.lieuId == null && lieuBean != null) {
            Integer sel = lieuBean.getSelectedLieu();
            if (sel != null) {
                this.lieuId = sel;
            }
        }
        if (this.dateVisite == null) {
            this.dateVisite = LocalDate.now();
        }
        // Récupérer et préremplir la météo pour le lieu sélectionné
        if (this.lieuId != null && lieuBean != null) {
            Lieu lieu = lieuEntrepriseBean.trouverParId(this.lieuId);
            if (lieu != null) {
                try {
                    lieuBean.setSelectedLieu(this.lieuId);
                    lieuBean.fetchWeatherMessage(lieu);
                    // Préremplir le nom du lieu pour l'affichage
                    this.lieuNom = lieu.getNom();
                } catch (Exception ex) {
                    lieuBean.setWeatherMessage("Météo non disponible");
                    this.lieuNom = lieu.getNom();
                }
            }
        }
    }

    public String ajouterVisite() {
        FacesContext context = FacesContext.getCurrentInstance();

        // Vérifications minimales
        if (lieuId == null) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Le lieu est obligatoire", null));
            return null;
        }

        // Récupérer l'email de l'utilisateur connecté depuis la session
        String emailSession = sessionManager.getValueFromSession("utilisateurEmail");
        if (emailSession == null) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Utilisateur non connecté", null));
            return null;
        }

        Utilisateur utilisateur = utilisateurEntrepriseBean.trouverParEmail(emailSession);
        Lieu lieu = lieuEntrepriseBean.trouverParId(lieuId);
        if (utilisateur == null || lieu == null) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Utilisateur ou lieu introuvable", null));
            return null;
        }

        LocalDate date = dateVisite != null ? dateVisite : LocalDate.now();

        Visite visite = new Visite(date, tempsPasse, observations, depense, utilisateur, lieu);
        visiteEntrepriseBean.creer(visite);

        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Visite enregistrée", null));
        reinitialiserFormulaire();
        return null;
    }

    // edition and modification removed: visits are immutable after creation

    public void supprimer(int id) {
        visiteEntrepriseBean.supprimer(id);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Visite supprimée", null));
    }

    private void reinitialiserFormulaire() {
        this.dateVisite = null;
        this.tempsPasse = null;
        this.observations = null;
        this.depense = null;
        this.lieuId = null;
        this.lieuNom = null;
    }

    public List<Visite> getVisites() {
        return visiteEntrepriseBean.lister();
    }

    // Getters / Setters
    public LocalDate getDateVisite() {
        return dateVisite;
    }

    public void setDateVisite(LocalDate dateVisite) {
        this.dateVisite = dateVisite;
    }

    public Integer getTempsPasse() {
        return tempsPasse;
    }

    public void setTempsPasse(Integer tempsPasse) {
        this.tempsPasse = tempsPasse;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public Integer getDepense() {
        return depense;
    }

    public void setDepense(Integer depense) {
        this.depense = depense;
    }

    public Integer getLieuId() {
        return lieuId;
    }

    public void setLieuId(Integer lieuId) {
        this.lieuId = lieuId;
    }

    public String getLieuNom() {
        return lieuNom;
    }

    public void setLieuNom(String lieuNom) {
        this.lieuNom = lieuNom;
    }

    // edit-related getters/setters removed
}
