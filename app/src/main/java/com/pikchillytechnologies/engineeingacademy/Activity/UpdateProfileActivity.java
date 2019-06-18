package com.pikchillytechnologies.engineeingacademy.Activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import com.pikchillytechnologies.engineeingacademy.HelperFiles.EAHelper;
import com.pikchillytechnologies.engineeingacademy.HelperFiles.SessionHandler;
import com.pikchillytechnologies.engineeingacademy.Model.UserModel;
import com.pikchillytechnologies.engineeingacademy.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateProfileActivity extends AppCompatActivity {

    private static String userDataURL = "http://onlineengineeringacademy.co.in/api/get_update_user_profile";
    private static String updateUserDataURL = "http://onlineengineeringacademy.co.in/api/user_profile_update_2";

    private final int PICK_IMAGE_REQUEST = 71;
    RequestQueue m_Queue;
    StringRequest loadRequest;
    StringRequest uploadImageRequest;
    StringRequest updateUserRequest;
    private TextView m_TextView_Activity_Title;
    private Button m_Button_Back;
    private Bundle m_User_Bundle;
    private String m_User_Id;
    private String m_User_Name;
    private EditText mEditText_FirstName;
    private EditText mEditText_LastName;
    private EditText mEditText_PhoneNumber;
    private EditText mEditText_EmailId;
    private EditText mEditText_Password;
    private EditText mEditText_Address;
    private EditText mEditText_City;
    private EditText mEditText_State;
    private ImageView mImageView_UserProfilePhoto;
    private TextView mTextView_ChangePhoto;
    private Button mButton_Update;
    private String userFirstName;
    private String userLastName;
    private String userPhoneNumber;
    private String userEmailId;
    private String userPassword;
    private String userAddress;
    private String userCity;
    private String userState;
    private String userProfilePhoto;
    private boolean userPhotoChangeFlag;
    private UserModel user;
    private List<UserModel> m_User_List;
    private EAHelper m_Helper;
    //Navigation Drawer
    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;
    private Button menuButton;
    private ProgressDialog pd;
    private Bitmap bitmap;
    private String mUserUpdatedImage;
    private Uri filePath;
    private SessionHandler session;
    boolean mAlert_Action;
    private final int REQUEST_IMAGE_CAPTURE = 1;
    private int REQUEST_CAMERA_PERMISSIONS = 12;
    private boolean photoUpdateFlag;
    private boolean cameraPermissionFlag;
    private String appLink = "https://play.google.com/store/apps/details?id=com.pikchillytechnologies.engineeingacademy";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        // Function to initialize values
        initValues();

        //Function to Set Values
        setValues();

        // Function to cehck if internet is available
        if (m_Helper.isNetworkAvailable(UpdateProfileActivity.this)) {

            prepareUserData();

        } else {
            Toast.makeText(getApplicationContext(), "Please connect to Internet.", Toast.LENGTH_LONG).show();
        }

        mTextView_ChangePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (m_Helper.isNetworkAvailable(getApplicationContext())) {
                    checkPermissions();

                } else {
                    Toast.makeText(getApplicationContext(), "Please connect to Internet.", Toast.LENGTH_LONG).show();
                }
            }
        });

        mButton_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (m_Helper.isNetworkAvailable(getApplicationContext())) {

                        showAlertDialog();

                } else {
                    Toast.makeText(getApplicationContext(), "Please connect to Internet.", Toast.LENGTH_LONG).show();
                }
            }
        });

        m_Button_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent destinationDetailIntent = new Intent(UpdateProfileActivity.this, CoursesActivity.class);
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

                    mDrawerLayout.closeDrawers();
                } else if(menuItem.getTitle().equals(getResources().getString(R.string.share_app))){

                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_SUBJECT, "Engineering Academy Dehradun App");
                    String message = "\nLet me recommend you this application: \n" + appLink;

                    i.putExtra(Intent.EXTRA_TEXT, message);
                    startActivity(Intent.createChooser(i, "Select one.."));


                } else if (menuItem.getTitle().equals(getResources().getString(R.string.logout))) {

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


    public void checkPermissions(){

        if(ContextCompat.checkSelfPermission(UpdateProfileActivity.this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(UpdateProfileActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSIONS);
            //showAlert();
        }else{
            updateUserPhoto();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == REQUEST_CAMERA_PERMISSIONS){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"Granted", Toast.LENGTH_LONG).show();
                updateUserPhoto();
            }else{
                Toast.makeText(this,"NotGranted", Toast.LENGTH_LONG).show();
                showAlert();

            }
        }
    }

    private void showAlert() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("Engineering Academy needs to access the Camera to take photo.");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "DONT ALLOW",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        ActivityCompat.requestPermissions(UpdateProfileActivity.this,
                                new String[]{Manifest.permission.CAMERA},
                                REQUEST_CAMERA_PERMISSIONS);

                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "ALLOW",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        checkPermissions();
                    }
                });
        alertDialog.show();
    }

    /**
     * Function to initialize values
     * */
    public void initValues(){

        mDrawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        menuButton = findViewById(R.id.button_Menu);
        m_TextView_Activity_Title = findViewById(R.id.textView_Activity_Title);
        m_Button_Back = findViewById(R.id.button_Back);
        mEditText_FirstName = findViewById(R.id.edittext_First_Name);
        mEditText_LastName = findViewById(R.id.edittext_Last_Name);
        mEditText_PhoneNumber = findViewById(R.id.edittext_Phone);
        mEditText_EmailId = findViewById(R.id.edittext_Email_Id);
        mEditText_Password = findViewById(R.id.edittext_Password);
        mEditText_Address = findViewById(R.id.edittext_Address);
        mEditText_City = findViewById(R.id.edittext_City);
        mEditText_State = findViewById(R.id.edittext_State);
        mImageView_UserProfilePhoto = findViewById(R.id.imageView_UserProfilePhoto);
        mTextView_ChangePhoto = findViewById(R.id.textView_UploadPhoto);
        mButton_Update = findViewById(R.id.button_Update);
        m_Queue = Volley.newRequestQueue(UpdateProfileActivity.this);
        m_Queue.getCache().clear();
        mAlert_Action = false;
        session = new SessionHandler(getApplicationContext());
        m_Helper = new EAHelper();
        userPhotoChangeFlag = false;
        pd = new ProgressDialog(UpdateProfileActivity.this);
        photoUpdateFlag = false;
        cameraPermissionFlag = false;
    }

    /**
     * Function to Set values
     * */
    public void setValues(){
        m_TextView_Activity_Title.setText(getResources().getString(R.string.my_profile));
        m_User_Bundle = getIntent().getExtras();
        m_User_Id = m_User_Bundle.getString(getResources().getString(R.string.userid), "User Id");
        m_User_Name = m_User_Bundle.getString("username", "User Name");

    }

    /**
     * Function to show Alert Dialog
     * */
    public void showAlertDialog(){

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("Save Changes");
        alertBuilder.setMessage("Do you want to save the Changes ?");
        alertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                updateUserData();
            }
        });

        alertBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                    Log.d("Save Changes:", "No");
            }
        });

        alertBuilder.create();
        alertBuilder.show();

    }

    /**
     * Function to prepare User Data
     * */
    public void prepareUserData() {

        pd.setMessage("Loading . . .");
        pd.show();

        String response = null;

        loadRequest = new StringRequest(Request.Method.POST, userDataURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        pd.hide();
                        Log.d("UPDATEFLAG---",String.valueOf(photoUpdateFlag));
                        Log.d("UPDATEIMAGE---",String.valueOf(mUserUpdatedImage));

                        Log.d("RESPONSE:::>>>>",response);
                        //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                        try {
                            JSONObject userJSON = new JSONObject(response);
                            JSONArray userArray = userJSON.getJSONArray("user_data");
                            JSONObject userObject = userArray.getJSONObject(0);

                            //creating a tutorial object and giving them the values from json object
                            user = new UserModel(userObject.getString("first_name"), userObject.getString("last_name"), userObject.getString("email")
                                    , userObject.getString("phone"), userObject.getString("password"), userObject.getString("address"),
                                    userObject.getString("city"), userObject.getString("state"), userObject.getString("photo"));

                            loadUserData();

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
                Map<String, String> params = new HashMap<>();
                params.put("username", m_User_Id);
                return params;
            }
        };

        loadRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        m_Queue.add(loadRequest);
    }

    /**
     * Function to Load user
     * */
    public void loadUserData() {

        Log.e("USERREPONSE--->",user.getmUserPhotoURL());

        mEditText_FirstName.setText(user.getmFirstName().toUpperCase());
        mEditText_LastName.setText(user.getmLastName().toUpperCase());
        mEditText_EmailId.setText(user.getmEmailId());
        mEditText_PhoneNumber.setText(user.getmPhoneNumber());
        mEditText_Password.setText(user.getmPassword());
        mEditText_Address.setText(user.getmAddress());
        mEditText_City.setText(user.getmCity());
        mEditText_State.setText(user.getmState());

        //setUserImage();
        RequestOptions requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true);

        try {
            Glide.with(this)
                    .load(user.getmUserPhotoURL())
                    .placeholder(R.drawable.ea_logo_icon)
                    .apply(requestOptions)
                    .error(R.drawable.back_icon)
                    .into(mImageView_UserProfilePhoto);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Could not Load image.." + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Function to show Camera to user
     */
    public void updateUserPhoto() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    /**
     * Function to Set photo taken by user from camera
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            try {

                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    mImageView_UserProfilePhoto.setImageBitmap(imageBitmap);

                    Bitmap lastBitmap;
                    lastBitmap = imageBitmap;

                    //encoding image to string
                    mUserUpdatedImage = getStringImage(lastBitmap);
                    photoUpdateFlag = true;



            } catch (Exception e) {
                Log.e("Camera Error:", e.getMessage());
                photoUpdateFlag = false;
            }
        }
    }

    /**
     * Function to Encode Image to String
     */
    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedUserImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedUserImage;
    }

    /**
     * Function to Decode Image From String
     */
    public void setUserImage() {

        if (mUserUpdatedImage != null && !mUserUpdatedImage.isEmpty() && mUserUpdatedImage.length() > 1) {

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] imageBytes = baos.toByteArray();
            imageBytes = Base64.decode(mUserUpdatedImage, Base64.DEFAULT);
            Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            mImageView_UserProfilePhoto.setImageBitmap(decodedImage);
        }
    }

    /**
     * Function to update user data
     * */
    public void updateUserData() {

        pd.setMessage("Saving Data . . .");
        pd.show();

        String response = null;

        updateUserRequest = new StringRequest(Request.Method.POST, updateUserDataURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        pd.hide();
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                        try {

                            JSONObject userJSON = new JSONObject(response);

                            if (userJSON.getString("user_data").equals("Successful")) {
                                Toast.makeText(getApplicationContext(), "Your update has been saved successfully.", Toast.LENGTH_LONG).show();
                                prepareUserData();
                            } else {
                                Toast.makeText(getApplicationContext(), "Your update could not be stored. Please try Again!!.", Toast.LENGTH_LONG).show();
                            }

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
                Map<String, String> params = new HashMap<>();
                params.put("username", m_User_Id);

                params.put("first_name", mEditText_FirstName.getText().toString());
                params.put("last_name", mEditText_LastName.getText().toString());
                params.put("email", mEditText_EmailId.getText().toString());
                params.put("phone", mEditText_PhoneNumber.getText().toString());
                params.put("password", mEditText_Password.getText().toString());
                params.put("address", mEditText_Address.getText().toString());
                params.put("city", mEditText_City.getText().toString());
                params.put("state", mEditText_State.getText().toString());

                if(photoUpdateFlag){
                    String photoName = m_User_Id + ".jpg";
                    params.put("photoname", photoName);
                    params.put("userphotostring", mUserUpdatedImage);
                    params.put("photo", mEditText_FirstName.getText().toString());
                }else{
                    params.put("photoname", "NA");
                    params.put("userphotostring", "NA");
                    params.put("photo", mEditText_FirstName.getText().toString());
                }

                return params;
            }
        };

        updateUserRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        m_Queue.add(updateUserRequest);

    }

}