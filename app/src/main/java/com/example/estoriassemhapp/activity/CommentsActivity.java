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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.estoriassemhapp.R;
import com.example.estoriassemhapp.adapter.CommentsAdapter;
import com.example.estoriassemhapp.model.Comment;
import com.example.estoriassemhapp.model.CommentsViewModel;

import java.util.List;

public class CommentsActivity extends AppCompatActivity {

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        Toolbar toolbar = findViewById(R.id.tbMain);
        setSupportActionBar(toolbar);
        Intent i = getIntent();

        id = i.getStringExtra("idhist");

        /*
        Intent i =getIntent();
        String id = i.getStringExtra("id");

        CommentsViewModel commentsViewModel = new ViewModelProvider(this, new CommentsViewModel.CommentsViewModelFactory(id)).get(CommentsViewModel.class);

        // Adicionar a opção de voltar para a página inicial na toolbar.
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //Configuração do recycler view
        RecyclerView rvComments = findViewById(R.id.rvComments);
        rvComments.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvComments.setLayoutManager(layoutManager);


        //Configuração do Adapter (parte do recycler view)
        LiveData<List<Comment>> comments = commentsViewModel.getComments();
        //Atualização automática da lista de produtos
        comments.observe(this, new Observer<List<Comment>>() {
            @Override
            public void onChanged(List<Comment> comments1) {
                CommentsAdapter commentsAdapter = new CommentsAdapter(CommentsActivity.this, comments1);
                rvComments.setAdapter(commentsAdapter);
            }
        });
        */


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
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