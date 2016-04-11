package com.firsttread.grouply.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firsttread.grouply.R;
import com.firsttread.grouply.view.adapters.NameListAdapter;

import java.util.ArrayList;


public class NameListFragment extends Fragment implements AddNameDialog.OnCompleteListener {

    private ArrayList<CharSequence> savedNames;

    protected RecyclerView recyclerView;
    protected NameListAdapter adapter;
    protected RecyclerView.LayoutManager layoutManager;

    private final String KEY_RECYCLER_STATE = "recycler_state";

    protected Bundle recyclerViewBundle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //probably get the data from realm

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(savedInstanceState != null){

            savedNames = savedInstanceState.getCharSequenceArrayList("nameList");
            adapter = new NameListAdapter(savedNames);
            recyclerView.setAdapter(adapter);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        savedNames = adapter.getNameList();
        outState.putCharSequenceArrayList("nameList", savedNames);
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



    @Override
    public void onComplete(String name) {
        adapter.addNew(name);
        adapter.notifyItemInserted(adapter.getItemCount());
    }
}
