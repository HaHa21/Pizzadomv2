package com.pizzadom.pizzadom;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;


import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;


import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener,
        ViewPagerEx.OnPageChangeListener, OnClickListener {
    private android.support.v7.widget.ShareActionProvider shareActionProvider;
    private PopupWindow pw;

    @BindView(R.id.btnpopup)
    public Button btnpopup;
    @BindView(R.id.logintext)
    public Button login;
    @BindView(R.id.goshopping)
    public Button gotoshopping;
    @BindView(R.id.slider)
    public SliderLayout imageSlider;
    @BindView(R.id.toolbar)
    public Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        toolbar.setTitle("Pizzadom");
        setSupportActionBar(toolbar);



        HashMap<String, Integer> file_maps = new HashMap<>();
        file_maps.put("Beef_Pepperoni_Pizza", R.drawable.photo1);
        file_maps.put("Cheezy_Pizza", R.drawable.photo2);
        file_maps.put("Vegetarian_Pizza", R.drawable.photo3);


        for (String name : file_maps.keySet()) {
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
                    .putString("extra", name);

            imageSlider.addSlider(textSliderView);
        }

        imageSlider.setPresetTransformer(SliderLayout.Transformer.DepthPage);
        imageSlider.setCustomIndicator((PagerIndicator) findViewById(R.id.custom_indicator));
        imageSlider.setCustomAnimation(new DescriptionAnimation());
        imageSlider.setDuration(12000);
        imageSlider.addOnPageChangeListener(this);
        login.setOnClickListener(this);
        btnpopup.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                LayoutInflater layoutInflater
                        = (LayoutInflater)getBaseContext()
                        .getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = layoutInflater.inflate(R.layout.ad_content, null);
                final PopupWindow popupWindow = new PopupWindow(
                        popupView,
                        LayoutParams.WRAP_CONTENT,
                       LayoutParams.WRAP_CONTENT);

                Button btnDismiss = (Button)popupView.findViewById(R.id.dismiss);
                btnDismiss.setOnClickListener(new Button.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        popupWindow.dismiss();
                    }});

                popupWindow.showAsDropDown(btnpopup, 50, 150);

            }});



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
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
