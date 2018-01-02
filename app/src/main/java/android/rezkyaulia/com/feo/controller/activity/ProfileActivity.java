package android.rezkyaulia.com.feo.controller.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.rezkyaulia.com.feo.R;
import android.rezkyaulia.com.feo.controller.fragment.ProfileFragment;
import android.rezkyaulia.com.feo.databinding.ActivityProfileBinding;
import android.support.v7.app.AlertDialog;
import android.view.View;

/**
 * Created by Rezky Aulia Pratama on 12/29/2017.
 */

public class ProfileActivity extends BaseActivity {

    ActivityProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile);

        displayFragment(binding.content.layoutContent.getId(),new ProfileFragment());

        binding.imageViewHome.setOnClickListener(v -> {
            finish();
        });

        binding.content.buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               showAlertDialogLogout();
            }
        });
    }


    private void showAlertDialogLogout(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Logout"); //Set Alert dialog title here
        alert.setMessage("Do you want to logout ?"); //Message here
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //You will get as string input data in this variable.
                // here we convert the input to a string and show in a toast.
                facade.getManagerUserTbl().removeAll();
                startActivity(new Intent(ProfileActivity.this,LoginActivity.class));
                finish();
                dialog.dismiss();
            } // End of onClick(DialogInterface dialog, int whichButton)
        }); //End of alert.setPositiveButton
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
                dialog.cancel();

            }
        }); //End of alert.setNegativeButton
        AlertDialog alertDialog = alert.create();
        alertDialog.show();
    }
}
