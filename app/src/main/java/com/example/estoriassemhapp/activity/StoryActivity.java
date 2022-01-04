package com.example.estoriassemhapp.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.estoriassemhapp.R;
import com.example.estoriassemhapp.adapter.StoryAdapter;
import com.example.estoriassemhapp.model.MainViewModel;
import com.example.estoriassemhapp.model.Story;
import com.example.estoriassemhapp.model.StoryViewModel;

import java.util.List;

public class StoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        ProgressBar pbStory = findViewById(R.id.pbStory);
        pbStory.setVisibility(View.VISIBLE);

        Toolbar toolbar = findViewById(R.id.tbMain);
        setSupportActionBar(toolbar);

        // Adicionar a opção de voltar para a página inicial na toolbar.
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Button btnComments = findViewById(R.id.btnComments);

        Intent i = getIntent();
        String id = i.getStringExtra("idhist");

        System.out.println(id);

        StoryViewModel storyViewModel = new ViewModelProvider(this, new StoryViewModel.StoryViewModelFactory(id)).get(StoryViewModel.class);
        LiveData<Story> story = storyViewModel.getStory();

        story.observe(this, new Observer<Story>() {
            @Override
            public void onChanged(Story story) {
                pbStory.setVisibility(View.GONE);

                TextView tvStory = findViewById(R.id.tvStory);
                tvStory.setText(story.getText());

                tvStory.setMovementMethod(new ScrollingMovementMethod());

                TextView tvTitle = findViewById(R.id.tvTitulo);
                tvTitle.setText(story.getTitle());

                TextView tvWriter = findViewById(R.id.tvAutor);
                tvWriter.setText("Autor: " + story.getAutor());

            }
        });

        btnComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(StoryActivity.this, CommentsActivity.class);
                i.putExtra("idhist", id);
                startActivity(i);
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