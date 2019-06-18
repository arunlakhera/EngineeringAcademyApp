package com.pikchillytechnologies.engineeingacademy.Activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
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

import com.pikchillytechnologies.engineeingacademy.Adapter.MyDownloadsAdapter;
import com.pikchillytechnologies.engineeingacademy.BuildConfig;
import com.pikchillytechnologies.engineeingacademy.HelperFiles.SessionHandler;
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

    private SessionHandler session;
    private int MY_PERMISSIONS_REQUEST_WRITE = 100;
    private int MY_PERMISSIONS_REQUEST_READ = 200;
    private String appLink = "https://play.google.com/store/apps/details?id=com.pikchillytechnologies.engineeingacademy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_downloads);

        session = new SessionHandler(getApplicationContext());

        mDrawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        menuButton = findViewById(R.id.button_Menu);
        m_TextView_Activity_Title = findViewById(R.id.textView_Activity_Title);
        m_Button_Back = findViewById(R.id.button_Back);
        m_RecyclerView_DownloadedFile = findViewById(R.id.recyclerView_MyDownloads);

        m_User_Bundle = getIntent().getExtras();
        m_User_Id = m_User_Bundle.getString(getResources().getString(R.string.userid), "User Id");
        m_User_Name = m_User_Bundle.getString("username", "User Name");
        m_TextView_Activity_Title.setText("My Downloads");
        m_Button_Back.setVisibility(View.VISIBLE);

        m_DownloadedFile_List = new ArrayList<>();
        m_DownloadFile_Adapter = new MyDownloadsAdapter(m_DownloadedFile_List);
        m_Layout_Manager = new LinearLayoutManager(getApplicationContext());
        m_RecyclerView_DownloadedFile.setLayoutManager(m_Layout_Manager);
        m_RecyclerView_DownloadedFile.setAdapter(m_DownloadFile_Adapter);

        if (ContextCompat.checkSelfPermission(MyDownloadsActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted

            ActivityCompat.requestPermissions(MyDownloadsActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_WRITE);
        }

        if (ContextCompat.checkSelfPermission(MyDownloadsActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted

            ActivityCompat.requestPermissions(MyDownloadsActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ);
        }

        loadFileNames();

        m_RecyclerView_DownloadedFile.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), m_RecyclerView_DownloadedFile, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                String selectedFileName = m_DownloadedFile_List.get(position).getM_DownloadedFileName();
                //viewPdf(selectedFileName);
                openGeneratedPDF(selectedFileName);
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

                if(menuItem.getTitle().equals(getResources().getString(R.string.courses))){

                    Intent destinationDetailIntent = new Intent(getApplicationContext(), CoursesActivity.class);
                    destinationDetailIntent.putExtra(getResources().getString(R.string.userid), m_User_Id);
                    destinationDetailIntent.putExtra("username", m_User_Name);
                    startActivity(destinationDetailIntent);

                }else if(menuItem.getTitle().equals(getResources().getString(R.string.articles))){
                    Intent destinationDetailIntent = new Intent(getApplicationContext(), ArticlesActivity.class);
                    destinationDetailIntent.putExtra(getResources().getString(R.string.userid), m_User_Id);
                    destinationDetailIntent.putExtra("username", m_User_Name);
                    startActivity(destinationDetailIntent);
                }else if(menuItem.getTitle().equals(getResources().getString(R.string.my_downloads))){

                    mDrawerLayout.closeDrawers();

                }else if(menuItem.getTitle().equals(getResources().getString(R.string.my_results))){

                    Intent destinationDetailIntent = new Intent(getApplicationContext(), MyResultsActivity.class);
                    destinationDetailIntent.putExtra(getResources().getString(R.string.userid), m_User_Id);
                    destinationDetailIntent.putExtra("username", m_User_Name);
                    startActivity(destinationDetailIntent);

                }else if(menuItem.getTitle().equals(getResources().getString(R.string.update_profile))){

                    Intent destinationDetailIntent = new Intent(getApplicationContext(), UpdateProfileActivity.class);
                    destinationDetailIntent.putExtra(getResources().getString(R.string.userid), m_User_Id);
                    destinationDetailIntent.putExtra("username", m_User_Name);
                    startActivity(destinationDetailIntent);
                }else if(menuItem.getTitle().equals(getResources().getString(R.string.share_app))){

                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_SUBJECT, "Engineering Academy Dehradun App");
                    String message = "\nLet me recommend you this application: \n" + appLink;

                    i.putExtra(Intent.EXTRA_TEXT, message);
                    startActivity(Intent.createChooser(i, "Select one.."));


                }else if(menuItem.getTitle().equals(getResources().getString(R.string.logout))){

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

    public void loadFileNames(){

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        try{

            File ea_folder = new File(Environment.getExternalStorageDirectory() + File.separator + "EAAnswers/");
            String path = ea_folder.toString();
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

        }catch (Exception e){
            Log.e("Error:",e.getMessage());
        }

        m_DownloadFile_Adapter.notifyDataSetChanged();
        progressDialog.dismiss();
    }

    private void openGeneratedPDF(String pdfFileName){

        File ea_folder = new File(Environment.getExternalStorageDirectory() + File.separator + "EAAnswers/" + pdfFileName);
        String path = ea_folder.toString();
        File file = new File(path);

        //File file = new File("/sdcard/pdffromlayout.pdf");
        if (file.exists())
        {

            Intent intent=new Intent(Intent.ACTION_VIEW);
            //Uri uri = Uri.fromFile(file);
            Uri uri = FileProvider.getUriForFile(MyDownloadsActivity.this, BuildConfig.APPLICATION_ID + ".provider",file);

            intent.setDataAndType(uri, "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            try
            {
                startActivity(intent);
            }
            catch(ActivityNotFoundException e)
            {
                Toast.makeText(MyDownloadsActivity.this, "No Application available to view pdf", Toast.LENGTH_LONG).show();
            }
        }
    }

}
