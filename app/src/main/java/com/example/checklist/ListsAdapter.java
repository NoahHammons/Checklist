package com.example.checklist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableList;
import androidx.recyclerview.widget.RecyclerView;

public class ListsAdapter extends RecyclerView.Adapter<ListsAdapter.ViewHolder> {

    public interface OnListClicked {
        public void onClick(Lists list);
    }
    OnListClicked listener;

    private ObservableList<Lists> lists;
    public ListsAdapter(ObservableList<Lists> Lists, OnListClicked listener){
        this.lists = Lists;
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
        Lists list = lists.get(position);
        TextView nameView = holder.itemView.findViewById(R.id.name);

        nameView.setText(list.getName());
        holder.itemView.setOnClickListener(view -> {
            listener.onClick(lists.get(position));
        });

    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
