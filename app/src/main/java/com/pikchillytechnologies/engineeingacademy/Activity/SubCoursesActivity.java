package com.pikchillytechnologies.engineeingacademy.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

    private EAHelper m_Helper;

    private List<SubCoursePackage> m_Sub_Course_Package_List;
    private RecyclerView m_RecyclerView_Course_Package;
    private SubCoursesPackageAdapter m_Sub_Course_Package_Adapter;
    private ImageView m_Background_ImageView;

    //private String url = "http://onlineengineeringacademy.co.in/api/sub_category_request";

    private String url = "https://pikchilly.com/api/sub_category.php";

    String m_Category_Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_courses);

        m_Helper = new EAHelper();

        // Get variable from prev activity
        m_Course_Bundle = getIntent().getExtras();
        String m_Title = m_Course_Bundle.getString(getResources().getString(R.string.title),getResources().getString(R.string.packages));
        m_Category_Id = m_Course_Bundle.getString(getResources().getString(R.string.categoryid),"category_id");

        m_TextView_Activity_Title = findViewById(R.id.textView_Activity_Title);
        m_TextView_Activity_Title.setText(m_Title);

        m_Button_Back = findViewById(R.id.button_Back);
        m_Button_Back.setVisibility(View.VISIBLE);

        m_Sub_Course_Package_List = new ArrayList<>();
        m_RecyclerView_Course_Package = findViewById(R.id.recyclerView_Sub_Courses);

        m_Sub_Course_Package_Adapter = new SubCoursesPackageAdapter(getApplicationContext(),m_Sub_Course_Package_List);

        m_RecyclerView_Course_Package.setHasFixedSize(true);

        RecyclerView.LayoutManager m_Layout_Manager = new LinearLayoutManager(getApplicationContext());
        m_RecyclerView_Course_Package.setLayoutManager(m_Layout_Manager);

        m_RecyclerView_Course_Package.setItemAnimator(new DefaultItemAnimator());
        m_RecyclerView_Course_Package.setAdapter(m_Sub_Course_Package_Adapter);

        prepareSubCoursePackageData();

        m_RecyclerView_Course_Package.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), m_RecyclerView_Course_Package, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                SubCoursePackage scp = m_Sub_Course_Package_List.get(position);
                m_Helper.start_Activity(SubCoursesActivity.this, ExamListActivity.class);
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
                m_Helper.start_Activity(SubCoursesActivity.this, CoursesActivity.class);
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
                                SubCoursePackage subCourse = new SubCoursePackage(subCoursesObject.getString("name"), subCoursesObject.getString("cost"));

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
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);

    }


}
