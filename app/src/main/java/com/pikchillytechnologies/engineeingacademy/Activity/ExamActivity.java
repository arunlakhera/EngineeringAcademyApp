package com.pikchillytechnologies.engineeingacademy.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pikchillytechnologies.engineeingacademy.Adapter.ExamListAdapter;
import com.pikchillytechnologies.engineeingacademy.Model.ExamListModel;
import com.pikchillytechnologies.engineeingacademy.Model.ExamQuestionModel;
import com.pikchillytechnologies.engineeingacademy.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExamActivity extends AppCompatActivity {

    private TextView m_TextView_Activity_Title;
    private TextView m_TextView_Question;


    private Button m_Button_Submit;

    private String m_Title;
    private String m_Exam_Id;
    private String m_Exam_Duration;
    private String m_Questions;
    private Bundle m_Sub_Course_Bundle;

    private List<ExamQuestionModel> m_Exam_Question_List;

    Integer m_Current_Question;
    Integer m_Total_Questions;

    private String url = "https://pikchilly.com/api/exam_question.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);

        m_Sub_Course_Bundle = getIntent().getExtras();
        m_Title = m_Sub_Course_Bundle.getString(getResources().getString(R.string.title),"Exam");
        m_Exam_Id = m_Sub_Course_Bundle.getString(getResources().getString(R.string.examid),"Exam Id");
        m_Exam_Duration = m_Sub_Course_Bundle.getString(getResources().getString(R.string.examduration),"Exam Duration");
        m_Questions = m_Sub_Course_Bundle.getString(getResources().getString(R.string.totalquestions),"0");

        m_Exam_Question_List = new ArrayList<>();

        m_Total_Questions = Integer.valueOf(m_Questions);
        m_Current_Question = 0;

        m_TextView_Activity_Title = findViewById(R.id.textView_Activity_Title);
        m_TextView_Activity_Title.setText(m_Title);

        //Toast.makeText(ExamActivity.this,"Data:" + m_Exam_Id + "-" + m_Exam_Duration + "-" + m_Total_Questions, Toast.LENGTH_LONG).show();

        m_TextView_Question = findViewById(R.id.textView_Question);
        m_Button_Submit = findViewById(R.id.button_Submit);

        // To make textview scrollable of Question
        m_TextView_Question.setMovementMethod(new ScrollingMovementMethod());

        prepareExamQuestionListData();

        showQuestion();

        m_Button_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ExamActivity.this, ResultActivity.class));
            }
        });


    }

    public void prepareExamQuestionListData(){

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion
                        progressDialog.dismiss();

                        try {
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);

                            // Getting array inside the JSONObject
                            JSONArray examArray = obj.getJSONArray("exam_question");

                            //now looping through all the elements of the json array
                            for (int i = 0; i < examArray.length(); i++) {
                                //getting the json object of the particular index inside the array
                                JSONObject examObject = examArray.getJSONObject(i);

                                //creating a tutorial object and giving them the values from json object
                                ExamQuestionModel exam = new ExamQuestionModel(examObject.getString("question_id"),examObject.getString("question_eng"), examObject.getString("question_hindi"));

                                //adding data to list
                                m_Exam_Question_List.add(exam);
                            }

                            progressDialog.dismiss();

                            Toast.makeText(getApplicationContext(), "Questions:" + m_Exam_Question_List.size(), Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occur
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("exam_id", String.valueOf(m_Exam_Id));
                return params;
            }
        };

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }

    public void showQuestion(){

        //Toast.makeText(getApplicationContext(), "Questions:" + m_Exam_Question_List.get(0).getM_Question_Eng(), Toast.LENGTH_SHORT).show();

        /*
        if(m_Current_Question < m_Total_Questions){

            m_TextView_Question.setText(m_Exam_Question_List.get(0).getM_Question_Eng());

        }
        */

    }

}
