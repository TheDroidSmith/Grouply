package com.firsttread.grouply.presenter;

import java.util.ArrayList;

public interface IntListPresenter {

    ArrayList<CharSequence> sortLastName(ArrayList<CharSequence> nameList);

    ArrayList<CharSequence> sortFirstName(ArrayList<CharSequence> nameList);

    ArrayList<CharSequence> sortShuffle(ArrayList<CharSequence> nameList);

    ArrayList<CharSequence> sortFlip(ArrayList<CharSequence> nameList);

    void clearList();

    ArrayList<CharSequence> restoreGroup();

    void saveGroup(final ArrayList<CharSequence> nameList,final String groupName);

    ArrayList<CharSequence> getSavedGroup(String groupName);

    CharSequence[] getGroupList();

    void saveTempGroup(ArrayList<CharSequence> nameList);

    void deleteTempGroup();
}
