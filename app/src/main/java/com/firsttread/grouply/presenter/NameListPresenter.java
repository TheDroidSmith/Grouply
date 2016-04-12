package com.firsttread.grouply.presenter;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class NameListPresenter {

    public ArrayList<CharSequence> sortFirstName(ArrayList<CharSequence> nameList){
        Collections.sort(nameList, new Comparator<CharSequence>() {
            @Override
            public int compare(CharSequence cs1, CharSequence cs2) {
                return cs1.toString().compareTo(cs2.toString());
            }
        });
        return nameList;
    }


    public ArrayList<CharSequence> sortLastName(ArrayList<CharSequence> nameList){
        //ToDo: implement quicksort

        return nameList;
    }



    public ArrayList<CharSequence> sortRandom(ArrayList<CharSequence> nameList){
        Collections.shuffle(nameList);
        return nameList;
    }

    public ArrayList<CharSequence> sortFlip(ArrayList<CharSequence> nameList){
        Collections.reverse(nameList);
        return nameList;
    }

}
