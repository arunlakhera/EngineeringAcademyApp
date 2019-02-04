package com.pikchillytechnologies.engineeingacademy.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.Scroller;
import android.widget.TextView;

import com.pikchillytechnologies.engineeingacademy.R;

public class ExamActivity extends AppCompatActivity {

    TextView m_TextView_Question;
    Button m_Button_Submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);

        m_TextView_Question = findViewById(R.id.textView_Question);
        m_Button_Submit = findViewById(R.id.button_Submit);

        // To make textview scrollable of Question
        m_TextView_Question.setMovementMethod(new ScrollingMovementMethod());

        m_Button_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ExamActivity.this, ResultActivity.class));
            }
        });

    }
}
