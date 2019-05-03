package com.pikchillytechnologies.engineeingacademy.Activity;

import android.app.ProgressDialog;
import android.app.Activity;
import android.content.IntentFilter;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pikchillytechnologies.engineeingacademy.HelperFiles.EAHelper;
import com.pikchillytechnologies.engineeingacademy.HelperFiles.SessionHandler;
import com.pikchillytechnologies.engineeingacademy.Model.CoursesModel;
import com.pikchillytechnologies.engineeingacademy.R;
import com.pikchillytechnologies.engineeingacademy.Model.RecyclerTouchListener;
import com.pikchillytechnologies.engineeingacademy.Model.SubCoursePackage;
import com.pikchillytechnologies.engineeingacademy.Adapter.SubCoursesPackageAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubCoursesActivity extends AppCompatActivity {

    private TextView m_TextView_Activity_Title;
    private Button m_Button_Back;
    private Bundle m_Course_Bundle;
    private String m_Title;
    private String m_Category_Title;
    private String m_User_Id;
    private String m_User_Name;
    private String m_Sub_Category_Title;

    private EAHelper m_Helper;

    //Navigation Drawer
    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;
    private Button menuButton;

    private List<SubCoursePackage> m_Sub_Course_Package_List;
    private RecyclerView m_RecyclerView_Course_Package;
    private SubCoursesPackageAdapter m_Sub_Course_Package_Adapter;
    private ImageView m_Background_ImageView;

    private RecyclerView.LayoutManager m_Layout_Manager;
    private String url = "http://onlineengineeringacademy.co.in/api/sub_category_request";

    private String m_Category_Id;
    private SessionHandler session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_courses);
        // Call the function callInstamojo to start payment here

        session = new SessionHandler(getApplicationContext());
        m_Helper = new EAHelper();

        // Get variable from prev activity
        m_Course_Bundle = getIntent().getExtras();

        m_User_Id = m_Course_Bundle.getString(getResources().getString(R.string.userid),"User Id");
        m_User_Name = m_Course_Bundle.getString("username", "User Name");
        m_Category_Title = m_Course_Bundle.getString("category_title",getResources().getString(R.string.packages));
        m_Category_Id = m_Course_Bundle.getString(getResources().getString(R.string.categoryid),"category_id");
        m_Title = m_Course_Bundle.getString(getResources().getString(R.string.title),getResources().getString(R.string.packages));

        m_TextView_Activity_Title = findViewById(R.id.textView_Activity_Title);
        m_Button_Back = findViewById(R.id.button_Back);
        m_RecyclerView_Course_Package = findViewById(R.id.recyclerView_Sub_Courses);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        menuButton = findViewById(R.id.button_Menu);

        m_Sub_Course_Package_List = new ArrayList<>();
        m_TextView_Activity_Title.setText(m_Title);
        m_Button_Back.setVisibility(View.VISIBLE);
        m_Sub_Course_Package_Adapter = new SubCoursesPackageAdapter(getApplicationContext(),m_Sub_Course_Package_List, m_Category_Title, m_Title,m_User_Id,m_Category_Id);
        m_RecyclerView_Course_Package.setHasFixedSize(true);
        m_Layout_Manager = new LinearLayoutManager(getApplicationContext());
        m_RecyclerView_Course_Package.setLayoutManager(m_Layout_Manager);
        m_RecyclerView_Course_Package.setItemAnimator(new DefaultItemAnimator());
        m_RecyclerView_Course_Package.setAdapter(m_Sub_Course_Package_Adapter);

        if(m_Helper.isNetworkAvailable(getApplicationContext())){

            prepareSubCoursePackageData();

        }else{
            Toast.makeText(getApplicationContext(),"Please connect to Internet.", Toast.LENGTH_LONG).show();
        }

        m_RecyclerView_Course_Package.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), m_RecyclerView_Course_Package, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                SubCoursePackage scp = m_Sub_Course_Package_List.get(position);

                if(m_Helper.isNetworkAvailable(getApplicationContext())){

                    if(scp.getM_Payment_Status().equals("Paid") || scp.getM_Cost().trim().equals("0")){

                        Intent destinationDetailIntent = new Intent(SubCoursesActivity.this, ExamListActivity.class);
                        destinationDetailIntent.putExtra(getResources().getString(R.string.userid), m_User_Id);
                        destinationDetailIntent.putExtra("username", m_User_Name);
                        destinationDetailIntent.putExtra(getResources().getString(R.string.title), scp.getM_Sub_Course_Name());
                        destinationDetailIntent.putExtra(getResources().getString(R.string.categoryid), m_Category_Id);
                        destinationDetailIntent.putExtra(getResources().getString(R.string.subcategoryid), scp.getM_Sub_Course_Id());
                        destinationDetailIntent.putExtra("category_title", m_Category_Title);
                        destinationDetailIntent.putExtra("sub_category_title", scp.getM_Sub_Course_Name());

                        startActivity(destinationDetailIntent);
                    }else{
                        Toast.makeText(getApplicationContext(),"Please Buy the Package to Access.",Toast.LENGTH_LONG).show();
                    }

                }else{
                    Toast.makeText(getApplicationContext(),"Please connect to Internet.", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onLongClick(View view, int position) {
                    // Nothing
                Log.d("LongClick","Long Click");
            }
        }));

        m_Button_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent destinationDetailIntent = new Intent(SubCoursesActivity.this, CoursesActivity.class);
                destinationDetailIntent.putExtra(getResources().getString(R.string.userid), m_User_Id);
                destinationDetailIntent.putExtra("username", m_User_Name);
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
                            JSONArray subCoursesArray = obj.getJSONArray("sub_category");

                            //now looping through all the elements of the json array
                            for (int i = 0; i < subCoursesArray.length(); i++) {
                                //getting the json object of the particular index inside the array
                                JSONObject subCoursesObject = subCoursesArray.getJSONObject(i);

                                //creating a tutorial object and giving them the values from json object
                                SubCoursePackage subCourse = new SubCoursePackage(subCoursesObject.getString("sub_category_id") ,subCoursesObject.getString("name"), subCoursesObject.getString("total_exams"),subCoursesObject.getString("cost"), subCoursesObject.getString("payment_status"), subCoursesObject.getString("user_id"));

                                //adding data to list
                                m_Sub_Course_Package_List.add(subCourse);
                            }

                            //creating custom adapter object
                            m_Sub_Course_Package_Adapter.notifyDataSetChanged();
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
                params.put("category_id", String.valueOf(m_Category_Id));
                params.put("user_id", m_User_Id);
                return params;
            }
        };

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);

    }


}
