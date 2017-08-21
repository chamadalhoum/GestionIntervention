package com.example.dell.GestionIntervention.Fragment;


import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.dell.GestionIntervention.Entities.Intervention;
import com.example.dell.GestionIntervention.Entities.Modem;
import com.example.dell.GestionIntervention.PartnerActivity;
import com.example.dell.GestionIntervention.PendingInterventionActivity;
import com.example.dell.GestionIntervention.PendingStockActivity;
import com.example.dell.GestionIntervention.R;
import com.example.dell.GestionIntervention.RecievedInterventionActivity;
import com.example.dell.GestionIntervention.TechnicianActivity;
import com.example.dell.GestionIntervention.background.BackgroundTask;
import com.example.dell.GestionIntervention.background.InterventionAdapter;
import com.example.dell.GestionIntervention.background.SessionUtilisateur;
import com.example.dell.GestionIntervention.background.StockAdapter;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import hirondelle.date4j.DateTime;


/**
 * A simple {@link Fragment} subclass.
 */
public class PartnerCalendarFragment extends Fragment {

    private View view;
    private SessionUtilisateur  sessionUtilisateur ;
    private BackgroundTask backgroundTask;
    private ArrayList<Intervention> interventionEnAttenteArrayList;
    private InterventionAdapter interventionAdapter;
    private JSONObject jsonObject;
    private AlertDialog alertDialog;
    private ListView listView;
    private ListAdapter listAdapter;
    private String nomMethode;
    private Intent intent;
    final CaldroidFragment caldroidFragment = new CaldroidFragment();

    public PartnerCalendarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_partner_calendar, container, false);

        Bundle args = new Bundle();
        Calendar cal = Calendar.getInstance();
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        caldroidFragment.setArguments(args);
        alertDialog = new AlertDialog.Builder(getContext()).create();
        alertDialog.setIcon(R.drawable.erreur);
        alertDialog.setTitle("Erreur");
        sessionUtilisateur  = new SessionUtilisateur (getContext());
        listView = (ListView)view.findViewById(R.id.lvCalendar);


        final LinearLayout caldroidHolder = new LinearLayout(getContext());
        caldroidHolder.setId(View.generateViewId());
        caldroidHolder.setOrientation(LinearLayout.VERTICAL);

        FragmentTransaction t = getChildFragmentManager().beginTransaction();
        t.add(caldroidHolder.getId(), caldroidFragment);
        t.commit();

        final Button dateButton = new Button(getContext());
        dateButton.setBackgroundColor(getResources().getColor(R.color.BlueIve));
        dateButton.setTextColor(getResources().getColor(R.color.caldroid_white));

        listView.addHeaderView(caldroidHolder);
        listView.setVisibility(View.VISIBLE);
        listView.setAdapter(listAdapter);

        nomMethode = "getAllInterventionsDate";
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

                            String date = responseJsonObject.getString("date");
                            //String day = responseJsonObject.getString("day");


                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
                            Date convertedDate = new Date();
                            try {
                                convertedDate = dateFormat.parse(date);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            caldroidFragment.setTextColorForDate(R.color.BlueIve, convertedDate);
                            caldroidFragment.refreshView();
                            count++;

                        } catch (JSONException e) {
                            alertDialog.setMessage(e.toString());
                            alertDialog.show();
                        }
                    }
                }

                @Override
                public void onFail(String msg) {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final CaldroidListener listener = new CaldroidListener() {
            @Override
            public void onSelectDate(Date date, View view) {

                caldroidHolder.removeView(dateButton);
                dateButton.setText(SimpleDateFormat.getDateInstance(SimpleDateFormat.LONG, Locale.FRENCH).format(date));
                caldroidHolder.addView(dateButton);
                interventionEnAttenteArrayList = new ArrayList<>();
                nomMethode = "getAllInterventionsForCalendar";
                jsonObject = new JSONObject();

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                String dateJson = dateFormat.format(date);

                try {
                    jsonObject.put("login", sessionUtilisateur .getLogin());
                    jsonObject.put("date", dateJson);
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

                                    Intervention intervention = interventionAdapter.getItem(position-1);
                                    if(intervention.getStatut_intervention().equalsIgnoreCase("En Attente")) {
                                        intent = new Intent(getContext(), PendingInterventionActivity.class);
                                        intent.putExtra("Intervention", intervention);
                                        startActivity(intent);
                                    }
                                    else if(intervention.getStatut_intervention().equalsIgnoreCase("Accepte")){
                                        intent = new Intent(getContext(), RecievedInterventionActivity.class);
                                        intent.putExtra("Intervention", intervention);
                                        startActivity(intent);
                                    }
                                    else {
                                    }
                                }
                            });
                        }

                        @Override
                        public void onFail(String msg) {
                            ListAdapter listAdapterVide = null;
                            listView.setAdapter(listAdapterVide);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        caldroidFragment.setCaldroidListener(listener);

        return view;
    }

}
