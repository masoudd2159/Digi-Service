package ir.ac.sku.service.digiservice.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class SharedPreferencesUtils {

    private static final String PREFERENCE_USER_INFORMATION = "UserInformation";

    private static final String PREF_FIRST_NAME = "FirstName";
    private static final String PREF_LAST_NAME = "LastName";
    private static final String PREF_GENDER = "Gender";
    private static final String PREF_PERSONAL_ID = "PersonalId";
    private static final String PREF_PASSWORD = "Password";
    private static final String PREF_MOBILE_NUMBER = "MobileNumber";
    private static final String PREF_EMAIL_ADDRESS = "EmailAddress";
    private static final String PREF_ACCOUNT_TYPE = "AccountType";
    private static final String PREF_EDUCATION = "Education";
    private static final String PREF_PROFILE_PICTURE = "ProfilePicture";
    private static final String PREF_START_KEY = "StartKey";


    private final SharedPreferences preferencesUserInformation;
    private final SharedPreferences.Editor editSharedPreferences;

    @SuppressLint("CommitPrefEdits")
    public SharedPreferencesUtils(Context context) {
        preferencesUserInformation = context.getSharedPreferences(PREFERENCE_USER_INFORMATION, Context.MODE_PRIVATE);
        editSharedPreferences = preferencesUserInformation.edit();
    }

    public String getFirstName() {
        return preferencesUserInformation.getString(PREF_FIRST_NAME, null);
    }

    public void setFirstName(String firstName) {
        editSharedPreferences.putString(PREF_FIRST_NAME, firstName.trim()).apply();
    }

    public String getLastName() {
        return preferencesUserInformation.getString(PREF_LAST_NAME, null);
    }

    public void setLastName(String lastName) {
        editSharedPreferences.putString(PREF_LAST_NAME, lastName.trim()).apply();
    }

    public String getGender() {
        return preferencesUserInformation.getString(PREF_GENDER, null);
    }

    public void setGender(String gender) {
        editSharedPreferences.putString(PREF_GENDER, gender.trim()).apply();
    }

    public String getPersonalId() {
        return preferencesUserInformation.getString(PREF_PERSONAL_ID, null);
    }

    public void setPersonalId(String personalId) {
        editSharedPreferences.putString(PREF_PERSONAL_ID, personalId.trim()).apply();
    }

    public String getPassword() {
        return preferencesUserInformation.getString(PREF_PASSWORD, null);
    }

    public void setPassword(String password) {
        editSharedPreferences.putString(PREF_PASSWORD, password.trim()).apply();
    }

    public String getMobileNumber() {
        return preferencesUserInformation.getString(PREF_MOBILE_NUMBER, null);
    }

    public void setMobileNumber(String mobileNumber) {
        editSharedPreferences.putString(PREF_MOBILE_NUMBER, mobileNumber.trim()).apply();
    }

    public String getEmailAddress() {
        return preferencesUserInformation.getString(PREF_EMAIL_ADDRESS, null);
    }

    public void setEmailAddress(String emailAddress) {
        editSharedPreferences.putString(PREF_EMAIL_ADDRESS, emailAddress.trim()).apply();
    }

    public String getAccountType() {
        return preferencesUserInformation.getString(PREF_ACCOUNT_TYPE, null);
    }

    public void setAccountType(String accountType) {
        editSharedPreferences.putString(PREF_ACCOUNT_TYPE, accountType.trim()).apply();
    }

    public String getEducation() {
        return preferencesUserInformation.getString(PREF_EDUCATION, null);
    }

    public void setEducation(String education) {
        editSharedPreferences.putString(PREF_EDUCATION, education.trim()).apply();
    }

    public String getProfilePicture() {
        return preferencesUserInformation.getString(PREF_PROFILE_PICTURE, null);
    }

    public void setProfilePicture(String profilePicture) {
        editSharedPreferences.putString(PREF_PROFILE_PICTURE, profilePicture.trim()).apply();
    }

    public Bitmap getProfilePictureBitmap() {
        if (getProfilePicture() != null) {
            byte[] buffer = Base64.decode(getProfilePicture(), Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(buffer, 0, buffer.length);
        }
        return null;
    }

    public boolean isStartSigningKey() {
        return preferencesUserInformation.getBoolean(PREF_START_KEY, true);
    }

    public void setStartSigningKey(boolean starter) {
        editSharedPreferences.putBoolean(PREF_START_KEY, starter).apply();
    }

    public void clearSharedPreferences() {
        editSharedPreferences.clear().apply();
    }
}
