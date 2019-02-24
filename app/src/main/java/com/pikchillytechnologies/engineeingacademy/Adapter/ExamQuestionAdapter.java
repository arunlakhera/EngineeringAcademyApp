package com.pikchillytechnologies.engineeingacademy.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pikchillytechnologies.engineeingacademy.Model.ExamQuestionModel;
import com.pikchillytechnologies.engineeingacademy.R;

import java.util.List;

public class ExamQuestionAdapter extends RecyclerView.Adapter<ExamQuestionAdapter.MyViewHolder>{

    private List<ExamQuestionModel> m_Exam_Question_List;
    private Context context;
    private ExamQuestionModel question;
    public int row_index = -1;

    public ExamQuestionAdapter(Context context, List<ExamQuestionModel> examList){
        this.context = context;
        this.m_Exam_Question_List = examList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.exam_question_listview,parent,false);
        return new MyViewHolder(context,itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        question = m_Exam_Question_List.get(position);

        holder.m_TextView_Question_Number.setText(question.getM_Question_Number());
        holder.m_TextView_Question_Number.setBackgroundResource(R.drawable.black_circle);

        if(question.getRead()){
            holder.m_TextView_Question_Number.setBackgroundResource(R.drawable.green_circle);
        }else{
            holder.m_TextView_Question_Number.setBackgroundResource(R.drawable.black_circle);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                row_index = holder.getAdapterPosition();
                notifyDataSetChanged();

            }
        });

        // For Next/Prev Button
        if(row_index == position){
            holder.m_TextView_Question_Number.setBackgroundResource(R.drawable.white_circle);
        }else{

        }

    }


    @Override
    public int getItemCount() {
        return m_Exam_Question_List.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView m_TextView_Question_Number;
        private Context context;

        public MyViewHolder(Context context, View view){
            super(view);

            this.context = context;
            this.m_TextView_Question_Number = view.findViewById(R.id.textview_Question_Number);

        }

    }

}
