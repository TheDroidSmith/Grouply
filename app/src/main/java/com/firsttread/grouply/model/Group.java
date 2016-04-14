package com.firsttread.grouply.model;


import io.realm.RealmObject;
import io.realm.annotations.Required;

public class Group extends RealmObject {

    @Required
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
