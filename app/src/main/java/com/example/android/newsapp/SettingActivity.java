package com.example.android.newsapp;

import android.content.SharedPreferences;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }
    public static class NewsPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener{

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.setting_main);
            Preference sections = findPreference(getString(R.string.section_key));
            bindPreferenceSummaryToValue(sections);
            Preference orderBy = findPreference(getString(R.string.order_by_key));
            bindPreferenceSummaryToValue(orderBy);

        }

        private void bindPreferenceSummaryToValue(Preference sections) {
            sections.setOnPreferenceChangeListener(this);
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(sections.getContext());
            String preferenceString = preferences.getString(sections.getKey(),"");
            onPreferenceChange(sections, preferenceString);

        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String stringValue = newValue.toString();
            preference.setSummary(stringValue);
            if (preference instanceof ListPreference){
                ListPreference listPreference = (ListPreference)preference;
                int prefIndex = listPreference.findIndexOfValue(stringValue);
                if (prefIndex>=0){
                    CharSequence[] labels = listPreference.getEntries();
                    preference.setSummary(labels[prefIndex]);
                }
            }else {
                preference.setSummary(stringValue);
            }
            return true;
        }
    }
}
