package com.example.game;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateAccountActivity extends AppCompatActivity {

    Button createAccountButton;
    Button cancelButton;
    EditText createLogin;
    EditText createPassword;
    String createdLogin;
    String createdPassword;

    UsersDatabase db = new UsersDatabase(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        createAccountButton = (Button) findViewById(R.id.loginActOkButton);
        cancelButton = (Button) findViewById(R.id.loginActCancelButton);
        createLogin = (EditText) findViewById(R.id.loginEditText);
        createPassword = (EditText) findViewById(R.id.passwordEditText);

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createdLogin = createLogin.getText().toString();
                createdPassword = createPassword.getText().toString();

           if (createdLogin.length() > 0 && createdPassword.length() > 0){
               db.open();

               if (db.checkUserName(createdLogin)) {
                   Toast.makeText(CreateAccountActivity.this,"Login Is Already Taken, Please Change It", Toast.LENGTH_LONG).show();

               } else {
                   db.AddUser(createdLogin, createdPassword);
                   Toast.makeText(CreateAccountActivity.this,"Created Login: " + createdLogin + ", password: " + createdPassword, Toast.LENGTH_LONG).show();
                   db.close();

                   Intent intent = new Intent(CreateAccountActivity.this, MainActivity.class);
                   startActivity(intent);
               }


           } else {
               Toast.makeText(CreateAccountActivity.this,"Please enter both LOGIN and PASSWORD", Toast.LENGTH_LONG).show();
           }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
