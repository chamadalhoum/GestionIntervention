package com.example.dell.GestionIntervention;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TimePicker;

import com.example.dell.GestionIntervention.Entities.Client;
import com.example.dell.GestionIntervention.Entities.FicheConfirmation;
import com.example.dell.GestionIntervention.Entities.Intervention;
import com.example.dell.GestionIntervention.Entities.Modem;
import com.example.dell.GestionIntervention.background.BackgroundTask;
import com.example.dell.GestionIntervention.background.SessionUtilisateur ;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ValidationActivity extends AppCompatActivity {

    Calendar myCalendar = Calendar.getInstance();
    EditText etDate, etTimeStart, etTimeEnd, etDetails, etModemEntrant;
    CheckBox cbProblemeResolu, cbProblemeNonResolu;
    LinearLayout switchRemove, frameRemove;
    Switch aSwitch;
    Intent intent;
    Intervention intervention;
    JSONObject jsonObject;
    String nomMethode;
    BackgroundTask backgroundTask;
    AlertDialog alertDialog, succesDialog;
    Spinner marqueSpinner, modeleSpinner, typeSpinner, couleurSpinner, refSpinner;
    private SessionUtilisateur  sessionUtilisateur ;
    Button bValider;
    AutoCompleteTextView etKilometerage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validation);


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };


        alertDialog = new AlertDialog.Builder(this).create();

        alertDialog.setIcon(R.drawable.erreur);
        alertDialog.setTitle("Erreur");

        succesDialog = new AlertDialog.Builder(this).create();

        succesDialog.setIcon((R.drawable.succes));
        succesDialog.setTitle("Succès");


        sessionUtilisateur  = new SessionUtilisateur (this);

        etDate = (EditText) findViewById(R.id.etDate);
        etTimeStart = (EditText) findViewById(R.id.etTimeStart);
        etTimeEnd = (EditText) findViewById(R.id.etTimeEnd);
        etDetails = (EditText) findViewById(R.id.etDetails);
        cbProblemeResolu = (CheckBox) findViewById(R.id.cbProblemResolu);
        cbProblemeNonResolu = (CheckBox) findViewById(R.id.cbProblemeNonResolu);
        switchRemove = (LinearLayout) findViewById(R.id.switchRemove);
        aSwitch = (Switch) findViewById(R.id.sEchangeModem);
        frameRemove = (LinearLayout) findViewById(R.id.frameRemove);
        marqueSpinner = (Spinner) findViewById(R.id.spMarque);
        modeleSpinner = (Spinner) findViewById(R.id.spModele);
        typeSpinner = (Spinner) findViewById(R.id.spType);
        couleurSpinner = (Spinner) findViewById(R.id.spCouleur);
        refSpinner = (Spinner) findViewById(R.id.spModemSortant);
        etModemEntrant = (EditText) findViewById(R.id.etModemRentrant);
        bValider = (Button) findViewById(R.id.bValid);
        etKilometerage = (AutoCompleteTextView)findViewById(R.id.etKilometreage);

        frameRemove.removeView(switchRemove);

        etDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(ValidationActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        etTimeStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(ValidationActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String output = String.format("%02d:%02d", selectedHour, selectedMinute);
                        etTimeStart.setText(output);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.show();

            }
        });

        etTimeEnd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(ValidationActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String output = String.format("%02d:%02d", selectedHour, selectedMinute);
                        etTimeEnd.setText(output);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.show();

            }
        });

        cbProblemeResolu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cbProblemeNonResolu.setChecked(false);
                } else if (!isChecked) {
                    cbProblemeNonResolu.setChecked(true);
                }
            }
        });

        cbProblemeNonResolu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cbProblemeResolu.setChecked(false);
                } else if (!isChecked) {
                    cbProblemeResolu.setChecked(true);
                }
            }
        });

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    frameRemove.addView(switchRemove);

                    backgroundTask = new BackgroundTask(ValidationActivity.this);
                    try {
                        backgroundTask.getArrayList(null, "geReferences", new BackgroundTask.arrayCallBack() {
                            @Override
                            public void onSuccess(JSONArray response) {

                                List<String> marqueArray = new ArrayList<String>();
                                int count = 0;
                                while(count<response.length()){
                                    try {
                                        JSONObject responseJsonObject = response.getJSONObject(count);

                                        String marques = responseJsonObject.getString("ref_modem");

                                        marqueArray.add(marques);
                                        count++;
                                    } catch (JSONException e) {
                                        alertDialog.setMessage(e.toString());
                                        alertDialog.show();
                                    }
                                }


                                ArrayAdapter<String> marqueAdapter = new ArrayAdapter<String>(ValidationActivity.this, android.R.layout.simple_spinner_item, marqueArray);
                                marqueAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                refSpinner.setAdapter(marqueAdapter);
                            }

                            @Override
                            public void onFail(String msg) {

                            }
                        });
                    } catch (JSONException e) {
                        alertDialog.setMessage(e.toString());
                        alertDialog.show();
                    }

                    nomMethode = "getMarques";
                    jsonObject = new JSONObject();

                    backgroundTask = new BackgroundTask(ValidationActivity.this);
                    try {
                        backgroundTask.getArrayList(null, nomMethode, new BackgroundTask.arrayCallBack() {
                            @Override
                            public void onSuccess(JSONArray response) {

                                List<String> marqueArray = new ArrayList<String>();
                                int count = 0;
                                while(count<response.length()){
                                    try {
                                        JSONObject responseJsonObject = response.getJSONObject(count);

                                        String marques = responseJsonObject.getString("marque_modem");

                                        marqueArray.add(marques);
                                        count++;
                                    } catch (JSONException e) {
                                        alertDialog.setMessage(e.toString());
                                        alertDialog.show();
                                    }
                                }


                                ArrayAdapter<String> marqueAdapter = new ArrayAdapter<String>(ValidationActivity.this, android.R.layout.simple_spinner_item, marqueArray);
                                marqueAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                marqueSpinner.setAdapter(marqueAdapter);
                            }

                            @Override
                            public void onFail(String msg) {

                            }
                        });
                    } catch (JSONException e) {
                        alertDialog.setMessage(e.toString());
                        alertDialog.show();
                    }

                        marqueSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            nomMethode = "getModels";
                            jsonObject = new JSONObject();
                            try {
                                jsonObject.put("marque_modem", marqueSpinner.getSelectedItem().toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            backgroundTask = new BackgroundTask(ValidationActivity.this);
                            try {
                                backgroundTask.getArrayList(jsonObject, nomMethode, new BackgroundTask.arrayCallBack() {
                                    @Override
                                    public void onSuccess(JSONArray response) {

                                        List<String> marqueArray = new ArrayList<String>();
                                        int count = 0;
                                        while(count<response.length()){
                                            try {
                                                JSONObject responseJsonObject = response.getJSONObject(count);

                                                String marques = responseJsonObject.getString("model_modem");

                                                marqueArray.add(marques);
                                                count++;
                                            } catch (JSONException e) {
                                                alertDialog.setMessage(e.toString());
                                                alertDialog.show();
                                            }
                                        }


                                        ArrayAdapter<String> marqueAdapter = new ArrayAdapter<String>(ValidationActivity.this, android.R.layout.simple_spinner_item, marqueArray);
                                        marqueAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        modeleSpinner.setAdapter(marqueAdapter);
                                    }

                                    @Override
                                    public void onFail(String msg) {

                                    }
                                });
                            } catch (JSONException e) {
                                alertDialog.setMessage(e.toString());
                                alertDialog.show();
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                    backgroundTask = new BackgroundTask(ValidationActivity.this);
                    try {
                        backgroundTask.getArrayList(null, "getTypes", new BackgroundTask.arrayCallBack() {
                            @Override
                            public void onSuccess(JSONArray response) {

                                List<String> marqueArray = new ArrayList<String>();
                                int count = 0;
                                while(count<response.length()){
                                    try {
                                        JSONObject responseJsonObject = response.getJSONObject(count);

                                        String marques = responseJsonObject.getString("type_modem");

                                        marqueArray.add(marques);
                                        count++;
                                    } catch (JSONException e) {
                                        alertDialog.setMessage(e.toString());
                                        alertDialog.show();
                                    }
                                }


                                ArrayAdapter<String> marqueAdapter = new ArrayAdapter<String>(ValidationActivity.this, android.R.layout.simple_spinner_item, marqueArray);
                                marqueAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                typeSpinner.setAdapter(marqueAdapter);
                            }

                            @Override
                            public void onFail(String msg) {

                            }
                        });
                    } catch (JSONException e) {
                        alertDialog.setMessage(e.toString());
                        alertDialog.show();
                    }

                    backgroundTask = new BackgroundTask(ValidationActivity.this);
                    try {
                        backgroundTask.getArrayList(null, "getCouleurs", new BackgroundTask.arrayCallBack() {
                            @Override
                            public void onSuccess(JSONArray response) {

                                List<String> marqueArray = new ArrayList<String>();
                                int count = 0;
                                while(count<response.length()){
                                    try {
                                        JSONObject responseJsonObject = response.getJSONObject(count);

                                        String marques = responseJsonObject.getString("couleur_modem");

                                        marqueArray.add(marques);
                                        count++;
                                    } catch (JSONException e) {
                                        alertDialog.setMessage(e.toString());
                                        alertDialog.show();
                                    }
                                }


                                ArrayAdapter<String> marqueAdapter = new ArrayAdapter<String>(ValidationActivity.this, android.R.layout.simple_spinner_item, marqueArray);
                                marqueAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                couleurSpinner.setAdapter(marqueAdapter);
                            }

                            @Override
                            public void onFail(String msg) {

                            }
                        });
                    } catch (JSONException e) {
                        alertDialog.setMessage(e.toString());
                        alertDialog.show();
                    }
                } else if (!isChecked) {
                    frameRemove.removeView(switchRemove);
                }
            }
        });


    }


    private void updateLabel() {

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

        etDate.setText(sdf.format(myCalendar.getTime()));
    }

    public void onClickValider(View view) {


        intent = getIntent();
        intervention = (Intervention) intent.getSerializableExtra("Intervention");
        jsonObject = new JSONObject();

        if(!aSwitch.isChecked()) {

            if (!etDate.getText().toString().isEmpty() && !etTimeStart.getText().toString().isEmpty() && !etTimeEnd.getText().toString().isEmpty() && !etDetails.getText().toString().isEmpty() && !etKilometerage.getText().toString().isEmpty()) {
                FicheConfirmation ficheConfirmation = new FicheConfirmation();
                ficheConfirmation.setDateDebut_fiche_confirmation(etDate.getText().toString() + " " + etTimeStart.getText().toString() + ":00");
                ficheConfirmation.setDateFin_fiche_confirmation(etDate.getText().toString() + " " + etTimeEnd.getText().toString() + ":00");
                ficheConfirmation.setDetails_fiche_confirmation(etDetails.getText().toString());
                ficheConfirmation.setkilometrage_fiche_confirmation(etKilometerage.getText().toString());

                if (cbProblemeResolu.isChecked()) {
                    ficheConfirmation.setStaut_fiche_confirmation("Resolu");
                } else {
                    ficheConfirmation.setStaut_fiche_confirmation("Non resolu");
                }


                try {
                    jsonObject.put("dateDebut_fiche_confirmation", ficheConfirmation.getDateDebut_fiche_confirmation());
                    jsonObject.put("dateFin_fiche_confirmation", ficheConfirmation.getDateFin_fiche_confirmation());
                    jsonObject.put("details_fiche_confirmation", ficheConfirmation.getDetails_fiche_confirmation());
                    jsonObject.put("staut_fiche_confirmation", ficheConfirmation.getStaut_fiche_confirmation());
                    jsonObject.put("ref_modem_sortant", "");
                    jsonObject.put("ref_modem_entrant", "");
                    jsonObject.put("id_intervention", intervention.getId_intervention());
                    jsonObject.put("kilometrage_fiche_confirmation", ficheConfirmation.getkilometrage_fiche_confirmation());

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                nomMethode = "saveFicheConfirmation";
                backgroundTask = new BackgroundTask(this);

                backgroundTask.getObject(jsonObject, nomMethode, new BackgroundTask.objectCallBack() {
                    @Override
                    public void onSuccess(JSONObject response) {

                        try {


                            Boolean resultatFicheConfirmation = response.getBoolean("saveFicheConfirmationResult");
                            if (resultatFicheConfirmation) {
                                succesDialog.setMessage("Fiche de confirmation enregistrée avec succés");
                                succesDialog.show();
                                Intent intentBack = new Intent(getApplicationContext(), PartnerActivity.class);
                                intentBack.putExtra("Etat", "CalendarValidation");
                                startActivity(intentBack);
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

            } else {
                alertDialog.setMessage("Verifier que tout les champs sont remplis");
                alertDialog.show();
            }
        }
        else if(aSwitch.isChecked()) {
            if (!etDate.getText().toString().isEmpty() && !etTimeStart.getText().toString().isEmpty() && !etTimeEnd.getText().toString().isEmpty() && !etDetails.getText().toString().isEmpty() && !etModemEntrant.getText().toString().isEmpty() && !etKilometerage.getText().toString().isEmpty()) {



                final FicheConfirmation ficheConfirmation = new FicheConfirmation();
                ficheConfirmation.setDateDebut_fiche_confirmation(etDate.getText().toString() + " " + etTimeStart.getText().toString() + ":00");
                ficheConfirmation.setDateFin_fiche_confirmation(etDate.getText().toString() + " " + etTimeEnd.getText().toString() + ":00");
                ficheConfirmation.setDetails_fiche_confirmation(etDetails.getText().toString());
                ficheConfirmation.setkilometrage_fiche_confirmation(etKilometerage.getText().toString());

                if (cbProblemeResolu.isChecked()) {
                    ficheConfirmation.setStaut_fiche_confirmation("Resolu");
                } else {
                    ficheConfirmation.setStaut_fiche_confirmation("Non resolu");
                }


                try {
                    jsonObject.put("dateDebut_fiche_confirmation", ficheConfirmation.getDateDebut_fiche_confirmation());
                    jsonObject.put("dateFin_fiche_confirmation", ficheConfirmation.getDateFin_fiche_confirmation());
                    jsonObject.put("details_fiche_confirmation", ficheConfirmation.getDetails_fiche_confirmation());
                    jsonObject.put("staut_fiche_confirmation", ficheConfirmation.getStaut_fiche_confirmation());
                    jsonObject.put("ref_modem_sortant", refSpinner.getSelectedItem().toString());
                    jsonObject.put("ref_modem_entrant", etModemEntrant.getText().toString());
                    jsonObject.put("id_intervention", intervention.getId_intervention());
                    jsonObject.put("kilometrage_fiche_confirmation", ficheConfirmation.getkilometrage_fiche_confirmation());

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                nomMethode = "saveFicheConfirmation";
                backgroundTask = new BackgroundTask(this);

                backgroundTask.getObject(jsonObject, nomMethode, new BackgroundTask.objectCallBack() {
                    @Override
                    public void onSuccess(JSONObject response) {

                        try {


                            Boolean resultatFicheConfirmation = response.getBoolean("saveFicheConfirmationResult");
                            if (resultatFicheConfirmation) {

                                JSONObject jsonObject = new JSONObject();

                                Modem modem = new Modem();
                                modem.setRef_modem(etModemEntrant.getText().toString());
                                modem.setMarque_modem(marqueSpinner.getSelectedItem().toString());
                                modem.setModel_modem(modeleSpinner.getSelectedItem().toString());
                                modem.setType_modem(typeSpinner.getSelectedItem().toString());
                                modem.setCouleur_modem(couleurSpinner.getSelectedItem().toString());
                                modem.setLogin_utilisateur(sessionUtilisateur.getLogin());
                                modem.setEtat_modem("Retour");


                                try {
                                    jsonObject.put("ref_modem", modem.getRef_modem());
                                    jsonObject.put("marque_modem", modem.getMarque_modem());
                                    jsonObject.put("model_modem", modem.getModel_modem());
                                    jsonObject.put("type_modem", modem.getType_modem());
                                    jsonObject.put("couleur_modem", modem.getCouleur_modem());
                                    jsonObject.put("login_utilisateur", modem.getLogin_utilisateur());
                                    jsonObject.put("etat_modem", modem.getEtat_modem());

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                backgroundTask = new BackgroundTask(getApplicationContext());

                                backgroundTask.getObject(jsonObject, "saveFicheConfirmationModem", new BackgroundTask.objectCallBack() {
                                    @Override
                                    public void onSuccess(JSONObject response) {

                                        try {


                                            Boolean resultatFicheConfirmationModem = response.getBoolean("saveFicheConfirmationModemResult");
                                            if (resultatFicheConfirmationModem) {
                                                succesDialog.setMessage("Fiche de confirmation enregistrée avec succés");
                                                succesDialog.show();
                                                bValider.setEnabled(false);
                                                Intent intentBack = new Intent(getApplicationContext(), PartnerActivity.class);
                                                intentBack.putExtra("Etat", "CalendarValidation");
                                                startActivity(intentBack);
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

            } else {
                alertDialog.setMessage("Verifier que tout les champs sont remplis");
                alertDialog.show();
            }

        }
    }
}
