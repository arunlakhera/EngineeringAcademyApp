package com.pikchillytechnologies.engineeingacademy.Activity;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

    private List<DownloadedFileModel> m_DownloadedFile_List;
    private RecyclerView m_RecyclerView_DownloadedFile;
    private DownloadedFileModel downloadedFile;
    private MyDownloadsAdapter m_DownloadFile_Adapter;

    private String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_downloads);

        m_TextView_Activity_Title = findViewById(R.id.textView_Activity_Title);
        m_TextView_Activity_Title.setText("My Downloads");

        m_Button_Back = findViewById(R.id.button_Back);
        m_Button_Back.setVisibility(View.VISIBLE);
        m_DownloadedFile_List = new ArrayList<>();

        m_RecyclerView_DownloadedFile = findViewById(R.id.recyclerView_MyDownloads);
        m_DownloadFile_Adapter = new MyDownloadsAdapter(m_DownloadedFile_List);

        RecyclerView.LayoutManager m_Layout_Manager = new LinearLayoutManager(getApplicationContext());
        m_RecyclerView_DownloadedFile.setLayoutManager(m_Layout_Manager);
        m_RecyclerView_DownloadedFile.setAdapter(m_DownloadFile_Adapter);

        loadFileNames();

        m_RecyclerView_DownloadedFile.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), m_RecyclerView_DownloadedFile, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                String selectedFileName = m_DownloadedFile_List.get(position).getM_DownloadedFileName();

                Toast.makeText(getApplicationContext(),"File:" + selectedFileName, Toast.LENGTH_LONG).show();
                viewPdf(selectedFileName);
            }

            @Override
            public void onLongClick(View view, int position) {
                Log.d("LongPress:", "Long Pressed");
            }
        }));

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
