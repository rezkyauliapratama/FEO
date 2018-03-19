package android.rezkyaulia.com.feo.controller.fragment;

import android.accounts.Account;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.rezkyaulia.com.feo.R;
import android.rezkyaulia.com.feo.controller.activity.BaseActivity;
import android.rezkyaulia.com.feo.controller.activity.LanguageActivity;
import android.rezkyaulia.com.feo.controller.fragment.dialog.AccountDialogFragment;
import android.rezkyaulia.com.feo.controller.fragment.dialog.InformationDialogFragment;
import android.rezkyaulia.com.feo.controller.fragment.dialog.PasswordDialogFragment;
import android.rezkyaulia.com.feo.controller.service.PushScoreService;
import android.rezkyaulia.com.feo.controller.service.PushUserService;
import android.rezkyaulia.com.feo.database.entity.SubscriptionTbl;
import android.rezkyaulia.com.feo.database.entity.UserTbl;
import android.rezkyaulia.com.feo.databinding.FragmentProfileBinding;
import android.rezkyaulia.com.feo.databinding.FragmentRegisterBinding;
import android.rezkyaulia.com.feo.utility.Utils;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

import java.util.Date;

import timber.log.Timber;

/**
 * Created by Rezky Aulia Pratama on 12/29/2017.
 */

public class ProfileFragment extends BaseFragment implements
        PasswordDialogFragment.DialogListener,
        AccountDialogFragment.DialogListener,
        InformationDialogFragment.DialogListener{

    FragmentProfileBinding binding;
    FragmentManager fragmentManager;

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        /*Bundle args = new Bundle();
        args.putString(ARG_PARAM1, category);
        fragment.setArguments(args);*/
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
        binding =  DataBindingUtil.inflate(inflater, R.layout.fragment_profile,container,false);

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
        fragmentManager = getFragmentManager();

        initData();
        initButton();
    }

    void initData(){
        binding.textViewUsername.setText(userTbl.getUsername());
        binding.textViewEmail.setText(userTbl.getEmail());
        binding.textViewSchoolName.setText(userTbl.getSchool());
        binding.textViewClassName.setText(userTbl.getClassName());
        binding.textViewSchoolAddress.setText(userTbl.getSchoolAddress());
        binding.textViewHomeAddress.setText(userTbl.getHomeAddress());
        binding.textViewFullname.setText(userTbl.getName());

        SubscriptionTbl subscriptionTbl = facade.getManageSubscriptionTbl().getNewest(userTbl.getUserId());
        if (subscriptionTbl.getSubscriptionEndTimestamp() != null){
            Date date = Utils.getInstance().time().parseDate(subscriptionTbl.getSubscriptionEndTimestamp());
            binding.textViewTimeExpired.setText(Utils.getInstance().time().getUserFriendlyDateTimeString(date));
        }


    }

    void initButton(){
        binding.buttonChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogPassword();
            }
        });

        binding.buttonChangeAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogAccount();
            }
        });

        binding.buttonChangeInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogInformation();
            }
        });

        binding.buttonChangeLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), LanguageActivity.class));
            }
        });

    }

    @Override
    public void onSave(UserTbl userTbl) {
        this.userTbl = userTbl;
        facade.getManagerUserTbl().add(userTbl);
        initData();

        initJobDispatcher();


    }

    private void initJobDispatcher(){
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(getActivity()));
        Job myJob = dispatcher.newJobBuilder()
                .setService(PushUserService.class) // the JobService that will be called
                .setTag("pushUsersTbl")
                .setReplaceCurrent(true)
                .setRecurring(false)
                .setLifetime(Lifetime.UNTIL_NEXT_BOOT)
                // Run between 0 - 10 seconds from now.
                .setTrigger(Trigger.executionWindow(0, 10))
                .build();

        Timber.e("initJobDispatcher");
        dispatcher.mustSchedule(myJob);
    }


    void showDialogPassword(){
        PasswordDialogFragment passwordDialogFragment = PasswordDialogFragment.newInstance(userTbl);
        passwordDialogFragment.setCancelable(false);
        passwordDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.dialog_light );
        passwordDialogFragment.setTargetFragment(this,passwordDialogFragment.TARGET);
        passwordDialogFragment.show(fragmentManager.beginTransaction(), PasswordDialogFragment.Dialog);
    }

    void showDialogInformation(){
        InformationDialogFragment informationDialogFragment = InformationDialogFragment.newInstance(userTbl);
        informationDialogFragment.setCancelable(false);
        informationDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.dialog_light );
        informationDialogFragment.setTargetFragment(this,informationDialogFragment.TARGET);
        informationDialogFragment.show(fragmentManager.beginTransaction(), InformationDialogFragment.Dialog);
    }

    void showDialogAccount(){
        AccountDialogFragment accountDialogFragment = AccountDialogFragment.newInstance(userTbl);
        accountDialogFragment.setCancelable(false);
        accountDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.dialog_light );
        accountDialogFragment.setTargetFragment(this,accountDialogFragment.TARGET);
        accountDialogFragment.show(fragmentManager.beginTransaction(), AccountDialogFragment.Dialog);
    }
}
