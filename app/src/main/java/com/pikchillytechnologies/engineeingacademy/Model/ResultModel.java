package com.pikchillytechnologies.engineeingacademy.Model;

public class ResultModel {

    private String m_Question_Number;
    private String m_Question;
    private String m_Answer1;
    private String m_Answer2;
    private String m_Answer3;
    private String m_Answer4;
    private String m_Explaination;

    public ResultModel(String questionNumber, String question, String ans1, String ans2, String ans3, String ans4, String explaination){

        this.m_Question_Number = questionNumber;
        this.m_Question = question;
        this.m_Answer1 = ans1;
        this.m_Answer2 = ans2;
        this.m_Answer3 = ans3;
        this.m_Answer4 = ans4;
        this.m_Explaination = explaination;
    }

    public String getM_Question_Number() {
        return m_Question_Number;
    }

    public void setM_Question_Number(String m_Question_Number) {
        this.m_Question_Number = m_Question_Number;
    }

    public String getM_Question() {
        return m_Question;
    }

    public void setM_Question(String m_Question) {
        this.m_Question = m_Question;
    }

    public String getM_Answer1() {
        return m_Answer1;
    }

    public void setM_Answer1(String m_Answer1) {
        this.m_Answer1 = m_Answer1;
    }

    public String getM_Answer2() {
        return m_Answer2;
    }

    public void setM_Answer2(String m_Answer2) {
        this.m_Answer2 = m_Answer2;
    }

    public String getM_Answer3() {
        return m_Answer3;
    }

    public void setM_Answer3(String m_Answer3) {
        this.m_Answer3 = m_Answer3;
    }

    public String getM_Answer4() {
        return m_Answer4;
    }

    public void setM_Answer4(String m_Answer4) {
        this.m_Answer4 = m_Answer4;
    }

    public String getM_Explaination() {
        return m_Explaination;
    }

    public void setM_Explaination(String m_Explaination) {
        this.m_Explaination = m_Explaination;
    }
}
