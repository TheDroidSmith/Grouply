package com.firsttread.grouply.view.fragments;

import android.app.Dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firsttread.grouply.R;

public class AddNameDialog extends DialogFragment {

    private OnCompleteListener mListener;

    @SuppressWarnings("FieldCanBeLocal")
    private final int DIALOG_WIDTH = 1300;
    @SuppressWarnings("FieldCanBeLocal")
    private final int DIALOG_HEIGHT = 1100;

    public static AddNameDialog newInstance(){
        return new AddNameDialog();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE,0);
    }

    @Override
    public void onResume() {
        Window window = getDialog().getWindow();
        window.setLayout(DIALOG_WIDTH, DIALOG_HEIGHT);
        window.setGravity(Gravity.CENTER);
        super.onResume();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.add_name_dialog,container,false);

        final EditText first_txt = (EditText) v.findViewById(R.id.edit_first);
        final EditText last_txt = (EditText) v.findViewById(R.id.edit_last);

        Button done_btn = (Button) v.findViewById(R.id.button_done);
        Button cancel_btn = (Button) v.findViewById(R.id.button_cancel);

        final OnCompleteListener listenerRef = this.mListener;

        done_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = first_txt.getText().toString();
                String lastName = last_txt.getText().toString();

                if(firstName.isEmpty()){
                    Toast.makeText(getContext(),"A first name is required",Toast.LENGTH_LONG).show();
                }else{
                    if(lastName.isEmpty()){
                        listenerRef.onComplete(firstName);
                        dismiss();
                    }else{
                        listenerRef.onComplete(firstName + " " + lastName);
                        dismiss();
                    }
                }
            }
        });

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    //NameListFragment uses this to get the string from this dialog
    public interface OnCompleteListener {
        void onComplete(String name);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            this.mListener = (OnCompleteListener)getActivity().
                                        getSupportFragmentManager().findFragmentById(R.id.list);
        } catch (final ClassCastException e){
            throw new ClassCastException(getActivity().toString() +
                    " must implement OnCompleteListener");
        }
    }

}
