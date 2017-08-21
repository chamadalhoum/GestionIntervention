package com.example.dell.GestionIntervention.background;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;



public class AppSingleton {

    private static AppSingleton instance;
    private RequestQueue requestQueue;
    private static Context context;

    public AppSingleton(Context context) {
        this.context = context;
        requestQueue = getRequestQueue();
    }

    public RequestQueue getRequestQueue() {

        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }

        return requestQueue;
    }

    public static synchronized AppSingleton getInstance(Context context){
        if (instance == null) {
            instance = new AppSingleton(context);
        }

        return instance;
    }

    public<T> void addToRequestQueue(Request<T> request){
        requestQueue.add(request);
    }
}
