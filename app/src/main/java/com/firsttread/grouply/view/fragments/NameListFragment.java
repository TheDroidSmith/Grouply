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

package com.firsttread.grouply.view.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firsttread.grouply.presenter.NameListPresenter;
import com.firsttread.grouply.view.SingleGroup;
import com.firsttread.grouply.view.fragments.SingleControlFragment.MyAction;

import com.firsttread.grouply.R;
import com.firsttread.grouply.view.adapters.NameListAdapter;

import java.util.ArrayList;

public class NameListFragment extends Fragment implements AddNameDialog.OnCompleteListener,
        SingleControlFragment.ActionListener, IntNameListFragment {

    private String groupName;// for use in saveGroup

    private ArrayList<CharSequence> savedNames;
    private NameListPresenter listPresenter;

    protected RecyclerView recyclerView;
    protected NameListAdapter adapter;
    protected RecyclerView.LayoutManager layoutManager;

    //provides slide animation for recycler view
    ItemTouchHelper.SimpleCallback touchCallback =
            new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            String deletedName = ((TextView)viewHolder.
                                    itemView.
                                    findViewById(R.id.cardText)).getText().toString();

            adapter.removeFromList(deletedName);
            adapter.notifyDataSetChanged();
        }
    };

    ItemTouchHelper touchHelper = new ItemTouchHelper(touchCallback);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listPresenter = new NameListPresenter();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(savedInstanceState != null){
            savedNames = savedInstanceState.getCharSequenceArrayList("nameList");
            adapter = new NameListAdapter(savedNames);
            recyclerView.setAdapter(adapter);
        }

        /*Restores the list of names if the back button was pressed and the app was closed.
        * It stores a boolean to check if there was anything to save. The boolean
        * is set to false after every check and the temporary data/realm entries are deleted after
        * every query to prevent unwanted saves.*/
        SharedPreferences sharedPref = getActivity()
                .getSharedPreferences(getString(R.string.preference_file_key),Context.MODE_PRIVATE);

        boolean doRestore = sharedPref.getBoolean(getString(R.string.does_save_exist),false);

        if(doRestore){
            adapter.setNames(listPresenter.restoreGroup());
            adapter.notifyDataSetChanged();
            listPresenter.deleteTempGroup();
        }

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(getString(R.string.does_save_exist),false);
        editor.apply();



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

        touchHelper.attachToRecyclerView(recyclerView);


        return rootView;


    }


    //add person dialog button listener here
    @Override
    public void onComplete(String name) {
        adapter.addNew(name);
        adapter.notifyItemInserted(adapter.getItemCount());
    }


    //actions from SingleControl go here
    @Override
    public void retrieveAction(MyAction a) {
        switch(a){
            case SORT_FIRST:
                sortFirstName();
                Toast.makeText(getContext(),"Sorted by Fist Name",Toast.LENGTH_LONG).show();
                break;
            case SORT_LAST:
                sortLastName();
                Toast.makeText(getContext(),"Sorted by Last Name",Toast.LENGTH_LONG).show();
                break;
            case SORT_RANDOM:
                sortRandom();
                Toast.makeText(getContext(),"Randomly Sorted",Toast.LENGTH_LONG).show();
                break;
            case SORT_FLIP:
                sortFlip();
                Toast.makeText(getContext(),"Order Flipped",Toast.LENGTH_LONG).show();
                break;
            case SAVE_LIST: //save group
                makeSaveGroupDialog();
                break;
            case ADD_GROUP:
                makeAddGroupDialog();
                break;
            case CLEAR_LIST:
                clearList();
                break;
            case EMAIL_GROUP:
                makeEmailDialog();
                break;
            default:
                sortFirstName();
                Toast.makeText(getContext(),"Sorted by Fist Name",Toast.LENGTH_LONG).show();
                break;
        }
    }


    //action methods
    @Override
    public void sortLastName(){
        adapter.setNames(listPresenter.sortLastName(adapter.getNameList()));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void sortFirstName(){
        adapter.setNames(listPresenter.sortFirstName(adapter.getNameList()));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void sortRandom(){
        adapter.setNames(listPresenter.sortShuffle(adapter.getNameList()));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void sortFlip(){
        adapter.setNames(listPresenter.sortFlip(adapter.getNameList()));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void clearList(){
        new AlertDialog.Builder(getActivity())
                .setTitle("Clear Current List?")
                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapter.clearNamesList();
                        adapter.notifyDataSetChanged();
                        ((SingleGroup)getActivity()).changeTitle("Grouply");
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    @Override
    public void makeEmailDialog() {

        if(adapter.getNameList().size() <= 0){
            Toast.makeText(getContext(),"Send Email Failed: Empty Group",Toast.LENGTH_LONG).show();
        }else{
            String emailBody = "";
            ArrayList<CharSequence> nameList = adapter.getNameList();

            for(CharSequence name:nameList){
                emailBody += name + "\n";
            }

            Intent emailIntent = new Intent(Intent.ACTION_SENDTO,
                    Uri.fromParts("mailto","",null));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT,"A list made with Grouply");
            emailIntent.putExtra(Intent.EXTRA_TEXT,emailBody);
            startActivity(Intent.createChooser(emailIntent,"Send email..."));
        }

    }

    //saves group to realm
    @Override
    public void makeSaveGroupDialog(){

        //reset to null to prevent empty string
        groupName = null;

        final View v = getActivity().getLayoutInflater().inflate(R.layout.save_group_dialog,null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(v)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        EditText editText = (EditText) v.findViewById(R.id.editGroup);
                        groupName = editText.getText().toString();
                        if(groupName.isEmpty()){
                            Toast.makeText(getContext(),"Save Failed: Group Name Required!",Toast.LENGTH_LONG).show();
                        }else{
                            if(adapter.getNameList().isEmpty()){
                                Toast.makeText(getContext(),"Save Failed: Group Empty!",Toast.LENGTH_LONG).show();
                                ((SingleGroup)getActivity()).changeTitle("Grouply");
                                dialog.dismiss();
                            }else{
                                listPresenter.saveGroup(adapter.getNameList(),groupName);

                                ((SingleGroup)getActivity()).changeTitle("Grouply: " + groupName);
                                Toast.makeText(getContext(),"Group Saved!",Toast.LENGTH_LONG).show();
                            }
                        }
                        dialog.dismiss();

                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {

                        if(event.getAction() == KeyEvent.ACTION_DOWN){

                            switch (keyCode){
                                case KeyEvent.KEYCODE_ENTER:
                                    EditText editText = (EditText) v.findViewById(R.id.editGroup);
                                    groupName = editText.getText().toString();
                                    if(groupName.isEmpty()){
                                        Toast.makeText(getContext(),"Save Failed: Group Name Required!",Toast.LENGTH_LONG).show();
                                    }else{
                                        listPresenter.saveGroup(adapter.getNameList(),groupName);
                                        ((SingleGroup)getActivity()).changeTitle("Grouply: " + groupName);

                                        Toast.makeText(getContext(),"Group Saved!",Toast.LENGTH_LONG).show();
                                    }
                                    dialog.dismiss();
                                default:
                                    break;
                            }
                        }
                        return false;
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        dialog.show();
    }

    //loads group form realm
    @Override
    public void makeAddGroupDialog(){

        CharSequence[] groupList = listPresenter.getGroupList();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle("Select A Group")
                .setItems(groupList, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //setAdapter with the saved list names
                        //and rebuild adapter
                        ListView lv = ((AlertDialog)dialog).getListView();
                        String groupText = lv.getItemAtPosition(which).toString();
                        adapter.setNames(listPresenter.getSavedList(groupText));
                        adapter.notifyDataSetChanged();
                        ((SingleGroup)getActivity()).changeTitle("Grouply: " + groupText);
                        Toast.makeText(getContext(),"Group " + groupText + " added",Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    @Override
    public int saveOnPressBackButton() {
        if(adapter.getNameList().isEmpty()){
            return -1;
        }else{
            listPresenter.saveTempGroup(adapter.getNameList());

            SharedPreferences sharedPref = getActivity()
                    .getSharedPreferences(getString(R.string.preference_file_key),Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean(getString(R.string.does_save_exist),true);
            editor.apply();
            return 1;
        }


    }
}
