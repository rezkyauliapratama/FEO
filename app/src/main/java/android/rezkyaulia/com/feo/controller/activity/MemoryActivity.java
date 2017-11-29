package android.rezkyaulia.com.feo.controller.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.rezkyaulia.com.feo.R;
import android.rezkyaulia.com.feo.databinding.ActivityMemoryBinding;

/**
 * Created by Rezky Aulia Pratama on 11/29/2017.
 */

public class MemoryActivity extends BaseActivity {

    ActivityMemoryBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_memory);

    }
}
