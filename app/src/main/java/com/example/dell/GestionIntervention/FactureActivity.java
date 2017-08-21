package com.example.dell.GestionIntervention;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dell.GestionIntervention.Entities.Client;
import com.example.dell.GestionIntervention.Entities.Facture;
import com.example.dell.GestionIntervention.Entities.Intervention;
import com.example.dell.GestionIntervention.background.BackgroundTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FactureActivity extends AppCompatActivity {

    Facture facture;
    Intent intent;
    JSONObject jsonObject;
    String nomMethode;
    BackgroundTask backgroundTask;
    AlertDialog alertDialog, succesDialog;
    LinearLayout lyIntervention;
    Button validerpayement;
    LinearLayout lyFacture;

    AutoCompleteTextView tvAdmin, tvEmailAdmin, tvTelAdmin, tvMontantTotal;
    TextView tvDateFacture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facture);

        alertDialog = new AlertDialog.Builder(this).create();

        alertDialog.setIcon(R.drawable.erreur);
        alertDialog.setTitle("Erreur");

        succesDialog = new AlertDialog.Builder(this).create();
        succesDialog.setIcon((R.drawable.succes));
        succesDialog.setTitle("Succès");

        tvAdmin = (AutoCompleteTextView)findViewById(R.id.tvAdmin);
        tvEmailAdmin = (AutoCompleteTextView)findViewById(R.id.tvEmailAdmin);
        tvTelAdmin = (AutoCompleteTextView)findViewById(R.id.tvTelAdmin);
        tvMontantTotal = (AutoCompleteTextView)findViewById(R.id.tvMontantTotal);
        tvDateFacture = (TextView)findViewById(R.id.tvDateFacture);

        validerpayement = new Button(this);
        validerpayement = (Button)findViewById(R.id.bValiderPayement);

        lyFacture = new LinearLayout(this);
        lyIntervention = (LinearLayout)findViewById(R.id.lyIntervention);
        lyFacture = (LinearLayout)findViewById(R.id.lyFacture);

        intent = getIntent();
        facture = (Facture)intent.getSerializableExtra("Facture");


        if(!facture.getStatut_facture().equalsIgnoreCase("En attente")){
            lyFacture.removeView(validerpayement);
        }

        tvAdmin.setText(facture.getId_facture());

        jsonObject = new JSONObject();

        try {
            jsonObject.put("login", facture.getLogin_administrateur());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        nomMethode = "getAdministrateur";
        backgroundTask = new BackgroundTask(this);

        backgroundTask.getObject(jsonObject, nomMethode, new BackgroundTask.objectCallBack() {
            @Override
            public void onSuccess(JSONObject response) {

                try {

                    String nom = response.getString("nom_administrateur");
                    String prenom = response.getString("prenom_administrateur");
                    String email = response.getString("email_administrateur");
                    String tel = response.getString("tel_administrateur");
                    String montant = facture.getMontant_facture();

                    tvAdmin.setText(nom+" "+prenom);
                    tvEmailAdmin.setText(email);
                    tvTelAdmin.setText(tel);
                    tvMontantTotal.setText("Total: " + montant + " DT");

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Date convertedDate = new Date();
                    try {
                        convertedDate = dateFormat.parse(facture.getDate_facture());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    String output = dateFormat.format(convertedDate);

                    tvDateFacture.setText(output);

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

        jsonObject = new JSONObject();

        try {
            jsonObject.put("id_facture", facture.getId_facture());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        nomMethode = "getInterventionByIdFacture";
        backgroundTask = new BackgroundTask(this);

        try {
            backgroundTask.getArrayList(jsonObject, nomMethode, new BackgroundTask.arrayCallBack() {
                @Override
                public void onSuccess(JSONArray response) {

                    int count = 0;
                    while(count<response.length()) {
                        try {
                            JSONObject responseJsonObject = response.getJSONObject(count);

                            String motif = responseJsonObject.getString("motif_intervention");
                            String date = responseJsonObject.getString("date_intervention");
                            String kilometrage = responseJsonObject.getString("kilometrage");
                            String nom_client = responseJsonObject.getString("nom_client");
                            String categorie_client = responseJsonObject.getString("categorie_client");
                            String etat_intervention = responseJsonObject.getString("etat_intervention");
                            String montant = responseJsonObject.getString("montant");

                            TextView tvIntervention = new TextView(FactureActivity.this);
                            tvIntervention.setId(View.generateViewId());
                            tvIntervention.setText(+ (count+1) +" intervention " + motif +": ");
                            tvIntervention.setTextColor(getResources().getColor(R.color.BlueIve));
                            tvIntervention.setTextSize(18);
                            lyIntervention.addView(tvIntervention);

                            TextView tvdate = new TextView(FactureActivity.this);
                            tvdate.setId(View.generateViewId());
                            tvdate.setText("Date: " + date);
                            tvdate.setTextColor(getResources().getColor(R.color.caldroid_black));
                            tvdate.setTextSize(16);
                            tvdate.setGravity(Gravity.CENTER);
                            lyIntervention.addView(tvdate);

                            TextView tvClient = new TextView(FactureActivity.this);
                            tvClient.setId(View.generateViewId());
                            tvClient.setText("Client: "+nom_client);
                            tvClient.setTextColor(getResources().getColor(R.color.caldroid_black));
                            tvClient.setTextSize(16);
                            tvClient.setGravity(Gravity.CENTER);
                            lyIntervention.addView(tvClient);

                            TextView tvCategorie = new TextView(FactureActivity.this);
                            tvCategorie.setId(View.generateViewId());
                            tvCategorie.setText("Categorie: "+categorie_client);
                            tvCategorie.setTextColor(getResources().getColor(R.color.caldroid_black));
                            tvCategorie.setTextSize(16);
                            tvCategorie.setGravity(Gravity.CENTER);
                            lyIntervention.addView(tvCategorie);

                            TextView tvEtat = new TextView(FactureActivity.this);
                            tvEtat.setId(View.generateViewId());
                            tvEtat.setText("Etat: "+etat_intervention);
                            tvEtat.setTextColor(getResources().getColor(R.color.caldroid_black));
                            tvEtat.setTextSize(16);
                            tvEtat.setGravity(Gravity.CENTER);
                            lyIntervention.addView(tvEtat);

                            TextView tvKilometrage = new TextView(FactureActivity.this);
                            tvKilometrage.setId(View.generateViewId());
                            tvKilometrage.setText("Kilometrage: "+kilometrage);
                            tvKilometrage.setTextColor(getResources().getColor(R.color.caldroid_black));
                            tvKilometrage.setTextSize(16);
                            tvKilometrage.setGravity(Gravity.CENTER);
                            lyIntervention.addView(tvKilometrage);

                            TextView tvMontant = new TextView(FactureActivity.this);
                            tvMontant.setId(View.generateViewId());
                            tvMontant.setText("Montant: "+montant+" DT");
                            tvMontant.setTextColor(getResources().getColor(R.color.caldroid_black));
                            tvMontant.setTextSize(16);
                            tvMontant.setGravity(Gravity.CENTER);
                            lyIntervention.addView(tvMontant);


                            count++;

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFail(String msg) {

                   // alertDialog.setMessage(msg);
                   // alertDialog.show();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }


        validerpayement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomMethode = "validerFacture";
                jsonObject = new JSONObject();


                try {
                    jsonObject.put("id_facture",facture.getId_facture() );
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                backgroundTask.getObject(jsonObject, nomMethode, new BackgroundTask.objectCallBack() {
                    @Override
                    public void onSuccess(JSONObject response) {

                        try {

                            boolean result = response.getBoolean("validerFactureResult");

                            if (result == true) {

                                succesDialog.setMessage("Validée avec succès");
                                succesDialog.show();
                                lyFacture.removeView(validerpayement);

                            } else {
                                alertDialog.setMessage("Probleme survenu!");
                                alertDialog.show();
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
        });


    }
}
