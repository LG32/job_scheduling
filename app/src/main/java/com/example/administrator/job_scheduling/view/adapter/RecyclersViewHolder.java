package com.example.administrator.job_scheduling.view.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.tu.circlelibrary.CirclePercentBar;
import com.example.administrator.job_scheduling.R;

import static android.support.constraint.Constraints.TAG;

public class RecyclersViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        CirclePercentBar circlePercentBar;
        TextView tvTitle;
        View view;

        RecyclersViewHolder(View itemView) {
        super ( itemView );
        Log.i ( TAG, "BooksViewHolder: build books view" );
        view = itemView;

        cardView = view.findViewById ( R.id.cvOrder );
        circlePercentBar = view.findViewById ( R.id.circleBar );
        tvTitle = view.findViewById ( R.id.tvTitle );
        }
}