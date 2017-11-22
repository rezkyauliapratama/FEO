package android.rezkyaulia.com.feo.controller.fragment.dialog;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.rezkyaulia.com.feo.R;
import android.rezkyaulia.com.feo.databinding.DialogInputAnswerBinding;
import android.rezkyaulia.com.feo.databinding.DialogInputTextBinding;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Rezky Aulia Pratama on 11/20/2017.
 */

public class InputAnswerDialogFragment extends DialogFragment {
    public final static String Dialog= "DIALOG";
    public final static int TARGET = 1;
    public final static String ARG1 = "words";

    DialogInputAnswerBinding binding;

    private DialogListener mListener;

    private String mWords;

    public static InputAnswerDialogFragment newInstance(){
        InputAnswerDialogFragment dialogFragment = new InputAnswerDialogFragment();
        return dialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding =  DataBindingUtil.inflate(inflater, R.layout.dialog_input_answer,container,false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListener = (DialogListener) getTargetFragment();

        initButton();

    }

    private void initButton(){
        binding.buttonCancel.setOnClickListener(v -> {
            mListener.onGetAnswerDialog(null);
            dismiss();
        });

        binding.buttonAnswer.setOnClickListener(v -> {
            String s = binding.exittextDialog.getText().toString();
            if (!s.isEmpty()){
                mListener.onGetAnswerDialog(s);
                dismiss();
            }else{
                binding.exittextDialog.setError("Please fill the answer");
            }
        });
    }

    public interface DialogListener {
        void onGetAnswerDialog(String title);
    }
}
