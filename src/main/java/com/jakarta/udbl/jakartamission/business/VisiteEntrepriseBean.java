package com.jakarta.udbl.jakartamission.business;

import com.jakarta.udbl.jakartamission.entities.Visite;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

/**
 * EJB pour la gestion des visites
 *
 * @author gaelm
 */
@Stateless
@LocalBean
public class VisiteEntrepriseBean {

    @PersistenceContext(unitName = "indonesiaPU")
    private EntityManager em;

    /**
     * Créer une nouvelle visite
     */
    public void creer(Visite visite) {
        em.persist(visite);
    }

    /**
     * Lister toutes les visites
     */
    public List<Visite> lister() {
        return em.createQuery("SELECT v FROM Visite v ORDER BY v.id DESC", Visite.class)
                 .getResultList();
    }

    /**
     * Trouver par id
     */
    public Visite trouverParId(int id) {
        return em.find(Visite.class, id);
    }

    /**
     * Modifier
     */
    public void modifier(Visite visite) {
        em.merge(visite);
    }

    /**
     * Supprimer
     */
    public void supprimer(int id) {
        Visite v = trouverParId(id);
        if (v != null) {
            em.remove(v);
        }
    }

    /**
     * Lister les visites d'un utilisateur
     */
    public List<Visite> listerParUtilisateur(int utilisateurId) {
        return em.createQuery("SELECT v FROM Visite v WHERE v.utilisateur.id = :uid ORDER BY v.dateVisite DESC", Visite.class)
                 .setParameter("uid", utilisateurId)
                 .getResultList();
    }

    /**
     * Lister les visites pour un lieu
     */
    public List<Visite> listerParLieu(int lieuId) {
        return em.createQuery("SELECT v FROM Visite v WHERE v.lieu.id = :lid ORDER BY v.dateVisite DESC", Visite.class)
                 .setParameter("lid", lieuId)
                 .getResultList();
    }
}
