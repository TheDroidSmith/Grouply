package com.firsttread.grouply.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firsttread.grouply.R;

import java.util.ArrayList;


public class NameListAdapter extends RecyclerView.Adapter<NameListAdapter.ViewHolder> {


    private ArrayList<CharSequence> names;

    /*private String[] names = {"Anthony Smith","William O'dell", "Carol Heilman", "Vivyana Triviano",
            "Mike Smith", "Georgie Smith", "Spiderman", "Superman", "Batman", "Anthony Smith","William O'dell",
            "Carol Heilman", "Vivyana Triviano"};*/

    public NameListAdapter(ArrayList<CharSequence> savedNames){
            names = savedNames;
    }

    public NameListAdapter(){
        names = new ArrayList<>();
    }

    public void addNew(String name){
        names.add(name);
    }

    public ArrayList<CharSequence> getNameList(){
        return names;
    }

    @Override
    public void onBindViewHolder(NameListAdapter.ViewHolder holder, int position) {
        holder.getCardText().setText(names.get(position));
    }

    @Override
    public NameListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.name_card,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView cardText;

        public ViewHolder(View itemView) {
            super(itemView);

            //ToDo: going to probably add onTouch slide or hold deletes
            cardText = (TextView) itemView.findViewById(R.id.cardText);
        }

        public TextView getCardText(){
            return cardText;
        }

    }




}
