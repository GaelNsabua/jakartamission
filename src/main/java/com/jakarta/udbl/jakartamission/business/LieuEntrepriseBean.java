package com.jakarta.udbl.jakartamission.business;

import com.jakarta.udbl.jakartamission.entities.Lieu;
import jakarta.ejb.Stateless;
import jakarta.ejb.LocalBean;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

/**
 * EJB pour la gestion des lieux touristiques
 * 
 * @author gaelm
 */
@Stateless
@LocalBean
public class LieuEntrepriseBean {
    
    @PersistenceContext(unitName = "indonesiaPU")
    private EntityManager em;
    
    /**
     * Créer un nouveau lieu
     * @param lieu Le lieu à créer
     */
    public void creer(Lieu lieu) {
        em.persist(lieu);
    }
    
    /**
     * Lister tous les lieux
     * @return Liste de tous les lieux
     */
    public List<Lieu> lister() {
        return em.createQuery("SELECT l FROM Lieu l ORDER BY l.id DESC", Lieu.class)
                 .getResultList();
    }
    
    /**
     * Trouver un lieu par son ID
     * @param id L'identifiant du lieu
     * @return Le lieu trouvé ou null
     */
    public Lieu trouverParId(int id) {
        return em.find(Lieu.class, id);
    }
    
    /**
     * Modifier un lieu existant
     * @param lieu Le lieu à modifier
     */
    public void modifier(Lieu lieu) {
        em.merge(lieu);
    }
    
    /**
     * Supprimer un lieu
     * @param id L'identifiant du lieu à supprimer
     */
    public void supprimer(int id) {
        Lieu lieu = trouverParId(id);
        if (lieu != null) {
            em.remove(lieu);
        }
    }
}
