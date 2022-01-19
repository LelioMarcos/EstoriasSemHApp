package com.example.estoriassemhapp.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.estoriassemhapp.R;
import com.example.estoriassemhapp.adapter.StoryAdapter;
import com.example.estoriassemhapp.model.CommentsViewModel;
import com.example.estoriassemhapp.model.MainViewModel;
import com.example.estoriassemhapp.model.SearchViewModel;
import com.example.estoriassemhapp.model.Story;

import java.util.List;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ProgressBar pbSearch = findViewById(R.id.pbSearch);
        pbSearch.setVisibility(View.VISIBLE);

        TextView tvNoStorySearch = findViewById(R.id.tvNoStorySearch);
        tvNoStorySearch.setVisibility(View.GONE);

        Toolbar toolbar = findViewById(R.id.tbMain);
        setSupportActionBar(toolbar);

        // Adicionar a opção de voltar para a página inicial na toolbar.
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        String query = "";

        if (i.hasExtra("query")) {
            query = i.getStringExtra("query");
        } else if (i.hasExtra("genero")) {
            query = i.getStringExtra("genero");
        }

        RecyclerView rvSearchResult = findViewById(R.id.rvSearchResult);
        rvSearchResult.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvSearchResult.setLayoutManager(layoutManager);

        //Configuração do Adapter (parte do recycler view)
        SearchViewModel searchViewModel = new ViewModelProvider(SearchActivity.this, new SearchViewModel.SearchViewModelFactory(query)).get(SearchViewModel.class);
        LiveData<List<Story>> stories = searchViewModel.getStories();
        stories.observe(this, new Observer<List<Story>>() {
            @Override
            public void onChanged(List<Story> stories) {
                pbSearch.setVisibility(View.GONE);

                if (!stories.isEmpty()) {
                    StoryAdapter storyAdapter = new StoryAdapter(SearchActivity.this, stories);
                    rvSearchResult.setAdapter(storyAdapter);
                } else {
                    tvNoStorySearch.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        // Criar a toolbar da activity.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_nav_menu, menu);
        return true;
    }
}