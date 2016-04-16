package com.firsttread.grouply.view;

import android.content.DialogInterface;
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

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;

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

    private void deleteGroup(){

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
                        String groupText = lv.getItemAtPosition(which).toString();
                        RealmResults<Group> result = realm.where(Group.class)
                                                            .equalTo("name",groupText)
                                                            .findAll();
                        realm.beginTransaction();
                        result.first().removeFromRealm();
                        realm.commitTransaction();
                        Toast.makeText(SingleGroup.this,"Group Deleted",Toast.LENGTH_LONG).show();
                        realm.close();
                    }
                }).show();




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
