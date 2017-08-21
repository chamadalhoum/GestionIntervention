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

import com.example.dell.GestionIntervention.Entities.Intervention;
import com.example.dell.GestionIntervention.PendingInterventionActivity;
import com.example.dell.GestionIntervention.R;
import com.example.dell.GestionIntervention.background.BackgroundTask;
import com.example.dell.GestionIntervention.background.InterventionAdapter;
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
public class PartnerPrincipalFragment extends Fragment {

    private View view;
    private SessionUtilisateur sessionUtilisateur ;
    private BackgroundTask backgroundTask;
    private ArrayList<Intervention> interventionEnAttenteArrayList;
    private InterventionAdapter interventionAdapter;
    private JSONObject jsonObject;
    private AlertDialog alertDialog;
    private ListView listView;
    private ListAdapter listAdapter;
    private String nomMethode;
    private Intent intent;


    public PartnerPrincipalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_partner_principal, container, false);
        listView = (ListView)view.findViewById(R.id.lvNotification);


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        alertDialog = new AlertDialog.Builder(getContext()).create();
        alertDialog.setIcon(R.drawable.erreur);
        alertDialog.setTitle("Erreur");
        sessionUtilisateur  = new SessionUtilisateur (getContext());
        interventionEnAttenteArrayList = new ArrayList<>();
        nomMethode = "getInterventionEnAttente";
        jsonObject = new JSONObject();


        try {
            jsonObject.put("login", sessionUtilisateur .getLogin());
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

                            String id = responseJsonObject.getString("id_intervention");
                            String motif = responseJsonObject.getString("motif_intervention");
                            String libelle = responseJsonObject.getString("libelle_motif_intervention");
                            String reseau = responseJsonObject.getString("reseau_intervention");
                            String date = responseJsonObject.getString("date_intervention");
                            String priorite = responseJsonObject.getString("priorite_intervention");
                            String statut = responseJsonObject.getString("statut_intervention");
                            String teladsl = responseJsonObject.getString("tel_adsl");

                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
                            Date convertedDate = new Date();
                            try {
                                convertedDate = dateFormat.parse(date);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            Intervention intervention = new Intervention(id, motif, libelle, reseau, convertedDate, priorite, statut, teladsl);
                            interventionEnAttenteArrayList.add(intervention);

                            count++;

                        } catch (JSONException e) {
                            alertDialog.setMessage(e.toString());
                            alertDialog.show();
                        }
                    }

                    interventionAdapter = new InterventionAdapter(getContext(), interventionEnAttenteArrayList);
                    listAdapter = interventionAdapter;
                    listView.setAdapter(listAdapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            Intervention intervention = interventionAdapter.getItem(position);
                            intent = new Intent(getContext(), PendingInterventionActivity.class);
                            intent.putExtra("Intervention", intervention);
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
