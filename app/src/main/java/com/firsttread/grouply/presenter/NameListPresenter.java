package com.firsttread.grouply.presenter;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class NameListPresenter {

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

    public ArrayList<CharSequence> sortShuffle(ArrayList<CharSequence> nameList){
        Collections.shuffle(nameList);
        return nameList;
    }

    public ArrayList<CharSequence> sortFlip(ArrayList<CharSequence> nameList){
        Collections.reverse(nameList);
        return nameList;
    }


}
