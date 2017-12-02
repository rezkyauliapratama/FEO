package android.rezkyaulia.com.feo.controller.activity;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.rezkyaulia.com.feo.R;
import android.rezkyaulia.com.feo.controller.fragment.BaseFragment;
import android.rezkyaulia.com.feo.controller.fragment.MemoryImageFragment;
import android.rezkyaulia.com.feo.controller.fragment.dialog.AnswerMemoryDialogFragment;
import android.rezkyaulia.com.feo.databinding.ActivityMemoryBackupBinding;
import android.rezkyaulia.com.feo.databinding.ActivityMemoryBinding;
import android.rezkyaulia.com.feo.databinding.FragmentImageMemoryBinding;
import android.rezkyaulia.com.feo.utility.PreferencesManager;
import android.rezkyaulia.com.feo.utility.Utils;
import android.support.v4.app.DialogFragment;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import timber.log.Timber;

/**
 * Created by Rezky Aulia Pratama on 11/29/2017.
 */

public class MemoryActivity extends BaseActivity {

    ActivityMemoryBackupBinding binding;
    private int mWpm ;
    private Thread mThread;
    private int mIndex;
    private boolean mStart = false;

    List<Bitmap> mBitmaps;

    long wpmMilis ;

    List<MemoryImageFragment> fragments;
    private InputStream imageIS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_memory_backup);

        mBitmaps = new ArrayList<>();
        listRaw();

        initData();
        initSeekbar();


        binding.layoutContent.framelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initToggle(!mStart);

            }
        });

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIndex>0){

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    mBitmaps.get(mIndex-1).compress(Bitmap.CompressFormat.PNG, 50, stream);
                    byte[] byteArray = stream.toByteArray();

                    showDialogInputText(byteArray);
                }
            }
        });
    }

    private void initData(){
        mWpm = PreferencesManager.getInstance().getWPM();
        mIndex = -1;
        wpmMilis = Utils.getInstance().getMilisWPM(mWpm);


        binding.layoutContent.textviewValueWpm.setText(String.valueOf(mWpm));
        binding.layoutContent.seekbarWpm.setProgress(mWpm);

      /*  fragments = new ArrayList<>();
        int x = 0;
        for (int i = 0 ; i < 20 ; i++){
            if (x<5){
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                mBitmaps.get(x).compress(Bitmap.CompressFormat.PNG, 50, stream);
                byte[] byteArray = stream.toByteArray();

                fragments.add(MemoryImageFragment.newInstance(byteArray));
                fragments.add(MemoryImageFragment.newInstance(null));
            }

            x++;
            if (x>=5){
                x=0;
            }

        }*/

    }





    public void showDialogInputText(byte[] byteArray){
        AnswerMemoryDialogFragment answerMemoryDialogFragment = AnswerMemoryDialogFragment.newInstance(byteArray);
        answerMemoryDialogFragment.setStyle( DialogFragment.STYLE_NORMAL, R.style.dialog_light );
        answerMemoryDialogFragment.show(getSupportFragmentManager().beginTransaction(), AnswerMemoryDialogFragment.Dialog);
    }

    private void initSeekbar(){
        final int stepSize = 10;
        binding.layoutContent.seekbarWpm.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            int value= 10;
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {

                    this.value = ((int)Math.round(value/stepSize))*stepSize;
                    seekBar.setProgress(this.value);
                    seekBar.setIndicatorFormatter(String.format("%s", this.value));


            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {
                mWpm = this.value;
                binding.layoutContent.textviewValueWpm.setText(String.valueOf(this.value));
            }
        });
    }

    public void listRaw(){

        Field[] fields=R.raw.class.getFields();
        try {
            for(int count=0; count < fields.length; count++){

                    imageIS = this.getResources().openRawResource(fields[count].getInt(fields[count]));

                    mBitmaps.add(BitmapFactory.decodeStream(imageIS)) ;
                    mBitmaps.add(null);

            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            Timber.e("ERROR : "+e.getMessage());
        }
    }

    public void initThread(){
        mThread = new Thread() {
            @Override
            public void run() {
                Timber.e("mbitmaps size : " + mBitmaps.size());
                try {
                    wpmMilis = Utils.getInstance().getMilisWPM(mWpm);

                    Timber.e("mWPM : "+mWpm);

                    MemoryImageFragment fragment;
                    Bitmap bitmap;

                    while(mStart){

                        mIndex++;

                        if (mIndex>=mBitmaps.size()){
                            mIndex = 0;

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Timber.e("mIndex RESET ================");

                                    initToggle(!mStart);
                                    mStart = false;

                                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                    mBitmaps.get(mIndex).compress(Bitmap.CompressFormat.PNG, 50, stream);
                                    byte[] byteArray = stream.toByteArray();

                                    MemoryImageFragment fragment = MemoryImageFragment.newInstance(mIndex,byteArray);
                                    displayFragmentMemory(binding.layoutContent.framelayout.getId(),fragment);
                                    fragment = null;

                                    binding.layoutContent.framelayout.setEnabled(true);


                                }
                            });
                        }

                        Timber.e("mIndex : "+mIndex);

                        bitmap = mBitmaps.get(mIndex);

                        if (bitmap != null){
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            mBitmaps.get(mIndex).compress(Bitmap.CompressFormat.PNG, 50, stream);
                            byte[] byteArray = stream.toByteArray();
                            fragment = MemoryImageFragment.newInstance(mIndex,byteArray);
                        }else{
                            fragment = MemoryImageFragment.newInstance(mIndex,null);
                        }


                        final MemoryImageFragment finalFragment = fragment;

                        boolean canPause;
                        if (mIndex%2 != 0){
                            canPause = true;
                            wpmMilis = TimeUnit.SECONDS.toMillis(6);
                        }else{
                            canPause = false;
                            wpmMilis = Utils.getInstance().getMilisWPM(mWpm);
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Timber.e("bitmap");

                                displayFragmentMemory(binding.layoutContent.framelayout.getId(), finalFragment);

                                    binding.layoutContent.framelayout.setEnabled(canPause);

                            }
                        });

                        Thread.sleep(wpmMilis);

                        bitmap = null;
                        fragment = null;
                    }



                } catch (InterruptedException e) {
                    Timber.e("ERROR initThread : " + e.getMessage());
                }
            }
        };

        mThread.start();

    }

    private void killThread(){
        if (mThread != null){
            Timber.e("mThreadPlay != null");
            if (mThread.isAlive()){
                Timber.e("mThreadPlay.isAlive()");
                mThread.interrupt();
                mThread = null;


            }

        }
    }

    private void initToggle(boolean start){
        Timber.e("inittoggle");
        mStart = start;
        Drawable drawable = null;
        if (mStart){
            binding.layoutContent.seekbarWpm.setEnabled(false);
            initThread();
            binding.fab.setVisibility(View.GONE);
        }else{
            binding.layoutContent.seekbarWpm.setEnabled(true);
            killThread();
            if (mIndex > 0)
                binding.fab.setVisibility(View.VISIBLE);

        }

    }

}
