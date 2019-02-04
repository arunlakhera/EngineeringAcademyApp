package com.pikchillytechnologies.engineeingacademy.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.pikchillytechnologies.engineeingacademy.R;

public class SignUpActivity extends AppCompatActivity {

    private Button m_Sign_Up_Button;
    private TextView m_Sign_In_TextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        m_Sign_Up_Button = findViewById(R.id.button_Sign_Up);
        m_Sign_In_TextView = findViewById(R.id.textview_Sign_In);

        m_Sign_Up_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, CoursesActivity.class));
            }
        });

        m_Sign_In_TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this,SignInActivity.class));
            }
        });

    }
}
