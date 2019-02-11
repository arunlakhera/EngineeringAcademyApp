package com.pikchillytechnologies.engineeingacademy.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
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

public class SignUpActivity extends AppCompatActivity {

    private EditText m_First_Name_EditText;
    private EditText m_Last_Name_EditText;
    private EditText m_Phone_EditText;
    private EditText m_Email_Id_EditText;
    private EditText m_Password_EditText;

    private Button m_Sign_Up_Button;
    private TextView m_Sign_In_TextView;
    private ProgressDialog pd;
    private EAHelper m_Helper;

    private static String signupURL ="https://pikchilly.com/api/signup.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        m_Helper = new EAHelper();
        pd = new ProgressDialog(SignUpActivity.this);

        m_First_Name_EditText = findViewById(R.id.edittext_First_Name);
        m_Last_Name_EditText = findViewById(R.id.edittext_Last_Name);
        m_Phone_EditText = findViewById(R.id.edittext_Phone);
        m_Email_Id_EditText = findViewById(R.id.edittext_Email_Id);
        m_Password_EditText = findViewById(R.id.edittext_Password);

        m_Sign_Up_Button = findViewById(R.id.button_Sign_Up);
        m_Sign_In_TextView = findViewById(R.id.textview_Sign_In);

        m_Sign_Up_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signupRequest();
            }
        });

        m_Sign_In_TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_Helper.start_Activity(SignUpActivity.this, SignInActivity.class);
            }
        });

    }

    public void signupRequest(){

        pd.setMessage("Signing Up . . .");
        pd.show();

        RequestQueue queue = Volley.newRequestQueue(SignUpActivity.this);
        String response = null;

        StringRequest postRequest = new StringRequest(Request.Method.POST, signupURL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        pd.hide();

                        if(response.equals("SignUp_Success")) {

                            m_Helper.start_Activity(getApplicationContext(), CoursesActivity.class);

                        }else if(response.equals("SignUp_Failed")){

                            Toast.makeText(getApplicationContext(),"Could not Sign Up",Toast.LENGTH_LONG).show();
                        }else if(response.equals("UsernameExists")){
                            Toast.makeText(getApplicationContext(),"User with this email already exists!",Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("ErrorResponse", error.getMessage());

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();

                params.put("firstname", m_First_Name_EditText.getText().toString());
                params.put("lastname", m_Last_Name_EditText.getText().toString());
                params.put("phonenumber", m_Phone_EditText.getText().toString());
                params.put("username", m_Email_Id_EditText.getText().toString());
                params.put("password", m_Password_EditText.getText().toString());

                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(postRequest);

    }


}
