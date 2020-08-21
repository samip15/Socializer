package com.example.socializer.following;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.example.socializer.R;

public class FollowingPreferenceFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.following_preferences, rootKey);
    }
}