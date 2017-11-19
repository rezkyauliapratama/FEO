package android.rezkyaulia.com.feo.controller.fragment;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.rezkyaulia.com.feo.R;
import android.rezkyaulia.com.feo.controller.fragment.dialog.InputTextDialogFragment;
import android.rezkyaulia.com.feo.controller.fragment.dialog.SpeedReadingSettingDialogFragment;
import android.rezkyaulia.com.feo.database.Facade;
import android.rezkyaulia.com.feo.database.ManageLibraryTbl;
import android.rezkyaulia.com.feo.database.entity.LibraryTbl;
import android.rezkyaulia.com.feo.databinding.FragmentSpeedReadingBinding;
import android.rezkyaulia.com.feo.pojo.ReadableObj;
import android.rezkyaulia.com.feo.pojo.Words;
import android.rezkyaulia.com.feo.utility.PreferencesManager;
import android.rezkyaulia.com.feo.observer.RxBus;
import android.rezkyaulia.com.feo.utility.Utils;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;


import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

import static java.lang.Thread.sleep;

/**
 * Created by Rezky Aulia Pratama on 10/25/2017.
 */

public class SpeedReadingFragment extends BaseFragment implements SpeedReadingSettingDialogFragment.DialogListener
                                                                    ,InputTextDialogFragment.DialogListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    static final int READ_REQ = 1;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FragmentSpeedReadingBinding binding;

    private boolean mStart = false;
    private List<String> mWords;
    private List<ReadableObj> mReadableWords;

    private int mIndex;
    private int mDivided;

    private int mWpm;
    private int mGs;
    private int mNol;

    public static SpeedReadingFragment newInstance() {
        SpeedReadingFragment fragment = new SpeedReadingFragment();
        /*Bundle args = new Bundle();
        args.putString(ARG_PARAM1, category);
        fragment.setArguments(args);*/
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
        mWords = new ArrayList<>();
        mReadableWords = new ArrayList<>();
        mIndex = 0;
        mDivided = 0;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding =  DataBindingUtil.inflate(inflater, R.layout.fragment_speed_reading,container,false);

        if(savedInstanceState != null){
            Timber.e("SAVEDINSTACESTATE");
            /*mCategory = savedInstanceState.getString(EXTRA1);
            movies = savedInstanceState.getParcelableArrayList(EXTRA2);
            listState = savedInstanceState.getParcelable(LIST_STATE_KEY);
            mPage = savedInstanceState.getInt(EXTRA3);*/
        }



        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        initRX();

        initButton();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add_text) {
            Timber.e("onoption add text");
            showDialogInputText();
        }
        return super.onOptionsItemSelected(item);

    }




    @Override
    public void onSaveInstanceState(Bundle outState) {
        /*listState = mLayoutManager.onSaveInstanceState();
        outState.putParcelable(LIST_STATE_KEY, listState);*/

        /*outState.putString(EXTRA1, mCategory);
        outState.putParcelableArrayList(EXTRA2, new ArrayList<MovieAbstract>(movies));
        outState.putInt(EXTRA3, mPage);*/
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        Timber.e("ONRESUME");
        initPref();
    }


    @Override
    public void onGetPreferences() {
        initPref();
        initData(mWords);
    }

    @Override
    public void onGetTextDialog(String title,String content) {
        saveIntoLibrary(title,content);
        if (content != null){
            if (content.length()>0){
                mWords.clear();
                mWords.addAll(Utils.getInstance().convertStringIntoList(content));
                initData(mWords);
            }
        }
    }

    private void initButton(){
        binding.contentSpeedReading.btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mWords.size()>0)
                    initToggle(!mStart);
                else
                    Snackbar.make(binding.containerBody, R.string.sorrythereisnowordsthatcanplay,Snackbar.LENGTH_LONG).show();
            }
        });

        binding.contentSpeedReading.layoutSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mStart)
                    showDialogSetting();
            }
        });

        binding.contentSpeedReading.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextWords(true);
            }
        });

        binding.contentSpeedReading.btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextWords(false);
            }
        });
    }

    private void initToggle(boolean start){
        Timber.e("inittoggle");
        mStart = start;
        Drawable drawable = null;
        if (mStart){
            drawable = getResources().getDrawable(R.drawable.ic_icon_pause_round);
            binding.contentSpeedReading.layoutSetting.setEnabled(false);
        }else{
            drawable = getResources().getDrawable(R.drawable.ic_icon_play);
            binding.contentSpeedReading.layoutSetting.setEnabled(true);

        }
        binding.contentSpeedReading.btnPlay.setImageDrawable(drawable);
        playWords();
    }

    private void initData(List<String> words){
        if (words != null){
            if (words.size() > 0){
                Timber.e("initdata word.Size : "+words.size()+" | mNol : "+mNol+" | mGS : "+mGs);
                mReadableWords.clear();
                int pointer = 0;
                while(pointer<words.size()){
                    String temp = "";
                    int lenght=0;

                    for (int i = 0; i<mNol ; i++) {
                        Timber.e("For mNol");
                        for (int j = 0; j < mGs; j++) {
                            Timber.e("for mGS");
                            if (pointer < words.size()) {
                                temp = temp + " " + words.get(pointer);
                            }else{
                                break;
                            }
                            Timber.e("pointer : "+pointer);
                            pointer++;
                            lenght++;

                        }
                        temp = temp + "\n";
                        if (pointer >= words.size()) {
                            break;
                        }

                    }
                    mReadableWords.add(new ReadableObj(temp,lenght));
                    Timber.e("==============================================================");
                }

                Timber.e("start index : "+mIndex+" | MDIVIDED : "+mDivided);

                int dimension = mGs * mNol;
                int divided = mDivided / dimension;
                mIndex = divided;
                mDivided=divided*dimension;

                Timber.e("start divided : "+divided+" | dimension : "+dimension+" | mIndex : "+mIndex+" | MDIVIDED : "+mDivided);

                if (mIndex>=words.size())
                    mIndex = 0;

                if (mReadableWords.size()>0)
                    binding.contentSpeedReading.textviewContent.setText(mReadableWords.get(mIndex).getWord());

                initToggle(false);

            }
        }
    }

    /*private void playWords(){
        if (mWords != null){
            Timber.e("mwords != null");
            if (mWords.size() > 0){
                Timber.e("mwords.size()>0");

                Timber.e("mStart : "+mStart);
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            if (mStart){
                                int dimension = mGs * mNol;
                                int divided = mIndex / dimension;
                                Timber.e("start divided : "+divided);
                                mIndex = divided*dimension;
                                int pointWord = mIndex;
                                String tempWord = "";
                                Timber.e("start Index :"+mIndex);

                                while(mStart){

                                    long wpmMilis = Utils.getInstance().getMilisWPM(mWpm);
                                    if (mIndex>=mWords.size()){
                                        Timber.e("mIndex>=mWords.size()");
                                        pointWord = 0;
                                        mIndex = 0;
                                    }
                                    Timber.e("mIndex : "+mIndex+" | pointword : "+pointWord);
                                    if (pointWord == mIndex){
                                        Timber.e("pointWord == mIndex");
                                        tempWord = "";
                                        for (int i = 0; i<mNol ; i++){
                                            Timber.e("For mNol");
                                            for (int j = 0; j<mGs; j++){
                                                Timber.e("for mGS");
                                                if (pointWord>=mWords.size()){
                                                    pointWord = 0;
                                                    Timber.e("Point Word : "+pointWord+" | break GS");
                                                    break;
                                                }

                                                tempWord = tempWord+" ".concat(mWords.get(pointWord));
                                                Timber.e("tempWord GS:"+tempWord);

                                                pointWord++;


                                            }
                                            tempWord=tempWord+"\n";
                                            Timber.e("tempWord NOL:"+tempWord);

                                            Timber.e("pointwords : "+pointWord);
                                            if (pointWord>=mWords.size()){
                                                pointWord = 0;
                                                Timber.e("Point Word : "+pointWord+" | break NOL");
                                                break;
                                            }


                                        }
                                    }

                                    Timber.e("word : "+tempWord);
                                    final String word = tempWord;
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            binding.contentSpeedReading.textviewContent.setText(word);
                                        }
                                    });
                                    mIndex++;
                                    Timber.e("END mINdex : "+mIndex+" | END pointWord : "+pointWord);
                                    Timber.e("===================================================================================================");
                                    Thread.sleep(wpmMilis);

                                }
                            }

                        } catch (InterruptedException e) {
                            Timber.e("ERROR : "+e.getMessage());
                        }
                    }
                };

                thread.start();

            }
        }
    }*/

    private void playWords(){
        if (mReadableWords != null){
            Timber.e("mwords != null");
            if (mReadableWords.size() > 0){
                Timber.e("mReadableWords.Size : "+mReadableWords.size());

                Timber.e("mStart : "+mStart);
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            if (mStart){

                                while(mStart){

                                    long wpmMilis = Utils.getInstance().getMilisWPM(mWpm);


                                    final String word = mReadableWords.get(mIndex).getWord();
                                    final int length = mReadableWords.get(mIndex).getLenght();

                                    for (int i=0; i<length ; i++){
                                        mDivided++;
                                    }

                                    if (mDivided>mWords.size()){
                                        mDivided = 0;
                                    }

                                    Timber.e("mIndex : "+mIndex+" | word : "+word+" | lenght : "+length);
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            binding.contentSpeedReading.textviewContent.setText(word);
                                        }
                                    });
                                    mIndex++;


                                    Timber.e("WPM Milis : "+wpmMilis*length);
                                    Timber.e("===================================================================================================");
                                    Thread.sleep(wpmMilis*length);

                                    if (mIndex>=mReadableWords.size()){
                                        Timber.e("mIndex>=mReadableWords.size()");
                                        mIndex = 0;

                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                initToggle(!mStart);
                                                showAlertDialogFinish();

                                            }
                                        });
                                    }

                                }
                            }else{
                                if (mIndex>0)
                                    mIndex--;
                            }

                        } catch (InterruptedException e) {
                            Timber.e("ERROR : "+e.getMessage());
                        }
                    }
                };

                thread.start();

            }
        }
    }

    private void nextWords(boolean isNext){
        int dimension = mGs * mNol;

        if (isNext){
           if (mReadableWords != null){
               if (mReadableWords.size()>0){
                   mIndex++;
                   if (mIndex>=mReadableWords.size()){
                       mIndex=0;
                   }



                   final String tempWord = mReadableWords.get(mIndex).getWord();
                   final int lenght = mReadableWords.get(mIndex).getLenght();

                   Timber.e("start mDivided : "+mDivided);

                   mDivided = (mIndex * dimension)+lenght;

                   if (mDivided>mWords.size()){
                       mDivided = 0;
                   }

                   Timber.e("end mDivided : "+mDivided);

                   getActivity().runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           binding.contentSpeedReading.textviewContent.setText(tempWord);
                       }
                   });
               }
           }


        }else{
            if (mReadableWords != null){
                if (mReadableWords.size()>0){
                    mIndex--;
                    if (mIndex<0){
                        mIndex=mReadableWords.size()-1;
                    }



                    final String tempWord = mReadableWords.get(mIndex).getWord();
                    final int lenght = mReadableWords.get(mIndex).getLenght();

                    Timber.e("start mDivided : "+mDivided);

                    mDivided = (mIndex * dimension)+lenght;

                    if (mDivided>mWords.size()){
                        mDivided = 0;
                    }

                    Timber.e("end mDivided : "+mDivided);

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            binding.contentSpeedReading.textviewContent.setText(tempWord);
                        }
                    });
                }
            }
        }

    }


    private void showDialogSetting(){
        SpeedReadingSettingDialogFragment settingDialogFragment = SpeedReadingSettingDialogFragment.newInstance();
        settingDialogFragment.setStyle( DialogFragment.STYLE_NORMAL, R.style.dialog );
        settingDialogFragment.setTargetFragment(this,settingDialogFragment.TARGET);
        settingDialogFragment.show(getFragmentManager().beginTransaction(),SpeedReadingSettingDialogFragment.Dialog);

    }

    private void showDialogInputText(){
        InputTextDialogFragment inputTextDialog = InputTextDialogFragment.newInstance();
        inputTextDialog.setStyle( DialogFragment.STYLE_NORMAL, R.style.dialog_light );
        inputTextDialog.setTargetFragment(this,inputTextDialog.TARGET);
        inputTextDialog.show(getFragmentManager().beginTransaction(), InputTextDialogFragment.Dialog);
    }

    private void initPref(){
        mWpm = PreferencesManager.getInstance().getWPM();
        mNol = PreferencesManager.getInstance().getNOL();
        mGs = PreferencesManager.getInstance().getGS();

        binding.contentSpeedReading.textViewWpm.setText(String.format("WPM : %s",mWpm));
        binding.contentSpeedReading.textViewNol.setText(String.format("Number of lines : %s",mNol));
        binding.contentSpeedReading.textViewGs.setText(String.format("Group size : %s",mGs));
    }

    private void saveIntoLibrary(final String title, final String content){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setMessage(R.string.doyouwanttosaveitintolibrary)

                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ManageLibraryTbl manageLibraryTbl = Facade.getInstance().getManageLibraryTbl();

                        if (manageLibraryTbl.size()  <= 20){
                            LibraryTbl libraryTbl = new LibraryTbl();
                            libraryTbl.setContent(content);
                            libraryTbl.setTitle(title);
                            libraryTbl.setAuthor("Rezky");
                            Facade.getInstance().getManageLibraryTbl().add(libraryTbl);
                        }else{
                            Snackbar.make(binding.containerBody, R.string.sorryyoucannotaddnewlibrary,Snackbar.LENGTH_LONG).show();

                        }

                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void showAlertDialogFinish(){
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Well done, you finished it"); //Set Alert dialog title here
        alert.setMessage("DO you want to load the words from library ?"); //Message here
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //You will get as string input data in this variable.
                // here we convert the input to a string and show in a toast.


            } // End of onClick(DialogInterface dialog, int whichButton)
        }); //End of alert.setPositiveButton
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
                dialog.cancel();

            }
        }); //End of alert.setNegativeButton
        AlertDialog alertDialog = alert.create();
        alertDialog.show();
    }

    private void initRX(){
        RxBus.getInstance().observable(Words.class).subscribe(event -> {
            Timber.e("String RXBUS : "+new Gson().toJson(event));
            onEventListString(event.getStrings());
        });

        RxBus.getInstance().observable(LibraryTbl.class).subscribe(libraryTbl -> {
            Timber.e("LIBRARY RXBUS : "+new Gson().toJson(libraryTbl));
            onEventLibrary(libraryTbl);

        });
    }


    public void onEventListString(List<String> strings) {
        Timber.e("LIST OBSERVER STRING : "+new Gson().toJson(strings));
        mWords.clear();
        mWords.addAll(strings);

        initData(mWords);
    }

    public void onEventLibrary(LibraryTbl libraryTbl) {
        Timber.e("OBSERVER Speed REading : " + new Gson().toJson(libraryTbl));
        List<String> strings = Utils.getInstance().convertStringIntoList(libraryTbl.getContent());

        if (strings != null) {
            mWords.clear();
            mWords.addAll(strings);

            initData(mWords);
        }
    }

}

