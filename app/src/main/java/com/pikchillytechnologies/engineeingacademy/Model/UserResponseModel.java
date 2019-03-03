package com.pikchillytechnologies.engineeingacademy.Model;

public class UserResponseModel {

    private String m_User_Id;
    private String m_Exam_Id;
    private String m_Question_Id;
    private String m_User_Answer1_Flag;
    private String m_User_Answer2_Flag;
    private String m_User_Answer3_Flag;
    private String m_User_Answer4_Flag;
    private String m_User_Answer5_Flag;
    private String m_User_Answer6_Flag;
    private Boolean m_User_Responded_Flag;

    public UserResponseModel(String userId, String examId, String questionId, String userAnswer1Flag, String userAnswer2Flag, String userAnswer3Flag, String userAnswer4Flag, String userAnswer5Flag, String userAnswer6Flag, Boolean respondedFlag){

        this.m_User_Id = userId;
        this.m_Exam_Id = examId;
        this.m_Question_Id = questionId;
        this.m_User_Answer1_Flag = userAnswer1Flag;
        this.m_User_Answer2_Flag = userAnswer2Flag;
        this.m_User_Answer3_Flag = userAnswer3Flag;
        this.m_User_Answer4_Flag = userAnswer4Flag;
        this.m_User_Answer5_Flag = userAnswer5Flag;
        this.m_User_Answer6_Flag = userAnswer6Flag;
        this.m_User_Responded_Flag = respondedFlag;
    }

    public String getM_User_Id() {
        return m_User_Id;
    }

    public void setM_User_Id(String m_User_Id) {
        this.m_User_Id = m_User_Id;
    }

    public String getM_Exam_Id() {
        return m_Exam_Id;
    }

    public void setM_Exam_Id(String m_Exam_Id) {
        this.m_Exam_Id = m_Exam_Id;
    }

    public String getM_Question_Id() {
        return m_Question_Id;
    }

    public void setM_Question_Id(String m_Question_Id) {
        this.m_Question_Id = m_Question_Id;
    }

    public String getM_User_Answer1_Flag() {
        return m_User_Answer1_Flag;
    }

    public void setM_User_Answer1_Flag(String m_User_Answer1_Flag) {
        this.m_User_Answer1_Flag = m_User_Answer1_Flag;
    }

    public String getM_User_Answer2_Flag() {
        return m_User_Answer2_Flag;
    }

    public void setM_User_Answer2_Flag(String m_User_Answer2_Flag) {
        this.m_User_Answer2_Flag = m_User_Answer2_Flag;
    }

    public String getM_User_Answer3_Flag() {
        return m_User_Answer3_Flag;
    }

    public void setM_User_Answer3_Flag(String m_User_Answer3_Flag) {
        this.m_User_Answer3_Flag = m_User_Answer3_Flag;
    }

    public String getM_User_Answer4_Flag() {
        return m_User_Answer4_Flag;
    }

    public void setM_User_Answer4_Flag(String m_User_Answer4_Flag) {
        this.m_User_Answer4_Flag = m_User_Answer4_Flag;
    }

    public String getM_User_Answer5_Flag() {
        return m_User_Answer5_Flag;
    }

    public void setM_User_Answer5_Flag(String m_User_Answer5_Flag) {
        this.m_User_Answer5_Flag = m_User_Answer5_Flag;
    }

    public String getM_User_Answer6_Flag() {
        return m_User_Answer6_Flag;
    }

    public void setM_User_Answer6_Flag(String m_User_Answer6_Flag) {
        this.m_User_Answer6_Flag = m_User_Answer6_Flag;
    }

    public Boolean getM_User_Responded_Flag() {
        return m_User_Responded_Flag;
    }

    public void setM_User_Responded_Flag(Boolean m_User_Responded_Flag) {
        this.m_User_Responded_Flag = m_User_Responded_Flag;
    }
}
