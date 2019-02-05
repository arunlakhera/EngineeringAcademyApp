package com.pikchillytechnologies.engineeingacademy.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.pikchillytechnologies.engineeingacademy.Adapter.ExamListAdapter;
import com.pikchillytechnologies.engineeingacademy.Adapter.ResultAdapter;
import com.pikchillytechnologies.engineeingacademy.Model.ExamListModel;
import com.pikchillytechnologies.engineeingacademy.Model.ResultModel;
import com.pikchillytechnologies.engineeingacademy.R;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity {

    private TextView m_TextView_Activity_Title;
    private Button m_Button_Res_View_Answers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        m_TextView_Activity_Title = findViewById(R.id.textView_Activity_Title);
        m_TextView_Activity_Title.setText("Result");

        m_Button_Res_View_Answers = findViewById(R.id.button_Res_View_Answers);

        m_Button_Res_View_Answers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResultActivity.this, AnswersActivity.class));
            }
        });
    }

}
