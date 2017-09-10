package com.pizzadom.pizzadom;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static com.pizzadom.pizzadom.R.id.editName;
import static com.pizzadom.pizzadom.R.id.editPhone;

/**
 * Created by User on 24/6/2017.
 */

public class Firebaseregister extends AppCompatActivity implements View.OnClickListener{
    private EditText editTextEmail, editTextPassword, editName, editPhone;
    private Button buttonSavestate, buttonreset;
    private ImageButton btnback, btnforward;
    Snackbar snackbar;
    private CoordinatorLayout insertlayout;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.insertactivity);


        Firebase.setAndroidContext(this);
        auth = FirebaseAuth.getInstance();
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
    public void onClick(View v) {
        if(v.getId() == R.id.buttonSave){
            signUpUser(editTextEmail.getText().toString().trim(), editTextPassword.getText().toString().trim());
            signotherdetails(editName.getText().toString().trim(), editPhone.getText().toString().trim());
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

    private void signotherdetails(String name, String phone) {
        Firebase ref = new Firebase(Config.FIREBASE_URL);


        Person person = new Person(name, phone);

        person.setPhone(phone);
        person.setName(name);

        ref.child("Person").setValue(person);

    }

    public void signUpUser(String email, String password) {

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(Firebaseregister.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
               if(!task.isSuccessful()){
                   snackbar = Snackbar.make(insertlayout, "Error: " + task.getException(), snackbar.LENGTH_SHORT);
                   View snackBarView = snackbar.getView();
                   snackBarView.setBackgroundColor(Color.parseColor("#ff21ab29")); // snackbar background color
                   snackbar.setActionTextColor(Color.parseColor("#FFFFEE19")); // snackbar action text color
                   snackbar.show();
               }
               else{
                   snackbar = Snackbar.make(insertlayout, "Sucess in creating user ", snackbar.LENGTH_SHORT);
                   View snackBarView2 = snackbar.getView();
                   snackBarView2.setBackgroundColor(Color.parseColor("#ff21ab29")); // snackbar background color
                   snackbar.setActionTextColor(Color.parseColor("#FFFFEE19")); // snackbar action text color
                   snackbar.show();
               }
            }
        });

    }
}
