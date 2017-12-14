package android.rezkyaulia.com.feo.controller.fragment;

import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.rezkyaulia.com.feo.R;
import android.rezkyaulia.com.feo.controller.fragment.dialog.InputAnswerDialogFragment;
import android.rezkyaulia.com.feo.controller.fragment.dialog.InputTextDialogFragment;
import android.rezkyaulia.com.feo.controller.fragment.dialog.SpeedReadingSettingDialogFragment;
import android.rezkyaulia.com.feo.database.Facade;
import android.rezkyaulia.com.feo.database.ManageLibraryTbl;
import android.rezkyaulia.com.feo.database.entity.LibraryTbl;
import android.rezkyaulia.com.feo.database.entity.ScoreTbl;
import android.rezkyaulia.com.feo.databinding.FragmentSpeedReadingBinding;
import android.rezkyaulia.com.feo.model.ReadableObj;
import android.rezkyaulia.com.feo.utility.DimensionConverter;
import android.rezkyaulia.com.feo.utility.PreferencesManager;
import android.rezkyaulia.com.feo.handler.observer.RxBus;
import android.rezkyaulia.com.feo.utility.Utils;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;


import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

import static java.lang.Thread.sleep;

/**
 * Created by Rezky Aulia Pratama on 10/25/2017.
 */

public class SpeedReadingFragment extends BaseFragment implements SpeedReadingSettingDialogFragment.DialogListener
                                                                    ,InputTextDialogFragment.DialogListener
                                                                    ,InputAnswerDialogFragment.DialogListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARGS1 = "ARG1";
    private static final String ARGS2 = "ARG2";
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
    private boolean mIsQuiz = false;
    private String mGuid = "";
    private OnFragmentListener mListener;
    
    private Context mContext;
    private FragmentManager fragmentManager;

    private Disposable disposableString;
    private Disposable disposableLibraryTbl;

    private Thread mThreadPlay;

    public static SpeedReadingFragment newInstance(String guid,boolean b) {
        SpeedReadingFragment fragment = new SpeedReadingFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARGS1, b);
        args.putString(ARGS2, guid);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
        Timber.e("ON CREATE");

        if (getArguments() != null) {
            mIsQuiz = getArguments().getBoolean(ARGS1);
            mGuid= getArguments().getString(ARGS2);
        }

        fragmentManager = getFragmentManager();
        mContext = getContext();

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
           /* int test = savedInstanceState.getInt("tes", 0);
            Timber.e("savedinstancestate value : "+test);*/
        }



        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        initRX();

        initButton();

        setViewBasedOnSetting();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add_text) {
            Timber.e("onoption add text");
            showDialogInputText(null);
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
//        outState.putInt("tes", 3);
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
    public void onGetTextDialog(LibraryTbl libraryTbl,boolean b) {
        saveIntoLibrary(libraryTbl);
        if (libraryTbl.getContent() != null){
            if (libraryTbl.getContent().length()>0){
                mWords.clear();
                mWords.addAll(Utils.getInstance().convertStringIntoList(libraryTbl.getContent()));
                initData(mWords);
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentListener) {
            mListener = (OnFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        killThread();
        if (disposableLibraryTbl != null){
            Timber.e("disposableLibraryTbl != null");
            if (!disposableLibraryTbl.isDisposed()) {
                Timber.e("!disposableLibraryTbl.isDisposed()");

                disposableLibraryTbl.dispose();
            }
        }
    }

    private void killThread(){
        if (mThreadPlay != null){
            Timber.e("mThreadPlay != null");
            if (mThreadPlay.isAlive()){
                Timber.e("mThreadPlay.isAlive()");
                mThreadPlay.interrupt();
                mThreadPlay = null;
            }

        }
    }


    private void initButton(){
        binding.contentSpeedReading.btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.contentSpeedReading.textviewContent.setVisibility(View.VISIBLE);
                if (mWords.size()>0) {
                    initToggle(!mStart);

                    if (!mStart){
                        if (mIsQuiz && mIndex != 0)
                            showDialogInputAnswer();
                    }
                }else
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
            drawable = mContext.getResources().getDrawable(R.drawable.ic_icon_pause_round);
            binding.contentSpeedReading.layoutSetting.setEnabled(false);
        }else{
            drawable = mContext.getResources().getDrawable(R.drawable.ic_icon_play);
            binding.contentSpeedReading.layoutSetting.setEnabled(true);

            killThread();

        }
        binding.contentSpeedReading.lottieviewCheck.setVisibility(View.GONE);
        binding.contentSpeedReading.btnPlay.setImageDrawable(drawable);
        playWords();
    }

    private void initData(List<String> words){
        initToggle(false);

        if (words != null){
            if (words.size() > 0){
//                Timber.e("initdata word.Size : "+words.size()+" | mNol : "+mNol+" | mGS : "+mGs);
                mReadableWords.clear();
                int pointer = 0;
                while(pointer<words.size()){
                    String temp = "";
                    int lenght=0;

                    for (int i = 0; i<mNol ; i++) {
//                        Timber.e("For mNol");
                        for (int j = 0; j < mGs; j++) {
//                            Timber.e("for mGS");
                            if (pointer < words.size()) {
                                temp = temp + " " + words.get(pointer);
                            }else{
                                break;
                            }
//                            Timber.e("pointer : "+pointer);
                            pointer++;
                            lenght++;

                        }
                        temp = temp + "\n";
                        if (pointer >= words.size()) {
                            break;
                        }

                    }
                    mReadableWords.add(new ReadableObj(temp,lenght));
//                    Timber.e("==============================================================");
                }

//                Timber.e("start index : "+mIndex+" | MDIVIDED : "+mDivided);

                int dimension = mGs * mNol;
                int divided = mDivided / dimension;
                mIndex = divided;
                mDivided=divided*dimension;

//                Timber.e("start divided : "+divided+" | dimension : "+dimension+" | mIndex : "+mIndex+" | MDIVIDED : "+mDivided);

                if (mIndex>=words.size())
                    mIndex = 0;

                if (mReadableWords.size()>0)
                    binding.contentSpeedReading.textviewContent.setText(mReadableWords.get(mIndex).getWord());


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
//            Timber.e("mwords != null");
            if (mReadableWords.size() > 0){
//                Timber.e("mReadableWords.Size : "+mReadableWords.size());

//                Timber.e("mStart : "+mStart);
                mThreadPlay = new Thread() {
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

//                                    Timber.e("mIndex : "+mIndex+" | word : "+word+" | lenght : "+length);
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            binding.contentSpeedReading.textviewContent.setText(word);
                                        }
                                    });


//                                    Timber.e("WPM Milis : "+wpmMilis*length);
//                                    Timber.e("===================================================================================================");
                                    Thread.sleep(wpmMilis*length);
                                    mIndex++;
                                    Timber.e("MINDEX ++ : "+mIndex+" | mReadableWords.size():"+mReadableWords.size());

                                    if (mIndex>=mReadableWords.size()){
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
                            }/*else{
                                if (mIndex>0)
                                    mIndex--;
                            }
*/
                        } catch (InterruptedException e) {
                            Timber.e("ERROR : "+e.getMessage());
                        }catch (Exception e){
                            Timber.e("ERROR : "+e.getMessage());
                        }
                    }
                };

                mThreadPlay.start();

            }
        }
    }

    private void nextWords(boolean isNext){
        int dimension = mGs * mNol;
        binding.contentSpeedReading.textviewContent.setVisibility(View.VISIBLE);
        binding.contentSpeedReading.lottieviewCheck.setVisibility(View.GONE);

        if (isNext){
           if (mReadableWords != null){
               if (mReadableWords.size()>0){
                   mIndex++;
                   if (mIndex>=mReadableWords.size()){
                       mIndex=0;
                   }



                   final String tempWord = mReadableWords.get(mIndex).getWord();
                   final int lenght = mReadableWords.get(mIndex).getLenght();

//                   Timber.e("start mDivided : "+mDivided);

                   mDivided = (mIndex * dimension)+lenght;

                   if (mDivided>mWords.size()){
                       mDivided = 0;
                   }

//                   Timber.e("end mDivided : "+mDivided);

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

//                    Timber.e("start mDivided : "+mDivided);

                    mDivided = (mIndex * dimension)+lenght;

                    if (mDivided>mWords.size()){
                        mDivided = 0;
                    }

//                    Timber.e("end mDivided : "+mDivided);

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
        SpeedReadingSettingDialogFragment settingDialogFragment = SpeedReadingSettingDialogFragment.newInstance(mIsQuiz);
        settingDialogFragment.setStyle( DialogFragment.STYLE_NORMAL, R.style.dialog );
        settingDialogFragment.setTargetFragment(this,settingDialogFragment.TARGET);
        settingDialogFragment.show(fragmentManager.beginTransaction(),SpeedReadingSettingDialogFragment.Dialog);

    }

    private void showDialogInputText(LibraryTbl libraryTbl){
        InputTextDialogFragment inputTextDialog = InputTextDialogFragment.newInstance(libraryTbl,true);
        inputTextDialog.setStyle( DialogFragment.STYLE_NORMAL, R.style.dialog_light );
        inputTextDialog.setTargetFragment(this,inputTextDialog.TARGET);
        inputTextDialog.show(fragmentManager.beginTransaction(), InputTextDialogFragment.Dialog);
    }

    private void showDialogInputAnswer(){
        InputAnswerDialogFragment inputAnswerDialog = InputAnswerDialogFragment.newInstance();
        inputAnswerDialog.setCancelable(false);
        inputAnswerDialog.setStyle( DialogFragment.STYLE_NORMAL, R.style.dialog_light );
        inputAnswerDialog.setTargetFragment(this,inputAnswerDialog.TARGET);
        inputAnswerDialog.show(fragmentManager.beginTransaction(), InputAnswerDialogFragment.Dialog);
    }

    private void initPref(){
        mWpm = PreferencesManager.getInstance().getWPM();
        if (!mIsQuiz){
            mNol = PreferencesManager.getInstance().getNOL();
            mGs = PreferencesManager.getInstance().getGS();
        }else{
            mNol = 1;
            mGs = 1;
        }


        binding.contentSpeedReading.textViewWpm.setText(String.format("WPM : %s",mWpm));
        binding.contentSpeedReading.textViewNol.setText(String.format("Number of lines : %s",mNol));
        binding.contentSpeedReading.textViewGs.setText(String.format("Group size : %s",mGs));
    }

    private void saveIntoLibrary(LibraryTbl libraryTbl){
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext)
                .setMessage(R.string.doyouwanttosaveitintolibrary)

                .setPositiveButton(R.string.yes, (dialogInterface, i) -> {
                    ManageLibraryTbl manageLibraryTbl = Facade.getInstance().getManageLibraryTbl();

                    if (manageLibraryTbl.size()  <= 20){
                        Timber.e("manageLibraryTbl.size()  <= 20");
                        Facade.getInstance().getManageLibraryTbl().add(libraryTbl);

                    }else{
                        Snackbar.make(binding.containerBody, R.string.sorryyoucannotaddnewlibrary,Snackbar.LENGTH_LONG).show();

                    }

                })
                .setNegativeButton(R.string.no, (dialog, which) -> dialog.cancel());

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void showAlertDialogFinish(){
        AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
        alert.setTitle("Well done, you finished it"); //Set Alert dialog title here
        alert.setMessage("DO you want to load the words from library ?"); //Message here
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //You will get as string input data in this variable.
                // here we convert the input to a string and show in a toast.
                mListener.onFinishInteraction();

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

    private Disposable disposable;
    private void initRX(){

        disposableString = RxBus.getInstance().observable(String.class).subscribe(s -> {
            Timber.e("String RXBUS : "+new Gson().toJson(s));
            onEventListString(s);
        });

        disposableLibraryTbl = RxBus.getInstance().observable(LibraryTbl.class)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(libraryTbl -> {
                    Timber.e("LIBRARY RXBUS : "+new Gson().toJson(libraryTbl));
                    onEventLibrary(libraryTbl);
                });
    }


    public void onEventListString(String s) {
        Timber.e("LIST OBSERVER STRING : "+new Gson().toJson(s));
        LibraryTbl libraryTbl = new LibraryTbl();
        libraryTbl.setContent(s);
        showDialogInputText(libraryTbl);
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

    @Override
    public void onGetAnswerDialog(String answer) {
        Timber.e("onGetANswerDialog :"+mIndex+ " | mReadableWords:"+mReadableWords.size());
        if (mIndex > 0 && mIndex < mReadableWords.size()){
            String correctAns = mReadableWords.get(mIndex-1).getWord().trim();
            Timber.e("mIndex > 0 && mIndex < mReadableWords.size()");
            if (answer != null){
                Timber.e("correctAns :"+correctAns.toLowerCase()+" | ans :"+answer.trim().toLowerCase());
                if (answer.trim().toLowerCase().equals(correctAns.toLowerCase())){
                    checkAnswer(true,correctAns,answer);
                }else{
                    checkAnswer(false,correctAns,answer);
                }
            }else{
                checkAnswer(false,correctAns,answer);
            }
        }
    }

    private void checkAnswer(boolean b,String correctAnswer, String answer){
        Timber.e("CHECK ANSWER : "+b);
        ScoreTbl scoreTbl = new ScoreTbl();
        scoreTbl.setUserId(userTbl.getUserId());
        scoreTbl.setGuid(mGuid);
        scoreTbl.setAnswer(answer);
        scoreTbl.setCorrectAnswer(correctAnswer);
        scoreTbl.setCreatedDate(Utils.getInstance().time().getDateTimeString());
        if (b){
            scoreTbl.setScore(pref.getWPM());
            binding.contentSpeedReading.lottieviewCheck.setAnimation("animation/check.json");
        }else{
            scoreTbl.setScore(0);
            binding.contentSpeedReading.lottieviewCheck.setAnimation("animation/x_pop.json");

        }

        binding.contentSpeedReading.textviewContent.setVisibility(View.GONE);
        binding.contentSpeedReading.lottieviewCheck.setVisibility(View.VISIBLE);
        binding.contentSpeedReading.lottieviewCheck.loop(false);
        binding.contentSpeedReading.lottieviewCheck.playAnimation();
        Facade.getInstance().getManageScoreTbl().add(scoreTbl);
    }

    private void setViewBasedOnSetting(){
        if (PreferencesManager.getInstance().isBlack()){
            binding.contentSpeedReading.layoutBody.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.colorBlack_1000));
            binding.contentSpeedReading.textviewContent.setTextColor(ContextCompat.getColor(getContext(),R.color.colorWhite));

            binding.contentSpeedReading.btnPlay.setColorFilter(ContextCompat.getColor(getContext(),R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);
            binding.contentSpeedReading.btnPrevious.setColorFilter(ContextCompat.getColor(getContext(),R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);
            binding.contentSpeedReading.btnNext.setColorFilter(ContextCompat.getColor(getContext(),R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);
        }else{
            binding.contentSpeedReading.layoutBody.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.colorWhite));
            binding.contentSpeedReading.textviewContent.setTextColor(ContextCompat.getColor(getContext(),R.color.colorBlack_1000));

            binding.contentSpeedReading.btnPlay.setColorFilter(ContextCompat.getColor(getContext(),R.color.colorBlack_1000), PorterDuff.Mode.SRC_ATOP);
            binding.contentSpeedReading.btnPrevious.setColorFilter(ContextCompat.getColor(getContext(),R.color.colorBlack_1000), PorterDuff.Mode.SRC_ATOP);
            binding.contentSpeedReading.btnNext.setColorFilter(ContextCompat.getColor(getContext(),R.color.colorBlack_1000), PorterDuff.Mode.SRC_ATOP);
        }

        int spFont = DimensionConverter.getInstance().stringToDimensionPixelSize(((int)PreferencesManager.getInstance().getFontSize())+"sp", getContext().getResources().getDisplayMetrics());
        Timber.e("PreferencesManager.getInstance().getFontSize() : "+PreferencesManager.getInstance().getFontSize()+" | spFont : "+spFont);

        binding.contentSpeedReading.textviewContent.setTextSize(spFont);

    }

    public interface OnFragmentListener {
        void onFinishInteraction();
    }
}

