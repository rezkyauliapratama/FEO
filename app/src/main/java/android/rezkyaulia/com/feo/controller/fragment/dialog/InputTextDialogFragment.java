package android.rezkyaulia.com.feo.controller.fragment.dialog;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.rezkyaulia.com.feo.R;
import android.rezkyaulia.com.feo.database.entity.LibraryTbl;
import android.rezkyaulia.com.feo.databinding.DialogInputTextBinding;
import android.rezkyaulia.com.feo.utility.AdjustingViewGlobalLayoutListener;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * Created by Rezky Aulia Pratama on 11/9/2017.
 */

public class InputTextDialogFragment extends DialogFragment {
    public final static String Dialog= "DIALOG";
    public final static int TARGET = 1;
    public final static String ARG1 = "words";
    public final static String ARG2 = "issave";

    DialogInputTextBinding binding;

    private DialogListener mListener;

    private LibraryTbl mLibraryTbl;

    private boolean isSave;

    public static InputTextDialogFragment newInstance(LibraryTbl libraryTbl, boolean b){
        InputTextDialogFragment dialogFragment = new InputTextDialogFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG1, libraryTbl);
        args.putBoolean(ARG2, b);
        dialogFragment.setArguments(args);
        return dialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mLibraryTbl = (LibraryTbl) getArguments().getParcelable(ARG1);
            isSave = getArguments().getBoolean(ARG2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding =  DataBindingUtil.inflate(inflater, R.layout.dialog_input_text,container,false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListener = (DialogListener) getTargetFragment();

        if (mLibraryTbl != null){
            if (mLibraryTbl.getContent() != null)
                binding.edittextContent.setText(mLibraryTbl.getContent());

            if (mLibraryTbl.getTitle() != null)
                binding.edittextTitle.setText(mLibraryTbl.getTitle());

            if (mLibraryTbl.getAuthor() != null)
                binding.edittextAuthor.setText(mLibraryTbl.getAuthor());

            if (mLibraryTbl.getGenre() != null)
                binding.edittextAuthor.setText(mLibraryTbl.getGenre());

        }

        binding.buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = binding.edittextTitle.getText().toString();
                String words = binding.edittextContent.getText().toString();
                String genre = binding.edittextGenre.getText().toString();
                String author = binding.edittextAuthor.getText().toString();

                if (mLibraryTbl == null){
                    mLibraryTbl = new LibraryTbl();
                }
                mLibraryTbl.setContent(words);
                mLibraryTbl.setTitle(title);
                mLibraryTbl.setGenre(genre);
                mLibraryTbl.setAuthor(author);

                mListener.onGetTextDialog(mLibraryTbl, isSave);
                dismiss();
            }
        });

        binding
                .buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        /*AdjustingViewGlobalLayoutListener listen = new AdjustingViewGlobalLayoutListener(binding.edittextContent);
        binding.layoutButton.getViewTreeObserver().addOnGlobalLayoutListener(listen);*/
    }


    public interface DialogListener {
        void onGetTextDialog(LibraryTbl libraryTbl, boolean b);
    }
}
