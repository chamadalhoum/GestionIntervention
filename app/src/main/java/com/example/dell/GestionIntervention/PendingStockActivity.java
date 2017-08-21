package com.example.dell.GestionIntervention;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.example.dell.GestionIntervention.Entities.Modem;
import com.example.dell.GestionIntervention.background.BackgroundTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

public class PendingStockActivity extends AppCompatActivity {

    private Intent intent;
    private Modem modem;
    private BackgroundTask backgroundTask;
    AlertDialog alertDialog, succesDialog;
    private JSONObject jsonObject;
    private String nomMethode;
    private AutoCompleteTextView tvRef;
    private AutoCompleteTextView tvMarque;
    private AutoCompleteTextView tvModel;
    private AutoCompleteTextView tvType;
    private AutoCompleteTextView tvCouleur;
    private AutoCompleteTextView tvDate;
    private AutoCompleteTextView tvAdmin;
    private Button bAccept;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_stock);

        intent = getIntent();
        modem = (Modem)intent.getSerializableExtra("Modem");

        alertDialog = new AlertDialog.Builder(this).create();

        alertDialog.setIcon(R.drawable.erreur);
        alertDialog.setTitle("Erreur");

        succesDialog = new AlertDialog.Builder(this).create();

        succesDialog.setIcon((R.drawable.succes));
        succesDialog.setTitle("Succès");

        tvRef = (AutoCompleteTextView)findViewById(R.id.tvRefPS);
        tvMarque = (AutoCompleteTextView)findViewById(R.id.tvMarquePS);
        tvModel = (AutoCompleteTextView)findViewById(R.id.tvModelPS);
        tvType = (AutoCompleteTextView)findViewById(R.id.tvTypePS);
        tvCouleur = (AutoCompleteTextView)findViewById(R.id.tvCouleurPS);
        tvDate = (AutoCompleteTextView)findViewById(R.id.tvDatePS);
        tvAdmin = (AutoCompleteTextView)findViewById(R.id.tvAdminPS);
        bAccept = (Button)findViewById(R.id.bAcceptPS);


        tvRef.setText(modem.getRef_modem());
        tvMarque.setText(modem.getMarque_modem());
        tvModel.setText(modem.getModel_modem());
        tvType.setText(modem.getType_modem());
        tvCouleur.setText(modem.getCouleur_modem());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dateOutput = dateFormat.format(modem.getDate_sortie());
        tvDate.setText(dateOutput);
        tvAdmin.setText(modem.getLogin_administrateur());
    }

    public void validerModemRecu(View view){

        backgroundTask = new BackgroundTask(this);
        jsonObject = new JSONObject();
        nomMethode = "validerModemRecu";


        try {
            jsonObject.put("refModem", modem.getRef_modem());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        backgroundTask.getObject(jsonObject, nomMethode, new BackgroundTask.objectCallBack() {
            @Override
            public void onSuccess(JSONObject response) {

                Boolean result = false;
                try {
                    result = response.getBoolean("validerModemRecuResult");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (result) {
                    succesDialog.setMessage("Reçu avec succès");
                    succesDialog.show();
                    bAccept.setEnabled(false);
                    intent = new Intent(getApplicationContext(), PartnerActivity.class);
                    intent.putExtra("Etat", "StockEnAttente");
                    startActivity(intent);
                }
                else {
                    alertDialog.setMessage("Erreur essayer de nouveau!");
                    alertDialog.show();
                }
            }

            @Override
            public void onFail(String msg) {

                alertDialog.setMessage(msg+ ": Probleme de connexion");
                alertDialog.show();
            }
        });
    }

}
