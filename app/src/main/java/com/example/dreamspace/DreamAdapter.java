package com.example.dreamspace;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class DreamAdapter extends RecyclerView.Adapter<DreamAdapter.ViewHolder> {

    private List<Dream> dreams;
    private DreamClickListener clickListener;

    public DreamAdapter(List<Dream> dreams, DreamClickListener clickListener) {
        this.dreams = dreams;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dream_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Dream dream = dreams.get(position);
        holder.dreamButton.setText(dream.getTitle() + "\n\n" + dream.getDate());
        holder.dreamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onDreamItemClick(dream);
            }
        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.onDreamDeleteClick(dream);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dreams.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        Button dreamButton;
        Button deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dreamButton = itemView.findViewById(R.id.dreamButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
