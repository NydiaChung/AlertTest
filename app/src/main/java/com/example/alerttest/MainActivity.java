package com.example.alerttest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //一般AlertDialog
    public void ad(View v){
        new AlertDialog.Builder(this)
                .setTitle("删除标题")
                .setMessage("你确定删除🐎？")
                .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this,"删除数据",Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this,"取消删除数据",Toast.LENGTH_LONG).show();
                    }
                })
                .show();//方法链调用
    }

    //单选列表AlertDialog
    public void ad2(View v){
        final String[] items={"红","绿","蓝"};//局部变量方法结束后就消失，+final后变量在方法结束后还在
        new AlertDialog.Builder(this)
                .setTitle("指定背景颜色")
                .setSingleChoiceItems(items, 2, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //提示颜色
                        Toast.makeText(MainActivity.this,items[i],Toast.LENGTH_LONG).show();
                        //移除dialog
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

    //自定义AlertDialog
    public void ad3(View v){
        //动态加载布局文件，得到对应的View对象
        View view=View.inflate(this,R.layout.dialog_view,null);
        //view的真实类型？布局文件根标签的类型
        //如何得到一个独立View的子View? view.findViewById(id);
        //m默认findViewById(id);是在setContentView（）的View中找

        view.findViewById(R.id.name);
        final EditText name=view.findViewById(R.id.name);
        final EditText pwd=view.findViewById(R.id.pwd);

        new AlertDialog.Builder(this)
               .setView(view)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //读取用户密码
                        String usrname=name.getText().toString();
                        String mima=pwd.getText().toString();
                        //提示
                        Toast.makeText(MainActivity.this,usrname+":"+mima,Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("取消",null)
                .show();
    }


    //圆形进度ProgressDialog
    public void pd(View v) throws InterruptedException {//回调方法在主线程执行
        final ProgressDialog progressDialog=ProgressDialog.show(MainActivity.this,"数据加载","数据加载中...");

        //模拟一个长时间的工作
        //长时间的工作不能在主线程执行，得启动分线程完成
        new Thread(){
            public void run(){
                //分线程
                //休息一会
               for (int i=0;i<10;i++){
                   try {
                       Thread.sleep(100);
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
               }
                progressDialog.dismiss();//方法实在分线程，内部使用Handler实现主线程移除dialog
               //不能在分线程中直接更新UI
               //Toast.makeText(MainActivity.this,"加载完成！",Toast.LENGTH_LONG).show();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {//在主线程执行
                        Toast.makeText(MainActivity.this,"加载完成！",Toast.LENGTH_LONG).show();
                    }
                });

                //run方法在分线程执行的条件：
                /*new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //在分线程执行
                    }
                });*/
            }
        }.start();
    }

    //水平进度ProgressDialog
    public void pd2(View view){
        //1.创建dialog对象
        final ProgressDialog pd=new ProgressDialog(this);
        //2.设置样式
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        //3.显示
        pd.show();
        //4.启动分线程，加载数据，并显示进度，当加载完成移除dialog
        new Thread(new Runnable() {
            @Override
            public void run() {
                //设置最大进度
                pd.setMax(100);
                for (int i=0;i<10;i++){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    pd.setProgress(pd.getProgress()+10);
                }
                pd.dismiss();
            }
        });
    }

    //DatePickDialog
    public void dpd(View view){
        //创建日历对象
        Calendar calendar=Calendar.getInstance();
        //得到当前的年月日
        final int year=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH);
        final int data=calendar.get(Calendar.DAY_OF_MONTH);

        new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int data) {

            }
        },year,month,data).show();
    }

    //TimePickDialog
    public void tpd(View view){
        Calendar c=Calendar.getInstance();
        int hour=c.get(Calendar.HOUR_OF_DAY);
        int min=c.get(Calendar.MINUTE);

        new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {

            }
        },hour,min,true).show();
    }
}
