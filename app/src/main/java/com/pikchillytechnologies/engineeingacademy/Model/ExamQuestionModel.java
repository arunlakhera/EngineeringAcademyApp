package com.pikchillytechnologies.engineeingacademy.Model;

public class ExamQuestionModel {

    private String m_Question_Number;
    private String m_Question_Id;
    private String m_Question_Eng;
    private String m_Question_Hindi;
    private String m_Question_Eng_Img_url;
    private String m_Question_Hindi_Img_url;
    private String m_Answer1_Eng;
    private String m_Answer2_Eng;
    private String m_Answer3_Eng;
    private String m_Answer4_Eng;
    private String m_Answer5_Eng;
    private String m_Answer6_Eng;
    private String m_Answer1_Hindi;
    private String m_Answer2_Hindi;
    private String m_Answer3_Hindi;
    private String m_Answer4_Hindi;
    private String m_Answer5_Hindi;
    private String m_Answer6_Hindi;
    private String m_Answer1_Flag;
    private String m_Answer2_Flag;
    private String m_Answer3_Flag;
    private String m_Answer4_Flag;
    private String m_Answer5_Flag;
    private String m_Answer6_Flag;
    private String m_Question_Type;
    private String m_Question_Lang;
    private String m_Answer_Type;
    private String m_Answer_Lang;

    private Boolean isRead;

    public ExamQuestionModel(String question_number,String question_id, String question_eng, String question_hindi, String question_eng_img_url, String question_hindi_img_url,
                             String answer1_eng, String answer2_eng, String answer3_eng, String answer4_eng, String answer5_eng, String answer6_eng,
                             String answer1_hindi, String answer2_hindi, String answer3_hindi, String answer4_hindi, String answer5_hindi, String answer6_hindi,
                             String answer1_flag, String answer2_flag, String answer3_flag, String answer4_flag, String answer5_flag, String answer6_flag,
                             String question_type,String question_lang,String answer_type,String answer_lang, Boolean isread
                             ){
        this.m_Question_Number = question_number;
        this.m_Question_Id = question_id;
        this.m_Question_Eng = question_eng;
        this.m_Question_Hindi = question_hindi;
        this.m_Question_Eng_Img_url = question_eng_img_url;
        this.m_Question_Hindi_Img_url = question_hindi_img_url;
        this.m_Answer1_Eng = answer1_eng;
        this.m_Answer2_Eng = answer2_eng;
        this.m_Answer3_Eng = answer3_eng;
        this.m_Answer4_Eng = answer4_eng;
        this.m_Answer5_Eng = answer5_eng;
        this.m_Answer6_Eng = answer6_eng;
        this.m_Answer1_Hindi = answer1_hindi;
        this.m_Answer2_Hindi = answer2_hindi;
        this.m_Answer3_Hindi = answer3_hindi;
        this.m_Answer4_Hindi = answer4_hindi;
        this.m_Answer5_Hindi = answer5_hindi;
        this.m_Answer6_Hindi = answer6_hindi;
        this.m_Answer1_Flag = answer1_flag;
        this.m_Answer2_Flag = answer2_flag;
        this.m_Answer3_Flag = answer3_flag;
        this.m_Answer4_Flag = answer4_flag;
        this.m_Answer5_Flag = answer5_flag;
        this.m_Answer6_Flag = answer6_flag;
        this.m_Question_Type = question_type;
        this.m_Question_Lang = question_lang;
        this.m_Answer_Type = answer_type;
        this.m_Answer_Lang = answer_lang;
        this.isRead = isread;

    }

    public String getM_Question_Number() {
        return m_Question_Number;
    }

    public void setM_Question_Number(String m_Question_Number) {
        this.m_Question_Number = m_Question_Number;
    }

    public String getM_Question_Id() {
        return m_Question_Id;
    }

    public void setM_Question_Id(String m_Question_Id) {
        this.m_Question_Id = m_Question_Id;
    }

    public String getM_Question_Eng() {
        return m_Question_Eng;
    }

    public void setM_Question_Eng(String m_Question_Eng) {
        this.m_Question_Eng = m_Question_Eng;
    }

    public String getM_Question_Hindi() {
        return m_Question_Hindi;
    }

    public void setM_Question_Hindi(String m_Question_Hindi) {
        this.m_Question_Hindi = m_Question_Hindi;
    }

    public String getM_Question_Eng_Img_url() {
        return m_Question_Eng_Img_url;
    }

    public void setM_Question_Eng_Img_url(String m_Question_Eng_Img_url) {
        this.m_Question_Eng_Img_url = m_Question_Eng_Img_url;
    }

    public String getM_Question_Hindi_Img_url() {
        return m_Question_Hindi_Img_url;
    }

    public void setM_Question_Hindi_Img_url(String m_Question_Hindi_Img_url) {
        this.m_Question_Hindi_Img_url = m_Question_Hindi_Img_url;
    }

    public String getM_Answer1_Eng() {
        return m_Answer1_Eng;
    }

    public void setM_Answer1_Eng(String m_Answer1_Eng) {
        this.m_Answer1_Eng = m_Answer1_Eng;
    }

    public String getM_Answer2_Eng() {
        return m_Answer2_Eng;
    }

    public void setM_Answer2_Eng(String m_Answer2_Eng) {
        this.m_Answer2_Eng = m_Answer2_Eng;
    }

    public String getM_Answer3_Eng() {
        return m_Answer3_Eng;
    }

    public void setM_Answer3_Eng(String m_Answer3_Eng) {
        this.m_Answer3_Eng = m_Answer3_Eng;
    }

    public String getM_Answer4_Eng() {
        return m_Answer4_Eng;
    }

    public void setM_Answer4_Eng(String m_Answer4_Eng) {
        this.m_Answer4_Eng = m_Answer4_Eng;
    }

    public String getM_Answer5_Eng() {
        return m_Answer5_Eng;
    }

    public void setM_Answer5_Eng(String m_Answer5_Eng) {
        this.m_Answer5_Eng = m_Answer5_Eng;
    }

    public String getM_Answer6_Eng() {
        return m_Answer6_Eng;
    }

    public void setM_Answer6_Eng(String m_Answer6_Eng) {
        this.m_Answer6_Eng = m_Answer6_Eng;
    }

    public String getM_Answer1_Hindi() {
        return m_Answer1_Hindi;
    }

    public void setM_Answer1_Hindi(String m_Answer1_Hindi) {
        this.m_Answer1_Hindi = m_Answer1_Hindi;
    }

    public String getM_Answer2_Hindi() {
        return m_Answer2_Hindi;
    }

    public void setM_Answer2_Hindi(String m_Answer2_Hindi) {
        this.m_Answer2_Hindi = m_Answer2_Hindi;
    }

    public String getM_Answer3_Hindi() {
        return m_Answer3_Hindi;
    }

    public void setM_Answer3_Hindi(String m_Answer3_Hindi) {
        this.m_Answer3_Hindi = m_Answer3_Hindi;
    }

    public String getM_Answer4_Hindi() {
        return m_Answer4_Hindi;
    }

    public void setM_Answer4_Hindi(String m_Answer4_Hindi) {
        this.m_Answer4_Hindi = m_Answer4_Hindi;
    }

    public String getM_Answer5_Hindi() {
        return m_Answer5_Hindi;
    }

    public void setM_Answer5_Hindi(String m_Answer5_Hindi) {
        this.m_Answer5_Hindi = m_Answer5_Hindi;
    }

    public String getM_Answer6_Hindi() {
        return m_Answer6_Hindi;
    }

    public void setM_Answer6_Hindi(String m_Answer6_Hindi) {
        this.m_Answer6_Hindi = m_Answer6_Hindi;
    }

    public String getM_Answer1_Flag() {
        return m_Answer1_Flag;
    }

    public void setM_Answer1_Flag(String m_Answer1_Flag) {
        this.m_Answer1_Flag = m_Answer1_Flag;
    }

    public String getM_Answer2_Flag() {
        return m_Answer2_Flag;
    }

    public void setM_Answer2_Flag(String m_Answer2_Flag) {
        this.m_Answer2_Flag = m_Answer2_Flag;
    }

    public String getM_Answer3_Flag() {
        return m_Answer3_Flag;
    }

    public void setM_Answer3_Flag(String m_Answer3_Flag) {
        this.m_Answer3_Flag = m_Answer3_Flag;
    }

    public String getM_Answer4_Flag() {
        return m_Answer4_Flag;
    }

    public void setM_Answer4_Flag(String m_Answer4_Flag) {
        this.m_Answer4_Flag = m_Answer4_Flag;
    }

    public String getM_Answer5_Flag() {
        return m_Answer5_Flag;
    }

    public void setM_Answer5_Flag(String m_Answer5_Flag) {
        this.m_Answer5_Flag = m_Answer5_Flag;
    }

    public String getM_Answer6_Flag() {
        return m_Answer6_Flag;
    }

    public void setM_Answer6_Flag(String m_Answer6_Flag) {
        this.m_Answer6_Flag = m_Answer6_Flag;
    }

    public String getM_Question_Type() {
        return m_Question_Type;
    }

    public void setM_Question_Type(String m_Question_Type) {
        this.m_Question_Type = m_Question_Type;
    }

    public String getM_Question_Lang() {
        return m_Question_Lang;
    }

    public void setM_Question_Lang(String m_Question_Lang) {
        this.m_Question_Lang = m_Question_Lang;
    }

    public String getM_Answer_Type() {
        return m_Answer_Type;
    }

    public void setM_Answer_Type(String m_Answer_Type) {
        this.m_Answer_Type = m_Answer_Type;
    }

    public String getM_Answer_Lang() {
        return m_Answer_Lang;
    }

    public void setM_Answer_Lang(String m_Answer_Lang) {
        this.m_Answer_Lang = m_Answer_Lang;
    }
    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }
}
