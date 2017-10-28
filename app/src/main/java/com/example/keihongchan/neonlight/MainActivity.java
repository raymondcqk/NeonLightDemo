package com.example.keihongchan.neonlight;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * 霓虹灯（瞎眼灯）Demo
 *
 * @author 大云屋 陈其康
 * @email keihong_chan@outlook.com
 * @github https://github.com/raymondcqk
 */
public class MainActivity extends AppCompatActivity {

    //6个颜色
    private int[] color = {Color.parseColor("#FF6666"),
            Color.parseColor("#3399CC"),
            Color.parseColor("#FFFF00"),
            Color.parseColor("#CC0066"),
            Color.parseColor("#FF6600"),
            Color.parseColor("#009999")};

    //界面控件
    private TextView view0;
    private TextView view1;
    private TextView view2;
    private TextView view3;
    private TextView view4;
    private TextView view5;

    //背景布局
    private FrameLayout root;


    //全局计数（记录颜色index步进量）
    int count = 0;
    //按钮控件
    private Button btn_auto;
    private Button btn_click;
    //按钮状态标志
    private boolean autoKeyFlag = false;
    //线程对象（用于开始一个子线程，用于执行霓虹灯自动播放任务）
    private Thread thread;

    /**
     * 程序入口
     *
     * @param savedInstanceState 暂时不需理解
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化界面控件
        initView();

        //初始化 一个 子线程
        initThread();


    }

    /**
     * 初始化 一个 子线程
     */
    private void initThread() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (autoKeyFlag) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            neon();
                        }
                    });

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * 初始化界面控件
     */
    private void initView() {
        view0 = (TextView) findViewById(R.id.view_0);
        view1 = (TextView) findViewById(R.id.view_1);
        view2 = (TextView) findViewById(R.id.view_2);
        view3 = (TextView) findViewById(R.id.view_3);
        view4 = (TextView) findViewById(R.id.view_4);
        view5 = (TextView) findViewById(R.id.view_5);

        view0.setBackgroundColor(color[0]);
        view1.setBackgroundColor(color[1]);
        view2.setBackgroundColor(color[2]);
        view3.setBackgroundColor(color[3]);
        view4.setBackgroundColor(color[4]);
        view5.setBackgroundColor(color[5]);

        root = (FrameLayout) findViewById(R.id.root);


        btn_auto = (Button) findViewById(R.id.btn_auto);
        btn_click = (Button) findViewById(R.id.btn_click);
        btn_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                neon();
            }
        });

        btn_auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                autoKeyFlag = !autoKeyFlag;

                thread.start();
            }
        });
    }

    /**
     * 霓虹灯程序
     * <p>
     * 该函数每执行一次，步进变色一次
     */
    private void neon() {

        count++;


        view0.setBackgroundColor(color[(0 + count) % 6]);
        view1.setBackgroundColor(color[(1 + count) % 6]);
        view2.setBackgroundColor(color[(2 + count) % 6]);
        view3.setBackgroundColor(color[(3 + count) % 6]);
        view4.setBackgroundColor(color[(4 + count) % 6]);
        view5.setBackgroundColor(color[(5 + count) % 6]);

        //背景
//        root.setBackgroundColor(color[(0 + count) % 6]);


        //控制台打印当前 每个区块颜色信息
        Log.i("count", "count = " + count);
        Log.i("color", "color_0: index = " + ((0 + count) % 6) + " 颜色值：" + color[(0 + count) % 6]);
        Log.i("color", "color_1: index = " + ((1 + count) % 6) + " 颜色值：" + color[(1 + count) % 6]);
        Log.i("color", "color_2: index = " + ((2 + count) % 6) + " 颜色值：" + color[(3 + count) % 6]);
        Log.i("color", "color_3: index = " + ((3 + count) % 6) + " 颜色值：" + color[(4 + count) % 6]);
        Log.i("color", "color_4: index = " + ((4 + count) % 6) + " 颜色值：" + color[(5 + count) % 6]);
        Log.i("color", "color_5: index = " + ((5 + count) % 6) + " 颜色值：" + color[(6 + count) % 6]);

    }


    /**
     * 退出App时会执行的函数
     */
    @Override
    protected void onStop() {
        //退出App时停止线程
        if (thread != null && thread.isAlive()) {
            thread.interrupt();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        super.onStop();
    }
}
