package ir.ac.sku.service.digiservice.util;

import android.content.Context;
import android.content.SharedPreferences;

import ir.ac.sku.service.digiservice.config.PreferenceName;

public class UserProfilePreferenceManager {

    private Context context;
    private SharedPreferences preferences;

    public UserProfilePreferenceManager(Context context) {
        this.context = context;
        this.preferences = context.getSharedPreferences(PreferenceName.USER_PROFILE_PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public boolean startApplication() {
        return preferences.getBoolean(PreferenceName.USER_PROFILE_IS_LOGIN, true);
    }

    public void setStartApplication(boolean starter) {
        preferences
                .edit()
                .putBoolean(PreferenceName.USER_PROFILE_IS_LOGIN, starter)
                .apply();
    }
}
