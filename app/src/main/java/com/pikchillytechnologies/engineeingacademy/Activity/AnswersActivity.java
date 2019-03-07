package com.pikchillytechnologies.engineeingacademy.Activity;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
    private int question_number;

    private List<AnswersModel> m_Answer_List;
    private RecyclerView m_RecyclerView_Answers_List;
    private AnswersAdapter m_Answers_Adapter;
    private ProgressDialog progressDialog;

    private StringRequest stringRequest;
    private RequestQueue requestQueue;

    private List<ExamQuestionModel> m_Question_List;
    private List<UserResponseModel> m_User_Response_List;

    private RecyclerView.LayoutManager m_Layout_Manager;

    private LinearLayout m_Layout_Result_PDF;
    private Bitmap bitmap;
    private Button m_Button_Share;
    private Button m_Button_Download;

    private String url = "https://pikchilly.com/api/exam_question.php";
    private String getUserResponseURL = "https://pikchilly.com/api/get_user_response.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answers);

        m_Exam_Answer_Bundle = getIntent().getExtras();
        m_User_Id = m_Exam_Answer_Bundle.getString(getResources().getString(R.string.userid), "User Id");
        m_User_Name = m_Exam_Answer_Bundle.getString("username", "User Name");
        m_Exam_Id = m_Exam_Answer_Bundle.getString(getResources().getString(R.string.examid), "Exam Id");
        m_Title = m_Exam_Answer_Bundle.getString(getResources().getString(R.string.title), "Exam");

        m_TextView_Activity_Title = findViewById(R.id.textView_Activity_Title);
        m_RecyclerView_Answers_List = findViewById(R.id.recyclerView_Answers);
        m_Button_Back = findViewById(R.id.button_Back);
        m_Layout_Result_PDF = findViewById(R.id.layout_All_Answers);
        m_Button_Share = findViewById(R.id.button_Ans_Share);
        m_Button_Download = findViewById(R.id.button_Ans_Download);

        m_Answer_List = new ArrayList<>();
        m_Question_List = new ArrayList<>();
        m_User_Response_List = new ArrayList<>();
        m_Answers_Adapter = new AnswersAdapter(getApplicationContext(), m_Answer_List);
        m_Layout_Manager = new LinearLayoutManager(getApplicationContext());

        progressDialog = new ProgressDialog(this);
        m_TextView_Activity_Title.setText(m_Title);
        progressDialog.setMessage("Loading...");
        m_Button_Back.setVisibility(View.VISIBLE);
        m_RecyclerView_Answers_List.setHasFixedSize(true);
        m_RecyclerView_Answers_List.setLayoutManager(m_Layout_Manager);
        m_RecyclerView_Answers_List.setAdapter(m_Answers_Adapter);
        question_number = 0;

        prepareExamQuestionsData();

        m_Button_Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("size"," "+m_Layout_Result_PDF.getWidth() +"  "+m_Layout_Result_PDF.getWidth());
                bitmap = loadBitmapFromView(m_Layout_Result_PDF, m_Layout_Result_PDF.getWidth(), m_Layout_Result_PDF.getHeight());
                createPdf();
            }
        });

        m_Button_Download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Download The Answersheet",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void prepareExamQuestionsData() {

        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, getUserResponseURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion

                        try {
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);

                            // Getting array inside the JSONObject
                            JSONArray examArray = obj.getJSONArray("user_response");

                            //now looping through all the elements of the json array
                            for (int i = (examArray.length() - 1); i >= 0; i--) {
                                //getting the json object of the particular index inside the array
                                JSONObject examObject = examArray.getJSONObject(i);
                                question_number = question_number + 1;
                                AnswersModel exam = new AnswersModel(String.valueOf(question_number), examObject.getString("question_id"), examObject.getString("question_eng"), examObject.getString("question_hindi"),
                                        examObject.getString("question_eng_img"), examObject.getString("question_hindi_img"),
                                        examObject.getString("answer1_eng"), examObject.getString("answer2_eng"), examObject.getString("answer3_eng"),
                                        examObject.getString("answer4_eng"), examObject.getString("answer5_eng"), examObject.getString("answer6_eng"),
                                        examObject.getString("answer1_hindi"), examObject.getString("answer2_hindi"), examObject.getString("answer3_hindi"),
                                        examObject.getString("answer4_hindi"), examObject.getString("answer5_hindi"), examObject.getString("answer6_hindi"),
                                        examObject.getString("answer1_flag"), examObject.getString("answer2_flag"), examObject.getString("answer3_flag"),
                                        examObject.getString("answer4_flag"), examObject.getString("answer5_flag"), examObject.getString("answer6_flag"),
                                        examObject.getString("question_type"), examObject.getString("question_lang"), examObject.getString("answer_type"),
                                        examObject.getString("answer_lang"), examObject.getString("answer1"), examObject.getString("answer2"), examObject.getString("answer3"),
                                        examObject.getString("answer4"), examObject.getString("answer5"), examObject.getString("answer6")
                                );

                                m_Answer_List.add(exam);
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
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }

    public static Bitmap loadBitmapFromView(View v, int width, int height) {
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);

        return b;
    }

    private void createPdf(){
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        //  Display display = wm.getDefaultDisplay();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        float hight = displaymetrics.heightPixels ;
        float width = displaymetrics.widthPixels ;

        int convertHighet = (int) hight, convertWidth = (int) width;

//        Resources mResources = getResources();
//        Bitmap bitmap = BitmapFactory.decodeResource(mResources, R.drawable.screenshot);

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(convertWidth, convertHighet, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();

        Paint paint = new Paint();
        canvas.drawPaint(paint);

        bitmap = Bitmap.createScaledBitmap(bitmap, convertWidth, convertHighet, true);

        paint.setColor(Color.BLUE);
        canvas.drawBitmap(bitmap, 0, 0 , null);
        document.finishPage(page);

        // write the document content
        String targetPdf = "/sdcard/pdffromScroll.pdf";
        File filePath;
        filePath = new File(targetPdf);
        try {
            document.writeTo(new FileOutputStream(filePath));

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }

        // close the document
        document.close();
        Toast.makeText(this, "PDF of Scroll is created!!!", Toast.LENGTH_SHORT).show();

        openGeneratedPDF();
    }

    private void openGeneratedPDF(){
        File file = new File("/sdcard/pdffromScroll.pdf");
        if (file.exists())
        {
            Intent intent=new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            try
            {
                startActivity(intent);
            }
            catch(ActivityNotFoundException e)
            {
                Toast.makeText(getApplicationContext(), "No Application available to view pdf", Toast.LENGTH_LONG).show();
            }
        }
    }
}
