package com.example.dell.GestionIntervention.background;

import android.app.AlertDialog;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.dell.GestionIntervention.Entities.Intervention;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Hp on 05/04/2017.
 */

public class BackgroundTask {


    private Context context;
    private ArrayList<Intervention> interventionNonLuArrayList = new ArrayList<>();
    private String urlWCF = "http://192.168.1.5/GestionIntervention/index.php/GestionIntervention/";
    private SessionUtilisateur sessionUtilisateur;
    private JsonArrayRequest jsonArrayRequest;
    private JsonObjectRequest jsonObjectRequest;
    private AlertDialog alertDialog;

    public BackgroundTask(Context context) {
        this.context = context;
        SessionUtilisateur sessionUtilisateur = new SessionUtilisateur(context);
        alertDialog = new AlertDialog.Builder(context).create();
    }

    public void getArrayList(JSONObject jsonObject, String nomMethode, final arrayCallBack onCallBack) throws JSONException {


        jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, urlWCF + nomMethode, jsonObject, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                onCallBack.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onCallBack.onFail("Probleme de connexion chama");
            }
        });
        AppSingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);

    }

    public void getObject(JSONObject jsonObject, String nomMethode, final  objectCallBack onCallBack) {
        jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, urlWCF+nomMethode, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                onCallBack.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


              /*  onCallBack.onFail("Probleme de connexion dalhoum");*/

            }
        });
        AppSingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public interface arrayCallBack {

        void onSuccess(JSONArray response);

        void onFail(String msg);
    }

    public interface objectCallBack {

        void onSuccess(JSONObject response);

        void onFail(String msg);
    }

}
