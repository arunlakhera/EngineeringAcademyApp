package com.pikchillytechnologies.engineeingacademy.Model;

public class SubCoursePackage {

    private String m_Sub_Course_Name;
    private String m_Cost;

    public SubCoursePackage(){ }

    public SubCoursePackage(String sub_Course_Name, String cost){

        this.m_Sub_Course_Name = sub_Course_Name;
        this.m_Cost = cost;
      }


    public String getM_Sub_Course_Name() {
        return m_Sub_Course_Name;
    }

    public void setM_Sub_Course_Name(String m_Sub_Course_Name) {
        this.m_Sub_Course_Name = m_Sub_Course_Name;
    }

    public String getM_Cost() {
        return m_Cost;
    }

    public void setM_Cost(String m_Cost) {
        this.m_Cost = m_Cost;
    }

}
