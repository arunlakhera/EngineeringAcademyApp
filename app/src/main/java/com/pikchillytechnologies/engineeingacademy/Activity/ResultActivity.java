package com.pikchillytechnologies.engineeingacademy.Activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.pikchillytechnologies.engineeingacademy.HelperFiles.SessionHandler;
import com.pikchillytechnologies.engineeingacademy.Model.UserResultModel;
import com.pikchillytechnologies.engineeingacademy.R;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ResultActivity extends AppCompatActivity {

    private TextView m_TextView_Activity_Title;
    private Button m_Button_Res_View_Answers;
    private Button m_Button_Share;
    private Button m_Button_Back;

    private Bundle m_User_Exam_Bundle;
    private String m_User_Id;
    private String m_User_Name;
    private String m_Exam_Id;
    private String m_Total_Questions;
    private String m_Title;
    private String m_Correct;
    private String m_Wrong;
    private String m_Not_Attempted;
    private String m_Score;

    private TextView m_TextView_Total_Questions;
    private TextView m_TextView_Total_Correct;
    private TextView m_TextView_Total_Wrong;
    private TextView m_TextView_Total_Not_Attempted;
    private TextView m_TextView_Total_Score;
    private TextView m_TextView_User_Name;
    private PieChart pieChart;

    private LinearLayout m_Layout_Result_PDF;
    private Bitmap bitmap;

    //Navigation Drawer
    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;
    private Button menuButton;
    private RecyclerView.LayoutManager m_Layout_Manager;

    private ProgressDialog progressDialog;
    private UserResultModel userResult;
    private StringRequest stringRequest;
    private RequestQueue requestQueue;

    private SessionHandler session;
    private Date date;
    private String timeStamp;
    private int MY_PERMISSIONS_REQUEST_WRITE = 100;
    private int MY_PERMISSIONS_REQUEST_READ = 200;

    final private int REQUEST_MULTIPLE_PERMISSIONS = 124;
    private String appLink = "https://play.google.com/store/apps/details?id=com.pikchillytechnologies.engineeingacademy";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        session = new SessionHandler(getApplicationContext());

        m_User_Exam_Bundle = getIntent().getExtras();
        m_TextView_Activity_Title = findViewById(R.id.textView_Activity_Title);
        m_Button_Res_View_Answers = findViewById(R.id.button_Res_View_Answers);
        m_TextView_Total_Questions = findViewById(R.id.textView_Res_Total_Questions);
        m_TextView_Total_Correct = findViewById(R.id.textView_Res_Total_Correct);
        m_TextView_Total_Wrong = findViewById(R.id.textView_Res_Total_Wrong);
        m_TextView_Total_Not_Attempted = findViewById(R.id.textView_Res_Total_Not_Attempted);
        m_TextView_Total_Score = findViewById(R.id.textView_Res_Total_Score);
        m_TextView_User_Name = findViewById(R.id.textView_Res_Name);
        m_Layout_Result_PDF = findViewById(R.id.layout_Result);
        m_Button_Share = findViewById(R.id.button_Res_Share);
        m_Button_Back = findViewById(R.id.button_Back);

        m_TextView_Activity_Title.setText(getResources().getString(R.string.result));
        m_Button_Back.setVisibility(View.INVISIBLE);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

        m_User_Id = m_User_Exam_Bundle.getString(getResources().getString(R.string.userid),"User Id");
        m_User_Name = m_User_Exam_Bundle.getString("username", "User Name");
        m_Exam_Id = m_User_Exam_Bundle.getString(getResources().getString(R.string.examid), "Exam Id");
        m_Title = m_User_Exam_Bundle.getString(getResources().getString(R.string.title), "Exam");
        m_Total_Questions = m_User_Exam_Bundle.getString("total_questions", "Total Questions");
        m_Correct = m_User_Exam_Bundle.getString("correct", "Correct");
        m_Wrong = m_User_Exam_Bundle.getString("wrong", "Wrong");
        m_Not_Attempted = m_User_Exam_Bundle.getString("not_attempted", "Not Attempted");
        m_Score = m_User_Exam_Bundle.getString("total_marks", "Total Marks");

        mDrawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        menuButton = findViewById(R.id.button_Menu);

        progressDialog.show();
        updateUI();

        progressDialog.dismiss();

        m_Button_Res_View_Answers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent destinationDetailIntent = new Intent(ResultActivity.this, AnswersActivity.class);
                destinationDetailIntent.putExtra(getResources().getString(R.string.userid), m_User_Id);
                destinationDetailIntent.putExtra("username", m_User_Name);
                destinationDetailIntent.putExtra(getResources().getString(R.string.examid), m_Exam_Id);
                destinationDetailIntent.putExtra(getResources().getString(R.string.title), m_Title);
                destinationDetailIntent.putExtra("total_questions", m_Total_Questions);
                destinationDetailIntent.putExtra("correct", m_Correct);
                destinationDetailIntent.putExtra("wrong", m_Wrong);
                destinationDetailIntent.putExtra("not_attempted", m_Not_Attempted);
                destinationDetailIntent.putExtra("total_marks", m_Score);

                startActivity(destinationDetailIntent);

            }
        });

        m_Button_Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("size"," "+m_Layout_Result_PDF.getWidth() +"  "+m_Layout_Result_PDF.getWidth());

                // Check Api Version.
                int currentApiVersion = Build.VERSION.SDK_INT;
                if (currentApiVersion >= Build.VERSION_CODES.KITKAT) {
                    // Kitkat
                    Log.e("SDK_VERSION-->>","Your version is:" + currentApiVersion);

                    bitmap = loadBitmapFromView(m_Layout_Result_PDF, m_Layout_Result_PDF.getWidth(), m_Layout_Result_PDF.getHeight());

                    if ((ContextCompat.checkSelfPermission(ResultActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(ResultActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED)) {

                        accessLocationDialog();
                    }else{

                        createPdf();
                    }


                } else {
                    // Before Kitkat
                    Toast.makeText(getApplicationContext(),"Your android version does not support creation of PDF which is required to share.",Toast.LENGTH_SHORT).show();
                    Log.e("SDK_VERSION-->>", "Generate PDF is not available for your android version." + currentApiVersion);

                }
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
                    startActivity(new Intent(getApplicationContext(), ArticlesActivity.class));
                }else if(menuItem.getTitle().equals(getResources().getString(R.string.my_downloads))){

                    Intent destinationDetailIntent = new Intent(getApplicationContext(), MyDownloadsActivity.class);
                    destinationDetailIntent.putExtra(getResources().getString(R.string.userid), m_User_Id);
                    destinationDetailIntent.putExtra("username", m_User_Name);
                    startActivity(destinationDetailIntent);

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

    public static Bitmap loadBitmapFromView(View v, int width, int height) {
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);

        Drawable bgDrawable = v.getBackground();
        if(bgDrawable != null){
            bgDrawable.draw(c);
        }else{
            c.drawColor(Color.WHITE);
        }

        v.draw(c);
        return b;
    }

    public void accessLocationDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permission Required");
        builder.setMessage("EA App needs permission to save data on your phone.")
        .setPositiveButton("Allow", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                ActivityCompat.requestPermissions(ResultActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE);

                ActivityCompat.requestPermissions(ResultActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ);

            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        accessLocationDialog();
                    }
                });

        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Alert! Permission Required");
        alert.show();

    }

    private void createPdf(){

        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        float height = displaymetrics.heightPixels ;
        float width = displaymetrics.widthPixels ;

        int convertHeight = (int) height;
        int convertWidth = (int) width;

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(convertWidth, convertHeight, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();

        Paint paint = new Paint();
        canvas.drawPaint(paint);

        bitmap = Bitmap.createScaledBitmap(bitmap, convertWidth, convertHeight, true);

        paint.setColor(Color.BLUE);
        canvas.drawBitmap(bitmap, 0, 0 , null);
        document.finishPage(page);

        // write the document content
        File ea_folder = new File(Environment.getExternalStorageDirectory() + File.separator + "EAExamResults");
        boolean success = true;

        if (!ea_folder.exists()) {
            success = ea_folder.mkdirs();
        }

        if(success){

            date = new Date();
            timeStamp = new SimpleDateFormat("yyyyMMdd_mmss", Locale.ENGLISH).format(date);

            String targetPdf = ea_folder + File.separator + m_Title + timeStamp + ".pdf";
            File filePath;
            filePath = new File(targetPdf);

            try {
                document.writeTo(new FileOutputStream(filePath));
                // close the document
                document.close();

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
            }

            shareData();

        }else{
            Toast.makeText(this, "Could not find Directory!!!" + ea_folder, Toast.LENGTH_SHORT).show();
        }

    }

    public void shareData(){

        File ea_folder = new File(Environment.getExternalStorageDirectory() + File.separator + "EAExamResults");
        String path = ea_folder + File.separator + m_Title + timeStamp + ".pdf";

        File file = new File(path);
        if (file.exists())
        {
            //Intent intent=new Intent(Intent.ACTION_VIEW);
            Intent intent = new Intent(Intent.ACTION_SENDTO);

            Uri uri = Uri.fromFile(file);
            intent.setData(Uri.parse("mailto:"));
            intent.putExtra(Intent.EXTRA_SUBJECT,  "Exam Result for:" + m_User_Name);
            intent.putExtra(Intent.EXTRA_STREAM, uri);

            try
            {
                startActivity(intent);
            }
            catch(ActivityNotFoundException e)
            {
                Toast.makeText(getApplicationContext(), "No Application available to view pdf", Toast.LENGTH_LONG).show();
            }
        }
    }


    public void updateUI(){

        // Update Score
        m_TextView_Total_Questions.setText(m_Total_Questions);
        m_TextView_Total_Correct.setText(m_Correct);
        m_TextView_Total_Wrong.setText(m_Wrong);
        m_TextView_Total_Not_Attempted.setText(m_Not_Attempted);
        m_TextView_Total_Score.setText(m_Score);
        m_TextView_User_Name.setText(m_User_Name.toUpperCase());

        drawChart();

    }

    public void drawChart(){

        float correct = Float.valueOf(m_Correct);
        float wrong = Float.valueOf(m_Wrong);
        float not_attempted = Float.valueOf(m_Not_Attempted);

        // update chart
        pieChart = findViewById(R.id.pieChart);
        pieChart.setUsePercentValues(true);

        ArrayList<PieEntry> yvalues = new ArrayList<PieEntry>();

        if(!m_Correct.equals("0")){
            yvalues.add(new PieEntry(correct, "Correct", 0));
        }

        if(!m_Wrong.equals("0")){
            yvalues.add(new PieEntry(wrong, "Wrong", 1));
        }

        yvalues.add(new PieEntry(not_attempted, "Not Attempted", 2));

        PieDataSet dataSet = new PieDataSet(yvalues, m_Title);
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.WHITE);

        Description description = new Description();
        description.setText("");
        description.setTextColor(Color.CYAN);

        pieChart.setDescription(description);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(R.color.colorPrimary);
        pieChart.setTransparentCircleRadius(40f);
        pieChart.setHoleRadius(40f);
        pieChart.setData(data);
    }

    @Override
    public void onBackPressed() {

        Toast.makeText(getApplicationContext(),"Only one attempt per paper is allowed. You have already submitted the Exam!", Toast.LENGTH_SHORT).show();
        //super.onBackPressed();

    }
}
