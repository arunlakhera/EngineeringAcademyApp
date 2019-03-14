package com.pikchillytechnologies.engineeingacademy.Model;

public class ArticlesModel {

    private String m_Article_URL;
    private String m_Article_Name;
    private String m_Article_Category;

    public ArticlesModel(String articleurl, String articlename, String articlecategory){

        this.m_Article_URL = articleurl;
        this.m_Article_Name = articlename;
        this.m_Article_Category = articlecategory;
    }

    public String getM_Article_URL() {
        return m_Article_URL;
    }

    public void setM_Article_URL(String m_Article_URL) {
        this.m_Article_URL = m_Article_URL;
    }

    public String getM_Article_Name() {
        return m_Article_Name;
    }

    public void setM_Article_Name(String m_Article_Name) {
        this.m_Article_Name = m_Article_Name;
    }

    public String getM_Article_Category() {
        return m_Article_Category;
    }

    public void setM_Article_Category(String m_Article_Category) {
        this.m_Article_Category = m_Article_Category;
    }
}
