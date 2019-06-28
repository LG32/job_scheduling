package com.example.administrator.job_scheduling.view.fragment.fragment_order_child;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.administrator.job_scheduling.R;
import com.example.administrator.job_scheduling.model.OrderBean;
import com.example.administrator.job_scheduling.util.tool.MyHandlerMsg;
import com.example.administrator.job_scheduling.view.activity.OrderInfoActivity;
import com.example.administrator.job_scheduling.view.adapter.RecyclerViewAdapter;
import com.example.administrator.job_scheduling.view.diy_view.EmptyRecyclerView;

import java.util.ArrayList;

public class Fragment_order_finish extends Fragment {
    private View view;
    private static String TAG = "Fragment_order_finish";
    private ArrayList<OrderBean> orderBeans;
    private NewsHandler handler = new NewsHandler ();

    public static Fragment_order_finish newInstance() {
        Fragment_order_finish fragment_room_style1 = new Fragment_order_finish ();
        Bundle b = new Bundle ();
        b.putString ( "fragment", TAG );
        return fragment_room_style1;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i ( TAG, "onCreateView: running " );

        view = inflater.inflate ( R.layout.fragment_order_running, container, false );
        return view;
    }

    @Override
    public void onStart() {
        super.onStart ();
        initValue ();
        initView ();
        Log.i ( TAG, "onStart: running " );
    }

    private void initView(){
        EmptyRecyclerView mRecyclerView = view.findViewById ( R.id.recyclerView );
        View mEmptyView = view.findViewById ( R.id.empty_view );

        mRecyclerView.setLayoutManager ( new LinearLayoutManager ( this.getActivity (),
                LinearLayoutManager.VERTICAL, false) );
        GridLayoutManager gridLayoutManager = new GridLayoutManager ( this.getActivity (), 2 );
        mRecyclerView.setLayoutManager ( gridLayoutManager );
        RecyclerViewAdapter mbookAdapter = new RecyclerViewAdapter ( this.getActivity (), orderBeans );
        mRecyclerView.setAdapter ( mbookAdapter );
        mRecyclerView.setmEmptyView ( mEmptyView );
        mRecyclerView.setNestedScrollingEnabled ( false );
        mRecyclerView.hideEmptyView ();

        mbookAdapter.setOnBooksClickListener ( new RecyclerViewAdapter.OnBooksClickListener () {
            @Override
            public void onClick(int position, OrderBean booksBean) {
                Intent intent = new Intent ( getContext (), OrderInfoActivity.class );
                startActivity ( intent );
            }
        } );
    }

    private void initValue(){

        orderBeans = new ArrayList<> (  );
        OrderBean orderBean = new OrderBean ();
        orderBean.setDescription ( "sb jbl" );
        orderBean.setEndTime ( "2019-6-30" );
        orderBean.setOrderId ( 111 );
        orderBean.setStartTime ( "2019-6-25" );
        orderBeans.add ( orderBean );
        Log.i ( TAG, "initValue: Fragment_news initvalue" );

    }

    @SuppressLint("HandlerLeak")
    public class NewsHandler extends Handler implements MyHandlerMsg {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REQUEST_FAIL:
                    Toast.makeText ( getActivity (), "网络请求失败", Toast.LENGTH_SHORT )
                            .show ();
                    break;
            }
        }
    }
}
