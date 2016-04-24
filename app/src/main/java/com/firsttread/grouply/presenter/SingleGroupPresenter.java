package com.firsttread.grouply.presenter;

import com.firsttread.grouply.model.DatabaseInteractor;
import com.firsttread.grouply.model.IDatabase;
import com.firsttread.grouply.view.IntSingleView;


public class SingleGroupPresenter implements IntSinglePresenter{

    IntSingleView singleView;
    IDatabase dbInteractor;

    public SingleGroupPresenter(IntSingleView singleView) {
        this.singleView = singleView;
        this.dbInteractor = new DatabaseInteractor();
    }


    @Override
    public void showDialog() {
        singleView.onShowDialog();
    }

    @Override
    public CharSequence[] getGroupList() {
        return dbInteractor.getGroupNames();
    }

    @Override
    public void clearRealm() {
        dbInteractor.clearAll();
    }

    @Override
    public void deleteGroup(String groupName) {
        dbInteractor.removeFromRealm(groupName);
    }

}
