package com.pikchillytechnologies.engineeingacademy.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.MainThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.pikchillytechnologies.engineeingacademy.R;

import java.util.HashMap;
import java.util.Map;


public class SignInActivity extends AppCompatActivity {

    private EditText m_Email_Id_TextView;
    private EditText m_Password_TextView;

    private Button m_Sign_In_Button;
    private TextView m_Sign_Up_TextView;
    private EAHelper m_Helper;

    private ProgressDialog pd;

    private static String loginURL ="https://pikchilly.com/api/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        m_Helper = new EAHelper();

        pd = new ProgressDialog(SignInActivity.this);

        m_Email_Id_TextView = findViewById(R.id.edittext_Email_Id);
        m_Password_TextView = findViewById(R.id.edittext_Password);

        m_Sign_Up_TextView = findViewById(R.id.textview_New_Sign_Up);
        m_Sign_In_Button = findViewById(R.id.button_Sign_In);

        m_Sign_In_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //m_Helper.start_Activity(SignInActivity.this, CoursesActivity.class);

                signInRequest();

            }
        });

        m_Sign_Up_TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_Helper.start_Activity(SignInActivity.this, SignUpActivity.class);
            }
        });

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

                        if(response.equals("Sign_In_Success")) {

                            m_Helper.start_Activity(SignInActivity.this, CoursesActivity.class);

                        }else if(response.equals("Sign_In_Failed")){
                            Toast.makeText(getApplicationContext(),"User Name/Password does not match. Try Again.",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getApplicationContext(),"Error Occured. Please Try Again.",Toast.LENGTH_LONG).show();
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
