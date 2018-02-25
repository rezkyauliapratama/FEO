package android.rezkyaulia.com.feo.model;

import java.util.Locale;

import timber.log.Timber;

/**
 * Created by Shiburagi on 18/07/2017.
 */

public class Language {
    public String text;
    public String name;
    public String icon;
    public String locale;
    public boolean tick;

    public Language(String text, String name, String locale) {
        this.text = text;
        this.name = name;
        this.locale = locale;
    }

    public Language(String text, Locale locale) {
        this.text = text;
        this.name = locale.getDisplayLanguage();
        this.locale=locale.getLanguage();

        Timber.e("name : "+ this.name +" | locale : "+this.locale);
    }

    public Language(String text, String locale, boolean tick) {
        this.text = text;
        this.locale = locale;
        this.tick = tick;
    }

    public Language(Locale locale, boolean tick) {
        this.text = locale.getDisplayLanguage();
        this.locale=locale.getLanguage();
        this.tick = tick;
    }
}
