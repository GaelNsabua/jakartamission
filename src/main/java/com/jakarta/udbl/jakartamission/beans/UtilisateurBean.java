package com.jakarta.udbl.jakartamission.beans;

import com.jakarta.udbl.jakartamission.business.UtilisateurEntrepriseBean;
import com.jakarta.udbl.jakartamission.entities.Utilisateur;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import org.mindrot.jbcrypt.BCrypt;

import java.io.Serializable;
import java.util.List;

/**
 * Bean gérant les utilisateurs
 * 
 * @author gaelm
 */
@SessionScoped
@Named
public class UtilisateurBean implements Serializable {

    @EJB
    private UtilisateurEntrepriseBean utilisateurEntrepriseBean;

    @NotBlank(message = "Le nom d'utilisateur est obligatoire")
    @Size(min = 3, max = 50, message = "Le nom d'utilisateur doit avoir entre 3 et"
            + " 50 caractères")
    private String username;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "L'email doit être valide")
    private String email;

    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 6, message = "Le mot de passe doit contenir au moins 6 caractères")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$", message = "Le mot de passe doit contenir au moins une majuscule, un chiffre et un caractère spécial")
    private String password;

    @NotBlank(message = "La confirmation du mot de passe est obligatoire")
    private String confirmPassword;

    private String description;

    private Integer utilisateurIdEnEdition;
    private boolean modeEdition = false;

    public UtilisateurBean() {
    }

    /**
     * Ajoute un nouveau utilisateur
     * 
     * @return Navigation
     */
    public String ajouterUtilisateur() {
        FacesContext context = FacesContext.getCurrentInstance();
        
        // Vérifier que les champs obligatoires sont remplis
        if (username != null && !username.trim().isEmpty() &&
                email != null && !email.trim().isEmpty() &&
                password != null && !password.trim().isEmpty()) {

            // Vérifier la longueur du mot de passe
            if (password.length() < 6) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "Le mot de passe doit contenir au moins 6 caractères", null));
                return null;
            }

            // Vérifier si les mots de passe correspondent
            if (!password.equals(confirmPassword)) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "Les mots de passe ne correspondent pas", null));
                return null;
            }

            // Vérifier si l'email existe déjà
            Utilisateur utilisateurExistant = utilisateurEntrepriseBean.trouverParEmail(email);
            if (utilisateurExistant != null) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "Cet email est déjà utilisé", null));
                return null;
            }

            // Hasher le mot de passe
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

            Utilisateur utilisateur = new Utilisateur(username, email, hashedPassword, description);
            utilisateurEntrepriseBean.creer(utilisateur);

            // Ajout du message de succès
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, 
                "Utilisateur ajouté avec succès", null));

            // Réinitialiser le formulaire
            reinitialiserFormulaire();
        }

        return null; // Reste sur la même page
    }

    /**
     * Sauvegarde - redirige vers enregistrer ou modifier selon le mode
     * 
     * @return Navigation
     */
    public String sauvegarder() {
        if (modeEdition) {
            return modifier();
        } else {
            return ajouterUtilisateur();
        }
    }

    /**
     * Prépare l'édition d'un utilisateur
     * 
     * @param utilisateur L'utilisateur à éditer
     * @return Navigation vers le formulaire
     */
    public String preparerEdition(Utilisateur utilisateur) {
        this.utilisateurIdEnEdition = utilisateur.getId();
        this.username = utilisateur.getUsername();
        this.email = utilisateur.getEmail();
        this.password = utilisateur.getPassword();
        this.description = utilisateur.getDescription();
        this.modeEdition = true;

        return "/pages/ajouter_utilisateur?faces-redirect=true";
    }

    /**
     * Modifie un utilisateur existant
     * 
     * @return Navigation
     */
    public String modifier() {
        if (utilisateurIdEnEdition != null) {
            Utilisateur utilisateur = utilisateurEntrepriseBean.trouverParId(utilisateurIdEnEdition);
            if (utilisateur != null) {
                utilisateur.setUsername(username);
                utilisateur.setEmail(email);
                utilisateur.setPassword(password);
                utilisateur.setDescription(description);

                utilisateurEntrepriseBean.modifier(utilisateur);
                reinitialiserFormulaire();
            }
        }
        return null;
    }

    /**
     * Supprime un utilisateur
     * 
     * @param id L'ID de l'utilisateur à supprimer
     */
    public void supprimer(int id) {
        utilisateurEntrepriseBean.supprimer(id);
    }

    /**
     * Annule l'édition en cours
     * 
     * @return Navigation
     */
    public String annulerEdition() {
        reinitialiserFormulaire();
        return "/pages/utilisateur?faces-redirect=true";
    }

    /**
     * Réinitialise les champs du formulaire
     */
    private void reinitialiserFormulaire() {
        this.username = null;
        this.email = null;
        this.password = null;
        this.confirmPassword = null;
        this.description = null;
        this.utilisateurIdEnEdition = null;
        this.modeEdition = false;
    }

    /**
     * Récupère la liste de tous les utilisateurs
     * 
     * @return Liste des utilisateurs
     */
    public List<Utilisateur> getUtilisateurs() {
        return utilisateurEntrepriseBean.lister();
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isModeEdition() {
        return modeEdition;
    }

    public void setModeEdition(boolean modeEdition) {
        this.modeEdition = modeEdition;
    }

    public Integer getUtilisateurIdEnEdition() {
        return utilisateurIdEnEdition;
    }

    public void setUtilisateurIdEnEdition(Integer utilisateurIdEnEdition) {
        this.utilisateurIdEnEdition = utilisateurIdEnEdition;
    }
}
