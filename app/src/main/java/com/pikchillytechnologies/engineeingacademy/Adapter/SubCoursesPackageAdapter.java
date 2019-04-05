package com.pikchillytechnologies.engineeingacademy.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pikchillytechnologies.engineeingacademy.Activity.ExamListActivity;
import com.pikchillytechnologies.engineeingacademy.Activity.MyCartActivity;
import com.pikchillytechnologies.engineeingacademy.Activity.SubCoursesActivity;
import com.pikchillytechnologies.engineeingacademy.R;
import com.pikchillytechnologies.engineeingacademy.Model.SubCoursePackage;

import java.util.List;

public class SubCoursesPackageAdapter extends RecyclerView.Adapter<SubCoursesPackageAdapter.MyViewHolder>{

    private List<SubCoursePackage> m_Sub_Course_Packages_List;
    private Context mContext;
    private String mCategoryName;
    private String m_User_Id;
    private String m_Category_Id;
    private String m_SubCategory_Id;
    private String m_Title;

    public SubCoursesPackageAdapter(Context context, List<SubCoursePackage> subCoursesPackage, String categoryName, String title ,String userid, String categoryid){
        this.mContext = context;
        this.m_Sub_Course_Packages_List = subCoursesPackage;
        this.mCategoryName = categoryName;
        this.m_User_Id = userid;
        this.m_Category_Id = categoryid;
        this.m_Title = title;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_courses_listview, parent, false);
        mContext = parent.getContext();
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final SubCoursePackage subCourse = m_Sub_Course_Packages_List.get(position);
        holder.m_TextView_Sub_Category.setText(subCourse.getM_Sub_Course_Name());
        holder.m_TextView_Cost.setText("Rs." + subCourse.getM_Cost());
        holder.m_TextView_Total_Exams.setText(subCourse.getM_Total_Exams());

        if(mCategoryName.equals("Mechanical")){
            holder.m_Background_ImageView.setImageResource(R.drawable.mechanical);
        }else if(mCategoryName.equals("Civil")){
            holder.m_Background_ImageView.setImageResource(R.drawable.civil_bg);
        }else if(mCategoryName.equals("Computers")){
            holder.m_Background_ImageView.setImageResource(R.drawable.computer_bg);
        }else if(mCategoryName.equals("Railways")){
            holder.m_Background_ImageView.setImageResource(R.drawable.railways_bg);
        }else if(mCategoryName.equals("Non Technical")){
            holder.m_Background_ImageView.setImageResource(R.drawable.railways_bg);
        }else{
            holder.m_Background_ImageView.setImageResource(R.drawable.ea_bg);
        }

        // If user paid for the sub category hide the lock image
        if(subCourse.getM_Payment_Status().equals("Paid") || subCourse.getM_Cost().equals("0")){
            holder.m_ImageView_Lock.setVisibility(View.INVISIBLE);
            holder.m_Button_Buy.setVisibility(View.GONE);
        }else{
            holder.m_ImageView_Lock.setVisibility(View.VISIBLE);
            holder.m_Button_Buy.setVisibility(View.VISIBLE);
        }

        holder.m_Button_Buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent destinationDetailIntent = new Intent(mContext, MyCartActivity.class);
                destinationDetailIntent.putExtra(mContext.getResources().getString(R.string.userid), m_User_Id);
                destinationDetailIntent.putExtra("category_title", mCategoryName);
                destinationDetailIntent.putExtra("sub_category_title", subCourse.getM_Sub_Course_Name());
                destinationDetailIntent.putExtra(mContext.getResources().getString(R.string.categoryid), m_Category_Id);
                destinationDetailIntent.putExtra(mContext.getResources().getString(R.string.subcategoryid), subCourse.getM_Sub_Course_Id());
                destinationDetailIntent.putExtra("cost", subCourse.getM_Cost());
                destinationDetailIntent.putExtra(mContext.getResources().getString(R.string.title), m_Title);
                destinationDetailIntent.putExtra("total_exams", subCourse.getM_Total_Exams());
                mContext.startActivity(destinationDetailIntent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return m_Sub_Course_Packages_List.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView m_TextView_Sub_Category;
        private TextView m_TextView_Cost;
        private TextView m_TextView_Total_Exams;
        private ImageView m_ImageView_Lock;
        private Button m_Button_Buy;
        private ImageView m_Background_ImageView;

        public MyViewHolder(View view){
            super(view);

            m_TextView_Sub_Category = view.findViewById(R.id.textView_Sub_Category);
            m_TextView_Cost = view.findViewById(R.id.textView_Cost);
            m_TextView_Total_Exams = view.findViewById(R.id.textView_Total_Exams);
            m_ImageView_Lock = view.findViewById(R.id.imageView_Lock);
            m_Button_Buy = view.findViewById(R.id.button_Buy);
            m_Background_ImageView = view.findViewById(R.id.imageview_Background);
        }
    }
}
