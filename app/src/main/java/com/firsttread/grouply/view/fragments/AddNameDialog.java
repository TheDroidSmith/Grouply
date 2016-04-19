package com.firsttread.grouply.view.fragments;

import android.app.Dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firsttread.grouply.R;

//ToDo: dialog fragment screen size

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

        ((InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                .toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        View v = inflater.inflate(R.layout.add_name_dialog,container,false);

        final EditText editText = (EditText) v.findViewById(R.id.edit_first);

        Button done_btn = (Button) v.findViewById(R.id.button_done);
        Button cancel_btn = (Button) v.findViewById(R.id.button_cancel);

        final OnCompleteListener listenerRef = this.mListener;

        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                String newName = editText.getText().toString().trim();

                if(event.getAction() == KeyEvent.ACTION_DOWN){
                    switch (keyCode){

                        case KeyEvent.KEYCODE_NUMPAD_ENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            //keyboard is forced opened and closed.
                            //could not get keyboard to open normally on dialog launch.
                            //it might be due to the onCompleteListener.
                            if(newName.isEmpty()){
                                ((InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                                        .hideSoftInputFromWindow(editText.getWindowToken(), 0);
                                dismiss();
                                Toast.makeText(getContext(),"Add Name Failed: A first name is required",Toast.LENGTH_LONG).show();
                            }else{
                                listenerRef.onComplete(newName);

                                ((InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                                        .hideSoftInputFromWindow(editText.getWindowToken(), 0);
                                dismiss();
                            }
                        default:
                            break;
                    }
                }
                return false;
            }
        });

        done_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = editText.getText().toString().trim();

                //keyboard is forced opened and closed.
                //could not get keyboard to open normally on dialog launch.
                //it might be due to the onCompleteListener.
                if(newName.isEmpty()){
                    ((InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(editText.getWindowToken(), 0);
                    dismiss();
                    Toast.makeText(getContext(),"Add Name Failed: A first name is required",Toast.LENGTH_LONG).show();
                }else{
                    listenerRef.onComplete(newName);

                    ((InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(editText.getWindowToken(), 0);
                    dismiss();
                }
            }
        });
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(editText.getWindowToken(), 0);
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
