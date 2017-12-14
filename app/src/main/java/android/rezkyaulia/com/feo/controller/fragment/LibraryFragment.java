package android.rezkyaulia.com.feo.controller.fragment;

import android.app.DialogFragment;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.rezkyaulia.com.feo.R;
import android.rezkyaulia.com.feo.controller.adapter.LibraryRVAdapter;
import android.rezkyaulia.com.feo.controller.fragment.dialog.InputTextDialogFragment;
import android.rezkyaulia.com.feo.database.Facade;
import android.rezkyaulia.com.feo.database.ManageLibraryTbl;
import android.rezkyaulia.com.feo.database.entity.LibraryTbl;
import android.rezkyaulia.com.feo.databinding.ContentDialogSettingViewBinding;
import android.rezkyaulia.com.feo.databinding.FragmentLibraryBinding;
import android.rezkyaulia.com.feo.handler.observer.RxBus;
import android.rezkyaulia.com.feo.model.Events;
import android.rezkyaulia.com.feo.utility.PreferencesManager;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * Created by Rezky Aulia Pratama on 11/11/2017.
 */

public class LibraryFragment extends BaseFragment implements InputTextDialogFragment.DialogListener {

    FragmentLibraryBinding binding;
    private GridLayoutManager mLayoutManager;
    private LibraryRVAdapter mAdapter;
    private List<LibraryTbl> mLibraryTbls;
    private OnListFragmentInteractionListener mListener;

    private LibraryTbl mSelectedLibrary;

    public static LibraryFragment newInstance() {
        LibraryFragment fragment = new LibraryFragment();
        /*Bundle args = new Bundle();
        args.putString(ARG_PARAM1, category);
        fragment.setArguments(args);*/
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mLibraryTbls = new ArrayList<>();
        mLibraryTbls = Facade.getInstance().getManageLibraryTbl().getAll();
        mSelectedLibrary = null;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding =  DataBindingUtil.inflate(inflater, R.layout.fragment_library,container,false);

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

        initView();
        initData();
        initRecyclerview();

        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
            if (binding.swipeRefreshLayout != null) {
                binding.swipeRefreshLayout.setRefreshing(true);
            }

            initData();
            binding.swipeRefreshLayout.setRefreshing(false);

            }
        });
        initRX();
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
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
    public void onResume() {
        super.onResume();
        Timber.e("ON RESUME");
    }


    @Override
    public void update() {
        super.update();
        initData();
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

    private void showDialogInputText(LibraryTbl libraryTbl){
        InputTextDialogFragment inputTextDialog = InputTextDialogFragment.newInstance(libraryTbl,false);
        inputTextDialog.setStyle( DialogFragment.STYLE_NORMAL, R.style.dialog_light );
        inputTextDialog.setTargetFragment(this,inputTextDialog.TARGET);
        inputTextDialog.show(getFragmentManager().beginTransaction(), InputTextDialogFragment.Dialog);
    }


    private void initRecyclerview(){
        mLayoutManager = new GridLayoutManager(getContext(),2);
        binding.recyclerView.setLayoutManager(mLayoutManager);
        if (mLibraryTbls != null){
            mAdapter = new LibraryRVAdapter(getContext(),mLibraryTbls,mListener);
            binding.recyclerView.setAdapter(mAdapter);
        }

    }



    private void initRX(){
        RxBus.getInstance().observable(LibraryTbl.class).subscribe(libraryTbl -> {
           onEventLibrary(libraryTbl);
        });

        RxBus.getInstance().observable(new Events<>(LibraryTbl.class).getClass()).subscribe(events->{
            if (events.getTypeParameterClass() == LibraryTbl.class){
                initData();
            }
        });


    }

    public void onEventLibrary(LibraryTbl libraryTbl) {
        Timber.e("OBSERVER LIB : "+new Gson().toJson(libraryTbl));
        mSelectedLibrary = libraryTbl;

        if (mLibraryTbls != null){
            if (mLibraryTbls.size() > 0){
                for (LibraryTbl item : mLibraryTbls){
                    if (item.getId() == mSelectedLibrary.getId()){
                        item.setReadFlag(1);
                    }else{
                        item.setReadFlag(0);
                    }
                }
            }
        }
        if (mAdapter != null){
            mAdapter.notifyDataSetChanged();
        }
    }


    private void initData(){
        List<LibraryTbl> libraryTbls = Facade.getInstance().getManageLibraryTbl().getAll();
        if (mLibraryTbls != null){
            mLibraryTbls.clear();
            mLibraryTbls.addAll(libraryTbls);
            Timber.e("mLibraryTbls : "+new Gson().toJson(mLibraryTbls));
        }

        if (mAdapter != null)
            mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onGetTextDialog(LibraryTbl libraryTbl, boolean b) {
        saveIntoLibrary(libraryTbl);
    }

    private void saveIntoLibrary(LibraryTbl libraryTbl){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setMessage(R.string.doyouwanttosaveitintolibrary)

                .setPositiveButton(R.string.yes, (dialogInterface, i) -> {
                    ManageLibraryTbl manageLibraryTbl = Facade.getInstance().getManageLibraryTbl();

                    if (manageLibraryTbl.size()  <= 20){
                        Timber.e("manageLibraryTbl.size()  <= 20");
                        Facade.getInstance().getManageLibraryTbl().add(libraryTbl);
                        initData();
                    }else{
                        Snackbar.make(binding.recyclerView, R.string.sorryyoucannotaddnewlibrary,Snackbar.LENGTH_LONG).show();

                    }

                })
                .setNegativeButton(R.string.no, (dialog, which) -> dialog.cancel());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    void initView(){
        if (PreferencesManager.getInstance().isBlack()){
            binding.layoutBody.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.colorBlack_1000));
        }else{
            binding.layoutBody.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.colorWhite));

        }
    }
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name

        void onListFragmentInteraction(LibraryTbl libraryTbl);
    }

}
