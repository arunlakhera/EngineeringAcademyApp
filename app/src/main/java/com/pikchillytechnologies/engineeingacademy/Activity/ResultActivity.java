package com.pikchillytechnologies.engineeingacademy.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.pikchillytechnologies.engineeingacademy.Adapter.ExamListAdapter;
import com.pikchillytechnologies.engineeingacademy.Adapter.ExamQuestionAdapter;
import com.pikchillytechnologies.engineeingacademy.Adapter.ResultAdapter;
import com.pikchillytechnologies.engineeingacademy.Model.ExamListModel;
import com.pikchillytechnologies.engineeingacademy.Model.ExamQuestionModel;
import com.pikchillytechnologies.engineeingacademy.Model.ResultModel;
import com.pikchillytechnologies.engineeingacademy.Model.UserResponseModel;
import com.pikchillytechnologies.engineeingacademy.Model.UserResultModel;
import com.pikchillytechnologies.engineeingacademy.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultActivity extends AppCompatActivity {

    private TextView m_TextView_Activity_Title;
    private Button m_Button_Res_View_Answers;

    private Bundle m_User_Exam_Bundle;
    private String m_User_Id;
    private String m_Exam_Id;

    private TextView m_TextView_Total_Questions;
    private TextView m_TextView_Total_Correct;
    private TextView m_TextView_Total_Wrong;
    private TextView m_TextView_Total_Not_Attempted;
    private TextView m_TextView_Total_Score;
    private TextView m_TextView_User_Name;

    private UserResultModel userResult;
    private StringRequest stringRequest;
    private RequestQueue requestQueue;
    private String url = "https://pikchilly.com/api/user_result.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        m_User_Exam_Bundle = getIntent().getExtras();
        m_TextView_Activity_Title = findViewById(R.id.textView_Activity_Title);
        m_Button_Res_View_Answers = findViewById(R.id.button_Res_View_Answers);

        m_TextView_Total_Questions = findViewById(R.id.textView_Total_Questions);
        m_TextView_Total_Correct = findViewById(R.id.textView_Res_Total_Correct);
        m_TextView_Total_Wrong = findViewById(R.id.textView_Res_Total_Wrong);
        m_TextView_Total_Not_Attempted = findViewById(R.id.textView_Res_Total_Not_Attempted);
        m_TextView_Total_Score = findViewById(R.id.textView_Res_Total_Score);
        m_TextView_User_Name = findViewById(R.id.textView_Res_Name);

        m_TextView_Activity_Title.setText(getResources().getString(R.string.result));

        m_User_Id = m_User_Exam_Bundle.getString(getResources().getString(R.string.userid),"User Id");
        m_Exam_Id = m_User_Exam_Bundle.getString(getResources().getString(R.string.examid), "Exam Id");

        prepareUserExamResult();

        //updateUI();

        m_Button_Res_View_Answers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResultActivity.this, AnswersActivity.class));
            }
        });
    }

    public void prepareUserExamResult() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject objResult = new JSONObject(response);
                            JSONArray userResultArray = objResult.getJSONArray("user_result");

                            for (int i = 0; i < userResultArray.length(); i++) {

                                JSONObject resultObject = userResultArray.getJSONObject(i);
                                userResult = new UserResultModel(resultObject.getString("user_id"),resultObject.getString("exam_id"),resultObject.getString("correct"),resultObject.getString("wrong"),resultObject.getString("not_attempted"),resultObject.getString("total_marks"),resultObject.getString("date_of_attempt"),resultObject.getString("number_of_attempt"));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        updateUI();
                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occur
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", String.valueOf(m_User_Id));
                params.put("exam_id", String.valueOf(m_Exam_Id));
                return params;
            }
        };

        //creating a request queue
        requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }

    public void updateUI(){

        String total_Correct = userResult.getM_Correct();
        String total_Wrong = userResult.getM_Wrong();
        String total_Not_Attempted = userResult.getM_Not_Attempted();
        String total_Score = userResult.getM_Total_Marks();

        m_TextView_Total_Questions.setText("300");
        m_TextView_Total_Correct.setText(total_Correct);
        m_TextView_Total_Wrong.setText(total_Wrong);
        m_TextView_Total_Not_Attempted.setText(total_Not_Attempted);
        m_TextView_Total_Score.setText(total_Score);
        m_TextView_User_Name.setText("NAME OF USER");

    }

}
