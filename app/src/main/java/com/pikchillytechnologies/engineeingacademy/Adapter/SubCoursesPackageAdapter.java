package com.pikchillytechnologies.engineeingacademy.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.pikchillytechnologies.engineeingacademy.R;
import com.pikchillytechnologies.engineeingacademy.Model.SubCoursePackage;

import java.util.List;

public class SubCoursesPackageAdapter extends RecyclerView.Adapter<SubCoursesPackageAdapter.MyViewHolder>{

    private List<SubCoursePackage> m_Sub_Course_Packages_List;
    private Context mContext;

    public SubCoursesPackageAdapter(Context context, List<SubCoursePackage> subCoursesPackage){
        this.mContext = context;
        this.m_Sub_Course_Packages_List = subCoursesPackage;
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

        SubCoursePackage subCourse = m_Sub_Course_Packages_List.get(position);
        holder.m_TextView_Sub_Category.setText(subCourse.getM_Sub_Course_Name());
        holder.m_TextView_Cost.setText("Rs." + subCourse.getM_Cost());
        holder.m_TextView_Total_Exams.setText(subCourse.getM_Total_Exams());

        // If user paid for the sub category hide the lock image
        if(subCourse.getM_Payment_Status().equals("Paid")){
            holder.m_ImageView_Lock.setVisibility(View.INVISIBLE);
            holder.m_Button_Buy.setVisibility(View.GONE);
        }else{
            holder.m_ImageView_Lock.setVisibility(View.VISIBLE);
            holder.m_Button_Buy.setVisibility(View.VISIBLE);
        }
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

        public MyViewHolder(View view){
            super(view);

            m_TextView_Sub_Category = view.findViewById(R.id.textView_Sub_Category);
            m_TextView_Cost = view.findViewById(R.id.textView_Cost);
            m_TextView_Total_Exams = view.findViewById(R.id.textView_Total_Exams);
            m_ImageView_Lock = view.findViewById(R.id.imageView_Lock);
            m_Button_Buy = view.findViewById(R.id.button_Buy);
        }
    }
}
