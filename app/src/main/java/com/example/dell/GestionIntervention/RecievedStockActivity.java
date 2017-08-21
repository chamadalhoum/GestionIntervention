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

public class RecievedStockActivity extends AppCompatActivity {

        private Intent intent;
        private Modem modem;
        private BackgroundTask backgroundTask;
        AlertDialog alertDialog;
        private JSONObject jsonObject;
        private String nomMethode;
        private AutoCompleteTextView tvRef;
        private AutoCompleteTextView tvMarque;
        private AutoCompleteTextView tvModel;
        private AutoCompleteTextView tvType;
        private AutoCompleteTextView tvCouleur;
        private AutoCompleteTextView tvDateEnvoie, tvDateReception;
        private AutoCompleteTextView tvAdmin;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_recieved_stock);

            intent = getIntent();
            modem = (Modem)intent.getSerializableExtra("Modem");

            alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setIcon(R.drawable.erreur);
            alertDialog.setTitle("Erreur");
            tvRef = (AutoCompleteTextView)findViewById(R.id.tvRefRS);
            tvMarque = (AutoCompleteTextView)findViewById(R.id.tvMarqueRS);
            tvModel = (AutoCompleteTextView)findViewById(R.id.tvModelRS);
            tvType = (AutoCompleteTextView)findViewById(R.id.tvTypeRS);
            tvCouleur = (AutoCompleteTextView)findViewById(R.id.tvCouleurRS);
            tvDateEnvoie = (AutoCompleteTextView)findViewById(R.id.tvDateEnvoieRS);
            tvDateReception = (AutoCompleteTextView)findViewById(R.id.tvDateReceptionRS);
            tvAdmin = (AutoCompleteTextView)findViewById(R.id.tvAdminRS);


            tvRef.setText(modem.getRef_modem());
            tvMarque.setText(modem.getMarque_modem());
            tvModel.setText(modem.getModel_modem());
            tvType.setText(modem.getType_modem());
            tvCouleur.setText(modem.getCouleur_modem());
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String dateEnvoieOutput = dateFormat.format(modem.getDate_sortie());
            tvDateEnvoie.setText(dateEnvoieOutput);
            String dateReceptionOutput = dateFormat.format(modem.getDate_sortie());
            tvDateReception.setText(dateReceptionOutput);
            tvAdmin.setText(modem.getLogin_administrateur());
        }
}
