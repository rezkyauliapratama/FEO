package android.rezkyaulia.com.feo.controller.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.rezkyaulia.com.feo.R;
import android.rezkyaulia.com.feo.database.entity.UserTbl;
import android.rezkyaulia.com.feo.databinding.FragmentRegisterBinding;
import android.rezkyaulia.com.feo.handler.api.ApiClient;
import android.rezkyaulia.com.feo.handler.api.UserApi;
import android.rezkyaulia.com.feo.utility.HttpResponse;
import android.rezkyaulia.com.feo.utility.Utils;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.google.gson.Gson;

import timber.log.Timber;

/**
 * Created by Rezky Aulia Pratama on 12/14/2017.
 */

public class RegisterFragment extends BaseFragment {

    FragmentRegisterBinding binding;

    OnFragmentInteractionListener mListener;

    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();
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
        binding =  DataBindingUtil.inflate(inflater, R.layout.fragment_register,container,false);

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

    void initView(){
        binding.textViewSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onSignInteraction();
            }
        });

        binding.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerData();
            }
        });
    }

    boolean registerData(){
        boolean b = true;

        String fullname = binding.containerAccount.editTextFullname.getText().toString();
        String username = binding.containerAccount.editTextUsername.getText().toString();
        String email = binding.containerAccount.editTextEmail.getText().toString();
        String password = binding.containerAccount.editTextPassword.getText().toString();
        String retypePassword = binding.containerAccount.editTextRetypePassword.getText().toString();
        String school = binding.containerInformation.editTextSchool.getText().toString();
        String className = binding.containerInformation.editTextClass.getText().toString();
        String schoolAdrress = binding.containerInformation.editTextSchoolAddress.getText().toString();
        String homeAdrress = binding.containerInformation.editTextHomeAddress.getText().toString();

        if (!password.equals(retypePassword)) {
            return false;
        }

        if (fullname.isEmpty()){
            binding.containerAccount.editTextFullname.setError(getString(R.string.please_fill_in_here));
            b = false;
        }


        if (username.isEmpty()){
            binding.containerAccount.editTextUsername.setError(getString(R.string.please_fill_in_here));
       
            b = false;
        }

        if (email.isEmpty()){
            binding.containerAccount.editTextEmail.setError(getString(R.string.please_fill_in_here));

            b = false;
        }


        if (password.isEmpty()){
            binding.containerAccount.editTextPassword.setError(getString(R.string.please_fill_in_here));
       
            b = false;
        }


        if (retypePassword.isEmpty()){
            binding.containerAccount.editTextRetypePassword.setError(getString(R.string.please_fill_in_here));
       
            b = false;
        }


        if (school.isEmpty()){
            binding.containerInformation.editTextSchoolAddress.setError(getString(R.string.please_fill_in_here));
       
            b = false;
        }


        if (className.isEmpty()){
            binding.containerInformation.editTextClass.setError(getString(R.string.please_fill_in_here));
       
            b = false;
        }

        if (schoolAdrress.isEmpty()){
            binding.containerInformation.editTextSchoolAddress.setError(getString(R.string.please_fill_in_here));
       
            b = false;
        }

        if (homeAdrress.isEmpty()){
            binding.containerInformation.editTextHomeAddress.setError(getString(R.string.please_fill_in_here));
       
            b = false;
        }

       
        if (b){
            UserTbl userTbl = new UserTbl();
            userTbl.setUserRoleId(3L);
            userTbl.setName(fullname);
            userTbl.setUsername(username);
            userTbl.setPassword(password);
            userTbl.setEmail(email);
            userTbl.setSchool(school);
            userTbl.setClassName(className);
            userTbl.setSchoolAddress(schoolAdrress);
            userTbl.setHomeAddress(homeAdrress);
            userTbl.setCreatedDate(Utils.getInstance().time().getDateTimeString());
            postData(userTbl);
        }


        return b;
    }

    void postData(UserTbl userTbl){
        binding.layoutProgress.setVisibility(View.VISIBLE);
        ApiClient.getInstance().user().post(userTbl).getAsObject(UserApi.ResponseRegistration.class, new ParsedRequestListener<UserApi.ResponseRegistration>() {
            @Override
            public void onResponse(UserApi.ResponseRegistration response) {
                Timber.e("RESPONSE : "+new Gson().toJson(response));
                if (response != null){
                    if (HttpResponse.getInstance().success(response))
                        mListener.onRegisteredSuccesful(response);
                    else
                        mListener.onRegisteredFailed(response);

                    binding.layoutProgress.setVisibility(View.GONE);

                }
            }


            @Override
            public void onError(ANError anError) {
                Timber.e("ERROR : "+new Gson().toJson(anError));
                binding.layoutProgress.setVisibility(View.GONE);

            }
        });
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name

        void onSignInteraction();
        void onRegisteredSuccesful(UserApi.ResponseRegistration response);
        void onRegisteredFailed(UserApi.ResponseRegistration response);
    }

}
