package android.rezkyaulia.com.feo.controller.fragment.dialog;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.rezkyaulia.com.feo.R;
import android.rezkyaulia.com.feo.database.entity.UserTbl;
import android.rezkyaulia.com.feo.databinding.ContentGuideFeoBinding;
import android.rezkyaulia.com.feo.databinding.DialogEditInformationBinding;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Rezky Aulia Pratama on 11/20/2017.
 */

public class GuideFeoDialogFragment extends DialogFragment {
    public final static String Dialog= "DIALOG";
    public final static int TARGET = 1;
    public final static String ARG1 = "arg1";

    ContentGuideFeoBinding binding;

    private UserTbl mUserTbl;
    private String mWords;

    public static GuideFeoDialogFragment newInstance(){
        GuideFeoDialogFragment dialogFragment = new GuideFeoDialogFragment();
        Bundle args = new Bundle();
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
        binding =  DataBindingUtil.inflate(inflater, R.layout.content_guide_feo,container,false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
        initButton();

    }

    private void initView(){

    }


    private void initButton(){
        binding.buttonAgree.setOnClickListener(v -> {
            dismiss();
        });


    }

}
