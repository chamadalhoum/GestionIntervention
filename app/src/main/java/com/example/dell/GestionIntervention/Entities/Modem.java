package com.example.dell.GestionIntervention.Entities;

import java.io.Serializable;
import java.util.Date;



public class Modem implements Serializable {

    private String ref_modem;
    private String marque_modem;
    private String model_modem;
    private String type_modem;
    private String couleur_modem;
    private String etat_modem;
    private Date date_sortie;
    private Date date_entree;
    private Date date_affectation;
    private Date date_retour;
    private String login_utilisateur;
    private String login_administrateur;

    public Modem() {
    }

    public Modem(String ref_modem, String marque_modem, String model_modem, String type_modem, String couleur_modem, String etat_modem, Date date_sortie, String login_utilisateur, String login_administrateur) {
        this.ref_modem = ref_modem;
        this.marque_modem = marque_modem;
        this.model_modem = model_modem;
        this.type_modem = type_modem;
        this.couleur_modem = couleur_modem;
        this.etat_modem = etat_modem;
        this.date_sortie = date_sortie;
        this.login_utilisateur = login_utilisateur;
        this.login_administrateur = login_administrateur;
    }

    public void setRef_modem(String ref_modem) {
        this.ref_modem = ref_modem;
    }

    public void setMarque_modem(String marque_modem) {
        this.marque_modem = marque_modem;
    }

    public void setModel_modem(String model_modem) {
        this.model_modem = model_modem;
    }

    public void setType_modem(String type_modem) {
        this.type_modem = type_modem;
    }

    public void setCouleur_modem(String couleur_modem) {
        this.couleur_modem = couleur_modem;
    }

    public void setEtat_modem(String etat_modem) {
        this.etat_modem = etat_modem;
    }

    public void setDate_sortie(Date date_sortie) {
        this.date_sortie = date_sortie;
    }

    public void setDate_entree(Date date_entree) {
        this.date_entree = date_entree;
    }

    public void setDate_affectation(Date date_affectation) {
        this.date_affectation = date_affectation;
    }

    public void setDate_retour(Date date_retour) {
        this.date_retour = date_retour;
    }

    public void setLogin_utilisateur(String login_utilisateur) {
        this.login_utilisateur = login_utilisateur;
    }

    public void setLogin_administrateur(String login_administrateur) {
        this.login_administrateur = login_administrateur;
    }

    public String getRef_modem() {
        return ref_modem;
    }

    public String getMarque_modem() {
        return marque_modem;
    }

    public String getModel_modem() {
        return model_modem;
    }

    public String getType_modem() {
        return type_modem;
    }

    public String getCouleur_modem() {
        return couleur_modem;
    }

    public String getEtat_modem() {
        return etat_modem;
    }

    public Date getDate_sortie() {
        return date_sortie;
    }

    public Date getDate_entree() {
        return date_entree;
    }

    public Date getDate_affectation() {
        return date_affectation;
    }

    public Date getDate_retour() {
        return date_retour;
    }

    public String getLogin_utilisateur() {
        return login_utilisateur;
    }

    public String getLogin_administrateur() {
        return login_administrateur;
    }
}
