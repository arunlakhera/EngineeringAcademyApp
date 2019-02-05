package com.pikchillytechnologies.engineeingacademy.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.solver.widgets.Helper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.pikchillytechnologies.engineeingacademy.HelperFiles.EAHelper;
import com.pikchillytechnologies.engineeingacademy.R;

public class CoursesActivity extends AppCompatActivity {

    private TextView m_TextView_Activity_Title;

    private CardView m_CardView_Mechanical;
    private CardView m_CardView_Civil;
    private CardView m_CardView_Railways;
    private CardView m_CardView_Computer;
    private CardView m_CardView_Miscellaneous;

    private EAHelper m_Helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        m_Helper = new EAHelper();

        // Set the title of the courses activity
        m_TextView_Activity_Title = findViewById(R.id.textView_Activity_Title);
        m_TextView_Activity_Title.setText(R.string.courses);

        m_CardView_Mechanical = findViewById(R.id.cardView_Mechanical);
        m_CardView_Civil = findViewById(R.id.cardView_Civil);
        m_CardView_Railways = findViewById(R.id.cardView_Railways);
        m_CardView_Computer = findViewById(R.id.cardView_Computer);
        m_CardView_Miscellaneous = findViewById(R.id.cardView_Miscellaneous);

        m_CardView_Mechanical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_Helper.start_Activity(CoursesActivity.this, SubCoursesActivity.class, getResources().getString(R.string.mechanical));
            }
        });

        m_CardView_Civil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_Helper.start_Activity(CoursesActivity.this, SubCoursesActivity.class, getResources().getString(R.string.civil));
            }
        });

        m_CardView_Railways.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_Helper.start_Activity(CoursesActivity.this, SubCoursesActivity.class, getResources().getString(R.string.railways));
            }
        });

        m_CardView_Computer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_Helper.start_Activity(CoursesActivity.this, SubCoursesActivity.class, getResources().getString(R.string.computer));
            }
        });

        m_CardView_Miscellaneous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_Helper.start_Activity(CoursesActivity.this, SubCoursesActivity.class, getResources().getString(R.string.miscellaneous));
            }
        });

    }


}
