package com.pikchillytechnologies.engineeingacademy.Model;

public class UserResponseModel {

    private String m_User_Id;
    private String m_Exam_Id;
    private String m_Question_Id;
    private Boolean m_User_Answer1_Flag;
    private Boolean m_User_Answer2_Flag;
    private Boolean m_User_Answer3_Flag;
    private Boolean m_User_Answer4_Flag;
    private Boolean m_User_Answer5_Flag;
    private Boolean m_User_Answer6_Flag;

    public UserResponseModel(String userId, String examId, String questionId, Boolean userAnswer1Flag, Boolean userAnswer2Flag, Boolean userAnswer3Flag, Boolean userAnswer4Flag, Boolean userAnswer5Flag, Boolean userAnswer6Flag){

        this.m_User_Id = userId;
        this.m_Exam_Id = examId;
        this.m_Question_Id = examId;
        this.m_User_Answer1_Flag = userAnswer1Flag;
        this.m_User_Answer2_Flag = userAnswer2Flag;
        this.m_User_Answer3_Flag = userAnswer3Flag;
        this.m_User_Answer4_Flag = userAnswer4Flag;
        this.m_User_Answer5_Flag = userAnswer5Flag;
        this.m_User_Answer6_Flag = userAnswer6Flag;
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

    public Boolean getM_User_Answer1_Flag() {
        return m_User_Answer1_Flag;
    }

    public void setM_User_Answer1_Flag(Boolean m_User_Answer1_Flag) {
        this.m_User_Answer1_Flag = m_User_Answer1_Flag;
    }

    public Boolean getM_User_Answer2_Flag() {
        return m_User_Answer2_Flag;
    }

    public void setM_User_Answer2_Flag(Boolean m_User_Answer2_Flag) {
        this.m_User_Answer2_Flag = m_User_Answer2_Flag;
    }

    public Boolean getM_User_Answer3_Flag() {
        return m_User_Answer3_Flag;
    }

    public void setM_User_Answer3_Flag(Boolean m_User_Answer3_Flag) {
        this.m_User_Answer3_Flag = m_User_Answer3_Flag;
    }

    public Boolean getM_User_Answer4_Flag() {
        return m_User_Answer4_Flag;
    }

    public void setM_User_Answer4_Flag(Boolean m_User_Answer4_Flag) {
        this.m_User_Answer4_Flag = m_User_Answer4_Flag;
    }

    public Boolean getM_User_Answer5_Flag() {
        return m_User_Answer5_Flag;
    }

    public void setM_User_Answer5_Flag(Boolean m_User_Answer5_Flag) {
        this.m_User_Answer5_Flag = m_User_Answer5_Flag;
    }

    public Boolean getM_User_Answer6_Flag() {
        return m_User_Answer6_Flag;
    }

    public void setM_User_Answer6_Flag(Boolean m_User_Answer6_Flag) {
        this.m_User_Answer6_Flag = m_User_Answer6_Flag;
    }
}
