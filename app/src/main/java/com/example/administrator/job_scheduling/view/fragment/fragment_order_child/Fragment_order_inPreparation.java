package com.example.administrator.job_scheduling.view.fragment.fragment_order_child;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.job_scheduling.R;

public class Fragment_order_inPreparation extends Fragment {

    private View view;
    private static String TAG = "Fragment_order_inPreparation";

    public static Fragment_order_inPreparation newInstance() {
        Fragment_order_inPreparation fragment_room_style1 = new Fragment_order_inPreparation ();
        Bundle b = new Bundle ();
        b.putString ( "fragment", TAG );
        return fragment_room_style1;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i ( TAG, "onCreateView: running " );

        view = inflater.inflate ( R.layout.fragment_order_inpreparation, container, false );
        return view;
    }

    @Override
    public void onStart() {
        super.onStart ();
        Log.i ( TAG, "onStart: running " );
    }
}
