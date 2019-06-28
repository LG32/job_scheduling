package com.example.administrator.job_scheduling.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.android.tu.circlelibrary.CirclePercentBar;
import com.example.administrator.job_scheduling.R;
import com.example.administrator.job_scheduling.model.OrderBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.content.ContentValues.TAG;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclersViewHolder> {

    private Context mContext;
    private List<OrderBean> orderBeans;
    private OnBooksClickListener onBooksClickListener;

    public interface OnBooksClickListener{
        void onClick(int position, OrderBean booksBean);
    }

    public RecyclerViewAdapter(Context mContext, List<OrderBean> orderBeans){
        this.mContext = mContext;
        this.orderBeans = orderBeans;
    }

    @NonNull
    @Override
    public RecyclersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from ( mContext )
                .inflate ( R.layout.item_ordersfragment, parent, false);
        return new RecyclersViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclersViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final OrderBean booksBean = orderBeans.get ( position );

        holder.tvTitle.setText ( "order name" );
        holder.circlePercentBar.setPercentData((float) (100*Math.random()),new DecelerateInterpolator ());

        holder.view.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if (null != onBooksClickListener){
                    onBooksClickListener.onClick ( position, booksBean );
                }
            }
        } );
    }

    public void setOnBooksClickListener(OnBooksClickListener onBooksClickListener){
        this.onBooksClickListener = onBooksClickListener;
    }

    @Override
    public int getItemCount() {
        return orderBeans.size ();
    }

    class RecyclersViewHolder extends RecyclerView.ViewHolder{

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

    private String dateFormat(String date){
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat format = new SimpleDateFormat ( "yyyy-MM-dd'T'HH:mm:ss" );
        try {
            Date date1 = format.parse ( date );
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat sdf = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss" );
            return sdf.format ( date1 );
        } catch (ParseException e) {
            e.printStackTrace ();
            return null;
        }
    }
}