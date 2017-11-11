package android.rezkyaulia.com.feo.controller.fragment.dialog;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.rezkyaulia.com.feo.R;
import android.rezkyaulia.com.feo.databinding.DialogInputTextBinding;
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

    DialogInputTextBinding binding;

    private DialogListener mListener;

    public static InputTextDialogFragment newInstance(){
        InputTextDialogFragment dialogFragment = new InputTextDialogFragment();
        Bundle args = new Bundle();
        dialogFragment.setArguments(args);
        return dialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

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
        binding.buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String words = binding.edittextContent.getText().toString();
                mListener.onGetTextDialog(words);
                dismiss();
            }
        });
    }


    public interface DialogListener {
        void onGetTextDialog(String contents);
    }
}
