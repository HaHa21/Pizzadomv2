package com.pizzadom.pizzadom;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by User on 23/5/2017.
 */

public class EmailActivity extends AppCompatActivity implements View.OnClickListener {



    Button btnsend;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        btnsend = (Button) findViewById(R.id.btnsend);

        btnsend.setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnsend){
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setData(Uri.parse("mailto: "));
            String[] to = {"domlee2012@gmail.com"};
            intent.putExtra(Intent.EXTRA_EMAIL, to);
            intent.putExtra(Intent.EXTRA_SUBJECT, "Complain Email ");


            intent.setType("message/rfc822");



            startActivity(intent.createChooser(intent, "Pick email client: "));

        }
    }
}



