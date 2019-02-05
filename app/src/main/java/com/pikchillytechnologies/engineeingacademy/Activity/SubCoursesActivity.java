package com.pikchillytechnologies.engineeingacademy.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.pikchillytechnologies.engineeingacademy.HelperFiles.EAHelper;
import com.pikchillytechnologies.engineeingacademy.R;
import com.pikchillytechnologies.engineeingacademy.Model.RecyclerTouchListener;
import com.pikchillytechnologies.engineeingacademy.Model.SubCoursePackage;
import com.pikchillytechnologies.engineeingacademy.Adapter.SubCoursesPackageAdapter;

import java.util.ArrayList;
import java.util.List;

public class SubCoursesActivity extends AppCompatActivity {

    private TextView m_TextView_Activity_Title;
    private Button m_Button_Back;
    private Bundle m_Course_Bundle;

    private EAHelper m_Helper;

    private List<SubCoursePackage> m_Sub_Course_Package_List;
    private RecyclerView m_RecyclerView_Course_Package;
    private SubCoursesPackageAdapter m_Sub_Course_Package_Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_courses);

        m_Helper = new EAHelper();

        // Get variable from prev activity
        m_Course_Bundle = getIntent().getExtras();
        String m_Title = m_Course_Bundle.getString(getResources().getString(R.string.title),getResources().getString(R.string.packages));

        m_TextView_Activity_Title = findViewById(R.id.textView_Activity_Title);
        m_TextView_Activity_Title.setText(m_Title);

        m_Button_Back = findViewById(R.id.button_Back);
        m_Button_Back.setVisibility(View.VISIBLE);

        m_Sub_Course_Package_List = new ArrayList<>();
        m_RecyclerView_Course_Package = findViewById(R.id.recyclerView_Sub_Courses);
        m_Sub_Course_Package_Adapter = new SubCoursesPackageAdapter(m_Sub_Course_Package_List);

        m_RecyclerView_Course_Package.setHasFixedSize(true);

        RecyclerView.LayoutManager m_Layout_Manager = new LinearLayoutManager(getApplicationContext());
        m_RecyclerView_Course_Package.setLayoutManager(m_Layout_Manager);

        //m_RecyclerView_Course_Package.setItemAnimator(new DefaultItemAnimator());
        m_RecyclerView_Course_Package.setAdapter(m_Sub_Course_Package_Adapter);

        prepareCoursePackageData();

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
