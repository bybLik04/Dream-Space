package com.example.dreamspace;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HandbookAdapter extends RecyclerView.Adapter<HandbookAdapter.ViewHolder> {

    private List<TextData> textDataList;
    private Context context;

    public HandbookAdapter(List<TextData> textDataList, Context context) {
        this.textDataList = textDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_handbook, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TextData textData = textDataList.get(position);
        holder.handbookTitle.setText(textData.getTitle());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, HandbookActivity.class);
            intent.putExtra("content", textData.getContent());
            intent.putExtra("title",textData.getTitle());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return textDataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView handbookTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            handbookTitle = itemView.findViewById(R.id.handbookTitle);
        }
    }
}
