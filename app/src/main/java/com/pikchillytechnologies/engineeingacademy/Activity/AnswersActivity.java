package com.pikchillytechnologies.engineeingacademy.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.pikchillytechnologies.engineeingacademy.Adapter.ResultAdapter;
import com.pikchillytechnologies.engineeingacademy.Model.ResultModel;
import com.pikchillytechnologies.engineeingacademy.R;

import java.util.ArrayList;
import java.util.List;

public class AnswersActivity extends AppCompatActivity {

    private TextView m_TextView_Activity_Title;
    private Button m_Button_Back;

    private List<ResultModel> m_Result_List;
    private RecyclerView m_RecyclerView_Result_List;
    private ResultAdapter m_Result_Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answers);

        m_TextView_Activity_Title = findViewById(R.id.textView_Activity_Title);
        m_TextView_Activity_Title.setText("Answers");

        m_Button_Back = findViewById(R.id.button_Back);
        m_Button_Back.setVisibility(View.VISIBLE);

        m_Result_List = new ArrayList<>();
        m_RecyclerView_Result_List = findViewById(R.id.recyclerView_Ans_Answers);
        m_Result_Adapter = new ResultAdapter(m_Result_List);

        m_RecyclerView_Result_List.setHasFixedSize(true);

        RecyclerView.LayoutManager m_Layout_Manager = new LinearLayoutManager(getApplicationContext());
        m_RecyclerView_Result_List.setLayoutManager(m_Layout_Manager);

        m_RecyclerView_Result_List.setAdapter(m_Result_Adapter);

        prepareResultData();
    }

    public void prepareResultData(){

        ResultModel result = new ResultModel("Question 01","This is My Question 1","This is Ans 1","This is Ans 2","This is Ans 3","This is Ans 4","This is Explaination.");
        m_Result_List.add(result);

        result = new ResultModel("Question 02","This is My Question 2","This is Ans 1","This is Ans 2","This is Ans 3","This is Ans 4","This is Explaination.");
        m_Result_List.add(result);

        result = new ResultModel("Question 03","This is My Question 3","This is Ans 1","This is Ans 2","This is Ans 3","This is Ans 4","This is Explaination.");
        m_Result_List.add(result);

        result = new ResultModel("Question 04","This is My Question 4","This is Ans 1","This is Ans 2","This is Ans 3","This is Ans 4","This is Explaination.");
        m_Result_List.add(result);

        result = new ResultModel("Question 05","This is My Question 5","This is Ans 1","This is Ans 2","This is Ans 3","This is Ans 4","This is Explaination.");
        m_Result_List.add(result);

        result = new ResultModel("Question 06","This is My Question 6","This is Ans 1","This is Ans 2","This is Ans 3","This is Ans 4","This is Explaination.");
        m_Result_List.add(result);

        result = new ResultModel("Question 07","This is My Question 7","This is Ans 1","This is Ans 2","This is Ans 3","This is Ans 4","This is Explaination.");
        m_Result_List.add(result);

        result = new ResultModel("Question 08","This is My Question 8","This is Ans 1","This is Ans 2","This is Ans 3","This is Ans 4","This is Explaination.");
        m_Result_List.add(result);

        result = new ResultModel("Question 09","This is My Question 9","This is Ans 1","This is Ans 2","This is Ans 3","This is Ans 4","This is Explaination.");
        m_Result_List.add(result);

        result = new ResultModel("Question 10","This is My Question 10","This is Ans 1","This is Ans 2","This is Ans 3","This is Ans 4","This is Explaination.");
        m_Result_List.add(result);

    }
}
