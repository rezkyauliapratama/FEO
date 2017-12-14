package android.rezkyaulia.com.feo.controller.activity;

import android.app.DialogFragment;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.rezkyaulia.com.feo.R;
import android.rezkyaulia.com.feo.controller.fragment.dialog.InputAnswerDialogFragment;
import android.rezkyaulia.com.feo.controller.fragment.dialog.ViewSettingDialogFragment;
import android.rezkyaulia.com.feo.database.entity.LibraryTbl;
import android.rezkyaulia.com.feo.databinding.ActivityMainBinding;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import timber.log.Timber;


public class MainActivity extends BaseActivity {
    ActivityMainBinding binding;
    private Menu menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        binding.contentMain.btnSpeedReadingNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SpeedReadingActivity.class));
            }
        });

        binding.contentMain.btnSpeedReadingFeo.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,SpeedReadingActivity.class);
            intent.putExtra(SpeedReadingActivity.ARGS1,true);
            startActivity(intent);
        });

        binding.contentMain.btnSpeedReadingMemory.setOnClickListener(v->{
            Intent intent = new Intent(MainActivity.this,MemoryActivity.class);
            startActivity(intent);
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        this.menu = menu;

//        MenuItem item = menu.findItem(R.id.nav_more);
//        SpannableString s = new SpannableString("more");
//        s.setSpan(new ForegroundColorSpan(Color.WHITE), 0, s.length(), 0);
//        item.setTitle(s);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar rewardTbl clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_setting){
            Timber.e("Action Setting");
            showDialogInputText();
        }
        return super.onOptionsItemSelected(item);
    }


    public void showDialogInputText(){
        ViewSettingDialogFragment viewSettingDialogFragment = ViewSettingDialogFragment.newInstance();
        viewSettingDialogFragment.setStyle( DialogFragment.STYLE_NORMAL, R.style.dialog_light );
        viewSettingDialogFragment.show(getFragmentManager().beginTransaction(), ViewSettingDialogFragment.Dialog);
    }

/*
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void attachActivity(String string) {

    }*/
}
