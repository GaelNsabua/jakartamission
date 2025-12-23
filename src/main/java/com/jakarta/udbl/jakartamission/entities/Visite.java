package com.jakarta.udbl.jakartamission.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;

/**
 * Entité représentant une visite d'un lieu par un utilisateur
 *
 * Table VISITE : ID, DATE_VISITE, TEMPS_PASSE, OBSERVATIONS, DEPENSE, UTILISATEUR_ID, LIEU_ID
 *
 * @author gaelm
 */
@Entity
@Table(name = "visite")
public class Visite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "DATE_VISITE")
    private LocalDate dateVisite;

    @Column(name = "TEMPS_PASSE")
    private Integer tempsPasse;

    @Column(name = "OBSERVATIONS", length = 1000)
    private String observations;

    @Column(name = "DEPENSE")
    private Integer depense;

    @ManyToOne
    @JoinColumn(name = "UTILISATEUR_ID")
    private Utilisateur utilisateur;

    @ManyToOne
    @JoinColumn(name = "LIEU_ID")
    private Lieu lieu;

    public Visite() {
    }

    public Visite(LocalDate dateVisite, Integer tempsPasse, String observations, Integer depense, Utilisateur utilisateur, Lieu lieu) {
        this.dateVisite = dateVisite;
        this.tempsPasse = tempsPasse;
        this.observations = observations;
        this.depense = depense;
        this.utilisateur = utilisateur;
        this.lieu = lieu;
    }

    public int getId() {
        return id;
    }

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

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Lieu getLieu() {
        return lieu;
    }

    public void setLieu(Lieu lieu) {
        this.lieu = lieu;
    }
}
