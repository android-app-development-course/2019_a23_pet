package com.example.gohome.Member.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gohome.Entity.AdoptHandleOperation;
import com.example.gohome.Member.Fragment.MemberCheckFragment;
import com.example.gohome.Member.Fragment.MemberCheckUndoFragment;
import com.example.gohome.R;
import com.google.gson.Gson;
import com.sdsmdg.tastytoast.TastyToast;

import java.io.IOException;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import co.ceryle.radiorealbutton.RadioRealButtonGroup;
import fj.edittextcount.lib.FJEditTextCount;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MemberHandleOperationActivity extends AppCompatActivity {


    //记录提交结果
    public static final int SUCCESS = 1;
    public static final int FAIL = 0;

    private int infoId;

    private CircularProgressButton btn_submit;
    private FJEditTextCount et_description;
    private RadioRealButtonGroup radGro_handleOperationResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_handle_operation);

        setTitle("填写操作信息");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        infoId = intent.getIntExtra("infoId",0);  //获得infoId
        System.out.println("infoId:"+infoId);


        btn_submit = findViewById(R.id.btn_handleOperationSubmit);
        et_description = findViewById(R.id.et_handleOperationDes);
        radGro_handleOperationResult = findViewById(R.id.radGro_handleOperationResult);



        //初始化控件
        init();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // user_back button
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void init(){

        Bitmap bitmapDone =  BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_done);
        Bitmap bitmapFail = BitmapFactory.decodeResource(getResources(),R.drawable.ic_action_fail);

        radGro_handleOperationResult.setPosition(-1);  //将初始值设置为-1，用来检测用户是否选择

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //开始动画
                btn_submit.startAnimation();

                if(radGro_handleOperationResult.getPosition() == -1){
                    Toast.makeText(MemberHandleOperationActivity.this,"宠物类型选择不能为空，请重新提交！",Toast.LENGTH_LONG).show();
                    btn_submit.doneLoadingAnimation(getResources().getColor(R.color.red), bitmapFail);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            /**
                             *要执行的操作
                             */
                            cleanInputContent();
                        }
                    }, 3000);//3秒后执行Runnable中的run方法
                    return;
                }

                AdoptHandleOperation adoptHandleOperation = new AdoptHandleOperation();

                adoptHandleOperation.setInfoId(infoId);
                adoptHandleOperation.setDescription(et_description.getText());
                adoptHandleOperation.setState(radGro_handleOperationResult.getPosition() == 0 ? 1:0);


                //创建Handler，在子线程中使用handler发message给主线程
                @SuppressLint("HandlerLeak") Handler mHandler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        switch (msg.what) {
                            case SUCCESS: {// 设置提交成功的图标和颜色
                                btn_submit.doneLoadingAnimation(getResources().getColor(R.color.green), bitmapDone);
                                Toast.makeText(MemberHandleOperationActivity.this, "提交成功！", Toast.LENGTH_LONG).show();
                                System.out.println("成功啦啦啦啦啦！！");
                                break;
                            }
                            case FAIL:
                            {
                                //设置提交失败的图标和颜色
                                btn_submit.doneLoadingAnimation(getResources().getColor(R.color.red), bitmapFail);
                                Toast.makeText(MemberHandleOperationActivity.this,"提交失败，请重新提交！",Toast.LENGTH_LONG).show();
                                cleanInputContent();
                                System.out.println("失败啦啦啦啦啦啦！！");
                                break;
                            }

                        }
                    }
                };

                new Thread(
                        new Runnable() {
                            @Override
                            public void run() {
                                //建立client
                                final OkHttpClient[] client = {new OkHttpClient()};
                                //将传送实体类转为string类型的键值对
                                Gson gson = new Gson();
                                String json = gson.toJson(adoptHandleOperation);

                                System.out.println("json:"+json);
                                //设置请求体并设置contentType
                                RequestBody requestBody = FormBody.create(MediaType.parse("application/json;charset=utf-8"),json);
                                //请求
                                Request request=new Request.Builder()
                                        .url(getResources().getString(R.string.serverBasePath)+getResources().getString(R.string.insertAdoptHandleOperation))
                                        .post(requestBody)
                                        .build();
                                //新建call联结client和request
                                Call call= client[0].newCall(request);
                                //新建Message通过Handle与主线程通信
                                Message msg = new Message();
                                call.enqueue(new Callback() {
                                    @Override
                                    public void onFailure(Call call, IOException e) {
                                        //请求失败的处理
                                        Log.i("RESPONSE:","fail"+e.getMessage());
                                        msg.what = FAIL;
                                        mHandler.sendMessage(msg);
                                        Log.i("result的值", String.valueOf(false));
                                    }
                                    @Override
                                    public void onResponse(Call call, Response response) throws IOException {
                                        Log.i("RESPONSE:",response.body().string());
                                        msg.what = SUCCESS;
                                        mHandler.sendMessage(msg);
                                        Log.i("result的值", String.valueOf(true));
                                    }

                                });
                            }
                        }).start();


                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        /**
                         *要执行的操作
                         */
                        cleanInputContent();
                    }
                }, 3000);//3秒后执行Runnable中的run方法





                //设置提交成功的图标和颜色
                btn_submit.doneLoadingAnimation(getResources().getColor(R.color.green), bitmapDone);
                //设置提交失败的图标和颜色
                //btn.doneLoadingAnimation(getResources().getColor(R.color.red), bitmapFail);

                //提示提交成功toast
                Toast toast = TastyToast.makeText(MemberHandleOperationActivity.this, "提交成功!", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                //设置toast显示位置
                toast.setGravity(Gravity.CENTER,0,0);
                //调用show使得toast得以显示
                toast.show();

                //清空editText和选择框
                et_description.setText("");
                radGro_handleOperationResult.deselect();

                //还原提交按钮
//                btn.revertAnimation();
                Intent intent = new Intent(MemberHandleOperationActivity.this, MemberHomeActivity.class);
                intent.putExtra("idFirst",4);  //跳转到底部为审核fragment
                intent.putExtra("idSecond",2);  //跳转到处理中fragment
                startActivity(intent);
            }
        });


    }

    private void cleanInputContent(){
        //清空editText和选择框
        radGro_handleOperationResult.setPosition(-1);
        et_description.setText("");
        //还原提交按钮
        btn_submit.revertAnimation();
    }

}
