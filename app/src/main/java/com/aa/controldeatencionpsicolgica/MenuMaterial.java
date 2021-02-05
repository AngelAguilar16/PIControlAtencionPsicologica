package com.aa.controldeatencionpsicolgica;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.aa.controldeatencionpsicolgica.Fragments.AgendaFragment;
import com.aa.controldeatencionpsicolgica.Fragments.CitasFragment;
import com.aa.controldeatencionpsicolgica.Fragments.ExpedientesFragment;
import com.aa.controldeatencionpsicolgica.Fragments.ReportesFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MenuMaterial extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_material);

        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);

        openFragment(new AgendaFragment());


        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.home:
                        openFragment(new AgendaActivity());
                        return true;

                    case R.id.trend:
                        openFragment(new CitasActivity());
                        return true;

                    case R.id.account:
                        openFragment(new AddNewCaso());
                        return true;

                    case R.id.setting:
                        openFragment(new ExpedienteActivity());
                        return true;

                }


                return false;
            }
        });

    }

    void openFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }
}