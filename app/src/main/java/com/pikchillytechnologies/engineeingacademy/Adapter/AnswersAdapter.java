package com.pikchillytechnologies.engineeingacademy.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.pikchillytechnologies.engineeingacademy.Model.AnswersModel;
import com.pikchillytechnologies.engineeingacademy.Model.ExamQuestionModel;
import com.pikchillytechnologies.engineeingacademy.Model.UserResponseModel;
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

        String questionType = m_QuestionAnswer.getM_Question_Type();
        String answerType = m_QuestionAnswer.getM_Answer_Type();
        String questionSupportImage_Eng = m_QuestionAnswer.getM_Question_Eng_Img_url();
        String questionSupportImage_Hindi = m_QuestionAnswer.getM_Question_Hindi_Img_url();

        holder.mTextView_Question_Number.setText("Question: "+ m_QuestionAnswer.getM_Question_Number());

        //Check if the question is Text or Image
        if (questionType.equals("T")) {

            holder.mTextView_Question_Text.setVisibility(View.VISIBLE);
            holder.mImageView_Question_Image.setVisibility(View.GONE);

            holder.mTextView_Question_Text.setText(m_QuestionAnswer.getM_Question_Eng());

        } else if (questionType.equals("I")) {

            holder.mTextView_Question_Text.setVisibility(View.GONE);
            holder.mImageView_Question_Image.setVisibility(View.VISIBLE);

            try {
                Glide.with(context)
                        .load(m_QuestionAnswer.getM_Question_Eng())
                        .placeholder(R.drawable.logo)
                        .error(R.drawable.back_icon)
                        .into(holder.mImageView_Question_Image);
            } catch (Exception e) {
                Toast.makeText(context, "Could not Load image.." + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

        // Check if the supported image for question is available
        if (questionSupportImage_Eng.equals("NA")) {
            holder.mImageView_QuestionSupportedImage.setVisibility(View.GONE);

        } else {
            holder.mImageView_QuestionSupportedImage.setVisibility(View.VISIBLE);

            try {
                Glide.with(context)
                        .load(questionSupportImage_Eng)
                        .placeholder(R.drawable.logo)
                        .error(R.drawable.back_icon)
                        .into(holder.mImageView_QuestionSupportedImage);
            } catch (Exception e) {
                Toast.makeText(context, "Could not Load image.." + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

        if (answerType.equals("T")) {

            holder.mLayout_Answers_Text.setVisibility(View.VISIBLE);
            holder.mLayout_Answers_Image.setVisibility(View.GONE);

            // Set Answer for Text English
            setCheckboxAnswersText(holder,m_QuestionAnswer.getM_Answer1_Eng(), m_QuestionAnswer.getM_Answer2_Eng(), m_QuestionAnswer.getM_Answer3_Eng(),
                    m_QuestionAnswer.getM_Answer4_Eng(), m_QuestionAnswer.getM_Answer5_Eng(), m_QuestionAnswer.getM_Answer6_Eng());


        } else if (answerType.equals("I")) {

            holder.mLayout_Answers_Image.setVisibility(View.VISIBLE);
            holder.mLayout_Answers_Text.setVisibility(View.GONE);

            setCheckboxAnswersImage(holder,m_QuestionAnswer.getM_Answer1_Eng(), holder.mImageview_Answer1);
            setCheckboxAnswersImage(holder,m_QuestionAnswer.getM_Answer2_Eng(), holder.mImageview_Answer2);
            setCheckboxAnswersImage(holder,m_QuestionAnswer.getM_Answer3_Eng(), holder.mImageview_Answer3);
            setCheckboxAnswersImage(holder,m_QuestionAnswer.getM_Answer4_Eng(), holder.mImageview_Answer4);
            setCheckboxAnswersImage(holder,m_QuestionAnswer.getM_Answer5_Eng(), holder.mImageview_Answer5);
            setCheckboxAnswersImage(holder,m_QuestionAnswer.getM_Answer6_Eng(), holder.mImageview_Answer6);

        }
    }

    // Function to set the Text Answers into Text
    public void setCheckboxAnswersText(AnswersAdapter.MyViewHolder holder, String answer1, String answer2, String answer3, String answer4, String answer5, String answer6) {

        holder.mCheckbox_Answer1_Text.setText(answer1);
        holder.mCheckbox_Answer2_Text.setText(answer2);
        holder.mCheckbox_Answer3_Text.setText(answer3);
        holder.mCheckbox_Answer4_Text.setText(answer4);
        holder.mCheckbox_Answer5_Text.setText(answer5);
        holder.mCheckbox_Answer6_Text.setText(answer6);

        if (m_QuestionAnswer.getM_Answer1_Flag().equals("Y")) {
            holder.mCheckbox_Answer1_Text.setChecked(true);
            holder.mCheckbox_Answer1_Text.setBackgroundColor(Color.GREEN);
            holder.mCheckbox_Answer1_Text.setEnabled(false);
        } else if(m_QuestionAnswer.getM_Answer1_Flag().equals("N") && m_QuestionAnswer.getM_User_Answer1().equals("Y")){
            holder.mCheckbox_Answer1_Text.setChecked(true);
            holder.mCheckbox_Answer1_Text.setBackgroundColor(Color.RED);
            holder.mCheckbox_Answer1_Text.setEnabled(false);
        } else {
            holder.mCheckbox_Answer1_Text.setChecked(false);
            holder.mCheckbox_Answer1_Text.setEnabled(false);
        }

        if (m_QuestionAnswer.getM_Answer2_Flag().equals("Y")) {
            holder.mCheckbox_Answer2_Text.setChecked(true);
            holder.mCheckbox_Answer2_Text.setBackgroundColor(Color.GREEN);
            holder.mCheckbox_Answer2_Text.setEnabled(false);
        } else if(m_QuestionAnswer.getM_Answer1_Flag().equals("N") && m_QuestionAnswer.getM_User_Answer2().equals("Y")){
            holder.mCheckbox_Answer2_Text.setChecked(true);
            holder.mCheckbox_Answer2_Text.setBackgroundColor(Color.RED);
            holder.mCheckbox_Answer2_Text.setEnabled(false);
        } else {
            holder.mCheckbox_Answer2_Text.setChecked(false);
            holder.mCheckbox_Answer2_Text.setEnabled(false);
        }

        if (m_QuestionAnswer.getM_Answer3_Flag().equals("Y")) {
            holder.mCheckbox_Answer3_Text.setChecked(true);
            holder.mCheckbox_Answer3_Text.setBackgroundColor(Color.GREEN);
            holder.mCheckbox_Answer3_Text.setEnabled(false);
        } else if(m_QuestionAnswer.getM_Answer3_Flag().equals("N") && m_QuestionAnswer.getM_User_Answer3().equals("Y")){
            holder.mCheckbox_Answer3_Text.setChecked(true);
            holder.mCheckbox_Answer3_Text.setBackgroundColor(Color.RED);
            holder.mCheckbox_Answer3_Text.setEnabled(false);
        } else {
            holder.mCheckbox_Answer3_Text.setChecked(false);
            holder.mCheckbox_Answer3_Text.setEnabled(false);
        }

        if (m_QuestionAnswer.getM_Answer4_Flag().equals("Y")) {
            holder.mCheckbox_Answer4_Text.setChecked(true);
            holder.mCheckbox_Answer4_Text.setBackgroundColor(Color.GREEN);
            holder.mCheckbox_Answer4_Text.setEnabled(false);
        } else if(m_QuestionAnswer.getM_Answer4_Flag().equals("N") && m_QuestionAnswer.getM_User_Answer4().equals("Y")){
            holder.mCheckbox_Answer4_Text.setChecked(true);
            holder.mCheckbox_Answer4_Text.setBackgroundColor(Color.RED);
            holder.mCheckbox_Answer4_Text.setEnabled(false);
        } else {
            holder.mCheckbox_Answer4_Text.setChecked(false);
            holder.mCheckbox_Answer4_Text.setEnabled(false);
        }

        if (m_QuestionAnswer.getM_Answer5_Flag().equals("Y")) {
            holder.mCheckbox_Answer5_Text.setChecked(true);
            holder.mCheckbox_Answer5_Text.setBackgroundColor(Color.GREEN);
            holder.mCheckbox_Answer5_Text.setEnabled(false);
        } else if(m_QuestionAnswer.getM_Answer5_Flag().equals("N") && m_QuestionAnswer.getM_User_Answer5().equals("Y")){
            holder.mCheckbox_Answer5_Text.setChecked(true);
            holder.mCheckbox_Answer5_Text.setBackgroundColor(Color.RED);
            holder.mCheckbox_Answer5_Text.setEnabled(false);
        } else {
            holder.mCheckbox_Answer5_Text.setChecked(false);
            holder.mCheckbox_Answer5_Text.setEnabled(false);
        }

        if (m_QuestionAnswer.getM_Answer6_Flag().equals("Y")) {
            holder.mCheckbox_Answer6_Text.setChecked(true);
            holder.mCheckbox_Answer6_Text.setBackgroundColor(Color.GREEN);
            holder.mCheckbox_Answer6_Text.setEnabled(false);
        } else if(m_QuestionAnswer.getM_Answer6_Flag().equals("N") && m_QuestionAnswer.getM_User_Answer6().equals("Y")){
            holder.mCheckbox_Answer6_Text.setChecked(true);
            holder.mCheckbox_Answer6_Text.setBackgroundColor(Color.RED);
            holder.mCheckbox_Answer6_Text.setEnabled(false);
        } else {
            holder.mCheckbox_Answer6_Text.setChecked(false);
            holder.mCheckbox_Answer6_Text.setEnabled(false);
        }

    }

    // Function to set the Image Answers into checkbox
    public void setCheckboxAnswersImage(AnswersAdapter.MyViewHolder holder, String answer, ImageView imageViewAnswerImage) {

        try {
            Glide.with(context)
                    .load(answer)
                    .placeholder(R.drawable.logo)
                    .error(R.drawable.back_icon)
                    .into(imageViewAnswerImage);
        } catch (Exception e) {
            Toast.makeText(context, "Could not Load image.." + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        if (m_QuestionAnswer.getM_Answer1_Flag().equals("Y")) {
            holder.mCheckbox_Answer1_Image.setChecked(true);
            holder.mCheckbox_Answer1_Image.setBackgroundColor(Color.GREEN);
            holder.mCheckbox_Answer1_Image.setEnabled(false);
        } else {
            holder.mCheckbox_Answer1_Image.setChecked(false);
            holder.mCheckbox_Answer1_Image.setEnabled(false);
        }

        if (m_QuestionAnswer.getM_Answer2_Flag().equals("Y")) {
            holder.mCheckbox_Answer2_Image.setChecked(true);
            holder.mCheckbox_Answer2_Image.setBackgroundColor(Color.GREEN);
            holder.mCheckbox_Answer2_Image.setEnabled(false);
        } else {
            holder.mCheckbox_Answer2_Image.setChecked(false);
            holder.mCheckbox_Answer2_Image.setEnabled(false);
        }

        if (m_QuestionAnswer.getM_Answer3_Flag().equals("Y")) {
            holder.mCheckbox_Answer3_Image.setChecked(true);
            holder.mCheckbox_Answer3_Image.setBackgroundColor(Color.GREEN);
            holder.mCheckbox_Answer3_Image.setEnabled(false);
        } else {
            holder.mCheckbox_Answer3_Image.setChecked(false);
            holder.mCheckbox_Answer3_Image.setEnabled(false);
        }

        if (m_QuestionAnswer.getM_Answer4_Flag().equals("Y")) {
            holder.mCheckbox_Answer4_Image.setChecked(true);
            holder.mCheckbox_Answer4_Image.setBackgroundColor(Color.GREEN);
            holder.mCheckbox_Answer4_Image.setEnabled(false);
        } else {
            holder.mCheckbox_Answer4_Image.setChecked(false);
            holder.mCheckbox_Answer4_Image.setEnabled(false);
        }

        if (m_QuestionAnswer.getM_Answer5_Flag().equals("Y")){
            holder.mCheckbox_Answer5_Image.setChecked(true);
            holder.mCheckbox_Answer5_Image.setBackgroundColor(Color.GREEN);
            holder.mCheckbox_Answer5_Image.setEnabled(false);
        } else {
            holder.mCheckbox_Answer5_Image.setChecked(false);
            holder.mCheckbox_Answer5_Image.setEnabled(false);
        }

        if (m_QuestionAnswer.getM_Answer6_Flag().equals("Y")) {
            holder.mCheckbox_Answer6_Image.setChecked(true);
            holder.mCheckbox_Answer6_Image.setBackgroundColor(Color.GREEN);
            holder.mCheckbox_Answer6_Image.setEnabled(false);
        } else {
            holder.mCheckbox_Answer6_Image.setChecked(false);
            holder.mCheckbox_Answer6_Image.setEnabled(false);
        }

        if (m_QuestionAnswer.getM_User_Answer1().equals("Y") && m_QuestionAnswer.getM_Answer1_Flag().equals("N")) {
            holder.mCheckbox_Answer1_Image.setChecked(true);
            holder.mCheckbox_Answer1_Image.setBackgroundColor(Color.RED);
            holder.mCheckbox_Answer1_Image.setEnabled(false);
        }

        if (m_QuestionAnswer.getM_User_Answer2().equals("Y") && m_QuestionAnswer.getM_Answer2_Flag().equals("N")) {
            holder.mCheckbox_Answer2_Image.setChecked(true);
            holder.mCheckbox_Answer2_Image.setBackgroundColor(Color.RED);
            holder.mCheckbox_Answer2_Image.setEnabled(false);
        }

        if (m_QuestionAnswer.getM_User_Answer3().equals("Y") && m_QuestionAnswer.getM_Answer3_Flag().equals("N")) {
            holder.mCheckbox_Answer3_Image.setChecked(true);
            holder.mCheckbox_Answer3_Image.setBackgroundColor(Color.RED);
            holder.mCheckbox_Answer3_Image.setEnabled(false);
        }

        if (m_QuestionAnswer.getM_User_Answer4().equals("Y") && m_QuestionAnswer.getM_Answer4_Flag().equals("N")) {
            holder.mCheckbox_Answer4_Image.setChecked(true);
            holder.mCheckbox_Answer4_Image.setBackgroundColor(Color.RED);
            holder.mCheckbox_Answer4_Image.setEnabled(false);
        }

        if (m_QuestionAnswer.getM_User_Answer5().equals("Y") && m_QuestionAnswer.getM_Answer5_Flag().equals("N")) {
            holder.mCheckbox_Answer5_Image.setChecked(true);
            holder.mCheckbox_Answer5_Image.setBackgroundColor(Color.RED);
            holder.mCheckbox_Answer5_Image.setEnabled(false);
        }

        if (m_QuestionAnswer.getM_User_Answer6().equals("Y") && m_QuestionAnswer.getM_Answer6_Flag().equals("N")) {
            holder.mCheckbox_Answer6_Image.setChecked(true);
            holder.mCheckbox_Answer6_Image.setBackgroundColor(Color.RED);
            holder.mCheckbox_Answer6_Image.setEnabled(false);
        }
    }

    @Override
    public int getItemCount() {
        return m_QuestionAnswers_List.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private Context context;

        private TextView mTextView_Question_Number;
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

            this.mTextView_Question_Number = view.findViewById(R.id.textView_Question_Number);
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

            this.mLayout_Answers_Image = view.findViewById(R.id.layout_Answers_Image);
            this.mCheckbox_Answer1_Image = view.findViewById(R.id.checkbox_Answer1_Image);
            this.mCheckbox_Answer2_Image = view.findViewById(R.id.checkbox_Answer2_Image);
            this.mCheckbox_Answer3_Image = view.findViewById(R.id.checkbox_Answer3_Image);
            this.mCheckbox_Answer4_Image = view.findViewById(R.id.checkbox_Answer4_Image);
            this.mCheckbox_Answer5_Image = view.findViewById(R.id.checkbox_Answer5_Image);
            this.mCheckbox_Answer6_Image = view.findViewById(R.id.checkbox_Answer6_Image);

            this.mImageview_Answer1 = view.findViewById(R.id.imageview_Answer1);
            this.mImageview_Answer2 = view.findViewById(R.id.imageview_Answer2);
            this.mImageview_Answer3 = view.findViewById(R.id.imageview_Answer3);
            this.mImageview_Answer4 = view.findViewById(R.id.imageview_Answer4);
            this.mImageview_Answer5 = view.findViewById(R.id.imageview_Answer5);
            this.mImageview_Answer6 = view.findViewById(R.id.imageview_Answer6);

        }
    }
}