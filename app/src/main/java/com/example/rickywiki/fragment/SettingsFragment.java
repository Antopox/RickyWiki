package com.example.rickywiki.fragment;


import android.os.Bundle;
import androidx.preference.PreferenceFragmentCompat;
import com.example.rickywiki.R;
//Cargamos nuestro xml de preferencias
public class SettingsFragment extends PreferenceFragmentCompat{

    public static final String TAG = "SettingsFragment";

    @Override
    public void onCreatePreferences(Bundle saveInstance, String rootKey){
        setPreferencesFromResource(R.xml.settings, rootKey);
    }
}
