/*
    Copyright (c) 2016 Anthony Smith

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package com.firsttread.grouply.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firsttread.grouply.R;

import java.util.ArrayList;


public class NameListAdapter extends RecyclerView.Adapter<NameListAdapter.ViewHolder> {


    private ArrayList<CharSequence> names;


    public NameListAdapter(ArrayList<CharSequence> savedNames){
            names = savedNames;
    }

    public NameListAdapter(){
        names = new ArrayList<>();
    }


    public void addNew(String name){
        names.add(name);
        notifyItemInserted(getItemCount());
    }

    public ArrayList<CharSequence> getNameList(){
        return names;
    }

    public void removeFromList(CharSequence deletedName){
        names.remove(deletedName);
        notifyDataSetChanged();
    }

    public void setNames(ArrayList<CharSequence> names) {
        this.names = names;
        notifyDataSetChanged();
    }

    public void clearNamesList(){
        names.clear();
        notifyDataSetChanged();
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

        private TextView cardText;

        public ViewHolder(View card) {
            super(card);
            cardText = (TextView) itemView.findViewById(R.id.cardText);
        }

        public TextView getCardText(){
            return cardText;
        }

    }


}
