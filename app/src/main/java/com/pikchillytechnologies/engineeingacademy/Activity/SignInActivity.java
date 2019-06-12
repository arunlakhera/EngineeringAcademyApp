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
import android.os.Build;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;
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
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.Login;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.pikchillytechnologies.engineeingacademy.HelperFiles.EAHelper;
import com.pikchillytechnologies.engineeingacademy.HelperFiles.SessionHandler;
import com.pikchillytechnologies.engineeingacademy.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

public class SignInActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 1;
    //private static String loginURL ="http://onlineengineeringacademy.co.in/api/login_request";
    private static String loginURL = "http://onlineengineeringacademy.co.in/api/login_request";
    private static String forgotPasswordURL = "http://onlineengineeringacademy.co.in/api/forget_password";
    private static String signupURL = "http://onlineengineeringacademy.co.in/api/user_profile";
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
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleApiClient googleApiClient;
    private String google_FirstName;
    private String google_LastName;
    private String google_Phone;
    private String google_EmailId;
    private String google_Password;

    private SignInButton googleSignInButton;
    private LoginButton facebookSignInButton;
    private Button facebookCustomButton;
    private Button googleCustomButton;
    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;

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
        m_Sign_In_Button.setSelected(true);

        m_Helper = new EAHelper();
        pd = new ProgressDialog(SignInActivity.this);

        // Google Sign In Option
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        // Set the dimensions of the sign-in button.
        googleSignInButton = findViewById(R.id.google_sign_in_button);
        facebookSignInButton = findViewById(R.id.facebook_sign_in_button);

        googleCustomButton = findViewById(R.id.google_custom_button);
        facebookCustomButton = findViewById(R.id.facebook_custom_button);

        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, RC_SIGN_IN);
            }
        });

        // Defining the AccessTokenTracker
        accessTokenTracker = new AccessTokenTracker() {
            // This method is invoked everytime access token changes
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {

                // currentAccessToken is null if the user is logged out
                if (currentAccessToken != null) {
                    // AccessToken is not null implies user is logged in and hence we sen the GraphRequest
                    useLoginInformation(currentAccessToken);
                    useLoginInformation(currentAccessToken);
                    signupRequest();
                }
            }
        };

        // Creating CallbackManager
        callbackManager = CallbackManager.Factory.create();

        m_Sign_In_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (m_Helper.isNetworkAvailable(getApplicationContext())) {

                    String userEmailId = m_Email_Id_TextView.getText().toString();
                    String userPassword = m_Password_TextView.getText().toString();

                    if (!userEmailId.isEmpty() && !userPassword.isEmpty()) {

                        if (m_Helper.isValidEmail(userEmailId)) {
                            m_User_Id = m_Email_Id_TextView.getText().toString();
                            signInRequest();
                        } else {
                            m_Email_Id_TextView.setError("Please provide valid Email ID..");
                        }

                    } else {

                        m_Email_Id_TextView.setError("Please provide your registered Email ID..");
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Please connect to Internet.", Toast.LENGTH_LONG).show();
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

                callForgotPassword(SignInActivity.this);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        //This starts the access token tracking
        accessTokenTracker.startTracking();

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null) {
            useLoginInformation(accessToken);
            signupRequest();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // We stop the tracking before destroying the activity
        accessTokenTracker.stopTracking();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        callbackManager.onActivityResult(requestCode, resultCode, data);

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }

    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {

            google_FirstName = result.getSignInAccount().getGivenName();
            google_LastName = result.getSignInAccount().getFamilyName();
            google_Phone = "";
            google_EmailId = result.getSignInAccount().getEmail();
            google_Password = "";
            signupRequest();

        } else {
            Toast.makeText(getApplicationContext(), "Google Sign in cancelled", Toast.LENGTH_LONG).show();
        }
    }

    public void signupRequest() {

        pd.setMessage("Signing In . . .");
        pd.show();

        RequestQueue queue = Volley.newRequestQueue(SignInActivity.this);
        String response = null;

        StringRequest postRequest = new StringRequest(Request.Method.POST, signupURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        pd.hide();

                        response = response.trim();
                        if (response.equals("SignUpSuccess") || response.equals("UsernameExists")) {

                            String firstName = google_FirstName;
                            String lastName = google_LastName;
                            String userName = firstName + " " + lastName;
                            m_User_Id = google_EmailId;

                            session.loginUser(m_User_Id);

                            Intent destinationDetailIntent = new Intent(SignInActivity.this, CoursesActivity.class);
                            destinationDetailIntent.putExtra(getResources().getString(R.string.userid), m_User_Id);
                            destinationDetailIntent.putExtra("username", userName);
                            startActivity(destinationDetailIntent);

                        } else if (response.equals("SignUpFailed")) {

                            Toast.makeText(getApplicationContext(), "Sign In Failed. Please try again or contact contact us if problem persists.", Toast.LENGTH_LONG).show();
                        } else {

                            Toast.makeText(getApplicationContext(), "Error Occured while Sign In. Please try again or contact us if problem persists.", Toast.LENGTH_LONG).show();
                            Log.e("Error:",response);
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

                params.put("firstname", google_FirstName);
                params.put("lastname", google_LastName);
                params.put("phone", google_Phone);
                params.put("username", google_EmailId);
                params.put("password", google_Password);

                return params;

            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(postRequest);

    }

    // FB User Login Information
    private void useLoginInformation(AccessToken accessToken) {
        /**
         Creating the GraphRequest to fetch user details
         1st Param - AccessToken
         2nd Param - Callback (which will be invoked once the request is successful)
         **/
        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            //OnCompleted is invoked once the GraphRequest is successful
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {

                    Log.d("FACEBOOK---->>>>>", object.toString());
                    google_FirstName = object.getString("first_name");
                    google_LastName = object.getString("last_name");
                    google_Phone = "";
                    google_EmailId = object.getString("email");
                    google_Password = "";

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        // We set parameters to the GraphRequest using a Bundle.
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,first_name,last_name,email");
        request.setParameters(parameters);
        // Initiate the GraphRequest
        request.executeAsync();
    }


    public void callForgotPassword(Activity activity) {

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

                if (!registeredEmail.isEmpty()) {

                    if (m_Helper.isValidEmail(registeredEmail)) {

                        setPasswordRequest();
                        dialog.dismiss();

                    } else {
                        forgotPasswordEditText.setError("Please provide valid Email ID..");
                        send_Button.setBackgroundColor(getResources().getColor(R.color.colorDarkRed));
                    }

                } else {
                    forgotPasswordEditText.setError("Please provide your registered Email ID..");
                    send_Button.setBackgroundColor(getResources().getColor(R.color.colorDarkRed));
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
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        pd.hide();
                        response = response.trim();

                        if (response.equals("EMAIL_NOT_EXIST")) {

                            Toast.makeText(getApplicationContext(), "No user exists with this Email Id.", Toast.LENGTH_LONG).show();
                        } else if (response.equals("EMAIL_SENT")) {

                            Toast.makeText(getApplicationContext(), "An Email has been sent to the registered Email ID to reset Password", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Could not sent Password reset email. Please try again!", Toast.LENGTH_LONG).show();
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
                params.put("username", forgotPasswordEditText.getText().toString());
                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        m_Queue.add(postRequest);
    }

    public void signInRequest() {

        pd.setMessage("Signing In . . .");
        pd.show();

        RequestQueue m_Queue = Volley.newRequestQueue(SignInActivity.this);
        String response = null;

        StringRequest postRequest = new StringRequest(Request.Method.POST, loginURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        pd.hide();
                        response = response.trim();
                        if (response.equals("SignInFailed")) {

                            Toast.makeText(getApplicationContext(), "User Name/Password does not match. Try Again.", Toast.LENGTH_LONG).show();
                        } else {

                            try {
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

                            } catch (Exception e) {

                                Log.e("Error:", e.getMessage());
                            }
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
                params.put("username", m_Email_Id_TextView.getText().toString());
                params.put("password", m_Password_TextView.getText().toString());
                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        m_Queue.add(postRequest);

    }

    public void onClick_FB(View view) {
        if(view == facebookCustomButton){
            facebookSignInButton.performClick();
        }
    }

    public void onClick_Google(View view) {

        if(view == googleCustomButton){
            Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
            startActivityForResult(intent, RC_SIGN_IN);
        }
    }
}
