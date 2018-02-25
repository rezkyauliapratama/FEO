package android.rezkyaulia.com.feo.controller.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.rezkyaulia.com.feo.R;
import android.rezkyaulia.com.feo.controller.adapter.ScoreRVAdapter;
import android.rezkyaulia.com.feo.controller.adapter.ScoreSummaryRVAdapter;
import android.rezkyaulia.com.feo.controller.service.PushScoreService;
import android.rezkyaulia.com.feo.database.Facade;
import android.rezkyaulia.com.feo.database.entity.ScoreTbl;
import android.rezkyaulia.com.feo.databinding.ActivitySummaryScoresBinding;
import android.rezkyaulia.com.feo.databinding.ActivitySummarySpeedReadingBinding;
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

public class SummaryScoresActivity extends BaseActivity {
    public static final String ARGS1 = "ARGS1";
    public static final String ARGS2 = "ARGS2";

    private String mGuid;
    List<ScoreTbl> mScoreTbls;

    private LinearLayoutManager mLayoutManager;
    private ScoreSummaryRVAdapter mAdapter;

    ActivitySummaryScoresBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_summary_scores);

        mScoreTbls = new ArrayList<>();
        mScoreTbls.addAll(Facade.getInstance().getManageScoreTbl().getAllData());

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
        binding.content.textViewDate.setText(R.string.date);


        int bestScore = 0;
        ScoreTbl scoreTbl = Facade.getInstance().getManageScoreTbl().getHighScore();
        if (scoreTbl != null)
            bestScore = scoreTbl.getScore();

        binding.content.textViewBestScore.setText(getString(R.string.your_best_score)+bestScore+" "+getString(R.string.wpm));

        binding.imageViewBack.setOnClickListener(v->{
            finish();
        });
    }

    private void initRecyclerview(){
        mLayoutManager = new LinearLayoutManager(this);
        binding.content.recyclerView.setLayoutManager(mLayoutManager);
        if (mScoreTbls != null){
            mAdapter = new ScoreSummaryRVAdapter(this,mScoreTbls);
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
