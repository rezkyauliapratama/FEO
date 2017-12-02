package android.rezkyaulia.com.feo.controller.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.rezkyaulia.com.feo.R;
import android.rezkyaulia.com.feo.database.Facade;
import android.rezkyaulia.com.feo.database.entity.LibraryTbl;
import android.rezkyaulia.com.feo.databinding.FragmentImageMemoryBinding;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import timber.log.Timber;

/**
 * Created by Rezky Aulia Pratama on 12/2/2017.
 */

public class MemoryImageFragment extends BaseFragment {

    public final static String ARGS1= "args1";
    public final static String ARGS2= "args2";

    FragmentImageMemoryBinding binding;

//    private OnListFragmentInteractionListener mListener;
    private int mIndex;

    Bitmap mBitmap;
    public static MemoryImageFragment newInstance(int index,byte[] byteArray) {
        MemoryImageFragment fragment = new MemoryImageFragment();
        Bundle args = new Bundle();
        args.putByteArray(ARGS1, byteArray);
        args.putInt(ARGS2, index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


        if (getArguments() != null){
            byte[] byteArray = getArguments().getByteArray(ARGS1);
            if (byteArray != null) {
                mBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            }

            mIndex = getArguments().getInt(ARGS2);


        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding =  DataBindingUtil.inflate(inflater, R.layout.fragment_image_memory,container,false);

        if(savedInstanceState != null){
            Timber.e("SAVEDINSTACESTATE");
            /*mCategory = savedInstanceState.getString(EXTRA1);
            movies = savedInstanceState.getParcelableArrayList(EXTRA2);
            listState = savedInstanceState.getParcelable(LIST_STATE_KEY);
            mPage = savedInstanceState.getInt(EXTRA3);*/
        }


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (mBitmap != null)
            binding.imageView.setImageBitmap(mBitmap);
        else {
            binding.imageView.setVisibility(View.GONE);
            binding.textViewAnswer.setVisibility(View.VISIBLE);
        }

//        mListener.onShowAnswerInteraction(mIndex);



    }

/*
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
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


    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name

        void onShowAnswerInteraction(int index);
    }*/


}
