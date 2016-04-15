package com.firsttread.grouply.view;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.firsttread.grouply.R;
import com.firsttread.grouply.model.Group;
import com.firsttread.grouply.model.Person;
import com.firsttread.grouply.view.fragments.AddNameDialog;

import io.realm.Realm;
import io.realm.RealmConfiguration;

//ToDo: add delete this group to menu
//ToDo: restyle add person dialog
//ToDo: make keyboard pop up when add person dialog opens
//ToDo: add clear action to toolbar
//ToDo: import & export
//ToDo: add title of current group to toolbar?

public class SingleGroup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_group);

        RealmConfiguration config = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(config);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    private void showDialog(){

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");

        if(prev != null){
            ft.remove(prev);
        }

        ft.addToBackStack(null);

        DialogFragment newFragment = AddNameDialog.newInstance();

        newFragment.show(ft,"dialog");

    }

    public void clearRealm(){
        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();
        realm.clear(Group.class);
        realm.clear(Person.class);
        realm.commitTransaction();

        realm.close();
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
        }

        return super.onOptionsItemSelected(item);
    }






}
