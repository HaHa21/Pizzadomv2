package com.pizzadom.pizzadom;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;


import butterknife.BindView;

import butterknife.ButterKnife;



/**
 * Created by User on 24/6/2017.
 */

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "Pizzadom";
    @BindView(R.id.Email)
    EditText input_email;
    @BindView(R.id.Password)
    EditText input_password;

    @BindView(R.id.login)
    Button btnlogin;
    @BindView(R.id.ForgotPass)
    Button forgotpass;
    @BindView(R.id.SignUp)
    Button signup;
    @BindView(R.id.backbtn)
    ImageButton btnback;
    @BindView(R.id.forwardbtn)
    ImageButton btnforward;

    public String email, password;

    private FirebaseAuth.AuthStateListener mAuthListener;
    private Firebase mRef;
    private FirebaseAuth auth;
    @BindView(R.id.loginlayout)
    CoordinatorLayout login;
    //progress dialog
    private ProgressDialog progressDialog;
    //FaceBook callbackManager
    private CallbackManager callbackManager;
    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        ButterKnife.bind(this);

        toolbar.setTitle("Pizzadom");

        setSupportActionBar(toolbar);



        /*
        if(savedInstanceState != null){
            password = savedInstanceState.getString("password");
            email = savedInstanceState.getString("email");
        }*/
        auth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);


        btnlogin.setOnClickListener(new ButtonHandler(1, input_password, input_email, this, auth, progressDialog));
        forgotpass.setOnClickListener(new ButtonHandler(2, input_password, input_email, this, auth, progressDialog));
        signup.setOnClickListener(new ButtonHandler(3, input_password, input_email, this, auth, progressDialog));
        btnback.setOnClickListener(new ButtonHandler(4, input_password, input_email, this, auth, progressDialog));
        btnforward.setOnClickListener(new ButtonHandler(5, input_password, input_email, this, auth, progressDialog));

    }

    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(getString(R.string.loading));
            progressDialog.setIndeterminate(true);
        }

        progressDialog.show();
    }

    private void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.action_create_order:
                Intent intentorder = new Intent(this , ShoppingActivity.class);
                startActivity(intentorder);
                finish();
                return true;
            case R.id.action_settings:
                return true;
            case R.id.action_register:
                Intent intentregister = new Intent(this, Firebaseregister.class);
                startActivity(intentregister);
                finish();
                return true;
            case R.id.action_maps:
                Intent intentmaps = new Intent(this, MapsActivity.class);
                startActivity(intentmaps);
                finish();
                return true;
            case R.id.action_email:
                Intent intentemail = new Intent(this, EmailActivity.class);
                startActivity(intentemail);
                finish();
                return true;
            case R.id.action_contacts:
                Intent intentcontact = new Intent(this, ContactActivity.class);
                startActivity(intentcontact);
                finish();
                return true;


            default: return super.onOptionsItemSelected(item);
        }
    }



}

class ButtonHandler implements OnClickListener{
    private static final String TAG = "Pizzadom";
    ButtonHandler btn;
    int statehandler;

    EditText password;
    EditText email;
    LoginActivity login;
    FirebaseAuth auth;
    CallbackManager callbackManager;
    //progress dialog
    ProgressDialog progressDialog;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Firebase mRef;
    String input_pass;
    String input_email;


    public ButtonHandler(int statehandler, EditText password, EditText email,
                         LoginActivity login, FirebaseAuth auth, ProgressDialog progressDialog){
        this.statehandler = statehandler;
        this.password = password;
        this.email = email;
        this.login = login;
        this.auth = auth;
        this.progressDialog = progressDialog;

    }

    @Override
    public void onClick(View v) {

        input_pass = login.input_password.getText().toString();
        input_email = login.input_email.getText().toString();

        if(statehandler == 1){

            if(TextUtils.isEmpty(input_email)){
                Toast.makeText(login, "Please enter email ", Toast.LENGTH_LONG).show();
                return;
            }

             if(TextUtils.isEmpty(input_pass)){
                Toast.makeText(login, "Please enter password ", Toast.LENGTH_LONG).show();
                return;
            }

            signinTheUserWithEmailAndPassword(input_email, input_pass);

        }else if(statehandler == 2){
            login.startActivity(new Intent(login, Forgotpassword.class));
            login.finish();
        }else if(statehandler == 3){
            login.startActivity(new Intent(login, Firebaseregister.class));
            login.finish();
        }else if(statehandler == 4){
            login.startActivity(new Intent(login, MainActivity.class));
            login.finish();
        }else if(statehandler == 5){
            Intent intentshop = new Intent(login, ShoppingActivity.class);
            login.startActivity(intentshop);
            login.finish();
        }
    }

    private void signinTheUserWithEmailAndPassword(String userEmail, String userPassword){
        auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(
                login, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                           Toast.makeText(login, "There was an error", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(login, "Login successful", Toast.LENGTH_SHORT).show();
                            /*
                            showProgressDialog();
                           Intent welcomeIntent = new Intent(login, WelcomeShoppingActivity.class);
                            login.startActivity(welcomeIntent);
                            login.finish();
                            hideProgressDialog();
                            */
                        }
                    }
                }
        );
    }

    private void signInWithFacebook(AccessToken accessToken) {
        Log.d(TAG, "signInWithFacebook:" + accessToken);

        showProgressDialog();


        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        auth.signInWithCredential(credential)
                .addOnCompleteListener(login, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(login, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            String uid=task.getResult().getUser().getUid();
                            String name=task.getResult().getUser().getDisplayName();
                            String email=task.getResult().getUser().getEmail();
                            String image=task.getResult().getUser().getPhotoUrl().toString();

                            //Create a new User and Save it in Firebase database
                            User user = new User(uid,name,null,email,null);

                            mRef.child(uid).setValue(user);
                            Intent intent = new Intent(login, MainActivity.class);
                            intent.putExtra("user_id",uid);
                            intent.putExtra("profile_picture",image);
                            login.startActivity(intent);
                            login.finish();
                        }

                        hideProgressDialog();
                    }
                });
    }

    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(login);
            progressDialog.setMessage(login.getString(R.string.loading));
            progressDialog.setIndeterminate(true);
        }

        progressDialog.show();
    }

    private void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

}
