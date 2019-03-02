package com.pikchillytechnologies.engineeingacademy.Activity;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.pikchillytechnologies.engineeingacademy.Adapter.ExamListAdapter;
import com.pikchillytechnologies.engineeingacademy.Adapter.ExamQuestionAdapter;
import com.pikchillytechnologies.engineeingacademy.Model.CoursesModel;
import com.pikchillytechnologies.engineeingacademy.Model.ExamListModel;
import com.pikchillytechnologies.engineeingacademy.Model.ExamQuestionModel;
import com.pikchillytechnologies.engineeingacademy.Model.RecyclerTouchListener;
import com.pikchillytechnologies.engineeingacademy.Model.ResultModel;
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
import java.util.concurrent.TimeUnit;

public class ExamActivity extends AppCompatActivity {

    String quest_num;
    Integer m_Current_Question;
    Integer m_Total_Questions;
    RecyclerView.LayoutManager m_Layout_Manager;
    StringRequest stringRequest;
    RequestQueue requestQueue;
    int currentQuestion;
    private TextView m_TextView_Activity_Title;
    private TextView m_TextView_Question;
    private TextView m_TextView_Time_Remaining;
    // Text Check box layout
    private LinearLayout m_Layout_Answers_Text;
    private CheckBox m_CheckBox_Answer1;
    private CheckBox m_CheckBox_Answer2;
    private CheckBox m_CheckBox_Answer3;
    private CheckBox m_CheckBox_Answer4;
    private CheckBox m_CheckBox_Answer5;
    private CheckBox m_CheckBox_Answer6;
    // Answer Checkbox for Image
    private LinearLayout m_Layout_Answers_Image;
    private CheckBox m_CheckBox_Answer1_Image;
    private CheckBox m_CheckBox_Answer2_Image;
    private CheckBox m_CheckBox_Answer3_Image;
    private CheckBox m_CheckBox_Answer4_Image;
    private CheckBox m_CheckBox_Answer5_Image;
    private CheckBox m_CheckBox_Answer6_Image;
    private ImageView m_ImageView_Answer1_Image;
    private ImageView m_ImageView_Answer2_Image;
    private ImageView m_ImageView_Answer3_Image;
    private ImageView m_ImageView_Answer4_Image;
    private ImageView m_ImageView_Answer5_Image;
    private ImageView m_ImageView_Answer6_Image;
    private ImageView m_ImageView_Question_Image;
    private ImageView m_ImageView_QuestionSupported_Image;
    private TextView m_TextView_Total_Question;
    private Button m_Button_Hindi;
    private Button m_Button_Eng;
    private Button m_Button_Submit;
    private Button m_Button_Next;
    private Button m_Button_Previous;
    private String m_Title;
    private String m_Exam_Id;
    private String m_Exam_Duration;
    private String m_Questions;
    private Bundle m_Sub_Course_Bundle;
    private List<ExamQuestionModel> m_Exam_Question_List;
    private List<ExamQuestionModel> m_All_Questions_List;
    private List<UserResponseModel> m_User_Response_List;
    private UserResponseModel userResponse;
    private List<ExamQuestionModel> m_Question_List;
    private RecyclerView m_RecyclerView_Question_List;
    private ExamQuestionAdapter m_Question_List_Adapter;
    private Boolean m_Hindi_Flag;
    private Boolean m_English_Flag;
    private JSONArray userResponseJSONArray = new JSONArray();
    private JSONObject userResponseWholeJSON = new JSONObject();
    private ExamQuestionModel examQuestion;
    boolean m_User_Response_Flag;

    private String url = "https://pikchilly.com/api/exam_question.php";
    private String userResponseURL = "http://onlineengineeringacademy.co.in/api/user_response"; //"https://pikchilly.com/api/user_response.php";
    //private String userResponseURL = "https://pikchilly.com/api/user_response.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);

        m_Sub_Course_Bundle = getIntent().getExtras();
        m_Title = m_Sub_Course_Bundle.getString(getResources().getString(R.string.title), "Exam");
        m_Exam_Id = m_Sub_Course_Bundle.getString(getResources().getString(R.string.examid), "Exam Id");
        m_Exam_Duration = m_Sub_Course_Bundle.getString(getResources().getString(R.string.examduration), "Exam Duration");
        m_Questions = m_Sub_Course_Bundle.getString(getResources().getString(R.string.totalquestions), "0");

        m_Exam_Question_List = new ArrayList<>();
        m_User_Response_List = new ArrayList<>();

        m_Total_Questions = Integer.valueOf(m_Questions);
        m_Current_Question = 0;
        m_Hindi_Flag = false;
        m_English_Flag = true;
        m_User_Response_Flag = false;

        m_TextView_Activity_Title = findViewById(R.id.textView_Activity_Title);
        m_RecyclerView_Question_List = findViewById(R.id.recyclerView_question_List);
        m_TextView_Time_Remaining = findViewById(R.id.textView_Time_Remaining);

        m_TextView_Total_Question = findViewById(R.id.textView_Current_Question);
        m_ImageView_QuestionSupported_Image = findViewById(R.id.imageview_QuestionSupportedImage);
        m_ImageView_Question_Image = findViewById(R.id.imageview_Question_Image);

        m_Layout_Answers_Text = findViewById(R.id.layout_Answers_Text);
        m_Layout_Answers_Image = findViewById(R.id.layout_Answers_Image);

        m_ImageView_Answer1_Image = findViewById(R.id.imageview_Answer1);
        m_ImageView_Answer2_Image = findViewById(R.id.imageview_Answer2);
        m_ImageView_Answer3_Image = findViewById(R.id.imageview_Answer3);
        m_ImageView_Answer4_Image = findViewById(R.id.imageview_Answer4);
        m_ImageView_Answer5_Image = findViewById(R.id.imageview_Answer5);
        m_ImageView_Answer6_Image = findViewById(R.id.imageview_Answer6);

        m_TextView_Question = findViewById(R.id.textView_Question_Text);
        m_CheckBox_Answer1 = findViewById(R.id.checkbox_Answer1);
        m_CheckBox_Answer2 = findViewById(R.id.checkbox_Answer2);
        m_CheckBox_Answer3 = findViewById(R.id.checkbox_Answer3);
        m_CheckBox_Answer4 = findViewById(R.id.checkbox_Answer4);
        m_CheckBox_Answer5 = findViewById(R.id.checkbox_Answer5);
        m_CheckBox_Answer6 = findViewById(R.id.checkbox_Answer6);

        m_CheckBox_Answer1_Image = findViewById(R.id.checkbox_Answer1_Image);
        m_CheckBox_Answer2_Image = findViewById(R.id.checkbox_Answer2_Image);
        m_CheckBox_Answer3_Image = findViewById(R.id.checkbox_Answer3_Image);
        m_CheckBox_Answer4_Image = findViewById(R.id.checkbox_Answer4_Image);
        m_CheckBox_Answer5_Image = findViewById(R.id.checkbox_Answer5_Image);
        m_CheckBox_Answer6_Image = findViewById(R.id.checkbox_Answer6_Image);

        m_Button_Next = findViewById(R.id.button_Next);
        m_Button_Previous = findViewById(R.id.button_Previous);
        m_Button_Hindi = findViewById(R.id.button_Hindi);
        m_Button_Eng = findViewById(R.id.button_Eng);
        m_Button_Submit = findViewById(R.id.button_Submit);

        m_TextView_Activity_Title.setText(m_Title);
        m_Button_Submit.setVisibility(View.INVISIBLE);

        // To make textview scrollable of Question
        m_TextView_Question.setMovementMethod(new ScrollingMovementMethod());

        m_RecyclerView_Question_List.setHasFixedSize(true);

        m_Layout_Manager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        m_RecyclerView_Question_List.setLayoutManager(m_Layout_Manager);

        currentQuestion = 0;

        prepareExamQuestionListData();

        long noOfMinutes = 60 * 60 * 1000;//Convert minutes into milliseconds


        startExamTimer(noOfMinutes);

        //m_TextView_Total_Question.setText(quest_num);

        m_RecyclerView_Question_List.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), m_RecyclerView_Question_List, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                currentQuestion = position;
                updateUI(currentQuestion);

            }

            @Override
            public void onLongClick(View view, int position) {
                // Nothing
            }
        }));

        // Action to perform when Next button is pressed
        m_Button_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currentQuestion < (m_Question_List.size() - 1)) {
                    //saveResponse();
                    currentQuestion = currentQuestion + 1;
                    updateUI(currentQuestion);
                } else {
                    m_Button_Submit.setVisibility(View.VISIBLE);
                }

                m_Hindi_Flag = false;
                m_English_Flag = true;

                m_Layout_Manager.scrollToPosition(currentQuestion);

            }
        });

        // Action to perform when Previous button is pressed
        m_Button_Previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currentQuestion > 0) {
                    //saveResponse();
                    currentQuestion = currentQuestion - 1;
                    updateUI(currentQuestion);

                }

                m_Hindi_Flag = false;
                m_English_Flag = true;

                m_Layout_Manager.scrollToPosition(currentQuestion);

            }
        });

        // Action to perform when Hindi button is pressed
        m_Button_Hindi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (examQuestion.getM_Question_Hindi().length() > 0) {

                    m_Hindi_Flag = true;
                    m_English_Flag = false;
                    updateUI(currentQuestion);

                } else {
                    Toast.makeText(getApplicationContext(), "Hindi version not available.", Toast.LENGTH_LONG).show();
                }
            }
        });

        // Action to perform when English button is pressed
        m_Button_Eng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (examQuestion.getM_Question_Eng().length() > 0) {

                    m_Hindi_Flag = false;
                    m_English_Flag = true;
                    updateUI(currentQuestion);

                } else {
                    Toast.makeText(getApplicationContext(), "English version not available.", Toast.LENGTH_LONG).show();
                }
            }
        });

        m_Button_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Ask user to confirm if they want to submit the response
                // If user says yes save the response and move to next screen
                saveUserResponse();

            }
        });

    }

    public void startExamTimer(long noOfMinutes){

        //long examTime = 60000 * 60000 * 2;

        new CountDownTimer(noOfMinutes, 1000) {

            public void onTick(long millisUntilFinished) {

                long millis = millisUntilFinished;
                //Convert milliseconds into hour,minute and seconds

                long hh = TimeUnit.MILLISECONDS.toHours(millis);
                long mm = TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(hh);
                long ss = TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(mm);

                m_TextView_Time_Remaining.setText(hh + ":" + mm + ":" + ss);

            }

            public void onFinish() {

                m_TextView_Time_Remaining.setText("done!");
            }
        }.start();
    }

    public void saveUserResponse() {

        UserResponseModel userResponse;

        try {
            for (int i = 0; i < (m_User_Response_List.size()); i++) {

                userResponse = m_User_Response_List.get(i);
                JSONObject userResponseJSON = new JSONObject();

                userResponseJSON.put("user_id", userResponse.getM_User_Id());
                userResponseJSON.put("exam_id", userResponse.getM_Exam_Id());
                userResponseJSON.put("question_id", userResponse.getM_Question_Id());
                userResponseJSON.put("answer1_flag", userResponse.getM_User_Answer1_Flag());
                userResponseJSON.put("answer2_flag", userResponse.getM_User_Answer2_Flag());
                userResponseJSON.put("answer3_flag", userResponse.getM_User_Answer3_Flag());
                userResponseJSON.put("answer4_flag", userResponse.getM_User_Answer4_Flag());
                userResponseJSON.put("answer5_flag", userResponse.getM_User_Answer5_Flag());
                userResponseJSON.put("answer6_flag", userResponse.getM_User_Answer6_Flag());

                userResponseJSONArray.put(userResponseJSON);
            }

        } catch (Exception e) {
            Log.e("ERROR:", e.getMessage());
        }

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, userResponseURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion
                        progressDialog.dismiss();

                        try{

                            JSONObject userResponseSave = new JSONObject(response);
                            JSONArray userResponseSaveArray = userResponseSave.getJSONArray("user_status");
                            JSONObject statusObject = userResponseSaveArray.getJSONObject(0);

                            String saveStatus = statusObject.getString("status");

                            if(saveStatus.equals("success")){

                                Toast.makeText(getApplicationContext(),"Response Saved Successfully.", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(ExamActivity.this, ResultActivity.class));


                            }else{
                                Toast.makeText(getApplicationContext(),"Response Could not be Saved.", Toast.LENGTH_LONG).show();
                            }

                        }catch (Exception e){
                            Log.e("Error:",e.getMessage());
                        }

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

                try {

                    userResponseWholeJSON.put("user_response", userResponseJSONArray);

                } catch (Exception e) {
                    Log.e("Error:", e.getMessage());
                }

                return params;
            }
        };

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);

    }

    public void onTextCheckBoxClicked(View view) {

        switch (view.getId()) {

            case R.id.checkbox_Answer1:
                //answer1_Flag = true;
                userResponse.setM_User_Answer1_Flag(true);
                break;
            case R.id.checkbox_Answer2:
                //answer2_Flag = true;
                userResponse.setM_User_Answer2_Flag(true);
                break;
            case R.id.checkbox_Answer3:
                userResponse.setM_User_Answer3_Flag(true);
                break;
            case R.id.checkbox_Answer4:
                //answer4_Flag = true;
                userResponse.setM_User_Answer4_Flag(true);
                break;
            case R.id.checkbox_Answer5:
                //answer5_Flag = true;
                userResponse.setM_User_Answer5_Flag(true);
                break;
            case R.id.checkbox_Answer6:
                //answer6_Flag = true;
                userResponse.setM_User_Answer6_Flag(true);
                break;
        }

    }

    public void onImageCheckBoxClicked(View view) {

        switch (view.getId()) {

            case R.id.checkbox_Answer1_Image:
                //answer1_Flag = true;
                userResponse.setM_User_Answer1_Flag(true);
                break;
            case R.id.checkbox_Answer2_Image:
                //answer2_Flag = true;
                userResponse.setM_User_Answer2_Flag(true);
                break;
            case R.id.checkbox_Answer3_Image:
                //answer3_Flag = true;
                userResponse.setM_User_Answer3_Flag(true);
                break;
            case R.id.checkbox_Answer4_Image:
                //answer4_Flag = true;
                userResponse.setM_User_Answer4_Flag(true);
                break;
            case R.id.checkbox_Answer5_Image:
                //answer5_Flag = true;
                userResponse.setM_User_Answer5_Flag(true);
                break;
            case R.id.checkbox_Answer6_Image:
                //answer6_Flag = true;
                userResponse.setM_User_Answer6_Flag(true);
                break;
        }

    }

    public void updateUI(int curQuestion) {

        examQuestion = m_Question_List.get(curQuestion);
        m_Question_List_Adapter.row_index = curQuestion;

        userResponse = m_User_Response_List.get(curQuestion);

        examQuestion.setRead(true);

        m_TextView_Total_Question.setText(examQuestion.getM_Question_Number() + "/" + m_Total_Questions);

        String questionSupportImage_Eng = examQuestion.getM_Question_Eng_Img_url();
        String questionSupportImage_Hindi = examQuestion.getM_Question_Hindi_Img_url();

        String questionType = examQuestion.getM_Question_Type();
        String answerType = examQuestion.getM_Answer_Type();

        if (m_English_Flag) {

            m_Button_Eng.setBackgroundResource(R.drawable.button_red_flat);
            m_Button_Hindi.setBackgroundResource(R.drawable.button_flat);

            //Check if the question is Text or Image
            if (questionType.equals("T")) {

                m_TextView_Question.setVisibility(View.VISIBLE);
                m_ImageView_Question_Image.setVisibility(View.GONE);

                m_TextView_Question.setText(examQuestion.getM_Question_Eng());

            } else if (questionType.equals("I")) {

                m_TextView_Question.setVisibility(View.GONE);
                m_ImageView_Question_Image.setVisibility(View.VISIBLE);

                try {
                    Glide.with(this)
                            .load(examQuestion.getM_Question_Eng())
                            .placeholder(R.drawable.logo)
                            .error(R.drawable.back_icon)
                            .into(m_ImageView_Question_Image);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Could not Load image.." + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            // Check if the supported image for question is available
            if (questionSupportImage_Eng.equals("NA")) {
                m_ImageView_QuestionSupported_Image.setVisibility(View.GONE);

            } else {
                m_ImageView_QuestionSupported_Image.setVisibility(View.VISIBLE);

                try {
                    Glide.with(this)
                            .load(questionSupportImage_Eng)
                            .placeholder(R.drawable.logo)
                            .error(R.drawable.back_icon)
                            .into(m_ImageView_QuestionSupported_Image);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Could not Load image.." + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            if (answerType.equals("T")) {

                m_Layout_Answers_Text.setVisibility(View.VISIBLE);
                m_Layout_Answers_Image.setVisibility(View.GONE);

                // Set Answer for Text English
                setCheckboxAnswersText(examQuestion.getM_Answer1_Eng(), examQuestion.getM_Answer2_Eng(), examQuestion.getM_Answer3_Eng(),
                        examQuestion.getM_Answer4_Eng(), examQuestion.getM_Answer5_Eng(), examQuestion.getM_Answer6_Eng());


            } else if (answerType.equals("I")) {

                m_Layout_Answers_Image.setVisibility(View.VISIBLE);
                m_Layout_Answers_Text.setVisibility(View.GONE);

                setCheckboxAnswersImage(examQuestion.getM_Answer1_Eng(), m_ImageView_Answer1_Image);
                setCheckboxAnswersImage(examQuestion.getM_Answer2_Eng(), m_ImageView_Answer2_Image);
                setCheckboxAnswersImage(examQuestion.getM_Answer3_Eng(), m_ImageView_Answer3_Image);
                setCheckboxAnswersImage(examQuestion.getM_Answer4_Eng(), m_ImageView_Answer4_Image);
                setCheckboxAnswersImage(examQuestion.getM_Answer5_Eng(), m_ImageView_Answer5_Image);
                setCheckboxAnswersImage(examQuestion.getM_Answer6_Eng(), m_ImageView_Answer6_Image);

            }

        } else {

            if (examQuestion.getM_Question_Hindi().equals("NA")) {
                Toast.makeText(getApplicationContext(), "Hindi version not available.", Toast.LENGTH_LONG).show();
            } else {

                m_Button_Hindi.setBackgroundResource(R.drawable.button_red_flat);
                m_Button_Eng.setBackgroundResource(R.drawable.button_flat);

                //Check if the question is Text or Image
                if (questionType.equals("T")) {

                    m_TextView_Question.setVisibility(View.VISIBLE);
                    m_ImageView_Question_Image.setVisibility(View.GONE);

                    m_TextView_Question.setText(examQuestion.getM_Question_Hindi());

                } else if (questionType.equals("I")) {

                    m_TextView_Question.setVisibility(View.GONE);
                    m_ImageView_Question_Image.setVisibility(View.VISIBLE);

                    //m_TextView_Question.setText(examQuestion.getM_Question_Eng());

                    try {
                        Glide.with(this)
                                .load(examQuestion.getM_Question_Hindi())
                                .placeholder(R.drawable.logo)
                                .error(R.drawable.back_icon)
                                .into(m_ImageView_Question_Image);
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Could not Load image.." + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

                // Check if the supported image for question is available
                if (questionSupportImage_Hindi.equals("NA")) {
                    m_ImageView_QuestionSupported_Image.setVisibility(View.GONE);

                } else {
                    m_ImageView_QuestionSupported_Image.setVisibility(View.VISIBLE);

                    try {
                        Glide.with(this)
                                .load(questionSupportImage_Hindi)
                                .placeholder(R.drawable.logo)
                                .error(R.drawable.back_icon)
                                .into(m_ImageView_QuestionSupported_Image);
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Could not Load image.." + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

                if (answerType.equals("T")) {

                    m_Layout_Answers_Text.setVisibility(View.VISIBLE);
                    m_Layout_Answers_Image.setVisibility(View.GONE);

                    // Set Answer for Questions in Textview

                    setCheckboxAnswersText(examQuestion.getM_Answer1_Hindi(), examQuestion.getM_Answer2_Hindi(), examQuestion.getM_Answer3_Hindi(),
                            examQuestion.getM_Answer4_Hindi(), examQuestion.getM_Answer5_Hindi(), examQuestion.getM_Answer6_Hindi());


                } else if (answerType.equals("I")) {

                    m_Layout_Answers_Image.setVisibility(View.VISIBLE);
                    m_Layout_Answers_Text.setVisibility(View.GONE);

                    setCheckboxAnswersImage(examQuestion.getM_Answer1_Hindi(), m_ImageView_Answer1_Image);
                    setCheckboxAnswersImage(examQuestion.getM_Answer2_Hindi(), m_ImageView_Answer2_Image);
                    setCheckboxAnswersImage(examQuestion.getM_Answer3_Hindi(), m_ImageView_Answer3_Image);
                    setCheckboxAnswersImage(examQuestion.getM_Answer4_Hindi(), m_ImageView_Answer4_Image);
                    setCheckboxAnswersImage(examQuestion.getM_Answer5_Hindi(), m_ImageView_Answer5_Image);
                    setCheckboxAnswersImage(examQuestion.getM_Answer6_Hindi(), m_ImageView_Answer6_Image);

                }
            }

        }
        m_Question_List_Adapter.notifyDataSetChanged();

        m_Hindi_Flag = false;
        m_English_Flag = true;

    }

    // Function to set the Text Answers into Text
    public void setCheckboxAnswersText(String answer1, String answer2, String answer3, String answer4, String answer5, String answer6) {

        m_CheckBox_Answer1.setText(answer1);
        m_CheckBox_Answer2.setText(answer2);
        m_CheckBox_Answer3.setText(answer3);
        m_CheckBox_Answer4.setText(answer4);
        m_CheckBox_Answer5.setText(answer5);
        m_CheckBox_Answer6.setText(answer6);

        m_CheckBox_Answer1.setChecked(userResponse.getM_User_Answer1_Flag());
        m_CheckBox_Answer2.setChecked(userResponse.getM_User_Answer2_Flag());
        m_CheckBox_Answer3.setChecked(userResponse.getM_User_Answer3_Flag());
        m_CheckBox_Answer4.setChecked(userResponse.getM_User_Answer4_Flag());
        m_CheckBox_Answer5.setChecked(userResponse.getM_User_Answer5_Flag());
        m_CheckBox_Answer6.setChecked(userResponse.getM_User_Answer6_Flag());
    }

    // Function to set the Image Answers into checkbox
    public void setCheckboxAnswersImage(String answer, ImageView imageViewAnswerImage) {

        try {
            Glide.with(this)
                    .load(answer)
                    .placeholder(R.drawable.logo)
                    .error(R.drawable.back_icon)
                    .into(imageViewAnswerImage);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Could not Load image.." + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        m_CheckBox_Answer1_Image.setChecked(userResponse.getM_User_Answer1_Flag());
        m_CheckBox_Answer2_Image.setChecked(userResponse.getM_User_Answer2_Flag());
        m_CheckBox_Answer3_Image.setChecked(userResponse.getM_User_Answer3_Flag());
        m_CheckBox_Answer4_Image.setChecked(userResponse.getM_User_Answer4_Flag());
        m_CheckBox_Answer5_Image.setChecked(userResponse.getM_User_Answer5_Flag());
        m_CheckBox_Answer6_Image.setChecked(userResponse.getM_User_Answer6_Flag());
    }

    public void prepareExamQuestionListData() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        prepareJson(response);
                        updateUI(currentQuestion);

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
                params.put("exam_id", String.valueOf(m_Exam_Id));
                return params;
            }
        };

        //creating a request queue
        requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }

    public void prepareJson(String response) {
        try {

            if (m_Question_List == null) {
                m_Question_List = new ArrayList<>();
            } else {
                m_Question_List.clear();
            }

            JSONObject obj = new JSONObject(response);
            JSONArray examArray = obj.getJSONArray("exam_question");

            for (int i = 0; i < examArray.length(); i++) {

                JSONObject examObject = examArray.getJSONObject(i);
                ExamQuestionModel exam = new ExamQuestionModel(String.valueOf(i + 1), examObject.getString("question_id"), examObject.getString("question_eng"), examObject.getString("question_hindi"),
                        examObject.getString("question_eng_img"), examObject.getString("question_hindi_img"),
                        examObject.getString("answer1_eng"), examObject.getString("answer2_eng"), examObject.getString("answer3_eng"),
                        examObject.getString("answer4_eng"), examObject.getString("answer5_eng"), examObject.getString("answer6_eng"),
                        examObject.getString("answer1_hindi"), examObject.getString("answer2_hindi"), examObject.getString("answer3_hindi"),
                        examObject.getString("answer4_hindi"), examObject.getString("answer5_hindi"), examObject.getString("answer6_hindi"),
                        examObject.getString("answer1_flag"), examObject.getString("answer2_flag"), examObject.getString("answer3_flag"),
                        examObject.getString("answer4_flag"), examObject.getString("answer5_flag"), examObject.getString("answer6_flag"),
                        examObject.getString("question_type"), examObject.getString("question_lang"), examObject.getString("answer_type"),
                        examObject.getString("answer_lang"), false);

                UserResponseModel userResponse = new UserResponseModel("User_Id", m_Exam_Id, examObject.getString("question_id"), false, false, false, false, false, false);
                m_User_Response_List.add(userResponse);

                m_Question_List.add(exam);

            }

            if (m_Question_List_Adapter == null) {
                m_Question_List_Adapter = new ExamQuestionAdapter(getApplicationContext(), m_Question_List);
                m_RecyclerView_Question_List.setAdapter(m_Question_List_Adapter);
            } else {
                m_Question_List_Adapter.notifyDataSetChanged();
                m_RecyclerView_Question_List.scrollToPosition(0);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
