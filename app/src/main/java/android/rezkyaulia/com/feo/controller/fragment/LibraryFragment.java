package android.rezkyaulia.com.feo.controller.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.rezkyaulia.com.feo.R;
import android.rezkyaulia.com.feo.controller.adapter.LibraryRVAdapter;
import android.rezkyaulia.com.feo.database.Facade;
import android.rezkyaulia.com.feo.database.entity.LibraryTbl;
import android.rezkyaulia.com.feo.databinding.FragmentLibraryBinding;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.rezkyaulia.android.light_optimization_data.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import timber.log.Timber;

/**
 * Created by Rezky Aulia Pratama on 11/11/2017.
 */

public class LibraryFragment extends BaseFragment {

    FragmentLibraryBinding binding;
    private GridLayoutManager mLayoutManager;
    private LibraryRVAdapter mAdapter;
    private List<LibraryTbl> mLibraryTbls;

    public static LibraryFragment newInstance() {
        LibraryFragment fragment = new LibraryFragment();
        /*Bundle args = new Bundle();
        args.putString(ARG_PARAM1, category);
        fragment.setArguments(args);*/
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);

        mLibraryTbls = new ArrayList<>();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding =  DataBindingUtil.inflate(inflater, R.layout.fragment_library,container,false);

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
        initRecyclerview();

        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (binding.swipeRefreshLayout != null) {
                    binding.swipeRefreshLayout.setRefreshing(true);
                }

                mLibraryTbls = Facade.getInstance().getManageLibraryTbl().getAll();
                Timber.e("mLibraryTbls : "+new Gson().toJson(mLibraryTbls));
                binding.recyclerView.getAdapter().notifyDataSetChanged();
                binding.swipeRefreshLayout.setRefreshing(false);

            }
        });
    }

    private void initRecyclerview(){
        mLibraryTbls = Facade.getInstance().getManageLibraryTbl().getAll();
        Timber.e("Size libraryTbls : "+mLibraryTbls.size());
        mLayoutManager = new GridLayoutManager(getContext(),2);
        binding.recyclerView.setLayoutManager(mLayoutManager);
        if (mLibraryTbls != null){
            mAdapter = new LibraryRVAdapter(getContext(),mLibraryTbls);
            Timber.e("mAdapter.getcount : "+mAdapter.getItemCount());
            binding.recyclerView.setAdapter(mAdapter);
        }



    }

}
