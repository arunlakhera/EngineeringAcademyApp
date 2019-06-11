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
import com.pikchillytechnologies.engineeingacademy.HelperFiles.SessionHandler;
import com.pikchillytechnologies.engineeingacademy.R;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    //private static String signupURL ="https://pikchilly.com/api/signup.php";
    private static String signupURL = "http://onlineengineeringacademy.co.in/api/user_profile";
    private EditText m_First_Name_EditText;
    private EditText m_Last_Name_EditText;
    private EditText m_Phone_EditText;
    private EditText m_Email_Id_EditText;
    private EditText m_Password_EditText;
    private String m_User_Id;
    private Button m_Sign_Up_Button;
    private TextView m_Sign_In_TextView;
    private ProgressDialog pd;
    private EAHelper m_Helper;
    private SessionHandler session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        session = new SessionHandler(getApplicationContext());

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

                if (m_Helper.isNetworkAvailable(getApplicationContext())) {

                    String userFirstName = m_First_Name_EditText.getText().toString();
                    String userLastName = m_Last_Name_EditText.getText().toString();
                    String userPhone = m_Phone_EditText.getText().toString();
                    String userEmailId = m_Email_Id_EditText.getText().toString();
                    String userPassword = m_Password_EditText.getText().toString();

                    if (userFirstName.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Please provide First Name", Toast.LENGTH_SHORT).show();
                        m_First_Name_EditText.setFocusable(true);
                    } else if (userLastName.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Please provide Last Name", Toast.LENGTH_SHORT).show();
                        m_Last_Name_EditText.setFocusable(true);
                    } else if (userPhone.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Please provide Phone Number", Toast.LENGTH_SHORT).show();
                        m_Phone_EditText.setFocusable(true);
                    } else if (userEmailId.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Please provide Email Id", Toast.LENGTH_SHORT).show();
                        m_Email_Id_EditText.setFocusable(true);
                    } else if (userPassword.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Please provide Password", Toast.LENGTH_SHORT).show();
                        m_Password_EditText.setFocusable(true);
                    } else {

                        if (m_Helper.isValidEmail(userEmailId)) {
                            m_User_Id = m_Email_Id_EditText.getText().toString();
                            signupRequest();
                        } else {
                            Toast.makeText(getApplicationContext(), "Please provide valid Email Id !", Toast.LENGTH_SHORT).show();
                        }
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Please connect to Internet.", Toast.LENGTH_LONG).show();
                }

            }
        });

        m_Sign_In_TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_Helper.start_Activity(SignUpActivity.this, SignInActivity.class);
            }
        });

    }

    public void signupRequest() {

        pd.setMessage("Signing Up . . .");
        pd.show();

        RequestQueue queue = Volley.newRequestQueue(SignUpActivity.this);
        String response = null;

        StringRequest postRequest = new StringRequest(Request.Method.POST, signupURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        pd.hide();
//                        Toast.makeText(getApplicationContext(),"Response" + response,Toast.LENGTH_LONG).show();
                        response = response.trim();
                        if (response.equals("SignUpSuccess")) {

                            String firstName = m_First_Name_EditText.getText().toString();
                            String lastName = m_Last_Name_EditText.getText().toString();
                            String userName = firstName + " " + lastName;

                            session.loginUser(m_User_Id);

                            Intent destinationDetailIntent = new Intent(SignUpActivity.this, CoursesActivity.class);
                            destinationDetailIntent.putExtra(getResources().getString(R.string.userid), m_User_Id);
                            destinationDetailIntent.putExtra("username", userName);
                            startActivity(destinationDetailIntent);

                        } else if (response.equals("SignUpFailed")) {

                            Toast.makeText(getApplicationContext(), "Could not Sign Up", Toast.LENGTH_LONG).show();
                        } else if (response.equals("UsernameExists")) {
                            Toast.makeText(getApplicationContext(), "User with this email already exists!", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Response:" + response, Toast.LENGTH_LONG).show();
                        }

                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("ErrorResponse", error.getMessage());

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("firstname", m_First_Name_EditText.getText().toString());
                params.put("lastname", m_Last_Name_EditText.getText().toString());
                params.put("phone", m_Phone_EditText.getText().toString());
                params.put("username", m_Email_Id_EditText.getText().toString());
                params.put("password", m_Password_EditText.getText().toString());

                return params;

            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(postRequest);

    }

}
