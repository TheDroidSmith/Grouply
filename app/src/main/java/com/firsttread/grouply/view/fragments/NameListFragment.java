package com.firsttread.grouply.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firsttread.grouply.R;
import com.firsttread.grouply.view.adapters.NameListAdapter;


public class NameListFragment extends Fragment {

    protected RecyclerView recyclerView;
    protected NameListAdapter adapter;
    protected RecyclerView.LayoutManager layoutManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //probably get the data from realm

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.name_list_fragment,container,false);
        rootView.setTag("RecyclerViewFragment");

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);

        adapter = new NameListAdapter();
        recyclerView.setAdapter(adapter);


        return rootView;


    }

    @Override
    public void onPause() {
        super.onPause();
    }



}
