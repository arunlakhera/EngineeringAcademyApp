package com.pikchillytechnologies.engineeingacademy.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.pikchillytechnologies.engineeingacademy.HelperFiles.EAHelper;
import com.pikchillytechnologies.engineeingacademy.R;

public class SignInActivity extends AppCompatActivity {

    private Button m_Sign_In_Button;
    private TextView m_Sign_Up_TextView;
    private EAHelper m_Helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        m_Helper = new EAHelper();

        m_Sign_Up_TextView = findViewById(R.id.textview_New_Sign_Up);
        m_Sign_In_Button = findViewById(R.id.button_Sign_In);

        m_Sign_In_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                m_Helper.start_Activity(SignInActivity.this, CoursesActivity.class);
            }
        });

        m_Sign_Up_TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_Helper.start_Activity(SignInActivity.this, SignUpActivity.class);
            }
        });

    }
}
