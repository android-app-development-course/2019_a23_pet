package com.example.gohome.Register;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gohome.Entity.UserMessage;
import com.example.gohome.R;
import com.example.gohome.ui.login.LoginActivity;
import com.google.gson.Gson;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import co.ceryle.radiorealbutton.RadioRealButton;
import co.ceryle.radiorealbutton.RadioRealButtonGroup;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {

    private MaterialEditText et_registerUserName,et_registerUserAddress,et_registerUserPassword,et_registerUserTel;
    private RadioRealButtonGroup radGroupGender;
    private CircularProgressButton btn_registerSubmit;
    private UserMessage userMessage;
    private int gender;
    //提交成功图标
    private Bitmap bitmapDone;
    //提交失败图标
    private Bitmap bitmapFail;
    //记录请求结构
//    private boolean[] result = new boolean[1];

    public static final String USERNAME_REX = "^[a-zA-Z_\u4e00-\u9fa5]{2,8}$";
    public static final String PASSWORD_REX = "^[a-zA-Z0-9_]{8,16}$";
    public static final String TELEPHONE_REX = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";
    public static final int SUCCESS_CODE = 1;
    public static final int FAILURE_CODE = 0;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case SUCCESS_CODE://注册成功
                    //设置提交成功的图标和颜色
                    btn_registerSubmit.doneLoadingAnimation(getResources().getColor(R.color.green), bitmapDone);
                    Log.i("respond", msg.getData().getString("response"));
                    try {
                        JSONObject jsonObject = new JSONObject(msg.getData().getString("response"));
                        Boolean success = jsonObject.getBoolean("success");
                        if(success){//注册成功，跳转至登录
                            Toast.makeText(getApplicationContext(), "注册成功！", Toast.LENGTH_SHORT).show();
                            Handler handler1 = new Handler();
                            handler1.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent();
                                    setResult(0x110, intent);
                                    finish();
                                }
                            }, 2000);
                        }else {//注册失败，提示
                            String errMsg = jsonObject.getString("errMsg");
                            Toast.makeText(getApplicationContext(), errMsg, Toast.LENGTH_SHORT).show();
                            btn_registerSubmit.revertAnimation();
                        }
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(), "网络发生错误，注册失败！(" + e.getMessage() + ")", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case FAILURE_CODE:
                    //设置提交失败的图标和颜色
                    btn_registerSubmit.doneLoadingAnimation(getResources().getColor(R.color.green), bitmapFail);
                    Log.i("exception", msg.getData().getString("exception"));
                    String exceptionMsg = msg.getData().getString("exception");
                    Toast.makeText(getApplicationContext(), "网络发生错误，注册失败！", Toast.LENGTH_SHORT).show();
                    Handler handler1 = new Handler();
                    handler1.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            btn_registerSubmit.revertAnimation();
                        }
                    }, 3000);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //提交成功图标
        bitmapDone =  BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_action_done);
        //提交失败图标
        bitmapFail = BitmapFactory.decodeResource(this.getResources(),R.drawable.ic_action_fail);

        setTitle("注册");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        et_registerUserName = findViewById(R.id.et_registerUserName);
        et_registerUserAddress = findViewById(R.id.et_registerUserAddress);
        et_registerUserPassword = findViewById(R.id.et_registerUserPsd);
        et_registerUserTel = findViewById(R.id.et_registerUserTel);
        radGroupGender = findViewById(R.id.radGro_registerUserGender);
        btn_registerSubmit = findViewById(R.id.btn_registerSubmit);

        userMessage = new UserMessage();
        gender = 0;

        init();
    }

    private void init(){

        radGroupGender.setOnPositionChangedListener(new RadioRealButtonGroup.OnPositionChangedListener() {
            @Override
            public void onPositionChanged(RadioRealButton button, int currentPosition, int lastPosition) {
                gender = currentPosition;
            }
        });

        btn_registerSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_registerSubmit.startAnimation();

                String username, userPassword,telephone, address;
                username = et_registerUserName.getText().toString();
                userPassword = et_registerUserPassword.getText().toString();
                telephone = et_registerUserTel.getText().toString();
                address = et_registerUserAddress.getText().toString();

                if(!username.matches(USERNAME_REX)){
                    Toast.makeText(v.getContext(), "用户名无效，请重新输入", Toast.LENGTH_SHORT).show();
                    btn_registerSubmit.doneLoadingAnimation(getResources().getColor(R.color.red), bitmapFail);
                    btn_registerSubmit.revertAnimation();
                    return;
                }
                if(!userPassword.matches(PASSWORD_REX)){
                    Toast.makeText(v.getContext(), "密码无效，请重新输入", Toast.LENGTH_SHORT).show();
                    btn_registerSubmit.doneLoadingAnimation(getResources().getColor(R.color.red), bitmapFail);
                    btn_registerSubmit.revertAnimation();
                    return;
                }
                if(!telephone.matches(TELEPHONE_REX)){
                    Toast.makeText(v.getContext(), "联系方式无效，请重新输入", Toast.LENGTH_SHORT).show();
                    btn_registerSubmit.doneLoadingAnimation(getResources().getColor(R.color.red), bitmapFail);
                    btn_registerSubmit.revertAnimation();
                    return;
                }

                userMessage.setGender(gender);
                userMessage.setAddress(address);
                userMessage.setTelephone(telephone);
                userMessage.setUserType(0);
                userMessage.setUserPassword(userPassword);
                userMessage.setUserName(username);
                userMessage.setProtrait("");//头像url

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //建立client
                        OkHttpClient client = new OkHttpClient.Builder()
                                .connectTimeout(10, TimeUnit.SECONDS)//设置连接超时时间
                                .readTimeout(20, TimeUnit.SECONDS)//设置读取超时时间
                                .build();
                        //生成json数据
                        Gson gson = new Gson();
                        String json = gson.toJson(userMessage);
                        RequestBody requestBody = FormBody.create(MediaType.parse("application/json;charset=utf-8"),json);
                        //请求
                        Request request=new Request.Builder()
                                .url(getResources().getString(R.string.serverBasePath)+getResources().getString(R.string.insertUserMessage))
                                .post(requestBody)
                                .build();
                        //新建call联结client和request
                        Call call= client.newCall(request);
                        call.enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                //请求失败的处理
//                                Log.i("RESPONSE:","fail"+e.getMessage());
//                                result[0] = false;
//                                Log.i("result的值", String.valueOf(result[0]));
                                Message message = new Message();
                                message.what = 0;
                                Bundle bundle = new Bundle();
                                bundle.putString("exception", e.getMessage());
                                message.setData(bundle);
                                handler.sendMessage(message);
                               return;
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
//                                Log.i("RESPONSE:",response.body().string());
//                                result[0] = true;
//                                Log.i("result的值", String.valueOf(result[0]));
                                Message message = new Message();
                                message.what = 1;
                                Bundle bundle = new Bundle();
                                bundle.putString("response", response.body().string());
                                message.setData(bundle);
                                handler.sendMessage(message);
                                return;
                            }
                        });
                    }
                }).start();

            }
        });
    }


}
