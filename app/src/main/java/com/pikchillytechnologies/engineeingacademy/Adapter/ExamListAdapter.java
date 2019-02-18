package com.pikchillytechnologies.engineeingacademy.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pikchillytechnologies.engineeingacademy.Model.ExamListModel;
import com.pikchillytechnologies.engineeingacademy.R;

import java.util.List;

public class ExamListAdapter extends RecyclerView.Adapter<ExamListAdapter.MyViewHolder> {

    private List<ExamListModel> m_Exam_List;


    public ExamListAdapter(List<ExamListModel> examList){
        this.m_Exam_List = examList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.exam_list_listview,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ExamListModel exam = m_Exam_List.get(position);

        holder.m_TextView_Exam_Name.setText(exam.getM_Exam_Name());
        holder.m_TextView_Exam_Available_Date.setText(exam.getM_Exam_Available_From());
        holder.m_TextView_Exam_Till_Date.setText(exam.getM_Exam_Available_Till());

    }

    @Override
    public int getItemCount() {
        return m_Exam_List.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView m_TextView_Exam_Name;
        private TextView m_TextView_Exam_Available_Date;
        private TextView m_TextView_Exam_Till_Date;

        public MyViewHolder(View view){
            super(view);

            this.m_TextView_Exam_Name = view.findViewById(R.id.textView_Exam_Name);
            this.m_TextView_Exam_Available_Date = view.findViewById(R.id.textView_Available_Date);
            this.m_TextView_Exam_Till_Date = view.findViewById(R.id.textView_Till_Date);
        }
    }

}
