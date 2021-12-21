package com.example.estoriassemhapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.estoriassemhapp.R;
import com.example.estoriassemhapp.activity.StoryActivity;
import com.example.estoriassemhapp.fragment.MainFragment;
import com.example.estoriassemhapp.model.Story;

import java.util.List;

public class StoryAdapter extends RecyclerView.Adapter {

    Context context;
    List<Story> stories;

    public StoryAdapter(Context context, List<Story> stories) {
        this.context = context;
        this.stories = stories;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.list_story, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Story story = this.stories.get(position);

        TextView tvNameList = holder.itemView.findViewById(R.id.tvUsername);
        tvNameList.setText(story.getTitle());

        TextView tvSinopseList = holder.itemView.findViewById(R.id.tvBio);
        tvSinopseList.setText(story.getSinopse());

        TextView tvClassificacao = holder.itemView.findViewById(R.id.tvAge);
        tvClassificacao.setText(story.getIndicacao());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, StoryActivity.class);
                i.putExtra("idhist", story.getId());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.stories.size();
    }
}
