package com.firsttread.grouply.view.fragments;

public interface IntNameListFragment {

    void sortLastName();

    void sortFirstName();

    void sortRandom();

    void sortFlip();

    void clearList();

    void makeSaveGroupDialog();

    void makeAddGroupDialog();

    void makeEmailDialog();

    int saveOnPressBackButton();

}
