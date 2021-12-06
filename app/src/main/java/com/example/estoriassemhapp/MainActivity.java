package com.example.estoriassemhapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //MainActivityViewModel vm = new ViewModelProvider(this).get(MainActivityViewModel.class);

        // Ações para a seleção da guia inferior de escolha de fragments.
        bottomNavigationView = findViewById(R.id.btNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.homeViewOp: // Se a visualização por grid for selecionada, exibir em formato grid.
                        MainFragment mainFragment = MainFragment.newInstance();
                        setFragment(mainFragment);
                        break;
                    case R.id.profileViewOp: // Se a visualização por grid for selecionada, exibir em formato grid.
                        ProfileFragment profileFragment = ProfileFragment.newInstance();
                        setFragment(profileFragment);
                        break;
                    case R.id.writeViewOp: // Se a visualização por grid for selecionada, exibir em formato grid.
                        WriteViewFragment writeViewFragment = WriteViewFragment.newInstance();
                        setFragment(writeViewFragment);
                        break;
                    case R.id.tagsViewOp: // Se a visualização por grid for selecionada, exibir em formato grid.
                        break;
                    case R.id.exitOp: // Se a visualização por grid for selecionada, exibir em formato grid.
                        break;
                }

                return true;
            }
        });
    }

    void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragContainer, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}