package com.pikchillytechnologies.engineeingacademy.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pikchillytechnologies.engineeingacademy.Model.AnswersModel;
import com.pikchillytechnologies.engineeingacademy.Model.ExamQuestionModel;
import com.pikchillytechnologies.engineeingacademy.R;

import java.util.List;

public class AnswersAdapter extends RecyclerView.Adapter<AnswersAdapter.MyViewHolder> {

    private List<AnswersModel> m_QuestionAnswers_List;
    private Context context;
    private AnswersModel m_QuestionAnswer;

    public AnswersAdapter(Context context, List<AnswersModel> questionAnswerList){
        this.context = context;
        this.m_QuestionAnswers_List = questionAnswerList;
    }

    @NonNull
    @Override
    public AnswersAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.answers_listview,parent, false);
        return new AnswersAdapter.MyViewHolder(context, itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AnswersAdapter.MyViewHolder holder, int position) {

        m_QuestionAnswer = m_QuestionAnswers_List.get(position);

        holder.mTextView_Question_Text.setText(m_QuestionAnswer.getM_Question_Eng());
        holder.mCheckbox_Answer1_Text.setText(m_QuestionAnswer.getM_Answer1_Eng());
        holder.mCheckbox_Answer2_Text.setText(m_QuestionAnswer.getM_Answer2_Eng());
        holder.mCheckbox_Answer3_Text.setText(m_QuestionAnswer.getM_Answer3_Eng());
        holder.mCheckbox_Answer4_Text.setText(m_QuestionAnswer.getM_Answer4_Eng());
        holder.mCheckbox_Answer5_Text.setText(m_QuestionAnswer.getM_Answer5_Eng());
        holder.mCheckbox_Answer6_Text.setText(m_QuestionAnswer.getM_Answer6_Eng());

    }

    @Override
    public int getItemCount() {
        return m_QuestionAnswers_List.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private Context context;

        private TextView mTextView_Question_Text;
        private ImageView mImageView_Question_Image;

        private ImageView mImageView_QuestionSupportedImage;

        private LinearLayout mLayout_Answers_Text;
        private CheckBox mCheckbox_Answer1_Text;
        private CheckBox mCheckbox_Answer2_Text;
        private CheckBox mCheckbox_Answer3_Text;
        private CheckBox mCheckbox_Answer4_Text;
        private CheckBox mCheckbox_Answer5_Text;
        private CheckBox mCheckbox_Answer6_Text;

        private LinearLayout mLayout_Answers_Image;
        private CheckBox mCheckbox_Answer1_Image;
        private CheckBox mCheckbox_Answer2_Image;
        private CheckBox mCheckbox_Answer3_Image;
        private CheckBox mCheckbox_Answer4_Image;
        private CheckBox mCheckbox_Answer5_Image;
        private CheckBox mCheckbox_Answer6_Image;

        private ImageView mImageview_Answer1;
        private ImageView mImageview_Answer2;
        private ImageView mImageview_Answer3;
        private ImageView mImageview_Answer4;
        private ImageView mImageview_Answer5;
        private ImageView mImageview_Answer6;


        public MyViewHolder(Context context, View view){
            super(view);
            this.context = context;

            this.mTextView_Question_Text = view.findViewById(R.id.textView_Question_Text);
            this.mImageView_Question_Image = view.findViewById(R.id.imageview_Question_Image);
            this.mImageView_QuestionSupportedImage = view.findViewById(R.id.imageview_QuestionSupportedImage);

            this.mLayout_Answers_Text = view.findViewById(R.id.layout_Answers_Text);
            this.mCheckbox_Answer1_Text = view.findViewById(R.id.checkbox_Answer1);
            this.mCheckbox_Answer2_Text = view.findViewById(R.id.checkbox_Answer2);
            this.mCheckbox_Answer3_Text = view.findViewById(R.id.checkbox_Answer3);
            this.mCheckbox_Answer4_Text = view.findViewById(R.id.checkbox_Answer4);
            this.mCheckbox_Answer5_Text = view.findViewById(R.id.checkbox_Answer5);
            this.mCheckbox_Answer6_Text = view.findViewById(R.id.checkbox_Answer6);

            this.mLayout_Answers_Text = view.findViewById(R.id.layout_Answers_Image);
            this.mCheckbox_Answer1_Text = view.findViewById(R.id.checkbox_Answer1_Image);
            this.mCheckbox_Answer2_Text = view.findViewById(R.id.checkbox_Answer2_Image);
            this.mCheckbox_Answer3_Text = view.findViewById(R.id.checkbox_Answer3_Image);
            this.mCheckbox_Answer4_Text = view.findViewById(R.id.checkbox_Answer4_Image);
            this.mCheckbox_Answer5_Text = view.findViewById(R.id.checkbox_Answer5_Image);
            this.mCheckbox_Answer6_Text = view.findViewById(R.id.checkbox_Answer6_Image);

            this.mImageview_Answer1 = view.findViewById(R.id.imageview_Answer1);
            this.mImageview_Answer2 = view.findViewById(R.id.imageview_Answer2);
            this.mImageview_Answer3 = view.findViewById(R.id.imageview_Answer3);
            this.mImageview_Answer4 = view.findViewById(R.id.imageview_Answer4);
            this.mImageview_Answer5 = view.findViewById(R.id.imageview_Answer5);
            this.mImageview_Answer6 = view.findViewById(R.id.imageview_Answer6);

        }
    }


}