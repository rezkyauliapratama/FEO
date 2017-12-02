package android.rezkyaulia.com.feo.controller.activity;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.rezkyaulia.com.feo.R;
import android.rezkyaulia.com.feo.databinding.ActivityMemoryBinding;
import android.rezkyaulia.com.feo.utility.PreferencesManager;
import android.rezkyaulia.com.feo.utility.Utils;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;


import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import timber.log.Timber;

/**
 * Created by Rezky Aulia Pratama on 11/29/2017.
 */

public class MemoryActivity extends BaseActivity {

    ActivityMemoryBinding binding;
    private int mWpm ;
    private Thread mThread;
    private int mIndex;
    private boolean mStart = false;

    List<Bitmap> mBitmaps;
    private InputStream imageIS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_memory);

        mBitmaps = new ArrayList<>();
        initData();
        initSeekbar();

        listRaw();

        binding.layoutContent.layoutBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initToggle(!mStart);

            }
        });
    }

    private void initData(){
        mWpm = PreferencesManager.getInstance().getWPM();

        binding.layoutContent.textviewValueWpm.setText(String.valueOf(mWpm));
        binding.layoutContent.seekbarWpm.setProgress(mWpm);

    }

    private void initSeekbar(){
        final int stepSize = 10;
        binding.layoutContent.seekbarWpm.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            int value= 10;
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
               /* if (value<1000){
                    this.value = ((int)Math.round(value/stepSize))*stepSize;
                    seekBar.setProgress(this.value);
                    seekBar.setIndicatorFormatter(String.format("%s", this.value));
                }else{*/
                this.value = ((int)Math.round(value/(stepSize*stepSize)))*(stepSize*stepSize);
                seekBar.setProgress(this.value);
                seekBar.setIndicatorFormatter(String.format("%s", this.value));
//                }

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
                    long wpmMilis = Utils.getInstance().getMilisWPM(mWpm);
                    Timber.e("mWPM : "+mWpm);

                    while(mStart){

                        if (mIndex >= mBitmaps.size())
                            mIndex = 0;

                        Bitmap bitmap = mBitmaps.get(mIndex);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Timber.e("bitmap");


                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);

                                Glide.with(MemoryActivity.this)
                                        .load(stream.toByteArray())
                                        .asBitmap()
                                        .placeholder(binding.layoutContent.imageView.getDrawable())
                                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                                        .dontAnimate()
                                        .listener(new RequestListener<byte[], Bitmap>() {
                                            @Override
                                            public boolean onException(Exception e, byte[] model, Target<Bitmap> target, boolean isFirstResource) {
                                                return false;
                                            }

                                            @Override
                                            public boolean onResourceReady(Bitmap resource, byte[] model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                                                binding.layoutContent.imageView.setVisibility(View.VISIBLE);
                                                if (mStart)
                                                    binding.layoutContent.layoutBody.setEnabled(false);
                                                else
                                                    binding.layoutContent.layoutBody.setEnabled(true);

                                                binding.layoutContent.textViewAnswer.setVisibility(View.GONE);
                                                return false;
                                            }
                                        })
                                        .into(binding.layoutContent.imageView);



                            }
                        });
                        Thread.sleep(wpmMilis);
                        mIndex++;

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                binding.layoutContent.imageView.setVisibility(View.);
                                binding.layoutContent.layoutBody.setEnabled(true);
                                binding.layoutContent.textViewAnswer.setVisibility(View.VISIBLE);

                            }
                        });

                        Thread.sleep(TimeUnit.SECONDS.toMillis(6));

                        if (mIndex>=mBitmaps.size()){
                            mIndex = 0;

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    initToggle(!mStart);

                                }
                            });
                        }
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
                binding.layoutContent.layoutBody.setEnabled(true);
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

        }else{
            binding.layoutContent.seekbarWpm.setEnabled(true);
            killThread();
        }

    }

}
