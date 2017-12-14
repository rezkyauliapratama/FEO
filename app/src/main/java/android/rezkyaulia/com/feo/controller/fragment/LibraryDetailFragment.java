package android.rezkyaulia.com.feo.controller.fragment;

import android.app.DialogFragment;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.rezkyaulia.com.feo.R;
import android.rezkyaulia.com.feo.controller.fragment.dialog.InputTextDialogFragment;
import android.rezkyaulia.com.feo.database.Facade;
import android.rezkyaulia.com.feo.database.ManageLibraryTbl;
import android.rezkyaulia.com.feo.database.entity.LibraryTbl;
import android.rezkyaulia.com.feo.databinding.FragmentLibraryDetailBinding;
import android.rezkyaulia.com.feo.utility.PreferencesManager;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import timber.log.Timber;

/**
 * Created by Rezky Aulia Pratama on 11/27/2017.
 */

public class LibraryDetailFragment extends BaseFragment implements InputTextDialogFragment.DialogListener {

    FragmentLibraryDetailBinding binding;
    OnFragmentListener mListener;

    LibraryTbl mLibraryTbl;
    public static LibraryDetailFragment newInstance() {
        LibraryDetailFragment fragment = new LibraryDetailFragment();
        Bundle args = new Bundle();
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

        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding =  DataBindingUtil.inflate(inflater, R.layout.fragment_library_detail,container,false);

        if(savedInstanceState != null){
            Timber.e("SAVEDINSTACESTATE");
        }
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
            initButton();
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

    public void onInitData(LibraryTbl libraryTbl){
        mLibraryTbl = libraryTbl;

        if (mLibraryTbl != null){
            binding.textViewContent.setText(mLibraryTbl.getContent());
            binding.textViewTitle.setText(mLibraryTbl.getTitle());
            binding.textViewAuthor.setText(mLibraryTbl.getAuthor());
            binding.textViewGenre.setText(mLibraryTbl.getGenre());
        }
    }

    private void initButton(){
        binding.buttonSpreeder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onSpeedReadingInteraction(mLibraryTbl);
            }
        });

        binding.imageViewEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onEditLibraryInteraction(mLibraryTbl);
            }
        });

        binding.imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onDeleteLibraryInteraction(mLibraryTbl);
            }
        });
    }

    public void showDialogInputText(LibraryTbl libraryTbl){
        InputTextDialogFragment inputTextDialog = InputTextDialogFragment.newInstance(libraryTbl,false);
        inputTextDialog.setStyle( DialogFragment.STYLE_NORMAL, R.style.dialog_light );
        inputTextDialog.setTargetFragment(this,inputTextDialog.TARGET);
        inputTextDialog.show(getFragmentManager().beginTransaction(), InputTextDialogFragment.Dialog);
    }

    private void saveIntoLibrary(LibraryTbl libraryTbl){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setMessage(R.string.doyouwanttosaveitintolibrary)

                .setPositiveButton(R.string.yes, (dialogInterface, i) -> {
                    ManageLibraryTbl manageLibraryTbl = Facade.getInstance().getManageLibraryTbl();

                    if (manageLibraryTbl.size()  <= 20){
                        Timber.e("manageLibraryTbl.size()  <= 20");
                        Facade.getInstance().getManageLibraryTbl().add(libraryTbl);

                    }else{
                        Snackbar.make(binding.scrollView, R.string.sorryyoucannotaddnewlibrary,Snackbar.LENGTH_LONG).show();

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

    @Override
    public void onGetTextDialog(LibraryTbl libraryTbl, boolean b) {
        saveIntoLibrary(libraryTbl);
    }

    public interface OnFragmentListener {
        void onSpeedReadingInteraction(LibraryTbl libraryTbl);
        void onEditLibraryInteraction(LibraryTbl libraryTbl);
        void onDeleteLibraryInteraction(LibraryTbl libraryTbl);
    }

}
