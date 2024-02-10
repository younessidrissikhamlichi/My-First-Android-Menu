package com.mymenuapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    Toolbar toolbar;

    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Conteneurs */
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        /* Navigation Drawer Menu */
        navigationView.bringToFront();
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_home);
        mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void onBackPressed() {
        // Fermer le menu si je clique sur le bouton return
        if ( drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        // Définir une durée personnalisée
        int durationMillis = 5000; // 5 secondes

        if (id == R.id.nav_home) {
            Toast.makeText(this, "Vous êtes déjà dans la page de nav", Toast.LENGTH_LONG).show();
            return true;
        }
        else if (id == R.id.nav_calc) {
            startActivity(new Intent(MainActivity.this, MainActivityCalc.class));
        }
        else if (id == R.id.nav_share) {
            Snackbar.make(drawerLayout, "Traitement de l'opération", Snackbar.LENGTH_LONG)
                    .setAction("Annuler", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Snackbar.make(drawerLayout, "Opération annulée", Snackbar.LENGTH_LONG)
                                    .setTextColor(Color.RED).setBackgroundTint(Color.YELLOW).show();
                        }
                    })
                    .setActionTextColor(Color.RED)
                    .setTextColor(Color.GREEN)
                    .show();
        }
        else if (id == R.id.nav_rate) {
            Snackbar snackbar = Snackbar.make(drawerLayout, "Traitement de l'opération", Snackbar.LENGTH_LONG);
                    //snackbar.setDuration(durationMillis);
                    snackbar.show();

                    snackbar.setAction("Annuler", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Snackbar.make(drawerLayout, "Opération annulée", Snackbar.LENGTH_LONG)
                                    .setTextColor(Color.RED).setBackgroundTint(Color.YELLOW).show();
                        }
                    });


            // Ajouter un callback après un délai de 2 secondes
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    snackbar.addCallback(new Snackbar.Callback() {
                        @Override
                        public void onShown(Snackbar snackbar) {
                            super.onShown(snackbar);
                            // Action à exécuter lorsque le Snackbar est affiché
                            Snackbar.make(drawerLayout, "Opération effectuée 1", Snackbar.LENGTH_LONG).show();
                        }

                        @Override
                        public void onDismissed(Snackbar snackbar, int event) {
                            super.onDismissed(snackbar, event);
                            // Action à exécuter lorsque le Snackbar est fermé
                            Snackbar.make(drawerLayout, "Opération effectuée", Snackbar.LENGTH_LONG).show();
                        }
                    });
                }
            }, 2000); // Délai de 2 secondes (2000 millisecondes)
        }
        // Fermer le menu
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }
}