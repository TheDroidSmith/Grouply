package com.firsttread.grouply;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.github.angads25.filepicker.view.FilePickerPreference;


public class SettingsActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener {


    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_general);
        FilePickerPreference fileDialog = (FilePickerPreference)findPreference("directories");
        fileDialog.setOnPreferenceChangeListener(this);

    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Toolbar toolbar;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH){
            LinearLayout root = (LinearLayout)findViewById(android.R.id.list).getParent().getParent().getParent();
            toolbar = (Toolbar) LayoutInflater.from(this).inflate(R.layout.toolbar, root, false);
            root.addView(toolbar, 0);
        } else{
            ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
            ListView content = (ListView) root.getChildAt(0);
            root.removeAllViews();
            toolbar = (Toolbar) LayoutInflater.from(this).inflate(R.layout.toolbar, root, false);
            int height;
            TypedValue tv = new TypedValue();
            if(getTheme().resolveAttribute(R.attr.actionBarSize, tv, true)){
                height = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
            } else{
                height = toolbar.getHeight();
            }
            content.setPadding(0,height,0,0);
            root.addView(content);
            root.addView(toolbar);
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary,getTheme()));
        } else{
            toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }
        toolbar.setNavigationIcon(R.drawable.ic_up_navigation);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle(getResources().getString(R.string.title_activity_settings));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object o) {

        if(preference.getKey().equals("directories")){
            String value = (String)o;
            String arr[] = value.split(":");
            for(String path:arr){
                Toast.makeText(SettingsActivity.this,path,Toast.LENGTH_SHORT).show();
            }
        }

        return false;
    }




}















