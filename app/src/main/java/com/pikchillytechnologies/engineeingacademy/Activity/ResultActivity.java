package com.pikchillytechnologies.engineeingacademy.Activity;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.pikchillytechnologies.engineeingacademy.Model.UserResultModel;
import com.pikchillytechnologies.engineeingacademy.R;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    private TextView m_TextView_Activity_Title;
    private Button m_Button_Res_View_Answers;
    private Button m_Button_Share;

    private Bundle m_User_Exam_Bundle;
    private String m_User_Id;
    private String m_User_Name;
    private String m_Exam_Id;
    private String m_Total_Questions;
    private String m_Title;
    private String m_Correct;
    private String m_Wrong;
    private String m_Not_Attempted;
    private String m_Score;

    private TextView m_TextView_Total_Questions;
    private TextView m_TextView_Total_Correct;
    private TextView m_TextView_Total_Wrong;
    private TextView m_TextView_Total_Not_Attempted;
    private TextView m_TextView_Total_Score;
    private TextView m_TextView_User_Name;
    private PieChart pieChart;

    private LinearLayout m_Layout_Result_PDF;
    private Bitmap bitmap;

    private ProgressDialog progressDialog;
    private UserResultModel userResult;
    private StringRequest stringRequest;
    private RequestQueue requestQueue;
    private String url = "https://pikchilly.com/api/get_user_result.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        m_User_Exam_Bundle = getIntent().getExtras();
        m_TextView_Activity_Title = findViewById(R.id.textView_Activity_Title);
        m_Button_Res_View_Answers = findViewById(R.id.button_Res_View_Answers);
        m_TextView_Total_Questions = findViewById(R.id.textView_Res_Total_Questions);
        m_TextView_Total_Correct = findViewById(R.id.textView_Res_Total_Correct);
        m_TextView_Total_Wrong = findViewById(R.id.textView_Res_Total_Wrong);
        m_TextView_Total_Not_Attempted = findViewById(R.id.textView_Res_Total_Not_Attempted);
        m_TextView_Total_Score = findViewById(R.id.textView_Res_Total_Score);
        m_TextView_User_Name = findViewById(R.id.textView_Res_Name);
        m_Layout_Result_PDF = findViewById(R.id.layout_Result);
        m_Button_Share = findViewById(R.id.button_Res_Share);

        m_TextView_Activity_Title.setText(getResources().getString(R.string.result));

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

        m_User_Id = m_User_Exam_Bundle.getString(getResources().getString(R.string.userid),"User Id");
        m_User_Name = m_User_Exam_Bundle.getString("username", "User Name");
        m_Exam_Id = m_User_Exam_Bundle.getString(getResources().getString(R.string.examid), "Exam Id");
        m_Title = m_User_Exam_Bundle.getString(getResources().getString(R.string.title), "Exam");
        m_Total_Questions = m_User_Exam_Bundle.getString("total_questions", "Total Questions");
        m_Correct = m_User_Exam_Bundle.getString("correct", "Correct");
        m_Wrong = m_User_Exam_Bundle.getString("wrong", "Wrong");
        m_Not_Attempted = m_User_Exam_Bundle.getString("not_attempted", "Not Attempted");
        m_Score = m_User_Exam_Bundle.getString("total_marks", "Total Marks");

        progressDialog.show();
        updateUI();

        progressDialog.dismiss();

        m_Button_Res_View_Answers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResultActivity.this, AnswersActivity.class));
            }
        });

        m_Button_Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("size"," "+m_Layout_Result_PDF.getWidth() +"  "+m_Layout_Result_PDF.getWidth());
                bitmap = loadBitmapFromView(m_Layout_Result_PDF, m_Layout_Result_PDF.getWidth(), m_Layout_Result_PDF.getHeight());
                createPdf();
            }
        });

    }

    public static Bitmap loadBitmapFromView(View v, int width, int height) {
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);

        return b;
    }

    private void createPdf(){

        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        float height = displaymetrics.heightPixels ;
        float width = displaymetrics.widthPixels ;

        int convertHeight = (int) height;
        int convertWidth = (int) width;

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(convertWidth, convertHeight, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();

        Paint paint = new Paint();
        canvas.drawPaint(paint);

        bitmap = Bitmap.createScaledBitmap(bitmap, convertWidth, convertHeight, true);

        paint.setColor(Color.BLUE);
        canvas.drawBitmap(bitmap, 0, 0 , null);
        document.finishPage(page);

        // write the document content

        String targetPdf = "/sdcard/PDF_" + m_Exam_Id + ".pdf";
        File filePath;
        filePath = new File(targetPdf);
        try {
            document.writeTo(new FileOutputStream(filePath));

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }

        // close the document
        document.close();

        shareData();
    }

    public void shareData(){

        String path = "/sdcard/PDF_" + m_Exam_Id + ".pdf";
        File file = new File(path);
        if (file.exists())
        {
            //Intent intent=new Intent(Intent.ACTION_VIEW);
            Intent intent = new Intent(Intent.ACTION_SENDTO);

            Uri uri = Uri.fromFile(file);

            intent.setData(Uri.parse("mailto:"));
            intent.putExtra(Intent.EXTRA_SUBJECT,  "Exam Result for:" + m_User_Name);
            intent.putExtra(Intent.EXTRA_STREAM, uri);

            try
            {
                startActivity(intent);
            }
            catch(ActivityNotFoundException e)
            {
                Toast.makeText(getApplicationContext(), "No Application available to view pdf", Toast.LENGTH_LONG).show();
            }
        }

    }


    public void updateUI(){

        // Update Score
        m_TextView_Total_Questions.setText(m_Total_Questions);
        m_TextView_Total_Correct.setText(m_Correct);
        m_TextView_Total_Wrong.setText(m_Wrong);
        m_TextView_Total_Not_Attempted.setText(m_Not_Attempted);
        m_TextView_Total_Score.setText(m_Score);
        m_TextView_User_Name.setText(m_User_Name.toUpperCase());

        drawChart();

    }

    public void drawChart(){

        float correct = Float.valueOf(m_Correct);
        float wrong = Float.valueOf(m_Wrong);
        float not_attempted = Float.valueOf(m_Not_Attempted);

        // update chart
        pieChart = findViewById(R.id.pieChart);
        pieChart.setUsePercentValues(true);

        ArrayList<PieEntry> yvalues = new ArrayList<PieEntry>();

        if(!m_Correct.equals("0")){
            yvalues.add(new PieEntry(correct, "Correct", 0));
        }

        if(!m_Wrong.equals("0")){
            yvalues.add(new PieEntry(wrong, "Wrong", 1));
        }

        yvalues.add(new PieEntry(not_attempted, "Not Attempted", 2));

        PieDataSet dataSet = new PieDataSet(yvalues, m_Title);
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(10f);
        data.setValueTextColor(R.color.colorOffWhiteBg);

        Description description = new Description();
        description.setText("My Performance");
        description.setTextColor(Color.CYAN);

        pieChart.setDescription(description);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(R.color.colorPrimary);
        pieChart.setTransparentCircleRadius(40f);
        pieChart.setHoleRadius(40f);
        pieChart.setData(data);
    }
}
