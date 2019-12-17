package com.example.gohome.User.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gohome.Entity.AdoptAppliment;
import com.example.gohome.R;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import fj.edittextcount.lib.FJEditTextCount;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UserAdoptActivity extends AppCompatActivity {

    public static final int SUCCESS = 1;
    public static final int FAIL = 0;

    private EditText et_adoptUserName;
    private EditText et_adoptUserPhone;
    private EditText et_adoptUserAddress;
    private EditText et_adoptUserJob;
    private FJEditTextCount et_adoptDesc;
    private CircularProgressButton btn_submit;

    private int adoptId, userId;
    private String phone, address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_adopt);

        setTitle("填写领养信息");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
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

    private void init() {
        // 获取传过来的领养信息id
        Intent intent = getIntent();
        adoptId = intent.getIntExtra("adoptId", -1);
        userId = intent.getIntExtra("userId", -1);

        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        phone = sharedPreferences.getString("telephone", "");
        address = sharedPreferences.getString("address", "");

        et_adoptUserName = findViewById(R.id.et_adoptUserName);
        et_adoptUserPhone = findViewById(R.id.et_adoptUserPhone);
        et_adoptUserPhone.setText(phone);
        et_adoptUserAddress = findViewById(R.id.et_adoptUserAddress);
        et_adoptUserAddress.setText(address);
        et_adoptUserJob = findViewById(R.id.et_adoptUserJob);
        et_adoptDesc = findViewById(R.id.et_adoptDesc);
        btn_submit = findViewById(R.id.btn_adoptSubmit);

        Bitmap bitmapDone = BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_action_done);
        Bitmap bitmapFail = BitmapFactory.decodeResource(this.getResources(),R.drawable.ic_action_fail);

        btn_submit.setOnClickListener(view -> {
            btn_submit.startAnimation();

            String name = et_adoptUserName.getText().toString();
            phone = et_adoptUserPhone.getText().toString();
            address = et_adoptUserAddress.getText().toString();
            String job = et_adoptUserJob.getText().toString();
            String desc = et_adoptDesc.getText();

            if ("".equals(name) || "".equals(phone) || "".equals(address)
                    || "".equals(job) || "".equals(desc)) {
                btn_submit.doneLoadingAnimation(getResources().getColor(R.color.red), bitmapFail);
                Toast.makeText(UserAdoptActivity.this, "请不要留空", Toast.LENGTH_SHORT).show();
                Handler handler = new Handler();
                handler.postDelayed(this::cleanUp, 3000);
                return;
            }

            Handler handler = new Handler() {
                public void handleMessage(Message message){
                    switch (message.what){
                        case SUCCESS:
                            btn_submit.doneLoadingAnimation(getResources().getColor(R.color.green), bitmapDone);
                            Toast.makeText(UserAdoptActivity.this,"提交成功，请耐心等候！",Toast.LENGTH_LONG).show();
                            break;

                        case FAIL:
                            btn_submit.doneLoadingAnimation(getResources().getColor(R.color.red), bitmapFail);
                            Toast.makeText(UserAdoptActivity.this,"提交失败，请重新提交！",Toast.LENGTH_LONG).show();
                            break;
                    }
                }
            };

            new Thread(()->
            {
                Gson gson = new Gson();
                String json = gson.toJson(new AdoptAppliment(adoptId, userId, name, phone, address, job, desc));
                Log.i("表单json: ", json);
                RequestBody requestBody = FormBody.create(MediaType.parse("application/json;charset=utf-8"),json);

                Request request = new Request.Builder()
                        .url(getResources().getString(R.string.serverBasePath) +
                                getResources().getString(R.string.insertAdoptApplication))
                        .post(requestBody)
                        .build();
                Message msg = new Message();
                OkHttpClient okHttpClient = new OkHttpClient();
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.i("提交失败:", e.getMessage());
                        msg.what = FAIL;
                        handler.sendMessage(msg);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.i("提交成功:", response.body().string());
                        msg.what = SUCCESS;
                        handler.sendMessage(msg);
                    }
                });
            }).start();

            Handler mHandler = new Handler();
            mHandler.postDelayed(this::cleanUp, 3000);

        });
    }

    // 清空editText和选择框 还原提交按钮
    private void cleanUp() {
//        et_adoptUserName.setText("");
//        et_adoptUserPhone.setText("");
//        et_adoptUserAddress.setText("");
//        et_adoptUserJob.setText("");
//        et_adoptDesc.setText("");

        btn_submit.revertAnimation();
    }
}

