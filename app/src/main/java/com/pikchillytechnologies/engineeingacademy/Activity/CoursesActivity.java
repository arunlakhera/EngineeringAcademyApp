package com.pikchillytechnologies.engineeingacademy.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.pikchillytechnologies.engineeingacademy.R;

public class CoursesActivity extends AppCompatActivity {

    CardView m_CardView_Mechanical;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        m_CardView_Mechanical = findViewById(R.id.cardView_Mechanical);

        m_CardView_Mechanical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CoursesActivity.this, SubCoursesActivity.class));
            }
        });

    }
}
