package com.pikchillytechnologies.engineeingacademy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SignInActivity extends AppCompatActivity {

    private Button m_Sign_In_Button;
    private TextView m_Sign_Up_TextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        m_Sign_Up_TextView = findViewById(R.id.textview_New_Sign_Up);
        m_Sign_In_Button = findViewById(R.id.button_Sign_In);

        m_Sign_In_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(SignInActivity.this,CoursesActivity.class));

            }
        });

        m_Sign_Up_TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this,SignUpActivity.class));
            }
        });

    }
}
