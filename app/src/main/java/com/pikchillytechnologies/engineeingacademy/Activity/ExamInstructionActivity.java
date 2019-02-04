package com.pikchillytechnologies.engineeingacademy.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.pikchillytechnologies.engineeingacademy.R;

public class ExamInstructionActivity extends AppCompatActivity {

    private Button m_Button_Start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_instruction);

        m_Button_Start = findViewById(R.id.button_Start);

        m_Button_Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ExamInstructionActivity.this, ExamActivity.class));
            }
        });
    }
}
