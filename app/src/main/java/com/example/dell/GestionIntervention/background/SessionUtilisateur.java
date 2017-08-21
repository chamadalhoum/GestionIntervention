package com.example.dell.GestionIntervention.background;

import android.content.Context;
import android.content.SharedPreferences;


public class SessionUtilisateur {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Context context;

    public SessionUtilisateur(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences("GestionIntervention", context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void setLoggedIn(String loggedInMode) {
        editor.putString("loggedIn", loggedInMode);
        editor.commit();
    }
	public String getAdresse(){
		return preferences.getString("adresse", "");
	}
	public void setAdresse(String adresse) { editor.putString("adresse", adresse); }
	 public String getTel() { return preferences.getString("tel", ""); }
	public void setTel(String tel) { editor.putString("tel", tel); }

    public String loggedIn(){
        return preferences.getString("loggedIn", "false");
    }
	public String getMotdepasse() { return preferences.getString("motdepasse", ""); }
    public void setMotdepasse(String motdepasse) { editor.putString("motdepasse", motdepasse); }

    public String getNom(){
        return preferences.getString("nom", "");
    }
	public void setNom(String nom) { editor.putString("nom", nom); }
	public String getGouvernorat(){
		return preferences.getString("gouvernorat", "");
	}
	public void setGouvernorat(String gouvernorat) { editor.putString("gouvernorat", gouvernorat); }
	public String getCin() {
		return preferences.getString("cin", "");
	}
	public void setCin(String cin) { editor.putString("cin", cin); }

    public String getCodePostal(){
        return preferences.getString("CodePostal", "");
    }
	public void setCodePostal(String codePostal) { editor.putString("codepostal", codePostal); }
	public String getLocalite(){
		return preferences.getString("localite", "");
	}
	public void setLocalite(String localite) { editor.putString("localite", localite); }





    public String getStatutfamilial() { return preferences.getString("statutfamilial", ""); }
	public String getPrenom(){
		return preferences.getString("prenom", "");
	}
	public void setPrenom(String prenom) { editor.putString("prenom", prenom); }


	public String getEmail() { return preferences.getString("email", ""); }

    public void setEmail(String email) { editor.putString("email", email); }




    public void setUtilisateurInfo(String login, String nom, String prenom, String statutfamilial, String adresse, int id_role, int tel, int gsm, int cin,  String email){

        editor.putString("login", login);
        editor.apply();
        editor.putString("nom", nom);
        editor.apply();
        editor.putString("prenom", prenom);
        editor.apply();
        editor.putString("statutfamilial", statutfamilial);
        editor.apply();
        editor.putString("adresse",adresse);
        editor.apply();
        editor.putInt("tel", tel);
        editor.apply();
        editor.putInt("cin", cin);
        editor.apply();
        editor.putInt("gsm", gsm);
        editor.apply();
        editor.putString("email", email);
        editor.apply();
        editor.putInt("id_role", id_role);
    }

    public String getLogin(){
        return preferences.getString("login", "");
    }






    public void clearEdit() {
        editor.clear();
        editor.commit();
    }
}
