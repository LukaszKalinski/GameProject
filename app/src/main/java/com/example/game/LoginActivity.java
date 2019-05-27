package com.example.game;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    Button cancelButton;
    Button okButton;
    EditText username;
    EditText password;
    public static String loggedUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        cancelButton = (Button) findViewById(R.id.loginActCancelButton);
        okButton = (Button) findViewById(R.id.loginActOkButton);
        username = (EditText) findViewById(R.id.loginEditText);
        password = (EditText) findViewById(R.id.passwordEditText);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Closing Login Activity");
                finish();
            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if(username.length() > 0 && password.length() >0)
                    {
                        UsersDatabase dbUser = new UsersDatabase(LoginActivity.this);
                        dbUser.open();

                        if(dbUser.Login(username.getText().toString(), password.getText().toString()))
                        {
                            loggedUserName = username.getText().toString();
                            Toast.makeText(LoginActivity.this,"Successfully Logged In As Player: " + loggedUserName, Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(LoginActivity.this, FirstLogin.class);
                            startActivity(intent);

                        }else{
                            Toast.makeText(LoginActivity.this,"Invalid Username/Password", Toast.LENGTH_LONG).show();
                        }
                        dbUser.close();
                    }

                }catch(Exception e)
                {
                    Toast.makeText(LoginActivity.this,e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
