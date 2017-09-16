package com.pizzadom.pizzadom;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


/**
 * Created by User on 24/6/2017.
 */

public class LoginActivity extends AppCompatActivity implements OnClickListener {
    Button btnLogin,btnSignUp, btnForgotPass;
    ImageButton btnback, btnforward;
    EditText input_email, input_password;

    private FirebaseAuth auth;
    private CoordinatorLayout login;
    //progress dialog
    private ProgressDialog progressDialog;


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

        progressDialog = new ProgressDialog(this);
        btnSignUp.setOnClickListener(this);
        btnForgotPass.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btnback.setOnClickListener(this);
        btnforward.setOnClickListener(this);
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

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

           if(TextUtils.isEmpty(password)) {
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
                           Bundle b = new Bundle();
                           b.putString("email", input_email.getText().toString().trim());

                           if(task.isSuccessful()){

                               progressDialog.setMessage("Logging in Please Wait...");
                               progressDialog.setCancelable(true);
                               progressDialog.setCanceledOnTouchOutside(false);
                               progressDialog.show();
                               progressDialog.dismiss();
                               Intent intentorder = new Intent(LoginActivity.this, ShoppingActivity.class);
                               intentorder.putExtras(b);
                               startActivity(intentorder);
                               finish();
                           }
                             else{
                               Snackbar snackbar = Snackbar

                                       .make(login , "Please enter credentials again", Snackbar.LENGTH_INDEFINITE);

                               View snackbarView = snackbar.getView();
                               snackbarView.setBackgroundColor(Color.WHITE);
                               TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                               textView.setTextColor(Color.BLUE);

                               snackbar.show();

                           }

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
