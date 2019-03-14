package com.pikchillytechnologies.engineeingacademy.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pikchillytechnologies.engineeingacademy.Model.ArticlesModel;
import com.pikchillytechnologies.engineeingacademy.Model.CoursesModel;
import com.pikchillytechnologies.engineeingacademy.R;

import java.util.List;

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.MyViewHolder> {

    private List<ArticlesModel> m_Articles_List;
    private Context mContext;

    public ArticlesAdapter(Context context, List<ArticlesModel> articlesModel){
        this.mContext = context;
        this.m_Articles_List = articlesModel;
    }

    @NonNull
    @Override
    public ArticlesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.articles_listview,parent,false);
        mContext = parent.getContext();
        return new ArticlesAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticlesAdapter.MyViewHolder holder, int position) {

        ArticlesModel article = m_Articles_List.get(position);
        holder.m_ArticleName_TextView.setText(article.getM_Article_Name() + " [PDF]");

    }

    @Override
    public int getItemCount() {
        return m_Articles_List.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView m_ArticleName_TextView;

        public MyViewHolder(View view){
            super(view);

            m_ArticleName_TextView = view.findViewById(R.id.textView_ArticleName);

        }
    }
}