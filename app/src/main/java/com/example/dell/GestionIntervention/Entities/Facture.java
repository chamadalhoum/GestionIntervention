package com.example.dell.GestionIntervention.Entities;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Hp on 18/05/2017.
 */

public class Facture implements Serializable {

    private String id_facture;
    private String date_facture;
    private String montant_facture;
    private String login_administrateur;
    private String login_utilisateur;
    private ArrayList<String> id_intervention;
    private ArrayList<String> montant_intervention;
    private String statut_facture;

    public Facture() {
    }

    public void setStatut_facture(String staut_facture) {
        this.statut_facture = staut_facture;
    }

    public void setId_facture(String id_facture) {
        this.id_facture = id_facture;
    }

    public void setDate_facture(String date_facture) {
        this.date_facture = date_facture;
    }

    public void setMontant_facture(String montant_facture) {
        this.montant_facture = montant_facture;
    }

    public void setLogin_administrateur(String login_administrateur) {
        this.login_administrateur = login_administrateur;
    }

    public void setLogin_utilisateur(String login_utilisateur) {
        this.login_utilisateur = login_utilisateur;
    }

    public void setId_intervention(ArrayList<String> id_intervention) {
        this.id_intervention = id_intervention;
    }

    public void setMontant_intervention(ArrayList<String> montant_intervention) {
        this.montant_intervention = montant_intervention;
    }

    public String getStatut_facture() {
        return statut_facture;
    }

    public String getId_facture() {
        return id_facture;
    }

    public String getDate_facture() {
        return date_facture;
    }

    public String getMontant_facture() {
        return montant_facture;
    }

    public String getLogin_administrateur() {
        return login_administrateur;
    }

    public String getLogin_utilisateur() {
        return login_utilisateur;
    }

    public ArrayList<String> getId_intervention() {
        return id_intervention;
    }

    public ArrayList<String> getMontant_intervention() {
        return montant_intervention;
    }
}
