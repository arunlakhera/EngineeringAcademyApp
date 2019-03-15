package com.pikchillytechnologies.engineeingacademy.Activity;

import android.app.Activity;
import android.content.IntentFilter;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.pikchillytechnologies.engineeingacademy.HelperFiles.EAHelper;
import com.pikchillytechnologies.engineeingacademy.HelperFiles.SessionHandler;
import com.pikchillytechnologies.engineeingacademy.R;

import org.json.JSONException;
import org.json.JSONObject;

import instamojo.library.InstamojoPay;
import instamojo.library.InstapayListener;

public class MyCartActivity extends AppCompatActivity {

    private TextView m_TextView_Activity_Title;
    private Button m_Button_Pay;
    private Button m_Button_Back;

    //Navigation Drawer
    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;
    private Button menuButton;

    private EAHelper m_Helper;

    private Bundle m_Course_Bundle;
    private String m_User_Id;
    private String m_Category_Id;
    private String m_Sub_Category_Id;

    private SessionHandler session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);

        session = new SessionHandler(getApplicationContext());
        m_Helper = new EAHelper();

        m_Button_Pay = findViewById(R.id.button_Pay);
        m_TextView_Activity_Title = findViewById(R.id.textView_Activity_Title);
        m_Button_Back = findViewById(R.id.button_Back);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        menuButton = findViewById(R.id.button_Menu);

        // Get variable from prev activity
        m_Course_Bundle = getIntent().getExtras();

        m_User_Id = m_Course_Bundle.getString(getResources().getString(R.string.userid),"User Id");
        m_Category_Id = m_Course_Bundle.getString(getResources().getString(R.string.categoryid),getResources().getString(R.string.packages));
        m_Sub_Category_Id = m_Course_Bundle.getString(getResources().getString(R.string.subcategoryid),"sub_category_id");

        m_TextView_Activity_Title.setText("My Cart");


        m_Button_Pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(m_Helper.isNetworkAvailable(getApplicationContext())){

                    // Perform Action if network is available
                    callInstamojoPay("arunlakhera@gmail.com","7895099770","200","Course","Arun");

                }else{
                    Toast.makeText(getApplicationContext(),"Please connect to Internet.", Toast.LENGTH_LONG).show();
                }

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

                String orderId = responseArray[1].substring(responseArray[1].indexOf("=")+1);
                Log.d("ORDERID:", orderId);

                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int code, String reason) {
                Toast.makeText(getApplicationContext(), "Failed: " + reason, Toast.LENGTH_LONG)
                        .show();
            }
        };
    }

}
