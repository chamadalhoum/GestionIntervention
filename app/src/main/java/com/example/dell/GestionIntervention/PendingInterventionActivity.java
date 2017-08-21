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

public class PendingInterventionActivity extends AppCompatActivity {

    private Intent intent;
    private Intervention intervention;
    private Client client;
    private BackgroundTask backgroundTask;
    AlertDialog alertDialog, succesDialog;
    private JSONObject jsonObject;
    private String nomMethode;
    private AutoCompleteTextView tvMotif;
    private AutoCompleteTextView tvReasau;
    private AutoCompleteTextView tvTelADSL;
    private AutoCompleteTextView tvContact;
    private AutoCompleteTextView tvAdresse;
    private AutoCompleteTextView tvNom;
    private AutoCompleteTextView tvPriorite;
    private AutoCompleteTextView tvCategorie;
    private TextView tvDate;
    private Button bAccept;
    private Button bRefus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_intervention);

        alertDialog = new AlertDialog.Builder(this).create();

        alertDialog.setIcon(R.drawable.erreur);
        alertDialog.setTitle("Erreur");

        succesDialog = new AlertDialog.Builder(this).create();

        succesDialog.setIcon((R.drawable.succes));
        succesDialog.setTitle("Succès");


        tvMotif = (AutoCompleteTextView)findViewById(R.id.tvMotif);
        tvReasau = (AutoCompleteTextView)findViewById(R.id.tvReseau);
        tvTelADSL = (AutoCompleteTextView)findViewById(R.id.tvTelADSL);
        tvContact = (AutoCompleteTextView)findViewById(R.id.tvContact);
        tvAdresse = (AutoCompleteTextView)findViewById(R.id.tvCentral);
        tvNom = (AutoCompleteTextView)findViewById(R.id.tvNom);
        tvPriorite = (AutoCompleteTextView)findViewById(R.id.tvPriorite);
        tvCategorie = (AutoCompleteTextView)findViewById(R.id.tvCategorie);
        tvDate = (TextView)findViewById(R.id.tvDate);
        bAccept = (Button)findViewById(R.id.bAccept);
        bRefus = (Button)findViewById(R.id.bRefus);

        intent = getIntent();
        intervention = (Intervention)intent.getSerializableExtra("Intervention");
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
                    String adresse = response.getString("adresse_client");
                    String categorie = response.getString("categorie_client");
                    String raison = response.getString("raisonSocial_client");
                    String tel_gsm = response.getString("tel_gsm_client");


                    client.setCode_client(code);
                    client.setNom_client(nom);
                    client.setPrenom_client(prenom);
                    client.setAdresse_client(adresse);
                    client.setCategorie_client(categorie);
                    client.setRaisonSocial_client(raison);
                    client.setTel_gsm_client(tel_gsm);

                    tvMotif.setText(intervention.getMotif_intervention());
                    tvReasau.setText(intervention.getReseau_intervention());
                    tvTelADSL.setText(intervention.getTel_adsl());
                    tvContact.setText(client.getTel_gsm_client());
                    tvAdresse.setText(client.getAdresse_client());
                    tvNom.setText(client.getNom_client()+" "+client.getPrenom_client());
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


    }

    public void priseEnChargeIntervention(View view){

        backgroundTask = new BackgroundTask(this);
        jsonObject = new JSONObject();
        nomMethode = "priseEnChargeIntervention";

        final Button resultButton = (Button)view;

        if(resultButton.getText().toString().equalsIgnoreCase("Accepter")) {
            try {
                jsonObject.put("statutIntervention", "Accepte");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else if (resultButton.getText().toString().equalsIgnoreCase("Refuser"))
        {
            try {
                jsonObject.put("statutIntervention", "Refuse");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        try {
            jsonObject.put("idIntervention", intervention.getId_intervention());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        backgroundTask.getObject(jsonObject, nomMethode, new BackgroundTask.objectCallBack() {
            @Override
            public void onSuccess(JSONObject response) {

                Boolean result = false;
                try {
                    result = response.getBoolean("priseEnChargeInterventionResult");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (result) {
                    if(resultButton.getText().toString().equalsIgnoreCase("Accepter")) {
                        succesDialog.setMessage("Accepté avec succès");
                    }
                    else {
                        succesDialog.setMessage("Refusé avec succès");
                    }
                    bAccept.setEnabled(false);
                    bRefus.setEnabled(false);
                    intent = new Intent(getApplicationContext(), PartnerActivity.class);
                    intent.putExtra("Etat", "intervention");
                    startActivity(intent);
                    succesDialog.show();
                }
                else {
                    alertDialog.setMessage("Erreur essayer de nouveau!");
                    alertDialog.show();
                }
            }

            @Override
            public void onFail(String msg) {

                alertDialog.setMessage(msg);
                alertDialog.show();
            }
        });
    }
}
