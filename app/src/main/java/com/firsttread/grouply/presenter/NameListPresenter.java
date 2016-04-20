package com.firsttread.grouply.presenter;

import com.firsttread.grouply.model.Group;
import com.firsttread.grouply.model.Person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class NameListPresenter {

    public NameListPresenter(){

    }


    //sorting action methods
    public ArrayList<CharSequence> sortFirstName(ArrayList<CharSequence> nameList){
        Collections.sort(nameList, new Comparator<CharSequence>() {
            @Override
            public int compare(CharSequence cs1, CharSequence cs2) {
                return cs1.toString()
                        .toLowerCase()
                        .compareTo(cs2.toString().toLowerCase());
            }
        });
        return nameList;
    }

    public ArrayList<CharSequence> sortLastName(ArrayList<CharSequence> nameList){

        Collections.sort(nameList, new Comparator<CharSequence>() {
            @Override
            public int compare(CharSequence cs1, CharSequence cs2) {

                //to sort the last name: find substring starting at space+1 and sort there
                int spaceIndex_1 = cs1.toString().indexOf(" ") + 1;
                int spaceIndex_2 = cs2.toString().indexOf(" ") + 1;

                return cs1.toString()
                        .substring(spaceIndex_1)
                        .toLowerCase()
                        .compareTo(cs2.toString().substring(spaceIndex_2).toLowerCase());
            }
        });
        return nameList;
    }

    public ArrayList<CharSequence> sortShuffle(ArrayList<CharSequence> nameList){
        Collections.shuffle(nameList);
        return nameList;
    }

    public ArrayList<CharSequence> sortFlip(ArrayList<CharSequence> nameList){
        Collections.reverse(nameList);
        return nameList;
    }


    //save and load methods
    public void saveGroup(final ArrayList<CharSequence> nameList,final String groupName){

        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();
        Group groupObject = realm.createObject(Group.class);
        groupObject.setName(groupName);
        realm.commitTransaction();

        realm.beginTransaction();
        for(CharSequence name:nameList){
            Person personObject = realm.createObject(Person.class);
            personObject.setName(name.toString());
            personObject.setGroup(groupName);
        }

        realm.commitTransaction();
        realm.close();

    }

    public CharSequence[] getGroupList(){

        //uses CharSequence array because the dialog builder requires an array
        Realm realm = Realm.getDefaultInstance();

        RealmQuery<Group> query = realm.where(Group.class);

        RealmResults<Group> result = query.findAll();

        CharSequence[] groupList = new CharSequence[result.size()];

        for(int i=0;i<result.size();i++){
            groupList[i] = result.get(i).getName();
        }

        realm.close();

        return groupList;
    }

    public ArrayList<CharSequence> getSavedList(String groupName){

        Realm realm = Realm.getDefaultInstance();

        RealmResults<Person> people = realm.where(Person.class)
                .equalTo("group",groupName)
                .findAll();

        ArrayList<CharSequence> result = new ArrayList<>();

        for(Person person:people){
            result.add(person.getName());
        }

        realm.close();

        return result;
    }


    /*Recovery methods for state of group list if the back button was pressed
     *and the app was closed*/
    public void saveTempGroup(ArrayList<CharSequence> nameList){
        /*Method to save a temp group list of names so data isn't lost if the back button
         * is pressed and the app closes.*/
        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();
        Group groupObject = realm.createObject(Group.class);
        groupObject.setName("temp");
        realm.commitTransaction();

        realm.beginTransaction();
        for(CharSequence name:nameList){
            Person personObject = realm.createObject(Person.class);
            personObject.setName(name.toString());
            personObject.setGroup("temp");
        }
        realm.commitTransaction();

        realm.close();
    }

    public ArrayList<CharSequence> restoreGroup(){
        /*fetches the temporary group list that was saved when the back button is pressed
        * and the app closes.*/
        Realm realm = Realm.getDefaultInstance();

        RealmResults<Person> people = realm.where(Person.class)
                .equalTo("group","temp")
                .findAll();

        ArrayList<CharSequence> result = new ArrayList<>();

        for(Person person:people){
            result.add(person.getName());
        }

        realm.close();

        return result;
    }

    public void deleteTempGroup(){
        /*is called to ensure the temporary recovery group is erased to prevent unwanted storage*/
        Realm realm = Realm.getDefaultInstance();

        RealmResults<Group> groupResult = realm.where(Group.class)
                .equalTo("name","temp")
                .findAll();

        if(!groupResult.isEmpty()){
            realm.beginTransaction();
            groupResult.first().removeFromRealm();
            realm.commitTransaction();

            RealmResults<Person> personResult = realm.where(Person.class)
                    .equalTo("group","temp")
                    .findAll();

            int resultSize = personResult.size()-1;
            realm.beginTransaction();
            for(int i=resultSize; i>=0; i--){
                personResult.get(i).removeFromRealm();
            }
            realm.commitTransaction();
        }
        realm.close();
    }













}
