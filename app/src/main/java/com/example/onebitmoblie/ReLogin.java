package com.example.onebitmoblie;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class ReLogin  extends Activity {
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.relogin);
        TextView txt_username, txt_password;
        Button btn_login;


        txt_username  = findViewById(R.id.txt_username);
        txt_password  = findViewById(R.id.txt_password);
        btn_login = findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName, password;
                userName = txt_username.getText().toString();
                password = txt_password.getText().toString();

                if(userName.equals("a") && password.equals("123")){

                }else{

                }


            }
        });

    }
}
