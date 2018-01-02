package android.rezkyaulia.com.feo.controller.fragment.dialog;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.rezkyaulia.com.feo.R;
import android.rezkyaulia.com.feo.database.entity.UserTbl;
import android.rezkyaulia.com.feo.databinding.DialogEditAccountBinding;
import android.rezkyaulia.com.feo.databinding.DialogInputAnswerBinding;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Rezky Aulia Pratama on 11/20/2017.
 */

public class AccountDialogFragment extends DialogFragment {
    public final static String Dialog= "DIALOG";
    public final static int TARGET = 1;
    public final static String ARG1 = "arg1";

    DialogEditAccountBinding binding;

    private DialogListener mListener;
    private UserTbl mUserTbl;
    private String mWords;

    public static AccountDialogFragment newInstance(UserTbl userTbl){
        AccountDialogFragment dialogFragment = new AccountDialogFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG1, userTbl);
        dialogFragment.setArguments(args);
        return dialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUserTbl = (UserTbl) getArguments().getParcelable(ARG1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding =  DataBindingUtil.inflate(inflater, R.layout.dialog_edit_account,container,false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListener = (DialogListener) getTargetFragment();

        initView();
        initButton();

    }

    private void initView(){
        binding.editTextUsername.setText(mUserTbl.getUsername());
        binding.editTextEmail.setText(mUserTbl.getEmail());
    }


    private void initButton(){
        binding.buttonCancel.setOnClickListener(v -> {
            dismiss();
        });


        binding.buttonSave.setOnClickListener(v -> {
            mUserTbl.setUsername(binding.editTextUsername.getText().toString());
            mUserTbl.setEmail(binding.editTextEmail.getText().toString());

            mListener.onSave(mUserTbl);
            dismiss();
        });
    }

    public interface DialogListener {
        void onSave(UserTbl userTbl);
    }
}
