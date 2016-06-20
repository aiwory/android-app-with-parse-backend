package com.parse.starter;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UserFeed extends AppCompatActivity {

    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_feed);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        linearLayout = (LinearLayout) findViewById(R.id.imageLayout);



        Intent i = getIntent();
        String activeUserName = i.getStringExtra("username");
        String activeId = i.getStringExtra("user_id");
        Log.i("AppInfo", activeUserName);
        Log.i("AppInfo", activeId);

        if(ParseUser.getCurrentUser().getObjectId().equals(activeId)){
            setTitle(activeUserName);
        }
        else {
            setTitle(activeUserName + " attēli");
        }

        ParseUser user=new ParseUser();
        user.setObjectId(activeId);
        ParseQuery <ParseObject> query = new ParseQuery<>("Images");
        query.whereEqualTo("user_id",user);
        query.addDescendingOrder("createdAt");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    if (objects.size() > 0) {

                        for (ParseObject object : objects) {
                            ParseFile file = (ParseFile) object.get("image");

                            Date createDate;
                            createDate = object.getCreatedAt();

                            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                            final String dateString = sdf.format(createDate);


                            file.getDataInBackground(new GetDataCallback() {
                                @Override
                                public void done(byte[] data, ParseException f) {
                                    if (f == null) {

                                        Bitmap image = BitmapFactory.decodeByteArray(data, 0, data.length);


                                        ImageView imageHolder = new ImageView(getBaseContext());

                                        TextView dateHolder = new TextView(getBaseContext());

                                        dateHolder.setGravity(Gravity.CENTER_HORIZONTAL);

                                        dateHolder.setText("Pievienots: " + dateString);
                                        dateHolder.setTextColor(Color.parseColor("#000000"));


                                        Log.i("DateTest", dateHolder.getText().toString() + "");

                                        dateHolder.setLayoutParams(new ViewGroup.LayoutParams(
                                                ViewGroup.LayoutParams.MATCH_PARENT,
                                                ViewGroup.LayoutParams.WRAP_CONTENT
                                        ));

                                        imageHolder.setImageBitmap(image);
                                        imageHolder.setScaleType(ImageView.ScaleType.FIT_CENTER);
                                        imageHolder.setLayoutParams(new ViewGroup.LayoutParams(
                                                ViewGroup.LayoutParams.MATCH_PARENT,
                                                ViewGroup.LayoutParams.WRAP_CONTENT
                                        ));
                                        linearLayout.addView(dateHolder);
                                        linearLayout.addView(imageHolder);
                                        Log.i("AppInfo", "Images loaded");

                                    } else {
                                        Log.i("AppInfo", "No images");
                                    }
                                }
                            });
                        }
                    } else {
                        Log.i("AppInfo", "Error");
                        //e.printStackTrace();
                    }
                }
            }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
