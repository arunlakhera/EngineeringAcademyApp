package com.pikchillytechnologies.engineeingacademy.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.solver.widgets.Helper;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pikchillytechnologies.engineeingacademy.Adapter.CoursesAdapter;
import com.pikchillytechnologies.engineeingacademy.HelperFiles.EAHelper;
import com.pikchillytechnologies.engineeingacademy.Model.CoursesModel;
import com.pikchillytechnologies.engineeingacademy.Model.RecyclerTouchListener;
import com.pikchillytechnologies.engineeingacademy.Model.SubCoursePackage;
import com.pikchillytechnologies.engineeingacademy.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CoursesActivity extends AppCompatActivity {

    private TextView m_TextView_Activity_Title;

    private CardView m_CardView_Mechanical;
    private CardView m_CardView_Civil;
    private CardView m_CardView_Railways;
    private CardView m_CardView_Computer;
    private CardView m_CardView_Miscellaneous;

    private EAHelper m_Helper;
    private Bundle m_User_Bundle;
    private String m_User_Id;

    //Navigation Drawer
    private DrawerLayout mDrawerLayout;

    private List<CoursesModel> m_Courses_List;
    private RecyclerView m_RecyclerView_Courses;
    private CoursesAdapter m_Courses_Adapter;

    //private String url = "http://onlineengineeringacademy.co.in/api/category_request";
    private String url = "https://pikchilly.com/api/category.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        m_Helper = new EAHelper();
        m_User_Bundle = getIntent().getExtras();

        m_User_Id = m_User_Bundle.getString(getResources().getString(R.string.userid), "User Id");

        mDrawerLayout = findViewById(R.id.drawer_layout);
        m_TextView_Activity_Title = findViewById(R.id.textView_Activity_Title);
        m_TextView_Activity_Title.setText(R.string.courses);
        m_Courses_List = new ArrayList<>();
        m_RecyclerView_Courses = findViewById(R.id.recyclerView_Courses);
        m_Courses_Adapter = new CoursesAdapter(getApplicationContext(),m_Courses_List);

        m_RecyclerView_Courses.setHasFixedSize(true);

        RecyclerView.LayoutManager m_Layout_Manager = new LinearLayoutManager(getApplicationContext());
        m_RecyclerView_Courses.setLayoutManager(m_Layout_Manager);

        m_RecyclerView_Courses.setAdapter(m_Courses_Adapter);

        prepareCourseData();

        final NavigationView navigationView = findViewById(R.id.nav_view);
        Button menuButton = findViewById(R.id.button_Menu);

        m_RecyclerView_Courses.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), m_RecyclerView_Courses, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                CoursesModel course = m_Courses_List.get(position);

                Intent destinationDetailIntent = new Intent(CoursesActivity.this, SubCoursesActivity.class);
                destinationDetailIntent.putExtra(getResources().getString(R.string.userid), m_User_Id);
                destinationDetailIntent.putExtra(getResources().getString(R.string.title), course.getM_Name());
                destinationDetailIntent.putExtra(getResources().getString(R.string.categoryid), course.getM_Category_Id());
                startActivity(destinationDetailIntent);

            }

            @Override
            public void onLongClick(View view, int position) {
                // Nothing
                Log.d("LongClick","Long Click");
            }
        }));

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                m_Helper.start_Activity(CoursesActivity.this, SubCoursesActivity.class, getResources().getString(R.string.mechanical));
                mDrawerLayout.openDrawer(navigationView);
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                // set item as selected to persist highlight
                menuItem.setChecked(true);

                // close drawer when item is tapped
                mDrawerLayout.closeDrawers();

                return true;
            }
        });
    }

    public void prepareCourseData(){

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
                            JSONArray coursesArray = obj.getJSONArray("category");

                            //now looping through all the elements of the json array
                            for (int i = 0; i < coursesArray.length(); i++) {
                                //getting the json object of the particular index inside the array
                                JSONObject coursesObject = coursesArray.getJSONObject(i);

                                //creating a tutorial object and giving them the values from json object
                                CoursesModel course = new CoursesModel(coursesObject.getString("category_id"), coursesObject.getString("name"),coursesObject.getString("status"));

                                //adding data to list
                                m_Courses_List.add(course);
                            }

                            //creating custom adapter object
                            m_Courses_Adapter.notifyDataSetChanged();
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
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);

    }

}
