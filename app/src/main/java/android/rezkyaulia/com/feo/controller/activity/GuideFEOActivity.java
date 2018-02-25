package android.rezkyaulia.com.feo.controller.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.rezkyaulia.com.feo.R;
import android.rezkyaulia.com.feo.controller.fragment.ProfileFragment;
import android.rezkyaulia.com.feo.databinding.ActivityGuideFeoBinding;
import android.rezkyaulia.com.feo.databinding.ActivityProfileBinding;
import android.support.v7.app.AlertDialog;
import android.view.View;

/**
 * Created by Rezky Aulia Pratama on 12/29/2017.
 */

public class GuideFEOActivity extends BaseActivity {

    ActivityGuideFeoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_guide_feo);


        binding.imageViewHome.setOnClickListener(v -> {
            finish();
        });

        binding.content.buttonAgree.setOnClickListener(v-> {
            startActivity(new Intent(GuideFEOActivity.this,SpeedReadingActivity.class));
            finish();
        });
    }

}
