package com.firsttread.grouply.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firsttread.grouply.presenter.NameListPresenter;
import com.firsttread.grouply.view.fragments.SingleControlFragment.MyAction;

import com.firsttread.grouply.R;
import com.firsttread.grouply.view.adapters.NameListAdapter;

import java.util.ArrayList;


public class NameListFragment extends Fragment implements AddNameDialog.OnCompleteListener,
        SingleControlFragment.ActionListener {

    private ArrayList<CharSequence> savedNames;
    private NameListPresenter listPresenter;

    protected RecyclerView recyclerView;
    protected NameListAdapter adapter;
    protected RecyclerView.LayoutManager layoutManager;

    private final String KEY_RECYCLER_STATE = "recycler_state";

    protected Bundle recyclerViewBundle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listPresenter = new NameListPresenter();
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

    @Override
    public void retrieveAction(MyAction a) {
        switch(a){
            case SORT_FIRST:
                Toast.makeText(getContext(),"Sorted by Fist Name",Toast.LENGTH_LONG).show();
                sortFirstName();
                break;
            case SORT_LAST:
                Toast.makeText(getContext(),"action received2",Toast.LENGTH_LONG).show();
                sortLastName();
                break;
            case SORT_RANDOM:
                Toast.makeText(getContext(),"Randomly Sorted",Toast.LENGTH_LONG).show();
                sortRandom();
                break;
            case SORT_FLIP:
                Toast.makeText(getContext(),"Order Flipped",Toast.LENGTH_LONG).show();
                sortFlip();
                break;
            case SAVE_LIST:
                Toast.makeText(getContext(),"action received5",Toast.LENGTH_LONG).show();
                break;
            case PRINT_LIST:
                Toast.makeText(getContext(),"action received6",Toast.LENGTH_LONG).show();
                break;
            default:
                //same as sort first
                Toast.makeText(getContext(),"Sorted by Fist Name",Toast.LENGTH_LONG).show();
                sortFirstName();
                break;

        }
    }

    public void sortLastName(){
        adapter.setNames(listPresenter.sortLastName(adapter.getNameList()));
        adapter.notifyDataSetChanged();
    }

    private void sortFirstName(){
        adapter.setNames(listPresenter.sortFirstName(adapter.getNameList()));
        adapter.notifyDataSetChanged();
    }

    private void sortRandom(){
        adapter.setNames(listPresenter.sortShuffle(adapter.getNameList()));
        adapter.notifyDataSetChanged();
    }

    private void sortFlip(){
        adapter.setNames(listPresenter.sortFlip(adapter.getNameList()));
        adapter.notifyDataSetChanged();
    }

}
