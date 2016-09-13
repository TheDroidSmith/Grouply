package com.firsttread.grouply.presenter;

import com.firsttread.grouply.NameListFragment;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import junit.framework.Assert;

import java.util.ArrayList;

public class NameListPresenterTest {

    private IntListPresenter presenter;

    @Mock
    NameListFragment mockedNamedListFragment;
    private ArrayList<CharSequence> notOrderedNames;
    @Before
    public void setUp() throws Exception {
        presenter = new NameListPresenter(mockedNamedListFragment);

        notOrderedNames = new ArrayList<CharSequence>();
        notOrderedNames.add("Peregrin Took");
        notOrderedNames.add("Samwise Gamgee");
        notOrderedNames.add("Frodo Baggins");
    }

    @Test
    public void testSortFirstNameForAlphabeticalFirstNameOrder() throws Exception {

        ArrayList<CharSequence> CorrectFirstNameOrder = new ArrayList<CharSequence>();
        CorrectFirstNameOrder.add("Frodo Baggins");
        CorrectFirstNameOrder.add("Peregrin Took");
        CorrectFirstNameOrder.add("Samwise Gamgee");

        ArrayList<CharSequence> result = presenter.sortFirstName(notOrderedNames);

       Assert.assertEquals(result,CorrectFirstNameOrder);

    }

    @Test
    public void testSortLastNameForAlphabeticalLastNameOrder() throws Exception {
        ArrayList<CharSequence> CorrectLastNameOrder = new ArrayList<CharSequence>();
        CorrectLastNameOrder.add("Frodo Baggins");
        CorrectLastNameOrder.add("Samwise Gamgee");
        CorrectLastNameOrder.add("Peregrin Took");


        ArrayList<CharSequence> result = presenter.sortLastName(notOrderedNames);

        Assert.assertEquals(result,CorrectLastNameOrder);
    }

    @Test
    public void testSortFlipForReverseOfCurrentOrder() throws Exception {
        ArrayList<CharSequence> nameOrder = new ArrayList<CharSequence>();
        nameOrder.add("Frodo Baggins");
        nameOrder.add("Samwise Gamgee");
        nameOrder.add("Peregrin Took");


        ArrayList<CharSequence> result = presenter.sortLastName(notOrderedNames);

        Assert.assertEquals(result,nameOrder);
    }
}