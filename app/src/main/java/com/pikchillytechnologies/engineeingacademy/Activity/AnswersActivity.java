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
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.pikchillytechnologies.engineeingacademy.Adapter.AnswersAdapter;
import com.pikchillytechnologies.engineeingacademy.Adapter.ExamQuestionAdapter;
import com.pikchillytechnologies.engineeingacademy.HelperFiles.EAHelper;
import com.pikchillytechnologies.engineeingacademy.HelperFiles.SessionHandler;
import com.pikchillytechnologies.engineeingacademy.Model.AnswersModel;
import com.pikchillytechnologies.engineeingacademy.Model.ExamQuestionModel;
import com.pikchillytechnologies.engineeingacademy.Model.SubCoursePackage;
import com.pikchillytechnologies.engineeingacademy.Model.UserResponseModel;
import com.pikchillytechnologies.engineeingacademy.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.SimpleTimeZone;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pikchillytechnologies.engineeingacademy.Adapter.AnswersAdapter;
import com.pikchillytechnologies.engineeingacademy.Adapter.ExamQuestionAdapter;
import com.pikchillytechnologies.engineeingacademy.HelperFiles.EAHelper;
import com.pikchillytechnologies.engineeingacademy.HelperFiles.SessionHandler;
import com.pikchillytechnologies.engineeingacademy.Model.AnswersModel;
import com.pikchillytechnologies.engineeingacademy.Model.ExamQuestionModel;
import com.pikchillytechnologies.engineeingacademy.Model.SubCoursePackage;
import com.pikchillytechnologies.engineeingacademy.Model.UserResponseModel;
import com.pikchillytechnologies.engineeingacademy.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.SimpleTimeZone;

public class AnswersActivity extends AppCompatActivity {

    private TextView m_TextView_Activity_Title;
    private Button m_Button_Back;
    private Bundle m_Exam_Answer_Bundle;

    private String m_User_Id;
    private String m_User_Name;
    private String m_Exam_Id;
    private String m_Title;
    private String m_Total_Questions;
    private String m_Correct;
    private String m_Wrong;
    private String m_NotAttempted;
    private String total_marks;

    private EAHelper m_Helper;

    private int question_number;
    private int total_Questions;

    private List<AnswersModel> m_Answer_List;
    private RecyclerView m_RecyclerView_Answers_List;
    private AnswersAdapter m_Answers_Adapter;
    private ProgressDialog progressDialog;
    private TextView mTextView_Title;

    private StringRequest stringRequest;
    private RequestQueue requestQueue;

    private List<ExamQuestionModel> m_Question_List;
    private List<UserResponseModel> m_User_Response_List;

    private RecyclerView.LayoutManager m_Layout_Manager;

    //Navigation Drawer
    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;
    private Button menuButton;

    private LinearLayout m_Layout_Result_PDF;
    private ScrollView m_ScrollView_Result_PDF;
    private Bitmap bitmap;
    private Button m_Button_Download;

    //private String getUserResponseURL = "https://pikchilly.com/api/get_user_response.php";
    private String getUserResponseURL = "http://onlineengineeringacademy.co.in/api/get_response_request";
    private String appLink = "https://play.google.com/store/apps/details?id=com.pikchillytechnologies.engineeingacademy";

    private SessionHandler session;

    private int MY_PERMISSIONS_REQUEST_WRITE = 100;
    private int MY_PERMISSIONS_REQUEST_READ = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answers);

        // Function to initialize values
        initValues();

        //Function to Set Values
        setValues();

        // Check if internet is available
        if (m_Helper.isNetworkAvailable(getApplicationContext())) {

            prepareExamQuestionsData();

        } else {
            Toast.makeText(getApplicationContext(), "Please connect to Internet.", Toast.LENGTH_LONG).show();
        }

        // Action to perform when Download button is pressed
        m_Button_Download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                m_RecyclerView_Answers_List.getLayoutManager().scrollToPosition(0);
                pdfDownload();

            }
        });

        // Action to perform when Back button is pressed
        m_Button_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent destinationDetailIntent = new Intent(AnswersActivity.this, ResultActivity.class);
                destinationDetailIntent.putExtra(getResources().getString(R.string.userid), m_User_Id);
                destinationDetailIntent.putExtra("username", m_User_Name);
                destinationDetailIntent.putExtra(getResources().getString(R.string.examid), m_Exam_Id);
                destinationDetailIntent.putExtra(getResources().getString(R.string.title), m_Title);
                destinationDetailIntent.putExtra("total_questions", String.valueOf(m_Total_Questions));
                destinationDetailIntent.putExtra("correct", String.valueOf(m_Correct));
                destinationDetailIntent.putExtra("wrong", String.valueOf(m_Wrong));
                destinationDetailIntent.putExtra("not_attempted", String.valueOf(m_NotAttempted));
                destinationDetailIntent.putExtra("total_marks", String.valueOf(total_marks));
                startActivity(destinationDetailIntent);

            }
        });

        // Action to perform when Menu Button is pressed
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDrawerLayout.openDrawer(navigationView);
            }
        });

        // Action to draw Navigation drawer
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                // set item as selected to persist highlight
                menuItem.setChecked(true);

                if (menuItem.getTitle().equals(getResources().getString(R.string.courses))) {
                    Intent destinationDetailIntent = new Intent(getApplicationContext(), CoursesActivity.class);
                    destinationDetailIntent.putExtra(getResources().getString(R.string.userid), m_User_Id);
                    destinationDetailIntent.putExtra("username", m_User_Name);
                    startActivity(destinationDetailIntent);
                } else if (menuItem.getTitle().equals(getResources().getString(R.string.articles))) {
                    Intent destinationDetailIntent = new Intent(getApplicationContext(), ArticlesActivity.class);
                    destinationDetailIntent.putExtra(getResources().getString(R.string.userid), m_User_Id);
                    destinationDetailIntent.putExtra("username", m_User_Name);
                    startActivity(destinationDetailIntent);
                } else if (menuItem.getTitle().equals(getResources().getString(R.string.my_downloads))) {

                    Intent destinationDetailIntent = new Intent(getApplicationContext(), MyDownloadsActivity.class);
                    destinationDetailIntent.putExtra(getResources().getString(R.string.userid), m_User_Id);
                    destinationDetailIntent.putExtra("username", m_User_Name);
                    startActivity(destinationDetailIntent);

                } else if (menuItem.getTitle().equals(getResources().getString(R.string.my_results))) {

                    Intent destinationDetailIntent = new Intent(getApplicationContext(), MyResultsActivity.class);
                    destinationDetailIntent.putExtra(getResources().getString(R.string.userid), m_User_Id);
                    destinationDetailIntent.putExtra("username", m_User_Name);
                    startActivity(destinationDetailIntent);

                } else if (menuItem.getTitle().equals(getResources().getString(R.string.update_profile))) {

                    Intent destinationDetailIntent = new Intent(getApplicationContext(), UpdateProfileActivity.class);
                    destinationDetailIntent.putExtra(getResources().getString(R.string.userid), m_User_Id);
                    destinationDetailIntent.putExtra("username", m_User_Name);
                    startActivity(destinationDetailIntent);
                } else if(menuItem.getTitle().equals(getResources().getString(R.string.share_app))){

                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_SUBJECT, "Engineering Academy Dehradun App");
                    String message = "\nLet me recommend you this application: \n" + appLink;
                    i.putExtra(Intent.EXTRA_TEXT, message);
                    startActivity(Intent.createChooser(i, "Select one.."));

                }else if (menuItem.getTitle().equals(getResources().getString(R.string.logout))) {

                    session.logoutUser();
                    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build();
                    GoogleSignInClient signedClient = GoogleSignIn.getClient(AnswersActivity.this, gso);

                    signedClient.signOut();
                    LoginManager.getInstance().logOut();
                    Intent destinationDetailIntent = new Intent(getApplicationContext(), SignInActivity.class);
                    startActivity(destinationDetailIntent);

                }

                // close drawer when item is tapped
                mDrawerLayout.closeDrawers();

                return true;
            }
        });
    }

    /**
     * Function to initialize the values
     */
    public void initValues() {
        m_TextView_Activity_Title = findViewById(R.id.textView_Activity_Title);
        m_RecyclerView_Answers_List = findViewById(R.id.recyclerView_Answers);
        m_Button_Back = findViewById(R.id.button_Back);
        m_Layout_Result_PDF = findViewById(R.id.layout_All_Answers);
        m_Button_Download = findViewById(R.id.button_Ans_Download);
        mTextView_Title = findViewById(R.id.textview_Title);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        menuButton = findViewById(R.id.button_Menu);

        session = new SessionHandler(getApplicationContext());
        m_Helper = new EAHelper();
        m_Answer_List = new ArrayList<>();
        m_Question_List = new ArrayList<>();
        m_User_Response_List = new ArrayList<>();
        m_Answers_Adapter = new AnswersAdapter(getApplicationContext(), m_Answer_List);
        m_Layout_Manager = new LinearLayoutManager(getApplicationContext());
        progressDialog = new ProgressDialog(this);

    }

    /**
     * Function to set values
     */
    public void setValues() {
        m_Exam_Answer_Bundle = getIntent().getExtras();
        m_User_Id = m_Exam_Answer_Bundle.getString(getResources().getString(R.string.userid), "User Id");
        m_User_Name = m_Exam_Answer_Bundle.getString("username", "User Name");
        m_Exam_Id = m_Exam_Answer_Bundle.getString(getResources().getString(R.string.examid), "Exam Id");
        m_Title = m_Exam_Answer_Bundle.getString(getResources().getString(R.string.title), "Exam");
        m_Total_Questions = m_Exam_Answer_Bundle.getString("total_questions", "0");
        m_Correct = m_Exam_Answer_Bundle.getString("correct", "0");
        m_Wrong = m_Exam_Answer_Bundle.getString("wrong", "0");
        m_NotAttempted = m_Exam_Answer_Bundle.getString("not_attempted", "0");
        total_marks = m_Exam_Answer_Bundle.getString("total_marks", "0");
        total_Questions = Integer.valueOf(m_Total_Questions);

        mTextView_Title.setText(m_Title);
        m_TextView_Activity_Title.setText(m_Title);
        progressDialog.setMessage("Loading...");
        m_Button_Back.setVisibility(View.VISIBLE);
        m_RecyclerView_Answers_List.setHasFixedSize(true);
        m_RecyclerView_Answers_List.setLayoutManager(m_Layout_Manager);
        m_RecyclerView_Answers_List.setAdapter(m_Answers_Adapter);
        question_number = 0;
    }

    public void pdfDownload() {

        // Check Api Version.
        int currentApiVersion = Build.VERSION.SDK_INT;
        if (currentApiVersion >= Build.VERSION_CODES.KITKAT) {
            // Kitkat
            Log.e("SDK_VERSION-->>", "Your version is:" + currentApiVersion);

            if ((ContextCompat.checkSelfPermission(AnswersActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(AnswersActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED)) {

                accessLocationDialog();

            } else {

                if (m_Helper.isNetworkAvailable(getApplicationContext())) {

                    m_RecyclerView_Answers_List.measure(
                            View.MeasureSpec.makeMeasureSpec(m_RecyclerView_Answers_List.getWidth(), View.MeasureSpec.EXACTLY),
                            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

                    bitmap = loadBitmapFromView(m_RecyclerView_Answers_List, m_RecyclerView_Answers_List.getWidth(), m_RecyclerView_Answers_List.getMeasuredHeight());

                    createPdf();

                } else {
                    Toast.makeText(getApplicationContext(), "Please connect to Internet.", Toast.LENGTH_LONG).show();
                }
            }

        } else {
            // Before Kitkat
            Toast.makeText(getApplicationContext(), "Your android version does not support creation of PDF which is required to Download.", Toast.LENGTH_SHORT).show();
            Log.e("SDK_VERSION-->>", "Generate PDF is not available for your android version." + currentApiVersion);

        }
    }

    public void accessLocationDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permission Required");
        builder.setMessage("EA App needs permission to save data on your phone.")
                .setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        ActivityCompat.requestPermissions(AnswersActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                MY_PERMISSIONS_REQUEST_WRITE);

                        ActivityCompat.requestPermissions(AnswersActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                MY_PERMISSIONS_REQUEST_READ);

                        pdfDownload();

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

    /**
     * Function to load exam questions data
     */
    public void prepareExamQuestionsData() {

        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, getUserResponseURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion
                        Log.e("USERRESPONSE--->>", response);

                        response = response.trim();
                        try {
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);

                            // Getting array inside the JSONObject
                            JSONArray examArray = obj.getJSONArray("user_response");

                            //now looping through all the elements of the json array
                            for (int i = 0; i < examArray.length(); i++) {
                                //getting the json object of the particular index inside the array
                                JSONObject examObject = examArray.getJSONObject(i);
                                question_number = question_number + 1;
                                AnswersModel exam = new AnswersModel(String.valueOf(question_number), examObject.getString("question_id"), examObject.getString("question_eng"),
                                        examObject.getString("question_hindi"), examObject.getString("question_eng_img"), examObject.getString("question_hindi_img"),
                                        examObject.getString("answer1_eng"), examObject.getString("answer2_eng"), examObject.getString("answer3_eng"),
                                        examObject.getString("answer4_eng"), examObject.getString("answer5_eng"), examObject.getString("answer6_eng"),
                                        examObject.getString("answer1_hindi"), examObject.getString("answer2_hindi"), examObject.getString("answer3_hindi"),
                                        examObject.getString("answer4_hindi"), examObject.getString("answer5_hindi"), examObject.getString("answer6_hindi"),
                                        examObject.getString("answer1_flag"), examObject.getString("answer2_flag"), examObject.getString("answer3_flag"),
                                        examObject.getString("answer4_flag"), examObject.getString("answer5_flag"), examObject.getString("answer6_flag"),
                                        examObject.getString("question_type"), examObject.getString("question_lang"), examObject.getString("answer_type"),
                                        examObject.getString("answer_lang"), examObject.getString("answer1"), examObject.getString("answer2"), examObject.getString("answer3"),
                                        examObject.getString("answer4"), examObject.getString("answer5"), examObject.getString("answer6")
                                );

                                m_Answer_List.add(exam);
                            }

                            //creating custom adapter object
                            m_Answers_Adapter.notifyDataSetChanged();
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
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", String.valueOf(m_User_Id));
                params.put("exam_id", String.valueOf(m_Exam_Id));

                return params;
            }
        };

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);
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

    private void createPdf(){
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        float hight = m_RecyclerView_Answers_List.getMeasuredHeight() ;
        float width = m_RecyclerView_Answers_List.getWidth() ;

        int convertHighet = (int) hight, convertWidth = (int) width;

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(m_RecyclerView_Answers_List.getWidth(), m_RecyclerView_Answers_List.getMeasuredHeight(), total_Questions).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();

        Paint paint = new Paint();
        canvas.drawPaint(paint);

        bitmap = Bitmap.createScaledBitmap(bitmap, m_RecyclerView_Answers_List.getWidth(), m_RecyclerView_Answers_List.getMeasuredHeight(), true);

        paint.setColor(Color.BLUE);
        canvas.drawBitmap(bitmap, 0, 0 , null);
        document.finishPage(page);

        File ea_folder = new File(Environment.getExternalStorageDirectory() + File.separator + "EAAnswers");
        boolean success = true;

        if (!ea_folder.exists()) {
            success = ea_folder.mkdirs();
        }

        if(success){
            Date date = new Date();
            String timeStamp = new SimpleDateFormat("yyyyMMdd_mmss", Locale.ENGLISH).format(date);

            // write the document content
            File filePath;
            filePath = new File(ea_folder + File.separator + m_Title + timeStamp + ".pdf");

            try {
                document.writeTo(new FileOutputStream(filePath));

                // close the document
                document.close();
                Toast.makeText(this, "PDF Downloaded Successfully!!!", Toast.LENGTH_SHORT).show();

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error Occurred while Creating PDF " + e.toString(), Toast.LENGTH_LONG).show();
            }

        }else{
            Toast.makeText(this, "Could not find Directory!!!" + ea_folder, Toast.LENGTH_SHORT).show();
        }

        // close the document
        document.close();
    }

}
