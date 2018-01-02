package android.rezkyaulia.com.feo.controller.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.rezkyaulia.com.feo.R;
import android.rezkyaulia.com.feo.controller.adapter.ScoreRVAdapter;
import android.rezkyaulia.com.feo.controller.service.PushScoreService;
import android.rezkyaulia.com.feo.database.Facade;
import android.rezkyaulia.com.feo.database.entity.ScoreTbl;
import android.rezkyaulia.com.feo.databinding.ActivitySummarySpeedReadingBinding;
import android.rezkyaulia.com.feo.utility.PreferencesManager;
import android.rezkyaulia.com.feo.utility.TimeUtility;
import android.rezkyaulia.com.feo.utility.Utils;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;


import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * Created by Rezky Aulia Pratama on 11/23/2017.
 */

public class SummarySpeedReadingActivity extends BaseActivity {
    public static final String ARGS1 = "ARGS1";
    public static final String ARGS2 = "ARGS2";

    private String mGuid;
    List<ScoreTbl> mScoreTbls;

    private LinearLayoutManager mLayoutManager;
    private ScoreRVAdapter mAdapter;

    ActivitySummarySpeedReadingBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_summary_speed_reading);

        mScoreTbls = new ArrayList<>();

        if (getIntent() != null){
            mGuid = getIntent().getStringExtra(ARGS1);
            mScoreTbls.addAll(getIntent().getParcelableArrayListExtra(ARGS2)) ;
            Timber.e("GET INTENT != null : "+mGuid);
        }

        /*if (mGuid != null)
            if (mGuid.length() > 0){
                mScoreTbls = Facade.getInstance().getManageScoreTbl().getbyGuid(mGuid);
            }*/

        initView();
        initRecyclerview();
        initJobDispatcher();

    }

    private void initView(){
        binding.content.imageViewNoResult.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_trophy));
        binding.content.textViewNumber.setText(R.string.result);
        binding.content.textViewYourAnswer.setText(R.string.your_answer);
        binding.content.textViewCorrectAnswer.setText(R.string.correct_answer);
        binding.content.textViewWpm.setText(R.string.wpm);

        binding.content.textviewDate.setText(Utils.getInstance().time().getUserFriendlyDateTimeString());

        Timber.e("GUID : "+mGuid);
       if (mGuid != null){
           ScoreTbl scoreTbl = Facade.getInstance().getManageScoreTbl().getHighScore(mGuid);
           Timber.e("new SCORETBL HIGH : "+new Gson().toJson(scoreTbl));
           if (scoreTbl != null)
               binding.content.textviewValueScore.setText(String.valueOf(scoreTbl.getScore()));
       }

        int bestScore = 0;
        ScoreTbl scoreTbl = Facade.getInstance().getManageScoreTbl().getHighScore();
        if (scoreTbl != null)
            bestScore = scoreTbl.getScore();

        binding.content.textViewBestScore.setText("Your best score : "+bestScore+" WPM");
       binding.content.buttonFinish.setOnClickListener(v -> finish());

    }

    private void initRecyclerview(){
        mLayoutManager = new LinearLayoutManager(this);
        binding.content.recyclerView.setLayoutManager(mLayoutManager);
        if (mScoreTbls != null){
            mAdapter = new ScoreRVAdapter(this,mScoreTbls);
            binding.content.recyclerView.setAdapter(mAdapter);
        }
    }

    private void initJobDispatcher(){
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));
        Job myJob = dispatcher.newJobBuilder()
                .setLifetime(Lifetime.FOREVER)
                .setService(PushScoreService.class) // the JobService that will be called
                .setTag("my-unique-tag")
                .setReplaceCurrent(true)

                // Run between 30 - 60 seconds from now.
                .setTrigger(Trigger.executionWindow(5, 10))// uniquely identifies the job
                .build();

        Timber.e("initJobDispatcher");
        dispatcher.mustSchedule(myJob);
    }

}
