package com.pikchillytechnologies.engineeingacademy.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pikchillytechnologies.engineeingacademy.HelperFiles.EAHelper;
import com.pikchillytechnologies.engineeingacademy.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ExamInstructionActivity extends AppCompatActivity {

    private TextView m_TextView_Activity_Title;
    private Button m_Button_Back;
    private Button m_Button_Start;
    private EAHelper m_Helper;
    private String m_Title;
    private String m_Exam_Id;
    private String m_Exam_Duration;
    private String m_Total_Questions;
    private Bundle m_Sub_Course_Bundle;

    private String url = "https://pikchilly.com/api/exam_instruction.php";

    private TextView m_TextView_Instruction1;
    private TextView m_TextView_Instruction2;
    private TextView m_TextView_Instruction3;
    private TextView m_TextView_Instruction4;
    private TextView m_TextView_Instruction5;
    private TextView m_TextView_Marks;
    private TextView m_TextView_Negative_Marks;
    private TextView m_TextView_Total_Questions;
    private TextView m_TextView_Duration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_instruction);

        m_Sub_Course_Bundle = getIntent().getExtras();
        m_Title = m_Sub_Course_Bundle.getString(getResources().getString(R.string.title),"Exam");
        m_Exam_Id = m_Sub_Course_Bundle.getString(getResources().getString(R.string.examid),"Exam Id");
        m_Exam_Duration = m_Sub_Course_Bundle.getString(getResources().getString(R.string.examduration),"Exam Duration");

        m_TextView_Activity_Title = findViewById(R.id.textView_Activity_Title);
        m_TextView_Activity_Title.setText(m_Title);

        m_Helper = new EAHelper();

        m_Button_Back = findViewById(R.id.button_Back);
        m_Button_Back.setVisibility(View.VISIBLE);

        m_Button_Start = findViewById(R.id.button_Start);

        m_TextView_Instruction1 = findViewById(R.id.textView_Instruction1);
        m_TextView_Instruction2 = findViewById(R.id.textView_Instruction2);
        m_TextView_Instruction3 = findViewById(R.id.textView_Instruction3);
        m_TextView_Instruction4 = findViewById(R.id.textView_Instruction4);
        m_TextView_Instruction5 = findViewById(R.id.textView_Instruction5);
        m_TextView_Marks = findViewById(R.id.textView_Marks);
        m_TextView_Negative_Marks = findViewById(R.id.textView_Negative_Marks);
        m_TextView_Total_Questions = findViewById(R.id.textView_Total_Questions);
        m_TextView_Duration = findViewById(R.id.textView_Duration);

        prepapreExamInstructionsData();

        m_Button_Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent destinationDetailIntent = new Intent(ExamInstructionActivity.this, ExamActivity.class);
                destinationDetailIntent.putExtra(getResources().getString(R.string.title), m_Title);
                destinationDetailIntent.putExtra(getResources().getString(R.string.examid), m_Exam_Id);
                destinationDetailIntent.putExtra(getResources().getString(R.string.examduration), m_Exam_Duration);
                destinationDetailIntent.putExtra(getResources().getString(R.string.totalquestions), m_Total_Questions);

                startActivity(destinationDetailIntent);

            }
        });

        m_Button_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ExamInstructionActivity.this, ExamListActivity.class));
            }
        });
    }

    public void prepapreExamInstructionsData(){

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
                            JSONArray examInstructionArray = obj.getJSONArray("exam_instruction");

                            //Toast.makeText(ExamInstructionActivity.this,"Response:" + examInstructionArray, Toast.LENGTH_LONG).show();

                            //now looping through all the elements of the json array
                            for (int i = 0; i < examInstructionArray.length(); i++) {
                                //getting the json object of the particular index inside the array
                                JSONObject examInstructionObject = examInstructionArray.getJSONObject(i);

                                m_TextView_Instruction1.setText(examInstructionObject.getString("instruction1"));
                                m_TextView_Instruction2.setText(examInstructionObject.getString("instruction2"));
                                m_TextView_Instruction3.setText(examInstructionObject.getString("instruction3"));
                                m_TextView_Instruction4.setText(examInstructionObject.getString("instruction4"));
                                m_TextView_Instruction5.setText(examInstructionObject.getString("instruction5"));
                                m_TextView_Marks.setText(examInstructionObject.getString("marks") + " marks for correct answer");
                                m_TextView_Negative_Marks.setText(examInstructionObject.getString("negative_marks") + " marks for wrong answer");
                                m_TextView_Duration.setText(m_Exam_Duration + " Hours");
                                m_Total_Questions = examInstructionObject.getString("total_questions");
                                m_TextView_Total_Questions.setText( m_Total_Questions + " Questions");
                            }

                            progressDialog.dismiss();

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

}
