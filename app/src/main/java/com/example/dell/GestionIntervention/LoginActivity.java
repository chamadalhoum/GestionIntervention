package com.example.dell.GestionIntervention;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.dell.GestionIntervention.background.BackgroundTask;
import com.example.dell.GestionIntervention.background.SessionUtilisateur;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

	private EditText txtlogin;
	private EditText txtmotdepasse;
    private BackgroundTask backgroundTask;
    private SessionUtilisateur sessionUtilisateur;
    private Intent intent;
    private JSONObject jsonObject;
    AlertDialog alertDialog, succesDialog;
	public void onLogin(View view) {

		final String login = txtlogin.getText().toString();
		String motdepasse = txtmotdepasse.getText().toString();
		jsonObject = new JSONObject();
		String nomMethode = "logintopnet";


		try {
			jsonObject.put("login", login);
			jsonObject.put("motdepasse", motdepasse);

		} catch (JSONException e) {
			e.printStackTrace();
		}

		backgroundTask.getObject(jsonObject, nomMethode, new BackgroundTask.objectCallBack() {
			@Override
			public void onSuccess(JSONObject response) {
				try {
					String result = response.getString("logintopnetResult");
					startActivity(result, login);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFail(String msg) {
				alertDialog.setMessage(msg);
				alertDialog.show();
			}
		});
	}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
		txtlogin=(EditText)findViewById(R.id.txtlogin);
		txtmotdepasse=(EditText)findViewById(R.id.txtmotdepasse);

        sessionUtilisateur = new SessionUtilisateur(this);

        alertDialog = new AlertDialog.Builder(this).create();


        alertDialog.setTitle("Verifier Login ou Mot de passe");

        succesDialog = new AlertDialog.Builder(this).create();

        succesDialog.setTitle("Bienvenue sur votre Espace ");

        backgroundTask = new BackgroundTask(this);
        sessionUtilisateur = new SessionUtilisateur(this);

        if(sessionUtilisateur.loggedIn().equalsIgnoreCase("2")) {
            intent = new Intent(LoginActivity.this, PartnerActivity.class);
            startActivity(intent);
        }
        else if (sessionUtilisateur.loggedIn().equalsIgnoreCase("3"))  {
            intent = new Intent(LoginActivity.this, TechnicianActivity.class);
            startActivity(intent);
        }
    }



    public void startActivity(final String result, String login){

        if (result.equalsIgnoreCase("2") || result.equalsIgnoreCase("3")) {

            String nomMethode = "get_accueil";
            jsonObject = new JSONObject();

            succesDialog.setMessage("Authentification en cours");
            succesDialog.show();


            try {
                jsonObject.put("login", login);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            backgroundTask.getObject(jsonObject, nomMethode, new BackgroundTask.objectCallBack() {
                @Override
                public void onSuccess(JSONObject response) {

                    try {

                        String login = response.getString("login");
                        String nom = response.getString("nom");

                        String motdepasse = response.getString("motdepasse");
                        String prenom = response.getString("prenom");
                        String statutfamilial = response.getString("statutfamilial");
                        String adresse = response.getString("adresse");
                        int id_role = response.getInt("id_role");
                        int tel = response.getInt("tel");
                        int gsm = response.getInt("gsm");
                        int cin = response.getInt("cin");
                        String email = response.getString("email");
                        sessionUtilisateur.setUtilisateurInfo(login, nom, prenom, statutfamilial, adresse,id_role, tel,gsm,cin, email);
                        sessionUtilisateur.setMotdepasse(motdepasse);

                        if (result.equalsIgnoreCase("3")){

                            sessionUtilisateur.setLoggedIn("3");
                            intent = new Intent(getApplicationContext(), TechnicianActivity.class);
                            startActivity(intent);
                        }
                        else if (result.equalsIgnoreCase("2")) {
                            sessionUtilisateur.setLoggedIn("2");
                            intent = new Intent(getApplicationContext(), PartnerActivity.class);
                            startActivity(intent);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFail(String msg) {

                    alertDialog.setMessage(msg);
                    alertDialog.show();
                }
            });
        }
        else if (result.equalsIgnoreCase("false"))
        {
            alertDialog.setMessage("Login ou mot de passe incorrect");
            alertDialog.show();
        }
        else
        {
            alertDialog.setMessage("Quelque chose a mal tourné");
            alertDialog.show();
        }
    }

    @Override
    public void onBackPressed() {
         finish();
        System.exit(0);

    }
}
