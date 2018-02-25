package android.rezkyaulia.com.feo.controller.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.rezkyaulia.com.feo.R;
import android.rezkyaulia.com.feo.database.entity.PlanTbl;
import android.rezkyaulia.com.feo.database.entity.UserTbl;
import android.rezkyaulia.com.feo.databinding.FragmentCheckoutBinding;
import android.rezkyaulia.com.feo.databinding.FragmentRegisterBinding;
import android.rezkyaulia.com.feo.handler.api.ApiClient;
import android.rezkyaulia.com.feo.handler.api.UserApi;
import android.rezkyaulia.com.feo.utility.HttpResponse;
import android.rezkyaulia.com.feo.utility.TextStyleBuilder;
import android.rezkyaulia.com.feo.utility.Utils;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.Locale;

import timber.log.Timber;

import static java.lang.String.format;

/**
 * Created by Rezky Aulia Pratama on 12/14/2017.
 */

public class CheckoutFragment extends BaseFragment {
    public static final String ARG_PARAM1 = "ARGS1";
    FragmentCheckoutBinding binding;

    private PlanTbl mPlanTbl;
    OnFragmentInteractionListener mListener;

    public static CheckoutFragment newInstance(PlanTbl planTbl) {
        CheckoutFragment fragment = new CheckoutFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, planTbl);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if(getArguments() != null){
            Timber.e("SAVEDINSTACESTATE");
            mPlanTbl = getArguments().getParcelable(ARG_PARAM1);
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding =  DataBindingUtil.inflate(inflater, R.layout.fragment_checkout,container,false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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

    void initData(){
        Calendar dueDateCal = Calendar.getInstance();
        dueDateCal.add(Calendar.DAY_OF_MONTH,1);
        String transactionDate = Utils.getInstance().time().getUserFriendlyDateTimeString();
        String dueDate = Utils.getInstance().time().getUserFriendlyDateTimeString(dueDateCal.getTimeInMillis());

        binding.textViewTransactionDate.setText(transactionDate);
        binding.textViewDueDate.setText(dueDate);
        binding.textViewFullName.setText(userTbl.getName());
        binding.textViewEmail.setText(userTbl.getEmail());
        binding.textViewUsername.setText(userTbl.getUsername());
        binding.textViewPlanName.setText(mPlanTbl.getPlanName());
        binding.textViewDuration.setText(mPlanTbl.getTotalMonth()+" month(s)");
        binding.textViewTotalPrice.setText("Rp. "+mPlanTbl.getPrice());
    }


    void initView(){
        initData();

        binding.buttonCheckout.setOnClickListener(v -> mListener.onCheckout());
    }



    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name

        void onCheckout();

    }

}
