package com.pikchillytechnologies.engineeingacademy.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.pikchillytechnologies.engineeingacademy.Adapter.AnswersAdapter;
import com.pikchillytechnologies.engineeingacademy.Model.AnswersModel;
import com.pikchillytechnologies.engineeingacademy.R;

import java.util.ArrayList;
import java.util.List;

public class AnswersActivity extends AppCompatActivity {

    private TextView m_TextView_Activity_Title;
    private Button m_Button_Back;
    private Bundle m_Exam_Answer_Bundle;

    private String m_User_Id;
    private String m_User_Name;
    private String m_Exam_Id;
    private String m_Title;

    private List<AnswersModel> m_Answer_List;
    private RecyclerView m_RecyclerView_Answers_List;
    private AnswersAdapter m_Answers_Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answers);

        m_Exam_Answer_Bundle = getIntent().getExtras();
        m_User_Id = m_Exam_Answer_Bundle.getString(getResources().getString(R.string.userid),"User Id");
        m_User_Name = m_Exam_Answer_Bundle.getString("username", "User Name");
        m_Exam_Id = m_Exam_Answer_Bundle.getString(getResources().getString(R.string.examid), "Exam Id");
        m_Title = m_Exam_Answer_Bundle.getString(getResources().getString(R.string.title), "Exam");

        m_TextView_Activity_Title = findViewById(R.id.textView_Activity_Title);
        m_TextView_Activity_Title.setText(m_Title);

        m_Button_Back = findViewById(R.id.button_Back);
        m_Button_Back.setVisibility(View.VISIBLE);

        m_Answer_List = new ArrayList<>();
        m_RecyclerView_Answers_List = findViewById(R.id.recyclerView_Answers);
        m_Answers_Adapter = new AnswersAdapter(m_Answer_List);

        m_RecyclerView_Answers_List.setHasFixedSize(true);

        RecyclerView.LayoutManager m_Layout_Manager = new LinearLayoutManager(getApplicationContext());
        m_RecyclerView_Answers_List.setLayoutManager(m_Layout_Manager);

        m_RecyclerView_Answers_List.setAdapter(m_Answers_Adapter);

        prepareResultData();
    }

    public void prepareResultData(){

        AnswersModel answer1 = new AnswersModel("Question 01","This is My Question 1","This is Ans 1","This is Ans 2","This is Ans 3","This is Ans 4","This is Explaination.");
        AnswersModel answer2 = new AnswersModel("Question 02","This is My Question 2","This is Ans 1","This is Ans 2","This is Ans 3","This is Ans 4","This is Explaination.");
        AnswersModel answer3 = new AnswersModel("Question 03","This is My Question 3","This is Ans 1","This is Ans 2","This is Ans 3","This is Ans 4","This is Explaination.");
        AnswersModel answer4 = new AnswersModel("Question 04","This is My Question 4","This is Ans 1","This is Ans 2","This is Ans 3","This is Ans 4","This is Explaination.");
        AnswersModel answer5 = new AnswersModel("Question 05","This is My Question 5","This is Ans 1","This is Ans 2","This is Ans 3","This is Ans 4","This is Explaination.");

        m_Answer_List.add(answer1);
        m_Answer_List.add(answer2);
        m_Answer_List.add(answer3);
        m_Answer_List.add(answer4);
        m_Answer_List.add(answer5);


    }
}
