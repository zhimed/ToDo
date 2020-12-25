package com.codepath.zhimed.todo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

//Responsible for displaying data from the model into a row into the recycler view
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder>{

    public interface OnClickListener{
        void onItemClicked(int position);
    }


    public interface OnLongClickListener{
        void onItemLongClicked(int position);
    }

    List<String> items;
    OnLongClickListener longClickListener;
    OnClickListener clickListener;

    public ItemsAdapter(List<String> items, OnLongClickListener longClickListener,OnClickListener clickListener) {
        this.items=items;
        this.longClickListener=longClickListener;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //use layout inflator to inflate a view
        View todoView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        //wrap it inside a view holder and return it
        return new ViewHolder(todoView);
    }

    //responsible for binding data to a particular view holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Grab the item at the position
        String item =items.get(position);
        //Bind the item into the specified view holder
        holder.bind(item);

    }

    //Tell the recycler view how many items are in the list
    @Override
    public int getItemCount() {

        return items.size();
    }
    //This is a container to provide easy access to views that represent each row of the list

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvitem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvitem=itemView.findViewById(android.R.id.text1);
        }

        //Updates the view inside of the view holder with this data
        public void bind(String item) {

            tvitem.setText(item);
            tvitem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onItemClicked(getAdapterPosition());

                }
            });
            tvitem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //Notifies the listener which position was long pressed
                    longClickListener.onItemLongClicked(getAdapterPosition());
                    return true;
                }
            });
            ;
        }
    }
}
