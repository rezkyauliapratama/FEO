package android.rezkyaulia.com.feo.controller.activity;

import android.app.DialogFragment;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.rezkyaulia.com.feo.R;
import android.rezkyaulia.com.feo.controller.fragment.NotificationFragment;
import android.rezkyaulia.com.feo.controller.fragment.dialog.InputAnswerDialogFragment;
import android.rezkyaulia.com.feo.controller.fragment.dialog.ViewSettingDialogFragment;
import android.rezkyaulia.com.feo.controller.service.PushLibraryService;
import android.rezkyaulia.com.feo.controller.service.PushScoreService;
import android.rezkyaulia.com.feo.database.Facade;
import android.rezkyaulia.com.feo.database.entity.LibraryTbl;
import android.rezkyaulia.com.feo.database.entity.NotificationTbl;
import android.rezkyaulia.com.feo.database.entity.ScoreTbl;
import android.rezkyaulia.com.feo.database.entity.UserTbl;
import android.rezkyaulia.com.feo.databinding.ActivityMainBinding;
import android.rezkyaulia.com.feo.handler.api.ApiClient;
import android.rezkyaulia.com.feo.handler.api.LibraryApi;
import android.rezkyaulia.com.feo.handler.api.NotificationApi;
import android.rezkyaulia.com.feo.handler.observer.RxBus;
import android.rezkyaulia.com.feo.model.NotifModel;
import android.rezkyaulia.com.feo.utility.DimensionConverter;
import android.rezkyaulia.com.feo.utility.HttpResponse;
import android.rezkyaulia.com.feo.utility.IconTextDrawable;
import android.rezkyaulia.com.feo.utility.Utils;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.common.api.Api;
import com.google.gson.Gson;
import com.squareup.haha.perflib.Main;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;


public class MainActivity extends BaseActivity implements NotificationFragment.OnListFragmentInteractionListener {
    ActivityMainBinding binding;
    private Menu menu;

    NotificationFragment notificationFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        binding.contentMain.btnSpeedReadingNormal.setOnClickListener(v -> startActivity(new Intent(MainActivity.this,GuideFEOActivity.class)));

        binding.contentMain.btnSpeedReadingFeo.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this,GuideFEOChampionActivity.class));
        });

        binding.contentMain.btnSpeedReadingMemory.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this,GuideMemoryActivity.class));
        });

        binding.contentMain.textViewName.setText(userTbl.getName());

        binding.contentMain.buttonProfile.setOnClickListener(v -> startActivity(new Intent(MainActivity.this,ProfileActivity.class)));

        initNavigation();


        binding.contentMain.layoutTrophy.setOnClickListener(v->{
            startActivity(new Intent(MainActivity.this,SummaryScoresActivity.class));
        });

        RxBus.getInstance().observable(NotifModel.class).subscribe(new Observer<NotifModel>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(NotifModel notifModel) {
                Timber.e("OnNExt : "+new Gson().toJson(notifModel));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run()
                    {
                        if (notificationFragment != null)
                            notificationFragment.initData();

                        if (menu != null)
                            onNotificationReceivedIteration();
                    }
                });
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onResume() {
        super.onResume();
        initBestScore();
        initJobDispatcher();
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
        onNotificationReceivedIteration();

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
            return true;
        }else if (id == R.id.action_notification){
            /*binding..openDrawer(Gravity.END);
            rightNavFragment.show(1);
            return true;*/
            binding.drawerLayout.openDrawer(Gravity.END);
            notificationFragment.setRead(1);
            onNotificationReceivedIteration();
        }
        return super.onOptionsItemSelected(item);
    }

    void initNavigation(){
        notificationFragment = NotificationFragment.newInstance();

        displayFragment(R.id.navView_notification, notificationFragment);
    }

    void initBestScore(){
        int bestScore = 0;
        ScoreTbl scoreTbl = Facade.getInstance().getManageScoreTbl().getHighScore();
        if (scoreTbl != null)
            bestScore = scoreTbl.getScore();

        binding.contentMain.textViewBestScore.setText(bestScore+" WPM");

        initChart();

    }

    public void showDialogInputText(){
        ViewSettingDialogFragment viewSettingDialogFragment = ViewSettingDialogFragment.newInstance();
        viewSettingDialogFragment.setStyle( DialogFragment.STYLE_NORMAL, R.style.dialog_light );
        viewSettingDialogFragment.show(getFragmentManager().beginTransaction(), ViewSettingDialogFragment.Dialog);
    }

    void initChart(){
        List<ScoreTbl> scoreTbls= Facade.getInstance().getManageScoreTbl().getDataForGraph();

        if (scoreTbls != null && scoreTbls.size() > 0){
            Timber.e("scoreTbls.size() : "+scoreTbls.size());
            Timber.e("scoreTbls : "+new Gson().toJson(scoreTbls));

            binding.contentMain.chart.setVisibility(View.VISIBLE);
            List<BarEntry> entries = new ArrayList<BarEntry>();
            Date[] dates = new Date[scoreTbls.size()];
            int i = 0;
            for (ScoreTbl data : scoreTbls) {
                Date date = Utils.getInstance().time().parseDate(data.getCreatedDate());
                if (date == null)
                    date = Utils.getInstance().time().parseDateWithT(data.getCreatedDate());
                // turn your data into Entry objects
                entries.add(new BarEntry(i, data.getScore()));
                dates[i] = date;
                i++;

            }

            BarDataSet set = new BarDataSet(entries, "Scores");
            BarData data = new BarData(set);
            /*data.setBarWidth(0.9f);*/ // set custom bar width

            IAxisValueFormatter formatter = new IAxisValueFormatter() {

                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    Date date = dates[(int)value];
                    String label = Utils.getInstance().time().getUserFriendlyDateWithoutYear(date);
                    return label;
                }
            };

            XAxis xAxis = binding.contentMain.chart.getXAxis();
            xAxis.setDrawGridLines(false);
            xAxis.setGranularity(1f);
            xAxis.setValueFormatter(formatter);


            YAxis leftAxis = binding.contentMain.chart.getAxisLeft();
            leftAxis.setLabelCount(8, false);
            leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
            leftAxis.setSpaceTop(15f);
            leftAxis.setGranularity(200f);
            leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

            YAxis rightAxis = binding.contentMain.chart.getAxisRight();
            rightAxis.setDrawGridLines(false);
            rightAxis.setLabelCount(8, false);
            rightAxis.setSpaceTop(15f);
            rightAxis.setGranularity(200f);
            rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

            Legend l = binding.contentMain.chart.getLegend();
            l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
            l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
            l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
            l.setDrawInside(false);
            l.setForm(Legend.LegendForm.SQUARE);
            l.setFormSize(9f);
            l.setTextSize(11f);
            l.setXEntrySpace(4f);

            binding.contentMain.chart.setFitBars(true);
            binding.contentMain.chart.setDrawBarShadow(false);
            binding.contentMain.chart.setDrawValueAboveBar(true);

            binding.contentMain.chart.getDescription().setEnabled(false);

            // scaling can now only be done on x- and y-axis separately
            binding.contentMain.chart.setPinchZoom(false);

            binding.contentMain.chart.setDrawGridBackground(false);
            binding.contentMain.chart.setData(data);
            binding.contentMain.chart.invalidate();

        }else{
            binding.contentMain.chart.setVisibility(View.GONE);
        }

        // mChart.setDrawYLabels(false);


    }

    public void onNotificationReceivedIteration() {

        int size = (int) facade.getManageNotificationTbl().countNotSeen(String.valueOf(userTbl.getUserId()));
        MenuItem menuItem = menu.findItem(R.id.action_notification);
        int dp24 = DimensionConverter.getInstance().stringToDimensionPixelSize(
                "24dp",
                getResources().getDisplayMetrics()
        );
        if (size == 0)
            menuItem.setIcon(
                    new IconTextDrawable(
                            MainActivity.this, null,
                            R.drawable.ic_notifications_none_black_24dp,
                            0, 0

                    )
            );
        else {
            menuItem.setIcon(
                    new IconTextDrawable(
                            MainActivity.this, String.valueOf(size),
                            R.drawable.ic_notifications_black_24dp,
                            0, 0

                    )
            );
        }
    }


    private void initJobDispatcher(){
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));
        Job myJob = dispatcher.newJobBuilder()
                .setLifetime(Lifetime.FOREVER)
                .setService(PushLibraryService.class) // the JobService that will be called
                .setTag("libraryServices")
                .setReplaceCurrent(true)

                // Run between 30 - 60 seconds from now.
                .setTrigger(Trigger.executionWindow(5, 10))// uniquely identifies the job
                .build();

        Timber.e("initJobDispatcher");
        dispatcher.mustSchedule(myJob);
    }

    @Override
    public void onDeleteNotificationInteraction(NotificationTbl notificationTbl) {
        facade.getManageNotificationTbl().remove(notificationTbl);
        notificationFragment.initData();
        /*binding.layoutProgress.setVisibility(View.VISIBLE);
        ApiClient.getInstance().notification().delete(notificationTbl, new ParsedRequestListener<NotificationApi.Response>() {
            @Override
            public void onResponse(NotificationApi.Response response) {
                Timber.e("Response notification del : " +new Gson().toJson(response));
                if (HttpResponse.getInstance().success(response)){
                    facade.getManageNotificationTbl().remove(notificationTbl);
                    notificationFragment.initData();
                    Toast.makeText(MainActivity.this,"Delete successful",Toast.LENGTH_LONG);

                }else{
                    Toast.makeText(MainActivity.this,"Cannot delete this notification, please check your network",Toast.LENGTH_LONG);

                }
                binding.layoutProgress.setVisibility(View.GONE);
            }

            @Override
            public void onError(ANError anError) {
                binding.layoutProgress.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this,"Cannot delete this notification, please check your network",Toast.LENGTH_LONG);
            }
        });
*/
    }


/*
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void attachActivity(String string) {

    }*/
}
