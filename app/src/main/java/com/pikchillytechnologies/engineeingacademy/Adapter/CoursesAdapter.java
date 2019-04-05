package com.pikchillytechnologies.engineeingacademy.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pikchillytechnologies.engineeingacademy.Model.CoursesModel;
import com.pikchillytechnologies.engineeingacademy.Model.SubCoursePackage;
import com.pikchillytechnologies.engineeingacademy.R;

import java.security.PublicKey;
import java.util.List;

public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.MyViewHolder> {

    private List<CoursesModel> m_Courses_List;
    private Context mContext;

    public CoursesAdapter(Context context, List<CoursesModel> courseModel){
        this.mContext = context;
        this.m_Courses_List = courseModel;
    }

    @NonNull
    @Override
    public CoursesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.courses_list_listview,parent,false);
        mContext = parent.getContext();
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CoursesAdapter.MyViewHolder holder, int position) {

        CoursesModel course = m_Courses_List.get(position);
        holder.m_CourseName_TextView.setText(course.getM_Name());

        // Needs to be dynamic and need to put default image for else condition.

        if(course.getM_Name().equals("Mechanical Engineering")){
            holder.m_Background_ImageView.setImageResource(R.drawable.mechanical);
        }else if(course.getM_Name().equals("Civil Engineering")){
            holder.m_Background_ImageView.setImageResource(R.drawable.civil_bg);
        }else if(course.getM_Name().equals("Computer Engineering")){
            holder.m_Background_ImageView.setImageResource(R.drawable.computer_bg);
        }else if(course.getM_Name().equals("Railways")){
            holder.m_Background_ImageView.setImageResource(R.drawable.railways_bg);
        }else if(course.getM_Name().equals("Non Technical")){
            holder.m_Background_ImageView.setImageResource(R.drawable.railways_bg);
        }else{
            holder.m_Background_ImageView.setImageResource(R.drawable.mechanical);
        }
    }

    @Override
    public int getItemCount() {
        return m_Courses_List.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView m_CourseName_TextView;
        private ImageView m_Background_ImageView;

        public MyViewHolder(View view){
            super(view);

            m_CourseName_TextView = view.findViewById(R.id.textView_CourseName);
            m_Background_ImageView = view.findViewById(R.id.imageview_Background);
        }
    }
}
