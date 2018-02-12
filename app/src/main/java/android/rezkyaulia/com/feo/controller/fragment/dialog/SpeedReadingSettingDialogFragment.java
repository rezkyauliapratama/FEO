package android.rezkyaulia.com.feo.controller.fragment.dialog;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.rezkyaulia.com.feo.R;
import android.rezkyaulia.com.feo.databinding.ContentDialogSettingSpeedReadingBinding;
import android.rezkyaulia.com.feo.utility.PreferencesManager;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import timber.log.Timber;

/**
 * Created by Rezky Aulia Pratama on 11/5/2017.
 */

public class SpeedReadingSettingDialogFragment extends DialogFragment {
    public final static String Dialog= "DIALOG";
    public final static String ARGS1 = "args1";
    public final static int TARGET = 1;

    ContentDialogSettingSpeedReadingBinding binding;

    private int mWpm;
    private int mNol;
    private int mGs;

    private boolean mIsQuiz;

    private DialogListener mListener;

    public static SpeedReadingSettingDialogFragment newInstance(boolean b){
        SpeedReadingSettingDialogFragment dialogFragment = new SpeedReadingSettingDialogFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARGS1, b);
        dialogFragment.setArguments(args);
        return dialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mIsQuiz = getArguments().getBoolean(ARGS1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding =  DataBindingUtil.inflate(inflater, R.layout.content_dialog_setting_speed_reading,container,false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.seekbarWpm.setProgress(1000);
        Timber.e("progress : "+binding.seekbarWpm.getProgress());

         mListener = (DialogListener) getTargetFragment();


        initData();
        initSeekbar();
        initButton();
    }

    private void initData(){
        mWpm = PreferencesManager.getInstance().getWPM();
        if (!mIsQuiz){
            mGs = PreferencesManager.getInstance().getGS();
            mNol = PreferencesManager.getInstance().getNOL();
        }else{
            mGs = 1;
            mNol = 1;

            binding.seekbarGroupSize.setEnabled(false);
            binding.seekbarNumberOfLines.setEnabled(false);
            binding.layoutNol.setVisibility(View.GONE);
            binding.layoutGs.setVisibility(View.GONE);
        }


        binding.seekbarGroupSize.setProgress(mGs);
        binding.seekbarNumberOfLines.setProgress(mNol);
        binding.seekbarWpm.setProgress(mWpm);

        binding.textviewValueWpm.setText(String.valueOf(mWpm));
        binding.textviewValueGroupSize.setText(String.valueOf(mGs));
        binding.textviewValueNumberOfLines.setText(String.valueOf(mNol));



    }

    private void initButton(){
        binding.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               PreferencesManager.getInstance().setWPM(mWpm);

               if (!mIsQuiz){
                   PreferencesManager.getInstance().setGS(mGs);
                   PreferencesManager.getInstance().setNOL(mNol);
               }

               mListener.onGetPreferences();
               dismiss();
            }
        });

        binding.buttonNextWpm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mWpm < binding.seekbarWpm.getMax()){
                    mWpm = mWpm + 10;
                    binding.seekbarWpm.setProgress(mWpm);
                    binding.seekbarWpm.setIndicatorFormatter(String.format("%s", mWpm));
                    binding.textviewValueWpm.setText(String.valueOf(mWpm));
                }


            }
        });

        binding.buttonBackWpm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mWpm>0){
                    mWpm = mWpm - 10;
                    binding.seekbarWpm.setProgress(mWpm);
                    binding.seekbarWpm.setIndicatorFormatter(String.format("%s", mWpm));
                    binding.textviewValueWpm.setText(String.valueOf(mWpm));

                }

            }
        });
    }

    private void initSeekbar(){
        final int stepSize = 10;
        binding.seekbarWpm.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            int value= 10;
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
//                if (value<1000){
                    this.value = ((int)Math.round(value/stepSize))*stepSize;
                    seekBar.setProgress(this.value);
                    seekBar.setIndicatorFormatter(String.format("%s", this.value));
                /*}else{
                    this.value = ((int)Math.round(value/(stepSize*stepSize)))*(stepSize*stepSize);
                    seekBar.setProgress(this.value);
                    seekBar.setIndicatorFormatter(String.format("%s", this.value));
                }
*/
            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {
                mWpm = this.value;
                binding.textviewValueWpm.setText(String.valueOf(this.value));
            }
        });

        binding.seekbarNumberOfLines.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            int value = 1;
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                    this.value = value;
            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {
                mNol = this.value;
                binding.textviewValueNumberOfLines.setText(String.valueOf(value));
            }
        });

        binding.seekbarGroupSize.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            int value = 1;
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                this.value = value;
            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {
                mGs = value;
                binding.textviewValueGroupSize.setText(String.valueOf(value));
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    public interface DialogListener {
        void onGetPreferences();
    }
}
