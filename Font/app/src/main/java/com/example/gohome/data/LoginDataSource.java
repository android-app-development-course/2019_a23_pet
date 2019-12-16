package com.example.gohome.data;


import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.appcompat.widget.VectorEnabledTintResources;

import com.example.gohome.Entity.AreaOrganizer;
import com.example.gohome.Entity.MemberMessage;
import com.example.gohome.Entity.UserMessage;
import com.example.gohome.MainActivity;
import com.example.gohome.R;
import com.example.gohome.data.model.LoggedInUser;
import com.example.gohome.ui.login.LoginActivity;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */

public class LoginDataSource {

    private UserMessage userMessage;
    private MemberMessage memberMessage;
    private AreaOrganizer areaOrganizer;
    private int type;
    private String errMsg;
    private Activity context;
    private Message message;
    private Object lock = new Object();//控制message
    private Object lock1 = new Object();//控制userMessage
    public static final int SUCCESS_CODE = 1;
    public static final int FAILURE_CODE = 0;

    public Result<LoggedInUser> login(String userName, String userPassword, Activity context) {

        Log.i("loginDataSource", "login");
        this.context = context;

        return sendLoginMessage(userName, userPassword);
    }

    public void logout() {
        // TODO: revoke authentication
    }

    private Result<LoggedInUser> sendLoginMessage(String userName, String userPassword){
        Log.i("loginDataSource", "sendLogMessage");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.i("loginDataSource", "sendLogMessage thread run");
                    // TODO: handle loggedInUser authentication
                    OkHttpClient client = new OkHttpClient.Builder()
                            .connectTimeout(10, TimeUnit.SECONDS)//设置连接超时时间
                            .readTimeout(20, TimeUnit.SECONDS)//设置读取超时时间
                            .build();
                    //生成json数据
                    Request.Builder reqBuilder = new Request.Builder();
                    HttpUrl.Builder urlBuilder = HttpUrl.parse(context.getResources().getString(R.string.serverBasePath) + context.getResources().getString(R.string.loginUserMessage)).newBuilder();
                    urlBuilder.addQueryParameter("userName", userName);
                    urlBuilder.addQueryParameter("userPassword", userPassword);
                    //请求
                    reqBuilder.url(urlBuilder.build());
                    Request request = reqBuilder.build();
                    //新建call联结client和request
                    Response response = client.newCall(request).execute();

                    synchronized (lock) {
                        if (response.isSuccessful()) {//链接成功
                            String responseBody = response.body().string();
                            Log.i("RESPONSE IS SUCCESSFUL", responseBody);
                            message = new Message();
                            message.what = 1;
                            Bundle bundle = new Bundle();
                            bundle.putString("response", responseBody);
                            message.setData(bundle);

                        } else {//链接失败
                            Log.i("RESPONSE", "fail");
//                                result[0] = false;
//                                Log.i("result的值", String.valueOf(result[0]));
                            message = new Message();
                            message.what = 0;
                            Bundle bundle = new Bundle();
                            bundle.putString("exception", response.body().string());
                            message.setData(bundle);

                        }
                        Log.i("after get response", "lock.notify()");
                        lock.notify();
                    }
                    response.close();
                } catch (Exception e) {
                    message = new Message();
                    message.what = 0;
                    Bundle bundle = new Bundle();
                    bundle.putString("exception", e.getMessage());
                    message.setData(bundle);
                    Log.i("login connect error", e.getMessage());
                }
            }
        }).start();

        handleMessage();

        try{
//            synchronized (lock1){
//                if (errMsg==null){
//                    Log.i("errMsg", "lock1.wait()");
//                    lock1.wait();
//                }
//            }
            if(message!=null && message.what==SUCCESS_CODE){
                LoggedInUser loggedInUser = new LoggedInUser(userMessage, memberMessage, areaOrganizer);
                loggedInUser.setUserType(type);
                return new Result.Success<>(loggedInUser);
            }else{
                return new Result.Error(new RuntimeException(errMsg));
            }
        }catch (Exception e){
            return new Result.Error(new RuntimeException(e.getMessage()));
        }

    }

    private void handleMessage(){
        Log.i("handleMessage", "ing");

        try {
            synchronized (lock) {
                while (message == null) {
                    Log.i("message==null", "lock.wait()");
                    lock.wait();
                }
            }
            synchronized (lock1){
                Log.i("", "message" + message.getData());
                switch (message.what) {
                    case SUCCESS_CODE:
                        Log.i("respond", message.getData().getString("response"));
                        try {
                            JSONObject jsonObject = new JSONObject(message.getData().getString("response"));
                            Boolean success = jsonObject.getBoolean("success");
                            JSONObject userMsgJSON, memberMsgJSON, areaOrganizerJSON;
                            if(success){//登录成功，获取信息，转场
//                                Toast.makeText(context.getApplicationContext(), "登录成功！", Toast.LENGTH_SHORT).show();
                                type = jsonObject.getInt("type");
                                switch (type){
                                    case LoggedInUser.USERTYPE_NORMAL://游客
                                        userMessage = new UserMessage();//基本信息
                                        userMsgJSON = jsonObject.getJSONObject("userMessage");
                                        userMessage.setUserId(userMsgJSON.getInt("userId"));
                                        userMessage.setUserName(userMsgJSON.getString("userName"));
                                        userMessage.setProtrait(userMsgJSON.getString("portrait"));
                                        userMessage.setTelephone(userMsgJSON.getString("telephone"));
                                        userMessage.setCreated(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(userMsgJSON.getString("created")));
                                        userMessage.setAddress(userMsgJSON.getString("address"));
                                        userMessage.setGender(userMsgJSON.getInt("gender"));
                                        userMessage.setUserType(type);
                                        memberMessage = null;
                                        areaOrganizer = null;
                                        break;
                                    case LoggedInUser.USERTYPE_MEMBER://组员
                                        userMessage = new UserMessage();//基本信息
                                        userMsgJSON = jsonObject.getJSONObject("userMessage");
                                        userMessage.setUserId(userMsgJSON.getInt("userId"));
                                        userMessage.setUserName(userMsgJSON.getString("userName"));
                                        userMessage.setProtrait(userMsgJSON.getString("portrait"));
                                        userMessage.setTelephone(userMsgJSON.getString("telephone"));
                                        userMessage.setCreated(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(userMsgJSON.getString("created")));
                                        userMessage.setAddress(userMsgJSON.getString("address"));
                                        userMessage.setGender(userMsgJSON.getInt("gender"));
                                        userMessage.setUserType(type);
                                        memberMessage = new MemberMessage();
                                        memberMsgJSON = jsonObject.getJSONObject("memberMessage");
                                        memberMessage.setAreaId(memberMsgJSON.getInt("areaId"));
                                        memberMessage.setCreated(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(memberMsgJSON.getString("created")));
                                        memberMessage.setMessageId(memberMsgJSON.getInt("memberId"));
                                        memberMessage.setUserId(memberMsgJSON.getInt("userId"));
                                        areaOrganizer = new AreaOrganizer();
                                        areaOrganizerJSON = jsonObject.getJSONObject("areaOrganizer");
                                        areaOrganizer.setAddress(areaOrganizerJSON.getString("address"));
                                        areaOrganizer.setAreaId(areaOrganizerJSON.getInt("areaId"));
                                        areaOrganizer.setCreated(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(areaOrganizerJSON.getString("created")));
                                        areaOrganizer.setOrganizerName(areaOrganizerJSON.getString("organizerName"));
                                        areaOrganizer.setUserId(areaOrganizerJSON.getInt("userId"));
                                        break;
                                    case LoggedInUser.USERTYPE_ORGANIZER://组织
                                        userMessage = new UserMessage();//基本信息
                                        userMsgJSON = jsonObject.getJSONObject("userMessage");
                                        userMessage.setUserId(userMsgJSON.getInt("userId"));
                                        userMessage.setUserName(userMsgJSON.getString("userName"));
                                        userMessage.setProtrait(userMsgJSON.getString("portrait"));
                                        userMessage.setTelephone(userMsgJSON.getString("telephone"));
                                        userMessage.setCreated(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(userMsgJSON.getString("created")));
                                        userMessage.setAddress(userMsgJSON.getString("address"));
                                        userMessage.setGender(userMsgJSON.getInt("gender"));
                                        userMessage.setUserType(type);
                                        userMessage = (UserMessage) jsonObject.get("userMessage");
                                        memberMessage = null;
                                        areaOrganizer = new AreaOrganizer();
                                        areaOrganizerJSON = jsonObject.getJSONObject("areaOrganizer");
                                        areaOrganizer.setAddress(areaOrganizerJSON.getString("address"));
                                        areaOrganizer.setAreaId(areaOrganizerJSON.getInt("areaId"));
                                        areaOrganizer.setCreated(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(areaOrganizerJSON.getString("created")));
                                        areaOrganizer.setOrganizerName(areaOrganizerJSON.getString("organizerName"));
                                        areaOrganizer.setUserId(areaOrganizerJSON.getInt("userId"));
                                        break;
                                }
                            }else {//失败，提示
                                errMsg = jsonObject.getString("errMsg");
                                Log.i("登录失败", errMsg);
//                                Toast.makeText(context.getApplicationContext(), errMsg, Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            Log.i("登录返回信息处理错误" + e.getClass(), e.getMessage());
//                            Toast.makeText(context.getApplicationContext(), "网络发生错误，登录失败！(" + e.getMessage() + ")", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case FAILURE_CODE:
                        try {
                            errMsg = message.getData().getString("exception");
                            Log.i("登录信息发送失败", errMsg);
//                            LoginDataSource.this.notify();
//                            Toast.makeText(context.getApplicationContext(), "网络发生错误，登录失败！(" + errMsg + ")", Toast.LENGTH_SHORT).show();
                        }catch (Exception e){
                            Log.i("登录错误1", e.getMessage());
                        }
                        break;
                }
                Log.i("after handling message","lock1.notify()");
                lock1.notify();
            }

        }catch (Exception e){
            Log.i("handle Exception", e.getMessage());
        }



    }

}
