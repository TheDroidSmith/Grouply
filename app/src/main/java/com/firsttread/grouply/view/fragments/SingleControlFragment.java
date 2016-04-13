package com.firsttread.grouply.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firsttread.grouply.R;


public class SingleControlFragment extends Fragment {

    private ActionListener actionListener;

    public enum MyAction{
        SORT_FIRST,
        SORT_LAST,
        SORT_RANDOM,
        SORT_FLIP,
        SAVE_LIST,
        ADD_GROUP
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.single_control_fragment,container,false);



        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CardView sort_first_btn = (CardView) getView().findViewById(R.id.sort_first);
        CardView sort_last_btn = (CardView) getView().findViewById(R.id.sort_last);
        CardView sort_random_btn = (CardView) getView().findViewById(R.id.sort_random);
        CardView sort_flip_btn = (CardView) getView().findViewById(R.id.sort_flip);
        CardView sort_save_btn = (CardView) getView().findViewById(R.id.save);
        CardView sort_print_btn = (CardView) getView().findViewById(R.id.print);

        sort_first_btn.setOnClickListener(sortByFirst);
        sort_last_btn.setOnClickListener(sortByLast);
        sort_random_btn.setOnClickListener(sortRandom);
        sort_flip_btn.setOnClickListener(sortFlip);
        sort_save_btn.setOnClickListener(saveList);
        sort_print_btn.setOnClickListener(addGroup);

    }

    @Override
    public void onPause() {
        super.onPause();
    }


    //interface for NameListFragment to listen for button clicks and their acctions
    public interface ActionListener {
        void retrieveAction(MyAction a);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            this.actionListener = (ActionListener) getActivity()
                    .getSupportFragmentManager()
                    .findFragmentById(R.id.list);
        }catch (final ClassCastException e){
            throw new ClassCastException(getActivity().toString() +
                    " must implement ActionListener");
        }
    }


    private View.OnClickListener sortByFirst = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            actionListener.retrieveAction(MyAction.SORT_FIRST);
        }
    };

    private View.OnClickListener sortByLast = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            actionListener.retrieveAction(MyAction.SORT_LAST);
        }
    };

    private View.OnClickListener sortRandom = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            actionListener.retrieveAction(MyAction.SORT_RANDOM);
        }
    };

    private View.OnClickListener sortFlip = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            actionListener.retrieveAction(MyAction.SORT_FLIP);
        }
    };

    private View.OnClickListener saveList = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            actionListener.retrieveAction(MyAction.SAVE_LIST);
        }
    };

    private View.OnClickListener addGroup = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            actionListener.retrieveAction(MyAction.ADD_GROUP);
        }
    };





























}
