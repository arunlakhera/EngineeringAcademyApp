package com.pikchillytechnologies.engineeingacademy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SubCoursesActivity extends AppCompatActivity {

    private List<SubCoursePackage> m_Sub_Course_Package_List;
    private RecyclerView m_RecyclerView_Course_Package;
    private SubCoursesPackageAdapter m_Sub_Course_Package_Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_courses);

        m_Sub_Course_Package_List = new ArrayList<>();
        m_RecyclerView_Course_Package = findViewById(R.id.recyclerView_Sub_Courses);
        m_Sub_Course_Package_Adapter = new SubCoursesPackageAdapter(m_Sub_Course_Package_List);

        RecyclerView.LayoutManager m_Layout_Manager = new LinearLayoutManager(getApplicationContext());
        m_RecyclerView_Course_Package.setLayoutManager(m_Layout_Manager);
        m_RecyclerView_Course_Package.setItemAnimator(new DefaultItemAnimator());
        m_RecyclerView_Course_Package.setAdapter(m_Sub_Course_Package_Adapter);

        prepareCoursePackageData();

    }

    public void prepareCoursePackageData(){
        SubCoursePackage cp = new SubCoursePackage("Mechanical","JEE","Rs. 299","100","N");
        m_Sub_Course_Package_List.add(cp);

        cp = new SubCoursePackage("Mechanical","Sub Cat 1","Rs. 299","100","N");
        m_Sub_Course_Package_List.add(cp);

        cp = new SubCoursePackage("Mechanical","Sub Cat 2","Rs. 199","120","N");
        m_Sub_Course_Package_List.add(cp);

        cp = new SubCoursePackage("Mechanical","Sub Cat 3","Rs. 399","150","N");
        m_Sub_Course_Package_List.add(cp);

        cp = new SubCoursePackage("Mechanical","Sub Cat 4","Rs. 499","200","N");
        m_Sub_Course_Package_List.add(cp);

        cp = new SubCoursePackage("Mechanical","Sub Cat 6","Rs. 499","200","N");
        m_Sub_Course_Package_List.add(cp);


        m_Sub_Course_Package_Adapter.notifyDataSetChanged();
    }


}
