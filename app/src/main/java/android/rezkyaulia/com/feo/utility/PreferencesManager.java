package android.rezkyaulia.com.feo.utility;

import android.content.Context;

import com.securepreferences.SecurePreferences;

/**
 * Created by Shiburagi on 09/07/2016.
 */
public class PreferencesManager {

    private static final String CURRENT_VERSION = "CURRENT_VERSION";
    private static final String FONT_SIZE_KEY = "FONT_SIZE_KEY";
    private static final String WPM_KEY = "WPM_KEY";
    private static final String NOL_KEY = "NOL_KEY";
    private static final String GS_KEY = "GS_KEY";
    private static final String BACKGROUND_KEY = "BACKGROUND_KEY";


    private static PreferencesManager instance;
    private final Context context;
    private final SecurePreferences preference;

    public static PreferencesManager init(Context context) {
        instance = new PreferencesManager(context);

        return instance;
    }

    public static PreferencesManager getInstance() {
        return instance;
    }

    PreferencesManager(Context context) {
        this.context = context;
        preference = new SecurePreferences(context);

    }


    public void setCurrentVersion(int version) {
        SecurePreferences.Editor editor = preference.edit();
        editor.putInt(CURRENT_VERSION, version);
        editor.apply();
    }
    public int getCurrentVersion() {
        return preference.getInt(CURRENT_VERSION, 1);
    }


    public void setFontSize(float scale) {
        SecurePreferences.Editor editor = preference.edit();
        editor.putFloat(FONT_SIZE_KEY, scale);
        editor.apply();
    }
    public float getFontSize() {
        return preference.getFloat(FONT_SIZE_KEY, 12.0f);
    }

    public void setWPM(int scale) {
        SecurePreferences.Editor editor = preference.edit();
        editor.putInt(WPM_KEY, scale);
        editor.apply();
    }
    public int getWPM() {
        return preference.getInt(WPM_KEY, 2000);
    }

    public void setNOL(int scale) {
        SecurePreferences.Editor editor = preference.edit();
        editor.putInt(NOL_KEY, scale);
        editor.apply();
    }
    public int getNOL() {
        return preference.getInt(NOL_KEY, 1);
    }

    public void setGS(int scale) {
        SecurePreferences.Editor editor = preference.edit();
        editor.putInt(GS_KEY, scale);
        editor.apply();
    }
    public int getGS() {
        return preference.getInt(GS_KEY, 1);
    }

    public void setBlack(boolean b) {
        SecurePreferences.Editor editor = preference.edit();
        editor.putBoolean(BACKGROUND_KEY, b);
        editor.apply();
    }
    public boolean isBlack() {
        return preference.getBoolean(BACKGROUND_KEY,true);
    }




}
