package com.example.dell.GestionIntervention.background;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dell.GestionIntervention.Entities.Intervention;
import com.example.dell.GestionIntervention.Entities.Modem;
import com.example.dell.GestionIntervention.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class StockAdapter extends ArrayAdapter<Modem> {

    private Modem modem;
    private TextView tvSujet, tvRef;
    private ImageView ivModem;
    private View viewBas;
    private TextView tvDate;


    public StockAdapter(@NonNull Context context, ArrayList<Modem> modems) {
        super(context, R.layout.row_view, modems);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.row_view, parent, false);

        modem = getItem(position);
        tvSujet = (TextView)view.findViewById(R.id.tvSujet);
        ivModem = (ImageView)view.findViewById(R.id.ivNotification);
        tvRef = (TextView)view.findViewById(R.id.tvTexte);
        tvDate = (TextView)view.findViewById(R.id.tvDate);
        tvSujet.setText(modem.getMarque_modem());
        tvRef.setText("Ref: " + modem.getRef_modem());

        if(modem.getEtat_modem().equalsIgnoreCase("en attente")) {
            ivModem.setBackgroundResource(R.drawable.modem);
        }else if(modem.getEtat_modem().equalsIgnoreCase("recu")) {
            ivModem.setBackgroundResource(R.drawable.modemrecu);
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String output = dateFormat.format(modem.getDate_sortie());
        tvDate.setText(output);

        return view;
    }

    @Nullable
    @Override
    public Modem getItem(int position) {
        return super.getItem(position);
    }
}
