package com.firsttread.grouply.model;

import io.realm.RealmObject;
import io.realm.annotations.Required;

public class Person extends RealmObject {

    @Required
    private String name;

    @Required
    private String group;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
