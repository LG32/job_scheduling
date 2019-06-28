package com.example.administrator.job_scheduling.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.job_scheduling.R;
import com.jn.chart.charts.PieChart;
import com.jn.chart.components.Legend;
import com.jn.chart.data.Entry;
import com.jn.chart.data.PieData;
import com.jn.chart.data.PieDataSet;
import com.jn.chart.formatter.ValueFormatter;
import com.jn.chart.highlight.Highlight;
import com.jn.chart.listener.OnChartValueSelectedListener;
import com.jn.chart.utils.ViewPortHandler;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jayeshbhadja.ganttchartlibrary.activity.Gantt;
import me.jayeshbhadja.ganttchartlibrary.model.GanttData;
import me.jayeshbhadja.ganttchartlibrary.model.Milestone;
import me.jayeshbhadja.ganttchartlibrary.model.Project;
import me.jayeshbhadja.ganttchartlibrary.model.Task;

@SuppressLint("Registered")
public class OrderInfoActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.piechart1)
    PieChart piechart1;
    @BindView(R.id.tvPieChart1)
    TextView tvPieChart1;
    @BindView(R.id.piechart2)
    PieChart piechart2;
    @BindView(R.id.tvPieChart2)
    TextView tvPieChart2;
    @BindView(R.id.piechart3)
    PieChart piechart3;
    @BindView(R.id.tvPieChart3)
    TextView tvPieChart3;
    @BindView(R.id.btGantt)
    Button btGantt;

    private int pieChartSize = 3;
    private AppCompatActivity compatActivity;
    private Project project;
    private ArrayList<Task> tasks;
    private ArrayList<Milestone> milestones;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_order_info );
        ButterKnife.bind ( this );
        setSupportActionBar ( toolbar );

        initView ();
        initValue ();
    }

    private void initView() {
        ArrayList<PieChart> pieCharts = new ArrayList<> ();
        pieCharts.add ( piechart1 );
        pieCharts.add ( piechart2 );
        pieCharts.add ( piechart3 );

        ArrayList<TextView> textViews = new ArrayList<> ();
        textViews.add ( tvPieChart1 );
        textViews.add ( tvPieChart2 );
        textViews.add ( tvPieChart3 );

        tvTitle.setText ( "机器使用情况扇形图" );

        ArrayList<Float> date = new ArrayList<> ();
        date.add ( (float) (1 + Math.random () * 10) );
        date.add ( (float) (1 + Math.random () * 10) );

        for (int i = 0; i < pieChartSize; i++) {
            initPieChart ( pieCharts.get ( i ), date );
        }
    }

    private void initValue() {

    }

    public void initPieChart(PieChart pieChart, ArrayList<Float> date) {

        int sum = 15;

        pieChart.setDescription ( "机器运转监控" );
        pieChart.animateXY ( 1000, 1000 );
        pieChart.setDrawSliceText ( false );
        pieChart.setHoleRadius ( 40f );
        pieChart.setCenterTextSize ( 3f );
        pieChart.setCenterText ( generateCenterText ( sum ) );
        Legend legend = pieChart.getLegend ();

        if (sum == 0) {
            pieChart.setData ( generateEmptyPieData () );
            pieChart.setHighlightPerTapEnabled ( false );
            legend.setEnabled ( false );
            return;
        }

        pieChart.setData ( generatePieData ( date ) );

        legend.setEnabled ( true );
        legend.setPosition ( Legend.LegendPosition.BELOW_CHART_CENTER );
        pieChart.setHighlightPerTapEnabled ( true );

        //圆盘点击事件
        pieChart.setOnChartValueSelectedListener ( new OnChartValueSelectedListener () {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

            }

            @Override
            public void onNothingSelected() {

            }
        } );
    }

    /**
     * 中间文字绘制
     *
     * @param sum 总数
     * @return SpannableString类型对象
     */
    private SpannableString generateCenterText(int sum) {
        String total = Integer.toString ( sum );
        SpannableString s = new SpannableString ( total + "\n 监测天数" );
        s.setSpan ( new RelativeSizeSpan ( 5f ), 0, total.length (), 0 );
        s.setSpan ( new ForegroundColorSpan ( Color.rgb ( 88, 146, 240 ) ), 0, total.length (), 0 );
        s.setSpan ( new ForegroundColorSpan ( Color.rgb ( 153, 153, 153 ) ), total.length (), s.length (), 0 );

        return s;
    }

    /**
     * 图表数据设置
     *
     * @param data 运转时间参数
     * @return PieData对象
     */
    protected PieData generatePieData(ArrayList<Float> data) {
        ArrayList<Entry> yVals = new ArrayList<> ();
        ArrayList<String> xVals = new ArrayList<> ();

        xVals.add ( "使用率" );
        xVals.add ( "空闲率" );

        yVals.add ( new Entry ( data.get ( 0 ) * 100, 0 ) );
        yVals.add ( new Entry ( data.get ( 1 ) * 100, 1 ) );

        PieDataSet pieDataSet = new PieDataSet ( yVals, "" );
        pieDataSet.setValueFormatter ( new ValueFormatter () {//圆环内文字设置
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                int n = (int) value;

                String str = n + "%";
                if (n == 0) {
                    str = "";
                }
                return str;
            }
        } );

        ArrayList<Integer> colors = new ArrayList<> ();
        colors.add ( Color.rgb ( 23, 213, 159 ) );
        colors.add ( Color.rgb ( 245, 166, 35 ) );
        colors.add ( Color.rgb ( 184, 233, 134 ) );
        colors.add ( Color.rgb ( 255, 205, 210 ) );
        pieDataSet.setColors ( colors );//颜色设置

        pieDataSet.setSliceSpace ( 2f );
        pieDataSet.setValueTextColor ( Color.WHITE );
        pieDataSet.setValueTextSize ( 12f );

        return new PieData ( xVals, pieDataSet );
    }

    /**
     * 空图表数据设置
     *
     * @return PieData对象
     */
    protected PieData generateEmptyPieData() {
        ArrayList<Entry> yVals = new ArrayList<> ();
        ArrayList<String> xVals = new ArrayList<> ();

        xVals.add ( "无数据" );
        yVals.add ( new Entry ( (float) 1, 1 ) );

        PieDataSet pieDataSet = new PieDataSet ( yVals, "" );
        pieDataSet.setValueFormatter ( new ValueFormatter () {//圆环内文字设置为空
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return "";
            }
        } );

        ArrayList<Integer> colors = new ArrayList<> ();
        colors.add ( Color.rgb ( 153, 153, 153 ) );
        pieDataSet.setColors ( colors );

        pieDataSet.setSliceSpace ( 2f );
        pieDataSet.setValueTextColor ( Color.WHITE );
        pieDataSet.setValueTextSize ( 12f );

        return new PieData ( xVals, pieDataSet );
    }

    @OnClick(R.id.btGantt)
    public void onViewClicked(View view) {
        switch (view.getId ()){
            case R.id.btGantt:
                initGanttView ();
                break;
        }
    }

    private void initGanttView(){
        this.compatActivity = this;
        project = new Project("1", "Test", "2019-06-25", "2019-08-01");
        tasks = new ArrayList<Task>();
        milestones = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Task task = new Task(String.valueOf(i), "Task" + i, "2019-06-25", "2019-08-01", null, null, null);
            tasks.add(task);
        }
        GanttData.initGanttData(project, tasks, milestones);
        Intent intent = new Intent(compatActivity, Gantt.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.gantt, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_gantt:
                GanttData.initGanttData(project, tasks, milestones);
                Intent intent = new Intent(compatActivity, Gantt.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
