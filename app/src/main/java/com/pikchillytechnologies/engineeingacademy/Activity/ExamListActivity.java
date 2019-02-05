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
import com.pikchillytechnologies.engineeingacademy.Model.ExamListModel;
import com.pikchillytechnologies.engineeingacademy.R;
import com.pikchillytechnologies.engineeingacademy.Model.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

public class ExamListActivity extends AppCompatActivity {

    private TextView m_TextView_Activity_Title;
    private Button m_Button_Back;

    private List<ExamListModel> m_Exam_List;
    private RecyclerView m_RecyclerView_Exam_List;
    private ExamListAdapter m_Exam_List_Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_list);

        m_TextView_Activity_Title = findViewById(R.id.textView_Activity_Title);
        m_TextView_Activity_Title.setText("Exam List");

        m_Button_Back = findViewById(R.id.button_Back);
        m_Button_Back.setVisibility(View.VISIBLE);

        m_Exam_List = new ArrayList<>();
        m_RecyclerView_Exam_List = findViewById(R.id.recyclerView_Exam_List);
        m_Exam_List_Adapter = new ExamListAdapter(m_Exam_List);

        m_RecyclerView_Exam_List.setHasFixedSize(true);

        RecyclerView.LayoutManager m_Layout_Manager = new LinearLayoutManager(getApplicationContext());
        m_RecyclerView_Exam_List.setLayoutManager(m_Layout_Manager);

        m_RecyclerView_Exam_List.setAdapter(m_Exam_List_Adapter);

        prepareExamListData();

        m_RecyclerView_Exam_List.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), m_RecyclerView_Exam_List, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                ExamListModel exam = m_Exam_List.get(position);
                startActivity(new Intent(ExamListActivity.this, ExamInstructionActivity.class));
            }

            @Override
            public void onLongClick(View view, int position) {
                // Nothing
            }
        }));
    }

    public void prepareExamListData(){

        ExamListModel exam = new ExamListModel("Exam 01","02/02/2019","15/02/2019");
        m_Exam_List.add(exam);

        exam = new ExamListModel("Exam 02","02/02/2019","11/02/2019");
        m_Exam_List.add(exam);

        exam = new ExamListModel("Exam 03","12/02/2019","20/02/2019");
        m_Exam_List.add(exam);

        exam = new ExamListModel("Exam 04","22/02/2019","28/02/2019");
        m_Exam_List.add(exam);

        exam = new ExamListModel("Exam 05","13/02/2019","25/02/2019");
        m_Exam_List.add(exam);

        exam = new ExamListModel("Exam 06","02/02/2019","11/02/2019");
        m_Exam_List.add(exam);

        exam = new ExamListModel("Exam 07","12/02/2019","20/02/2019");
        m_Exam_List.add(exam);

        exam = new ExamListModel("Exam 08","22/02/2019","28/02/2019");
        m_Exam_List.add(exam);

        exam = new ExamListModel("Exam 09","13/02/2019","25/02/2019");
        m_Exam_List.add(exam);

        exam = new ExamListModel("Exam 10","12/02/2019","20/02/2019");
        m_Exam_List.add(exam);

        exam = new ExamListModel("Exam 11","22/02/2019","28/02/2019");
        m_Exam_List.add(exam);

        exam = new ExamListModel("Exam 12","13/02/2019","25/02/2019");
        m_Exam_List.add(exam);





    }

}
