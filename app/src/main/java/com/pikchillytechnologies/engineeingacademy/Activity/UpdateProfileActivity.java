package com.pikchillytechnologies.engineeingacademy.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.pikchillytechnologies.engineeingacademy.Model.CoursesModel;
import com.pikchillytechnologies.engineeingacademy.Model.UserModel;
import com.pikchillytechnologies.engineeingacademy.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class UpdateProfileActivity extends AppCompatActivity {

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

    //Navigation Drawer
    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;
    private Button menuButton;

    private ProgressDialog pd;
    private Bitmap bitmap;
    private String mUserUpdatedImage;

    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;

    private static String userDataURL = "https://pikchilly.com/api/get_user_profile.php";
    private static String updateUserDataURL = "https://pikchilly.com/api/update_user_profile.php";
    private String updatePhotoUrl = "https://pikchilly.com/api/update_user_photo.php";

    RequestQueue m_Queue;
    StringRequest loadRequest;
    StringRequest uploadImageRequest;
    StringRequest updateUserRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

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

        userPhotoChangeFlag = false;

        pd = new ProgressDialog(UpdateProfileActivity.this);
        m_TextView_Activity_Title.setText("My Profile");
        m_User_Bundle = getIntent().getExtras();
        m_User_Id = m_User_Bundle.getString(getResources().getString(R.string.userid), "User Id");
        m_User_Name = m_User_Bundle.getString("username", "User Name");

        prepareUserData();

        //loadUserData();

        mTextView_ChangePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeUserPhoto();
            }
        });

        mButton_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserData();
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

                if(menuItem.getTitle().equals("Courses")){
                    Intent destinationDetailIntent = new Intent(getApplicationContext(), CoursesActivity.class);
                    destinationDetailIntent.putExtra(getResources().getString(R.string.userid), m_User_Id);
                    destinationDetailIntent.putExtra("username", m_User_Name);
                    startActivity(destinationDetailIntent);
                }else if(menuItem.getTitle().equals("Articles")){
                    Intent destinationDetailIntent = new Intent(getApplicationContext(), ArticlesActivity.class);
                    destinationDetailIntent.putExtra(getResources().getString(R.string.userid), m_User_Id);
                    destinationDetailIntent.putExtra("username", m_User_Name);
                    startActivity(destinationDetailIntent);
                }else if(menuItem.getTitle().equals("My Downloads")){

                    Intent destinationDetailIntent = new Intent(getApplicationContext(), MyDownloadsActivity.class);
                    destinationDetailIntent.putExtra(getResources().getString(R.string.userid), m_User_Id);
                    destinationDetailIntent.putExtra("username", m_User_Name);
                    startActivity(destinationDetailIntent);

                }else if(menuItem.getTitle().equals("My Results")){

                    Intent destinationDetailIntent = new Intent(getApplicationContext(), MyResultsActivity.class);
                    destinationDetailIntent.putExtra(getResources().getString(R.string.userid), m_User_Id);
                    destinationDetailIntent.putExtra("username", m_User_Name);
                    startActivity(destinationDetailIntent);

                }else if(menuItem.getTitle().equals("Update Profile")){

                    mDrawerLayout.closeDrawers();
                }else if(menuItem.getTitle().equals("Logout")){

                }

                // close drawer when item is tapped
                mDrawerLayout.closeDrawers();

                return true;
            }
        });
    }

    // Function to Prepate user data
    public void prepareUserData(){

        pd.setMessage("Loading . . .");
        pd.show();

        //RequestQueue m_Queue = Volley.newRequestQueue(UpdateProfileActivity.this);
        String response = null;

        //StringRequest postRequest = new StringRequest(Request.Method.POST, userDataURL,
        loadRequest = new StringRequest(Request.Method.POST, userDataURL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        pd.hide();

                        try{
                            JSONObject userJSON = new JSONObject(response);
                            JSONArray userArray = userJSON.getJSONArray("user_data");
                            JSONObject userObject = userArray.getJSONObject(0);

                                //creating a tutorial object and giving them the values from json object
                            user = new UserModel(userObject.getString("first_name"), userObject.getString("last_name"),userObject.getString("email")
                                        ,userObject.getString("phone"),userObject.getString("password"),userObject.getString("address"),
                                        userObject.getString("city"),userObject.getString("state"),userObject.getString("photo"));

                            loadUserData();

                        }catch (Exception e){

                                Log.e("Error:", e.getMessage());
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
                params.put("username", m_User_Id);
                return params;
            }
        };
        //postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //m_Queue.add(postRequest);
        loadRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        m_Queue.add(loadRequest);
       // loadUserData();
    }

    // Function to load user data
    public void loadUserData(){

        mEditText_FirstName.setText(user.getmFirstName().toUpperCase());
        mEditText_LastName.setText(user.getmLastName().toUpperCase());
        mEditText_EmailId.setText(user.getmEmailId());
        mEditText_PhoneNumber.setText(user.getmPhoneNumber());
        mEditText_Password.setText(user.getmPassword());
        mEditText_Address.setText(user.getmAddress());
        mEditText_City.setText(user.getmCity());
        mEditText_State.setText(user.getmState());

        RequestOptions requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true);

        try {
            Glide.with(this)
                    .load(user.getmUserPhotoURL())
                    .apply(requestOptions)
                    .placeholder(R.drawable.ea_logo_icon)
                    .error(R.drawable.back_icon)
                    .into(mImageView_UserProfilePhoto);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Could not Load image.." + e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    // Function to change user Photo
    public void changeUserPhoto(){

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

    }

    // To Set selected Image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
                mImageView_UserProfilePhoto.setImageBitmap(bitmap);

                Bitmap lastBitmap = null;
                lastBitmap = bitmap;

                //encoding image to string
                mUserUpdatedImage = getStringImage(lastBitmap);
                userPhotoChangeFlag = true;
                //uploadImage(mUserUpdatedImage);

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    // Encode Image to string
    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedUserImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedUserImage;

    }

    public void uploadImage(final String updatedImage){

        pd.setMessage("Saving User Photo . . .");
        pd.show();

        //RequestQueue m_Queue = Volley.newRequestQueue(UpdateProfileActivity.this);
        String response = null;

        //StringRequest postRequest = new StringRequest(Request.Method.POST, updatePhotoUrl,
        uploadImageRequest = new StringRequest(Request.Method.POST, updatePhotoUrl,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        pd.hide();
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

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
                String photoName = m_User_Id + ".jpg";
                params.put("username", m_User_Id);
                params.put("photoname", photoName);
                params.put("userphotostring", updatedImage);

                return params;
            }
        };
        //postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //m_Queue.add(postRequest);

        uploadImageRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        m_Queue.add(uploadImageRequest);
    }

    // Function to Update user data
    public void updateUserData(){

        //user.setmFirstName(mEditText_FirstName.getText().toString());
        //user.setmLastName(mEditText_LastName.getText().toString());

        pd.setMessage("Saving Data . . .");
        pd.show();

        if(userPhotoChangeFlag){
            uploadImage(mUserUpdatedImage);
        }

        //RequestQueue m_Queue = Volley.newRequestQueue(UpdateProfileActivity.this);
        String response = null;

       // StringRequest postRequest = new StringRequest(Request.Method.POST, updateUserDataURL,
        updateUserRequest = new StringRequest(Request.Method.POST, updateUserDataURL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        pd.hide();

                        try{

                            JSONObject userJSON = new JSONObject(response);

                                String status;
                                status = userJSON.getString("user_data");

                                if(userJSON.getString("user_data").equals("Successful")){
                                    Toast.makeText(getApplicationContext(),"Your update has been saved successfully.", Toast.LENGTH_LONG).show();
                                    //prepareUserData();
                                }else{
                                    Toast.makeText(getApplicationContext(),"Your update could not be stored. Please try Again!!.", Toast.LENGTH_LONG).show();
                                }

                        }catch (Exception e){

                            Log.e("Error:", e.getMessage());
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
                params.put("username", m_User_Id);
                params.put("first_name", mEditText_FirstName.getText().toString());
                params.put("last_name", mEditText_LastName.getText().toString());
                params.put("email", mEditText_EmailId.getText().toString());
                params.put("phone", mEditText_PhoneNumber.getText().toString());
                params.put("password", mEditText_Password.getText().toString());
                params.put("address", mEditText_Address.getText().toString());
                params.put("city", mEditText_City.getText().toString());
                params.put("state", mEditText_State.getText().toString());

                //params.put("photo", mEditText_FirstName.getText().toString());


                return params;
            }
        };
        //postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //m_Queue.add(postRequest);

        updateUserRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        m_Queue.add(updateUserRequest);
    }

}