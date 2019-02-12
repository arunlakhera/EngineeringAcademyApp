package com.pikchillytechnologies.engineeingacademy.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.pikchillytechnologies.engineeingacademy.R;

public class ArticlesActivity extends AppCompatActivity {

    private TextView m_TextView_Activity_Title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);

        m_TextView_Activity_Title = findViewById(R.id.textView_Activity_Title);
        m_TextView_Activity_Title.setText("Articles");
    }
}