package com.example.estoriassemhapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.estoriassemhapp.fragment.MainFragment;
import com.example.estoriassemhapp.fragment.ProfileFragment;
import com.example.estoriassemhapp.R;
import com.example.estoriassemhapp.fragment.TagsFragment;
import com.example.estoriassemhapp.fragment.WriteViewFragment;
import com.example.estoriassemhapp.model.MainViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainViewModel mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        mainViewModel.getStories();

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
                        TagsFragment tagsFragment = TagsFragment.newInstance();
                        setFragment(tagsFragment);
                        break;
                    case R.id.exitOp: // Se a visualização por grid for selecionada, exibir em formato grid.
                        logout();
                        break;
                }

                return true;
            }
        });

        Toolbar toolbar = findViewById(R.id.tbMain);
        setSupportActionBar(toolbar);
    }

    void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragContainer, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    void logout() {
        Intent i = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(i);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_nav_menu, menu);

        super.onPrepareOptionsMenu(menu);
        MenuItem myActionMenuItem = menu.findItem(R.id.opSearch);
        SearchView searchView = (SearchView) myActionMenuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent i = new Intent(MainActivity.this, SearchActivity.class);
                i.putExtra("query", query);

                startActivity(i);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }
}