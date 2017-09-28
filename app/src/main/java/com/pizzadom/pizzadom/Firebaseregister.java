package com.pizzadom.pizzadom;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by User on 24/6/2017.
 */

public class Firebaseregister extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "Pizzadom";
    private Firebase mRef = new Firebase("https://pizzadom-3caec.firebaseio.com/");
    private User user;
    private EditText editTextEmail, editTextPassword, editName, editPhone;
    private Button buttonSavestate, buttonreset;
    private ImageButton btnback, btnforward;
    Snackbar snackbar;
    private ProgressDialog mProgressDialog;
    private CoordinatorLayout insertlayout;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.insertactivity);


        Firebase.setAndroidContext(this);
        auth = FirebaseAuth.getInstance();

    }

    @Override
    protected void onStart() {
        super.onStart();
        buttonSavestate = (Button) findViewById(R.id.buttonSave);

        editTextEmail = (EditText) findViewById(R.id.editemailAddress);
        editName = (EditText) findViewById(R.id.editName);
        editPhone = (EditText) findViewById(R.id.editPhone);
        editTextPassword = (EditText) findViewById(R.id.edittextpassword);

        insertlayout = (CoordinatorLayout) findViewById(R.id.insertLayout);
        buttonreset = (Button) findViewById(R.id.btnreset);
        btnback = (ImageButton) findViewById(R.id.backbtn);

        buttonSavestate.setOnClickListener(this);

        buttonreset.setOnClickListener(this);
        btnback.setOnClickListener(this);

    }

    @Override
    public void onStop() {
        super.onStop();
    }

    //This method sets up a new User by fetching the user entered details.
    protected void setUpUser() {
        user = new User();
        user.setName(editName.getText().toString());
        user.setPhoneNumber(editPhone.getText().toString());
        user.setEmail(editTextEmail.getText().toString());
        user.setPassword(editTextPassword.getText().toString());
    }

    private void createNewAccount(String email, String password) {
        Log.d(TAG, "createNewAccount:" + email);
        if (!validateForm()) {
            return;
        }
        //This method sets up a new User by fetching the user entered details.
        setUpUser();
        //This method  method  takes in an email address and password, validates them and then creates a new user
        // with the createUserWithEmailAndPassword method.
        // If the new account was created, the user is also signed in, and the AuthStateListener runs the onAuthStateChanged callback.
        // In the callback, you can use the getCurrentUser method to get the user's account data.

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
                        hideProgressDialog();

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(Firebaseregister.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            onAuthenticationSucess(task.getResult().getUser());
                        }


                    }
                });

    }


    private void onAuthenticationSucess(FirebaseUser mUser) {
        // Write new user
        saveNewUser(mUser.getUid(), user.getName(), user.getPhoneNumber(), user.getEmail(), user.getPassword());
        signOut();
        // Go to LoginActivity
        startActivity(new Intent(Firebaseregister.this, LoginActivity.class));
        finish();
    }

    private void saveNewUser(String userId, String name, String phone, String email, String password) {
        User user = new User(userId,name,phone,email,password);

        mRef.child("users").child(userId).setValue(user);
    }

    private void signOut() {
        auth.signOut();
    }

    private boolean validateForm() {
        boolean valid = true;

        String userEmail = editTextEmail.getText().toString();
        if (TextUtils.isEmpty(userEmail)) {
            editTextEmail.setError("Required.");
            valid = false;
        } else {
            editTextEmail.setError(null);
        }

        String userPassword = editTextPassword.getText().toString();
        if (TextUtils.isEmpty(userPassword)) {
            editTextPassword.setError("Required.");
            valid = false;
        } else {
            editTextPassword.setError(null);
        }

        return valid;
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.buttonSave){
            createNewAccount(editTextEmail.getText().toString(), editTextPassword.getText().toString());
            showProgressDialog();
        }

        else if(v.getId() == R.id.backbtn){
            Intent intentlogin = new Intent(Firebaseregister.this, LoginActivity.class);
            startActivity(intentlogin);
            finish();
        }
        else if(v.getId() == R.id.btnreset) {

                if (editTextEmail.hasFocus())
                {
                    editTextEmail.getText().clear();
                }
                else{
                    editTextPassword.getText().clear();
                }

        }

    }


}
