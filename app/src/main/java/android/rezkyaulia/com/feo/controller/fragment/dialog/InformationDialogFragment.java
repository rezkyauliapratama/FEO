package android.rezkyaulia.com.feo.controller.fragment.dialog;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.rezkyaulia.com.feo.R;
import android.rezkyaulia.com.feo.database.entity.UserTbl;
import android.rezkyaulia.com.feo.databinding.DialogEditAccountBinding;
import android.rezkyaulia.com.feo.databinding.DialogEditInformationBinding;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Rezky Aulia Pratama on 11/20/2017.
 */

public class InformationDialogFragment extends DialogFragment {
    public final static String Dialog= "DIALOG";
    public final static int TARGET = 1;
    public final static String ARG1 = "arg1";

    DialogEditInformationBinding binding;

    private DialogListener mListener;
    private UserTbl mUserTbl;
    private String mWords;

    public static InformationDialogFragment newInstance(UserTbl userTbl){
        InformationDialogFragment dialogFragment = new InformationDialogFragment();
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
        binding =  DataBindingUtil.inflate(inflater, R.layout.dialog_edit_information,container,false);
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
        binding.editTextFullname.setText(mUserTbl.getName());
        binding.editTextSchool.setText(mUserTbl.getSchool());
        binding.editTextClass.setText(mUserTbl.getClassName());
        binding.editTextSchoolAddress.setText(mUserTbl.getSchoolAddress());
        binding.editTextHomeAddress.setText(mUserTbl.getHomeAddress());
    }


    private void initButton(){
        binding.buttonCancel.setOnClickListener(v -> {
            dismiss();
        });


        binding.buttonSave.setOnClickListener(v -> {
            mUserTbl.setName(binding.editTextFullname.getText().toString());
            mUserTbl.setSchool(binding.editTextSchool.getText().toString());
            mUserTbl.setClassName(binding.editTextClass.getText().toString());
            mUserTbl.setSchoolAddress(binding.editTextSchoolAddress.getText().toString());
            mUserTbl.setHomeAddress(binding.editTextHomeAddress.getText().toString());

            mListener.onSave(mUserTbl);
            dismiss();

        });
    }

    public interface DialogListener {
        void onSave(UserTbl userTbl);
    }
}
