/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.parse.ParseAnalytics;

//import com.google.android.gms.appindexing.Action;
//import com.google.android.gms.appindexing.AppIndex;
//import com.google.android.gms.common.api.GoogleApiClient;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener {

  EditText usernameField;
  EditText passwordField;
  Button signUpButton;
  Button logInButton;

  ImageView logo;
  RelativeLayout relativeLayout;

  @Override
  public boolean onKey(View v, int keyCode, KeyEvent event) {


    if(keyCode==KeyEvent.KEYCODE_ENTER && event.getAction()==KeyEvent.ACTION_DOWN){

      logIn(v);
    }

    return false;
  }

  @Override
  public void onClick(View v) {

   if (v.getId()==R.id.logo || v.getId()==R.id.relativeLayout){
      InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
      imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
    }
    else if (v.getId()==R.id.signUpButton){
     Intent i = new Intent(getApplicationContext(),Register.class);
     startActivity(i);
   }

  }

  public void logIn(View view) {
/*
      ParseUser user = new ParseUser();
      user.setUsername(usernameField.getText().toString());
      user.setPassword(passwordField.getText().toString());

      user.signUpInBackground(new SignUpCallback() {
        @Override
        public void done(ParseException e) {
          if (e == null){
              Log.i("Signup status", "Sucessful");
              showUserList();
          }
          else {
            Log.i("Signup status", "Failed");
            Toast.makeText(getApplicationContext(), "Šis lietotājvārds ir aizņemts", Toast.LENGTH_LONG).show();

          }
        }
      });
*/
      ParseUser.logInInBackground(String.valueOf(usernameField.getText()), String.valueOf(passwordField.getText()), new LogInCallback() {
        @Override
        public void done(ParseUser user, ParseException e) {
          if (user !=null){
            Log.i("Appinfo", "Login sucess");
              showUserList();

          }
          else if (usernameField.getText().toString().isEmpty() || passwordField.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Nav ievadīts lietotājvārds vai parole", Toast.LENGTH_SHORT).show();
          }
          else Toast.makeText(getApplicationContext(), "Nepareizs lietotājvārds vai parole", Toast.LENGTH_SHORT).show();
        }
      });

  }

    public void showUserList(){
        Intent i = new Intent(getApplicationContext(),UserList.class);
        startActivity(i);
    }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);



    usernameField = (EditText) findViewById(R.id.username);
    passwordField = (EditText) findViewById(R.id.password);
    signUpButton = (Button) findViewById(R.id.signUpButton);
    logInButton = (Button) findViewById(R.id.logInButton);

    logo = (ImageView) findViewById(R.id.logo);
    relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);

    signUpButton.setOnClickListener(this);

    logo.setOnClickListener(this);
    relativeLayout.setOnClickListener(this);

    //usernameField.setOnKeyListener(this);//nevajag klausīties username
    passwordField.setOnKeyListener(this);


      if(ParseUser.getCurrentUser()!=null){
        Log.i("AppInfo",ParseUser.getCurrentUser()+"");
          showUserList();

      }

    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement

    return super.onOptionsItemSelected(item);
  }

}
