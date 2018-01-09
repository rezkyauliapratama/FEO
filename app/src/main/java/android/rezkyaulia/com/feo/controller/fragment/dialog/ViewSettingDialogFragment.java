package android.rezkyaulia.com.feo.controller.fragment.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.rezkyaulia.com.feo.R;
import android.rezkyaulia.com.feo.databinding.ContentDialogSettingViewBinding;
import android.rezkyaulia.com.feo.databinding.DialogSettingViewBinding;
import android.rezkyaulia.com.feo.utility.PreferencesManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * Created by Rezky Aulia Pratama on 12/13/2017.
 */

public class ViewSettingDialogFragment extends DialogFragment {
    public final static String Dialog= "DIALOG";
    public final static int TARGET = 1;
    public final static String ARG1 = "words";
    public final static String ARG2 = "issave";

    ContentDialogSettingViewBinding binding;

    ArrayAdapter<Integer> adapter;

    private boolean isSave;

    public static ViewSettingDialogFragment newInstance(){
        ViewSettingDialogFragment dialogFragment = new ViewSettingDialogFragment();
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
        binding =  DataBindingUtil.inflate(inflater, R.layout.content_dialog_setting_view,container,false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

            initSpinner();
            initButton();
            initRadioButton();
            initData();
    }

    void initRadioButton(){
        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Timber.e("checkId : "+checkedId);

            }
        });

    }

    void initSpinner(){
        List<Integer> spinnerArray =  new ArrayList<Integer>();
        spinnerArray.add(9);
        spinnerArray.add(10);
        spinnerArray.add(11);
        spinnerArray.add(12);
        spinnerArray.add(14);
        spinnerArray.add(16);
        spinnerArray.add(18);
        spinnerArray.add(20);
        spinnerArray.add(24);
        spinnerArray.add(28);
        spinnerArray.add(32);

        adapter = new ArrayAdapter<>(
                getActivity(), R.layout.textview_spinner, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        binding.spinner.setAdapter(adapter);



    }

    void initButton(){
        binding.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selected = (int) binding.spinner.getSelectedItem();
                Timber.e("selected value : "+selected);
                PreferencesManager.getInstance().setFontSize(selected);

                if (binding.radioBlack.isChecked())
                    PreferencesManager.getInstance().setBlack(true);
                else
                    PreferencesManager.getInstance().setBlack(false);

                dismiss();
            }
        });
    }

    void initData(){
        float fontSize = PreferencesManager.getInstance().getFontSize();
        binding.spinner.setSelection(adapter.getPosition((int)fontSize));

        boolean b = PreferencesManager.getInstance().isBlack();

        if (b){
            binding.radioBlack.setChecked(true);
        }else{
            binding.radioWhite.setChecked(true);
        }


    }
}
