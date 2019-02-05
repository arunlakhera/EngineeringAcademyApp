package com.pikchillytechnologies.engineeingacademy.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pikchillytechnologies.engineeingacademy.Model.ExamListModel;
import com.pikchillytechnologies.engineeingacademy.Model.ResultModel;
import com.pikchillytechnologies.engineeingacademy.R;

import java.util.List;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.MyViewHolder> {

    private List<ResultModel> m_Result_List;

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView m_TextView_Ques_Number;
        private TextView m_TextView_Question;
        private TextView m_Radio_Answer1;
        private TextView m_Radio_Answer2;
        private TextView m_Radio_Answer3;
        private TextView m_Radio_Answer4;
        private TextView m_textView_Explaination;

        public MyViewHolder(View view){
            super(view);

            this.m_TextView_Ques_Number = view.findViewById(R.id.textView_Ans_Ques_Number);
            this.m_TextView_Question = view.findViewById(R.id.textView_Ans_Question);
            this.m_Radio_Answer1 = view.findViewById(R.id.radio_Ans_Ans1);
            this.m_Radio_Answer2 = view.findViewById(R.id.radio_Ans_Ans2);
            this.m_Radio_Answer3 = view.findViewById(R.id.radio_Ans_Ans3);
            this.m_Radio_Answer4 = view.findViewById(R.id.radio_Ans_Ans4);
            this.m_textView_Explaination = view.findViewById(R.id.textView_Ans_Explaination);
        }
    }

    public ResultAdapter(List<ResultModel> resultList){
        this.m_Result_List = resultList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.answers_listview,parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ResultModel result = m_Result_List.get(position);
        holder.m_TextView_Ques_Number.setText(result.getM_Question_Number());
        holder.m_TextView_Question.setText(result.getM_Question());
        holder.m_Radio_Answer1.setText(result.getM_Answer1());
        holder.m_Radio_Answer2.setText(result.getM_Answer2());
        holder.m_Radio_Answer3.setText(result.getM_Answer3());
        holder.m_Radio_Answer4.setText(result.getM_Answer4());
        holder.m_textView_Explaination.setText(result.getM_Explaination());
    }

    @Override
    public int getItemCount() {
        return m_Result_List.size();
    }
}
