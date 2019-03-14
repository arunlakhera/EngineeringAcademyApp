package com.pikchillytechnologies.engineeingacademy.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
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
import com.pikchillytechnologies.engineeingacademy.HelperFiles.SessionHandler;
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
    private String m_User_Id;
    private String m_Title;
    private String m_Exam_Id;
    private String m_Exam_Duration;
    private String m_Total_Questions;
    private Bundle m_Sub_Course_Bundle;
    private String m_User_Name;
    private String m_Category_Id;
    private String m_Sub_Category_Id;

    //Navigation Drawer
    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;
    private Button menuButton;

    private TextView m_TextView_Instruction1;
    private TextView m_TextView_Instruction2;
    private TextView m_TextView_Instruction3;
    private TextView m_TextView_Instruction4;
    private TextView m_TextView_Instruction5;
    private TextView m_TextView_Marks;
    private TextView m_TextView_Negative_Marks;
    private TextView m_TextView_Total_Questions;
    private TextView m_TextView_Duration;
    private String m_Marks;
    private String m_Negative_Marks;
    private String m_Category_Title;
    private String m_Sub_Category_Title;

    private String url = "https://pikchilly.com/api/exam_instruction.php";

    private SessionHandler session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_instruction);

        session = new SessionHandler(getApplicationContext());

        m_Sub_Course_Bundle = getIntent().getExtras();
        m_User_Id = m_Sub_Course_Bundle.getString(getResources().getString(R.string.userid),"User Id");
        m_User_Name = m_Sub_Course_Bundle.getString("username", "User Name");
        m_Title = m_Sub_Course_Bundle.getString(getResources().getString(R.string.title),"Exam");
        m_Exam_Id = m_Sub_Course_Bundle.getString(getResources().getString(R.string.examid),"Exam Id");
        m_Exam_Duration = m_Sub_Course_Bundle.getString(getResources().getString(R.string.examduration),"Exam Duration");
        m_Category_Id = m_Sub_Course_Bundle.getString(getResources().getString(R.string.categoryid),"Category Id");
        m_Sub_Category_Id = m_Sub_Course_Bundle.getString(getResources().getString(R.string.subcategoryid),"Sub Category Id");
        m_Category_Title = m_Sub_Course_Bundle.getString("category_title","Category Title");
        m_Sub_Category_Title = m_Sub_Course_Bundle.getString("sub_category_title","Sub Category Title");

        m_TextView_Activity_Title = findViewById(R.id.textView_Activity_Title);
        m_TextView_Instruction1 = findViewById(R.id.textView_Instruction1);
        m_TextView_Instruction2 = findViewById(R.id.textView_Instruction2);
        m_TextView_Instruction3 = findViewById(R.id.textView_Instruction3);
        m_TextView_Instruction4 = findViewById(R.id.textView_Instruction4);
        m_TextView_Instruction5 = findViewById(R.id.textView_Instruction5);
        m_TextView_Marks = findViewById(R.id.textView_Marks);
        m_TextView_Negative_Marks = findViewById(R.id.textView_Negative_Marks);
        m_TextView_Total_Questions = findViewById(R.id.textView_Total_Questions);
        m_TextView_Duration = findViewById(R.id.textView_Duration);
        m_Button_Start = findViewById(R.id.button_Start);
        m_Button_Back = findViewById(R.id.button_Back);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        menuButton = findViewById(R.id.button_Menu);

        m_TextView_Activity_Title.setText(m_Title);
        m_Helper = new EAHelper();
        m_Button_Back.setVisibility(View.VISIBLE);

        prepapreExamInstructionsData();

        m_Button_Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(m_Total_Questions != null && !m_Total_Questions.isEmpty() && Integer.valueOf(m_Total_Questions) > 0){

                    Intent destinationDetailIntent = new Intent(ExamInstructionActivity.this, ExamActivity.class);
                    destinationDetailIntent.putExtra(getResources().getString(R.string.userid), m_User_Id);
                    destinationDetailIntent.putExtra("username", m_User_Name);
                    destinationDetailIntent.putExtra(getResources().getString(R.string.title), m_Title);
                    destinationDetailIntent.putExtra(getResources().getString(R.string.examid), m_Exam_Id);
                    destinationDetailIntent.putExtra(getResources().getString(R.string.examduration), m_Exam_Duration);
                    destinationDetailIntent.putExtra(getResources().getString(R.string.totalquestions), m_Total_Questions);
                    destinationDetailIntent.putExtra("Marks", m_Marks);
                    destinationDetailIntent.putExtra("Negative_Marks", m_Negative_Marks);
                    startActivity(destinationDetailIntent);

                }else{

                    Toast.makeText(getApplicationContext(),"No Questions assigned to Exam.", Toast.LENGTH_LONG).show();
                }


            }
        });

        m_Button_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent destinationDetailIntent = new Intent(ExamInstructionActivity.this, ExamListActivity.class);
                destinationDetailIntent.putExtra(getResources().getString(R.string.userid), m_User_Id);
                destinationDetailIntent.putExtra("username", m_User_Name);
                destinationDetailIntent.putExtra(getResources().getString(R.string.title), m_Sub_Category_Title);
                destinationDetailIntent.putExtra(getResources().getString(R.string.categoryid), m_Category_Id);
                destinationDetailIntent.putExtra(getResources().getString(R.string.subcategoryid), m_Sub_Category_Id);
                destinationDetailIntent.putExtra("category_title", m_Category_Title);
                destinationDetailIntent.putExtra("sub_category_title", m_Sub_Category_Title);
                startActivity(destinationDetailIntent);
            }
        });

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDrawerLayout.openDrawer(navigationView);
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                // set item as selected to persist highlight
                menuItem.setChecked(true);

                if(menuItem.getTitle().equals("Courses")){

                    Intent destinationDetailIntent = new Intent(getApplicationContext(), CoursesActivity.class);
                    destinationDetailIntent.putExtra(getResources().getString(R.string.userid), m_User_Id);
                    destinationDetailIntent.putExtra("username", m_User_Name);
                    startActivity(destinationDetailIntent);

                }else if(menuItem.getTitle().equals("Articles")){
                    startActivity(new Intent(getApplicationContext(), ArticlesActivity.class));
                }else if(menuItem.getTitle().equals("My Downloads")){

                    Intent destinationDetailIntent = new Intent(getApplicationContext(), MyDownloadsActivity.class);
                    destinationDetailIntent.putExtra(getResources().getString(R.string.userid), m_User_Id);
                    destinationDetailIntent.putExtra("username", m_User_Name);
                    startActivity(destinationDetailIntent);

                }else if(menuItem.getTitle().equals("My Results")){

                    Intent destinationDetailIntent = new Intent(getApplicationContext(), MyResultsActivity.class);
                    destinationDetailIntent.putExtra(getResources().getString(R.string.userid), m_User_Id);
                    destinationDetailIntent.putExtra("username", m_User_Name);
                    startActivity(destinationDetailIntent);

                }else if(menuItem.getTitle().equals("Update Profile")){

                    Intent destinationDetailIntent = new Intent(getApplicationContext(), UpdateProfileActivity.class);
                    destinationDetailIntent.putExtra(getResources().getString(R.string.userid), m_User_Id);
                    destinationDetailIntent.putExtra("username", m_User_Name);
                    startActivity(destinationDetailIntent);
                }else if(menuItem.getTitle().equals("Logout")){

                    session.logoutUser();
                    Intent destinationDetailIntent = new Intent(getApplicationContext(), SignInActivity.class);
                    startActivity(destinationDetailIntent);

                }

                // close drawer when item is tapped
                mDrawerLayout.closeDrawers();

                return true;
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
                                m_Marks = examInstructionObject.getString("marks");
                                m_TextView_Marks.setText(m_Marks + " marks for correct answer");
                                m_Negative_Marks = examInstructionObject.getString("negative_marks");
                                m_TextView_Negative_Marks.setText( m_Negative_Marks + " marks for wrong answer");
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
