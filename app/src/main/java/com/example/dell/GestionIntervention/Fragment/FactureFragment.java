package com.example.dell.GestionIntervention.Fragment;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.dell.GestionIntervention.Entities.Facture;
import com.example.dell.GestionIntervention.FactureActivity;
import com.example.dell.GestionIntervention.R;
import com.example.dell.GestionIntervention.background.BackgroundTask;
import com.example.dell.GestionIntervention.background.FactureAdapter;
import com.example.dell.GestionIntervention.background.SessionUtilisateur;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class FactureFragment extends Fragment {

    private View view;
    private SessionUtilisateur sessionUtilisateur;
    private BackgroundTask backgroundTask;
    private ArrayList<Facture> factureArrayList;
    private FactureAdapter factureAdapter;
    private JSONObject jsonObject;
    private AlertDialog alertDialog;
    private ListView listView;
    private ListAdapter listAdapter;
    private String nomMethode;
    private Intent intent;

    public FactureFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_facture, container, false);
        listView = (ListView)view.findViewById(R.id.lvNotification);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        alertDialog = new AlertDialog.Builder(getContext()).create();
        alertDialog.setIcon(R.drawable.erreur);
        alertDialog.setTitle("Erreur");
        sessionUtilisateur = new SessionUtilisateur(getContext());
        factureArrayList = new ArrayList<>();
        nomMethode = "getFacture";
        jsonObject = new JSONObject();


        try {
            jsonObject.put("login", sessionUtilisateur.getLogin());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        backgroundTask = new BackgroundTask(getContext());
        try {
            backgroundTask.getArrayList(jsonObject, nomMethode, new BackgroundTask.arrayCallBack() {
                @Override
                public void onSuccess(JSONArray response) {

                    int count = 0;
                    while(count<response.length()){
                        try {

                            JSONObject responseJsonObject = response.getJSONObject(count);

                            Facture facture = new Facture();

                            String id = responseJsonObject.getString("id_facture");
                            String date = responseJsonObject.getString("date_facture");
                            String montant = responseJsonObject.getString("montant_facture");
                            String admin = responseJsonObject.getString("login_administrateur");
                            String partenaire = responseJsonObject.getString("login_utilisateur");
                            String statut = responseJsonObject.getString("statut_facture");

                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                            Date convertedDate = new Date();
                            try {
                                convertedDate = dateFormat.parse(date);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }


                            facture.setId_facture(id);
                            facture.setDate_facture(date);
                            facture.setMontant_facture(montant);
                            facture.setLogin_administrateur(admin);
                            facture.setLogin_utilisateur(partenaire);
                            facture.setStatut_facture(statut);



                            factureArrayList.add(facture);

                            count++;

                        } catch (JSONException e) {
                            alertDialog.setMessage(e.toString());
                            alertDialog.show();
                        }
                    }

                    factureAdapter = new FactureAdapter(getContext(), factureArrayList);
                    listAdapter = factureAdapter;
                    listView.setAdapter(listAdapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            Facture facture = factureAdapter.getItem(position);
                            intent = new Intent(getContext(), FactureActivity.class);
                            intent.putExtra("Facture", facture);
                            startActivity(intent);
                        }
                    });
                }

                @Override
                public void onFail(String msg) {
                    alertDialog.setMessage(msg);
                    alertDialog.show();

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
