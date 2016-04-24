package com.firsttread.grouply.presenter;

import java.util.ArrayList;

public interface IntListPresenter {

    ArrayList<CharSequence> sortLastName(ArrayList<CharSequence> nameList);

    ArrayList<CharSequence> sortFirstName(ArrayList<CharSequence> nameList);

    ArrayList<CharSequence> sortShuffle(ArrayList<CharSequence> nameList);

    ArrayList<CharSequence> sortFlip(ArrayList<CharSequence> nameList);

    void clearList();

}
