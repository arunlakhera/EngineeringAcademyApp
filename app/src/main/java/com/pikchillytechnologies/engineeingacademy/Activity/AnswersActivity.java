package com.pikchillytechnologies.engineeingacademy.Activity;

import android.app.ProgressDialog;
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
import com.pikchillytechnologies.engineeingacademy.Adapter.AnswersAdapter;
import com.pikchillytechnologies.engineeingacademy.Adapter.ExamQuestionAdapter;
import com.pikchillytechnologies.engineeingacademy.Model.AnswersModel;
import com.pikchillytechnologies.engineeingacademy.Model.ExamQuestionModel;
import com.pikchillytechnologies.engineeingacademy.Model.SubCoursePackage;
import com.pikchillytechnologies.engineeingacademy.Model.UserResponseModel;
import com.pikchillytechnologies.engineeingacademy.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private ProgressDialog progressDialog;

    private StringRequest stringRequest;
    private RequestQueue requestQueue;

    private List<ExamQuestionModel> m_Question_List;
    private List<UserResponseModel> m_User_Response_List;

    private String url = "https://pikchilly.com/api/exam_question.php";
    private String getUserResponseURL = "http://onlineengineeringacademy.co.in/api/user_response";

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

        m_RecyclerView_Answers_List = findViewById(R.id.recyclerView_Answers);

        m_Button_Back = findViewById(R.id.button_Back);
        m_Button_Back.setVisibility(View.VISIBLE);

        m_Answer_List = new ArrayList<>();
        m_Question_List = new ArrayList<>();
        m_User_Response_List = new ArrayList<>();

        m_Answers_Adapter = new AnswersAdapter(getApplicationContext(),m_Answer_List);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

        m_RecyclerView_Answers_List.setHasFixedSize(true);

        RecyclerView.LayoutManager m_Layout_Manager = new LinearLayoutManager(getApplicationContext());
        m_RecyclerView_Answers_List.setLayoutManager(m_Layout_Manager);

        m_RecyclerView_Answers_List.setAdapter(m_Answers_Adapter);

        //prepareResultData();
        prepareSubCoursePackageData();
    }

    public void prepareSubCoursePackageData(){

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
                                AnswersModel questionAnswer = new AnswersModel(String.valueOf(i + 1), examObject.getString("question_id"), examObject.getString("question_eng"), examObject.getString("question_hindi"),
                                        examObject.getString("question_eng_img"), examObject.getString("question_hindi_img"),
                                        examObject.getString("answer1_eng"), examObject.getString("answer2_eng"), examObject.getString("answer3_eng"),
                                        examObject.getString("answer4_eng"), examObject.getString("answer5_eng"), examObject.getString("answer6_eng"),
                                        examObject.getString("answer1_hindi"), examObject.getString("answer2_hindi"), examObject.getString("answer3_hindi"),
                                        examObject.getString("answer4_hindi"), examObject.getString("answer5_hindi"), examObject.getString("answer6_hindi"),
                                        examObject.getString("answer1_flag"), examObject.getString("answer2_flag"), examObject.getString("answer3_flag"),
                                        examObject.getString("answer4_flag"), examObject.getString("answer5_flag"), examObject.getString("answer6_flag"),
                                        examObject.getString("question_type"), examObject.getString("question_lang"), examObject.getString("answer_type"),
                                        examObject.getString("answer_lang"));
                                //adding data to list
                                m_Answer_List.add(questionAnswer);
                            }

                            //creating custom adapter object
                            m_Answers_Adapter.notifyDataSetChanged();
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

    public void prepareResultData(){
/*
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
*/
    }

}
