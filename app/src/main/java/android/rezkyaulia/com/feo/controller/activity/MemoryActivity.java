package android.rezkyaulia.com.feo.controller.activity;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.renderscript.ScriptGroup;
import android.rezkyaulia.com.feo.R;
import android.rezkyaulia.com.feo.controller.fragment.MemoryImageFragment;
import android.rezkyaulia.com.feo.controller.fragment.dialog.AnswerMemoryDialogFragment;
import android.rezkyaulia.com.feo.databinding.ActivityMemoryBackupBinding;
import android.rezkyaulia.com.feo.utility.PreferencesManager;
import android.rezkyaulia.com.feo.utility.Utils;
import android.support.v4.app.DialogFragment;
import android.view.MenuItem;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.StreamBitmapDecoder;
import com.bumptech.glide.request.target.Target;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

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

    private Random rand = new Random();

    List<Bitmap> mBitmaps;

    long wpmMilis ;

    List<MemoryImageFragment> fragments;
    private InputStream imageIS;


    int size1 = 3;
    int size2 = 3;
    int size3 = 3;
    int size4 = 15;
    int size5 = 15;
    int size6 = 15;
    int size7 = 15;
    int size8 = 15;
    int size9 = 15;
    int size10 = 20;

    List <String> list1;
    List <String> list2;
    List <String> list3;
    List <String> list4;
    List <String> list5;
    List <String> list6;
    List <String> list7;
    List <String> list8;
    List <String> list9;
    List <String> list10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_memory_backup);

        setSupportActionBar(binding.toolbar);

        mBitmaps = new ArrayList<>();

        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        list3 = new ArrayList<>();
        list4 = new ArrayList<>();
        list5 = new ArrayList<>();
        list6 = new ArrayList<>();
        list7 = new ArrayList<>();
        list8 = new ArrayList<>();
        list9 = new ArrayList<>();
        list10 = new ArrayList<>();


        binding.layoutProgress.setVisibility(View.VISIBLE);
        new FirstInit().execute();

        initData();
        initSeekbar();



        binding.imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
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

                                    boolean b = false;
                                    if (mIndex <5){
                                        b = true;
                                    }else{
                                        b = false;
                                    }
                                    MemoryImageFragment fragment = MemoryImageFragment.newInstance(mIndex,byteArray,b);
                                    displayFragmentMemory(binding.layoutContent.framelayout.getId(),fragment);
                                    fragment = null;

                                    binding.layoutContent.framelayout.setEnabled(true);


                                }
                            });
                        }

                        Timber.e("mIndex : "+mIndex);

                        bitmap = mBitmaps.get(mIndex);


                        boolean b = false;
                        if (mIndex <3){
                            b = true;
                        }else{
                            b = false;
                        }

                        if (bitmap != null){
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            mBitmaps.get(mIndex).compress(Bitmap.CompressFormat.PNG, 100, stream);
                            byte[] byteArray = stream.toByteArray();
                            fragment = MemoryImageFragment.newInstance(mIndex,byteArray,b);
                        }else{
                            fragment = MemoryImageFragment.newInstance(mIndex,null,b);
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
            binding.layoutFab.setVisibility(View.GONE);
        }else{
            binding.layoutContent.seekbarWpm.setEnabled(true);
            killThread();
            if (mIndex > 0)
                binding.layoutFab.setVisibility(View.VISIBLE);

        }

    }


    public Bitmap getBitmapFromAssets(String fileName) throws IOException, ExecutionException, InterruptedException {
       /* AssetManager assetManager = getAssets();
        InputStream istr = assetManager.open("picture/"+fileName);
        Bitmap bitmap = decodeSampledBitmapFromResource(istr,100,100);
        istr.close();*/

        return Glide.with(this)
                .load(Uri.parse("file:///android_asset/picture/"+fileName))
                .asBitmap()
                .into(400, 400). // Width and height
                get();
//        return bitmap;
       /*
        BufferedInputStream inputStream = new BufferedInputStream(istr);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int count;
        while ((count = inputStream.read(buffer)) > -1) {
            outputStream.write(buffer, 0, count);
        }
        byte[] data = outputStream.toByteArray();

        try {
            istr = new ByteArrayInputStream(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        outputStream.close();

        return new StreamBitmapDecoder(this).decode(istr, com.bumptech.glide.request.target.Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)*/
/*

       fileName = fileName.substring(0,fileName.length()-4);
       Timber.e("filename : "+fileName);
        int resID = getResources().getIdentifier(fileName, "drawable",  getPackageName());
        Timber.e("RESID : "+resID);
        if (resID > 0){
            return BitmapFactory.decodeResource(this.getResources(),resID);
        }else{
            return null;
        }
*/

    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(InputStream res,
                                                         int reqWidth, int reqHeight) {

        try {
        InputStream newIs = res;
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(newIs, null, options);

        newIs.close();
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        Bitmap b =  BitmapFactory.decodeStream(res, null, options);

        return b;
        } catch (IOException e) {
            Timber.e("ERROR : "+e.getMessage());
            return null;
        }
    }

    private boolean listAssetFiles(String path) {



        String [] list;
        try {
            list = getAssets().list(path);
            if (list.length > 0) {
                // This is a folder
                for (String file : list) {
                    if (!listAssetFiles(path + "/" + file))
                        return false;
                    else {

                        Timber.e("FILENAME : "+file);
                        String [] str = file.split("-");

                        if (str[0].equals("1")){
                            list1.add(file);
                        }

                        if (str[0].equals("2")){
                            list2.add(file);
                        }

                        if (str[0].equals("3")){
                            list3.add(file);
                        }

                        if (str[0].equals("4")){
                            list4.add(file);
                        }

                        if (str[0].equals("5")){
                            list5.add(file);
                        }

                        if (str[0].equals("6")){
                            list6.add(file);
                        }

                       /* if (str[0].equals("7")){
                            list7.add(file);
                        }*/

                        if (str[0].equals("8")){
                            list8.add(file);
                        }

                        /*if (str[0].equals("9")){
                            list9.add(file);
                        }
*/
                        if (str[0].equals("10")){
                            list10.add(file);
                        }
                    }
                }
            }
        } catch (IOException e) {
            Timber.e("ERROR : "+e.getMessage());
            return false;
        }

        return true;
    }

    void initListData(){
        List<String> names = new ArrayList<>();

        if(list1.size()>0){
            for (int i = 0; i < (size1 > list1.size() ? list1.size() : size1) ; i++){
                String tmp = getRandArrayElement(list1,names);
                if (tmp.length()>0){
                    names.add(tmp);
                    Timber.e("Names ADD : "+tmp);
                }
            }
        }


        if(list2.size()>0){
            for (int i = 0; i < (size2 > list2.size() ? list2.size() : size2) ; i++){
                String tmp = getRandArrayElement(list2,names);
                if (tmp.length()>0){
                    names.add(tmp);
                    Timber.e("Names ADD : "+tmp);
                }
            }
        }

        if(list3.size()>0){
            for (int i = 0; i < (size3 > list3.size() ? list3.size() : size3) ; i++){
                String tmp = getRandArrayElement(list3,names);
                if (tmp.length()>0){
                    names.add(tmp);
                    Timber.e("Names ADD : "+tmp);
                }
            }
        }

        if(list4.size()>0){
            for (int i = 0; i < (size4 > list4.size() ? list4.size() : size4) ; i++){
                String tmp = getRandArrayElement(list4,names);
                if (tmp.length()>0){
                    names.add(tmp);
                    Timber.e("Names ADD : "+tmp);
                }
            }
        }

        if(list5.size()>0){
            for (int i = 0; i < (size5 > list5.size() ? list5.size() : size5) ; i++){
                String tmp = getRandArrayElement(list5,names);
                if (tmp.length()>0){
                    names.add(tmp);
                    Timber.e("Names ADD : "+tmp);
                }
            }
        }

        if(list6.size()>0){
            for (int i = 0; i < (size6 > list6.size() ? list6.size() : size6) ; i++){
                String tmp = getRandArrayElement(list6,names);
                if (tmp.length()>0){
                    names.add(tmp);
                    Timber.e("Names ADD : "+tmp);
                }
            }
        }

        if(list7.size()>0){
            for (int i = 0; i < (size7 > list7.size() ? list7.size() : size7) ; i++){
                String tmp = getRandArrayElement(list7,names);
                if (tmp.length()>0){
                    names.add(tmp);
                    Timber.e("Names ADD : "+tmp);
                }
            }
        }

        if(list8.size()>0){
            for (int i = 0; i < (size8 > list8.size() ? list8.size() : size8) ; i++){
                String tmp = getRandArrayElement(list8,names);
                if (tmp.length()>0){
                    names.add(tmp);
                    Timber.e("Names ADD : "+tmp);
                }
            }
        }

        if(list9.size()>0){
            for (int i = 0; i < (size9 > list9.size() ? list9.size() : size9) ; i++){
                String tmp = getRandArrayElement(list9,names);
                if (tmp.length()>0){
                    names.add(tmp);
                    Timber.e("Names ADD : "+tmp);
                }
            }
        }

        if(list10.size()>0){
            for (int i = 0; i < (size10 > list10.size() ? list10.size() : size10) ; i++){
                String tmp = getRandArrayElement(list10,names);
                if (tmp.length()>0){
                    names.add(tmp);
                    Timber.e("Names ADD : "+tmp);
                }
            }
        }

        initBitmap(names);
    }

    public String getRandArrayElement(List<String> str, List<String> names){
        String res = "";
        boolean b = true;

        while (b){
            String tmp = str.get(rand.nextInt(str.size()));
            b = false;
            for(String s : names){
               if (s.equalsIgnoreCase(tmp))
                   b = true;
            }
            if (!b)
                res = tmp;
        }
        Timber.e("getRandArrayElement : "+res);
        return res;
    }

    void initBitmap(List<String> names){
        try {
        for (String s : names){
            Timber.e("listNames : "+s);
            Bitmap bitmap = getBitmapFromAssets(s);
            if (bitmap != null){
                mBitmaps.add(bitmap);
                mBitmaps.add(null);
            }else{
                Timber.e("BITMAP IS NULL "+s);
            }
        }

        } catch (IOException e) {
            Timber.e("ERROR : "+e.getMessage());

        } catch (InterruptedException e) {
            Timber.e("InterruptedException : "+e.getMessage());


        } catch (ExecutionException e) {
            Timber.e("ExecutionException : "+e.getMessage());
        }

    }

    private class FirstInit extends AsyncTask<Void,Void,Void> {

            @Override
            protected Void doInBackground(Void... params) {
                Looper.prepare();

                listAssetFiles("picture");
                initListData();



                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                binding.layoutProgress.setVisibility(View.GONE);
            }

    }

}
