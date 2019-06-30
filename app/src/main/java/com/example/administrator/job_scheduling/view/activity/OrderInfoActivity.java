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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.job_scheduling.R;
import com.example.administrator.job_scheduling.model.TaskBean;
import com.jn.chart.charts.LineChart;
import com.jn.chart.charts.PieChart;
import com.jn.chart.components.Legend;
import com.jn.chart.data.Entry;
import com.jn.chart.data.PieData;
import com.jn.chart.data.PieDataSet;
import com.jn.chart.formatter.PercentFormatter;
import com.jn.chart.formatter.ValueFormatter;
import com.jn.chart.highlight.Highlight;
import com.jn.chart.listener.OnChartValueSelectedListener;
import com.jn.chart.manager.LineChartManager;
import com.jn.chart.utils.ViewPortHandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jayeshbhadja.ganttchartlibrary.activity.Gantt;
import me.jayeshbhadja.ganttchartlibrary.model.GanttData;
import me.jayeshbhadja.ganttchartlibrary.model.Milestone;
import me.jayeshbhadja.ganttchartlibrary.model.Project;
import me.jayeshbhadja.ganttchartlibrary.model.Task;
import me.jayeshbhadja.ganttchartlibrary.utils.AppConstant;

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
    private static String TAG = "OrderInfoActivity";
    private ArrayList<ArrayList<TaskBean>> arrayLists;

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
        initPieChartView ();
        initLineChartView ();
    }

    private void initValue() {

    }

    private void initLineChartView() {

        LineChart lineChart = findViewById ( R.id.lineChart1 );
        lineChart.setDescription ( "周数" );
        lineChart.setTouchEnabled ( true );
        lineChart.setScaleEnabled ( false );

        ArrayList<Integer> data = new ArrayList<> ();
        int sum = 0;
        Random ra = new Random ();
        for (int i = 0; i < 20; i++) {
            sum = (int) (sum + ra.nextInt ( 5 ));
            Log.i ( TAG, "initLineChartView: " + sum );
            data.add ( sum );
        }

        initLineChart1 ( lineChart, data );
    }

    private void initLineChart1(LineChart mLineChart1, ArrayList<Integer> data) {
        //设置x轴的数据
        ArrayList<String> xValues = new ArrayList<> ();
        for (int i = 0; i < 20; i++) {
            xValues.add ( i + "周" );
        }

        //设置y轴的数据
        ArrayList<Entry> yValue = new ArrayList<> ();

        for (int i = 0; i < data.size (); i++) {
            yValue.add ( new Entry ( data.get ( i ), i + 1 ) );
        }
        //设置折线的名称
        LineChartManager.setLineName ( "成本/万" );
        //创建一条折线的图表
        LineChartManager.initSingleLineChart ( this, mLineChart1, xValues, yValue );
    }

    /**
     * 初始化饼图数据
     */
    private void initPieChartView() {
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


    /**
     * 初始化饼图设定
     *
     * @param pieChart piechart对象
     * @param data     piechart具体数据
     */
    public void initPieChart(PieChart pieChart, ArrayList<Float> data) {

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

        pieChart.setData ( generatePieData ( data ) );

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
        switch (view.getId ()) {
            case R.id.btGantt:
                initGanttView ();
                break;
        }
    }

    private void initDate() {
        arrayLists = new ArrayList<> ();
        ArrayList<TaskBean> taskBeans = new ArrayList<> ();
        TaskBean taskBean = new TaskBean ();
        taskBean.setUuid ( "1" );
        taskBean.setTaskId ( 1 );
        taskBean.setMachineId ( 1 );
        taskBean.setStartDate ( "2019-03-25" );
        taskBean.setEndDate ( "2019-04-15" );

        TaskBean taskBean1 = new TaskBean ();
        taskBean1.setUuid ( "1" );
        taskBean1.setTaskId ( 2 );
        taskBean1.setMachineId ( 1 );
        taskBean1.setStartDate ( "2019-04-20" );
        taskBean1.setEndDate ( "2019-05-20" );

        TaskBean taskBean2 = new TaskBean ();
        taskBean2.setUuid ( "2" );
        taskBean2.setTaskId ( 3 );
        taskBean2.setMachineId ( 1 );
        taskBean2.setStartDate ( "2019-06-10" );
        taskBean2.setEndDate ( "2019-07-30" );

        taskBeans.add ( taskBean );
        taskBeans.add ( taskBean1 );
        taskBeans.add ( taskBean2 );
        arrayLists.add ( taskBeans );

/*********************************************************************/

        ArrayList<TaskBean> taskBeans1 = new ArrayList<> ();
        TaskBean taskBean3 = new TaskBean ();
        taskBean3.setUuid ( "2" );
        taskBean3.setTaskId ( 1 );
        taskBean3.setMachineId ( 2 );
        taskBean3.setStartDate ( "2019-04-25" );
        taskBean3.setEndDate ( "2019-05-15" );

        TaskBean taskBean4 = new TaskBean ();
        taskBean4.setUuid ( "2" );
        taskBean4.setTaskId ( 2 );
        taskBean4.setMachineId ( 2 );
        taskBean4.setStartDate ( "2019-05-20" );
        taskBean4.setEndDate ( "2019-06-10" );

        TaskBean taskBean5 = new TaskBean ();
        taskBean5.setUuid ( "3" );
        taskBean5.setTaskId ( 3 );
        taskBean5.setMachineId ( 2 );
        taskBean5.setStartDate ( "2019-06-10" );
        taskBean5.setEndDate ( "2019-07-30" );

        taskBeans1.add ( taskBean3 );
        taskBeans1.add ( taskBean4 );
        taskBeans1.add ( taskBean5 );
        arrayLists.add ( taskBeans1 );

        /****************************************************************/

        ArrayList<TaskBean> taskBeans2 = new ArrayList<> ();
        TaskBean taskBean6 = new TaskBean ();
        taskBean6.setUuid ( "3" );
        taskBean6.setTaskId ( 1 );
        taskBean6.setMachineId ( 3 );
        taskBean6.setStartDate ( "2019-03-25" );
        taskBean6.setEndDate ( "2019-04-25" );

        TaskBean taskBean7 = new TaskBean ();
        taskBean7.setUuid ( "4" );
        taskBean7.setTaskId ( 2 );
        taskBean7.setMachineId ( 3 );
        taskBean7.setStartDate ( "2019-05-10" );
        taskBean7.setEndDate ( "2019-05-20" );

        TaskBean taskBean8 = new TaskBean ();
        taskBean8.setUuid ( "4" );
        taskBean8.setTaskId ( 3 );
        taskBean8.setMachineId ( 3 );
        taskBean8.setStartDate ( "2019-06-10" );
        taskBean8.setEndDate ( "2019-07-30" );

        taskBeans2.add ( taskBean6 );
        taskBeans2.add ( taskBean7 );
        taskBeans2.add ( taskBean8 );
        arrayLists.add ( taskBeans2 );
    }

    private void initGanttView() {
        this.compatActivity = this;
        String projectStart = "2019-03-25";
        String projectEnd = "2019-09-01";
        project = new Project ( "1", "Test", projectStart, projectEnd );
        tasks = new ArrayList<Task> ();
        milestones = new ArrayList<> ();
        initDate ();
        for (int i = 0; i < arrayLists.size (); i++) {

            for (int j = 0; j < arrayLists.get ( i ).size (); j++) {
                TaskBean taskBean = arrayLists.get ( i ).get ( j );
                Task task = new Task ( taskBean.getUuid (),
                        "Machine" + taskBean.getMachineId () + " Task" + taskBean.getTaskId (),
                        taskBean.getStartDate (),
                        taskBean.getEndDate (),
                        judgeState ( taskBean.getStartDate (), taskBean.getEndDate () ),
                        judgeStatusDate ( taskBean.getEndDate () ),
                        null );
                tasks.add ( task );
            }

            Task task = new Task("分割线", "分割线", projectStart, projectEnd, AppConstant.GANTT_STATUS_CUTOFF, projectEnd, null);
            tasks.add(task);

        }
        GanttData.initGanttData ( project, tasks, milestones );
        Intent intent = new Intent ( compatActivity, Gantt.class );
        startActivity ( intent );
    }

    /**
     * @param startDate task开始日期 yyyy-mm-dd
     * @param endDate   task结束日期 yyyy-mm-dd
     * @return AppConstant中的状态码
     */
    private String judgeState(String startDate, String endDate) {
        String todayDate = getTodayDate ();

        assert todayDate != null;
        int today = stringToInt ( todayDate );
        int start = stringToInt ( startDate );
        int end = stringToInt ( endDate );

        if (start >= today) {
            return AppConstant.DATE_FORMAT_yyyy_MM_dd;
        } else {
            if (end <= today) {
                return AppConstant.GANTT_STATUS_COMPLETED;
            }
            return AppConstant.GANTT_STATUS_ONTRACK;
        }
    }

    /**
     * yyyy-mm-dd转int
     *
     * @param date yyyy-mm-dd格式String日期
     * @return int型如20190625日期格式
     */
    private int stringToInt(String date) {
        String str[] = date.split ( "-" );
        StringBuilder strDate = new StringBuilder ();
        int intDate;
        if (str.length != 0) {
            for (String aStr : str) {
                strDate.append ( aStr );
            }

            intDate = Integer.parseInt ( String.valueOf ( strDate ) );
            return intDate;
        }
        return 0;
    }

    private String toPercentageComplete(int start, int end, int today) {
        int percentage = 0;
        if (today <= start) {
            percentage = 0;
        } else if (end >= today) {
            percentage = 100;
        } else if (today > start && today < end) {
            percentage = ((today - start) / (end - start)) * 100;
        }
        return String.valueOf ( percentage );
    }

    private String judgeStatusDate(String endDate) {
        if (stringToInt ( endDate ) > stringToInt ( Objects.requireNonNull ( getTodayDate () ) )) {
            return getTodayDate ();
        } else {
            return endDate;
        }
    }

    private String getTodayDate() {

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat format = new SimpleDateFormat ( "yyyy-MM-dd" );
        try {
            String date = format.format ( new Date () );
            Log.i ( TAG, "getTodayDate" + date );
            return date;
        } catch (Exception e) {
            e.printStackTrace ();
            return null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu ( menu );
        getMenuInflater ().inflate ( R.menu.gantt, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId ()) {
            case R.id.action_gantt:
                GanttData.initGanttData ( project, tasks, milestones );
                Intent intent = new Intent ( compatActivity, Gantt.class );
                startActivity ( intent );
                return true;
            default:
                return super.onOptionsItemSelected ( item );
        }
    }
}
