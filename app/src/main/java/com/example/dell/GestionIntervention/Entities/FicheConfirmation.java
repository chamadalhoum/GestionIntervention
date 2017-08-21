package com.example.dell.GestionIntervention.Entities;

/**
 * Created by Hp on 01/05/2017.
 */

public class FicheConfirmation {

    private String id_fiche_confirmation;
    private String dateDebut_fiche_confirmation;
    private String dateFin_fiche_confirmation;
    private String details_fiche_confirmation;
    private String staut_fiche_confirmation;
    private String ref_modem_sortant;
    private String ref_modem_entrant;
    private String kilometrage_fiche_confirmation;

    public FicheConfirmation() {
    }

    public String getId_fiche_confirmation() {
        return id_fiche_confirmation;
    }

    public String getDateDebut_fiche_confirmation() {
        return dateDebut_fiche_confirmation;
    }

    public String getDateFin_fiche_confirmation() {
        return dateFin_fiche_confirmation;
    }

    public String getDetails_fiche_confirmation() {
        return details_fiche_confirmation;
    }

    public String getStaut_fiche_confirmation() {
        return staut_fiche_confirmation;
    }

    public String getRef_modem_sortant() {
        return ref_modem_sortant;
    }

    public String getRef_modem_entrant() {
        return ref_modem_entrant;
    }

    public String getkilometrage_fiche_confirmation() {
        return kilometrage_fiche_confirmation;
    }

    public void setId_fiche_confirmation(String id_fiche_confirmation) {
        this.id_fiche_confirmation = id_fiche_confirmation;
    }

    public void setDateDebut_fiche_confirmation(String dateDebut_fiche_confirmation) {
        this.dateDebut_fiche_confirmation = dateDebut_fiche_confirmation;
    }

    public void setDateFin_fiche_confirmation(String dateFin_fiche_confirmation) {
        this.dateFin_fiche_confirmation = dateFin_fiche_confirmation;
    }

    public void setDetails_fiche_confirmation(String details_fiche_confirmation) {
        this.details_fiche_confirmation = details_fiche_confirmation;
    }

    public void setStaut_fiche_confirmation(String staut_fiche_confirmation) {
        this.staut_fiche_confirmation = staut_fiche_confirmation;
    }

    public void setRef_modem_sortant(String ref_modem_sortant) {
        this.ref_modem_sortant = ref_modem_sortant;
    }

    public void setRef_modem_entrant(String ref_modem_entrant) {
        this.ref_modem_entrant = ref_modem_entrant;
    }

    public void setkilometrage_fiche_confirmation(String kilometrage_fiche_confirmation) {
        this.kilometrage_fiche_confirmation = kilometrage_fiche_confirmation;
    }
}
