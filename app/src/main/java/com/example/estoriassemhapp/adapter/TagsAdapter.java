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
import com.example.estoriassemhapp.activity.SearchActivity;
import com.example.estoriassemhapp.activity.StoryActivity;
import com.example.estoriassemhapp.model.Story;
import com.example.estoriassemhapp.model.Tag;

import java.util.List;

public class TagsAdapter extends RecyclerView.Adapter {

    Context context;
    List<Tag> tags;

    public TagsAdapter(Context context, List<Tag> tags) {
        this.context = context;
        this.tags = tags;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.list_gender, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Tag tag = this.tags.get(position);

        TextView tvGnder = holder.itemView.findViewById(R.id.tvGnder);
        tvGnder.setText(tag.getGenero());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, SearchActivity.class);
                i.putExtra("genero", tag.getGenero());
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.tags.size();
    }
}
