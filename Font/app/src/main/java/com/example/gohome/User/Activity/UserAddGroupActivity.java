package com.example.gohome.User.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gohome.R;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.sdsmdg.tastytoast.TastyToast;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import co.ceryle.radiorealbutton.RadioRealButtonGroup;
import fj.edittextcount.lib.FJEditTextCount;

public class UserAddGroupActivity extends AppCompatActivity {
    private EditText et_sendUserName;
    private EditText et_sendUserPhone;
    private EditText et_sendUserAddress;
    private FJEditTextCount et_personalDesc;
    private FJEditTextCount et_addDesc;
    private CircularProgressButton btn_addGroupSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_add_group);

        setTitle("加入组织");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
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

    public void init() {
        et_sendUserName = findViewById(R.id.et_sendUserName);
        et_sendUserPhone = findViewById(R.id.et_sendUserPhone);
        et_sendUserAddress = findViewById(R.id.et_sendUserAddress);
        et_personalDesc = findViewById(R.id.et_personalDesc);
        et_addDesc = findViewById(R.id.et_addDesc);
        btn_addGroupSubmit = findViewById(R.id.user_btn_addGroupSubmit);

        btn_addGroupSubmit.setOnClickListener(view -> {
            btn_addGroupSubmit.startAnimation();

            Bitmap bitmapDone = BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_action_done);
            Bitmap bitmapFail = BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_action_fail);

            //设置提交成功的图标和颜色
            btn_addGroupSubmit.doneLoadingAnimation(getResources().getColor(R.color.green), bitmapDone);
            //设置提交失败的图标和颜色
            //btn_addGroupSubmit.doneLoadingAnimation(getResources().getColor(R.color.red), bitmapFail);

            //提示提交成功toast
            Toast toast = TastyToast.makeText(this, "提交成功!", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
            //设置toast显示位置
            toast.setGravity(Gravity.CENTER, 0, 0);
            //调用show使得toast得以显示
            toast.show();

            //清空editText和选择框
            et_sendUserName.setText("");
            et_sendUserPhone.setText("");
            et_sendUserAddress.setText("");
            et_personalDesc.setText("");
            et_addDesc.setText("");

            //还原提交按钮
            //btn_addGroupSubmit.revertAnimation();
        });
    }
}
