package android.rezkyaulia.com.feo.controller.fragment.dialog;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.rezkyaulia.com.feo.R;
import android.rezkyaulia.com.feo.database.entity.LibraryTbl;
import android.rezkyaulia.com.feo.databinding.DialogAnswerMemoryBinding;
import android.rezkyaulia.com.feo.databinding.DialogInputTextBinding;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Rezky Aulia Pratama on 11/9/2017.
 */

public class AnswerMemoryDialogFragment extends DialogFragment {
    public final static String Dialog= "DIALOG";
    public final static int TARGET = 1;
    public final static String ARGS1 = "args1";
    DialogAnswerMemoryBinding binding;

    private Bitmap mBitmap;

    public static AnswerMemoryDialogFragment newInstance(byte[] byteArray){
        AnswerMemoryDialogFragment dialogFragment = new AnswerMemoryDialogFragment();
        Bundle args = new Bundle();
        args.putByteArray(ARGS1, byteArray);
        dialogFragment.setArguments(args);
        return dialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            byte[] byteArray = getArguments().getByteArray(ARGS1);
            if (byteArray != null) {
                mBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding =  DataBindingUtil.inflate(inflater, R.layout.dialog_answer_memory,container,false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (mBitmap != null)
            binding.imageView.setImageBitmap(mBitmap);


        binding.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

}
