package com.pikchillytechnologies.engineeingacademy.Adapter;

import android.content.Context;
import android.graphics.Movie;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pikchillytechnologies.engineeingacademy.Model.CoursesModel;
import com.pikchillytechnologies.engineeingacademy.Model.DownloadedFileModel;
import com.pikchillytechnologies.engineeingacademy.R;

import java.util.List;

public class MyDownloadsAdapter extends RecyclerView.Adapter<MyDownloadsAdapter.MyViewHolder>{

    private List<DownloadedFileModel> m_FileName_List;

    public MyDownloadsAdapter(List<DownloadedFileModel> fileName){

        this.m_FileName_List = fileName;
    }

    @NonNull
    @Override
    public MyDownloadsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_downloads_listview,parent,false);
        return new MyDownloadsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyDownloadsAdapter.MyViewHolder holder, int position) {

        DownloadedFileModel downloadedFile = m_FileName_List.get(position);
        holder.mTextView_FileName.setText(downloadedFile.getM_DownloadedFileName());
    }

    @Override
    public int getItemCount() {
        return m_FileName_List.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView mTextView_FileName;

        public MyViewHolder(View view){
            super(view);

            mTextView_FileName = view.findViewById(R.id.textView_FileName);
        }
    }
}
