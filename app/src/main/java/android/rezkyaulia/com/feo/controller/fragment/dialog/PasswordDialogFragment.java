package android.rezkyaulia.com.feo.controller.fragment.dialog;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.rezkyaulia.com.feo.R;
import android.rezkyaulia.com.feo.database.entity.UserTbl;
import android.rezkyaulia.com.feo.databinding.DialogEditInformationBinding;
import android.rezkyaulia.com.feo.databinding.DialogEditPasswordBinding;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by Rezky Aulia Pratama on 11/20/2017.
 */

public class PasswordDialogFragment extends DialogFragment {
    public final static String Dialog= "DIALOG";
    public final static int TARGET = 1;
    public final static String ARG1 = "arg1";

    DialogEditPasswordBinding binding;

    private DialogListener mListener;
    private UserTbl mUserTbl;
    private String mWords;

    public static PasswordDialogFragment newInstance(UserTbl userTbl){
        PasswordDialogFragment dialogFragment = new PasswordDialogFragment();
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
        binding =  DataBindingUtil.inflate(inflater, R.layout.dialog_edit_password,container,false);
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
            dismiss();
        });


        binding.buttonSave.setOnClickListener(v -> {
            String password = binding.editTextPassword.getText().toString();
            String rePassword = binding.editTextRetypePassword.getText().toString();

            boolean b = true;

            if (password.length() > 0){
                b = true;
            }else{
                b = false;
                binding.editTextPassword.setError(getString(R.string.please_fill_in_here));
            }

            if (rePassword.length() > 0){
                b = true;
            }else{
                b = false;
                binding.editTextRetypePassword.setError(getString(R.string.please_fill_in_here));
            }
            if (b){
                if (password.equals(rePassword)){
                    mUserTbl.setPassword(password);
                    mListener.onSave(mUserTbl);
                    dismiss();
                }else{
                    Toast.makeText(getContext(),"Password and retype password is not match",Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    public interface DialogListener {
        void onSave(UserTbl userTbl);
    }
}
