package com.example.dell.GestionIntervention;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.dell.GestionIntervention.Fragment.FactureFragment;
import com.example.dell.GestionIntervention.Fragment.PartnerCalendarFragment;
import com.example.dell.GestionIntervention.Fragment.PartnerPrincipalFragment;
import com.example.dell.GestionIntervention.Fragment.PendingStockFragment;
import com.example.dell.GestionIntervention.Fragment.ReceivedStockFragment;
import com.example.dell.GestionIntervention.background.SessionUtilisateur ;

public class PartnerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView;
    private SessionUtilisateur  sessionUtilisateur ;
    private PartnerPrincipalFragment partnerPrincipalFragment;
    private PartnerCalendarFragment partnerCalendarFragment;
    private PendingStockFragment pendingStockFragment;
    private ReceivedStockFragment receivedStockFragment;
    private FactureFragment factureFragment;
    private FragmentTransaction fragmentTransaction;
    private TextView tvPartnerLogin;
    private TextView tvPartnerNom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partner);

        partnerPrincipalFragment = new PartnerPrincipalFragment();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        sessionUtilisateur  = new SessionUtilisateur (this);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView =  navigationView.getHeaderView(0);
        tvPartnerLogin = (TextView)headerView.findViewById(R.id.tvPartnerLogin);
        tvPartnerNom = (TextView)headerView.findViewById(R.id.tvPartnerNom);
       // tvPartnerNom.setText(sessionUtilisateur .getUtilisateurNom() + " " + sessionUtilisateur .getUtilisateurPrenom());
      //  tvPartnerLogin.setText(sessionMangement.getUtilisateurLogin());
        navigationView.setNavigationItemSelectedListener(this);

        //Still not working here
        if(navigationView.getMenu().getItem(1).isChecked() == false && navigationView.getMenu().getItem(2).isChecked() == false && navigationView.getMenu().getItem(3).isChecked() == false) {

            navigationView.getMenu().getItem(0).setChecked(true);
            onNavigationItemSelected(navigationView.getMenu().getItem(0));
        }

        Intent intent = getIntent();

        if(intent.getStringExtra("Etat") != null) {

            String etat = intent.getStringExtra("Etat");

            if (etat.equalsIgnoreCase("intervention")) {
                navigationView.getMenu().getItem(0).setChecked(true);
                onNavigationItemSelected(navigationView.getMenu().getItem(0));
            }

            else if (etat.equalsIgnoreCase("CalendarValidation")){
                navigationView.getMenu().getItem(1).setChecked(true);
                onNavigationItemSelected(navigationView.getMenu().getItem(1));
            }

            else if (etat.equalsIgnoreCase("StockEnAttente")){
                navigationView.getMenu().getItem(3).setChecked(true);
                onNavigationItemSelected(navigationView.getMenu().getItem(3));
            }

            else if (etat.equalsIgnoreCase("Facture")){
                navigationView.getMenu().getItem(4).setChecked(true);
                onNavigationItemSelected(navigationView.getMenu().getItem(4));
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.partner, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_principale_P) {

            partnerPrincipalFragment = new PartnerPrincipalFragment();
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, partnerPrincipalFragment);
            fragmentTransaction.commit();

        }  else if (id == R.id.nav_calendrier_P) {

            partnerCalendarFragment = new PartnerCalendarFragment();
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, partnerCalendarFragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_stockEntrant_P) {

            pendingStockFragment = new PendingStockFragment();
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, pendingStockFragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_monStock_P) {
            receivedStockFragment = new ReceivedStockFragment();
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, receivedStockFragment);
            fragmentTransaction.commit();
        }

        else if (id == R.id.nav_deconnexion_P) {
            sessionUtilisateur.clearEdit();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            this.finish();
        }

        else if (id == R.id.nav_parameteres_P){
            Intent intent = new Intent(getApplicationContext(), ProfilActivity.class);
            startActivity(intent);
        }

        else if (id == R.id.nav_facture_P){
            factureFragment = new FactureFragment();
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, factureFragment);
            fragmentTransaction.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        /* Intent intent = getIntent();
        String pageResult;
        pageResult =  intent.getStringExtra("backValue");*/

       /* partnerPrincipalFragment = new PartnerPrincipalFragment();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, partnerPrincipalFragment);
        fragmentTransaction.commit(); */
    }


}
