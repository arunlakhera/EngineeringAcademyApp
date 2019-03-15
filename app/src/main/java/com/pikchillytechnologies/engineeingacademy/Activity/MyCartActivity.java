package com.pikchillytechnologies.engineeingacademy.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pikchillytechnologies.engineeingacademy.HelperFiles.EAHelper;
import com.pikchillytechnologies.engineeingacademy.HelperFiles.SessionHandler;
import com.pikchillytechnologies.engineeingacademy.Model.UserModel;
import com.pikchillytechnologies.engineeingacademy.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import instamojo.library.InstamojoPay;
import instamojo.library.InstapayListener;

public class MyCartActivity extends AppCompatActivity {

    private TextView m_TextView_Activity_Title;
    private TextView m_TextView_SubCategory_Title;
    private TextView m_TextView_Amount;
    private TextView m_TextView_Total_Exams;

    private Button m_Button_Pay;
    private Button m_Button_Back;

    private RequestQueue m_Queue;
    //StringRequest loadRequest;
    private ProgressDialog pd;
    private UserModel user;

    //Navigation Drawer
    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;
    private Button menuButton;

    private EAHelper m_Helper;

    private Bundle m_Course_Bundle;
    private String m_User_Id;
    private String m_Category_Id;
    private String m_Sub_Category_Id;
    private String m_Amount;
    private String userName;
    private String m_Category_Title;
    private String m_Sub_Category_Title;
    private String m_Title;
    private String m_Total_Exams;

    private String status;
    private String orderId;
    private String txnId;
    private String paymentId;
    private String token;

    private SessionHandler session;

    private static String userDataURL = "https://pikchilly.com/api/get_user_profile.php";
    private static String userSubscriptionURL = "https://pikchilly.com/api/save_user_subscription.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);

        m_Button_Pay = findViewById(R.id.button_Pay);
        m_TextView_Activity_Title = findViewById(R.id.textView_Activity_Title);
        m_Button_Back = findViewById(R.id.button_Back);
        m_TextView_SubCategory_Title = findViewById(R.id.textView_Sub_Category);
        m_TextView_Amount = findViewById(R.id.textView_Cost);
        m_TextView_Total_Exams = findViewById(R.id.textView_Total_Exams);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        menuButton = findViewById(R.id.button_Menu);

        m_Course_Bundle = getIntent().getExtras();
        pd = new ProgressDialog(MyCartActivity.this);
        m_Queue = Volley.newRequestQueue(MyCartActivity.this);
        m_Queue.getCache().clear();

        session = new SessionHandler(getApplicationContext());
        m_Helper = new EAHelper();
        user = null;

        m_User_Id = m_Course_Bundle.getString(getResources().getString(R.string.userid),"User Id");
        m_Category_Id = m_Course_Bundle.getString(getResources().getString(R.string.categoryid),getResources().getString(R.string.packages));
        m_Sub_Category_Id = m_Course_Bundle.getString(getResources().getString(R.string.subcategoryid),"sub_category_id");
        m_Amount = m_Course_Bundle.getString("cost","0");
        m_Category_Title = m_Course_Bundle.getString("category_title","0");
        m_Sub_Category_Title = m_Course_Bundle.getString("sub_category_title","0");
        m_Title = m_Course_Bundle.getString(getResources().getString(R.string.title),"Title");
        m_Total_Exams =  m_Course_Bundle.getString("total_exams","0");

        m_TextView_Activity_Title.setText("My Cart");
        m_Button_Back.setVisibility(View.VISIBLE);
        m_TextView_SubCategory_Title.setText(m_Sub_Category_Title);
        m_TextView_Amount.setText("Rs." + m_Amount);

        if (m_Helper.isNetworkAvailable(MyCartActivity.this)) {

            prepareUserData();

        } else {
            Toast.makeText(getApplicationContext(), "Please connect to Internet.", Toast.LENGTH_LONG).show();
        }

        m_Button_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent destinationDetailIntent = new Intent(MyCartActivity.this, SubCoursesActivity.class);
                destinationDetailIntent.putExtra(getResources().getString(R.string.userid), m_User_Id);
                destinationDetailIntent.putExtra("username", userName);
                destinationDetailIntent.putExtra("category_title", m_Category_Title);
                destinationDetailIntent.putExtra(getResources().getString(R.string.categoryid), m_Category_Id);
                destinationDetailIntent.putExtra(getResources().getString(R.string.title), m_Title);
                startActivity(destinationDetailIntent);

            }
        });

        m_Button_Pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(m_Helper.isNetworkAvailable(getApplicationContext())){

                    // Perform Action if network is available
                    userName = user.getmFirstName() + " " + user.getmLastName();
                    callInstamojoPay(user.getmEmailId(),user.getmPhoneNumber(),m_Amount,m_Sub_Category_Id,userName);

                }else{
                    Toast.makeText(getApplicationContext(),"Please connect to Internet.", Toast.LENGTH_LONG).show();
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

                if(menuItem.getTitle().equals("Courses")){

                    Intent destinationDetailIntent = new Intent(getApplicationContext(), CoursesActivity.class);
                    destinationDetailIntent.putExtra(getResources().getString(R.string.userid), m_User_Id);
                    destinationDetailIntent.putExtra("username", userName);
                    startActivity(destinationDetailIntent);

                }else if(menuItem.getTitle().equals("Articles")){
                    startActivity(new Intent(getApplicationContext(), ArticlesActivity.class));
                }else if(menuItem.getTitle().equals("My Downloads")){

                    Intent destinationDetailIntent = new Intent(getApplicationContext(), MyDownloadsActivity.class);
                    destinationDetailIntent.putExtra(getResources().getString(R.string.userid), m_User_Id);
                    destinationDetailIntent.putExtra("username", userName);
                    startActivity(destinationDetailIntent);

                }else if(menuItem.getTitle().equals("My Results")){

                    Intent destinationDetailIntent = new Intent(getApplicationContext(), MyResultsActivity.class);
                    destinationDetailIntent.putExtra(getResources().getString(R.string.userid), m_User_Id);
                    destinationDetailIntent.putExtra("username", userName);
                    startActivity(destinationDetailIntent);

                }else if(menuItem.getTitle().equals("Update Profile")){

                    Intent destinationDetailIntent = new Intent(getApplicationContext(), UpdateProfileActivity.class);
                    destinationDetailIntent.putExtra(getResources().getString(R.string.userid), m_User_Id);
                    destinationDetailIntent.putExtra("username", userName);
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

    private void callInstamojoPay(String email, String phone, String amount, String purpose, String buyername) {
        final Activity activity = this;
        InstamojoPay instamojoPay = new InstamojoPay();
        IntentFilter filter = new IntentFilter("ai.devsupport.instamojo");
        registerReceiver(instamojoPay, filter);
        JSONObject pay = new JSONObject();
        try {
            pay.put("email", email);
            pay.put("phone", phone);
            pay.put("purpose", purpose);
            pay.put("amount", amount);
            pay.put("name", buyername);
            pay.put("send_sms", true);
            pay.put("send_email", true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        initListener();
        instamojoPay.start(activity, pay, listener);
    }

    InstapayListener listener;

    private void initListener() {
        listener = new InstapayListener() {
            @Override
            public void onSuccess(String response) {

                String[] responseArray = response.split(":");

                status = responseArray[0].substring(responseArray[0].indexOf("=")+1);
                orderId = responseArray[1].substring(responseArray[1].indexOf("=")+1);
                txnId = responseArray[2].substring(responseArray[2].indexOf("=")+1);
                paymentId = responseArray[3].substring(responseArray[3].indexOf("=")+1);
                token = responseArray[4].substring(responseArray[4].indexOf("=")+1);

                Log.d("Response:", response);

                saveUserSubscription();
            }

            @Override
            public void onFailure(int code, String reason) {
                Toast.makeText(getApplicationContext(), "Failed: " + reason, Toast.LENGTH_LONG).show();
            }
        };
    }

    // Function to Prepare user data
    public void prepareUserData() {

        pd.setMessage("Loading . . .");
        pd.show();

        String response = null;
        m_Queue.getCache().clear();

        StringRequest loadRequest = new StringRequest(Request.Method.POST,userDataURL ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        pd.hide();

                        try {
                            JSONObject userJSON = new JSONObject(response);
                            JSONArray userArray = userJSON.getJSONArray("user_data");
                            JSONObject userObject = userArray.getJSONObject(0);

                            //creating a tutorial object and giving them the values from json object
                            user = new UserModel(userObject.getString("first_name"), userObject.getString("last_name"), userObject.getString("email")
                                    , userObject.getString("phone"), userObject.getString("password"), userObject.getString("address"),
                                    userObject.getString("city"), userObject.getString("state"), userObject.getString("photo"));

                        } catch (Exception e) {

                            Log.e("Error:", e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        pd.hide();
                        String err = (error.getMessage() == null) ? "Error is:" : error.getMessage();
                        Log.d("ErrorResponse", err);

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", m_User_Id);
                return params;
            }
        };

        loadRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        m_Queue.add(loadRequest);
    }

    // Function to Prepate user data
    public void saveUserSubscription() {

        pd.setMessage("Saving . . .");
        pd.show();

        String response = null;
        m_Queue.getCache().clear();

        StringRequest loadRequest = new StringRequest(Request.Method.POST, userSubscriptionURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pd.hide();

                        Intent destinationDetailIntent = new Intent(MyCartActivity.this, SubCoursesActivity.class);
                        destinationDetailIntent.putExtra(getResources().getString(R.string.userid), m_User_Id);
                        destinationDetailIntent.putExtra("username", userName);
                        destinationDetailIntent.putExtra("category_title", m_Category_Title);
                        destinationDetailIntent.putExtra(getResources().getString(R.string.categoryid), m_Category_Id);
                        destinationDetailIntent.putExtra(getResources().getString(R.string.title), m_Title);
                        startActivity(destinationDetailIntent);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        pd.hide();
                        String err = (error.getMessage() == null) ? "Error is:" : error.getMessage();
                        Log.d("ErrorResponse", err);

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("userid", m_User_Id);
                params.put("categoryid", m_Category_Id);
                params.put("subcategoryid", m_Sub_Category_Id);
                params.put("amount", m_Amount);
                Date todayDate = new Date();
                params.put("dateofpayment", todayDate.toString());
                params.put("paymentid", paymentId);
                params.put("orderid", orderId);
                params.put("txnid", txnId);
                params.put("token", token);

                return params;
            }
        };

        loadRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        m_Queue.add(loadRequest);
    }


}
