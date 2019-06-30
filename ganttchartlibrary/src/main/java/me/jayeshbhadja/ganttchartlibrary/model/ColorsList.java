package me.jayeshbhadja.ganttchartlibrary.model;


import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import me.jayeshbhadja.ganttchartlibrary.R;

import static android.content.ContentValues.TAG;

public class ColorsList {

    private static ColorsList colorsList;

    private HashMap<String, ColorBean> colorMap;

    private HashSet<String> colorSet;

    private ArrayList<ColorBean> colorBeans = new ArrayList<> (  );

    private static int flag = 1;

    private ColorsList(){}

    public static ColorsList getColorsList(){
        if (colorsList == null){
            colorsList = new ColorsList ();
            colorsList.initColor ();
            colorsList.initValue ();
            return colorsList;
        }else {
            return colorsList;
        }
    }

    private void initValue(){
        colorMap = new HashMap<> (  );
        colorSet = new HashSet<> (  );
        colorMap.put ( "分割线", colorBeans.get ( 0 ) );
        colorSet.add ( "分割线" );
    }

    private void initColor(){
        ColorBean cut_off_color = new ColorBean ();
        cut_off_color.setBar_border ( R.color.bar_cut_off );
        cut_off_color.setBar_done ( R.color.bar_cut_off );
        cut_off_color.setBar_undone ( R.color.bar_cut_off );
        colorBeans.add ( cut_off_color );

        ColorBean bar_color_cambridge_blue = new ColorBean ();
        bar_color_cambridge_blue.setBar_undone ( R.color.bar_undone_cambridge_blue );
        bar_color_cambridge_blue.setBar_done ( R.color.bar_done_cambridge_blue );
        bar_color_cambridge_blue.setBar_border ( R.color.bar_border_cambridge_blue );
        colorBeans.add ( bar_color_cambridge_blue );

        ColorBean bar_color_blue = new ColorBean ();
        bar_color_blue.setBar_border ( R.color.bar_border_blue );
        bar_color_blue.setBar_done ( R.color.bar_done_blue );
        bar_color_blue.setBar_undone ( R.color.bar_undone_blue );
        colorBeans.add ( bar_color_blue );

        ColorBean bar_color_pink = new ColorBean ();
        bar_color_pink.setBar_undone ( R.color.bar_undone_pink );
        bar_color_pink.setBar_done ( R.color.bar_done_pink );
        bar_color_pink.setBar_border ( R.color.bar_border_pink );
        colorBeans.add ( bar_color_pink );

        ColorBean bar_color_yellow = new ColorBean ();
        bar_color_yellow.setBar_border ( R.color.bar_border_yellow );
        bar_color_yellow.setBar_done ( R.color.bar_done_yellow );
        bar_color_yellow.setBar_undone ( R.color.bar_done_yellow );
        colorBeans.add ( bar_color_yellow );

        ColorBean bar_color_red = new ColorBean ();
        bar_color_red.setBar_undone ( R.color.bar_undone_red );
        bar_color_red.setBar_done ( R.color.bar_done_red );
        bar_color_red.setBar_border ( R.color.bar_border_red );
        colorBeans.add ( bar_color_red );
    }

    public ColorBean getColor(String uuid){
        Log.i ( TAG, "getColor: start " + uuid );
        if (colorSet.add ( uuid )){
            colorMap.put ( uuid, colorBeans.get ( flag ) );
            Log.i ( TAG, "getColor: " + flag );
            flag = flag + 1;
            if (flag >= colorBeans.size ()){
                flag = 1;
            }
            return colorMap.get ( uuid );
        }else {
            return colorMap.get ( uuid );
        }
    }

    public HashMap<String, ColorBean> getColorMap() {
        return colorMap;
    }

    public void setColorMap(HashMap<String, ColorBean> colorMap) {
        this.colorMap = colorMap;
    }

    public HashSet<String> getColorSet() {
        return colorSet;
    }

    public void setColorSet(HashSet<String> colorSet) {
        this.colorSet = colorSet;
    }
}
