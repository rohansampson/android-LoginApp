package com.rohan.loginapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends Activity {

    EditText editTextUserName, editTextPassword, editTextConfirmPassoword;
    Button btnCreateAccount;
    Context context;
    LoginDataBaseAdapter loginDataBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        loginDataBaseAdapter = loginDataBaseAdapter.open();
        editTextUserName = (EditText) findViewById(R.id.editTextUserName);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextConfirmPassoword = (EditText) findViewById(R.id.editTextConfirmPassword);

        btnCreateAccount = (Button) findViewById(R.id.buttonCreateAccount);
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUserName.getText().toString();
                String password = editTextPassword.getText().toString();
                String confirmPassword = editTextPassword.getText().toString();
                if(username.equals("")||password.equals("")||confirmPassword.equals("")){
                    Toast.makeText(getApplicationContext(),"Field Vacant",Toast.LENGTH_LONG).show();
                    return;
                }
                if(!password.equals(confirmPassword)){
                    Toast.makeText(getApplicationContext(),"Passwords do not match",Toast.LENGTH_LONG).show();
                    return;
                }
                else {
                    loginDataBaseAdapter.InsertEntry(username,password);
                    Toast.makeText(getApplicationContext(),"Account Successfully Created",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    protected void onDestroy() {
        super.onDestroy();
        loginDataBaseAdapter.close();
    }
}
