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

package com.firsttread.grouply.presenter;

import com.firsttread.grouply.model.DatabaseInteractor;
import com.firsttread.grouply.model.Group;
import com.firsttread.grouply.model.IDatabase;
import com.firsttread.grouply.model.Person;
import com.firsttread.grouply.view.IntNameListFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class NameListPresenter implements IntListPresenter{

    private IDatabase dbInteractor;
    private IntNameListFragment iNameFrag;

    public NameListPresenter(IntNameListFragment iNameFrag){
        this.iNameFrag = iNameFrag;
        this.dbInteractor = new DatabaseInteractor();
    }



    //sorting action methods
    @Override
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

    @Override
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

    @Override
    public ArrayList<CharSequence> sortShuffle(ArrayList<CharSequence> nameList){
        Collections.shuffle(nameList);
        return nameList;
    }

    @Override
    public ArrayList<CharSequence> sortFlip(ArrayList<CharSequence> nameList){
        Collections.reverse(nameList);
        return nameList;
    }

    @Override
    public void clearList() {
        iNameFrag.onClearList();
    }



    @Override
    /*save and load methods*/
    public void saveGroup(final ArrayList<CharSequence> nameList,final String groupName){
        dbInteractor.addGroup(nameList,groupName);
    }

    @Override
    //uses CharSequence array because the dialog builder requires an array
    public CharSequence[] getGroupList(){
        return dbInteractor.getGroupNames();
    }

    @Override
    public ArrayList<CharSequence> getSavedGroup(String groupName){
        return dbInteractor.getGroup(groupName);
    }



    @Override
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

    @Override
    public ArrayList<CharSequence> restoreGroup(){
        /*fetches the temporary group list that was saved when the back button is pressed
        * and the app closes.*/
        return dbInteractor.getTempGroup();
    }

    @Override
    public void deleteTempGroup(){
        //called to ensure the temporary recovery group is erased to prevent unwanted saves
        dbInteractor.removeTempGroup();
    }

}
