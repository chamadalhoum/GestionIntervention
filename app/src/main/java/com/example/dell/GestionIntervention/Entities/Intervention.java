package com.example.dell.GestionIntervention.Entities;


import java.io.Serializable;
import java.util.Date;

/**
 * Created by Hp on 04/04/2017.
 */

public class Intervention implements Serializable {

    private String id_intervention;
    private String motif_intervention;
    private String libelle_motif_intervention;
    private String reseau_intervention;
    private Date date_intervention;
    private String priorite_intervention;
    private String statut_intervention;
    private String tel_adsl;

    public Intervention() {
    }

    public Intervention(String id_intervention, String motif_intervention, String libelle_motif_intervention, String reseau_intervention, Date date_intervention, String priorite_intervention, String statut_intervention, String tel_adsl) {
        this.id_intervention = id_intervention;
        this.motif_intervention = motif_intervention;
        this.libelle_motif_intervention = libelle_motif_intervention;
        this.reseau_intervention = reseau_intervention;
        this.date_intervention = date_intervention;
        this.priorite_intervention = priorite_intervention;
        this.statut_intervention = statut_intervention;
        this.tel_adsl = tel_adsl;
    }

    public void setId_intervention(String id_intervention) {
        this.id_intervention = id_intervention;
    }

    public void setMotif_intervention(String motif_intervention) {
        this.motif_intervention = motif_intervention;
    }

    public void setDate_intervention(Date date_intervention) {
        this.date_intervention = date_intervention;
    }

    public void setPriorite_intervention(String priorite_intervention) {
        this.priorite_intervention = priorite_intervention;
    }

    public void setStatut_intervention(String statut_intervention) {
        this.statut_intervention = statut_intervention;
    }

    public void setTel_adsl(String tel_adsl) {
        this.tel_adsl = tel_adsl;
    }

    public void setLibelle_motif_intervention(String libelle_motif_intervention) {
        this.libelle_motif_intervention = libelle_motif_intervention;
    }

    public void setReseau_intervention(String reseau_intervention) {
        this.reseau_intervention = reseau_intervention;
    }

    public String getId_intervention() {
        return id_intervention;
    }

    public String getMotif_intervention() {
        return motif_intervention;
    }

    public Date getDate_intervention() {
        return date_intervention;
    }

    public String getPriorite_intervention() {
        return priorite_intervention;
    }

    public String getStatut_intervention() {
        return statut_intervention;
    }

    public String getTel_adsl() {
        return tel_adsl;
    }

    public String getLibelle_motif_intervention() {
        return libelle_motif_intervention;
    }

    public String getReseau_intervention() {
        return reseau_intervention;
    }
}
