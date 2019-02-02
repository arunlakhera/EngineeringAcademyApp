package com.pikchillytechnologies.engineeingacademy;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class SubCoursesPackageAdapter extends RecyclerView.Adapter<SubCoursesPackageAdapter.MyViewHolder>{

    private List<SubCoursePackage> m_Sub_Course_Packages_List;

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView m_TextView_Sub_Category;
        private TextView m_TextView_Cost;
        private TextView m_TextView_Total_Exams;
        //private ImageView m_ImageView_Lock;
        //private Button m_Button_Buy;

        public MyViewHolder(View view){
            super(view);

            m_TextView_Sub_Category = view.findViewById(R.id.textView_Sub_Category);
            m_TextView_Cost = view.findViewById(R.id.textView_Cost);
            m_TextView_Total_Exams = view.findViewById(R.id.textView_Total_Exams);
        }
    }

    public SubCoursesPackageAdapter(List<SubCoursePackage> subCoursesPackage){
        this.m_Sub_Course_Packages_List = subCoursesPackage;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_courses_listview, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        SubCoursePackage course = m_Sub_Course_Packages_List.get(position);
        holder.m_TextView_Sub_Category.setText(course.getM_Sub_Course_Name());
        holder.m_TextView_Cost.setText(course.getM_Cost());
        holder.m_TextView_Total_Exams.setText(course.getM_Total_Exams());
    }

    @Override
    public int getItemCount() {
        return m_Sub_Course_Packages_List.size();
    }
}
