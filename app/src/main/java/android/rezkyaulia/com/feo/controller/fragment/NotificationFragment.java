package android.rezkyaulia.com.feo.controller.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.rezkyaulia.com.feo.R;
import android.rezkyaulia.com.feo.controller.adapter.LibraryRVAdapter;
import android.rezkyaulia.com.feo.controller.adapter.NotificationRVAdapter;
import android.rezkyaulia.com.feo.database.Facade;
import android.rezkyaulia.com.feo.database.entity.LibraryTbl;
import android.rezkyaulia.com.feo.database.entity.NotificationTbl;
import android.rezkyaulia.com.feo.databinding.FragmentNotificationBinding;
import android.rezkyaulia.com.feo.handler.api.UserApi;
import android.rezkyaulia.com.feo.handler.observer.RxBus;
import android.rezkyaulia.com.feo.model.NotifModel;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

/**
 * Created by Rezky Aulia Pratama on 1/2/2018.
 */

public class NotificationFragment extends BaseFragment {
    public static final String ARG_PARAM1 = "ARG_PARAM1";
    public static final String ARG_PARAM2 = "ARG_PARAM2";


    FragmentNotificationBinding binding;
    List<NotificationTbl> mNotifcationTbls;

    private LinearLayoutManager mLayoutManager;
    private NotificationRVAdapter mAdapter;

    private OnListFragmentInteractionListener mListener;


    public static NotificationFragment newInstance() {
        NotificationFragment fragment = new NotificationFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, category);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding =  DataBindingUtil.inflate(inflater, R.layout.fragment_notification,container,false);

        if(savedInstanceState != null){
            Timber.e("SAVEDINSTACESTATE");
        }

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mNotifcationTbls = new ArrayList<>();
        initRV();
        Timber.e("NOTIFICATION FRAGMENT");

       /* RxBus.getInstance().observable(NotifModel.class).subscribe(new Observer<NotifModel>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(NotifModel notifModel) {
                Timber.e("OnNExt : "+new Gson().toJson(notifModel));
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run()
                    {
                        if(getActivity() != null)
                            if (notifModel.getAction().equals(NotifModel.NOTIF_MESSAGE))
                                initData();
                    }
                });
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });*/
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();

    }

    public void initData(){
        List<NotificationTbl> notificationTbls = facade.getManageNotificationTbl().getAll();
        if (mNotifcationTbls != null){
            mNotifcationTbls.clear();
        }
        mNotifcationTbls.addAll(notificationTbls);

        if (mNotifcationTbls.size()>0){
            binding.contentNoResult.setVisibility(View.GONE);
        }else{
            binding.contentNoResult.setVisibility(View.VISIBLE);
        }
        Timber.e("notificationTbls : "+new Gson().toJson(mNotifcationTbls));

        if (mAdapter != null)
            mAdapter.notifyDataSetChanged();
    }

    public void setRead(int flag){
        List<NotificationTbl> notificationTbls = facade.getManageNotificationTbl().getAll();
        for (NotificationTbl notificationTbl :notificationTbls){
            notificationTbl.setFlagRead(flag);
            Facade.getInstance().getManageNotificationTbl().add(notificationTbl);
        }
    }

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


    private void initRV(){
        mLayoutManager = new LinearLayoutManager(getActivity());
        binding.recyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new NotificationRVAdapter(getActivity(),mNotifcationTbls, mListener);
        binding.recyclerView.setAdapter(mAdapter);
    }


    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name

        void onDeleteNotificationInteraction(NotificationTbl notificationTbl);
    }

}
