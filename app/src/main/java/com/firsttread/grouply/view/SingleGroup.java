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

package com.firsttread.grouply.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.firsttread.grouply.R;
import com.firsttread.grouply.model.Group;
import com.firsttread.grouply.model.Person;
import com.firsttread.grouply.view.fragments.AddNameDialog;
import com.firsttread.grouply.view.fragments.NameListFragment;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class SingleGroup extends AppCompatActivity implements IntSingleGroup{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_group);

        //sets up a realm and sets is as default
        RealmConfiguration config = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(config);

        SharedPreferences sharedPref =
                getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        String actTitle = sharedPref.getString(getString(R.string.act_title),"Grouply");
        setTitle(actTitle);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        /*to maintain list of names on back press*/

        NameListFragment frag =
                (NameListFragment) getSupportFragmentManager().findFragmentById(R.id.list);

        if(frag.saveOnPressBackButton() > 0){
            SharedPreferences sharedPref =
                    getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(getString(R.string.act_title),getTitle().toString());
            editor.apply();
        }
    }

    @Override
    public void showDialog(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");

        if(prev != null){
            ft.remove(prev);
        }

        ft.addToBackStack(null);
        DialogFragment newFragment = AddNameDialog.newInstance();
        newFragment.show(ft,"dialog");
    }

    @Override
    public void clearRealm(){

        new AlertDialog.Builder(this)
                .setTitle("Delete All Saved Groups?")
                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Realm realm = Realm.getDefaultInstance();

                        realm.beginTransaction();
                        realm.clear(Group.class);
                        realm.clear(Person.class);
                        realm.commitTransaction();
                        realm.close();

                        Toast.makeText(SingleGroup.this,"All Groups Deleted",Toast.LENGTH_LONG).show();
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
    public void deleteGroup(){

        Realm realm = Realm.getDefaultInstance();

        RealmQuery<Group> query = realm.where(Group.class);
        RealmResults<Group> result = query.findAll();
        CharSequence[] groupList = new CharSequence[result.size()];

        for(int i=0;i<result.size();i++){
            groupList[i] = result.get(i).getName();
        }

        new AlertDialog.Builder(this)
                .setTitle("Choose a Group to Delete")
                .setItems(groupList, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Realm realm = Realm.getDefaultInstance();

                        ListView lv = ((AlertDialog)dialog).getListView();
                        String groupName = lv.getItemAtPosition(which).toString();
                        RealmResults<Group> groupResult = realm.where(Group.class)
                                                            .equalTo("name",groupName)
                                                            .findAll();

                        if(!groupResult.isEmpty()){
                            realm.beginTransaction();
                            groupResult.first().removeFromRealm();
                            realm.commitTransaction();

                            RealmResults<Person> personResult = realm.where(Person.class)
                                    .equalTo("group",groupName)
                                    .findAll();

                            int resultSize = personResult.size()-1;
                            realm.beginTransaction();
                            for(int i=resultSize; i>=0; i--){
                                personResult.get(i).removeFromRealm();
                            }
                            realm.commitTransaction();
                        }

                        realm.close();

                        Toast.makeText(SingleGroup.this,"Group Deleted",Toast.LENGTH_LONG).show();
                    }
                }).show();




    }


    //gives ability to change title from fragment
    @Override
    public void changeTitle(String newTitle) {
        setTitle(newTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.single_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id){
            case R.id.add_item:
                showDialog();
                return true;
            case R.id.clear_groups:
                clearRealm();
                return true;
            case R.id.delete_group:
                deleteGroup();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
