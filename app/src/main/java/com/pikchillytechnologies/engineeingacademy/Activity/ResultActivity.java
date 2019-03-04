package com.pikchillytechnologies.engineeingacademy.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

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
    private String m_User_Name;
    private String m_Exam_Id;
    private String m_Total_Questions;
    String total_Correct;
    String total_Wrong;
    String total_Not_Attempted;
    String total_Score;

    private TextView m_TextView_Total_Questions;
    private TextView m_TextView_Total_Correct;
    private TextView m_TextView_Total_Wrong;
    private TextView m_TextView_Total_Not_Attempted;
    private TextView m_TextView_Total_Score;
    private TextView m_TextView_User_Name;
    private PieChart pieChart;

    private ProgressDialog progressDialog;
    private UserResultModel userResult;
    private StringRequest stringRequest;
    private RequestQueue requestQueue;
    private String url = "https://pikchilly.com/api/get_user_result.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        m_User_Exam_Bundle = getIntent().getExtras();
        m_TextView_Activity_Title = findViewById(R.id.textView_Activity_Title);
        m_Button_Res_View_Answers = findViewById(R.id.button_Res_View_Answers);
        m_TextView_Total_Questions = findViewById(R.id.textView_Res_Total_Questions);
        m_TextView_Total_Correct = findViewById(R.id.textView_Res_Total_Correct);
        m_TextView_Total_Wrong = findViewById(R.id.textView_Res_Total_Wrong);
        m_TextView_Total_Not_Attempted = findViewById(R.id.textView_Res_Total_Not_Attempted);
        m_TextView_Total_Score = findViewById(R.id.textView_Res_Total_Score);
        m_TextView_User_Name = findViewById(R.id.textView_Res_Name);
        pieChart = findViewById(R.id.pieChart);

        m_TextView_Activity_Title.setText(getResources().getString(R.string.result));

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

        m_User_Id = m_User_Exam_Bundle.getString(getResources().getString(R.string.userid),"User Id");
        m_User_Name = m_User_Exam_Bundle.getString("username", "User Name");
        m_Exam_Id = m_User_Exam_Bundle.getString(getResources().getString(R.string.examid), "Exam Id");
        m_Total_Questions = m_User_Exam_Bundle.getString("total_questions", "Total Questions");

        prepareUserExamResult();

        m_Button_Res_View_Answers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResultActivity.this, AnswersActivity.class));
            }
        });
    }

    public void prepareUserExamResult() {

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

                            updateUI();

                        } catch (JSONException e) {
                            Log.e("Error", e.getMessage());
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occur
                        //Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("Error", error.getMessage());
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

        progressDialog.dismiss();
        //creating a request queue
        requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }

    public void updateUI(){

        // User Result UI
        total_Correct = userResult.getM_Correct();
        total_Wrong = userResult.getM_Wrong();
        total_Not_Attempted = userResult.getM_Not_Attempted();
        String total_Score = userResult.getM_Total_Marks();

        // update chart
        pieChart.setUsePercentValues(true);

        ArrayList<PieEntry> yvalues = new ArrayList<PieEntry>();
        yvalues.add(new PieEntry(Float.valueOf(total_Correct), "Correct", 0));
        yvalues.add(new PieEntry(Float.valueOf(total_Wrong), "Wrong", 1));
        yvalues.add(new PieEntry(Float.valueOf(total_Not_Attempted), "Not Attempted", 2));

        PieDataSet dataSet = new PieDataSet(yvalues, "Exam");
        PieData data = new PieData(dataSet);

        data.setValueFormatter(new PercentFormatter());
        pieChart.setData(data);
        Description description = new Description();
        description.setText("My Performance");
        description.setTextColor(R.color.colorOffWhiteBg);
        pieChart.setDescription(description);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setTransparentCircleRadius(40f);
        pieChart.setHoleRadius(40f);
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        data.setValueTextSize(8f);
        data.setValueTextColor(Color.DKGRAY);

        // Update Score
        m_TextView_Total_Questions.setText(m_Total_Questions);
        m_TextView_Total_Correct.setText(total_Correct);
        m_TextView_Total_Wrong.setText(total_Wrong);
        m_TextView_Total_Not_Attempted.setText(total_Not_Attempted);
        m_TextView_Total_Score.setText(total_Score);
        m_TextView_User_Name.setText(m_User_Name.toUpperCase());

    }
}
