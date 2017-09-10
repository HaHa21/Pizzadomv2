package com.pizzadom.pizzadom;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;


import java.util.HashMap;


public class MainActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener,
        ViewPagerEx.OnPageChangeListener, OnClickListener {
    private android.support.v7.widget.ShareActionProvider shareActionProvider;

    private SliderLayout imageSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Pizzadom");
        setSupportActionBar(toolbar);


        Button login = (Button) findViewById(R.id.logintext);

        imageSlider = (SliderLayout)findViewById(R.id.slider);

        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
          file_maps.put("Beef_Pepperoni_Pizza",R.drawable.photo1);
          file_maps.put("Cheezy_Pizza",R.drawable.photo2);
          file_maps.put("Vegetarian_Pizza",R.drawable.photo3);


        for(String name : file_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            imageSlider.addSlider(textSliderView);
        }

        imageSlider.setPresetTransformer(SliderLayout.Transformer.DepthPage);
        imageSlider.setCustomIndicator((PagerIndicator) findViewById(R.id.custom_indicator));
        imageSlider.setCustomAnimation(new DescriptionAnimation());
        imageSlider.setDuration(12000);
        imageSlider.addOnPageChangeListener(this);
        login.setOnClickListener(this);


    }


    /*public void login(View v) {

            Intent intentlogin = new Intent(this , LoginActivity.class);
            startActivity(intentlogin);

    }*/

    @Override
    protected void onStop() {
        /* make sure to call stopAutoCycle() on the slider before activity
        or fragment is destroyed*/
        imageSlider.stopAutoCycle();
        super.onStop();
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
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(this,slider.getBundle().get("extra") + " ",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Log.e("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.logintext) {
            Intent intentlogin = new Intent(this, LoginActivity.class);
            startActivity(intentlogin);
            finish();
        }
    }
}
