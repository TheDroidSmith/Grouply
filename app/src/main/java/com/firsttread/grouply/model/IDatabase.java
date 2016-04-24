package com.firsttread.grouply.model;

import java.util.ArrayList;

public interface IDatabase {

    CharSequence[] getGroupNames();

    void removeFromRealm(String groupName);

    void clearAll();

    void addGroup(final ArrayList<CharSequence> nameList, final String groupName);

    ArrayList<CharSequence> getGroup(String groupName);

    ArrayList<CharSequence> getTempGroup();

    void removeTempGroup();
}
