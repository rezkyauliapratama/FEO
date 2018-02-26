package android.rezkyaulia.com.feo.controller.activity;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.rezkyaulia.com.feo.R;
import android.rezkyaulia.com.feo.controller.fragment.BaseFragment;
import android.rezkyaulia.com.feo.controller.fragment.LibraryDetailFragment;
import android.rezkyaulia.com.feo.controller.fragment.LibraryFragment;
import android.rezkyaulia.com.feo.controller.fragment.SpeedReadingFragment;
import android.rezkyaulia.com.feo.controller.fragment.dialog.GuideFeoChampDialogFragment;
import android.rezkyaulia.com.feo.controller.fragment.dialog.GuideFeoDialogFragment;
import android.rezkyaulia.com.feo.controller.fragment.dialog.SpeedReadingSettingDialogFragment;
import android.rezkyaulia.com.feo.database.Facade;
import android.rezkyaulia.com.feo.database.entity.LibraryTbl;
import android.rezkyaulia.com.feo.database.entity.ScoreTbl;
import android.rezkyaulia.com.feo.databinding.ActivitySpeedReadingBinding;
import android.rezkyaulia.com.feo.handler.api.ApiClient;
import android.rezkyaulia.com.feo.handler.api.LibraryApi;
import android.rezkyaulia.com.feo.handler.observer.RxBus;
import android.rezkyaulia.com.feo.model.Events;
import android.rezkyaulia.com.feo.utility.HttpResponse;
import android.rezkyaulia.com.feo.utility.Utils;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.app.infideap.stylishwidget.view.ATextView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * Created by Rezky Aulia Pratama on 10/18/2017.
 */

public class SpeedReadingActivity extends BaseActivity implements
        LibraryFragment.OnListFragmentInteractionListener,
        SpeedReadingFragment.OnFragmentListener,
        LibraryDetailFragment.OnFragmentListener {

    static final int READ_REQ = 1;
    public static final String ARGS1 = "ARGS1";

    ActivitySpeedReadingBinding binding;
    List<BaseFragment> fragments;
    BaseFragment fragment;
    LibraryDetailFragment libraryDetailFragment;
    Menu menu;

    private String mGuid;
    LfPagerAdapter adapter;
    boolean mIsQuiz = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_speed_reading);
        fragments = new ArrayList<>();
        fragment = new BaseFragment();

        setSupportActionBar(binding.toolbar);

        if (getIntent() != null){
            mIsQuiz = getIntent().getBooleanExtra(ARGS1, false);
            Timber.e("GET INTENT != null : "+mIsQuiz);

        }

        if (mIsQuiz){
            binding.actionbarTitle.setText(R.string.speedreadingfeo);
        }
        mGuid = Utils.getInstance().getUniqueID(this);

        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        initViewPager();
        initTab();

        libraryDetailFragment = LibraryDetailFragment.newInstance();
        displayFragment(binding.navView.getId(),libraryDetailFragment);

        binding.imageViewHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        initLibrary();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_speed_reading, menu);
        this.menu = menu;

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar rewardTbl clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == R.id.action_add_file) {
            /*Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("application/pdf|text*//*");
*/
            String[] mimeTypes =
                    {"application/msword","application/vnd.openxmlformats-officedocument.wordprocessingml.document", // .doc & .docx
                            "application/vnd.ms-powerpoint","application/vnd.openxmlformats-officedocument.presentationml.presentation", // .ppt & .pptx
                            "application/vnd.ms-excel","application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", // .xls & .xlsx
                            "text/plain",
                            "application/pdf",
                            "application/zip"};

            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                intent.setType(mimeTypes.length == 1 ? mimeTypes[0] : "*/*");
                if (mimeTypes.length > 0) {
                    intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
                }
            } else {
                String mimeTypesStr = "";
                for (String mimeType : mimeTypes) {
                    mimeTypesStr += mimeType + "|";
                }
                intent.setType(mimeTypesStr.substring(0,mimeTypesStr.length() - 1));
            }
            startActivityForResult(intent, READ_REQ);
        }else if (id == R.id.action_help){
            if (mIsQuiz){
                showDialogGuideFeoChamp();
            }else{
                showDialogGuideFeo();
            }
        }

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {

            Uri uri = null;
            if (data != null) {
                uri = data.getData();

            }

            if(requestCode == READ_REQ){
                Timber.e("OnActivityResult");
                String words = Utils.getInstance().readTextFile(this,uri);


                if (fragments.get(0) != null){
                    ((SpeedReadingFragment)fragments.get(0)).onEventListString(words);
                }

            }
        }
    }

    @Override
    public void onListFragmentInteraction(LibraryTbl libraryTbl) {
//     showAlertDialogLibrary(libraryTbl);
        binding.drawerLayout.openDrawer(binding.navView);
        if (libraryDetailFragment != null)
            libraryDetailFragment.onInitData(libraryTbl);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Facade.getInstance().getDaoSession().clear();
    /*    RefWatcher refWatcher = BaseApplication.getRefWatcher(this);
        refWatcher.watch(this);*/
    }


    @Override
    public void onFinishInteraction() {
        TabLayout.Tab tab = binding.content.tabLayout.getTabAt(1);
        tab.select();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ArrayList <ScoreTbl> mScoreTbls = new ArrayList<>();
        mScoreTbls.addAll(Facade.getInstance().getManageScoreTbl().getbyGuid(mGuid));

        if (mIsQuiz & mScoreTbls.size() > 0){
            Intent intent = new Intent(this,SummarySpeedReadingActivity.class);
            intent.putExtra(SummarySpeedReadingActivity.ARGS1,mGuid);
            intent.putParcelableArrayListExtra(SummarySpeedReadingActivity.ARGS2,mScoreTbls);
            startActivity(intent);
            finish();
        }

    }


    private void initTab(){
        TabLayout.Tab[] tabs = {
                binding.content.tabLayout.newTab().setText("Speed Reading"),
                binding.content.tabLayout.newTab().setText("Library")

        };

        for (TabLayout.Tab tab : tabs) {
            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
            layout.setWeightSum(1);
            ATextView newTab = new ATextView(this);
            newTab .setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
            newTab.setGravity(Gravity.CENTER );
            newTab.setMaxLines(1);
            newTab.setText(tab.getText());

            newTab.setTextColor(ContextCompat.getColor(this, (R.color.colorWhite)));

            layout.addView(newTab);

            tab.setCustomView(layout);
            binding.content.tabLayout.addTab(tab);
        }

        binding.content.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                fragment = fragments.get(tab.getPosition());
                binding.content.viewPager.setCurrentItem(tab.getPosition());

                if (fragment instanceof SpeedReadingFragment){
                    binding.actionbarTitle.setText(getString(R.string.speedreading));
                }else{
                    binding.actionbarTitle.setText(getString(R.string.library));
                    fragment.update();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initViewPager(){
        Timber.e("INITVIEWPAGER");
        fragments.add(SpeedReadingFragment.newInstance(mGuid,mIsQuiz));
        fragments.add(LibraryFragment.newInstance());

        fragment = fragments.get(0);
        this.adapter = new LfPagerAdapter(getSupportFragmentManager());

        binding.content.viewPager.setAdapter(adapter);
        binding.content.viewPager.setPagingEnabled(false);
        binding.content.viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.content.tabLayout));
    }

    private void showAlertDialogLibrary(final LibraryTbl libraryTbl){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Library"); //Set Alert dialog title here
        alert.setMessage("Do you want to load this library ?"); //Message here
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //You will get as string input data in this variable.
                // here we convert the input to a string and show in a toast.
                Timber.e("LIBRARY TBL : "+new Gson().toJson(libraryTbl));
                RxBus.getInstance().post(libraryTbl);
                binding.content.viewPager.setCurrentItem(0,true);

                binding.drawerLayout.closeDrawer(binding.navView);
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

    private void showAlertDialogDeleteLibrary(final LibraryTbl libraryTbl){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Library"); //Set Alert dialog title here
        alert.setMessage("Do you want to delete this library ?"); //Message here
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //You will get as string input data in this variable.
                // here we convert the input to a string and show in a toast.
                binding.layoutProgress.setVisibility(View.VISIBLE);
                if (libraryTbl.getLibraryId() != null){
                    ApiClient.getInstance().library().delete(libraryTbl, new ParsedRequestListener<LibraryApi.Response>() {
                        @Override
                        public void onResponse(LibraryApi.Response response) {
                            if (HttpResponse.getInstance().success(response)){
                                Facade.getInstance().getManageLibraryTbl().remove(libraryTbl);
                                Toast.makeText(SpeedReadingActivity.this,"Delete successful",Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(SpeedReadingActivity.this,"Cannot delete this library, please check your network",Toast.LENGTH_LONG).show();
                            }
                            binding.layoutProgress.setVisibility(View.GONE);
                            RxBus.getInstance().post(new Events<>(LibraryTbl.class));
                        }

                        @Override
                        public void onError(ANError anError) {
                            Timber.e("ONERROR LIB DEL: "+new Gson().toJson(anError));
                            RxBus.getInstance().post(new Events<>(LibraryTbl.class));
                            Toast.makeText(SpeedReadingActivity.this,"Cannot delete this library, please check your network",Toast.LENGTH_LONG).show();
                            binding.layoutProgress.setVisibility(View.GONE);
                        }
                    });
                }else{
                    Facade.getInstance().getManageLibraryTbl().remove(libraryTbl);
                    RxBus.getInstance().post(new Events<>(LibraryTbl.class));
                    binding.layoutProgress.setVisibility(View.GONE);
                    Toast.makeText(SpeedReadingActivity.this,"Delete successful",Toast.LENGTH_LONG).show();
                }

                Timber.e("onDeleteLibraryInteraction");

                binding.drawerLayout.closeDrawer(binding.navView);
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

    @Override
    public void onSpeedReadingInteraction(LibraryTbl libraryTbl) {
        showAlertDialogLibrary(libraryTbl);

    }

    @Override
    public void onEditLibraryInteraction(LibraryTbl libraryTbl) {
        if (libraryDetailFragment != null){
            libraryDetailFragment.showDialogInputText(libraryTbl);
            binding.drawerLayout.closeDrawer(binding.navView);
        }
    }

    @Override
    public void onDeleteLibraryInteraction(LibraryTbl libraryTbl) {
        if (libraryTbl.getUserId() == userTbl.getUserId())
            showAlertDialogDeleteLibrary(libraryTbl);
        else
            Toast.makeText(this, R.string.sorry_cannot_delete_admin_library,Toast.LENGTH_LONG).show();

    }

    @Override
    public void onClose() {
        binding.drawerLayout.closeDrawer(binding.navView);
    }

    private void initLibrary(){
        ApiClient.getInstance().library().getAll(new ParsedRequestListener<LibraryApi.Response>() {
            @Override
            public void onResponse(LibraryApi.Response response) {
                Timber.e("initlib : "+new Gson().toJson(response));

                if (HttpResponse.getInstance().success(response)){
                    if (response.getApiList() != null){
                        List <LibraryTbl> temp = facade.getManageLibraryTbl().updateCurrent(response.getApiList());
                        Timber.e("temp size : "+temp.size());
                        if (temp.size()>0){
                            RxBus.getInstance().post(temp.toArray());
                        }
                    }
                }
            }

            @Override
            public void onError(ANError anError) {
                Timber.e("Initlib ANError : "+new Gson().toJson(anError));
            }
        });
    }


    private void showDialogGuideFeo(){
        GuideFeoDialogFragment guideFeoDialogFragment = GuideFeoDialogFragment.newInstance();
        guideFeoDialogFragment.setStyle( DialogFragment.STYLE_NORMAL, R.style.dialog );
        guideFeoDialogFragment.show(getSupportFragmentManager().beginTransaction(), SpeedReadingSettingDialogFragment.Dialog);

    }

    private void showDialogGuideFeoChamp(){
        GuideFeoChampDialogFragment guideFeoChampDialogFragment = GuideFeoChampDialogFragment.newInstance();
        guideFeoChampDialogFragment.setStyle( DialogFragment.STYLE_NORMAL, R.style.dialog );
        guideFeoChampDialogFragment.show(getSupportFragmentManager().beginTransaction(),SpeedReadingSettingDialogFragment.Dialog);

    }
    protected class LfPagerAdapter extends FragmentStatePagerAdapter {

        private static final int NUM_ITEMS = 2;

        private FragmentManager fragmentManager;

        public LfPagerAdapter(FragmentManager fm) {
            super(fm);
            this.fragmentManager = fm;
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 1:
                    return fragments.get(1);
                default:
                    return fragments.get(0);
            }
        }
    }

}

