package com.pikchillytechnologies.engineeingacademy.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import com.pikchillytechnologies.engineeingacademy.HelperFiles.EAHelper;
import com.pikchillytechnologies.engineeingacademy.Model.ExamListModel;
import com.pikchillytechnologies.engineeingacademy.Model.SubCoursePackage;
import com.pikchillytechnologies.engineeingacademy.R;
import com.pikchillytechnologies.engineeingacademy.Model.RecyclerTouchListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExamListActivity extends AppCompatActivity {

    private TextView m_TextView_Activity_Title;
    private Button m_Button_Back;
    private Bundle m_Sub_Course_Bundle;

    private EAHelper m_Helper;

    private List<ExamListModel> m_Exam_List;
    private RecyclerView m_RecyclerView_Exam_List;
    private ExamListAdapter m_Exam_List_Adapter;

    private String m_Category_Id;
    private String m_Title;
    private String m_Sub_Category_Id;

    private String url = "https://pikchilly.com/api/exam_list.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_list);

        m_Helper = new EAHelper();

        // Get variable from prev activity
        m_Sub_Course_Bundle = getIntent().getExtras();
        m_Category_Id = m_Sub_Course_Bundle.getString(getResources().getString(R.string.categoryid),"Category");
        m_Title = m_Sub_Course_Bundle.getString(getResources().getString(R.string.title),"Sub Category Title");
        m_Sub_Category_Id = m_Sub_Course_Bundle.getString(getResources().getString(R.string.subcategoryid),"Sub Category Id");

        m_TextView_Activity_Title = findViewById(R.id.textView_Activity_Title);
        m_TextView_Activity_Title.setText(m_Title);

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

                Intent destinationDetailIntent = new Intent(ExamListActivity.this, ExamInstructionActivity.class);
                destinationDetailIntent.putExtra(getResources().getString(R.string.title), exam.getM_Exam_Name());
                destinationDetailIntent.putExtra(getResources().getString(R.string.examid), exam.getM_Exam_Id());
                destinationDetailIntent.putExtra(getResources().getString(R.string.examduration), exam.getM_Exam_Duration());
                startActivity(destinationDetailIntent);
            }

            @Override
            public void onLongClick(View view, int position) {
                // Nothing
                Log.d("Message","Long Click");
            }
        }));

        m_Button_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_Helper.start_Activity(ExamListActivity.this, SubCoursesActivity.class,"");
            }
        });
    }

    public void prepareExamListData(){

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
                            JSONArray examArray = obj.getJSONArray("exam_list");

                            //now looping through all the elements of the json array
                            for (int i = 0; i < examArray.length(); i++) {
                                //getting the json object of the particular index inside the array
                                JSONObject examObject = examArray.getJSONObject(i);

                                //creating a tutorial object and giving them the values from json object
                                ExamListModel exam = new ExamListModel(examObject.getString("exam_id"), examObject.getString("name"), examObject.getString("available_from"), examObject.getString("available_till"),examObject.getString("no_of_attempts"),examObject.getString("exam_duration"));

                                //adding data to list
                                m_Exam_List.add(exam);
                            }

                            //creating custom adapter object
                            m_Exam_List_Adapter.notifyDataSetChanged();
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
                params.put("sub_category_id", String.valueOf(m_Sub_Category_Id));
                return params;
            }
        };

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }

}
