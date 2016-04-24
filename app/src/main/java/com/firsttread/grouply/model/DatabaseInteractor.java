package com.firsttread.grouply.model;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class DatabaseInteractor implements IDatabase {

    @Override
    public CharSequence[] getGroupNames() {

        Realm realm = Realm.getDefaultInstance();

        RealmQuery<Group> query = realm.where(Group.class);
        RealmResults<Group> result = query.findAll();
        CharSequence[] groupList = new CharSequence[result.size()];

        for (int i = 0; i < result.size(); i++) {
            groupList[i] = result.get(i).getName();
        }

        return groupList;

    }

    @Override
    public void removeFromRealm(String groupName) {

        Realm realm = Realm.getDefaultInstance();

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

    }

    @Override
    public void clearAll() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.clear(Group.class);
        realm.clear(Person.class);
        realm.commitTransaction();
        realm.close();
    }

    @Override
    public void addGroup(final ArrayList<CharSequence> nameList, final String groupName) {

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

    @Override
    public ArrayList<CharSequence> getGroup(String groupName) {
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

    @Override
    public ArrayList<CharSequence> getTempGroup() {

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

    @Override
    public void removeTempGroup() {
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