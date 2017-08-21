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

import com.example.dell.GestionIntervention.Entities.Facture;
import com.example.dell.GestionIntervention.Entities.Intervention;
import com.example.dell.GestionIntervention.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;



public class FactureAdapter extends ArrayAdapter<Facture> {

    private Facture facture;
    private TextView tvSujet, tvText,tvDate;
    private ImageView ivClient;

    public FactureAdapter(@NonNull Context context, ArrayList<Facture> factures) {
        super(context, R.layout.row_view, factures);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.row_view, parent, false);

        facture = getItem(position);
        tvSujet = (TextView)view.findViewById(R.id.tvSujet);
        ivClient = (ImageView)view.findViewById(R.id.ivNotification);
        tvText = (TextView)view.findViewById(R.id.tvTexte);
        tvDate = (TextView)view.findViewById(R.id.tvDate);

        tvSujet.setText("Facture");
        tvText.setText("Montant totale: " + facture.getMontant_facture() + " DT");

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(facture.getDate_facture());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String output = dateFormat.format(convertedDate);

        tvDate.setText(output);
        ivClient.setBackgroundResource(R.drawable.facturelist);

        return view;
    }

    @Nullable
    @Override
    public Facture getItem(int position) {
        return super.getItem(position);
    }
}
