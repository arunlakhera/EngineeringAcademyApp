package com.pikchillytechnologies.engineeingacademy.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.pikchillytechnologies.engineeingacademy.Adapter.ExamListAdapter;
import com.pikchillytechnologies.engineeingacademy.HelperFiles.EAHelper;
import com.pikchillytechnologies.engineeingacademy.HelperFiles.SessionHandler;
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
    private String m_User_Id;
    private String m_User_Name;

    private EAHelper m_Helper;

    private List<ExamListModel> m_Exam_List;
    private RecyclerView m_RecyclerView_Exam_List;
    private ExamListAdapter m_Exam_List_Adapter;

    //Navigation Drawer
    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;
    private Button menuButton;
    private RecyclerView.LayoutManager m_Layout_Manager;

    private String m_Category_Id;
    private String m_Title;
    private String m_Sub_Category_Id;
    private String m_Category_Title;
    private String m_Sub_Category_Title;

    //private String url = "https://pikchilly.com/api/exam_list.php";
    private String url = "http://onlineengineeringacademy.co.in/api/exam_request";

    private SessionHandler session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_list);

        session = new SessionHandler(getApplicationContext());
        m_Helper = new EAHelper();

        // Get variable from prev activity
        m_Sub_Course_Bundle = getIntent().getExtras();
        m_User_Id = m_Sub_Course_Bundle.getString(getResources().getString(R.string.userid),"User Id");
        m_User_Name = m_Sub_Course_Bundle.getString("username", "User Name");
        m_Category_Id = m_Sub_Course_Bundle.getString(getResources().getString(R.string.categoryid),"Category");
        m_Category_Title = m_Sub_Course_Bundle.getString("category_title","Category Title");
        m_Title = m_Sub_Course_Bundle.getString(getResources().getString(R.string.title),"Sub Category Title");
        m_Sub_Category_Id = m_Sub_Course_Bundle.getString(getResources().getString(R.string.subcategoryid),"Sub Category Id");
        m_Sub_Category_Title = m_Sub_Course_Bundle.getString("sub_category_title","Sub Category Title");

        m_TextView_Activity_Title = findViewById(R.id.textView_Activity_Title);
        m_Button_Back = findViewById(R.id.button_Back);
        m_RecyclerView_Exam_List = findViewById(R.id.recyclerView_Exam_List);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        menuButton = findViewById(R.id.button_Menu);

        m_Exam_List = new ArrayList<>();
        m_Exam_List_Adapter = new ExamListAdapter(m_Exam_List);
        m_Layout_Manager = new LinearLayoutManager(getApplicationContext());

        m_TextView_Activity_Title.setText(m_Title);
        m_Button_Back.setVisibility(View.VISIBLE);
        m_RecyclerView_Exam_List.setHasFixedSize(true);
        m_RecyclerView_Exam_List.setLayoutManager(m_Layout_Manager);
        m_RecyclerView_Exam_List.setAdapter(m_Exam_List_Adapter);

        if(m_Helper.isNetworkAvailable(getApplicationContext())){

            prepareExamListData();

        }else{
            Toast.makeText(getApplicationContext(),"Please connect to Internet.", Toast.LENGTH_LONG).show();
        }

        m_RecyclerView_Exam_List.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), m_RecyclerView_Exam_List, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                if(m_Helper.isNetworkAvailable(getApplicationContext())){

                    ExamListModel exam = m_Exam_List.get(position);

                    // Condition to allow student to take exam only if not attempted before.
                    int attempt_number = Integer.valueOf(exam.getM_No_Of_Attempts().trim());

                    if(attempt_number < 1){

                        Intent destinationDetailIntent = new Intent(ExamListActivity.this, ExamInstructionActivity.class);
                        destinationDetailIntent.putExtra(getResources().getString(R.string.userid), m_User_Id);
                        destinationDetailIntent.putExtra("username", m_User_Name);
                        destinationDetailIntent.putExtra(getResources().getString(R.string.title), exam.getM_Exam_Name());
                        destinationDetailIntent.putExtra(getResources().getString(R.string.examid), exam.getM_Exam_Id());
                        destinationDetailIntent.putExtra(getResources().getString(R.string.examduration), exam.getM_Exam_Duration());
                        destinationDetailIntent.putExtra(getResources().getString(R.string.categoryid), m_Category_Id);
                        destinationDetailIntent.putExtra(getResources().getString(R.string.subcategoryid), m_Sub_Category_Id);
                        destinationDetailIntent.putExtra("category_title", m_Category_Title);
                        destinationDetailIntent.putExtra("sub_category_title", m_Sub_Category_Title);
                        startActivity(destinationDetailIntent);

                    }else{
                        Toast.makeText(getApplicationContext(),"You have already taken the exam. Only 1 attempt is allowed per exam.", Toast.LENGTH_LONG).show();

                    }


                }else{
                    Toast.makeText(getApplicationContext(),"Please connect to Internet.", Toast.LENGTH_LONG).show();
                }


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

                Intent destinationDetailIntent = new Intent(ExamListActivity.this, SubCoursesActivity.class);
                destinationDetailIntent.putExtra(getResources().getString(R.string.userid), m_User_Id);
                destinationDetailIntent.putExtra("username", m_User_Name);
                destinationDetailIntent.putExtra(getResources().getString(R.string.title), m_Category_Title);
                destinationDetailIntent.putExtra("sub_category_title", m_Sub_Category_Title);
                destinationDetailIntent.putExtra("category_title", m_Category_Title);
                destinationDetailIntent.putExtra(getResources().getString(R.string.categoryid), m_Category_Id);
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
                params.put("user_id", String.valueOf(m_User_Id));
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
