package com.pikchillytechnologies.engineeingacademy.Activity;

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

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageScrollListener;
import com.github.barteksc.pdfviewer.listener.OnRenderListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.github.barteksc.pdfviewer.util.FitPolicy;
import com.pikchillytechnologies.engineeingacademy.Activity.ArticlesActivity;
import com.pikchillytechnologies.engineeingacademy.Activity.CoursesActivity;
import com.pikchillytechnologies.engineeingacademy.Activity.MyDownloadsActivity;
import com.pikchillytechnologies.engineeingacademy.Activity.MyResultsActivity;
import com.pikchillytechnologies.engineeingacademy.Activity.SignInActivity;
import com.pikchillytechnologies.engineeingacademy.Activity.UpdateProfileActivity;
import com.pikchillytechnologies.engineeingacademy.HelperFiles.SessionHandler;
import com.pikchillytechnologies.engineeingacademy.R;

import java.io.File;

public class PdfActivity extends AppCompatActivity {

    private Bundle m_PDF_Bundle;
    private String pdf;
    private String pdfType;
    private String pdfFolder;
    private PDFView pdfView;
    private Uri path;
    private TextView m_TextView_Activity_Title;

    private String m_User_Id;
    private String m_User_Name;

    //Navigation Drawer
    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;
    private Button menuButton;
    private RecyclerView.LayoutManager m_Layout_Manager;
    private SessionHandler session;
    Integer pageNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);

        m_TextView_Activity_Title = findViewById(R.id.textView_Activity_Title);
        session = new SessionHandler(getApplicationContext());
        m_PDF_Bundle = getIntent().getExtras();
        pdfView = findViewById(R.id.pdfView);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        menuButton = findViewById(R.id.button_Menu);

        m_Layout_Manager = new LinearLayoutManager(getApplicationContext());

        m_User_Id = m_PDF_Bundle.getString(getResources().getString(R.string.userid), "User Id");
        m_User_Name = m_PDF_Bundle.getString("username", "User Name");

        if(m_PDF_Bundle != null){

            pdf = m_PDF_Bundle.getString("pdf_File", "pdf name");
            pdfType = m_PDF_Bundle.getString("pdf_Type", "pdf type");

            if(pdfType.equals("Answers")){
                m_TextView_Activity_Title.setText(getResources().getString(R.string.answer_pdf));
                pdfFolder = "EAAnswers";
            }else if(pdfType.equals("Result")){
                m_TextView_Activity_Title.setText(getResources().getString(R.string.result_pdf));
                pdfFolder = "EAExamResults";
            }

            File ea_folder = new File(Environment.getExternalStorageDirectory() + File.separator + pdfFolder);
            File pdfFile = new File(ea_folder + File.separator + pdf);
            path = Uri.fromFile(pdfFile);

            pdfView.fromUri(path)
                    .enableAnnotationRendering(true)
                    .pageSnap(true)
                    .pageFitPolicy(FitPolicy.WIDTH)
                    .scrollHandle(new DefaultScrollHandle(this))
                    .spacing(10) // in dp
                    .load();

        }else {
            pdf="NoFile";
        }

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
                    Intent destinationDetailIntent = new Intent(getApplicationContext(), ArticlesActivity.class);
                    destinationDetailIntent.putExtra(getResources().getString(R.string.userid), m_User_Id);
                    destinationDetailIntent.putExtra("username", m_User_Name);
                    startActivity(destinationDetailIntent);
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

}
