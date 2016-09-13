package com.firsttread.grouply.presenter;

import android.content.Context;

import com.firsttread.grouply.BuildConfig;
import com.firsttread.grouply.SingleGroup;
import com.firsttread.grouply.model.Person;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.doCallRealMethod;
import static org.powermock.api.mockito.PowerMockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.internal.RealmCore;
import io.realm.internal.log.RealmLog;

import static org.junit.Assert.*;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
@PowerMockIgnore({"org.mockito.*", "org.robolectric.*", "android.*", "org.powermock.*"})
@SuppressStaticInitializationFor("io.realm.internal.Util")
@PrepareForTest({Realm.class, RealmConfiguration.class, RealmQuery.class, RealmResults.class, RealmCore.class, RealmLog.class})
public class SingleGroupPresenterTest {


    /*This test class is not working. There seems to be a problem with powermock and Realm.*/


    @Rule
    public PowerMockRule rule = new PowerMockRule(); //used so powermock can run in JUnit

    private Context mockContext;

    @Mock
    private Realm mockRealm;
    @Mock
    private RealmConfiguration mockRealmConfig;
    private RealmResults<Person> people;


    @Before
    public void setUp() throws Exception {


        // Setup Realm to be mocked. The order of these matters
        mockStatic(RealmCore.class);
        mockStatic(RealmLog.class);
        mockStatic(Realm.class);
        mockStatic(RealmConfiguration.class);
        mockStatic(RealmConfiguration.Builder.class);
        mockStatic(RealmResults.class);

        // Create mocks
        final Realm mockRealm = mock(Realm.class);
        final RealmConfiguration mockRealmConfig = mock(RealmConfiguration.class);

        /*ToDo: May need to use this instead of mocking RealmConfiguration.Builder.
          ToDo: If so, remove from PrepareForTest and the mockStatic and initialization*/
        doNothing().when(RealmCore.class);
        RealmCore.loadLibrary(any(Context.class));

        // TODO: Mock the RealmConfiguration's constructor. If the RealmConfiguration.Builder.build can be mocked, this
        // is not necessary anymore.
        whenNew(RealmConfiguration.class).withAnyArguments().thenReturn(mockRealmConfig);

        // Anytime getInstance is called with any configuration, then return the mockRealm
        when(Realm.getInstance(any(RealmConfiguration.class))).thenReturn(mockRealm);

        // Anytime we ask Realm to create a Person, return a new instance.
        when(mockRealm.createObject(Person.class)).thenReturn(new Person());

        // Set up some naive stubs
        Person p1 = new Person();
        p1.setGroup("group_1");
        p1.setName("John Young");

        Person p2 = new Person();
        p2.setGroup("group_1");
        p2.setName("John Senior");

        Person p3 = new Person();
        p3.setGroup("group_1");
        p3.setName("Jane");

        Person p4 = new Person();
        p4.setGroup("group_1");
        p4.setName("Robert");

        ArrayList<Person> personList = new ArrayList<>(Arrays.asList(p1, p2, p3, p4));

        RealmQuery<Person> personQuery = mockRealmQuery();

        when(personQuery.findFirst()).thenReturn(personList.get(0));

        when(mockRealm.where(Person.class)).thenReturn(personQuery);

        when(personQuery.equalTo(anyString(), anyInt())).thenReturn(personQuery);



        RealmResults<Person> people = mockRealmResults();

        when(mockRealm.where(Person.class).findAll()).thenReturn(people);

        when(personQuery.between(anyString(), anyInt(), anyInt())).thenReturn(personQuery);

        when(personQuery.beginsWith(anyString(), anyString())).thenReturn(personQuery);

        when(personQuery.findAll()).thenReturn(people);

        when(people.iterator()).thenReturn(personList.iterator());

        when(people.size()).thenReturn(personList.size());

        this.mockRealm = mockRealm;
        this.people = people;

    }


    @Test
    public void shouldBeAbleToAccessActivityAndVerifyRealmInteractions() {

        doCallRealMethod().when(mockRealm).executeTransaction(Mockito.any(Realm.Transaction.class));

        ActivityController<SingleGroup> controller =
                Robolectric.buildActivity(SingleGroup.class).setup();
        SingleGroup activity = controller.get();

        //assertThat(activity.getTitle().toString(), is("Unit Test Example"));

        //verifyStatic(times(2));
        Realm.getInstance(any(RealmConfiguration.class));

        verify(mockRealm, times(4)).executeTransaction(Mockito.any(Realm.Transaction.class));

        controller.destroy();

        //verify(mockRealm, times(2)).close();

    }




    //for a generic query object
    @SuppressWarnings("unchecked")
    private <T extends RealmObject> RealmQuery<T> mockRealmQuery() {
        return mock(RealmQuery.class);
    }

    //for a generic result object
    @SuppressWarnings("unchecked")
    private <T extends RealmObject> RealmResults<T> mockRealmResults() {
        return mock(RealmResults.class);
    }

}