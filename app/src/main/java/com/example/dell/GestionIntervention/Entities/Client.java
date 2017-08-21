package com.example.dell.GestionIntervention.Entities;

/**
 * Created by Hp on 08/04/2017.
 */

public class Client {

    private String code_client;
    private String nom_client;
    private String prenom_client;
    private String adresse_client;
    private String email_client;
    private String Categorie_client;
    private String raisonSocial_client;
    private String tel_gsm_client;
    private String code_postale;
    private String ville;
    private String delegation;
    private String gouvernorat;


    public Client() {
    }

    public void setCode_client(String code_client) {
        this.code_client = code_client;
    }

    public void setNom_client(String nom_client) {
        this.nom_client = nom_client;
    }

    public void setPrenom_client(String prenom_client) {
        this.prenom_client = prenom_client;
    }

    public void setAdresse_client(String adresse_client) {
        this.adresse_client = adresse_client;
    }

    public void setEmail_client(String email_client) {
        this.email_client = email_client;
    }

    public void setCategorie_client(String categorie_client) {
        Categorie_client = categorie_client;
    }

    public void setRaisonSocial_client(String raisonSocial_client) {
        this.raisonSocial_client = raisonSocial_client;
    }

    public void setTel_gsm_client(String tel_gsm_client) {
        this.tel_gsm_client = tel_gsm_client;
    }

    public void setCode_postale(String code_postale) {
        this.code_postale = code_postale;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public void setDelegation(String delegation) {
        this.delegation = delegation;
    }

    public void setGouvernorat(String gouvernorat) {
        this.gouvernorat = gouvernorat;
    }

    public String getCode_client() {
        return code_client;
    }

    public String getNom_client() {
        return nom_client;
    }

    public String getPrenom_client() {
        return prenom_client;
    }

    public String getAdresse_client() {
        return adresse_client;
    }

    public String getEmail_client() {
        return email_client;
    }

    public String getCategorie_client() {
        return Categorie_client;
    }

    public String getRaisonSocial_client() {
        return raisonSocial_client;
    }

    public String getTel_gsm_client() {
        return tel_gsm_client;
    }

    public String getCode_postale() {
        return code_postale;
    }

    public String getVille() {
        return ville;
    }

    public String getDelegation() {
        return delegation;
    }

    public String getGouvernorat() {
        return gouvernorat;
    }
}
