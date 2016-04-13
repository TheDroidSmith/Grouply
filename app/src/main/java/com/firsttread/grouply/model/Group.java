package com.firsttread.grouply.model;

import io.realm.RealmObject;
import io.realm.annotations.Required;


public class Group extends RealmObject {

    @Required
    String groupName;

    @Required
    NameList listName;


    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public NameList getListName() {
        return listName;
    }

    public void setListName(NameList listName) {
        this.listName = listName;
    }
}
