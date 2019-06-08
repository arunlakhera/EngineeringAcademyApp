package com.pikchillytechnologies.engineeingacademy.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.MainThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
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
import com.pikchillytechnologies.engineeingacademy.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class SignInActivity extends AppCompatActivity {

    private EditText m_Email_Id_TextView;
    private EditText m_Password_TextView;
    private String m_User_Id;

    private Button m_Sign_In_Button;
    private TextView m_Sign_Up_TextView;
    private EAHelper m_Helper;
    private ProgressDialog pd;

    private SessionHandler session;
    private TextView m_Forgot_Password_TextView;
    private EditText forgotPasswordEditText;
    private Button cancel_Button;
    private Button send_Button;

    //private static String loginURL ="http://onlineengineeringacademy.co.in/api/login_request";
    private static String loginURL = "http://onlineengineeringacademy.co.in/api/login_request";
    private static String forgotPasswordURL = "http://onlineengineeringacademy.co.in/api/forgot_password_request";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        session = new SessionHandler(getApplicationContext());

        m_Email_Id_TextView = findViewById(R.id.edittext_Email_Id);
        m_Password_TextView = findViewById(R.id.edittext_Password);
        m_Sign_Up_TextView = findViewById(R.id.textview_New_Sign_Up);
        m_Sign_In_Button = findViewById(R.id.button_Sign_In);
        m_Forgot_Password_TextView = findViewById(R.id.textview_ForgotPassword);

        m_Helper = new EAHelper();
        pd = new ProgressDialog(SignInActivity.this);

        m_Sign_In_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(m_Helper.isNetworkAvailable(getApplicationContext())){

                    String userEmailId = m_Email_Id_TextView.getText().toString();
                    String userPassword = m_Password_TextView.getText().toString();

                    if(!userEmailId.isEmpty() && !userPassword.isEmpty()){

                        if(m_Helper.isValidEmail(userEmailId)){
                            m_User_Id = m_Email_Id_TextView.getText().toString();
                            signInRequest();
                        }else{
                            Toast.makeText(getApplicationContext(),"Please provide valid Email Id !", Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        Toast.makeText(getApplicationContext(),"Please provide Email Id & Password !", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(getApplicationContext(),"Please connect to Internet.", Toast.LENGTH_LONG).show();
                }

            }
        });

        m_Sign_Up_TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_Helper.start_Activity(SignInActivity.this, SignUpActivity.class);
            }
        });

        m_Forgot_Password_TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Please Enter registered email id..", Toast.LENGTH_SHORT).show();
                callForgotPassword(SignInActivity.this);
            }
        });

    }

    public void callForgotPassword(Activity activity){

        // Show popup to take users registered email id for which user forgot password

        final Dialog dialog = new Dialog(activity);
        //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.forgot_password_dialog_box);

        forgotPasswordEditText = dialog.findViewById(R.id.registeredEmailId);
        cancel_Button = dialog.findViewById(R.id.button_Cancel);
        send_Button = dialog.findViewById(R.id.button_Send);

        cancel_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        send_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                send_Button.setBackgroundColor(getResources().getColor(R.color.colorDarkGreen));
                String registeredEmail = forgotPasswordEditText.getText().toString();

                if(!registeredEmail.isEmpty()){

                    if(m_Helper.isValidEmail(registeredEmail)){

                        // Send the provided email id to WEB API to check if this email exists in system
                        // if email does not exists receive msg "Email_Not_Exists" and show message to user
                        // if email exists Web API will send email to the user with a link to reset the password/ or send temp password and send msg "Email_Sent" to app
                        // Show msg to the user that email to reset has been sent to the user.

                        setPasswordRequest();
                        Toast.makeText(getApplicationContext(),"----An Email has been sent to reset Password.", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                    }else{
                        Toast.makeText(getApplicationContext(),"Please provide valid Email Id !", Toast.LENGTH_SHORT).show();
                    }


                }else{
                    Toast.makeText(getApplicationContext(),"Please provide registered Email Id !", Toast.LENGTH_SHORT).show();
                }

            }
        });

        dialog.show();

    }

    private void setPasswordRequest() {

        pd.setMessage("Processing request . . .");
        pd.show();

        RequestQueue m_Queue = Volley.newRequestQueue(SignInActivity.this);
        String response = null;

        StringRequest postRequest = new StringRequest(Request.Method.POST, forgotPasswordURL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        pd.hide();
                        response = response.trim();

                        if(response.equals("EMAIL_NOT_EXIST")){

                            Toast.makeText(getApplicationContext(),"No user exists with this Email Id.",Toast.LENGTH_LONG).show();
                        }else if(response.equals("EMAIL_SENT")){

                            Toast.makeText(getApplicationContext(),"An Email has been sent to reset Password",Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(getApplicationContext(),"Could not sent Password reset email. Please try again!",Toast.LENGTH_LONG).show();
                        }

                    }

                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        pd.hide();
                        String err = (error.getMessage()==null)?"Error is:":error.getMessage();
                        Log.d("ErrorResponse", err);

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("username", forgotPasswordEditText.getText().toString());
                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        m_Queue.add(postRequest);
    }

    public void signInRequest(){

        pd.setMessage("Signing In . . .");
        pd.show();

        RequestQueue m_Queue = Volley.newRequestQueue(SignInActivity.this);
        String response = null;

        StringRequest postRequest = new StringRequest(Request.Method.POST, loginURL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        pd.hide();
                        response = response.trim();
                            if(response.equals("SignInFailed")){

                                Toast.makeText(getApplicationContext(),"User Name/Password does not match. Try Again.",Toast.LENGTH_LONG).show();
                            }else{

                                try{
                                JSONObject userJSON = new JSONObject(response);

                                JSONArray userArray = userJSON.getJSONArray("user_data");
                                JSONObject userObject = userArray.getJSONObject(0);

                                String userFirstName = userObject.getString("first_name");
                                String userLastName = userObject.getString("last_name");
                                String userName = userFirstName + " " + userLastName;

                                session.loginUser(m_User_Id);

                                Intent destinationDetailIntent = new Intent(SignInActivity.this, CoursesActivity.class);
                                destinationDetailIntent.putExtra(getResources().getString(R.string.userid), m_User_Id);
                                destinationDetailIntent.putExtra("username", userName);
                                startActivity(destinationDetailIntent);

                                }catch (Exception e){

                                    Log.e("Error:", e.getMessage());
                                }
                            }

                    }

                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        pd.hide();
                        String err = (error.getMessage()==null)?"Error is:":error.getMessage();
                        Log.d("ErrorResponse", err);

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("username", m_Email_Id_TextView.getText().toString());
                params.put("password", m_Password_TextView.getText().toString());
                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        m_Queue.add(postRequest);

    }
    
}
