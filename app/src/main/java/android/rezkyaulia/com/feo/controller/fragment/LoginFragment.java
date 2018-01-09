package android.rezkyaulia.com.feo.controller.fragment;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.rezkyaulia.com.feo.R;
import android.rezkyaulia.com.feo.database.Facade;
import android.rezkyaulia.com.feo.database.entity.UserTbl;
import android.rezkyaulia.com.feo.databinding.FragmentLoginBinding;
import android.rezkyaulia.com.feo.handler.api.ApiClient;
import android.rezkyaulia.com.feo.handler.api.UserApi;
import android.rezkyaulia.com.feo.handler.observer.RxBus;
import android.rezkyaulia.com.feo.utility.HttpResponse;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.gson.Gson;

import io.reactivex.Observer;
import timber.log.Timber;

/**
 * Created by Rezky Aulia Pratama on 12/14/2017.
 */

public class LoginFragment extends BaseFragment {

    FragmentLoginBinding binding;


    private final int RC_SIGN_IN = 9001;

    private FirebaseAuth mAuth;

    GoogleSignInClient mGoogleSignInClient;

    OnFragmentInteractionListener mListener;

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
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
        binding =  DataBindingUtil.inflate(inflater, R.layout.fragment_login,container,false);

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

        initSignIn();

        signIn();

        binding.textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onRegisterInteraction();
            }
        });


        RxBus.getInstance().observable(UserApi.Response.class).subscribe( event ->{
            Timber.e("setAccount : "+new Gson().toJson(event));
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    binding.edittextUsername.setText(event.getApiValue().getUsername());
                    binding.edittextPin.setText(event.getApiValue().getPassword());

                }
            });

        });
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Timber.e("Google sign in failed : "+ new Gson().toJson(e));
//                updateUi(null);
            }

        }

    }

    private void initView(){
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.edittextUsername.getText().toString();
                String password = binding.edittextPin.getText().toString();
                postlogin(email,password);
            }
        });



    }

    private void postlogin(String email, String password){
        UserTbl userTbl = new UserTbl();
        userTbl.setUsername(email);
        userTbl.setPassword(password);

        binding.layoutProgress.setVisibility(View.VISIBLE);

        ApiClient.getInstance().user().login(userTbl).
                getAsObject(UserApi.Response.class, new ParsedRequestListener<UserApi.Response>() {

                    @Override
                    public void onResponse(UserApi.Response response) {
                        if (response != null){
                            Timber.e("RESPONSE : "+new Gson().toJson(response));

                            if (HttpResponse.getInstance().success(response)){
                                long id = Facade.getInstance().getManagerUserTbl().add(response.ApiValue);
                                if (id>0) {

                                    Thread thread = new Thread() {
                                        @Override
                                        public void run() {
                                            try {
                                                Thread.sleep(6000);
                                                getActivity().runOnUiThread(() -> {
                                                    binding.layoutProgress.setVisibility(View.GONE);
                                                    mListener.onLoginSuccessfullInteraction();
                                                });
                                            } catch (InterruptedException e) {
                                                Timber.e("ERROR : "+e.getMessage());
                                            }

                                        }};
                                    thread.start();
                                }else
                                    Snackbar.make(binding.layoutContentMain,"An error expected",Snackbar.LENGTH_LONG).show();

                            }else if (response.ApiStatus.equalsIgnoreCase(HttpResponse.getInstance().NOT_FOUND)){
                                mListener.onSubscribeInteraction(userTbl);
                            }
                            else{
                                Snackbar.make(binding.layoutContentMain,response.getApiMessage(),Snackbar.LENGTH_LONG).show();
                                binding.layoutProgress.setVisibility(View.GONE);

                            }
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Timber.e("Error : "+anError.getMessage());
                        binding.layoutProgress.setVisibility(View.GONE);
                    }
                });
    }

    private void initSignIn(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("851111297033-bvavn4dfffpomv2tai9tbfu30ornibh5.apps.googleusercontent.com")
                .requestEmail()
                .build();


        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
        mAuth = FirebaseAuth.getInstance();

/*
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getActivity());
        updateUi(account);*/
    }

    private void signIn(){
        binding.buttonGoogleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });


    }


    private void updateSignInData(FirebaseUser acct){
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
            String personId = acct.getUid();
            String phone = acct.getPhoneNumber();
            Uri personPhoto = acct.getPhotoUrl();
            String providerId = acct.getProviderId();
            Timber.e(personName + " | " + phone + " | " +providerId + " | " +personEmail + " | " +personId + " | " +personPhoto);
            postlogin(personEmail,"");
        }

    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Timber.e("firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Timber.e( "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
//                            updateUi(user);
                            updateSignInData(user);

                        } else {
                            // If sign in fails, display a message to the user.
                            Timber.e("signInWithCredential:failure : "+ task.getException());
                            Toast.makeText(getActivity(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
//                            updateUi(null);
                        }

                        // ...
                    }
                });
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name

        void onLoginSuccessfullInteraction();
        void onSubscribeInteraction(UserTbl userTbl);
        void onRegisterInteraction();
    }


}
