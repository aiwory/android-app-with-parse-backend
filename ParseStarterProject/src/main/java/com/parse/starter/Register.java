package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class Register extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener {

    EditText usernameField;
    EditText passwordField;
    EditText repeatPasswordField;
    Button registerButton;
    RelativeLayout relativeLayout;


    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.reg_background){
            InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {

        if(keyCode==KeyEvent.KEYCODE_ENTER && event.getAction()==KeyEvent.ACTION_DOWN){

            register(v);
        }
        return false;
    }

    public void register(View view) {



        if(!passwordField.getText().toString().equals(repeatPasswordField.getText().toString())){
            Toast.makeText(getApplicationContext(), "Paroles nesakrīt!", Toast.LENGTH_SHORT).show();
        }
        else if(usernameField.getText().toString().isEmpty()
                ||passwordField.getText().toString().isEmpty()
                ||repeatPasswordField.getText().toString().isEmpty()){

            Toast.makeText(getApplicationContext(), "Nav ievadīts lietotājvārds vai parole", Toast.LENGTH_SHORT).show();
        }
        else {
            ParseUser user = new ParseUser();
            user.setUsername(usernameField.getText().toString());
            user.setPassword(passwordField.getText().toString());

            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        Log.i("Signup status", "Sucessful");
                        showUserList();
                    }

                    else {
                        Log.i("Signup status", "Failed");
                        Toast.makeText(getApplicationContext(), "Šis lietotājvārds ir aizņemts", Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
    }

    public void showUserList(){
        Intent i = new Intent(getApplicationContext(),UserList.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        usernameField = (EditText) findViewById(R.id.r_username);
        passwordField = (EditText) findViewById(R.id.r_password);
        repeatPasswordField = (EditText) findViewById(R.id.repeatPassword);
        registerButton = (Button) findViewById(R.id.registerButton);
        relativeLayout = (RelativeLayout) findViewById(R.id.reg_background);

        repeatPasswordField.setOnKeyListener(this);
        relativeLayout.setOnClickListener(this);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
