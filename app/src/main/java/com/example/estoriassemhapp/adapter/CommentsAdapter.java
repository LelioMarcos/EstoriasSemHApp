package com.example.estoriassemhapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.estoriassemhapp.R;
import com.example.estoriassemhapp.activity.CommentsActivity;
import com.example.estoriassemhapp.model.Comment;
import com.example.estoriassemhapp.model.Story;
import com.example.estoriassemhapp.model.Tag;
import com.example.estoriassemhapp.util.ImageCache;

import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter {

    Context context;
    List<Comment> comments;

    public CommentsAdapter(Context context, List<Comment> comments) {
        this.context = context;
        this.comments = comments;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.list_comment, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Comment comment = this.comments.get(position);

        TextView tvBio = holder.itemView.findViewById(R.id.tvBio);
        tvBio.setText(comment.getComentario());

        TextView tvUser = holder.itemView.findViewById(R.id.tvUsername);
        tvUser.setText(comment.getNomusu());

        ImageView imvProfilePhoto = holder.itemView.findViewById(R.id.imvProfilePhoto);
        ImageCache.loadToImageView((Activity) context, comment.getUsucom(), imvProfilePhoto);
    }

    @Override
    public int getItemCount() {
        return this.comments.size();
    }
}
