package com.example.checklist;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.databinding.ObservableList;
import androidx.recyclerview.widget.RecyclerView;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    public interface OnItemClicked {

        public void onClick(Items items);
    }
    OnItemClicked listener;

    private ObservableList<Items> items;
    public ItemsAdapter(ObservableList<Items> items, OnItemClicked listener){
        this.items = items;
        this.listener = listener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_item,parent,false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Items item = items.get(position);
        TextView nameView = holder.itemView.findViewById(R.id.name);
        CardView cardView = holder.itemView.findViewById(R.id.cardView);
        if(item.getchecked()){
            cardView.setCardBackgroundColor(Color.GREEN);
        }
        else {
            cardView.setCardBackgroundColor(Color.WHITE);
        }

        nameView.setText(item.getName());
        holder.itemView.setOnClickListener(view -> {
            //item.setChecked(true);
            if(item.getchecked()){
                item.setChecked(false);
            }
            else{
                item.setChecked(true);
            }

            if(item.getchecked()){
                cardView.setCardBackgroundColor(Color.GREEN);
            }
            else {
                cardView.setCardBackgroundColor(Color.WHITE);
            }
            listener.onClick(items.get(position));
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
