package com.example.gohome.User.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gohome.R;
import com.sdsmdg.tastytoast.TastyToast;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import fj.edittextcount.lib.FJEditTextCount;

public class UserAdoptActivity extends AppCompatActivity {

    private EditText et_adoptUserName;
    private EditText et_adoptUserPhone;
    private EditText et_adoptUserAddress;
    private EditText et_adoptUserJob;
    private FJEditTextCount et_adoptDesc;
    private CircularProgressButton btn_submit;

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
        et_adoptUserName = findViewById(R.id.et_adoptUserName);
        et_adoptUserPhone = findViewById(R.id.et_adoptUserPhone);
        et_adoptUserAddress = findViewById(R.id.et_adoptUserAddress);
        et_adoptUserJob = findViewById(R.id.et_adoptUserJob);
        et_adoptDesc = findViewById(R.id.et_adoptDesc);

        btn_submit = findViewById(R.id.btn_adoptSubmit);
        btn_submit.setOnClickListener(view -> {
            btn_submit.startAnimation();

            Bitmap bitmapDone = BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_action_done);
            Bitmap bitmapFail = BitmapFactory.decodeResource(this.getResources(),R.drawable.ic_action_fail);

            //设置提交成功的图标和颜色
            btn_submit.doneLoadingAnimation(getResources().getColor(R.color.green), bitmapDone);
            //设置提交失败的图标和颜色
//            btn_submit.doneLoadingAnimation(getResources().getColor(R.color.red), bitmapFail);

            //提示提交成功toast
            Toast toast = TastyToast.makeText(this, "提交成功!", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
            //设置toast显示位置
            toast.setGravity(Gravity.CENTER,0,0);
            //调用show使得toast得以显示
            toast.show();

            //清空editText和选择框
            et_adoptUserName.setText("");
            et_adoptUserPhone.setText("");
            et_adoptUserAddress.setText("");
            et_adoptUserJob.setText("");
            et_adoptDesc.setText("");

            //还原提交按钮
//            btn_submit.revertAnimation();
        });
    }
}

