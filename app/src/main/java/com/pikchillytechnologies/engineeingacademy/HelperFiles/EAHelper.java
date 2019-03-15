package com.pikchillytechnologies.engineeingacademy.HelperFiles;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;

import com.pikchillytechnologies.engineeingacademy.R;

public class EAHelper {

   private Context m_Context;

    // Intent
    public void start_Activity(Context context, Class destClass){
        this.m_Context = context;
        Intent destinationDetailIntent = new Intent(m_Context, destClass);
        this.m_Context.startActivity(destinationDetailIntent);
    }

    // Intent with Title
    public void start_Activity(Context context, Class destClass, String title){
        this.m_Context = context;
        Intent destinationDetailIntent = new Intent(context, destClass);
        destinationDetailIntent.putExtra(this.m_Context.getResources().getString(R.string.title), title);
        this.m_Context.startActivity(destinationDetailIntent);
    }

    // Intent with Title
    public void start_Activity(Context context, Class destClass, String categoryId, String title){
        this.m_Context = context;
        Intent destinationDetailIntent = new Intent(context, destClass);
        destinationDetailIntent.putExtra(this.m_Context.getResources().getString(R.string.title), title);
        destinationDetailIntent.putExtra(this.m_Context.getResources().getString(R.string.categoryid), categoryId);
        this.m_Context.startActivity(destinationDetailIntent);
    }

    // Intent with Title
    public void start_Activity(Context context, Class destClass, String categoryId, String title, String subCategoryId){
        this.m_Context = context;
        Intent destinationDetailIntent = new Intent(context, destClass);
        destinationDetailIntent.putExtra(this.m_Context.getResources().getString(R.string.title), title);
        destinationDetailIntent.putExtra(this.m_Context.getResources().getString(R.string.categoryid), categoryId);
        destinationDetailIntent.putExtra(this.m_Context.getResources().getString(R.string.subcategoryid), subCategoryId);
        this.m_Context.startActivity(destinationDetailIntent);
    }


    /**
     * Function to check if Device is connected to Internet
     */
    public boolean isNetworkAvailable(Context context) {

        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnected();
        return isConnected;
    }

}
