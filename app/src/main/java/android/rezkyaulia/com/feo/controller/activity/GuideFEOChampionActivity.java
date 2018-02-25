package android.rezkyaulia.com.feo.controller.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.rezkyaulia.com.feo.R;
import android.rezkyaulia.com.feo.databinding.ActivityGuideFeoBinding;
import android.rezkyaulia.com.feo.databinding.ActivityGuideFeoChampionBinding;

/**
 * Created by Rezky Aulia Pratama on 12/29/2017.
 */

public class GuideFEOChampionActivity extends BaseActivity {

    ActivityGuideFeoChampionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_guide_feo_champion);


        binding.imageViewHome.setOnClickListener(v -> {
            finish();
        });

        binding.content.buttonAgree.setOnClickListener(v-> {
            Intent intent = new Intent(GuideFEOChampionActivity.this,SpeedReadingActivity.class);
            intent.putExtra(SpeedReadingActivity.ARGS1,true);
            startActivity(intent);
            finish();

        });
    }

}
