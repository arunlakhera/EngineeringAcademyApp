package com.pikchillytechnologies.engineeingacademy.Activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import com.pikchillytechnologies.engineeingacademy.Adapter.ArticlesAdapter;
import com.pikchillytechnologies.engineeingacademy.Adapter.CoursesAdapter;
import com.pikchillytechnologies.engineeingacademy.HelperFiles.EAHelper;
import com.pikchillytechnologies.engineeingacademy.HelperFiles.SessionHandler;
import com.pikchillytechnologies.engineeingacademy.Model.ArticlesModel;
import com.pikchillytechnologies.engineeingacademy.Model.CoursesModel;
import com.pikchillytechnologies.engineeingacademy.Model.RecyclerTouchListener;
import com.pikchillytechnologies.engineeingacademy.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ArticlesActivity extends AppCompatActivity {

    private TextView m_TextView_Activity_Title;

    private Button m_Button_Back;
    private Bundle m_User_Bundle;
    private String m_User_Id;
    private String m_User_Name;

    //Navigation Drawer
    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;
    private Button menuButton;
    private SessionHandler session;

    private List<ArticlesModel> m_Articles_List;
    private RecyclerView m_RecyclerView_Articles;
    private ArticlesAdapter m_Articles_Adapter;
    private RecyclerView.LayoutManager m_Layout_Manager;

    private EAHelper m_Helper;
    private String articleURL;
    private String articleName;

    private int MY_PERMISSIONS_REQUEST_WRITE = 100;
    private int MY_PERMISSIONS_REQUEST_READ = 200;

    private String url = "http://onlineengineeringacademy.co.in/api/all_article_request";
    private String appLink = "https://play.google.com/store/apps/details?id=com.pikchillytechnologies.engineeingacademy";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);
        session = new SessionHandler(getApplicationContext());
        m_Helper = new EAHelper();
        mDrawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        menuButton = findViewById(R.id.button_Menu);
        m_TextView_Activity_Title = findViewById(R.id.textView_Activity_Title);
        m_Button_Back = findViewById(R.id.button_Back);
        m_RecyclerView_Articles = findViewById(R.id.recyclerView_Articles);

        m_TextView_Activity_Title.setText("Articles");
        m_User_Bundle = getIntent().getExtras();
        m_User_Id = m_User_Bundle.getString(getResources().getString(R.string.userid), "User Id");
        m_User_Name = m_User_Bundle.getString("username", "User Name");

        m_Layout_Manager = new LinearLayoutManager(getApplicationContext());
        m_Articles_List = new ArrayList<>();
        m_Articles_Adapter = new ArticlesAdapter(getApplicationContext(),m_Articles_List);
        m_RecyclerView_Articles.setHasFixedSize(true);
        m_RecyclerView_Articles.setLayoutManager(m_Layout_Manager);
        m_RecyclerView_Articles.setAdapter(m_Articles_Adapter);

        // Check if app has read and write permissions
        if (ContextCompat.checkSelfPermission(ArticlesActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted

            ActivityCompat.requestPermissions(ArticlesActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_WRITE);
        }

        if (ContextCompat.checkSelfPermission(ArticlesActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted

            ActivityCompat.requestPermissions(ArticlesActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ);
        }

        if(m_Helper.isNetworkAvailable(getApplicationContext())){

            prepareArticlesData();

        }else{
            Toast.makeText(getApplicationContext(),"Please connect to Internet.", Toast.LENGTH_LONG).show();
        }


        m_RecyclerView_Articles.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), m_RecyclerView_Articles, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                if(m_Helper.isNetworkAvailable(getApplicationContext())){

                    articleURL = m_Articles_List.get(position).getM_Article_URL();
                    articleName = m_Articles_List.get(position).getM_Article_Name();

                    new DownloadFile().execute(articleURL);

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
                    mDrawerLayout.closeDrawers();
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

                    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build();
                    GoogleSignInClient signedClient = GoogleSignIn.getClient(ArticlesActivity.this, gso);

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

    public void prepareArticlesData(){

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
                            JSONArray articlesArray = obj.getJSONArray("articles");

                            //now looping through all the elements of the json array
                            for (int i = 0; i < articlesArray.length(); i++) {
                                //getting the json object of the particular index inside the array
                                JSONObject articlesObject = articlesArray.getJSONObject(i);

                                //creating a tutorial object and giving them the values from json object
                                ArticlesModel article = new ArticlesModel(articlesObject.getString("article_url"),articlesObject.getString("article_name"), articlesObject.getString("article_category"));
                                m_Articles_List.add(article);
                            }

                            //creating custom adapter object
                            m_Articles_Adapter.notifyDataSetChanged();
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

    private class DownloadFile extends AsyncTask<String, String, String> {

        private ProgressDialog progressDialog;
        private String fileName;
        private String folder;
        private boolean isDownloaded;

        /**
         * Before starting background thread
         * Show Progress Bar Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.progressDialog = new ProgressDialog(ArticlesActivity.this);
            this.progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            this.progressDialog.setCancelable(false);
            this.progressDialog.show();
        }

        /**
         * Downloading file in background thread
         */
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection connection = url.openConnection();
                connection.connect();
                // getting file length
                int lengthOfFile = connection.getContentLength();

                // input stream to read file - with 8k buffer
                InputStream input = new BufferedInputStream(url.openStream(), 8192);

                Date date = new Date();
                String timeStamp = new SimpleDateFormat("yyyyMMdd_mmss", Locale.ENGLISH).format(date);

                //Append timestamp to file name
                fileName = timeStamp + "_" + articleName + ".pdf";

                //External directory path to save file
                folder = Environment.getExternalStorageDirectory() + File.separator + "EAArticles/";

                //Create androiddeft folder if it does not exist
                File directory = new File(folder);

                if (!directory.exists()) {
                    directory.mkdirs();
                }

                // Output stream to write file
                OutputStream output = new FileOutputStream(folder + fileName);

                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress("" + (int) ((total * 100) / lengthOfFile));
                    Log.d("Progress", "Progress: " + (int) ((total * 100) / lengthOfFile));

                    // writing data to file
                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();
                return "Downloaded at: " + folder + fileName;

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

            return "Something went wrong";
        }

        /**
         * Updating progress bar
         */
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
            progressDialog.setProgress(Integer.parseInt(progress[0]));
        }

        @Override
        protected void onPostExecute(String message) {
            // dismiss the dialog after the file was downloaded
            this.progressDialog.dismiss();

            // Display File path after downloading
            Toast.makeText(getApplicationContext(),
                    message, Toast.LENGTH_LONG).show();
        }
    }
}

