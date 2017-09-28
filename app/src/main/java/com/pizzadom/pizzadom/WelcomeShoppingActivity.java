package com.pizzadom.pizzadom;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;


import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by User on 29/9/2017.
 */

public class WelcomeShoppingActivity extends AppCompatActivity {
    private static final String TAG = "AndroidBash";
    private Firebase myFirebaseRef;
    private FirebaseAuth mAuth;
    private TextView name;


    // To hold Facebook profile picture
    private ImageView profilePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcomeshopping);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Creates a reference for  your Firebase database
        //Add YOUR Firebase Reference URL instead of the following URL
        myFirebaseRef = new Firebase("https://pizzadom-3caec.firebaseio.com/users/");
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        name = (TextView) findViewById(R.id.text_view_name);

        profilePicture = (ImageView) findViewById(R.id.profile_picture);
        //Get the uid for the currently logged in User from intent data passed to this activity
        String uid = getIntent().getExtras().getString("user_id");
        //Get the imageUrl  for the currently logged in User from intent data passed to this activity
        String imageUrl = getIntent().getExtras().getString("profile_picture");

        new ImageLoadTask(imageUrl, profilePicture).execute();

        //Referring to the name of the User who has logged in currently and adding a valueChangeListener
        myFirebaseRef.child(uid).child("name").addValueEventListener(new ValueEventListener() {
            //onDataChange is called every time the name of the User changes in your Firebase Database
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Inside onDataChange we can get the data as an Object from the dataSnapshot
                //getValue returns an Object. We can specify the type by passing the type expected as a parameter
                String data = dataSnapshot.getValue(String.class);
                name.setText("Hello " + data + ", ");
            }



            //onCancelled is called in case of any error
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Toast.makeText(getApplicationContext(), "" + firebaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }

    private class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

        private String url;
        private ImageView imageView;

        public ImageLoadTask(String url, ImageView imageView) {
            this.url = url;
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            try {
                URL urlConnection = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) urlConnection
                        .openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            imageView.setImageBitmap(result);
        }


    }
}
