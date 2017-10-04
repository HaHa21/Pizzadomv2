package com.pizzadom.pizzadom;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
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


/**
 * Created by User on 24/6/2017.
 */

public class LoginActivity extends AppCompatActivity implements OnClickListener {
    private static final String TAG = "Pizzadom";
    Button btnLogin,btnSignUp, btnForgotPass;
    ImageButton btnback, btnforward;
    EditText input_email, input_password;
    private String password, email;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Firebase mRef;
    private FirebaseAuth auth;
    private CoordinatorLayout login;
    //progress dialog
    private ProgressDialog progressDialog;
    //FaceBook callbackManager
    private CallbackManager callbackManager;
    //

    @Override
    public void onCreate(Bundle savedInstanceState){
       super.onCreate(savedInstanceState);
       setContentView(R.layout.login);

        btnLogin = (Button) findViewById(R.id.login);
        btnForgotPass = (Button) findViewById(R.id.ForgotPass);
        btnSignUp = (Button) findViewById(R.id.SignUp);
        btnback = (ImageButton) findViewById(R.id.backbtn);
        btnforward = (ImageButton) findViewById(R.id.forwardbtn);
        input_email = (EditText) findViewById(R.id.Email);
        input_password = (EditText) findViewById(R.id.Password);
        login = (CoordinatorLayout) findViewById(R.id.loginlayout);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Pizzadom");

        setSupportActionBar(toolbar);

        if(savedInstanceState != null){
            password = savedInstanceState.getString("password");
            email = savedInstanceState.getString("email");
        }

        progressDialog = new ProgressDialog(this);
        btnSignUp.setOnClickListener(this);
        btnForgotPass.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btnback.setOnClickListener(this);
        btnforward.setOnClickListener(this);
        //Add YOUR Firebase Reference URL instead of the following URL
         mRef = new Firebase("https://pizzadom-3caec.firebaseio.com/users/");
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        FirebaseUser mUser = auth.getCurrentUser();
        if (mUser != null) {
            // User is signed in
            Intent intent = new Intent(getApplicationContext(), WelcomeShoppingActivity.class);
            String uid = auth.getCurrentUser().getUid();
            String image = auth.getCurrentUser().getPhotoUrl().toString();
            intent.putExtra("user_id", uid);
            if(image!=null || image!=""){
                intent.putExtra("profile_picture",image);
            }
            startActivity(intent);
            finish();
            Log.d(TAG, "onAuthStateChanged:signed_in:" + mUser.getUid());
        }

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mUser = firebaseAuth.getCurrentUser();
                if (mUser != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + mUser.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }

            }
        };

        //FaceBook
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id.button_facebook_login);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                signInWithFacebook(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
            }
        });
    }

    public void onSavedInstanceState(Bundle saveState){
        saveState.putString("email", input_email.getText().toString());
        saveState.putString("password", input_password.getText().toString());
    }

    private void signInWithFacebook(AccessToken accessToken) {
        Log.d(TAG, "signInWithFacebook:" + accessToken);

        showProgressDialog();


        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            String uid=task.getResult().getUser().getUid();
                            String name=task.getResult().getUser().getDisplayName();
                            String email=task.getResult().getUser().getEmail();
                            String image=task.getResult().getUser().getPhotoUrl().toString();

                            //Create a new User and Save it in Firebase database
                            User user = new User(uid,name,null,email,null);

                            mRef.child(uid).setValue(user);
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("user_id",uid);
                            intent.putExtra("profile_picture",image);
                            startActivity(intent);
                            finish();
                        }

                        hideProgressDialog();
                    }
                });
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

    @Override
    public void onClick(View v) {
        String email = input_email.getText().toString();
        final String password = input_password.getText().toString();

       if(v.getId() == R.id.ForgotPass) {
           startActivity(new Intent(LoginActivity.this, Forgotpassword.class));
           finish();
       }
        else if(v.getId() == R.id.SignUp) {
           startActivity(new Intent(LoginActivity.this, Firebaseregister.class));
           finish();
       }
       else if(v.getId() == R.id.login){

           if(TextUtils.isEmpty(password)){
               Toast.makeText(this, "Please enter password ", Toast.LENGTH_LONG).show();
               return;
           }

           if(TextUtils.isEmpty(email)){
               Toast.makeText(this, "Please enter email ", Toast.LENGTH_LONG).show();
               return;
           }


           auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>(){

                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {


                           if(!task.isSuccessful()){


                               Log.w(TAG, "signInWithEmail", task.getException());
                               Toast.makeText(LoginActivity.this, "Authentication failed.",
                                       Toast.LENGTH_SHORT).show();
                           }
                           else {
                               progressDialog.setMessage("Logging in Please Wait...");
                               progressDialog.setCancelable(true);
                               progressDialog.setCanceledOnTouchOutside(false);
                               progressDialog.show();
                               progressDialog.dismiss();
                               Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                               String uid = auth.getCurrentUser().getUid();
                               intent.putExtra("user_id", uid);
                               startActivity(intent);
                               finish();
                           }
                          hideProgressDialog();
                       }
                   }
           );
       }
       else if(v.getId() == R.id.backbtn) {
           Intent intenthome = new Intent(this, MainActivity.class);
           startActivity(intenthome);
           finish();
       }
       else if(v.getId() == R.id.forwardbtn){
           Intent intentshopping = new Intent(this, ShoppingActivity.class);
           startActivity(intentshopping);
           finish();
       }
    }


}
