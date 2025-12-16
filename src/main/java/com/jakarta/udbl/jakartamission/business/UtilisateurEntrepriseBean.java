package com.jakarta.udbl.jakartamission.business;

import com.jakarta.udbl.jakartamission.entities.Utilisateur;
import jakarta.ejb.Stateless;
import jakarta.ejb.LocalBean;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;

/**
 * EJB pour la gestion des utilisateurs
 * 
 * @author gaelm
 */
@Stateless
@LocalBean
public class UtilisateurEntrepriseBean {
    
    @PersistenceContext(unitName = "indonesiaPU")
    private EntityManager em;
    
    /**
     * Créer un nouveau utilisateur
     * @param utilisateur L'utilisateur à créer
     */
    public void creer(Utilisateur utilisateur) {
        em.persist(utilisateur);
    }
    
    /**
     * Lister tous les utilisateurs
     * @return Liste de tous les utilisateurs
     */
    public List<Utilisateur> lister() {
        return em.createQuery("SELECT u FROM Utilisateur u ORDER BY u.id DESC", Utilisateur.class)
                 .getResultList();
    }
    
    /**
     * Trouver un utilisateur par son ID
     * @param id L'identifiant de l'utilisateur
     * @return L'utilisateur trouvé ou null
     */
    public Utilisateur trouverParId(int id) {
        return em.find(Utilisateur.class, id);
    }

    /**
     * Trouver un utilisateur par son Email
     * @param id L'identifiant de l'utilisateur
     * @return L'utilisateur trouvé ou null
     */
    public Utilisateur trouverParEmail(String email) {
        try{
            return em.createQuery("SELECT u FROM Utilisateur u WHERE u.email = :email", Utilisateur.class)
                     .setParameter("email", email)
                     .getSingleResult();
        } catch (jakarta.persistence.NoResultException e) {
            return null;
        }
    }
    
    /**
     * Modifier un utilisateur existant
     * @param utilisateur L'utilisateur à modifier
     */
    public void modifier(Utilisateur utilisateur) {
        em.merge(utilisateur);
    }
    
    /**
     * Supprimer un utilisateur
     * @param id L'identifiant de l'utilisateur à supprimer
     */
    @Transactional
    public void supprimer(int id) {
        Utilisateur utilisateur = trouverParId(id);
        if (utilisateur != null) {
            em.remove(utilisateur);
        }
    }
    
    /**
     * Méthode pour vérifier un mot de passe
     * @param password Le mot de passe en clair
     * @param hashedPassword Le mot de passe hashé
     * @return true si le mot de passe correspond, false sinon
     */
    public boolean verifierMotDePasse(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}
