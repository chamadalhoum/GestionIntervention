package com.example.dell.GestionIntervention;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dell.GestionIntervention.background.BackgroundTask;
import com.example.dell.GestionIntervention.background.SessionUtilisateur ;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfilActivity extends AppCompatActivity {


    EditText monNom, monPrenom, monTel, monEmail,monDelegation,monLocalite,monCodePostal , monMotDePasse,monStatutfamilial,monLogin,monGouvernorat,monCin, ancienMotDePasse, nouveauMotDePasse, nouveauMotDePasse2;
    TextView modifierTel, modifierEmail, modifierMotDePasse;
    SessionUtilisateur  sessionUtilisateur;
    Boolean modeEmail = false, modeTel = false, modeMotDePasse = false;
    LinearLayout lyModifTel, lyModifEmail, lyModifMotDePasse;
    TextView tvValiderTel, tvValiderEmail, tvValiderMotDePasse, tvAncientMotDePasse, tvNouveautMotDePasse, tvNouveauMotDePasse2;
    JSONObject jsonObject;
    BackgroundTask backgroundTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        alertDialog.setIcon(R.drawable.erreur);
        alertDialog.setTitle("Erreur");

        final AlertDialog succesDialog = new AlertDialog.Builder(this).create();

        succesDialog.setIcon((R.drawable.succes));
        succesDialog.setTitle("Succès");

        sessionUtilisateur = new SessionUtilisateur(this);
        monNom = (EditText)findViewById(R.id.etNom);
        monPrenom = (EditText)findViewById(R.id.etPrenom);
		monCin = (EditText)findViewById(R.id.etCin);
		monStatutfamilial = (EditText)findViewById(R.id.etStatutfamilial);
        monTel = (EditText)findViewById(R.id.etTel);
        monEmail = (EditText)findViewById(R.id.etEmail);
		monGouvernorat = (EditText)findViewById(R.id.etGouvernorat);
		monDelegation = (EditText)findViewById(R.id.etDelegation);
		monLocalite = (EditText)findViewById(R.id.etLocalite);
		monCodePostal = (EditText)findViewById(R.id.etCodePostal);

		monLogin = (EditText)findViewById(R.id.etLogin);
        monMotDePasse = (EditText)findViewById(R.id.etMotDePasse);
        modifierTel = (TextView)findViewById(R.id.modifierTel);
        modifierEmail = (TextView)findViewById(R.id.modifierEmail);
        modifierMotDePasse = (TextView)findViewById(R.id.modifierMotDePasse);
        backgroundTask = new BackgroundTask(this);

        lyModifTel = (LinearLayout)findViewById(R.id.lyModifTel);
        lyModifEmail = (LinearLayout)findViewById(R.id.lyModifEmail);
        lyModifMotDePasse = (LinearLayout)findViewById(R.id.lyModifMotDePasse);

        ancienMotDePasse = new EditText(this);
        nouveauMotDePasse = new EditText(this);
        nouveauMotDePasse2 = new EditText(this);

        tvAncientMotDePasse = new TextView(this);
        tvNouveautMotDePasse = new TextView(this);
        tvNouveauMotDePasse2 = new TextView(this);

        ancienMotDePasse.setBackgroundColor(getResources().getColor(R.color.caldroid_white));
        nouveauMotDePasse.setBackgroundColor(getResources().getColor(R.color.caldroid_white));
        nouveauMotDePasse2.setBackgroundColor(getResources().getColor(R.color.caldroid_white));

        tvValiderTel = new TextView(this);
        tvValiderTel.setTextColor(getResources().getColor(R.color.caldroid_black));
        tvValiderTel.setPadding(0,20,0,0);
        tvValiderTel.setGravity(Gravity.CENTER);
        tvValiderTel.setText("Valider");
        tvValiderTel.setId(View.generateViewId());

        tvAncientMotDePasse = new TextView(this);
        tvAncientMotDePasse.setTextColor(getResources().getColor(R.color.caldroid_black));
        tvAncientMotDePasse.setPadding(0,20,0,20);
        tvAncientMotDePasse.setText("Ancien mot de passe");

        tvNouveautMotDePasse = new TextView(this);
        tvNouveautMotDePasse.setTextColor(getResources().getColor(R.color.caldroid_black));
        tvNouveautMotDePasse.setPadding(0,20,0,20);
        tvNouveautMotDePasse.setText("Nouveau mot de passe");

        tvNouveauMotDePasse2 = new TextView(this);
        tvNouveauMotDePasse2.setTextColor(getResources().getColor(R.color.caldroid_black));
        tvNouveauMotDePasse2.setPadding(0,20,0,20);
        tvNouveauMotDePasse2.setText("Nouveau mot de passe");

        tvValiderEmail = new TextView(this);
        tvValiderEmail.setTextColor(getResources().getColor(R.color.caldroid_black));
        tvValiderEmail.setPadding(0,20,0,0);
        tvValiderEmail.setGravity(Gravity.CENTER);
        tvValiderEmail.setText("Valider");
        tvValiderEmail.setId(View.generateViewId());

        tvValiderMotDePasse = new TextView(this);
        tvValiderMotDePasse.setTextColor(getResources().getColor(R.color.caldroid_black));
        tvValiderMotDePasse.setPadding(0,20,0,0);
        tvValiderMotDePasse.setGravity(Gravity.CENTER);
        tvValiderMotDePasse.setText("Valider");
        tvValiderMotDePasse.setId(View.generateViewId());

        monNom.setText(sessionUtilisateur .getNom());
        monLocalite.setText(sessionUtilisateur .getLocalite());
		monLogin.setText(sessionUtilisateur .getLogin());

		monGouvernorat.setText(sessionUtilisateur .getGouvernorat());
		monCin.setText(sessionUtilisateur .getCin());
		monCodePostal.setText(sessionUtilisateur .getCodePostal());
        monTel.setText(sessionUtilisateur.getTel());
        monEmail.setText(sessionUtilisateur .getEmail());
		monStatutfamilial.setText(sessionUtilisateur .getStatutfamilial());

        monMotDePasse.setText(sessionUtilisateur.getMotdepasse());

        monNom.setBackgroundColor(getResources().getColor(R.color.Grey));
        monPrenom.setBackgroundColor(getResources().getColor(R.color.Grey));
        monTel.setBackgroundColor(getResources().getColor(R.color.Grey));
		monLocalite.setBackgroundColor(getResources().getColor(R.color.Grey));
		monLogin.setBackgroundColor(getResources().getColor(R.color.Grey));
		monGouvernorat.setBackgroundColor(getResources().getColor(R.color.Grey));
		monCin.setBackgroundColor(getResources().getColor(R.color.Grey));
		monCodePostal.setBackgroundColor(getResources().getColor(R.color.Grey));
		monStatutfamilial.setBackgroundColor(getResources().getColor(R.color.Grey));
        monEmail.setBackgroundColor(getResources().getColor(R.color.Grey));
        monMotDePasse.setBackgroundColor(getResources().getColor(R.color.Grey));

        tvValiderTel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (monTel.getText().toString().length() != 8) {

                    alertDialog.setMessage("La longueur doit etre 8 chiffre");
                    alertDialog.show();

                } else if (monTel.getText().toString().length() == 8) {

                    String nomMethode = "modifyTel";
                    jsonObject = new JSONObject();


                    try {
                        jsonObject.put("login", sessionUtilisateur.getLogin());
                        jsonObject.put("tel", monTel.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    backgroundTask.getObject(jsonObject, nomMethode, new BackgroundTask.objectCallBack() {
                        @Override
                        public void onSuccess(JSONObject response) {

                            try {

                                boolean result = response.getBoolean("ModifyTelResult");

                                if(result == true){

                                    succesDialog.setMessage("Num Téléphone modifié avec succès");
                                    succesDialog.show();
                                    sessionUtilisateur.setTel(monTel.getText().toString());

                                    monTel.setClickable(false);
                                    monTel.setFocusable(false);
                                    monTel.setFocusableInTouchMode(false);
                                    monTel.setCursorVisible(false);
                                    monTel.setBackgroundColor(getResources().getColor(R.color.Grey));
                                    modeTel = false;
                                    lyModifTel.removeView(tvValiderTel);

                                }
                                else{
                                    alertDialog.setMessage("probleme survenu!");
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


            }

        });

        tvValiderEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isEmailValid(monEmail.getText().toString())) {

                    alertDialog.setMessage("Verifer l'adresse e-mail");
                    alertDialog.show();

                } else if (isEmailValid(monEmail.getText().toString())) {

                    String nomMethode = "modifyEmail";
                    jsonObject = new JSONObject();


                    try {
                        jsonObject.put("login", sessionUtilisateur .getLogin());
                        jsonObject.put("email", monEmail.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    backgroundTask.getObject(jsonObject, nomMethode, new BackgroundTask.objectCallBack() {
                        @Override
                        public void onSuccess(JSONObject response) {

                            try {

                                boolean result = response.getBoolean("ModifyEmailResult");

                                if (result == true) {

                                    succesDialog.setMessage("E-mail modifié avec succès");
                                    succesDialog.show();
                                    sessionUtilisateur.setEmail(monEmail.getText().toString());

                                    monEmail.setClickable(false);
                                    monEmail.setFocusable(false);
                                    monEmail.setFocusableInTouchMode(false);
                                    monEmail.setCursorVisible(false);
                                    monEmail.setBackgroundColor(getResources().getColor(R.color.Grey));
                                    modeEmail = false;
                                    lyModifEmail.removeView(tvValiderEmail);

                                } else {
                                    alertDialog.setMessage("probleme survenu!");
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
            }
        });

        tvValiderMotDePasse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ancienMotDePasse.getText().toString().equalsIgnoreCase(sessionUtilisateur.getMotdepasse()) || !nouveauMotDePasse.getText().toString().equals(nouveauMotDePasse2.getText().toString()) || nouveauMotDePasse.getText().toString().length() < 6) {

                    alertDialog.setMessage("Verifer que l'ancien mot de passe et correct, et les champs du nouveau mot de passe sont identiques, et le nombre des caractères du nouveau mot de passe superieurs a 6");
                    alertDialog.show();

                } else if (ancienMotDePasse.getText().toString().equalsIgnoreCase(sessionUtilisateur.getMotdepasse()) || nouveauMotDePasse.getText().toString().equals(nouveauMotDePasse2.getText().toString())) {

                    String nomMethode = "modifyMotDePasse";
                    jsonObject = new JSONObject();


                    try {
                        jsonObject.put("login", sessionUtilisateur.getLogin());
                        jsonObject.put("motDePasse", nouveauMotDePasse.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    backgroundTask.getObject(jsonObject, nomMethode, new BackgroundTask.objectCallBack() {
                        @Override
                        public void onSuccess(JSONObject response) {

                            try {

                                boolean result = response.getBoolean("ModifyMotDePasseResult");

                                if (result == true) {

                                    succesDialog.setMessage("Mot de passe modifié avec succès");
                                    succesDialog.show();
                                    sessionUtilisateur.setMotdepasse(nouveauMotDePasse.getText().toString());

                                    lyModifMotDePasse.removeView(tvNouveauMotDePasse2);
                                    lyModifMotDePasse.removeView(tvNouveautMotDePasse);
                                    lyModifMotDePasse.removeView(tvAncientMotDePasse);
                                    lyModifMotDePasse.removeView(ancienMotDePasse);
                                    lyModifMotDePasse.removeView(nouveauMotDePasse);
                                    lyModifMotDePasse.removeView(nouveauMotDePasse2);
                                    lyModifMotDePasse.removeView(tvValiderMotDePasse);
                                    monMotDePasse.setText(sessionUtilisateur .getMotdepasse());
                                    modeMotDePasse = false;
                                    lyModifEmail.removeView(tvValiderEmail);

                                } else {
                                    alertDialog.setMessage("probleme survenu!");
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
            }
        });

        modifierTel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(modeTel == false) {

                    monTel.setClickable(true);
                    monTel.setFocusable(true);
                    monTel.setFocusableInTouchMode(true);
                    monTel.setCursorVisible(true);
                    monTel.setBackgroundColor(getResources().getColor(R.color.caldroid_white));
                    modeTel = true;
                    lyModifTel.addView(tvValiderTel);

                }else if (modeTel == true)
                {
                    monTel.setClickable(false);
                    monTel.setFocusable(false);
                    monTel.setFocusableInTouchMode(false);
                    monTel.setCursorVisible(false);
                    monTel.setBackgroundColor(getResources().getColor(R.color.Grey));
                    monTel.setText(sessionUtilisateur.getTel());
                    modeTel = false;

                    lyModifTel.removeView(tvValiderTel);
                }
            }
        });

        modifierEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(modeEmail == false) {
                    monEmail.setClickable(true);
                    monEmail.setFocusable(true);
                    monEmail.setFocusableInTouchMode(true);
                    monEmail.setCursorVisible(true);
                    monEmail.setBackgroundColor(getResources().getColor(R.color.caldroid_white));
                    lyModifEmail.addView(tvValiderEmail);
                    modeEmail = true;
                }else if (modeEmail == true)
                {
                    monEmail.setClickable(false);
                    monEmail.setFocusable(false);
                    monEmail.setFocusableInTouchMode(false);
                    monEmail.setCursorVisible(false);
                    monEmail.setText(sessionUtilisateur.getEmail());
                    monEmail.setBackgroundColor(getResources().getColor(R.color.Grey));
                    lyModifEmail.removeView(tvValiderEmail);
                    modeEmail = false;
                }
            }
        });

        modifierMotDePasse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(modeMotDePasse == false) {

                    lyModifMotDePasse.addView(tvAncientMotDePasse);
                    lyModifMotDePasse.addView(ancienMotDePasse);
                    lyModifMotDePasse.addView(tvNouveautMotDePasse);
                    lyModifMotDePasse.addView(nouveauMotDePasse);
                    lyModifMotDePasse.addView(tvNouveauMotDePasse2);
                    lyModifMotDePasse.addView(nouveauMotDePasse2);
                    lyModifMotDePasse.addView(tvValiderMotDePasse);
                    modeMotDePasse = true;

                }else if (modeMotDePasse == true)
                {
                    lyModifMotDePasse.removeView(tvNouveauMotDePasse2);
                    lyModifMotDePasse.removeView(tvNouveautMotDePasse);
                    lyModifMotDePasse.removeView(tvAncientMotDePasse);
                    lyModifMotDePasse.removeView(ancienMotDePasse);
                    lyModifMotDePasse.removeView(nouveauMotDePasse);
                    lyModifMotDePasse.removeView(nouveauMotDePasse2);
                    lyModifMotDePasse.removeView(tvValiderMotDePasse);
                    monMotDePasse.setText(sessionUtilisateur.getMotdepasse());
                    modeMotDePasse = false;
                }
            }
        });

    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
