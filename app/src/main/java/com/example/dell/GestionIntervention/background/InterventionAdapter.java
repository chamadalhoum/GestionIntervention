package com.example.dell.GestionIntervention.background;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dell.GestionIntervention.Entities.Intervention;
import com.example.dell.GestionIntervention.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class InterventionAdapter extends ArrayAdapter<Intervention> {

    private Intervention intervention;
    private TextView tvSujet, tvText,tvDate;
    private ImageView ivClient;

    public InterventionAdapter(@NonNull Context context, ArrayList<Intervention> interventions) {
        super(context, R.layout.row_view, interventions);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.row_view, parent, false);

        intervention = getItem(position);
        tvSujet = (TextView)view.findViewById(R.id.tvSujet);
        ivClient = (ImageView)view.findViewById(R.id.ivNotification);
        tvText = (TextView)view.findViewById(R.id.tvTexte);
        tvDate = (TextView)view.findViewById(R.id.tvDate);

        tvSujet.setText(intervention.getMotif_intervention());
        tvText.setText(intervention.getLibelle_motif_intervention());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String output = dateFormat.format(intervention.getDate_intervention());
        tvDate.setText(output);

        if(intervention.getStatut_intervention().equalsIgnoreCase("en attente")) {
            ivClient.setBackgroundResource(R.drawable.intervention);
        }else if(intervention.getStatut_intervention().equalsIgnoreCase("accepte")){
            ivClient.setBackgroundResource(R.drawable.interventionaccepter);
        }else if(intervention.getStatut_intervention().equalsIgnoreCase("validee")) {
            ivClient.setBackgroundResource(R.drawable.interventionvalider);
        }
        return view;
    }

    @Nullable
    @Override
    public Intervention getItem(int position) {
        return super.getItem(position);
    }
}
