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

import com.example.dell.GestionIntervention.Entities.Modem;
import com.example.dell.GestionIntervention.PendingInterventionActivity;
import com.example.dell.GestionIntervention.R;
import com.example.dell.GestionIntervention.RecievedStockActivity;
import com.example.dell.GestionIntervention.background.BackgroundTask;
import com.example.dell.GestionIntervention.background.SessionUtilisateur ;
import com.example.dell.GestionIntervention.background.StockAdapter;

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
public class ReceivedStockFragment extends Fragment {

    private View view;
    private SessionUtilisateur  sessionUtilisateur ;
    private BackgroundTask backgroundTask;
    private ArrayList<Modem> modemEnAttenteArrayList;
    private StockAdapter stockAdapter;
    private JSONObject jsonObject;
    private AlertDialog alertDialog;
    private ListView listView;
    private ListAdapter listAdapter;
    private String nomMethode;
    private Intent intent;


    public ReceivedStockFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_received_stock, container, false);

        listView = (ListView)view.findViewById(R.id.lvReceivedStockNotification);
        alertDialog = new AlertDialog.Builder(getContext()).create();
        alertDialog.setIcon(R.drawable.erreur);
        alertDialog.setTitle("Erreur");
        sessionUtilisateur  = new SessionUtilisateur (getContext());
        modemEnAttenteArrayList = new ArrayList<>();
        nomMethode = "getAllModemsRecus";
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

                            String ref = responseJsonObject.getString("ref_modem");
                            String marque = responseJsonObject.getString("marque_modem");
                            String model = responseJsonObject.getString("model_modem");
                            String type = responseJsonObject.getString("type_modem");
                            String couleur = responseJsonObject.getString("couleur_modem");
                            String etat = responseJsonObject.getString("etat_modem");
                            String dateSortie = responseJsonObject.getString("date_sortie");
                            String dateEntree = responseJsonObject.getString("date_entree");
                            String loginUtilisateur = responseJsonObject.getString("login_utilisateur");
                            String loginAdministrateur = responseJsonObject.getString("login_administrateur");

                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                            SimpleDateFormat dateOutFormat = new SimpleDateFormat("dd/MM/yyyy");
                            Date convertedDateSortie = new Date();
                            Date convertedDateEntree = new Date();
                            try {
                                convertedDateSortie = dateFormat.parse(dateSortie);
                                convertedDateEntree = dateFormat.parse(dateEntree);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            Modem modem = new Modem(ref, marque, model, type, couleur, etat, convertedDateSortie, loginUtilisateur, loginAdministrateur);
                            modem.setDate_entree(convertedDateEntree);
                            modemEnAttenteArrayList.add(modem);

                            count++;

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    stockAdapter = new StockAdapter(getContext(), modemEnAttenteArrayList);
                    listAdapter = stockAdapter;
                    listView.setAdapter(listAdapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            Modem modem = stockAdapter.getItem(position);
                            intent = new Intent(getContext(), RecievedStockActivity.class);
                            intent.putExtra("Modem", modem);
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

        return view;
    }

}
