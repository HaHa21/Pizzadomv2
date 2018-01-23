package com.pizzadom.pizzadom;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.R.attr.mode;

/**
 * Created by User on 24/6/2017.
 */

public class Forgotpassword extends AppCompatActivity {
    private TextView txtWelcome;
    private EditText input_new_password;
    private Button btnChangePass, btnLogout, btnback;
    private FirebaseAuth auth;
    private RelativeLayout activity_dashboard;
    private ProgressDialog PD;

    Intent i;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgotpassword);
        activity_dashboard = (RelativeLayout) findViewById(R.id.forgotpasslayout);
        txtWelcome = (TextView) findViewById(R.id.dashboard_welcome);
        input_new_password = (EditText) findViewById(R.id.email_address);
        btnChangePass = (Button) findViewById(R.id.dashboard_btn_change_pass);
        btnback = (Button) findViewById(R.id.btnback);


        btnChangePass.setOnClickListener(new ButtonHandlerForgotPassword(this, 1, PD, input_new_password, auth));
        btnback.setOnClickListener(new ButtonHandlerForgotPassword(this, 2, PD, input_new_password, auth));
        PD = new ProgressDialog(this);
        PD.setMessage("Loading...");
        PD.setCancelable(true);
        PD.setCanceledOnTouchOutside(false);


        //init Firebase
        auth = FirebaseAuth.getInstance();




    }

    /*
    @Override
    public void onClick(View v) {
       if(v.getId() == R.id.dashboard_btn_change_pass){
           String email_id = input_new_password.getText().toString().trim();
           auth.sendPasswordResetEmail(email_id)
                   .addOnCompleteListener(new OnCompleteListener<Void>() {
                       @Override
                       public void onComplete(@NonNull Task<Void> task) {
                           if (task.isSuccessful()) {
                               PD.show();
                               PD.dismiss();
                               Toast.makeText(Forgotpassword.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();


                           } else {
                               PD.show();
                               PD.dismiss();
                               Toast.makeText(Forgotpassword.this, "Failed to send reset email !! This Email is not registerd !!", Toast.LENGTH_SHORT).show();

                           }

                       }
                   });
       }



       else if(v.getId() == R.id.btnback) {
           i = new Intent(Forgotpassword.this, LoginActivity.class);
           startActivity(i);
       }
    }
    */

}

class ButtonHandlerForgotPassword implements View.OnClickListener {
    public Forgotpassword btnhandler;
    public int statehandler;
    private EditText input_new_password;
    private FirebaseAuth auth;
    private ProgressDialog PD;

    public ButtonHandlerForgotPassword(Forgotpassword btnhandler, int statehandler, ProgressDialog PD, EditText input_new_password, FirebaseAuth auth){
        this.btnhandler = btnhandler;
        this.statehandler = statehandler;
        this.PD = PD;
        this.input_new_password = input_new_password;
        this.auth = auth;
    }

    @Override
    public void onClick(View v) {
        if(statehandler == 1) {
            String email_id = input_new_password.getText().toString().trim();
            auth.sendPasswordResetEmail(email_id)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                PD.show();
                                PD.dismiss();
                                Toast.makeText(btnhandler, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();


                            } else {
                                PD.show();
                                PD.dismiss();
                                Toast.makeText(btnhandler, "Failed to send reset email !! This Email is not registerd !!", Toast.LENGTH_SHORT).show();

                            }

                        }
                    });
        }

        else if(statehandler == 2){
            Intent i = new Intent(btnhandler, LoginActivity.class);
            btnhandler.startActivity(i);
        }
    }
}






