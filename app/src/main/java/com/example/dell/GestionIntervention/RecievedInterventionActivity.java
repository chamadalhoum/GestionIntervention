package com.example.dell.GestionIntervention;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.example.dell.GestionIntervention.Entities.Client;
import com.example.dell.GestionIntervention.Entities.Intervention;
import com.example.dell.GestionIntervention.background.BackgroundTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

public class RecievedInterventionActivity extends AppCompatActivity {

    private Intent intent;
    private Intervention intervention;
    private Client client;
    private BackgroundTask backgroundTask;
    AlertDialog alertDialog, succesDialog;
    private JSONObject jsonObject;
    private String nomMethode;
    private AutoCompleteTextView tvMotif, tvReasau, tvTelADSL, tvContact, tvCentral, tvNom, tvPriorite, tvCategorie, tvlogin, tvMotDePasse ;
    private TextView tvDate;
    private Button bAccept, bRefus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recieved_intervention);

        tvMotif = (AutoCompleteTextView) findViewById(R.id.tvMotif);
        tvReasau = (AutoCompleteTextView) findViewById(R.id.tvReseau);
        tvTelADSL = (AutoCompleteTextView) findViewById(R.id.tvTelADSL);
        tvContact = (AutoCompleteTextView) findViewById(R.id.tvContact);
        tvCentral = (AutoCompleteTextView) findViewById(R.id.tvCentral);
        tvNom = (AutoCompleteTextView) findViewById(R.id.tvNom);
        tvPriorite = (AutoCompleteTextView) findViewById(R.id.tvPriorite);
        tvCategorie = (AutoCompleteTextView) findViewById(R.id.tvCategorie);
        tvlogin = (AutoCompleteTextView) findViewById(R.id.tvLogin);
        tvMotDePasse = (AutoCompleteTextView) findViewById(R.id.tvMotDePasse);
        tvDate = (TextView) findViewById(R.id.tvDate);
        bAccept = (Button) findViewById(R.id.bAccept);
        bRefus = (Button) findViewById(R.id.bRefus);

        alertDialog = new AlertDialog.Builder(this).create();

        alertDialog.setIcon(R.drawable.erreur);
        alertDialog.setTitle("Erreur");

        succesDialog = new AlertDialog.Builder(this).create();

        succesDialog.setIcon((R.drawable.succes));
        succesDialog.setIcon(R.drawable.succes);

        intent = getIntent();
        intervention = (Intervention) intent.getSerializableExtra("Intervention");
        jsonObject = new JSONObject();

        try {
            jsonObject.put("telAdsl", intervention.getTel_adsl());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        nomMethode = "getClient";
        client = new Client();
        backgroundTask = new BackgroundTask(this);

        backgroundTask.getObject(jsonObject, nomMethode, new BackgroundTask.objectCallBack() {
            @Override
            public void onSuccess(JSONObject response) {

                try {

                    String code = response.getString("code_client");
                    String nom = response.getString("nom_client");
                    String prenom = response.getString("prenom_client");
                    String categorie = response.getString("categorie_client");
                    String raison = response.getString("raisonSocial_client");
                    String tel_gsm = response.getString("tel_gsm_client");
                    String adresse = response.getString("adresse_client");

                    client.setCode_client(code);
                    client.setNom_client(nom);
                    client.setPrenom_client(prenom);
                    client.setCategorie_client(categorie);
                    client.setRaisonSocial_client(raison);
                    client.setTel_gsm_client(tel_gsm);
                    client.setAdresse_client(adresse);

                    tvMotif.setText(intervention.getMotif_intervention());
                    tvReasau.setText(intervention.getReseau_intervention());
                    tvTelADSL.setText(intervention.getTel_adsl());
                    tvContact.setText(client.getTel_gsm_client());
                    tvCentral.setText(client.getAdresse_client());
                    tvNom.setText(client.getNom_client() + " " + client.getPrenom_client());
                    tvPriorite.setText(intervention.getPriorite_intervention());
                    tvCategorie.setText(client.getCategorie_client());

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    String date = dateFormat.format(intervention.getDate_intervention());
                    tvDate.setText(date);

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

        nomMethode = "getAdsl";
        backgroundTask = new BackgroundTask(this);

        backgroundTask.getObject(jsonObject, nomMethode, new BackgroundTask.objectCallBack() {
            @Override
            public void onSuccess(JSONObject response) {

                try {

                    String login = response.getString("login_adsl");
                    String motDePasse = response.getString("motDePasse_adsl");


                    tvlogin.setText(login);
                    tvMotDePasse.setText(motDePasse);

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    String date = dateFormat.format(intervention.getDate_intervention());
                    tvDate.setText(date);

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

    public void onPasseValidClick(View view){
        intent = new Intent(this, ValidationActivity.class);
        intent.putExtra("Intervention", intervention);
        startActivity(intent);
    }
}

