package com.rohan.loginapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class HomeActivity extends Activity {

    private Button btnSignIn, btnSignUp;
    LoginDataBaseAdapter loginDataBaseAdapter;
    PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        prefManager = new PrefManager(this);
        if(prefManager.isLoggedIn()){
            Intent intent = new Intent(HomeActivity.this, WelcomeActivity.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(this,"Not Logged in",Toast.LENGTH_SHORT).show();
        }

        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        loginDataBaseAdapter = loginDataBaseAdapter.open();

        btnSignIn = (Button) findViewById(R.id.buttonSignIN);
        btnSignUp = (Button) findViewById(R.id.buttonSignUP);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SignUp = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(SignUp);
            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignIn();
            }
        });
    }

    public void SignIn(){
        final Dialog dialog = new Dialog(HomeActivity.this);
        dialog.setContentView(R.layout.login);
        dialog.setTitle("Login");
        final EditText editTextUserNameToLogin = (EditText) dialog.findViewById(R.id.editTextUserNameToLogin);
        final EditText editTextPasswordToLogin = (EditText) dialog.findViewById(R.id.editTextPasswordToLogin);
        Button DbtnSignIn = (Button) dialog.findViewById(R.id.DbuttonSignIn);

        DbtnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String UserName = editTextUserNameToLogin.getText().toString();
                String Password = editTextPasswordToLogin.getText().toString();
                String storedPassword = loginDataBaseAdapter.getSingleEntry(UserName);
                if(Password.equals(storedPassword)){
                    Toast.makeText(HomeActivity.this,"Login Successful",Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                    Intent main = new Intent(HomeActivity.this,WelcomeActivity.class);
                    startActivity(main);
                    prefManager.setLoggedIn(true);

                    }
                else {
                    Toast.makeText(HomeActivity.this,"Username or Password does not match", Toast.LENGTH_LONG).show();
                }
            }
        });
        dialog.show();
    }

    protected void onDestroy(){
        super.onDestroy();
        loginDataBaseAdapter.close();
    }
}
