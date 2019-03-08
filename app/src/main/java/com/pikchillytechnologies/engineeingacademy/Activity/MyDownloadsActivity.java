package com.pikchillytechnologies.engineeingacademy.Activity;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
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

import com.pikchillytechnologies.engineeingacademy.Adapter.CoursesAdapter;
import com.pikchillytechnologies.engineeingacademy.Adapter.MyDownloadsAdapter;
import com.pikchillytechnologies.engineeingacademy.Model.CoursesModel;
import com.pikchillytechnologies.engineeingacademy.Model.DownloadedFileModel;
import com.pikchillytechnologies.engineeingacademy.Model.RecyclerTouchListener;
import com.pikchillytechnologies.engineeingacademy.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MyDownloadsActivity extends AppCompatActivity {

    private TextView m_TextView_Activity_Title;
    private Button m_Button_Back;

    private Bundle m_User_Bundle;
    private String m_User_Id;
    private String m_User_Name;

    private List<DownloadedFileModel> m_DownloadedFile_List;
    private RecyclerView m_RecyclerView_DownloadedFile;
    private DownloadedFileModel downloadedFile;
    private MyDownloadsAdapter m_DownloadFile_Adapter;

    private String fileName;
    private RecyclerView.LayoutManager m_Layout_Manager;

    //Navigation Drawer
    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;
    private Button menuButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_downloads);

        m_User_Bundle = getIntent().getExtras();
        m_User_Id = m_User_Bundle.getString(getResources().getString(R.string.userid), "User Id");
        m_User_Name = m_User_Bundle.getString("username", "User Name");

        mDrawerLayout = findViewById(R.id.drawer_layout);
        m_TextView_Activity_Title = findViewById(R.id.textView_Activity_Title);
        m_Button_Back = findViewById(R.id.button_Back);
        m_RecyclerView_DownloadedFile = findViewById(R.id.recyclerView_MyDownloads);
        navigationView = findViewById(R.id.nav_view);
        menuButton = findViewById(R.id.button_Menu);

        m_TextView_Activity_Title.setText("My Downloads");
        m_Button_Back.setVisibility(View.VISIBLE);
        m_DownloadedFile_List = new ArrayList<>();
        m_DownloadFile_Adapter = new MyDownloadsAdapter(m_DownloadedFile_List);
        m_Layout_Manager = new LinearLayoutManager(getApplicationContext());
        m_RecyclerView_DownloadedFile.setLayoutManager(m_Layout_Manager);
        m_RecyclerView_DownloadedFile.setAdapter(m_DownloadFile_Adapter);

        loadFileNames();

        m_RecyclerView_DownloadedFile.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), m_RecyclerView_DownloadedFile, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                String selectedFileName = m_DownloadedFile_List.get(position).getM_DownloadedFileName();
                viewPdf(selectedFileName);
            }

            @Override
            public void onLongClick(View view, int position) {
                Log.d("LongPress:", "Long Pressed");
            }
        }));

        m_Button_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent destinationDetailIntent = new Intent(getApplicationContext(), CoursesActivity.class);
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

                    mDrawerLayout.closeDrawers();
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

                }

                // close drawer when item is tapped
                mDrawerLayout.closeDrawers();

                return true;
            }
        });
    }

    public void loadFileNames(){

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        File ea_folder = new File(Environment.getExternalStorageDirectory() + File.separator + "EA Exam Answers");
        String path = ea_folder + File.separator;
        File file = new File(path);

        File[] files = file.listFiles();

        if (files.length == 0) {
            Toast.makeText(getApplicationContext(), "No Files to Show", Toast.LENGTH_SHORT).show();
            DownloadedFileModel downloadedFileName = new DownloadedFileModel("No Downloaded Files");
            m_DownloadedFile_List.add(downloadedFileName);
        }else{
            for(File aFile : files){

                String fileName = aFile.getName();
                DownloadedFileModel downloadedFileName = new DownloadedFileModel(aFile.getName());
                m_DownloadedFile_List.add(downloadedFileName);
            }
        }
        m_DownloadFile_Adapter.notifyDataSetChanged();
        progressDialog.dismiss();
    }

    // Method for opening a pdf file
    private void viewPdf(String pdfFileName) {

        File ea_folder = new File(Environment.getExternalStorageDirectory() + File.separator + "EA Exam Answers");
        File pdfFile = new File(ea_folder + File.separator + pdfFileName);
        Uri path = Uri.fromFile(pdfFile);

        // Setting the intent for pdf reader
        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(path, "application/pdf");
        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        try {
            startActivity(pdfIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "Can't read pdf file", Toast.LENGTH_SHORT).show();
        }
    }
}
