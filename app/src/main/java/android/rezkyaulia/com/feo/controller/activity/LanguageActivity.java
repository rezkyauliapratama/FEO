package android.rezkyaulia.com.feo.controller.activity;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.rezkyaulia.com.feo.R;
import android.rezkyaulia.com.feo.controller.fragment.LanguagesFragment;
import android.rezkyaulia.com.feo.databinding.ActivityLanguageBinding;
import android.rezkyaulia.com.feo.model.Language;
import android.support.v7.widget.Toolbar;

import timber.log.Timber;


public class LanguageActivity extends BaseActivity implements LanguagesFragment.OnListFragmentInteractionListener {

    ActivityLanguageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_language);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(R.string.languages);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onListFragmentInteraction(Language item) {
        Timber.e("Translation Change");

        pref.setLocale(item.locale);

        Timber.e("CURRENT LOCALE : "+pref.getLocale());
        finish();
    }

}
