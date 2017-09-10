package com.pizzadom.pizzadom;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pizzadom.pizzadom.adapters.ShopRecyclerViewAdapter;
import com.pizzadom.pizzadom.entities.ProductObject;
import com.pizzadom.pizzadom.helpers.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class ShoppingActivity extends AppCompatActivity {

    private static final String TAG = ShoppingActivity.class.getSimpleName();

    private RecyclerView shoppingRecyclerView;

    private TextView txtwelcome;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Bundle b = getIntent().getExtras();
        txtwelcome = (TextView) findViewById(R.id.sessionwelcome);
        shoppingRecyclerView = (RecyclerView)findViewById(R.id.product_list);
        GridLayoutManager mGrid = new GridLayoutManager(ShoppingActivity.this, 2);
        shoppingRecyclerView.setLayoutManager(mGrid);
        shoppingRecyclerView.setHasFixedSize(true);
        shoppingRecyclerView.addItemDecoration(new SpacesItemDecoration(2, 12, false));

        ShopRecyclerViewAdapter shopAdapter = new ShopRecyclerViewAdapter(ShoppingActivity.this, getAllProductsOnSale());
        shoppingRecyclerView.setAdapter(shopAdapter);



        txtwelcome.setText("welcome " + b.getCharSequence("email"));

    }




    private List<ProductObject> getAllProductsOnSale(){
        List<ProductObject> products = new ArrayList<ProductObject>();
        products.add(new ProductObject(1, "Sleek Black Top", R.drawable.productonesmall, "Beautiful sleek black top for casual outfit and evening walk", 20, 38, "Black"));
        products.add(new ProductObject(1, "Flare Black Gown", R.drawable.producttwo, "Beautiful sleek black top for casual outfit and evening walk", 20, 38, "Black"));
        products.add(new ProductObject(1, "Flare White Blouse", R.drawable.productthree, "Beautiful sleek black top for casual outfit and evening walk", 20, 38, "White"));
        products.add(new ProductObject(1, "Blue Swed Gown", R.drawable.productfour, "Beautiful sleek black top for casual outfit and evening walk", 20, 38, "Dark Blue"));
        products.add(new ProductObject(1, "Spotted Gown", R.drawable.productfive, "Beautiful sleek black top for casual outfit and evening walk", 20, 38, "Spotted Green"));
        products.add(new ProductObject(1, "Flare Wax Gown", R.drawable.productsix, "Beautiful sleek black top for casual outfit and evening walk", 20, 38, "Multi-color"));
        return products;
    }





}
