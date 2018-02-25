package android.rezkyaulia.com.feo.controller.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.rezkyaulia.com.feo.R;
import android.rezkyaulia.com.feo.databinding.ActivityGuideFeoChampionBinding;
import android.rezkyaulia.com.feo.databinding.ActivityGuideMemoryBinding;

/**
 * Created by Rezky Aulia Pratama on 12/29/2017.
 */

public class GuideMemoryActivity extends BaseActivity {

    ActivityGuideMemoryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_guide_memory);


        binding.imageViewHome.setOnClickListener(v -> {
            finish();
        });

        binding.content.buttonAgree.setOnClickListener(v-> {
            Intent intent = new Intent(GuideMemoryActivity.this,MemoryActivity.class);
            startActivity(intent);
            finish();

        });
    }

}
